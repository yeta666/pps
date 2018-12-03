package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GoodsTypeVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 商品分类id
     */
    private Integer id;

    /**
     * 商品分类名
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String name;

    public GoodsTypeVo() {
    }

    public GoodsTypeVo(Integer storeId) {
        this.storeId = storeId;
    }

    public GoodsTypeVo(Integer storeId, Integer id) {
        this.storeId = storeId;
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GoodsTypeVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}