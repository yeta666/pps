package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class FundCheckOrderVo {

    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    /**
     * 资金对账记录编号
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
     * 银行账户编号
     */
    @JsonIgnore
    private String bankAccountId;

    private String bankAccount;

    /**
     * 收入金额
     */
    private Double inMoney;

    /**
     * 支出金额
     */
    private Double outMoney;

    /**
     * 当前余额
     */
    private Double balanceMoney;

    /**
     * 经手人编号
     */
    private String userId;

    private String userName;

    private String remark;

    public FundCheckOrderVo() {
    }

    public FundCheckOrderVo(Integer storeId, String orderId) {
        this.storeId = storeId;
        this.orderId = orderId;
    }

    public FundCheckOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String orderId, String userId) {
        this.storeId = storeId;
        this.orderId = orderId;
        this.userId = userId;
    }

    public FundCheckOrderVo(Integer storeId, Date startTime, Date endTime) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public FundCheckOrderVo(Integer storeId, Date startTime, Date endTime, String bankAccountId) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bankAccountId = bankAccountId;
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

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Double getInMoney() {
        return inMoney;
    }

    public void setInMoney(Double inMoney) {
        this.inMoney = inMoney;
    }

    public Double getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(Double outMoney) {
        this.outMoney = outMoney;
    }

    public Double getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(Double balanceMoney) {
        this.balanceMoney = balanceMoney;
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
        return "FundCheckOrderVo{" +
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
                ", bankAccountId=" + bankAccountId +
                ", bankAccount='" + bankAccount + '\'' +
                ", inMoney=" + inMoney +
                ", outMoney=" + outMoney +
                ", balanceMoney=" + balanceMoney +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}