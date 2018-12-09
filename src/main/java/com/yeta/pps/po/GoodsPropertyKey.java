package com.yeta.pps.po;

public class GoodsPropertyKey {

    /**
     * 商品属性名id
     */
    private Integer id;

    /**
     * 商品属性名
     */
    private String name;

    /**
     * 商品分类id
     */
    private Integer typeId;

    public GoodsPropertyKey() {
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
        return "GoodsPropertyKey{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}