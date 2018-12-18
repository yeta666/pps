package com.yeta.pps.mapper;

import com.yeta.pps.po.SellApplyOrder;
import com.yeta.pps.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MySellMapper {

    //销售申请单

    int addApplyOrder(SellApplyOrderVo sellApplyOrderVo);

    int deleteApplyOrder(SellApplyOrderVo sellApplyOrderVo);

    int updateApplyOrder(SellApplyOrderVo sellApplyOrderVo);

    int updateApplyOrderRemark(SellApplyOrderVo sellApplyOrderVo);

    int updateApplyOrderOrderStatusAndQuantity(StorageOrderVo storageOrderVo);

    int updateApplyOrderClearStatusAndMoney(FundOrderVo fundOrderVo);

    int findCountApplyOrder(SellApplyOrderVo sellApplyOrderVo);

    List<SellApplyOrderVo> findAllPagedApplyOrder(@Param("sellApplyOrderVo") SellApplyOrderVo sellApplyOrderVo,
                                                  @Param("pageVo") PageVo pageVo);

    SellApplyOrder findApplyOrderById(SellApplyOrderVo sellApplyOrderVo);

    SellApplyOrderVo findApplyOrderDetailById(SellApplyOrderVo sellApplyOrderVo);

    //销售结果单

    int addResultOrder(SellResultOrderVo sellResultOrderVo);

    int updateResultOrder(SellResultOrderVo sellResultOrderVo);

    int findCountResultOrder(SellResultOrderVo sellResultOrderVo);

    List<SellResultOrderVo> findAllPagedResultOrder(@Param("sellResultOrderVo") SellResultOrderVo sellResultOrderVo,
                                                    @Param("pageVo") PageVo pageVo);

    SellResultOrderVo findResultOrderById(SellResultOrderVo sellResultOrderVo);

    SellResultOrderVo findResultOrderDetailById(SellResultOrderVo sellResultOrderVo);
}
