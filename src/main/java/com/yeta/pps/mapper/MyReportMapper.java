package com.yeta.pps.mapper;

import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.ReportInventoryVo;
import com.yeta.pps.vo.StorageCheckOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YETA
 * @date 2019/01/07/14:38
 */
public interface MyReportMapper {

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
}
