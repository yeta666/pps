package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyFundMapper;
import com.yeta.pps.mapper.MyProcurementMapper;
import com.yeta.pps.mapper.MySellMapper;
import com.yeta.pps.po.ProcurementApplyOrder;
import com.yeta.pps.po.SellApplyOrder;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.FundOrderVo;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.ProcurementApplyOrderVo;
import com.yeta.pps.vo.SellApplyOrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 资金相关逻辑处理
 * @author YETA
 * @date 2018/12/13/14:22
 */
@Service
public class FundService {

    private static final Logger LOG = LoggerFactory.getLogger(FundService.class);

    @Autowired
    private MyFundMapper myFundMapper;

    @Autowired
    private MyProcurementMapper myProcurementMapper;

    @Autowired
    private MySellMapper mySellMapper;

    /**
     * 新增收/付款单
     * @param fundOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addFundOrder(FundOrderVo fundOrderVo) {
        //获取参数
        Integer storeId = fundOrderVo.getStoreId();
        String applyOrderId = fundOrderVo.getApplyOrderId();
        byte type = fundOrderVo.getType();
        //结算状态和金额
        byte clearStatus;
        double orderMoney;
        double clearedMoney;
        double notClearedMoney;
        double changeMoney = Math.abs(fundOrderVo.getMoney().doubleValue());
        //判断新增类型
        switch (type) {
            //付款单
            case 1:
                fundOrderVo.setId("FKD_" + UUID.randomUUID().toString().replace("-", ""));
                ProcurementApplyOrder procurementApplyOrder = myProcurementMapper.findApplyOrderById(new ProcurementApplyOrderVo(storeId, applyOrderId));
                if (procurementApplyOrder == null) {
                    LOG.info("新增收/付款单，类型：【{}】，来源订单错误：【{}】", type, applyOrderId);
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                //获取结算状态和金额
                clearStatus = procurementApplyOrder.getClearStatus();
                orderMoney = Math.abs(procurementApplyOrder.getOrderMoney().doubleValue());
                clearedMoney = Math.abs(procurementApplyOrder.getClearedMoney().doubleValue());
                notClearedMoney = Math.abs(procurementApplyOrder.getNotClearedMoney().doubleValue());
                break;
            //收款单
            case 2:
                fundOrderVo.setId("SKD_" + UUID.randomUUID().toString().replace("-", ""));
                SellApplyOrder sellApplyOrder = mySellMapper.findApplyOrderById(new SellApplyOrderVo(storeId, applyOrderId));
                if (sellApplyOrder == null) {
                    LOG.info("新增收/付款单，类型：【{}】，来源订单错误：【{}】", type, applyOrderId);
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                //获取结算状态和金额
                clearStatus = sellApplyOrder.getClearStatus();
                orderMoney = Math.abs(sellApplyOrder.getOrderMoney().doubleValue());
                clearedMoney = Math.abs(sellApplyOrder.getClearedMoney().doubleValue());
                notClearedMoney = Math.abs(sellApplyOrder.getNotClearedMoney().doubleValue());
                break;
            default:
                LOG.info("新增收/付款单，类型错误：【{}】", type);
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //判断来源订单结算状态
        if (clearStatus != 0) {
            LOG.info("新增收/付款单，类型：【{}】，来源订单：【{}】，结算状态错误：【{}】", type, applyOrderId, clearStatus);
            return new CommonResponse(CommonResponse.CODE19, null, CommonResponse.MESSAGE19);
        }
        //判断要结算的金额是否正确
        if (changeMoney == notClearedMoney && changeMoney + clearedMoney == orderMoney) {       //全部结算
            fundOrderVo.setOrderStatus((byte) 1);
        } else if (changeMoney < notClearedMoney && changeMoney + clearedMoney < orderMoney) {      //部分结算
            fundOrderVo.setOrderStatus((byte) 0);
        } else {        //错误
            LOG.info("新增收/付款单，类型：【{}】，来源订单：【{}】，本单金额：【{}】，已结算金额：【{}】，未结算金额：【{}】，要结算的金额错误：【{}】",
                    type, applyOrderId, orderMoney, clearedMoney, notClearedMoney, changeMoney);
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        //修改来源订单结算状态和金额
        if (type == 1) {
            if (myProcurementMapper.updateApplyOrderClearStatusAndMoney(fundOrderVo) != 1) {
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }
        } else {
            if (mySellMapper.updateApplyOrderClearStatusAndMoney(fundOrderVo) != 1) {
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }
        }
        //设置初始属性
        fundOrderVo.setCreateTime(new Date());
        fundOrderVo.setOrderStatus((byte) 1);
        //新增收/付款单
        if (myFundMapper.addFundOrder(fundOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //TODO
        //收/付款是否要修改其他信息？？？
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }


    /**
     * 红冲收/付款单
     * @param fundOrderVo
     * @return
     */
    @Transactional
    public CommonResponse redDashed(FundOrderVo fundOrderVo) {
        Integer storeId = fundOrderVo.getStoreId();
        //修改单据状态
        if (myFundMapper.redDashed(fundOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        //获取该订单
        fundOrderVo = myFundMapper.findFundOrderById(fundOrderVo);
        //新增红冲单
        fundOrderVo.setStoreId(storeId);
        fundOrderVo.setId("HC_" + fundOrderVo.getId());
        fundOrderVo.setCreateTime(new Date());
        fundOrderVo.setOrderStatus((byte) -2);
        fundOrderVo.setMoney(new BigDecimal(-fundOrderVo.getMoney().doubleValue()));
        if (myFundMapper.addFundOrder(fundOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }


    /**
     * 根据type查询所有收/付款单
     * @param fundOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllFundOrder(FundOrderVo fundOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myFundMapper.findCountFundOrder(fundOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<FundOrderVo> fundOrderVos = myFundMapper.findAllPagedFundOrder(fundOrderVo, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "id"));
        titles.add(new Title("单据类型", "type"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("来源订单", "applyOrderId"));
        if (fundOrderVo.getType() == 1) {       //付款
            titles.add(new Title("供应商", "procurementApplyOrderVo.supplierName"));
        } else if (fundOrderVo.getType() == 2) {        //收款
            titles.add(new Title("客户", "sellApplyOrderVo.client.name"));
            titles.add(new Title("电话", "sellApplyOrderVo.client.phone"));
            titles.add(new Title("会员卡号", "sellApplyOrderVo.client.membershipNumber"));
        } else {
            throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
        }
        titles.add(new Title("本单金额", "money"));
        titles.add(new Title("银行账户", "bankAccount.name"));
        titles.add(new Title("账户号", "bankAccount.account"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, fundOrderVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }
}
