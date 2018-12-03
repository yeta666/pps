package com.yeta.pps.po;

import javax.persistence.*;

public class GoodsBrand {

    /**
     * 商品品牌id
     */
    @Id
    private Integer id;

    /**
     * 商品品牌名
     */
    private String name;

    public GoodsBrand() {
    }

    /**
     * 获取商品品牌id
     *
     * @return id - 商品品牌id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品品牌id
     *
     * @param id 商品品牌id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品品牌名
     *
     * @return name - 商品品牌名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品品牌名
     *
     * @param name 商品品牌名
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GoodsBrand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}