package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * @author YETA
 * @date 2019/01/07/13:40
 */
public class ReportFundVo {

    /**
     * 店铺编号
     */
    @JsonIgnore
    private Integer storeId;

    /**
     * 开始时间
     */
    @JsonIgnore
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonIgnore
    private Date endTime;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 单量
     */
    private Integer orderQuantity;

    /**
     * 回款总额
     */
    private Double totalInMoney;

    /**
     * 现金回款
     */
    private Double cashInMoney;

    /**
     * 支付宝回款
     */
    private Double alipayInMoney;

    /**
     * 微信回款
     */
    private Double wechatInMoney;

    /**
     * 银行卡回款
     */
    private Double bankCardInMoney;

    /**
     * 职员编号
     */
    private String userId;

    /**
     * 职员名称
     */
    private String userName;

    /**
     * 费用编号
     */
    private String expensesId;

    /**
     * 费用名称
     */
    private String expensesName;

    /**
     * 费用金额
     */
    private Double totalOutMoney;

    /**
     * 占比
     */
    private Double proportion;

    /**
     * 单位编号
     */
    private String targetId;

    /**
     * 单位名称
     */
    private String targetName;

    private Integer flag;

    public ReportFundVo() {
    }

    public ReportFundVo(Integer storeId, Date startTime, Date endTime) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Double getTotalInMoney() {
        return totalInMoney;
    }

    public void setTotalInMoney(Double totalInMoney) {
        this.totalInMoney = totalInMoney;
    }

    public Double getCashInMoney() {
        return cashInMoney;
    }

    public void setCashInMoney(Double cashInMoney) {
        this.cashInMoney = cashInMoney;
    }

    public Double getAlipayInMoney() {
        return alipayInMoney;
    }

    public void setAlipayInMoney(Double alipayInMoney) {
        this.alipayInMoney = alipayInMoney;
    }

    public Double getWechatInMoney() {
        return wechatInMoney;
    }

    public void setWechatInMoney(Double wechatInMoney) {
        this.wechatInMoney = wechatInMoney;
    }

    public Double getBankCardInMoney() {
        return bankCardInMoney;
    }

    public void setBankCardInMoney(Double bankCardInMoney) {
        this.bankCardInMoney = bankCardInMoney;
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

    public String getExpensesId() {
        return expensesId;
    }

    public void setExpensesId(String expensesId) {
        this.expensesId = expensesId;
    }

    public String getExpensesName() {
        return expensesName;
    }

    public void setExpensesName(String expensesName) {
        this.expensesName = expensesName;
    }

    public Double getTotalOutMoney() {
        return totalOutMoney;
    }

    public void setTotalOutMoney(Double totalOutMoney) {
        this.totalOutMoney = totalOutMoney;
    }

    public Double getProportion() {
        return proportion;
    }

    public void setProportion(Double proportion) {
        this.proportion = proportion;
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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "ReportFundVo{" +
                "storeId=" + storeId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", orderQuantity=" + orderQuantity +
                ", totalInMoney=" + totalInMoney +
                ", cashInMoney=" + cashInMoney +
                ", alipayInMoney=" + alipayInMoney +
                ", wechatInMoney=" + wechatInMoney +
                ", bankCardInMoney=" + bankCardInMoney +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", expensesId='" + expensesId + '\'' +
                ", expensesName='" + expensesName + '\'' +
                ", totalOutMoney=" + totalOutMoney +
                ", proportion=" + proportion +
                ", targetId='" + targetId + '\'' +
                ", targetName='" + targetName + '\'' +
                ", flag=" + flag +
                '}';
    }
}
