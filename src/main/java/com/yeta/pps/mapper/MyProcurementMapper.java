package com.yeta.pps.mapper;

import com.yeta.pps.po.ProcurementApplyOrder;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.ProcurementApplyOrderGoodsSkuVo;
import com.yeta.pps.vo.ProcurementApplyOrderVo;
import com.yeta.pps.vo.ProcurementResultOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyProcurementMapper {

    //采购申请订单

    int addApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    int deleteApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    int updateApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    int updateApplyOrderRemark(ProcurementApplyOrderVo procurementApplyOrderVo);

    int updateApplyOrderOrderStatusAndQuantity(ProcurementApplyOrderVo procurementApplyOrderVo);

    int findCountApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    List<ProcurementApplyOrderVo> findAllPagedApplyOrder(@Param("procurementApplyOrderVo") ProcurementApplyOrderVo procurementApplyOrderVo,
                                                         @Param("pageVo") PageVo pageVo);

    ProcurementApplyOrder findApplyOrderById(ProcurementApplyOrderVo procurementApplyOrderVo);

    ProcurementApplyOrderVo findApplyOrderDetailById(ProcurementApplyOrderVo procurementApplyOrderVo);

    //采购申请订单/商品规格关系

    int addApplyOrderGoodsSku(ProcurementApplyOrderGoodsSkuVo procurementApplyOrderGoodsSkuVo);

    int deleteApplyOrderGoodsSku(ProcurementApplyOrderGoodsSkuVo procurementApplyOrderGoodsSkuVo);

    int updateApplyOrderGoodsSku(ProcurementApplyOrderGoodsSkuVo procurementApplyOrderGoodsSkuVo);

    //采购结果订单

    int addResultOrder(ProcurementResultOrderVo procurementResultOrderVo);

    int findCountResultOrder(ProcurementResultOrderVo procurementResultOrderVo);

    List<ProcurementResultOrderVo> findAllPagedResultOrder(@Param("procurementResultOrderVo") ProcurementResultOrderVo procurementResultOrderVo,
                                                           @Param("pageVo") PageVo pageVo);
}
