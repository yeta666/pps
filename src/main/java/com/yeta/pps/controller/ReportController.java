package com.yeta.pps.controller;

import com.yeta.pps.service.ReportService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.ReportInventoryVo;
import com.yeta.pps.vo.StorageCheckOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 报表相关接口
 * @author YETA
 * @date 2019/01/07/16:39
 */
@Api(value = "报表相关接口")
@RestController
@RequestMapping(value = "/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 库存报表-进销存分析-按商品接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param id
     * @param name
     * @param barCode
     * @param typeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "库存报表-进销存分析-按商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "id", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "商品名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "barCode", value = "条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeId", value = "商品分类编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/inventory/byGoods")
    public CommonResponse<CommonResult<ReportInventoryVo>> findReportInventoryByGoods(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                      @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                      @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                      @RequestParam(value = "id", required = false) String id,
                                                                                      @RequestParam(value = "name", required = false) String name,
                                                                                      @RequestParam(value = "barCode", required = false) String barCode,
                                                                                      @RequestParam(value = "typeId", required = false) Integer typeId,
                                                                                      @RequestParam(value = "page", required = true) Integer page,
                                                                                      @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportInventoryByGoods(new ReportInventoryVo(storeId, startTime, endTime, id, name, barCode, typeId), new PageVo(page, pageSize));
    }

    /**
     * 库存报表-进销存分析-按商品-仓库接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param id
     * @param barCode
     * @param typeId
     * @param warehouseId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "库存报表-进销存分析-按商品-仓库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "id", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "商品名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "barCode", value = "条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeId", value = "商品分类编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "warehouseId", value = "仓库编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/inventory/byGoodsWarehouse")
    public CommonResponse<CommonResult<ReportInventoryVo>> findReportInventoryByGoodsWarehouse(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                               @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                               @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                               @RequestParam(value = "id", required = false) String id,
                                                                                               @RequestParam(value = "name", required = false) String name,
                                                                                               @RequestParam(value = "barCode", required = false) String barCode,
                                                                                               @RequestParam(value = "typeId", required = false) Integer typeId,
                                                                                               @RequestParam(value = "warehouseId", required = false) Integer warehouseId,
                                                                                               @RequestParam(value = "page", required = true) Integer page,
                                                                                               @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportInventoryByGoodsWarehouse(new ReportInventoryVo(storeId, startTime, endTime, id, name, barCode, typeId, warehouseId), new PageVo(page, pageSize));
    }

    /**
     * 库存报表-进销存分析-其他出入库分析/报损报溢分析接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param id
     * @param name
     * @param barCode
     * @param typeId
     * @param flag
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "库存报表-进销存分析-其他出入库分析/报损报溢分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "id", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "商品名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "barCode", value = "条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeId", value = "商品分类编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "flag", value = "1：其他出入库分析，2：报损报溢分析", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/inventory/analysis/{flag}")
    public CommonResponse<CommonResult<ReportInventoryVo>> findReportInventoryAnalysis(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                       @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                       @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                       @RequestParam(value = "id", required = false) String id,
                                                                                       @RequestParam(value = "name", required = false) String name,
                                                                                       @RequestParam(value = "barCode", required = false) String barCode,
                                                                                       @RequestParam(value = "typeId", required = false) Integer typeId,
                                                                                       @PathVariable(value = "flag", required = true) Integer flag,
                                                                                       @RequestParam(value = "page", required = true) Integer page,
                                                                                       @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        ReportInventoryVo vo = new ReportInventoryVo(storeId, startTime, endTime, id, name, barCode, typeId);
        vo.setFlag(flag);
        return reportService.findReportInventoryAnalysis(vo, new PageVo(page, pageSize));
    }

    /**
     * 库存报表-出入库明细接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param id
     * @param name
     * @param barCode
     * @param typeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "库存报表-出入库明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "id", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "商品名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "barCode", value = "条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeId", value = "商品分类编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/inventory/details")
    public CommonResponse<CommonResult<StorageCheckOrderVo>> findReportInventoryDetails(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                        @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                        @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                        @RequestParam(value = "id", required = false) String id,
                                                                                        @RequestParam(value = "name", required = false) String name,
                                                                                        @RequestParam(value = "barCode", required = false) String barCode,
                                                                                        @RequestParam(value = "typeId", required = false) Integer typeId,
                                                                                        @RequestParam(value = "page", required = true) Integer page,
                                                                                        @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportInventoryDetails(new ReportInventoryVo(storeId, startTime, endTime, id, name, barCode, typeId), new PageVo(page, pageSize));
    }

}
