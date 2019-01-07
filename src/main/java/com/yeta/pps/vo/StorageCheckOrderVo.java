package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class StorageCheckOrderVo {

    @NotNull(message = CommonResponse.PARAMETER_ERROR)
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

    /**
     * 单据状态
     */
    private Byte orderStatus;

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
    private Double inMoney;

    /**
     * 入库成本金额
     */
    private Double inTotalMoney;

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
    private Double outMoney;

    /**
     * 出库成本金额
     */
    private Double outTotalMoney;

    /**
     * 结存数量
     */
    private Integer checkQuantity;

    /**
     * 结存成本单价
     */
    private Double checkMoney;

    /**
     * 结存成本金额
     */
    private Double checkTotalMoney;

    /**
     * 经手人编号
     */
    private String userId;

    private String userName;

    private String remark;

    @JsonIgnore
    private Integer flag;

    public StorageCheckOrderVo() {
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Integer goodsSkuId) {
        this.storeId = storeId;
        this.goodsSkuId = goodsSkuId;
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String orderId, Integer goodsSkuId, String userId) {
        this.storeId = storeId;
        this.orderId = orderId;
        this.goodsSkuId = goodsSkuId;
        this.userId = userId;
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Date startTime, Date endTime, Integer goodsSkuId) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goodsSkuId = goodsSkuId;
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Date startTime, Date endTime, Integer goodsSkuId, Integer flag) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goodsSkuId = goodsSkuId;
        this.flag = flag;
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

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
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

    public Double getInMoney() {
        return inMoney;
    }

    public void setInMoney(Double inMoney) {
        this.inMoney = inMoney;
    }

    public Double getInTotalMoney() {
        return inTotalMoney;
    }

    public void setInTotalMoney(Double inTotalMoney) {
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

    public Double getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(Double outMoney) {
        this.outMoney = outMoney;
    }

    public Double getOutTotalMoney() {
        return outTotalMoney;
    }

    public void setOutTotalMoney(Double outTotalMoney) {
        this.outTotalMoney = outTotalMoney;
    }

    public Integer getCheckQuantity() {
        return checkQuantity;
    }

    public void setCheckQuantity(Integer checkQuantity) {
        this.checkQuantity = checkQuantity;
    }

    public Double getCheckMoney() {
        return checkMoney;
    }

    public void setCheckMoney(Double checkMoney) {
        this.checkMoney = checkMoney;
    }

    public Double getCheckTotalMoney() {
        return checkTotalMoney;
    }

    public void setCheckTotalMoney(Double checkTotalMoney) {
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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
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
                ", orderStatus=" + orderStatus +
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