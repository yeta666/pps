package com.yeta.pps.controller;

import com.yeta.pps.po.Client;
import com.yeta.pps.service.StorageService;
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
    @ApiOperation(value = "新增收/发货单接口", notes = "通过type判断，其中applyOrderId代表对应采购申请订单的单据编号，procurementApplyOrderVo或者sellApplyOrderVo就是该对象，采购只传第一个，销售只传第二个，该对象中的details代表具体的商品规格，其中id, type, goodsSkuId, changeQuantity必填")
    @ApiImplicitParam(name = "storageOrderVo", value = "storeId, procurementApplyOrderVo, sellApplyOrderVo, type(1：采购订单收货单，2：退换货申请收货单，3：销售订单发货单，4：退换货申请发货单), applyOrderId, quantity(不管收/发货都传>0的过来), userId必填, logistics_company(物流公司), waybill_number(运单号), remark选填", required = true, paramType = "body", dataType = "StorageOrderVo")
    @PostMapping(value = "/storage")
    public CommonResponse addStorageOrder(@RequestBody @Valid StorageOrderVo storageOrderVo) {
        return storageService.addStorageOrder(storageOrderVo);
    }

    /**
     * 红冲收/发货单接口
     * @param storeId
     * @param id
     * @param userId
     * @param remark
     * @return
     */
    @ApiOperation(value = "红冲收/发货单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "单据编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "String")
    })
    @PostMapping(value = "/storage/redDashed")
    public CommonResponse redDashed(@RequestParam(value = "storeId") Integer storeId,
                                    @RequestParam(value = "id") String id,
                                    @RequestParam(value = "userId") String userId,
                                    @RequestParam(value = "remark", required = false) String remark) {
        return storageService.redDashed(new StorageOrderVo(storeId, id, userId, remark));
    }

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
            @ApiImplicitParam(name = "id", value = "收/发货单编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "clientName", value = "客户名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "membershipNumber", value = "会员卡号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/storage/{type}")
    public CommonResponse<CommonResult<List<StorageOrderVo>>> findAllStorageOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                  @PathVariable(value = "type") Byte type,
                                                                                  @RequestParam(value = "supplierName", required = false) String supplierName,
                                                                                  @RequestParam(value = "clientName", required = false) String clientName,
                                                                                  @RequestParam(value = "phone", required = false) String phone,
                                                                                  @RequestParam(value = "membershipNumber", required = false) String membershipNumber,
                                                                                  @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                  @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                  @RequestParam(value = "id", required = false) String id,
                                                                                  @RequestParam(value = "page") Integer page,
                                                                                  @RequestParam(value = "pageSize") Integer pageSize) {
        ProcurementApplyOrderVo procurementApplyOrderVo = new ProcurementApplyOrderVo(supplierName, startTime, endTime);
        Client client = new Client(clientName, phone, membershipNumber);
        SellApplyOrderVo sellApplyOrderVo = new SellApplyOrderVo(startTime, endTime, client);
        return storageService.findAllStorageOrder(new StorageOrderVo(storeId, id, type, procurementApplyOrderVo, sellApplyOrderVo), new PageVo(page, pageSize));
    }

    //其他入/出库单、报溢/损单、成本调价单、库存盘点单

    /**
     * 新增其他入/出库单、报溢/损单、成本调价单、库存盘点单接口
     * @param storageResultOrderVo
     * @return
     */
    @ApiOperation(value = "新增其他入/出库单、报溢/损单、成本调价单、库存盘点单")
    @ApiImplicitParam(name = "storageResultOrderVo", value = "参数格式看【仓库存储流程.txt】", required = true, paramType = "body", dataType = "StorageResultOrderVo"
    )
    @PostMapping(value = "/storage/result")
    public CommonResponse addStorageResultOrder(@RequestBody @Valid StorageResultOrderVo storageResultOrderVo) {
        return storageService.addStorageResultOrder(storageResultOrderVo);
    }

    /**
     * 红冲其他入/出库单、报溢/损单、成本调价单接口
     * @param storeId
     * @param id
     * @param userId
     * @param remark
     * @return
     */
    @ApiOperation(value = "红冲其他入/出库单、报溢/损单、成本调价单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "单据编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "String")
    })
    @PostMapping(value = "/storage/result/redDashed")
    public CommonResponse redDashedStorageResultOrder(@RequestParam(value = "storeId") Integer storeId,
                                                      @RequestParam(value = "id") String id,
                                                      @RequestParam(value = "userId") String userId,
                                                      @RequestParam(value = "remark", required = false) String remark) {
        return storageService.redDashedStorageResultOrder(new StorageResultOrderVo(storeId, id, userId, remark));
    }

    /**
     * 根据条件查询其他入/出库单、报溢/损单、成本调价单、库存盘点单接口
     * @param storeId
     * @param id
     * @param type
     * @param targetName
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据条件查询其他入/出库单、报溢/损单、成本调价单、库存盘点单", notes = "分页、筛选查询，其中targetName为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "单据编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "单据类型，1：其他入库单，2：其他出库单，3：报溢单，4：报损单，5：成本调价单，6：库存盘点单", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "targetName", value = "往来单位", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/storage/result")
    public CommonResponse<CommonResult<List<StorageResultOrderVo>>> findAllStorageResultOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                              @RequestParam(value = "id", required = false) String id,
                                                                                              @RequestParam(value = "type") Byte type,
                                                                                              @RequestParam(value = "targetName", required = false) String targetName,
                                                                                              @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                              @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                              @RequestParam(value = "page") Integer page,
                                                                                              @RequestParam(value = "pageSize") Integer pageSize) {
        return storageService.findAllStorageResultOrder(new StorageResultOrderVo(storeId, id, type, startTime, endTime, targetName), new PageVo(page, pageSize));
    }

    /**
     * 根据单据编号查询其他入/出库单、报溢/损单、成本调价单、库存盘点单详情接口
     * @param storeId
     * @param id
     * @return
     */
    @ApiOperation(value = "根据单据编号查询其他入/出库单、报溢/损单、成本调价单、库存盘点单详情", notes = "主要是查关联的商品和商品规格信息，用于点击其他入/出库单时")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "单据编号", required = true, paramType = "path", dataType = "String")
    })
    @GetMapping(value = "/storage/result/detail/{id}")
    public CommonResponse<StorageResultOrderVo> findStorageResultOrderDetailById(@RequestParam(value = "storeId") Integer storeId,
                                                                                 @PathVariable(value = "id") String id) {
        return storageService.findStorageResultOrderDetailById(new StorageResultOrderVo(storeId, id));
    }

    //查库存

    /**
     * 查当前库存接口
     * @param id
     * @param name
     * @param typeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查当前库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "商品名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeId", value = "分类编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/storage/inventory")
    public CommonResponse<CommonResult<List<GoodsWarehouseSkuVo>>> findCurrentInventory(@RequestParam(value = "storeId") Integer storeId,
                                                                                        @RequestParam(value = "id", required = false) String id,
                                                                                        @RequestParam(value = "name", required = false) String name,
                                                                                        @RequestParam(value = "typeId", required = false) Integer typeId,
                                                                                        @RequestParam(value = "page") Integer page,
                                                                                        @RequestParam(value = "pageSize") Integer pageSize) {
        return storageService.findCurrentInventory(new GoodsWarehouseSkuVo(storeId, id, name, typeId), new PageVo(page, pageSize));
    }

    /**
     * 根据商品货号查分布接口
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据商品货号查分布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "商品货号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/storage/inventory/distribution/{id}")
    public CommonResponse<CommonResult<List<GoodsWarehouseSkuVo>>> findDistributionByGoodsId(@RequestParam(value = "storeId") Integer storeId,
                                                                                             @PathVariable(value = "id") String id,
                                                                                             @RequestParam(value = "page") Integer page,
                                                                                             @RequestParam(value = "pageSize") Integer pageSize) {
        return storageService.findDistributionByGoodsId(new GoodsWarehouseSkuVo(storeId, id), new PageVo(page, pageSize));
    }

    /**
     * 根据商品规格编号查对账接口
     * @param storeId
     * @param goodsSkuId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据商品规格编号查对账", notes = "只做了商品规格的查账，商品的查账不做")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "goodsSkuId", value = "商品规格编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/storage/inventory/check")
    public CommonResponse<CommonResult<List<StorageCheckOrderVo>>> findStorageCheckOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                         @RequestParam(value = "goodsSkuId") Integer goodsSkuId,
                                                                                         @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                         @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                         @RequestParam(value = "page") Integer page,
                                                                                         @RequestParam(value = "pageSize") Integer pageSize) {
        return storageService.findStorageCheckOrder(new StorageCheckOrderVo(storeId, startTime, endTime, goodsSkuId), new PageVo(page, pageSize));
    }

    /**
     * 根据商品货号查属性接口
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据商品货号查属性")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "商品货号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/storage/inventory/sku/{id}")
    public CommonResponse<CommonResult<List<GoodsWarehouseSkuVo>>> findSkuByGoodsId(@RequestParam(value = "storeId") Integer storeId,
                                                                                    @PathVariable(value = "id") String id,
                                                                                    @RequestParam(value = "page") Integer page,
                                                                                    @RequestParam(value = "pageSize") Integer pageSize) {
        return storageService.findSkuByGoodsId(new GoodsWarehouseSkuVo(storeId, id), new PageVo(page, pageSize));
    }

    /**
     * 按属性查库存接口
     * @param id
     * @param name
     * @param typeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "按属性查库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "商品名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeId", value = "分类编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/storage/inventory/sku")
    public CommonResponse<CommonResult<List<GoodsWarehouseSkuVo>>> findBySku(@RequestParam(value = "storeId") Integer storeId,
                                                                             @RequestParam(value = "id", required = false) String id,
                                                                             @RequestParam(value = "name", required = false) String name,
                                                                             @RequestParam(value = "typeId", required = false) Integer typeId,
                                                                             @RequestParam(value = "page") Integer page,
                                                                             @RequestParam(value = "pageSize") Integer pageSize) {
        return storageService.findBySku(new GoodsWarehouseSkuVo(storeId, id, name, typeId), new PageVo(page, pageSize));
    }

    /**
     * 按仓库查库存接口
     * @param id
     * @param name
     * @param typeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "按仓库查库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "商品货号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "商品名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "typeId", value = "分类编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "warehouseId", value = "仓库编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "flag", value = "按仓库查库存传0，库存预警设置传1，库存预警查询传2，缺货查询传3，库存期初设置传4", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/storage/inventory/warehouse")
    public CommonResponse<CommonResult<List<GoodsWarehouseSkuVo>>> findByWarehouse(@RequestParam(value = "storeId") Integer storeId,
                                                                                   @RequestParam(value = "id", required = false) String id,
                                                                                   @RequestParam(value = "name", required = false) String name,
                                                                                   @RequestParam(value = "typeId", required = false) Integer typeId,
                                                                                   @RequestParam(value = "warehouseId", required = false) Integer warehouseId,
                                                                                   @RequestParam(value = "flag") Byte flag,
                                                                                   @RequestParam(value = "page") Integer page,
                                                                                   @RequestParam(value = "pageSize") Integer pageSize) {
        return storageService.findByWarehouse(new GoodsWarehouseSkuVo(storeId, id, name, typeId, warehouseId, flag), new PageVo(page, pageSize));
    }

    //库存预警

    /**
     * 库存预警设置接口
     * @param warehouseGoodsSkuVo
     * @return
     */
    @ApiOperation(value = "库存预警设置", notes = "传null表示取消设置")
    @ApiImplicitParam(name = "warehouseGoodsSkuVo", value = "storeId, warehouseId, goodsSkuId, inventoryUpperLimit, inventoryLowLimit, userId必填", required = true, paramType = "body", dataType = "WarehouseGoodsSkuVo")
    @PutMapping(value = "/storage/inventory/warning")
    public CommonResponse updateLimitInventory(@RequestBody @Valid WarehouseGoodsSkuVo warehouseGoodsSkuVo) {
        return storageService.updateLimitInventory(warehouseGoodsSkuVo);
    }

    //库存期初

    /**
     * 库存期初设置接口
     * @param warehouseGoodsSkuVo
     * @return
     */
    @ApiOperation(value = "库存期初设置")
    @ApiImplicitParam(name = "warehouseGoodsSkuVo", value = "storeId, warehouseId, goodsSkuId, openingQuantity, openingMoney, openingTotalMoney, userId必填", required = true, paramType = "body", dataType = "WarehouseGoodsSkuVo")
    @PostMapping(value = "/storage/inventory/opening")
    public CommonResponse addWarehouseGoodsSku(@RequestBody @Valid WarehouseGoodsSkuVo warehouseGoodsSkuVo) {
        return storageService.addWarehouseGoodsSku(warehouseGoodsSkuVo);
    }
}
