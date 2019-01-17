package com.yeta.pps.po;

public class GoodsSku {

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
    private Double purchasePrice;

    /**
     * 零售价
     */
    private Double retailPrice;

    /**
     * vip售价
     */
    private Double vipPrice;

    /**
     * 商品积分
     */
    private Integer integral;

    /**
     * 商品规格
     */
    private String sku;

    public GoodsSku() {
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

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Double getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Double vipPrice) {
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
        return "GoodsSku{" +
                "id=" + id +
                ", goodsId='" + goodsId + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", retailPrice=" + retailPrice +
                ", vipPrice=" + vipPrice +
                ", integral=" + integral +
                ", sku='" + sku + '\'' +
                '}';
    }
}