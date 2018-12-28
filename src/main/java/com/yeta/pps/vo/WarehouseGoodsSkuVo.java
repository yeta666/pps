package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author YETA
 * @date 2018/12/20/21:48
 */
public class WarehouseGoodsSkuVo {

    /**
     * 店铺编号
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 仓库/商品规格关系编号
     */
    @JsonIgnore
    private Integer id;

    /**
     * 仓库编号
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer warehouseId;

    /**
     * 商品规格编号
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer goodsSkuId;

    /**
     * 实物库存
     */
    private Integer realInventory;

    /**
     * 待发货数量
     */
    private Integer notSentQuantity;

    /**
     * 待收货数量
     */
    private Integer notReceivedQuantity;

    /**
     * 可用库存
     */
    private Integer canUseInventory;

    /**
     * 账面库存
     */
    private Integer bookInventory;

    /**
     * 库存上限
     */
    private Integer inventoryUpperLimit;

    /**
     * 库存下限
     */
    private Integer inventoryLowLimit;

    /**
     * 缺货数量
     */
    private Integer needQuantity;

    /**
     * 期初数量
     */
    private Integer openingQuantity;

    /**
     * 期初成本单价
     */
    private Double openingMoney;

    /**
     * 期初金额
     */
    private Double openingTotalMoney;

    /**
     * 经手人
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String userId;

    public WarehouseGoodsSkuVo() {
    }

    public WarehouseGoodsSkuVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, @NotNull(message = CommonResponse.MESSAGE3) Integer warehouseId) {
        this.storeId = storeId;
        this.warehouseId = warehouseId;
    }

    public WarehouseGoodsSkuVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, @NotNull(message = CommonResponse.MESSAGE3) Integer warehouseId, @NotNull(message = CommonResponse.MESSAGE3) Integer goodsSkuId) {
        this.storeId = storeId;
        this.warehouseId = warehouseId;
        this.goodsSkuId = goodsSkuId;
    }

    public WarehouseGoodsSkuVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, @NotNull(message = CommonResponse.MESSAGE3) Integer warehouseId, @NotNull(message = CommonResponse.MESSAGE3) Integer goodsSkuId, Integer realInventory, Integer canUseInventory, Integer bookInventory) {
        this.storeId = storeId;
        this.warehouseId = warehouseId;
        this.goodsSkuId = goodsSkuId;
        this.realInventory = realInventory;
        this.canUseInventory = canUseInventory;
        this.bookInventory = bookInventory;
    }

    public WarehouseGoodsSkuVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, @NotNull(message = CommonResponse.MESSAGE3) Integer warehouseId, @NotNull(message = CommonResponse.MESSAGE3) Integer goodsSkuId, Integer notSentQuantity, Integer notReceivedQuantity) {
        this.storeId = storeId;
        this.warehouseId = warehouseId;
        this.goodsSkuId = goodsSkuId;
        this.notSentQuantity = notSentQuantity;
        this.notReceivedQuantity = notReceivedQuantity;
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

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public Integer getRealInventory() {
        return realInventory;
    }

    public void setRealInventory(Integer realInventory) {
        this.realInventory = realInventory;
    }

    public Integer getNotSentQuantity() {
        return notSentQuantity;
    }

    public void setNotSentQuantity(Integer notSentQuantity) {
        this.notSentQuantity = notSentQuantity;
    }

    public Integer getNotReceivedQuantity() {
        return notReceivedQuantity;
    }

    public void setNotReceivedQuantity(Integer notReceivedQuantity) {
        this.notReceivedQuantity = notReceivedQuantity;
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

    public Integer getInventoryUpperLimit() {
        return inventoryUpperLimit;
    }

    public void setInventoryUpperLimit(Integer inventoryUpperLimit) {
        this.inventoryUpperLimit = inventoryUpperLimit;
    }

    public Integer getInventoryLowLimit() {
        return inventoryLowLimit;
    }

    public void setInventoryLowLimit(Integer inventoryLowLimit) {
        this.inventoryLowLimit = inventoryLowLimit;
    }

    public Integer getNeedQuantity() {
        return needQuantity;
    }

    public void setNeedQuantity(Integer needQuantity) {
        this.needQuantity = needQuantity;
    }

    public Integer getOpeningQuantity() {
        return openingQuantity;
    }

    public void setOpeningQuantity(Integer openingQuantity) {
        this.openingQuantity = openingQuantity;
    }

    public Double getOpeningMoney() {
        return openingMoney;
    }

    public void setOpeningMoney(Double openingMoney) {
        this.openingMoney = openingMoney;
    }

    public Double getOpeningTotalMoney() {
        return openingTotalMoney;
    }

    public void setOpeningTotalMoney(Double openingTotalMoney) {
        this.openingTotalMoney = openingTotalMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "WarehouseGoodsSkuVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", warehouseId=" + warehouseId +
                ", goodsSkuId=" + goodsSkuId +
                ", realInventory=" + realInventory +
                ", notSentQuantity=" + notSentQuantity +
                ", notReceivedQuantity=" + notReceivedQuantity +
                ", canUseInventory=" + canUseInventory +
                ", bookInventory=" + bookInventory +
                ", inventoryUpperLimit=" + inventoryUpperLimit +
                ", inventoryLowLimit=" + inventoryLowLimit +
                ", needQuantity=" + needQuantity +
                ", openingQuantity=" + openingQuantity +
                ", openingMoney=" + openingMoney +
                ", openingTotalMoney=" + openingTotalMoney +
                ", userId='" + userId + '\'' +
                '}';
    }
}
