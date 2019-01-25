package com.yeta.pps.mapper;

import com.yeta.pps.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author YETA
 * @date 2018/12/11/15:59
 */
public interface MyFundMapper {

    //按单收款、按单付款

    int findCountNotClearedSellApplyOrder(SellApplyOrderVo sellApplyOrderVo);

    List<SellApplyOrderVo> findPagedNotClearedSellApplyOrder(@Param(value = "vo") SellApplyOrderVo sellApplyOrderVo,
                                                             @Param(value = "pageVo") PageVo pageVo);

    int findCountNotClearedProcurementApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    List<ProcurementApplyOrderVo> findPagedNotClearedProcurementApplyOrder(@Param(value = "vo") ProcurementApplyOrderVo procurementApplyOrderVo,
                                                                           @Param(value = "pageVo") PageVo pageVo);

    //收/付款单、预收/付款单

    int addFundOrder(FundOrderVo fundOrderVo);

    int redDashed(FundOrderVo fundOrderVo);

    int findCountFundOrder(FundOrderVo fundOrderVo);

    List<FundOrderVo> findAllPagedFundOrder(@Param(value = "fundOrderVo") FundOrderVo fundOrderVo,
                                            @Param(value = "pageVo") PageVo pageVo);

    List<FundOrderVo> findAllFundOrder(FundOrderVo fundOrderVo);

    List<FundOrderVo> findFundOrder(FundOrderVo fundOrderVo);

    List<String> findFundOrderPrimaryKey(FundOrderVo fundOrderVo);

    //资金对账

    int addFundCheckOrder(FundCheckOrderVo fundCheckOrderVo);

    int redDashedFundCheckOrder(FundCheckOrderVo fundCheckOrderVo);

    int findCountFundCheckOrderBalanceMoney(FundCheckOrderVo fundCheckOrderVo);

    List<BankAccountVo> findPagedFundCheckOrderBalanceMoney(@Param(value = "vo") FundCheckOrderVo fundCheckOrderVo,
                                                            @Param(value = "pageVo") PageVo pageVo);

    int findCountFundCheckOrder(FundCheckOrderVo fundCheckOrderVo);

    List<FundCheckOrderVo> findAllPagedFundCheckOrder(@Param(value = "vo") FundCheckOrderVo fundCheckOrderVo,
                                                      @Param(value = "pageVo") PageVo pageVo);

    List<FundCheckOrderVo> findFundCheckOrderByOrderId(FundCheckOrderVo fundCheckOrderVo);

    FundCheckOrderVo findLastBalanceMoney(FundCheckOrderVo fundCheckOrderVo);

    FundCheckOrderVo findFirstBalanceMoney(FundCheckOrderVo fundCheckOrderVo);

    List<FundCheckOrderVo> findAllFundCheckOrder(FundCheckOrderVo fundCheckOrderVo);

    //往来对账

    int addFundTargetCheckOrder(FundTargetCheckOrderVo fundTargetCheckOrderVo);

    int redDashedFundTargetCheckOrder(FundTargetCheckOrderVo fundTargetCheckOrderVo);

    FundTargetCheckOrderVo findLastFundTargetCheckOrder(FundTargetCheckOrderVo fundTargetCheckOrderVo);

    List<FundTargetCheckOrderVo> findFundTargetCheckOrder(FundTargetCheckOrderVo fundTargetCheckOrderVo);

    int findCountFundTargetCheckOrderNeedInByClient(FundTargetCheckOrderVo fundTargetCheckOrderVo);

    List<FundTargetCheckOrderVo> findPagedFundTargetCheckOrderNeedInByClient(@Param(value = "vo") FundTargetCheckOrderVo fundTargetCheckOrderVo,
                                                                             @Param(value = "pageVo") PageVo pageVo);

    int findCountFundTargetCheckOrderNeedInByClientOrder(FundTargetCheckOrderVo fundTargetCheckOrderVo);

    List<FundTargetCheckOrderVo> findPagedFundTargetCheckOrderNeedInByClientOrder(@Param(value = "vo") FundTargetCheckOrderVo fundTargetCheckOrderVo,
                                                                                  @Param(value = "pageVo") PageVo pageVo);

    int findCountFundTargetCheckOrderNeedOutByClient(FundTargetCheckOrderVo fundTargetCheckOrderVo);

    List<FundTargetCheckOrderVo> findPagedFundTargetCheckOrderNeedOutByClient(@Param(value = "vo") FundTargetCheckOrderVo fundTargetCheckOrderVo,
                                                                              @Param(value = "pageVo") PageVo pageVo);

    int findCountFundTargetCheckOrderNeedOutByClientOrder(FundTargetCheckOrderVo fundTargetCheckOrderVo);

    List<FundTargetCheckOrderVo> findPagedFundTargetCheckOrderNeedOutByClientOrder(@Param(value = "vo") FundTargetCheckOrderVo fundTargetCheckOrderVo,
                                                                                   @Param(value = "pageVo") PageVo pageVo);

    int findCountFundTargetCheckOrderNeedInByUser(FundTargetCheckOrderVo fundTargetCheckOrderVo);

    List<FundTargetCheckOrderVo> findPagedFundTargetCheckOrderNeedInByUser(@Param(value = "vo") FundTargetCheckOrderVo fundTargetCheckOrderVo,
                                                                           @Param(value = "pageVo") PageVo pageVo);

    int findCountFundTargetCheckOrderNeedOutByUser(FundTargetCheckOrderVo fundTargetCheckOrderVo);

    List<FundTargetCheckOrderVo> findPagedFundTargetCheckOrderNeedOutByUser(@Param(value = "vo") FundTargetCheckOrderVo fundTargetCheckOrderVo,
                                                                            @Param(value = "pageVo") PageVo pageVo);

    //其他收入单/费用单

    int addFundResultOrder(FundResultOrderVo fundResultOrderVo);

    int redDashedFundResultOrder(FundResultOrderVo fundResultOrderVo);

    int findCountFundResultOrder(FundResultOrderVo fundResultOrderVo);

    List<FundCheckOrderVo> findAllPagedFundResultOrder(@Param(value = "vo") FundResultOrderVo fundResultOrderVo,
                                                       @Param(value = "pageVo") PageVo pageVo);

    List<FundResultOrderVo> findAllFundResultOrder(FundResultOrderVo fundResultOrderVo);

    List<FundResultOrderVo> findFundResultOrder(FundResultOrderVo fundResultOrderVo);

    List<String> findFundResultOrderPrimaryKey(FundResultOrderVo fundResultOrderVo);


}
