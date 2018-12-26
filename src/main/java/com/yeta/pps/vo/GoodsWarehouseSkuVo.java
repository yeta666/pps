package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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

    /**
     * 图片
     */
    private String image;

    private Integer goodsSkuId;

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

    @JsonIgnore
    private Byte flag;

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
    private BigDecimal openingMoney;

    /**
     * 期初金额
     */
    private BigDecimal openingTotalMoney;

    /**
     * 成本单价
     */
    private BigDecimal costMoney;

    /**
     * 成本金额
     */
    private BigDecimal totalCostMoney;

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

    public GoodsWarehouseSkuVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id, String name, Integer typeId, Integer warehouseId, Byte flag) {
        this.storeId = storeId;
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.warehouseId = warehouseId;
        this.flag = flag;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
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

    public Byte getFlag() {
        return flag;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
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

    public BigDecimal getOpeningMoney() {
        return openingMoney;
    }

    public void setOpeningMoney(BigDecimal openingMoney) {
        this.openingMoney = openingMoney;
    }

    public BigDecimal getOpeningTotalMoney() {
        return openingTotalMoney;
    }

    public void setOpeningTotalMoney(BigDecimal openingTotalMoney) {
        this.openingTotalMoney = openingTotalMoney;
    }

    public BigDecimal getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(BigDecimal costMoney) {
        this.costMoney = costMoney;
    }

    public BigDecimal getTotalCostMoney() {
        return totalCostMoney;
    }

    public void setTotalCostMoney(BigDecimal totalCostMoney) {
        this.totalCostMoney = totalCostMoney;
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
                ", image='" + image + '\'' +
                ", goodsSkuId=" + goodsSkuId +
                ", sku='" + sku + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", flag=" + flag +
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
                ", costMoney=" + costMoney +
                ", totalCostMoney=" + totalCostMoney +
                '}';
    }
}