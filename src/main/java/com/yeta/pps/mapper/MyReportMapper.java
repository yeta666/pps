package com.yeta.pps.mapper;

import com.yeta.pps.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YETA
 * @date 2019/01/07/14:38
 */
public interface MyReportMapper {

    //库存报表

    int findCountGoods(ReportInventoryVo reportInventoryVo);

    List<ReportInventoryVo> findPagedGoods(@Param(value = "vo") ReportInventoryVo reportInventoryVo,
                                           @Param(value = "pageVo") PageVo pageVo);

    int findCountGoodsWarehouse(ReportInventoryVo reportInventoryVo);

    List<ReportInventoryVo> findPagedGoodsWarehouse(@Param(value = "vo") ReportInventoryVo reportInventoryVo,
                                                    @Param(value = "pageVo") PageVo pageVo);

    ReportInventoryVo findProcurementIn(ReportInventoryVo reportInventoryVo);

    ReportInventoryVo findOtherIn(ReportInventoryVo reportInventoryVo);

    ReportInventoryVo findSellOut(ReportInventoryVo reportInventoryVo);

    ReportInventoryVo findOtherOut(ReportInventoryVo reportInventoryVo);

    StorageCheckOrderVo findBeforeOrEnding(StorageCheckOrderVo storageCheckOrderVo);

    ReportInventoryVo findAnalysis(ReportInventoryVo reportInventoryVo);

    int findCountDetails(ReportInventoryVo reportInventoryVo);

    List<StorageCheckOrderVo> findPagedDetails(@Param(value = "vo") ReportInventoryVo reportInventoryVo,
                                               @Param(value = "pageVo") PageVo pageVo);

    //资金报表

    int findCountFundInByBankAccount(ReportFundVo reportFundVo);

    List<ReportFundVo> findPagedFundInByBankAccount(@Param(value = "vo") ReportFundVo reportFundVo,
                                                    @Param(value = "pageVo")PageVo pageVo);

    List<Integer> findOrderQuantity(ReportFundVo reportFundVo);

    List<FundCheckOrderVo> findAllFundIn(ReportFundVo reportFundVo);

    int findCountFundInByUser(ReportFundVo reportFundVo);

    List<ReportFundVo> findPagedFundInByUser(@Param(value = "vo") ReportFundVo reportFundVo,
                                             @Param(value = "pageVo")PageVo pageVo);

    int findCountFundOutByType(ReportFundVo reportFundVo);

    List<ReportFundVo> findPagedFundOutByType(@Param(value = "vo") ReportFundVo reportFundVo,
                                              @Param(value = "pageVo")PageVo pageVo);

    int findCountFundOutByTarget(ReportFundVo reportFundVo);

    List<ReportFundVo> findPagedFundOutByTarget(@Param(value = "vo") ReportFundVo reportFundVo,
                                                @Param(value = "pageVo")PageVo pageVo);

    Double findDiscountMoney(ReportFundVo reportFundVo);

    int findCountFundOutByUser(ReportFundVo reportFundVo);

    List<ReportFundVo> findPagedFundOutByUser(@Param(value = "vo") ReportFundVo reportFundVo,
                                              @Param(value = "pageVo")PageVo pageVo);

    int findCountFundOutByDetails(ReportFundVo reportFundVo);

    List<FundCheckOrderVo> findPagedFundOutByDetails(@Param(value = "vo") ReportFundVo reportFundVo,
                                                     @Param(value = "pageVo")PageVo pageVo);
}
