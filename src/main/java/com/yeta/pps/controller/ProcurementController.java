package com.yeta.pps.controller;

import com.yeta.pps.service.ProcurementService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.ProcurementApplyOrderVo;
import com.yeta.pps.vo.ProcurementResultOrderVo;
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
@RequestMapping(value = "/procurement")
public class ProcurementController {

    @Autowired
    private ProcurementService procurementService;

    /**
     * 新增采购申请订单接口
     * @param procurementApplyOrderVo
     * @return
     */
    @ApiOperation(value = "新增采购申请订单", notes = "包括采购订单、采购退货申请、采购换货申请，用type判断。" +
            "details中的OrderGoodsSkuVo对象表示订单关联的商品规格，其中id(采购退货申请和采购换货申请的出库商品规格需要填上此字段), type(1：入库，0：出库), goodsSkuId, quantity, money, discountMoney必填, remark选填")
    @ApiImplicitParam(name = "procurementApplyOrderVo",
            value = "storeId, details, type(1：采购订单，2：采购退货申请，3,：采购换货申请), supplierId, 采购订单填inWarehouseId, inTotalQuantity, 采购退货申请填outWarehouseId, outTotalQuantity, resultOrderId(来源订单，应该是某一个采购结果订单), 采购换货申请前面五个都要填, totalMoney(采购订单大于0，采购退货申请小于0，采购换货申请入库-出库的价钱), totalDiscountMoney, orderMoney, userId必填",
            required = true,
            paramType = "body",
            dataType = "ProcurementApplyOrderVo"
    )
    @PostMapping(value = "/apply")
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
    @DeleteMapping(value = "/apply")
    public CommonResponse deleteApplyOrder(@RequestParam(value = "storeId") Integer storeId,
                                           @RequestParam(value = "ids") String ids) {
        List<ProcurementApplyOrderVo> procurementApplyOrderVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            procurementApplyOrderVos.add(new ProcurementApplyOrderVo(storeId, id));
        });
        return procurementService.deleteApplyOrder(procurementApplyOrderVos);
    }

    /**
     * 修改采购申请订单接口
     * @param procurementApplyOrderVo
     * @return
     */
    @ApiOperation(value = "修改采购申请订单", notes = "包括采购订单、采购退货申请、采购换货申请，用type判断。" +
            "details中的OrderGoodsSkuVo对象表示订单关联的商品规格，其中type(1：入库，0：出库), goodsSkuId, quantity, money, discountMoney必填, remark选填")
    @ApiImplicitParam(name = "procurementApplyOrderVo",
            value = "storeId, id, details, supplierId, 采购订单填inWarehouseId, inTotalQuantity, 采购退货申请填outWarehouseId, outTotalQuantity, 采购换货申请前面四个都要填, totalMoney(采购订单大于0，采购退货申请小于0，采购换货申请入库-出库的价钱), totalDiscountMoney, orderMoney, userId必填",
            required = true,
            paramType = "body",
            dataType = "ProcurementApplyOrderVo"
    )
    @PutMapping(value = "/apply")
    public CommonResponse updateApplyOrder(@RequestBody ProcurementApplyOrderVo procurementApplyOrderVo) {
        return procurementService.updateApplyOrder(procurementApplyOrderVo);
    }

    /**
     * 修改采购申请订单备注接口
     * @param storeId
     * @param id
     * @param userId
     * @param remark
     * @return
     */
    @ApiOperation(value = "修改采购申请订单备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "采购申请订单单据编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = true, paramType = "query", dataType = "String")
    })
    @PutMapping(value = "/apply/remark")
    public CommonResponse updateApplyOrderRemark(@RequestParam(value = "storeId") Integer storeId,
                                                 @RequestParam(value = "id") String id,
                                                 @RequestParam(value = "userId") String userId,
                                                 @RequestParam(value = "remark") String remark) {
        return procurementService.updateApplyOrderRemark(new ProcurementApplyOrderVo(storeId, id, userId, remark));
    }

    /**
     * 根据type所有采购申请订单接口
     * @param storeId
     * @param supplierName
     * @param startTime
     * @param endTime
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询所有采购申请订单", notes = "分页、筛选查询，其中supplierName为模糊查询，startTime和endTime要么都传，要么都不传，仓库查未收/发货传type和ordersStatus，资金查未收/付款传type和clearStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "id", value = "采购申请订单编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "采购申请订单类型(1：采购订单，2：采购退货申请，3,：采购换货申请)", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/apply")
    public CommonResponse<CommonResult<List<ProcurementApplyOrderVo>>> findAllApplyOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                         @RequestParam(value = "supplierName", required = false) String supplierName,
                                                                                         @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                         @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                         @RequestParam(value = "id", required = false) String id,
                                                                                         @RequestParam(value = "type") Byte type,
                                                                                         @RequestParam(value = "page") Integer page,
                                                                                         @RequestParam(value = "pageSize") Integer pageSize) {
        return procurementService.findAllApplyOrder(new ProcurementApplyOrderVo(storeId, supplierName, startTime, endTime, id, type), new PageVo(page, pageSize));
    }

    /**
     * 根据单据编号查询申请订单详情
     * @param storeId
     * @param id
     * @return
     */
    @ApiOperation(value = "根据单据编号查询申请订单详情", notes = "主要是查关联的商品和商品规格信息，用于点击采购申请单时")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "采购申请订单编号", required = true, paramType = "path", dataType = "String")
    })
    @GetMapping(value = "/apply/detail/{id}")
    public CommonResponse<ProcurementApplyOrderVo> findApplyOrderDetailById(@RequestParam(value = "storeId") Integer storeId,
                                                                            @PathVariable(value = "id") String id) {
        return procurementService.findApplyOrderDetailById(new ProcurementApplyOrderVo(storeId, id));
    }

    //采购结果订单

    /**
     * 红冲采购结果订单接口
     * @param storeId
     * @param id
     * @param userId
     * @param remark
     * @return
     */
    @ApiOperation(value = "红冲采购结果订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "单据编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "String")
    })
    @PostMapping(value = "/result/redDashed")
    public CommonResponse redDashed(@RequestParam(value = "storeId") Integer storeId,
                                    @RequestParam(value = "id") String id,
                                    @RequestParam(value = "userId") String userId,
                                    @RequestParam(value = "remark", required = false) String remark) {
        return procurementService.redDashed(new ProcurementResultOrderVo(storeId, id, userId, remark));
    }

    /**
     * 查询所有采购结果订单接口
     * @param storeId
     * @param supplierName
     * @param startTime
     * @param endTime
     * @param id
     * @param type
     * @param flag
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询所有采购结果订单", notes = "分页、筛选查询，其中supplierName为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "id", value = "采购结果订单编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "flag", value = "标志位，1表示申请退换货的时候查询销售出库单", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/result")
    public CommonResponse<CommonResult<List<ProcurementResultOrderVo>>> findAllResultOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                           @RequestParam(value = "supplierName", required = false) String supplierName,
                                                                                           @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                           @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                           @RequestParam(value = "id", required = false) String id,
                                                                                           @RequestParam(value = "type", required = false) Byte type,
                                                                                           @RequestParam(value = "flag", required = false) Integer flag,
                                                                                           @RequestParam(value = "page") Integer page,
                                                                                           @RequestParam(value = "pageSize") Integer pageSize) {
        ProcurementApplyOrderVo procurementApplyOrderVo = new ProcurementApplyOrderVo(supplierName, startTime, endTime);
        return procurementService.findAllResultOrder(new ProcurementResultOrderVo(storeId, procurementApplyOrderVo, id, type, flag), new PageVo(page, pageSize));
    }

    /**
     * 根据单据编号查询结果订单详情
     * @param storeId
     * @param id
     * @return
     */
    @ApiOperation(value = "根据单据编号查询结果订单详情", notes = "主要是查关联的商品和商品规格信息，用于点击采购结果单时")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "采购结果订单编号", required = true, paramType = "path", dataType = "String")
    })
    @GetMapping(value = "/result/detail/{id}")
    public CommonResponse<ProcurementResultOrderVo> findResultOrderDetailById(@RequestParam(value = "storeId") Integer storeId,
                                                                              @PathVariable(value = "id") String id) {
        return procurementService.findResultOrderDetailById(new ProcurementResultOrderVo(storeId, id));
    }

}
