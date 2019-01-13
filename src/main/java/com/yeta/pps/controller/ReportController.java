package com.yeta.pps.controller;

import com.yeta.pps.service.ReportService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.*;
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

    //库存报表

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

    //资金报表

    /**
     * 资金报表-查回款-按账户接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "资金报表-查回款-按账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/in/byBankAccount")
    public CommonResponse<CommonResult<ReportFundVo>> findReportFundInByBankAccount(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                    @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                    @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                    @RequestParam(value = "page", required = true) Integer page,
                                                                                    @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportFundInByBankAccount(new ReportFundVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    /**
     * 资金报表-查回款-按职员接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "资金报表-查回款-职员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/in/byUser")
    public CommonResponse<CommonResult<ReportFundVo>> findReportFundInByUser(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                             @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                             @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                             @RequestParam(value = "page", required = true) Integer page,
                                                                             @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportFundInByUser(new ReportFundVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    /**
     * 资金报表-查费用-按分类接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "资金报表-查费用-按分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/out/byType")
    public CommonResponse<CommonResult<ReportFundVo>> findReportFundOutByType(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                              @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                              @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                              @RequestParam(value = "page", required = true) Integer page,
                                                                              @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportFundOutByType(new ReportFundVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    /**
     * 资金报表-查费用-按往来单位接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "资金报表-查费用-按往来单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/out/byTarget")
    public CommonResponse<CommonResult<ReportFundVo>> findReportFundOutByTarget(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                @RequestParam(value = "page", required = true) Integer page,
                                                                                @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportFundOutByTarget(new ReportFundVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    /**
     * 资金报表-查费用-按职员接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "资金报表-查费用-按职员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/out/byUser")
    public CommonResponse<CommonResult<ReportFundVo>> findReportFundOutByUser(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                              @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                              @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                              @RequestParam(value = "page", required = true) Integer page,
                                                                              @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportFundOutByUser(new ReportFundVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    /**
     * 资金报表-查费用-按明细接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "资金报表-查费用-按明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/out/byDetails")
    public CommonResponse<CommonResult<ReportFundVo>> findReportFundOutByDetails(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                 @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                 @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                 @RequestParam(value = "page", required = true) Integer page,
                                                                                 @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportFundOutByDetails(new ReportFundVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    //订单统计

    /**
     * 订单统计-订单分析接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "订单统计-订单分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/order/byOrder")
    public CommonResponse<CommonResult<ReportOrderVo>> findReportOrderByOrder(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                              @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                              @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                              @RequestParam(value = "page", required = true) Integer page,
                                                                              @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportOrderByOrder(new ReportOrderVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    /**
     * 订单统计-商品订货分析接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "订单统计-商品订货分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "goodsId", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsName", value = "商品名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsBarCode", value = "商品条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsTypeId", value = "商品分类编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/order/byGoods")
    public CommonResponse<CommonResult<ReportOrderVo>> findReportOrderByGoods(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                              @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                              @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                              @RequestParam(value = "goodsId", required = false) String goodsId,
                                                                              @RequestParam(value = "goodsName", required = false) String goodsName,
                                                                              @RequestParam(value = "goodsBarCode", required = false) String goodsBarCode,
                                                                              @RequestParam(value = "goodsTypeId", required = false) Integer goodsTypeId,
                                                                              @RequestParam(value = "page", required = true) Integer page,
                                                                              @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportOrderByGoods(new ReportOrderVo(storeId, startTime, endTime, goodsId, goodsName, goodsBarCode, goodsTypeId), new PageVo(page, pageSize));
    }

    /**
     * 订单统计-客户订货分析接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "订单统计-客户订货分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/order/byClient")
    public CommonResponse<CommonResult<ReportOrderVo>> findReportOrderByClient(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                               @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                               @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                               @RequestParam(value = "page", required = true) Integer page,
                                                                               @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportOrderByClient(new ReportOrderVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    //销售报表

    //销售报表-销售日月年报

    /**
     * 销售报表-销售日月年报-销售日报接口
     * @param storeId
     * @param createTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "销售报表-销售日月年报-销售日报")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "createTime", value = "单据日期", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/sell/byDay")
    public CommonResponse<CommonResult<ReportSellVo>> findReportSellByDay(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                          @RequestParam(value = "createTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date createTime,
                                                                          @RequestParam(value = "page", required = true) Integer page,
                                                                          @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportSellByDay(new ReportSellVo(storeId, createTime), new PageVo(page, pageSize));
    }

    /**
     * 销售报表-销售日月年报-销售月报接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "销售报表-销售日月年报-销售月报")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/sell/byMonth")
    public CommonResponse<CommonResult<ReportSellVo>> findReportSellByMonth(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                            @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                            @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                            @RequestParam(value = "page", required = true) Integer page,
                                                                            @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportSellByMonth(new ReportSellVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    /**
     * 销售报表-销售日月年报-销售年报接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "销售报表-销售日月年报-销售年报")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/sell/byYear")
    public CommonResponse<CommonResult<ReportSellVo>> findReportSellByYear(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                           @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM") Date startTime,
                                                                           @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM") Date endTime,
                                                                           @RequestParam(value = "page", required = true) Integer page,
                                                                           @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportSellByYear(new ReportSellVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    //销售报表-商品销售分析

    /**
     * 销售报表-商品销售分析-按商品接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "销售报表-商品销售分析-按商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "goodsId", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsName", value = "商品名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsBarCode", value = "商品条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsTypeId", value = "商品分类编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/sell/byGoods")
    public CommonResponse<CommonResult<ReportSellVo>> findReportSellByGoods(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                            @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                            @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                            @RequestParam(value = "goodsId", required = false) String goodsId,
                                                                            @RequestParam(value = "goodsName", required = false) String goodsName,
                                                                            @RequestParam(value = "goodsBarCode", required = false) String goodsBarCode,
                                                                            @RequestParam(value = "goodsTypeId", required = false) Integer goodsTypeId,
                                                                            @RequestParam(value = "page", required = true) Integer page,
                                                                            @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportSellByGoods(new ReportSellVo(storeId, startTime, endTime, goodsId, goodsName, goodsBarCode, goodsTypeId), new PageVo(page, pageSize));
    }

    //销售报表-客户销售分析

    /**
     * 销售报表-客户销售分析-按客户接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "销售报表-客户销售分析-按客户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/sell/byClient")
    public CommonResponse<CommonResult<ReportSellVo>> findReportSellByClient(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                             @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                             @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                             @RequestParam(value = "page", required = true) Integer page,
                                                                             @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportSellByClient(new ReportSellVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    //销售报表-业绩统计

    /**
     * 销售报表-业绩统计-按职员接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "销售报表-业绩统计-按职员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/sell/byUser")
    public CommonResponse<CommonResult<ReportSellVo>> findReportSellByUser(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                           @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                           @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                           @RequestParam(value = "page", required = true) Integer page,
                                                                           @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportSellByUser(new ReportSellVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    /**
     * 销售报表-业绩统计-按仓库接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "销售报表-业绩统计-仓库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/sell/byWarehouse")
    public CommonResponse<CommonResult<ReportSellVo>> findReportSellByWarehouse(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                @RequestParam(value = "page", required = true) Integer page,
                                                                                @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportSellByWarehouse(new ReportSellVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    /**
     * 销售报表-回款统计-按职员接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "销售报表-回款统计-按职员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/sell/in/byUser")
    public CommonResponse<CommonResult<ReportSellVo>> findReportSellInByUser(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                             @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                             @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                             @RequestParam(value = "page", required = true) Integer page,
                                                                             @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportSellInByUser(new ReportSellVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    //采购报表

    //采购报表-商品采购分析

    /**
     * 采购报表-商品采购分析-按商品接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param goodsId
     * @param goodsName
     * @param goodsBarCode
     * @param goodsTypeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "采购报表-商品采购分析-按商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "goodsId", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsName", value = "商品名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsBarCode", value = "商品条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsTypeId", value = "商品分类编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/procurement/byGoods")
    public CommonResponse<CommonResult<ReportProcurementVo>> findReportProcurementByGoods(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                          @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                          @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                          @RequestParam(value = "goodsId", required = false) String goodsId,
                                                                                          @RequestParam(value = "goodsName", required = false) String goodsName,
                                                                                          @RequestParam(value = "goodsBarCode", required = false) String goodsBarCode,
                                                                                          @RequestParam(value = "goodsTypeId", required = false) Integer goodsTypeId,
                                                                                          @RequestParam(value = "page", required = true) Integer page,
                                                                                          @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportProcurementByGoods(new ReportProcurementVo(storeId, startTime, endTime, goodsId, goodsName, goodsBarCode, goodsTypeId), new PageVo(page, pageSize));
    }

    //采购报表-供应商采购分析

    /**
     * 采购报表-供应商采购分析接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "采购报表-供应商采购分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/procurement/bySupplier")
    public CommonResponse<CommonResult<ReportProcurementVo>> findReportProcurementBySupplier(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                             @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                             @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                             @RequestParam(value = "page", required = true) Integer page,
                                                                                             @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportProcurementBySupplier(new ReportProcurementVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    //采购报表-采购订单分析

    /**
     * 采购报表-采购订单分析-按商品接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param goodsId
     * @param goodsName
     * @param goodsBarCode
     * @param goodsTypeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "采购报表-采购订单分析-按商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "goodsId", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsName", value = "商品名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsBarCode", value = "商品条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsTypeId", value = "商品分类编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/procurement/order/byGoods")
    public CommonResponse<CommonResult<ReportProcurementVo>> findReportProcurementOrderByGoods(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                               @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                               @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                               @RequestParam(value = "goodsId", required = false) String goodsId,
                                                                                               @RequestParam(value = "goodsName", required = false) String goodsName,
                                                                                               @RequestParam(value = "goodsBarCode", required = false) String goodsBarCode,
                                                                                               @RequestParam(value = "goodsTypeId", required = false) Integer goodsTypeId,
                                                                                               @RequestParam(value = "page", required = true) Integer page,
                                                                                               @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportProcurementOrderByGoods(new ReportProcurementVo(storeId, startTime, endTime, goodsId, goodsName, goodsBarCode, goodsTypeId), new PageVo(page, pageSize));
    }

    /**
     * 采购报表-采购订单分析-按明细接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param goodsId
     * @param goodsName
     * @param goodsBarCode
     * @param goodsTypeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "采购报表-采购订单分析-按明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "goodsId", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsName", value = "商品名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsBarCode", value = "商品条码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "goodsTypeId", value = "商品分类编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/procurement/order/byDetail")
    public CommonResponse<CommonResult<ReportProcurementVo>> findReportProcurementOrderByDetail(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                                @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                                @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                                @RequestParam(value = "goodsId", required = false) String goodsId,
                                                                                                @RequestParam(value = "goodsName", required = false) String goodsName,
                                                                                                @RequestParam(value = "goodsBarCode", required = false) String goodsBarCode,
                                                                                                @RequestParam(value = "goodsTypeId", required = false) Integer goodsTypeId,
                                                                                                @RequestParam(value = "page", required = true) Integer page,
                                                                                                @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportProcurementOrderByDetail(new ReportProcurementVo(storeId, startTime, endTime, goodsId, goodsName, goodsBarCode, goodsTypeId), new PageVo(page, pageSize));
    }

    //经营中心

    //经营中心-销售经营分析

    /**
     * 经营中心-销售经营分析接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "经营中心-销售经营分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/manage/bySell")
    public CommonResponse<CommonResult<ReportManageVo>> findReportManageBySell(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                               @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                               @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                               @RequestParam(value = "page", required = true) Integer page,
                                                                               @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportManageBySell(new ReportManageVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    //经营中心-资金经营分析

    /**
     * 经营中心-资金经营分析接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "经营中心-资金经营分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/manage/byFund")
    public CommonResponse<CommonResult<ReportManageVo>> findReportManageByFund(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                               @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                               @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                               @RequestParam(value = "page", required = true) Integer page,
                                                                               @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportManageByFund(new ReportManageVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    //经营中心-库存经营分析

    /**
     * 经营中心-库存经营分析接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "经营中心-库存经营分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/manage/byInventory")
    public CommonResponse<CommonResult<ReportManageVo>> findReportManageByInventory(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                    @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                    @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                    @RequestParam(value = "page", required = true) Integer page,
                                                                                    @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportManageByInventory(new ReportManageVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    //经营中心-利润经营分析

    /**
     * 经营中心-利润经营分析接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "经营中心-利润经营分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/manage/byProfit")
    public CommonResponse<CommonResult<ReportManageVo>> findReportManageByProfit(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                 @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                 @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                 @RequestParam(value = "page", required = true) Integer page,
                                                                                 @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportManageByProfit(new ReportManageVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    //经营中心-往来经营分析

    /**
     * 经营中心-往来经营分析接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "经营中心-往来经营分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/manage/byTarget")
    public CommonResponse<CommonResult<ReportManageVo>> findReportManageByTarget(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                 @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                 @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                 @RequestParam(value = "page", required = true) Integer page,
                                                                                 @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return reportService.findReportManageByTarget(new ReportManageVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    //经营中心-老板中心

    /**
     * 经营中心-老板中心接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @return
     */
    @ApiOperation(value = "经营中心-老板中心")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
    })
    @GetMapping(value = "/manage/byBoss")
    public CommonResponse<CommonResult<ReportManageVo>> findReportManageByBoss(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                               @RequestParam(value = "startTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                               @RequestParam(value = "endTime", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) {
        return reportService.findReportManageByBoss(new ReportManageVo(storeId, startTime, endTime));
    }

}
