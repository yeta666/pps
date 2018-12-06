package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GoodsBrandVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 商品品牌id
     */
    private Integer id;

    /**
     * 商品品牌名
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String name;

    public GoodsBrandVo() {
    }

    public GoodsBrandVo(Integer storeId) {
        this.storeId = storeId;
    }

    public GoodsBrandVo(Integer storeId, Integer id) {
        this.storeId = storeId;
        this.id = id;
    }

    public GoodsBrandVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, @NotBlank(message = CommonResponse.MESSAGE3) String name) {
        this.storeId = storeId;
        this.name = name;
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
        return "GoodsBrandVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}