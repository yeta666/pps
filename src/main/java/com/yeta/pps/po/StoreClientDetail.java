package com.yeta.pps.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class StoreClientDetail {

    /**
     * 店铺/客户明细关系编号
     */
    private Integer id;

    /**
     * 店铺编号
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    /**
     * 客户编号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String clientId;

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
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Byte type;

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

    /**
     * 经手人
     */
    private String userId;

    /**
     * 提现方式
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Byte withdrawalWay;

    /**
     * 备注
     */
    private String remark;

    /**
     * 银行账户编号
     */
    private String bankAccountId;

    public StoreClientDetail() {
    }

    public StoreClientDetail(String clientId) {
        this.clientId = clientId;
    }

    public StoreClientDetail(Integer storeId, String clientId, Date createTime, Date updateTime, Byte type, Integer changeIntegral, String orderId, Byte status, String userId) {
        this.storeId = storeId;
        this.clientId = clientId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.type = type;
        this.changeIntegral = changeIntegral;
        this.orderId = orderId;
        this.status = status;
        this.userId = userId;
    }

    public StoreClientDetail(Integer storeId, String clientId, Date createTime, Date updateTime, Byte type, Double changePushMoney, String orderId, Byte status, String userId) {
        this.storeId = storeId;
        this.clientId = clientId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.type = type;
        this.changePushMoney = changePushMoney;
        this.orderId = orderId;
        this.status = status;
        this.userId = userId;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Byte getWithdrawalWay() {
        return withdrawalWay;
    }

    public void setWithdrawalWay(Byte withdrawalWay) {
        this.withdrawalWay = withdrawalWay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    @Override
    public String toString() {
        return "StoreClientDetail{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", clientId='" + clientId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", changeIntegral=" + changeIntegral +
                ", changePushMoney=" + changePushMoney +
                ", orderId='" + orderId + '\'' +
                ", status=" + status +
                ", userId='" + userId + '\'' +
                ", withdrawalWay=" + withdrawalWay +
                ", remark='" + remark + '\'' +
                ", bankAccountId='" + bankAccountId + '\'' +
                '}';
    }
}