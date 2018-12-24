package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.util.List;

public class GoodsWarehouseSkuVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 商品货号
     */
    private String id;

    /**
     * 商品名
     */
    private String name;

    /**
     * 条码
     */
    private String barCode;

    /**
     * 分类编号
     */
    private Integer typeId;

    /**
     * 分类
     */
    private String typeName;

    private Integer goodsSkuId;

    /**
     * skus
     */
    private String skus;

    /**
     * 图片
     */
    private String image;

    /**
     * 规格
     */
    private String sku;

    /**
     * 仓库编号
     */
    private Integer warehouseId;

    /**
     * 仓库
     */
    private String warehouseName;

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
     * 可用存库
     */
    private Integer canUseInventory;

    /**
     * 账面库存
     */
    private Integer bookInventory;

    @JsonIgnore
    private Byte warning;

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

    public GoodsWarehouseSkuVo() {
    }

    public GoodsWarehouseSkuVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id) {
        this.storeId = storeId;
        this.id = id;
    }

    public GoodsWarehouseSkuVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id, String name, Integer typeId) {
        this.storeId = storeId;
        this.id = id;
        this.name = name;
        this.typeId = typeId;
    }

    public GoodsWarehouseSkuVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id, String name, Integer typeId, Integer warehouseId, Byte warning) {
        this.storeId = storeId;
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.warehouseId = warehouseId;
        this.warning = warning;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

    public Byte getWarning() {
        return warning;
    }

    public void setWarning(Byte warning) {
        this.warning = warning;
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

    @Override
    public String toString() {
        return "GoodsWarehouseSkuVo{" +
                "storeId=" + storeId +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", barCode='" + barCode + '\'' +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", goodsSkuId=" + goodsSkuId +
                ", skus='" + skus + '\'' +
                ", image='" + image + '\'' +
                ", sku='" + sku + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", realInventory=" + realInventory +
                ", notSentQuantity=" + notSentQuantity +
                ", notReceivedQuantity=" + notReceivedQuantity +
                ", canUseInventory=" + canUseInventory +
                ", bookInventory=" + bookInventory +
                ", warning=" + warning +
                ", inventoryUpperLimit=" + inventoryUpperLimit +
                ", inventoryLowLimit=" + inventoryLowLimit +
                ", needQuantity=" + needQuantity +
                '}';
    }
}