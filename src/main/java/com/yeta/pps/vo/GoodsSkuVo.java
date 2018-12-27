package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class GoodsSkuVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 规格编号
     */
    private Integer id;

    /**
     * 商品货号
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String goodsId;

    /**
     * 进价
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal purchasePrice;

    /**
     * 零售价
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal retailPrice;

    /**
     * vip售价
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal vipPrice;

    /**
     * 积分
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer integral;

    /**
     * 规格
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String sku;

    /**
     * 实物库存
     */
    private Integer realInventory;

    /**
     * 可用库存
     */
    private Integer canUseInventory;

    /**
     * 账面库存
     */
    private Integer bookInventory;

    private Integer checkQuantity;

    private BigDecimal checkMoney;

    private BigDecimal checkTotalMoney;

    public GoodsSkuVo() {
    }

    public GoodsSkuVo(Integer storeId) {
        this.storeId = storeId;
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

    public Integer getRealInventory() {
        return realInventory;
    }

    public void setRealInventory(Integer realInventory) {
        this.realInventory = realInventory;
    }

    public Integer getCanUseInventory() {
        return canUseInventory;
    }

    public void setCanUseInventory(Integer canUseInventory) {
        this.canUseInventory = canUseInventory;
    }

    public Integer getBookInventory() {
        return bookInventory;
    }

    public void setBookInventory(Integer bookInventory) {
        this.bookInventory = bookInventory;
    }

    public Integer getCheckQuantity() {
        return checkQuantity;
    }

    public void setCheckQuantity(Integer checkQuantity) {
        this.checkQuantity = checkQuantity;
    }

    public BigDecimal getCheckMoney() {
        return checkMoney;
    }

    public void setCheckMoney(BigDecimal checkMoney) {
        this.checkMoney = checkMoney;
    }

    public BigDecimal getCheckTotalMoney() {
        return checkTotalMoney;
    }

    public void setCheckTotalMoney(BigDecimal checkTotalMoney) {
        this.checkTotalMoney = checkTotalMoney;
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
                ", realInventory=" + realInventory +
                ", canUseInventory=" + canUseInventory +
                ", bookInventory=" + bookInventory +
                ", checkQuantity=" + checkQuantity +
                ", checkMoney=" + checkMoney +
                ", checkTotalMoney=" + checkTotalMoney +
                '}';
    }
}