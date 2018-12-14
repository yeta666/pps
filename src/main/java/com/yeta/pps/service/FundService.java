package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyFundMapper;
import com.yeta.pps.mapper.MyProcurementMapper;
import com.yeta.pps.po.ProcurementApplyOrder;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.FundOrderVo;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.ProcurementApplyOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 新增收/付款单
     * @param fundOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addFundOrder(FundOrderVo fundOrderVo) {
        byte type = fundOrderVo.getType();
        switch (type) {
            case 1:
                fundOrderVo.setId("SKD_" + UUID.randomUUID().toString());
                break;
            case 2:
                fundOrderVo.setId("FKD_" + UUID.randomUUID().toString());
                break;
            default:
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //查询来源订单
        //TODO
        //销售订单查询来源订单？？？
        ProcurementApplyOrder procurementApplyOrder = myProcurementMapper.findApplyOrderById(new ProcurementApplyOrderVo(fundOrderVo.getStoreId(), fundOrderVo.getApplyOrderId()));
        if (procurementApplyOrder == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //判断来源订单结算状态
        double notClearedMoney = procurementApplyOrder.getNotClearedMoney().doubleValue();
        double clearedMoney = procurementApplyOrder.getClearedMoney().doubleValue();
        double orderMoney = procurementApplyOrder.getOrderMoney().doubleValue();
        if (procurementApplyOrder.getClearStatus() != 0) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        double changeMoney = fundOrderVo.getMoney().doubleValue();
        if (changeMoney == notClearedMoney && changeMoney + clearedMoney == orderMoney) {       //全部完成
            fundOrderVo.setOrderStatus((byte) 1);
        } else if ((changeMoney < 0 ? -changeMoney : changeMoney) < (notClearedMoney < 0 ? -notClearedMoney : notClearedMoney) && (changeMoney < 0 ? -changeMoney : changeMoney) + (clearedMoney < 0 ? -clearedMoney : clearedMoney) < (orderMoney < 0 ? -orderMoney : orderMoney)) {      //部分完成
            fundOrderVo.setOrderStatus((byte) 0);
        } else {        //错误
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        //修改采购申请订单结算状态和金额
        if (myProcurementMapper.updateApplyOrderClearStatusAndMoney(fundOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //设置初始属性
        fundOrderVo.setCreateTime(new Date());
        fundOrderVo.setOrderStatus((byte) 1);
        //新增
        if (myFundMapper.addFundOrder(fundOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //TODO
        //收/付款是否要修改其他信息？？？
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
        /*titles.add(new Title("单据状态", "orderStatus"));*/
        titles.add(new Title("来源订单", "applyOrderId"));
        titles.add(new Title("供应商", "procurementApplyOrderVo.supplierName"));
        titles.add(new Title("本单金额", "money"));
        titles.add(new Title("银行账户", "bankAccount.name"));
        titles.add(new Title("账户号", "bankAccount.account"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, fundOrderVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }
}
