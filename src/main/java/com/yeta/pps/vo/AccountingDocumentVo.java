package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * @author YETA
 * @date 2019/01/21/15:37
 */
public class AccountingDocumentVo {

    /**
     * 店铺编号
     */
    private Integer storeId;

    /**
     * 单据编号
     */
    private String id;

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

    /**
     * 来源单据编号
     */
    private String orderId;

    /**
     * 科目编号
     */
    private String subjectId;

    private String subjectName;

    /**
     * 单据类型
     */
    private Byte type;

    private String typeName;

    /**
     * 核算单位编号
     */
    private String targetId;

    private String targetName;

    /**
     * 借方金额
     */
    private Double debitMoney;

    /**
     * 贷方金额
     */
    private Double creditMoney;

    /**
     * 经手人
     */
    private String userId;

    private String userName;

    public AccountingDocumentVo() {
    }

    public AccountingDocumentVo(Integer storeId, String orderId, String userId) {
        this.storeId = storeId;
        this.orderId = orderId;
        this.userId = userId;
    }

    public AccountingDocumentVo(Integer storeId, String id, Date startTime, Date endTime, String subjectId) {
        this.storeId = storeId;
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subjectId = subjectId;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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

    public Double getDebitMoney() {
        return debitMoney;
    }

    public void setDebitMoney(Double debitMoney) {
        this.debitMoney = debitMoney;
    }

    public Double getCreditMoney() {
        return creditMoney;
    }

    public void setCreditMoney(Double creditMoney) {
        this.creditMoney = creditMoney;
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

    @Override
    public String toString() {
        return "AccountingDocumentVo{" +
                "storeId=" + storeId +
                ", id='" + id + '\'' +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderId='" + orderId + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", type=" + type +
                ", typeName='" + typeName + '\'' +
                ", targetId='" + targetId + '\'' +
                ", targetName='" + targetName + '\'' +
                ", debitMoney=" + debitMoney +
                ", creditMoney=" + creditMoney +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
