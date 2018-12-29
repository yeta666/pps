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
        double changeMoney = Math.abs(fundOrderVo.getMoney());

        //判断新增类型
        switch (type) {
            case 1:     //付款单
                fundOrderVo.setId("FKD_" + UUID.randomUUID().toString().replace("-", ""));
                ProcurementApplyOrder procurementApplyOrder = myProcurementMapper.findApplyOrderById(new ProcurementApplyOrderVo(storeId, applyOrderId));
                if (procurementApplyOrder == null) {
                    return CommonResponse.error(CommonResponse.ADD_ERROR);
                }
                //获取结算状态和金额
                clearStatus = procurementApplyOrder.getClearStatus();
                orderMoney = Math.abs(procurementApplyOrder.getOrderMoney());
                clearedMoney = Math.abs(procurementApplyOrder.getClearedMoney());
                notClearedMoney = Math.abs(procurementApplyOrder.getNotClearedMoney());
                break;

            case 2:     //收款单
                fundOrderVo.setId("SKD_" + UUID.randomUUID().toString().replace("-", ""));
                SellApplyOrder sellApplyOrder = mySellMapper.findApplyOrderById(new SellApplyOrderVo(storeId, applyOrderId));
                if (sellApplyOrder == null) {
                    return CommonResponse.error(CommonResponse.ADD_ERROR);
                }

                //获取结算状态和金额
                clearStatus = sellApplyOrder.getClearStatus();
                orderMoney = Math.abs(sellApplyOrder.getOrderMoney());
                clearedMoney = Math.abs(sellApplyOrder.getClearedMoney());
                notClearedMoney = Math.abs(sellApplyOrder.getNotClearedMoney());
                break;

            default:
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //判断来源订单结算状态
        if (clearStatus != 0) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }

        //判断要结算的金额是否正确
        if (changeMoney == notClearedMoney && changeMoney + clearedMoney == orderMoney) {       //全部结算
            fundOrderVo.setOrderStatus((byte) 1);
            
        } else if (changeMoney < notClearedMoney && changeMoney + clearedMoney < orderMoney) {      //部分结算
            fundOrderVo.setOrderStatus((byte) 0);
            
        } else {        //错误
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }

        //修改来源订单结算状态和金额
        if (type == 1) {
            if (myProcurementMapper.updateApplyOrderClearStatusAndMoney(fundOrderVo) != 1) {
                return CommonResponse.error(CommonResponse.ADD_ERROR);
            }
        } else {
            if (mySellMapper.updateApplyOrderClearStatusAndMoney(fundOrderVo) != 1) {
                return CommonResponse.error(CommonResponse.ADD_ERROR);
            }
        }

        //设置初始属性
        fundOrderVo.setCreateTime(new Date());
        fundOrderVo.setOrderStatus((byte) 1);

        //新增收/付款单
        if (myFundMapper.addFundOrder(fundOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }
        //TODO
        //收/付款是否要修改其他信息？？？
        return CommonResponse.success();
    }


    /**
     * 红冲收/付款单
     * @param fundOrderVo
     * @return
     */
    @Transactional
    public CommonResponse redDashed(FundOrderVo fundOrderVo) {
        //获取参数
        Integer storeId = fundOrderVo.getStoreId();
        String userId = fundOrderVo.getUserId();
        String remark = fundOrderVo.getRemark();

        //红冲
        if (myFundMapper.redDashed(fundOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //获取红冲蓝单
        fundOrderVo = myFundMapper.findFundOrderById(fundOrderVo);

        //设置红冲红单
        fundOrderVo.setStoreId(storeId);
        fundOrderVo.setId("HC_" + fundOrderVo.getId());
        fundOrderVo.setCreateTime(new Date());
        fundOrderVo.setOrderStatus((byte) -2);
        fundOrderVo.setMoney(-fundOrderVo.getMoney());
        fundOrderVo.setUserId(userId);
        fundOrderVo.setRemark(remark);

        //新增红冲红单
        if (myFundMapper.addFundOrder(fundOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        
        return CommonResponse.success();
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

        //判断查询类型
        if (fundOrderVo.getType() == 1) {       //付款
            titles.add(new Title("供应商", "procurementApplyOrderVo.supplierName"));

        } else if (fundOrderVo.getType() == 2) {        //收款
            titles.add(new Title("客户", "sellApplyOrderVo.client.name"));
            titles.add(new Title("电话", "sellApplyOrderVo.client.phone"));
            titles.add(new Title("会员卡号", "sellApplyOrderVo.client.membershipNumber"));

        } else {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }
        
        titles.add(new Title("本单金额", "money"));
        titles.add(new Title("银行账户", "bankAccount.name"));
        titles.add(new Title("账户号", "bankAccount.account"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, fundOrderVos, pageVo);
        
        return CommonResponse.success(commonResult);
    }
}
