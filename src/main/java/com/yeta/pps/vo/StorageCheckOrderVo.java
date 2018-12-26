package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class StorageCheckOrderVo {

    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 库存对账记录编号
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String orderId;

    private String typeName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonIgnore
    private Date startTime;

    @JsonIgnore
    private Date endTime;

    private String applyOrderId;

    /**
     * 往来单位编号
     */
    private String targetId;

    private String targetName;

    /**
     * 商品规格编号
     */
    @JsonIgnore
    private Integer goodsSkuId;

    private String sku;

    /**
     * 入库仓库编号
     */
    private Integer inWarehouseId;

    private String inWarehouseName;

    /**
     * 入库数量
     */
    private Integer inQuantity;

    /**
     * 入库成本单价
     */
    private BigDecimal inMoney;

    /**
     * 入库成本金额
     */
    private BigDecimal inTotalMoney;

    /**
     * 出库仓库编号
     */
    private Integer outWarehouseId;

    private String outWarehouseName;

    /**
     * 出库数量
     */
    private Integer outQuantity;

    /**
     * 出库成本单价
     */
    private BigDecimal outMoney;

    /**
     * 出库成本金额
     */
    private BigDecimal outTotalMoney;

    /**
     * 结存数量
     */
    private Integer checkQuantity;

    /**
     * 结存成本单价
     */
    private BigDecimal checkMoney;

    /**
     * 结存成本金额
     */
    private BigDecimal checkTotalMoney;

    /**
     * 经手人编号
     */
    private String userId;

    private String userName;

    private String remark;

    public StorageCheckOrderVo() {
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Integer goodsSkuId) {
        this.storeId = storeId;
        this.goodsSkuId = goodsSkuId;
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Date startTime, Date endTime, Integer goodsSkuId) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goodsSkuId = goodsSkuId;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
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

    public Integer getInQuantity() {
        return inQuantity;
    }

    public void setInQuantity(Integer inQuantity) {
        this.inQuantity = inQuantity;
    }

    public BigDecimal getInMoney() {
        return inMoney;
    }

    public void setInMoney(BigDecimal inMoney) {
        this.inMoney = inMoney;
    }

    public BigDecimal getInTotalMoney() {
        return inTotalMoney;
    }

    public void setInTotalMoney(BigDecimal inTotalMoney) {
        this.inTotalMoney = inTotalMoney;
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

    public Integer getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(Integer outQuantity) {
        this.outQuantity = outQuantity;
    }

    public BigDecimal getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(BigDecimal outMoney) {
        this.outMoney = outMoney;
    }

    public BigDecimal getOutTotalMoney() {
        return outTotalMoney;
    }

    public void setOutTotalMoney(BigDecimal outTotalMoney) {
        this.outTotalMoney = outTotalMoney;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        return "StorageCheckOrderVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", orderId='" + orderId + '\'' +
                ", typeName='" + typeName + '\'' +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", targetId='" + targetId + '\'' +
                ", targetName='" + targetName + '\'' +
                ", goodsSkuId=" + goodsSkuId +
                ", sku='" + sku + '\'' +
                ", inWarehouseId=" + inWarehouseId +
                ", inWarehouseName='" + inWarehouseName + '\'' +
                ", inQuantity=" + inQuantity +
                ", inMoney=" + inMoney +
                ", inTotalMoney=" + inTotalMoney +
                ", outWarehouseId=" + outWarehouseId +
                ", outWarehouseName='" + outWarehouseName + '\'' +
                ", outQuantity=" + outQuantity +
                ", outMoney=" + outMoney +
                ", outTotalMoney=" + outTotalMoney +
                ", checkQuantity=" + checkQuantity +
                ", checkMoney=" + checkMoney +
                ", checkTotalMoney=" + checkTotalMoney +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}