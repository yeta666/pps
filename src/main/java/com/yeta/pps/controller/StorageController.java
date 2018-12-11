package com.yeta.pps.controller;

import com.yeta.pps.service.StorageService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.ProcurementApplyOrderVo;
import com.yeta.pps.vo.ProcurementResultOrderVo;
import com.yeta.pps.vo.StorageOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 仓库存储相关接口
 * @author YETA
 * @date 2018/12/11/17:11
 */
@Api(value = "仓库存储相关接口")
@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;

    /**
     * 新增收/发货单接口
     * @param storageOrderVo
     * @return
     */
    @ApiOperation(value = "新增收/发货单接口", notes = "通过type判断，其中applyOrderId代表对应采购申请订单的单据编号，procurementApplyOrderVo就是该对象，其中采购订单和采购换货申请单必填inTotalQuantity, inReceivedQuantity, inNotReceivedQuantity，采购退货申请单和采购换货申请单必填outTotalQuantity, outSentQuantity, outNotSentQuantity，该对象中的details代表具体的商品规格，其中goodsSkuId, finishQuantity, notFinishQuantity, money, discountMoney必填")
    @ApiImplicitParam(name = "storageOrderVo", value = "storeId, procurementApplyOrderVo, type(1：采购订单收货单，2：退换货申请收货单，3：销售订单发货单，4：退换货申请发货单), applyOrderId, userId必填", required = true, paramType = "body", dataType = "StorageOrderVo")
    @PostMapping(value = "/orders/storage")
    public CommonResponse addStorageOrder(@RequestBody @Valid StorageOrderVo storageOrderVo) {
        return storageService.addStorageOrder(storageOrderVo);
    }

    //TODO
    //接口测试

    /**
     * 根据type查询所有收/发货单接口
     * @param storeId
     * @param supplierName
     * @param startTime
     * @param endTime
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据type查询所有收/发货单", notes = "分页、筛选查询，其中type必填，supplierName为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "单据类型(1：采购订单收货单，2：退换货申请收货单，3：销售订单发货单，4：退换货申请发货单)", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "采购结果订单编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/storage/{type}")
    public CommonResponse<CommonResult<List<StorageOrderVo>>> findAllStorageOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                  @PathVariable(value = "type") Byte type,
                                                                                  @RequestParam(value = "supplierName", required = false) String supplierName,
                                                                                  @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                  @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                  @RequestParam(value = "id", required = false) String id,
                                                                                  @RequestParam(value = "page") Integer page,
                                                                                  @RequestParam(value = "pageSize") Integer pageSize) {
        ProcurementApplyOrderVo procurementApplyOrderVo = new ProcurementApplyOrderVo(supplierName, startTime, endTime);
        return storageService.findAllStorageOrder(new StorageOrderVo(storeId, id, type, procurementApplyOrderVo), new PageVo(page, pageSize));
    }
}
