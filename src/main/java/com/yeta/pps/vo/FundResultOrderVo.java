package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class FundResultOrderVo {

    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

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

    /**
     * 往来单位编号
     */
    private String targetId;

    private String targetName;

    /**
     * 银行账户编号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String bankAccountId;

    private String bankAccount;

    /**
     * 金额
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Double money;

    /**
     * 收支费用编号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String incomeExpensesId;

    private String incomeExpenses;

    /**
     * 经手人编号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String userId;

    private String userName;

    private String remark;

    public FundResultOrderVo() {
    }

    public FundResultOrderVo(Integer storeId, String id, String userId, String remark) {
        this.storeId = storeId;
        this.id = id;
        this.userId = userId;
        this.remark = remark;
    }

    public FundResultOrderVo(Integer storeId, String id,Byte type, Date startTime, Date endTime, String targetName, String bankAccountId, String incomeExpensesId) {
        this.storeId = storeId;
        this.id = id;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.targetName = targetName;
        this.bankAccountId = bankAccountId;
        this.incomeExpensesId = incomeExpensesId;
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getIncomeExpensesId() {
        return incomeExpensesId;
    }

    public void setIncomeExpensesId(String incomeExpensesId) {
        this.incomeExpensesId = incomeExpensesId;
    }

    public String getIncomeExpenses() {
        return incomeExpenses;
    }

    public void setIncomeExpenses(String incomeExpenses) {
        this.incomeExpenses = incomeExpenses;
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
        return "FundResultOrderVo{" +
                "storeId=" + storeId +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", orderStatus=" + orderStatus +
                ", targetId='" + targetId + '\'' +
                ", targetName='" + targetName + '\'' +
                ", bankAccountId='" + bankAccountId + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", money=" + money +
                ", incomeExpensesId='" + incomeExpensesId + '\'' +
                ", incomeExpenses='" + incomeExpenses + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}