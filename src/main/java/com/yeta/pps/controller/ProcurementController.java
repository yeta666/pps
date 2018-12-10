package com.yeta.pps.controller;

import com.yeta.pps.service.ProcurementService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.ProcurementApplyOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 采购相关接口
 * @author YETA
 * @date 2018/12/10/19:26
 */
@Api(value = "采购相关接口")
@RestController
public class ProcurementController {

    @Autowired
    private ProcurementService procurementService;

    /**
     * 新增采购申请订单接口
     * @param procurementApplyOrderVo
     * @return
     */
    @ApiOperation(value = "新增采购申请订单", notes = "包括采购订单、采购退货申请、采购换货申请，用type判断。" +
            "details中的ProcurementApplyOrderGoodsSkuVo对象表示订单关联的商品规格，其中type(1：入库，0：出库), goodsSkuId, quantity, money, discountMoney必填")
    @ApiImplicitParam(name = "procurementApplyOrderVo",
            value = "storeId, details, type(1：采购订单，2：采购退货申请，3,：采购换货申请), createTime, supplierId, 采购订单填inWarehouseId, inTotalQuantity, 采购退货申请填outWarehouseId, outTotalQuantity, 采购换货申请前面四个都要填, totalMoney, totalDiscountMoney, userId必填",
            required = true,
            paramType = "body",
            dataType = "ProcurementApplyOrderVo"
    )
    @PostMapping(value = "/orders/apply")
    public CommonResponse addApplyOrder(@RequestBody @Valid ProcurementApplyOrderVo procurementApplyOrderVo) {
        return procurementService.addApplyOrder(procurementApplyOrderVo);
    }

    /**
     * 删除采购申请订单接口
     * @param storeId
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除采购申请订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "采购申请订单编号，英文逗号隔开", required = true, paramType = "query", dataType = "String"),
    })
    @DeleteMapping(value = "/orders/apply")
    public CommonResponse deleteApplyOrder(@RequestParam(value = "storeId") Integer storeId,
                                           @RequestParam(value = "ids") String ids) {
        List<ProcurementApplyOrderVo> procurementApplyOrderVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            procurementApplyOrderVos.add(new ProcurementApplyOrderVo(storeId, id));
        });
        return procurementService.deleteApplyOrder(procurementApplyOrderVos);
    }

    /**
     * 删除采购申请订单接口
     * @param procurementApplyOrderVo
     * @return
     */
    @ApiOperation(value = "修改采购申请订单", notes = "包括采购订单、采购退货申请、采购换货申请，用type判断。" +
            "details中的ProcurementApplyOrderGoodsSkuVo对象表示订单关联的商品规格，其中type(1：入库，0：出库), goodsSkuId, quantity, money, discountMoney必填")
    @ApiImplicitParam(name = "procurementApplyOrderVo",
            value = "storeId, id, details, createTime, supplierId, 采购订单填inWarehouseId, inTotalQuantity, 采购退货申请填outWarehouseId, outTotalQuantity, 采购换货申请前面四个都要填, totalMoney, totalDiscountMoney, userId必填",
            required = true,
            paramType = "body",
            dataType = "ProcurementApplyOrderVo"
    )
    @PutMapping(value = "/orders/apply")
    public CommonResponse updateApplyOrder(@RequestBody @Valid ProcurementApplyOrderVo procurementApplyOrderVo) {
        return procurementService.updateApplyOrder(procurementApplyOrderVo);
    }

    /**
     * 修改采购申请订单单据状态
     * @param storeId
     * @param id
     * @param type
     * @return
     */
    @ApiOperation(value = "修改采购申请订单单据状态", notes = "点击收货或发货按钮")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "采购申请订单编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "1：收货，2：发货", required = true, paramType = "query", dataType = "int")
    })
    @PutMapping(value = "/orders/apply/status/order")
    public CommonResponse updateApplyOrderOrderStatus(@RequestParam(value = "storeId") Integer storeId,
                                                      @RequestParam(value = "id") String id,
                                                      @RequestParam(value = "type") Byte type) {
        return procurementService.updateApplyOrderOrderStatus(new ProcurementApplyOrderVo(storeId, id, type));
    }

    /**
     * 修改采购申请订单结算状态
     * @param storeId
     * @param id
     * @param type
     * @return
     */
    @ApiOperation(value = "修改采购申请订单结算状态", notes = "点击收款或付款按钮")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "采购申请订单编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "1：收款，2：付款", required = true, paramType = "query", dataType = "int")
    })
    @PutMapping(value = "/orders/apply/status/check")
    public CommonResponse updateApplyOrderCheckStatus(@RequestParam(value = "storeId") Integer storeId,
                                                      @RequestParam(value = "id") String id,
                                                      @RequestParam(value = "type") Byte type) {
        return procurementService.updateApplyOrderOrderStatus(new ProcurementApplyOrderVo(storeId, id, type));
    }

    /**
     * 查询所有采购申请订单接口
     * @param storeId
     * @param supplierName
     * @param startTime
     * @param endTime
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询所有采购申请订单", notes = "分页、筛选查询，其中supplierName为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "id", value = "采购申请订单编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/orders/apply")
    public CommonResponse<CommonResult<List<ProcurementApplyOrderVo>>> findAllApplyOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                         @RequestParam(value = "supplierName", required = false) String supplierName,
                                                                                         @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                         @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                         @RequestParam(value = "id", required = false) String id,
                                                                                         @RequestParam(value = "page") Integer page,
                                                                                         @RequestParam(value = "pageSize") Integer pageSize) {
        return procurementService.findAllApplyOrder(new ProcurementApplyOrderVo(storeId, supplierName, startTime, endTime, id), new PageVo(page, pageSize));
    }

    /**
     * 根据单据编号查询单据详情
     * @param storeId
     * @param id
     * @return
     */
    @ApiOperation(value = "根据单据编号查询单据详情", notes = "主要是查关联的商品和商品规格信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "采购申请订单编号", required = true, paramType = "path", dataType = "String")
    })
    @GetMapping(value = "/orders/apply/detail/{id}")
    public CommonResponse<ProcurementApplyOrderVo> findApplyOrderDetailById(@RequestParam(value = "storeId") Integer storeId,
                                                                            @PathVariable(value = "id") String id) {
        return procurementService.findApplyOrderDetailById(new ProcurementApplyOrderVo(storeId, id));
    }

}
