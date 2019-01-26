package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class StorageResultOrderVo {

    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private List<OrderGoodsSkuVo> details;

    /**
     * 单据编号
     */
    private String id;

    /**
     * 单据类型
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Byte type;

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
     * 单据状态
     */
    private Byte orderStatus;

    /**
     * 往来单位编号
     */
    public String targetId;

    public String targetName;

    /**
     * 仓库编号
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer warehouseId;

    private String warehouseName;

    /**
     * 出库仓库编号
     */
    private Integer outWarehouseId;

    private String outWarehouseName;

    /**
     * 总数量
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer totalQuantity;

    /**
     * 总金额
     */
    private Double totalMoney;

    /**
     * 总库存数量
     */
    private Integer totalCheckQuantity;

    /**
     * 总报溢数量
     */
    private Integer totalInQuantity;

    /**
     * 总报溢金额
     */
    private Double totalInMoney;

    /**
     * 总报损数量
     */
    private Integer totalOutQuantity;

    /**
     * 总报损金额
     */
    private Double totalOutMoney;

    /**
     * 经手人
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String userId;

    private String userName;

    /**
     * 单据备注
     */
    private String remark;

    public StorageResultOrderVo() {
    }

    public StorageResultOrderVo(Integer storeId, String id) {
        this.storeId = storeId;
        this.id = id;
    }

    public StorageResultOrderVo(Integer storeId, Byte type) {
        this.storeId = storeId;
        this.type = type;
    }

    public StorageResultOrderVo(Integer storeId, String id, String userId, String remark) {
        this.storeId = storeId;
        this.id = id;
        this.userId = userId;
        this.remark = remark;
    }

    public StorageResultOrderVo(Integer storeId, String id, Byte type, Date startTime, Date endTime, String targetName) {
        this.storeId = storeId;
        this.id = id;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.targetName = targetName;
    }

    public StorageResultOrderVo(Integer storeId, String id, Byte type, Date createTime, String applyOrderId, Byte orderStatus, Integer warehouseId, Integer outWarehouseId, Integer totalQuantity, Double totalMoney, String userId, String remark) {
        this.storeId = storeId;
        this.id = id;
        this.type = type;
        this.createTime = createTime;
        this.applyOrderId = applyOrderId;
        this.orderStatus = orderStatus;
        this.warehouseId = warehouseId;
        this.outWarehouseId = outWarehouseId;
        this.totalQuantity = totalQuantity;
        this.totalMoney = totalMoney;
        this.userId = userId;
        this.remark = remark;
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

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
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

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getTotalCheckQuantity() {
        return totalCheckQuantity;
    }

    public void setTotalCheckQuantity(Integer totalCheckQuantity) {
        this.totalCheckQuantity = totalCheckQuantity;
    }

    public Integer getTotalInQuantity() {
        return totalInQuantity;
    }

    public void setTotalInQuantity(Integer totalInQuantity) {
        this.totalInQuantity = totalInQuantity;
    }

    public Double getTotalInMoney() {
        return totalInMoney;
    }

    public void setTotalInMoney(Double totalInMoney) {
        this.totalInMoney = totalInMoney;
    }

    public Integer getTotalOutQuantity() {
        return totalOutQuantity;
    }

    public void setTotalOutQuantity(Integer totalOutQuantity) {
        this.totalOutQuantity = totalOutQuantity;
    }

    public Double getTotalOutMoney() {
        return totalOutMoney;
    }

    public void setTotalOutMoney(Double totalOutMoney) {
        this.totalOutMoney = totalOutMoney;
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
        return "StorageResultOrderVo{" +
                "storeId=" + storeId +
                ", details=" + details +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", orderStatus=" + orderStatus +
                ", targetId='" + targetId + '\'' +
                ", targetName='" + targetName + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", outWarehouseId=" + outWarehouseId +
                ", outWarehouseName='" + outWarehouseName + '\'' +
                ", totalQuantity=" + totalQuantity +
                ", totalMoney=" + totalMoney +
                ", totalCheckQuantity=" + totalCheckQuantity +
                ", totalInQuantity=" + totalInQuantity +
                ", totalInMoney=" + totalInMoney +
                ", totalOutQuantity=" + totalOutQuantity +
                ", totalOutMoney=" + totalOutMoney +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}