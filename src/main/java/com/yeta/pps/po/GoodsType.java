package com.yeta.pps.po;

import javax.persistence.*;

public class GoodsType {

    /**
     * 商品分类id
     */
    @Id
    private Integer id;

    /**
     * 商品分类名
     */
    private String name;

    public GoodsType() {
    }

    /**
     * 获取商品分类id
     *
     * @return id - 商品分类id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品分类id
     *
     * @param id 商品分类id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品分类名
     *
     * @return name - 商品分类名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品分类名
     *
     * @param name 商品分类名
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GoodsType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}