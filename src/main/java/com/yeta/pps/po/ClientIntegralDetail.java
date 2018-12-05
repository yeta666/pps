package com.yeta.pps.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

public class ClientIntegralDetail {

    /**
     * 积分明细id
     */
    private String id;

    /**
     * 客户编号
     */
    private String clientId;

    /**
     * 发生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 操作类型，1：后台增加，3：消费增加，4：提成增加，5：后台减少，6：提现减少
     */
    private Byte type;

    /**
     * 改变积分
     */
    private Integer changeIntegral;

    /**
     * 改变后的积分
     */
    private Integer afterChangeIntegral;

    /**
     * 单据日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date invoicesDate;

    /**
     * 单据编号
     */
    private String invoicesId;

    /**
     * 单据类型，？？
     */
    private Byte invoicesType;

    /**
     * 经手人
     */
    private String handledBy;

    /**
     * 备注
     */
    private String remark;

    public ClientIntegralDetail() {
    }

    public ClientIntegralDetail(Byte type, String invoicesId) {
        this.type = type;
        this.invoicesId = invoicesId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getAfterChangeIntegral() {
        return afterChangeIntegral;
    }

    public void setAfterChangeIntegral(Integer afterChangeIntegral) {
        this.afterChangeIntegral = afterChangeIntegral;
    }

    public Date getInvoicesDate() {
        return invoicesDate;
    }

    public void setInvoicesDate(Date invoicesDate) {
        this.invoicesDate = invoicesDate;
    }

    public String getInvoicesId() {
        return invoicesId;
    }

    public void setInvoicesId(String invoicesId) {
        this.invoicesId = invoicesId;
    }

    public Byte getInvoicesType() {
        return invoicesType;
    }

    public void setInvoicesType(Byte invoicesType) {
        this.invoicesType = invoicesType;
    }

    public String getHandledBy() {
        return handledBy;
    }

    public void setHandledBy(String handledBy) {
        this.handledBy = handledBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ClientIntegralDetail{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", createTime=" + createTime +
                ", type=" + type +
                ", changeIntegral=" + changeIntegral +
                ", afterChangeIntegral=" + afterChangeIntegral +
                ", invoicesDate=" + invoicesDate +
                ", invoicesId='" + invoicesId + '\'' +
                ", invoicesType=" + invoicesType +
                ", handledBy='" + handledBy + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}