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

    ProcurementApplyOrder findApplyOrderById(ProcurementApplyOrderVo procurementApplyOrderVo);

    ProcurementApplyOrderVo findApplyOrderDetailById(ProcurementApplyOrderVo procurementApplyOrderVo);

    //采购结果订单

    int addResultOrder(ProcurementResultOrderVo procurementResultOrderVo);

    int updateResultOrder(ProcurementResultOrderVo procurementResultOrderVo);

    int findCountResultOrder(ProcurementResultOrderVo procurementResultOrderVo);

    List<ProcurementResultOrderVo> findAllPagedResultOrder(@Param("procurementResultOrderVo") ProcurementResultOrderVo procurementResultOrderVo,
                                                           @Param("pageVo") PageVo pageVo);

    ProcurementResultOrderVo findResultOrderById(ProcurementResultOrderVo procurementResultOrderVo);

    ProcurementResultOrderVo findResultOrderDetailById(ProcurementResultOrderVo procurementResultOrderVo);
}
