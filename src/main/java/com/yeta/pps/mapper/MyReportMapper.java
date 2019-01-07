package com.yeta.pps.mapper;

import com.yeta.pps.vo.ReportInventoryVo;
import com.yeta.pps.vo.StorageCheckOrderVo;

/**
 * @author YETA
 * @date 2019/01/07/14:38
 */
public interface MyReportMapper {

    ReportInventoryVo findProcurementIn(ReportInventoryVo reportInventoryVo);

    ReportInventoryVo findOtherIn(ReportInventoryVo reportInventoryVo);

    ReportInventoryVo findSellOut(ReportInventoryVo reportInventoryVo);

    ReportInventoryVo findOtherOut(ReportInventoryVo reportInventoryVo);

    StorageCheckOrderVo findBeforeOrEnding(StorageCheckOrderVo storageCheckOrderVo);
}
