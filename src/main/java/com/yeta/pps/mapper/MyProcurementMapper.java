package com.yeta.pps.mapper;

import com.yeta.pps.po.ProcurementApplyOrder;
import com.yeta.pps.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyProcurementMapper {

    //采购申请订单

    int addApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    int deleteApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    int updateApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    int updateApplyOrderRemark(ProcurementApplyOrderVo procurementApplyOrderVo);

    int updateApplyOrderOrderStatusAndQuantity(StorageOrderVo storageOrderVo);

    int updateApplyOrderClearStatusAndMoney(FundOrderVo fundOrderVo);

    int findCountApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    List<ProcurementApplyOrderVo> findAllPagedApplyOrder(@Param("procurementApplyOrderVo") ProcurementApplyOrderVo procurementApplyOrderVo,
                                                         @Param("pageVo") PageVo pageVo);

    List<ProcurementApplyOrderVo> findAllApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    ProcurementApplyOrder findApplyOrderById(ProcurementApplyOrderVo procurementApplyOrderVo);

    List<ProcurementApplyOrderVo> findAllApplyOrderDetail(ProcurementApplyOrderVo procurementApplyOrderVo);

    List<String> findApplyOrderPrimaryKey(ProcurementApplyOrderVo procurementApplyOrderVo);

    //采购结果订单

    int addResultOrder(ProcurementResultOrderVo procurementResultOrderVo);

    int redDashed(ProcurementResultOrderVo procurementResultOrderVo);

    int findCountResultOrder(ProcurementResultOrderVo procurementResultOrderVo);

    List<ProcurementResultOrderVo> findAllPagedResultOrder(@Param("procurementResultOrderVo") ProcurementResultOrderVo procurementResultOrderVo,
                                                           @Param("pageVo") PageVo pageVo);

    List<ProcurementResultOrderVo> findAllResultOrder(ProcurementResultOrderVo procurementResultOrderVo);

    ProcurementResultOrderVo findResultOrderDetailById(ProcurementResultOrderVo procurementResultOrderVo);

    List<String> findResultOrderPrimaryKey(ProcurementResultOrderVo procurementResultOrderVo);
}
