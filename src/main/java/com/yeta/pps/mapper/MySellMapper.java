package com.yeta.pps.mapper;

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

    List<SellApplyOrderVo> findAllApplyOrder(SellApplyOrderVo saoVo);

    SellApplyOrderVo findApplyOrderById(SellApplyOrderVo sellApplyOrderVo);

    List<SellApplyOrderVo> findAllApplyOrderDetail(SellApplyOrderVo sellApplyOrderVo);

    List<String> findApplyOrderPrimaryKey(SellApplyOrderVo sellApplyOrderVo);

    //销售结果单

    int addResultOrder(SellResultOrderVo sellResultOrderVo);

    int redDashed(SellResultOrderVo sellResultOrderVo);

    int findCountResultOrder(SellResultOrderVo sellResultOrderVo);

    List<SellResultOrderVo> findAllPagedResultOrder(@Param("sellResultOrderVo") SellResultOrderVo sellResultOrderVo,
                                                    @Param("pageVo") PageVo pageVo);

    List<SellResultOrderVo> findAllResultOrder(SellResultOrderVo sellResultOrderVo);

    SellResultOrderVo findResultOrderDetailById(SellResultOrderVo sellResultOrderVo);

    List<String> findResultOrderPrimaryKey(SellResultOrderVo sellResultOrderVo);
}
