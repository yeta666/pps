package com.yeta.pps.mapper;

import com.yeta.pps.po.*;
import com.yeta.pps.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyGoodsMapper {

    //商品标签

    int addLabel(GoodsLabelVo goodsLabelVo);

    int deleteLabel(GoodsLabelVo goodsLabelVo);

    int updateLabel(GoodsLabelVo goodsLabelVo);

    int findCountLabel(GoodsLabelVo goodsLabelVo);

    List<GoodsLabel> findAllPagedLabel(@Param(value = "goodsLabelVo") GoodsLabelVo goodsLabelVo,
                                       @Param(value = "pageVo") PageVo pageVo);

    List<GoodsLabel> findAllLabel(GoodsLabelVo goodsLabelVo);

    GoodsLabel findLabel(GoodsLabelVo goodsLabelVo);

    //商品/商品标签关系

    int addGoodsLabel(GoodsGoodsLabelVo goodsGoodsLabelVo);

    int deleteGoodsLabel(GoodsGoodsLabelVo goodsGoodsLabelVo);

    List<GoodsGoodsLabel> findGoodsLabel(GoodsGoodsLabelVo goodsGoodsLabelVo);


    //商品类型

    int addType(GoodsTypeVo goodsTypeVo);

    int deleteType(GoodsTypeVo goodsTypeVo);

    int updateType(GoodsTypeVo goodsTypeVo);

    int findCountType(GoodsTypeVo goodsTypeVo);

    List<GoodsType> findAllPagedType(@Param(value = "goodsTypeVo") GoodsTypeVo goodsTypeVo,
                                     @Param(value = "pageVo") PageVo pageVo);

    List<GoodsType> findAllType(GoodsTypeVo goodsTypeVo);

    GoodsType findType(GoodsTypeVo goodsTypeVo);


    //商品属性名

    int addPropertyKey(GoodsPropertyKeyVo goodsPropertyKeyVo);

    int deletePropertyKeyByTypeId(GoodsPropertyKeyVo goodsPropertyKeyVo);

    int deletePropertyKeyById(GoodsPropertyKeyVo goodsPropertyKeyVo);

    int updatePropertyKey(GoodsPropertyKeyVo goodsPropertyKeyVo);

    int findCountPropertyKey(GoodsPropertyKeyVo goodsPropertyKeyVo);

    List<GoodsPropertyKeyVo> findAllPagedPropertyKey(@Param(value = "goodsPropertyKeyVo") GoodsPropertyKeyVo goodsPropertyKeyVo,
                                                     @Param(value = "pageVo") PageVo pageVo);

    List<GoodsPropertyKey> findAllPropertyKey(GoodsPropertyKeyVo goodsPropertyKeyVo);

    List<GoodsPropertyKeyVo> findPropertyKey(GoodsPropertyKeyVo goodsPropertyKeyVo);

    //商品属性值

    int addPropertyValue(GoodsPropertyValueVo goodsPropertyValueVo);

    int deletePropertyValueByPropertyKeyId(GoodsPropertyValueVo goodsPropertyValueVo);

    int deletePropertyValueById(GoodsPropertyValueVo goodsPropertyValueVo);

    int updatePropertyValue(GoodsPropertyValueVo goodsPropertyValueVo);

    int findCountPropertyValue(GoodsPropertyValueVo goodsPropertyValueVo);

    List<GoodsPropertyValueVo> findAllPagedPropertyValue(@Param(value = "goodsPropertyValueVo") GoodsPropertyValueVo goodsPropertyValueVo,
                                                         @Param(value = "pageVo") PageVo pageVo);

    List<GoodsPropertyValueVo> findPropertyValue(GoodsPropertyValueVo goodsPropertyValueVo);

    //商品类型/商品属性名/商品属性值

    List<GoodsTypeVo> findAllProperties(GoodsTypeVo goodsTypeVo);

    //商品规格

    int addGoodsSku(GoodsSkuVo goodsSkuVo);

    int updateGoodsSku(GoodsSkuVo goodsSkuVo);

    int deleteGoodsSku(GoodsSkuVo goodsSkuVo);

    List<GoodsSku> findAllGoodsSku(GoodsSkuVo goodsSkuVo);

    GoodsSku findGoodsSkuById(GoodsSkuVo goodsSkuVo);

    //仓库/商品规格关系

    int addWarehouseGoodsSku(WarehouseGoodsSkuVo warehouseGoodsSkuVo);

    WarehouseGoodsSkuVo findWarehouseGoodsSku(WarehouseGoodsSkuVo warehouseGoodsSkuVo);

    List<WarehouseGoodsSkuVo> findAllWarehouseGoodsSku(WarehouseGoodsSkuVo warehouseGoodsSkuVo);

    int updateInventoryOpening(WarehouseGoodsSkuVo warehouseGoodsSkuVo);

    int increaseInventory(WarehouseGoodsSkuVo warehouseGoodsSkuVo);

    int decreaseInventory(WarehouseGoodsSkuVo warehouseGoodsSkuVo);

    int increaseNotQuantity(WarehouseGoodsSkuVo warehouseGoodsSkuVo);

    int decreaseNotQuantity(WarehouseGoodsSkuVo warehouseGoodsSkuVo);

    int updateLimitInventoryMethod(WarehouseGoodsSkuVo vo);

    //商品

    int add(GoodsVo goodsVo);

    int delete(GoodsVo goodsVo);

    int update(GoodsVo goodsVo);

    int findCount(GoodsVo goodsVo);

    List<GoodsVo> findAllPaged(@Param(value = "goodsVo") GoodsVo goodsVo, @Param(value = "pageVo") PageVo page);

    List<GoodsVo> findAll(GoodsVo goodsVo);

    List<Goods> findByTypeId(GoodsVo goodsVo);

    //下单查商品

    List<GoodsTypeVo> findCanUseByWarehouseId(WarehouseGoodsSkuVo vo);
}
