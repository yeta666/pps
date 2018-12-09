package com.yeta.pps.po;

public class GoodsPropertyValue {

    /**
     * 商品属性值id
     */
    private Integer id;

    /**
     * 商品属性值
     */
    private String name;

    /**
     * 商品属性名id
     */
    private Integer propertyKeyId;

    public GoodsPropertyValue() {
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
        return "GoodsPropertyValue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", propertyKeyId=" + propertyKeyId +
                '}';
    }
}