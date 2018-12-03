package com.yeta.pps.po;

import javax.persistence.*;

public class GoodsUnit {

    /**
     * 商品单位id
     */
    @Id
    private Integer id;

    /**
     * 商品单位名
     */
    private String name;

    public GoodsUnit() {
    }

    /**
     * 获取商品单位id
     *
     * @return id - 商品单位id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品单位id
     *
     * @param id 商品单位id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品单位名
     *
     * @return name - 商品单位名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品单位名
     *
     * @param name 商品单位名
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GoodsUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}