package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class GoodsWarehouseSkuOrderVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;
    
    /**
     * 单据编号
     */
    private String id;
    
    /**
     * 单据类型
     */
    private String typeName;
    
    /**
     * 单据日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @JsonIgnore
    private Date startTime;

    @JsonIgnore
    private Date endTime;

    /**
     * 来源订单
     */
    private String applyOrderId;

    /**
     * 往来单位
     */
    private String targetName;

    @JsonIgnore
    private String goodsId;

    /**
     * 商品规格
     */
    private String sku;

    private Integer inWarehouseId;

    /**
     * 入库仓库
     */
    private String inWarehouseName;

    /**
     * 入库总数量
     */
    private Integer inTotalQuantity;

    /**
     * 入库总金额
     */
    private BigDecimal inTotalMoney;

    /**
     * 入库总优惠金额
     */
    private BigDecimal inTotalDiscountMoney;

    private Integer outWarehouseId;

    /**
     * 出库仓库
     */
    private String outWarehouseName;

    /**
     * 出库总数量
     */
    private Integer outTotalQuantity;

    /**
     * 出库总金额
     */
    private BigDecimal outTotalMoney;

    /**
     * 出库总优惠金额
     */
    private BigDecimal outTotalDiscountMoney;

    /**
     * 总成本金额
     */
    private BigDecimal totalPurchasePrice;

    /**
     * 经手人
     */
    private String userName;

    /**
     * 单据备注
     */
    private String remark;

    public GoodsWarehouseSkuOrderVo() {
    }

    public GoodsWarehouseSkuOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id, Date startTime, Date endTime, String goodsId) {
        this.storeId = storeId;
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goodsId = goodsId;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getApplyOrderId() {
        return applyOrderId;
    }

    public void setApplyOrderId(String applyOrderId) {
        this.applyOrderId = applyOrderId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getInWarehouseId() {
        return inWarehouseId;
    }

    public void setInWarehouseId(Integer inWarehouseId) {
        this.inWarehouseId = inWarehouseId;
    }

    public String getInWarehouseName() {
        return inWarehouseName;
    }

    public void setInWarehouseName(String inWarehouseName) {
        this.inWarehouseName = inWarehouseName;
    }

    public Integer getInTotalQuantity() {
        return inTotalQuantity;
    }

    public void setInTotalQuantity(Integer inTotalQuantity) {
        this.inTotalQuantity = inTotalQuantity;
    }

    public BigDecimal getInTotalMoney() {
        return inTotalMoney;
    }

    public void setInTotalMoney(BigDecimal inTotalMoney) {
        this.inTotalMoney = inTotalMoney;
    }

    public BigDecimal getInTotalDiscountMoney() {
        return inTotalDiscountMoney;
    }

    public void setInTotalDiscountMoney(BigDecimal inTotalDiscountMoney) {
        this.inTotalDiscountMoney = inTotalDiscountMoney;
    }

    public Integer getOutWarehouseId() {
        return outWarehouseId;
    }

    public void setOutWarehouseId(Integer outWarehouseId) {
        this.outWarehouseId = outWarehouseId;
    }

    public String getOutWarehouseName() {
        return outWarehouseName;
    }

    public void setOutWarehouseName(String outWarehouseName) {
        this.outWarehouseName = outWarehouseName;
    }

    public Integer getOutTotalQuantity() {
        return outTotalQuantity;
    }

    public void setOutTotalQuantity(Integer outTotalQuantity) {
        this.outTotalQuantity = outTotalQuantity;
    }

    public BigDecimal getOutTotalMoney() {
        return outTotalMoney;
    }

    public void setOutTotalMoney(BigDecimal outTotalMoney) {
        this.outTotalMoney = outTotalMoney;
    }

    public BigDecimal getOutTotalDiscountMoney() {
        return outTotalDiscountMoney;
    }

    public void setOutTotalDiscountMoney(BigDecimal outTotalDiscountMoney) {
        this.outTotalDiscountMoney = outTotalDiscountMoney;
    }

    public BigDecimal getTotalPurchasePrice() {
        return totalPurchasePrice;
    }

    public void setTotalPurchasePrice(BigDecimal totalPurchasePrice) {
        this.totalPurchasePrice = totalPurchasePrice;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "GoodsWarehouseSkuOrderVo{" +
                "storeId=" + storeId +
                ", id='" + id + '\'' +
                ", typeName='" + typeName + '\'' +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", targetName='" + targetName + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", sku='" + sku + '\'' +
                ", inWarehouseId=" + inWarehouseId +
                ", inWarehouseName='" + inWarehouseName + '\'' +
                ", inTotalQuantity=" + inTotalQuantity +
                ", inTotalMoney=" + inTotalMoney +
                ", inTotalDiscountMoney=" + inTotalDiscountMoney +
                ", outWarehouseId=" + outWarehouseId +
                ", outWarehouseName='" + outWarehouseName + '\'' +
                ", outTotalQuantity=" + outTotalQuantity +
                ", outTotalMoney=" + outTotalMoney +
                ", outTotalDiscountMoney=" + outTotalDiscountMoney +
                ", totalPurchasePrice=" + totalPurchasePrice +
                ", userName='" + userName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}