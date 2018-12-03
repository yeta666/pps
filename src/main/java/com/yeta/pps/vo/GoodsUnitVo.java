package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GoodsUnitVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 商品单位id
     */
    private Integer id;

    /**
     * 商品单位名
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String name;

    public GoodsUnitVo() {
    }

    public GoodsUnitVo(Integer storeId) {
        this.storeId = storeId;
    }

    public GoodsUnitVo(Integer storeId, Integer id) {
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
        return "GoodsUnitVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}