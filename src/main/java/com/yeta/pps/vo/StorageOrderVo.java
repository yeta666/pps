package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class StorageOrderVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    /**
     * 单据编号
     */
    private String id;

    /**
     * 单据类型，1：采购订单收货单，2：退换货申请收货单，3：销售订单发货单，4：退换货申请发货单
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
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String applyOrderId;

    private ProcurementApplyOrderVo procurementApplyOrderVo;

    private SellApplyOrderVo sellApplyOrderVo;

    /**
     * 单据状态
     */
    private Byte orderStatus;

    private String targetId;

    private String targetName;

    private String targetPhone;

    private Integer warehouseId;

    private String warehouseName;

    /**
     * 数量
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer quantity;

    /**
     * 物流公司
     */
    private String logisticsCompany;

    /**
     * 运单号
     */
    private String waybillNumber;

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

    @JsonIgnore
    private Integer flag;

    public StorageOrderVo() {
    }

    public StorageOrderVo(Integer storeId, String id, String userId, String remark) {
        this.storeId = storeId;
        this.id = id;
        this.userId = userId;
        this.remark = remark;
    }

    public StorageOrderVo(Integer storeId, String id, Date startTime, Date endTime, String targetName, Integer warehouseId, Integer flag) {
        this.storeId = storeId;
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.targetName = targetName;
        this.warehouseId = warehouseId;
        this.flag = flag;
    }

    public StorageOrderVo(Integer storeId, Byte type, String applyOrderId, Byte orderStatus, Integer quantity) {
        this.storeId = storeId;
        this.type = type;
        this.applyOrderId = applyOrderId;
        this.orderStatus = orderStatus;
        this.quantity = quantity;
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

    public ProcurementApplyOrderVo getProcurementApplyOrderVo() {
        return procurementApplyOrderVo;
    }

    public void setProcurementApplyOrderVo(ProcurementApplyOrderVo procurementApplyOrderVo) {
        this.procurementApplyOrderVo = procurementApplyOrderVo;
    }

    public SellApplyOrderVo getSellApplyOrderVo() {
        return sellApplyOrderVo;
    }

    public void setSellApplyOrderVo(SellApplyOrderVo sellApplyOrderVo) {
        this.sellApplyOrderVo = sellApplyOrderVo;
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

    public String getTargetPhone() {
        return targetPhone;
    }

    public void setTargetPhone(String targetPhone) {
        this.targetPhone = targetPhone;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {
        this.waybillNumber = waybillNumber;
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
        return "StorageOrderVo{" +
                "storeId=" + storeId +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", procurementApplyOrderVo=" + procurementApplyOrderVo +
                ", sellApplyOrderVo=" + sellApplyOrderVo +
                ", orderStatus=" + orderStatus +
                ", targetId='" + targetId + '\'' +
                ", targetName='" + targetName + '\'' +
                ", targetPhone='" + targetPhone + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", quantity=" + quantity +
                ", logisticsCompany='" + logisticsCompany + '\'' +
                ", waybillNumber='" + waybillNumber + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", remark='" + remark + '\'' +
                ", flag=" + flag +
                '}';
    }
}