package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class StorageApplyOrderVo {

    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    List<OrderGoodsSkuVo> details;

    /**
     * 单据编号
     */
    private String id;

    /**
     * 单据类型，1：调拨单
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Byte type;

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
     * 单据状态
     */
    private Byte orderStatus;

    private String orderStatusName;

    /**
     * 总调拨数量
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer totalQuantity;

    /**
     * 入库仓库编号，对应收货
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer inWarehouseId;

    private String inWarehouseName;

    /**
     * 已收货数量
     */
    private Integer inReceivedQuantity;

    /**
     * 未收货数量
     */
    private Integer inNotReceivedQuantity;

    /**
     * 出库仓库编号，对应发货
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer outWarehouseId;

    private String outWarehouseName;

    /**
     * 已发货数量
     */
    private Integer outSentQuantity;

    /**
     * 未发货数量
     */
    private Integer outNotSentQuantity;

    /**
     * 总调拨金额
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Double totalMoney;

    /**
     * 经手人编号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String userId;

    private String userName;

    /**
     * 单据备注
     */
    private String remark;

    public StorageApplyOrderVo() {
    }

    public StorageApplyOrderVo(Integer totalQuantity, Double totalMoney) {
        this.totalQuantity = totalQuantity;
        this.totalMoney = totalMoney;
    }

    public StorageApplyOrderVo(Integer storeId, String id) {
        this.storeId = storeId;
        this.id = id;
    }

    public StorageApplyOrderVo(Integer storeId, String id, Byte type, Date startTime, Date endTime, Integer inWarehouseId, Integer outWarehouseId) {
        this.storeId = storeId;
        this.id = id;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.inWarehouseId = inWarehouseId;
        this.outWarehouseId = outWarehouseId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public List<OrderGoodsSkuVo> getDetails() {
        return details;
    }

    public void setDetails(List<OrderGoodsSkuVo> details) {
        this.details = details;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
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

    public Integer getInReceivedQuantity() {
        return inReceivedQuantity;
    }

    public void setInReceivedQuantity(Integer inReceivedQuantity) {
        this.inReceivedQuantity = inReceivedQuantity;
    }

    public Integer getInNotReceivedQuantity() {
        return inNotReceivedQuantity;
    }

    public void setInNotReceivedQuantity(Integer inNotReceivedQuantity) {
        this.inNotReceivedQuantity = inNotReceivedQuantity;
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

    public Integer getOutSentQuantity() {
        return outSentQuantity;
    }

    public void setOutSentQuantity(Integer outSentQuantity) {
        this.outSentQuantity = outSentQuantity;
    }

    public Integer getOutNotSentQuantity() {
        return outNotSentQuantity;
    }

    public void setOutNotSentQuantity(Integer outNotSentQuantity) {
        this.outNotSentQuantity = outNotSentQuantity;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
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
        return "StorageApplyOrderVo{" +
                "storeId=" + storeId +
                ", details=" + details +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", typeName='" + typeName + '\'' +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", orderStatus=" + orderStatus +
                ", orderStatusName='" + orderStatusName + '\'' +
                ", totalQuantity=" + totalQuantity +
                ", inWarehouseId=" + inWarehouseId +
                ", inWarehouseName='" + inWarehouseName + '\'' +
                ", inReceivedQuantity=" + inReceivedQuantity +
                ", inNotReceivedQuantity=" + inNotReceivedQuantity +
                ", outWarehouseId=" + outWarehouseId +
                ", outWarehouseName='" + outWarehouseName + '\'' +
                ", outSentQuantity=" + outSentQuantity +
                ", outNotSentQuantity=" + outNotSentQuantity +
                ", totalMoney=" + totalMoney +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}