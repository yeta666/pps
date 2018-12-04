package com.yeta.pps.mapper;

import com.yeta.pps.po.*;
import com.yeta.pps.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyGoodsMapper {

    int addBrand(GoodsBrandVo goodsBrandVo);

    int deleteBrand(GoodsBrandVo goodsBrandVo);

    int updateBrand(GoodsBrandVo goodsBrandVo);

    int findCountBrand(GoodsBrandVo goodsBrandVo);

    List<GoodsBrand> findAllBrand(GoodsBrandVo goodsBrandVo);

    List<GoodsBrand> findAllPagedBrand(@Param(value = "goodsBrandVo") GoodsBrandVo goodsBrandVo, @Param(value = "pageVo") PageVo pageVo);

    GoodsBrand findBrandById(GoodsBrandVo goodsBrandVo);

    //

    int addLabel(GoodsLabelVo goodsLabelVo);

    int deleteLabel(GoodsLabelVo goodsLabelVo);

    int updateLabel(GoodsLabelVo goodsLabelVo);

    int findCountLabel(GoodsLabelVo goodsLabelVo);

    List<GoodsLabel> findAllLabel(GoodsLabelVo goodsLabelVo);

    List<GoodsLabel> findAllPagedLabel(@Param(value = "goodsLabelVo") GoodsLabelVo goodsLabelVo, @Param(value = "pageVo") PageVo pageVo);

    GoodsLabel findLabelById(GoodsLabelVo goodsLabelVo);

    //

    int addType(GoodsTypeVo goodsTypeVo);

    int deleteType(GoodsTypeVo goodsTypeVo);

    int updateType(GoodsTypeVo goodsTypeVo);

    int findCountType(GoodsTypeVo goodsTypeVo);

    List<GoodsType> findAllType(GoodsTypeVo goodsTypeVo);

    List<GoodsType> findAllPagedType(@Param(value = "goodsTypeVo") GoodsTypeVo goodsTypeVo, @Param(value = "pageVo") PageVo pageVo);

    GoodsType findTypeById(GoodsTypeVo goodsTypeVo);

    //

    int addUnit(GoodsUnitVo goodsUnitVo);

    int deleteUnit(GoodsUnitVo goodsUnitVo);

    int updateUnit(GoodsUnitVo goodsUnitVo);

    int findCountUnit(GoodsUnitVo goodsUnitVo);

    List<GoodsUnit> findAllUnit(GoodsUnitVo goodsUnitVo);

    List<GoodsUnit> findAllPagedUnit(@Param(value = "goodsUnitVo") GoodsUnitVo goodsUnitVo, @Param(value = "pageVo") PageVo pageVo);

    GoodsUnit findUnitById(GoodsUnitVo goodsUnitVo);

    //

    int add(GoodsVo goodsVo);

    int delete(GoodsVo goodsVo);

    int update(GoodsVo goodsVo);

    int findCount(GoodsVo goodsVo);

    List<GoodsVo> findAllPaged(@Param(value = "goodsVo") GoodsVo goodsVo, @Param(value = "pageVo") PageVo page);

    GoodsVo findById(GoodsVo goodsVo);

    GoodsVo findBrandLabelTypeUnit(GoodsVo goodsVo);
}