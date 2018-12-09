package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GoodsPropertyValueVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    private String propertyKeyName;

    private String typeName;

    /**
     * 商品属性值id
     */
    private Integer id;

    /**
     * 商品属性值
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String name;

    /**
     * 商品属性名id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer propertyKeyId;

    public GoodsPropertyValueVo() {
    }

    public GoodsPropertyValueVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId) {
        this.storeId = storeId;
    }

    public GoodsPropertyValueVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Integer id, @NotNull(message = CommonResponse.MESSAGE3) Integer propertyKeyId) {
        this.storeId = storeId;
        this.id = id;
        this.propertyKeyId = propertyKeyId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getPropertyKeyName() {
        return propertyKeyName;
    }

    public void setPropertyKeyName(String propertyKeyName) {
        this.propertyKeyName = propertyKeyName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 获取商品属性值id
     *
     * @return id - 商品属性值id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品属性值id
     *
     * @param id 商品属性值id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品属性值
     *
     * @return name - 商品属性值
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品属性值
     *
     * @param name 商品属性值
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取商品属性名id
     *
     * @return property_key_id - 商品属性名id
     */
    public Integer getPropertyKeyId() {
        return propertyKeyId;
    }

    /**
     * 设置商品属性名id
     *
     * @param propertyKeyId 商品属性名id
     */
    public void setPropertyKeyId(Integer propertyKeyId) {
        this.propertyKeyId = propertyKeyId;
    }

    @Override
    public String toString() {
        return "GoodsPropertyValueVo{" +
                "storeId=" + storeId +
                ", propertyKeyName='" + propertyKeyName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", propertyKeyId=" + propertyKeyId +
                '}';
    }
}