package com.yeta.pps.mapper;

import com.yeta.pps.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YETA
 * @date 2018/12/11/15:59
 */
public interface MyStorageMapper {

    //收/发货单

    int addStorageOrder(StorageOrderVo storageOrderVo);

    int redDashed(StorageOrderVo storageOrderVo);

    int findCountStorageOrder(StorageOrderVo storageOrderVo);

    List<StorageOrderVo> findAllPagedStorageOrder(@Param(value = "storageOrderVo") StorageOrderVo storageOrderVo,
                                                  @Param(value = "pageVo") PageVo pageVo);

    StorageOrderVo findStorageOrderById(StorageOrderVo storageOrderVo);

    //其他入/出库单

    int addStorageResultOrder(StorageResultOrderVo storageResultOrderVo);

    int redDashedStorageResultOrder(StorageResultOrderVo storageResultOrderVo);

    int findCountStorageResultOrder(StorageResultOrderVo storageResultOrderVo);

    List<StorageResultOrderVo> findAllPagedStorageResultOrder(@Param(value = "vo") StorageResultOrderVo storageResultOrderVo,
                                                              @Param(value = "pageVo") PageVo pageVo);

    StorageResultOrderVo findStorageResultOrderDetailById(StorageResultOrderVo storageResultOrderVo);

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

    int findCountByWarehouse(GoodsWarehouseSkuVo goodsWarehouseSkuVo);

    List<GoodsWarehouseSkuVo> findPagedByWarehouse(@Param(value = "vo") GoodsWarehouseSkuVo goodsWarehouseSkuVo,
                                                   @Param(value = "pageVo") PageVo pageVo);

    //库存对账

    int addStorageCheckOrder(StorageCheckOrderVo storageCheckOrderVo);

    StorageCheckOrderVo findLastCheckMoney(StorageCheckOrderVo storageCheckOrderVo);
}
