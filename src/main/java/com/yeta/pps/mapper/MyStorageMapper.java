package com.yeta.pps.mapper;

import com.yeta.pps.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YETA
 * @date 2018/12/11/15:59
 */
public interface MyStorageMapper {

    //待收货

    int findCountInCGApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    List<ProcurementApplyOrderVo> findPagedInCGApplyOrder(@Param(value = "vo") ProcurementApplyOrderVo procurementApplyOrderVo,
                                                          @Param(value = "pageVo") PageVo pageVo);

    int findCountInTHHApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    List<ProcurementApplyOrderVo> findPagedInTHHApplyOrder(@Param(value = "vo") ProcurementApplyOrderVo procurementApplyOrderVo,
                                                           @Param(value = "pageVo") PageVo pageVo);

    int findCountInDBApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    List<StorageApplyOrderVo> findPagedInDBApplyOrder(@Param(value = "vo") ProcurementApplyOrderVo procurementApplyOrderVo,
                                                      @Param(value = "pageVo") PageVo pageVo);

    //待发货

    int findCountOutCGApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    List<ProcurementApplyOrderVo> findPagedOutCGApplyOrder(@Param(value = "vo") ProcurementApplyOrderVo procurementApplyOrderVo,
                                                           @Param(value = "pageVo") PageVo pageVo);

    int findCountOutTHHApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    List<ProcurementApplyOrderVo> findPagedOutTHHApplyOrder(@Param(value = "vo") ProcurementApplyOrderVo procurementApplyOrderVo,
                                                            @Param(value = "pageVo") PageVo pageVo);

    int findCountOutDBApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo);

    List<StorageApplyOrderVo> findPagedOutDBApplyOrder(@Param(value = "vo") ProcurementApplyOrderVo procurementApplyOrderVo,
                                                       @Param(value = "pageVo") PageVo pageVo);

    //收/发货单

    int addStorageOrder(StorageOrderVo storageOrderVo);

    int redDashed(StorageOrderVo storageOrderVo);

    int findCountStorageOrder(StorageOrderVo storageOrderVo);

    List<StorageOrderVo> findAllPagedStorageOrder(@Param(value = "vo") StorageOrderVo storageOrderVo,
                                                  @Param(value = "pageVo") PageVo pageVo);

    List<StorageOrderVo> findAllStorageOrder(StorageOrderVo storageOrderVo);

    StorageOrderVo findStorageOrderById(StorageOrderVo storageOrderVo);

    List<String> findStorageOrderPrimaryKey(StorageOrderVo storageOrderVo);

    //申请单

    int addStorageApplyOrder(StorageApplyOrderVo storageApplyOrderVo);

    int deleteStorageApplyOrder(StorageApplyOrderVo storageApplyOrderVo);

    int updateStorageApplyOrderOrderStatusAndQuantity(StorageOrderVo storageOrderVo);

    int findCountStorageApplyOrder(StorageApplyOrderVo storageApplyOrderVo);

    List<StorageApplyOrderVo> findPagedStorageApplyOrder(@Param(value = "vo") StorageApplyOrderVo storageApplyOrderVo,
                                                         @Param(value = "pageVo") PageVo pageVo);

    List<StorageApplyOrderVo> findAllStorageApplyOrder(StorageApplyOrderVo storageApplyOrderVo);

    List<StorageApplyOrderVo> findStorageApplyOrderDetail(StorageApplyOrderVo storageApplyOrderVo);

    List<String> findStorageApplyOrderPrimaryKey(StorageApplyOrderVo storageApplyOrderVo);

    //结果单

    int addStorageResultOrder(StorageResultOrderVo storageResultOrderVo);

    int redDashedStorageResultOrder(StorageResultOrderVo storageResultOrderVo);

    int findCountStorageResultOrder(StorageResultOrderVo storageResultOrderVo);

    List<StorageResultOrderVo> findAllPagedStorageResultOrder(@Param(value = "vo") StorageResultOrderVo storageResultOrderVo,
                                                              @Param(value = "pageVo") PageVo pageVo);

    List<StorageResultOrderVo> findAllStorageResultOrder(StorageResultOrderVo storageResultOrderVo);

    List<StorageResultOrderVo> findStorageResultOrderDetail(StorageResultOrderVo storageResultOrderVo);

    List<String> findStorageResultOrderPrimaryKey(StorageResultOrderVo storageResultOrderVo);

    //查库存

    int findCountCurrentInventory(GoodsWarehouseSkuVo goodsWarehouseSkuVo);

    List<GoodsWarehouseSkuVo> findPagedCurrentInventory(@Param(value = "vo") GoodsWarehouseSkuVo goodsWarehouseSkuVo,
                                                        @Param(value = "pageVo") PageVo pageVo);

    int findCountDistributionByGoodsId(GoodsWarehouseSkuVo goodsWarehouseSkuVo);

    List<GoodsWarehouseSkuVo> findPagedDistributionByGoodsId(@Param(value = "vo") GoodsWarehouseSkuVo goodsWarehouseSkuVo,
                                                             @Param(value = "pageVo") PageVo pageVo);

    int findCountOrderByGoodsId(StorageCheckOrderVo storageCheckOrderVo);

    List<StorageCheckOrderVo> findPagedOrderByGoodsId(@Param(value = "vo") StorageCheckOrderVo storageCheckOrderVo,
                                                      @Param(value = "pageVo") PageVo pageVo);

    int findCountSkuByGoodsId(GoodsWarehouseSkuVo goodsWarehouseSkuVo);

    List<GoodsWarehouseSkuVo> findPagedSkuByGoodsId(@Param(value = "vo") GoodsWarehouseSkuVo goodsWarehouseSkuVo,
                                                    @Param(value = "pageVo") PageVo pageVo);

    int findCountBySku(GoodsWarehouseSkuVo goodsWarehouseSkuVo);

    List<GoodsWarehouseSkuVo> findPagedBySku(@Param(value = "vo") GoodsWarehouseSkuVo goodsWarehouseSkuVo,
                                             @Param(value = "pageVo") PageVo pageVo);

    int findCountDistributionByGoodsSkuId(GoodsWarehouseSkuVo goodsWarehouseSkuVo);

    List<GoodsWarehouseSkuVo> findPagedDistributionByGoodsSkuId(@Param(value = "vo") GoodsWarehouseSkuVo goodsWarehouseSkuVo,
                                                                @Param(value = "pageVo") PageVo pageVo);

    int findCountOrderByGoodsSkuId(StorageCheckOrderVo storageCheckOrderVo);

    List<StorageCheckOrderVo> findPagedOrderByGoodsSkuId(@Param(value = "vo") StorageCheckOrderVo storageCheckOrderVo,
                                                         @Param(value = "pageVo") PageVo pageVo);

    int findCountByWarehouse(GoodsWarehouseSkuVo goodsWarehouseSkuVo);

    List<GoodsWarehouseSkuVo> findPagedByWarehouse(@Param(value = "vo") GoodsWarehouseSkuVo goodsWarehouseSkuVo,
                                                   @Param(value = "pageVo") PageVo pageVo);

    int findCountOrderByGoodsSkuIdAndWarehouseId(StorageCheckOrderVo storageCheckOrderVo);

    List<StorageCheckOrderVo> findPagedOrderByGoodsSkuIdAndWarehouseId(@Param(value = "vo") StorageCheckOrderVo storageCheckOrderVo,
                                                                       @Param(value = "pageVo") PageVo pageVo);

    //库存对账

    int addStorageCheckOrder(StorageCheckOrderVo storageCheckOrderVo);

    int redDashedInStorageCheckOrder(StorageCheckOrderVo storageCheckOrderVo);

    int redDashedOutStorageCheckOrder(StorageCheckOrderVo storageCheckOrderVo);

    StorageCheckOrderVo findInStorageCheckOrder(StorageCheckOrderVo storageCheckOrderVo);

    StorageCheckOrderVo findOutStorageCheckOrder(StorageCheckOrderVo storageCheckOrderVo);

    StorageCheckOrderVo findLastCheckMoneyByGoodsSkuIdAndWarehouseId(StorageCheckOrderVo storageCheckOrderVo);

    StorageCheckOrderVo findLastCheckMoneyByGoodsSkuId(StorageCheckOrderVo storageCheckOrderVo);

    StorageCheckOrderVo findLastCheckMoneyByGoodsId(StorageCheckOrderVo storageCheckOrderVo);

    List<StorageCheckOrderVo> findAllStorageCheckOrder(StorageCheckOrderVo storageCheckOrderVo);
}
