package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class StoreClientDetailVo {

    /**
     * 店铺/客户明细关系编号
     */
    private Integer id;

    /**
     * 店铺编号
     */
    private Integer storeId;

    private String storeName;

    /**
     * 客户编号
     */
    private String clientId;

    private String clientName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 类型
     */
    private Byte type;

    private String typeName;

    /**
     * 改变积分
     */
    private Integer changeIntegral;

    /**
     * 改变提成
     */
    private Double changePushMoney;

    /**
     * 单据编号
     */
    private String orderId;

    /**
     * 状态
     */
    private Byte status;

    private String statusName;

    /**
     * 经手人
     */
    private String userId;

    private String userName;

    /**
     * 提现方式
     */
    private Byte withdrawalWay;

    private String withdrawalWayName;

    /**
     * 备注
     */
    private String remark;

    public StoreClientDetailVo() {
    }

    public StoreClientDetailVo(Integer storeId, String clientId, String clientName, Byte type, Byte status) {
        this.storeId = storeId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.type = type;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Integer getChangeIntegral() {
        return changeIntegral;
    }

    public void setChangeIntegral(Integer changeIntegral) {
        this.changeIntegral = changeIntegral;
    }

    public Double getChangePushMoney() {
        return changePushMoney;
    }

    public void setChangePushMoney(Double changePushMoney) {
        this.changePushMoney = changePushMoney;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    public Byte getWithdrawalWay() {
        return withdrawalWay;
    }

    public void setWithdrawalWay(Byte withdrawalWay) {
        this.withdrawalWay = withdrawalWay;
    }

    public String getWithdrawalWayName() {
        return withdrawalWayName;
    }

    public void setWithdrawalWayName(String withdrawalWayName) {
        this.withdrawalWayName = withdrawalWayName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "StoreClientDetailVo{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", typeName='" + typeName + '\'' +
                ", changeIntegral=" + changeIntegral +
                ", changePushMoney=" + changePushMoney +
                ", orderId='" + orderId + '\'' +
                ", status=" + status +
                ", statusName='" + statusName + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", withdrawalWay=" + withdrawalWay +
                ", withdrawalWayName='" + withdrawalWayName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}