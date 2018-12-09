package com.yeta.pps.po;

import java.math.BigDecimal;

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
     * 可用库存
     */
    private Integer inventory;

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

    /**
     * 获取商品规格id
     *
     * @return id - 商品规格id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品规格id
     *
     * @param id 商品规格id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品货号
     *
     * @return goods_id - 商品货号
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品货号
     *
     * @param goodsId 商品货号
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取进价
     *
     * @return purchase_price - 进价
     */
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * 设置进价
     *
     * @param purchasePrice 进价
     */
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * 获取零售价
     *
     * @return retail_price - 零售价
     */
    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    /**
     * 设置零售价
     *
     * @param retailPrice 零售价
     */
    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    /**
     * 获取vip售价
     *
     * @return vip_price - vip售价
     */
    public BigDecimal getVipPrice() {
        return vipPrice;
    }

    /**
     * 设置vip售价
     *
     * @param vipPrice vip售价
     */
    public void setVipPrice(BigDecimal vipPrice) {
        this.vipPrice = vipPrice;
    }

    /**
     * 获取可用库存
     *
     * @return inventory - 可用库存
     */
    public Integer getInventory() {
        return inventory;
    }

    /**
     * 设置可用库存
     *
     * @param inventory 可用库存
     */
    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    /**
     * 获取商品积分
     *
     * @return integral - 商品积分
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * 设置商品积分
     *
     * @param integral 商品积分
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * 获取商品规格
     *
     * @return sku - 商品规格
     */
    public String getSku() {
        return sku;
    }

    /**
     * 设置商品规格
     *
     * @param sku 商品规格
     */
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
                ", inventory=" + inventory +
                ", integral=" + integral +
                ", sku='" + sku + '\'' +
                '}';
    }
}