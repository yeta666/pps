package com.yeta.pps.vo;

import java.math.BigDecimal;

public class GoodsSkuVo {

    /**
     * 店铺id
     */
    private Integer storeId;

    /**
     * 商品规格id
     */
    private Integer id;

    /**
     * 商品货号
     */
    private String goodsId;

    /**
     * 进价
     */
    private BigDecimal purchasePrice;

    /**
     * 零售价
     */
    private BigDecimal retailPrice;

    /**
     * vip售价
     */
    private BigDecimal vipPrice;

    /**
     * 商品积分
     */
    private Integer integral;

    /**
     * 商品规格
     */
    private String sku;

    public GoodsSkuVo() {
    }

    public GoodsSkuVo(Integer storeId) {
        this.storeId = storeId;
    }

    public GoodsSkuVo(Integer storeId, String goodsId) {
        this.storeId = storeId;
        this.goodsId = goodsId;
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(BigDecimal vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "GoodsSkuVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", goodsId='" + goodsId + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", retailPrice=" + retailPrice +
                ", vipPrice=" + vipPrice +
                ", integral=" + integral +
                ", sku='" + sku + '\'' +
                '}';
    }
}