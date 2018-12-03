package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GoodsLabelVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 商品标签id
     */
    private Integer id;

    /**
     * 商品便签名
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String name;

    public GoodsLabelVo() {
    }

    public GoodsLabelVo(Integer storeId) {
        this.storeId = storeId;
    }

    public GoodsLabelVo(Integer storeId, Integer id) {
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
        return "GoodsLabelVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}