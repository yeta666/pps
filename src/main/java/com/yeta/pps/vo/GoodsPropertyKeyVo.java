package com.yeta.pps.vo;

import com.yeta.pps.po.GoodsPropertyValue;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class GoodsPropertyKeyVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    private String typeName;

    private List<GoodsPropertyValue> goodsPropertyValues;

    /**
     * 商品属性名id
     */
    private Integer id;

    /**
     * 商品属性名
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String name;

    /**
     * 商品分类id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer typeId;

    public GoodsPropertyKeyVo() {
    }

    public GoodsPropertyKeyVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId) {
        this.storeId = storeId;
    }

    public GoodsPropertyKeyVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Integer id, @NotNull(message = CommonResponse.MESSAGE3) Integer typeId) {
        this.storeId = storeId;
        this.id = id;
        this.typeId = typeId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<GoodsPropertyValue> getGoodsPropertyValues() {
        return goodsPropertyValues;
    }

    public void setGoodsPropertyValues(List<GoodsPropertyValue> goodsPropertyValues) {
        this.goodsPropertyValues = goodsPropertyValues;
    }

    /**
     * 获取商品属性名id
     *
     * @return id - 商品属性名id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品属性名id
     *
     * @param id 商品属性名id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品属性名
     *
     * @return name - 商品属性名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品属性名
     *
     * @param name 商品属性名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取商品分类id
     *
     * @return type_id - 商品分类id
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * 设置商品分类id
     *
     * @param typeId 商品分类id
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "GoodsPropertyKeyVo{" +
                "storeId=" + storeId +
                ", typeName='" + typeName + '\'' +
                ", goodsPropertyValues=" + goodsPropertyValues +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}