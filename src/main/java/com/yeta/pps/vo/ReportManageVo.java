package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * @author YETA
 * @date 2019/01/07/13:40
 */
public class ReportManageVo {

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

    //销售经营分析

    /**
     * 销售数量
     */
    private Integer sellQuantity;

    /**
     * 销售收入
     */
    private Double sellProceedsMoney;

    /**
     * 销售成本
     */
    private Double sellCostMoney;

    /**
     * 毛利
     */
    private Double grossMarginMoney;

    /**
     * 毛利率
     */
    private Double grossMarginRate;

    //资金经营分析

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
     * 回款总额/入库金额
     */
    private Double totalInMoney;

    /**
     * 付款总额/出库金额
     */
    private Double totalOutMoney;

    //库存经营分析

    /**
     * 入库数量
     */
    private Integer totalInQuantity;

    /**
     * 出库数量
     */
    private Integer totalOutQuantity;

    //利润经营分析

    /**
     * 其他收入
     */
    private Double otherInMoney;

    /**
     * 其他费用
     */
    private Double otherOutMoney;

    /**
     * 净利润
     */
    private Double netMoney;

    //往来经营分析

    /**
     * 应收增加
     */
    private Double needInMoneyIncrease;

    /**
     * 应收减少
     */
    private Double needInMoneyDecrease;

    /**
     * 期末应收
     */
    private Double needInMoney;

    /**
     * 应付增加
     */
    private Double needOutMoneyIncrease;

    /**
     * 应付减少
     */
    private Double needOutMoneyDecrease;

    /**
     * 期付应付
     */
    private Double needOutMoney;

    //老板中心

    /**
     * 资金余额
     */
    private Double balanceMoney;

    public ReportManageVo() {
    }

    public ReportManageVo(Integer storeId, Date startTime, Date endTime) {
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

    public Integer getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(Integer sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public Double getSellProceedsMoney() {
        return sellProceedsMoney;
    }

    public void setSellProceedsMoney(Double sellProceedsMoney) {
        this.sellProceedsMoney = sellProceedsMoney;
    }

    public Double getSellCostMoney() {
        return sellCostMoney;
    }

    public void setSellCostMoney(Double sellCostMoney) {
        this.sellCostMoney = sellCostMoney;
    }

    public Double getGrossMarginMoney() {
        return grossMarginMoney;
    }

    public void setGrossMarginMoney(Double grossMarginMoney) {
        this.grossMarginMoney = grossMarginMoney;
    }

    public Double getGrossMarginRate() {
        return grossMarginRate;
    }

    public void setGrossMarginRate(Double grossMarginRate) {
        this.grossMarginRate = grossMarginRate;
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

    public Double getTotalInMoney() {
        return totalInMoney;
    }

    public void setTotalInMoney(Double totalInMoney) {
        this.totalInMoney = totalInMoney;
    }

    public Double getTotalOutMoney() {
        return totalOutMoney;
    }

    public void setTotalOutMoney(Double totalOutMoney) {
        this.totalOutMoney = totalOutMoney;
    }

    public Integer getTotalInQuantity() {
        return totalInQuantity;
    }

    public void setTotalInQuantity(Integer totalInQuantity) {
        this.totalInQuantity = totalInQuantity;
    }

    public Integer getTotalOutQuantity() {
        return totalOutQuantity;
    }

    public void setTotalOutQuantity(Integer totalOutQuantity) {
        this.totalOutQuantity = totalOutQuantity;
    }

    public Double getOtherInMoney() {
        return otherInMoney;
    }

    public void setOtherInMoney(Double otherInMoney) {
        this.otherInMoney = otherInMoney;
    }

    public Double getOtherOutMoney() {
        return otherOutMoney;
    }

    public void setOtherOutMoney(Double otherOutMoney) {
        this.otherOutMoney = otherOutMoney;
    }

    public Double getNetMoney() {
        return netMoney;
    }

    public void setNetMoney(Double netMoney) {
        this.netMoney = netMoney;
    }

    public Double getNeedInMoneyIncrease() {
        return needInMoneyIncrease;
    }

    public void setNeedInMoneyIncrease(Double needInMoneyIncrease) {
        this.needInMoneyIncrease = needInMoneyIncrease;
    }

    public Double getNeedInMoneyDecrease() {
        return needInMoneyDecrease;
    }

    public void setNeedInMoneyDecrease(Double needInMoneyDecrease) {
        this.needInMoneyDecrease = needInMoneyDecrease;
    }

    public Double getNeedInMoney() {
        return needInMoney;
    }

    public void setNeedInMoney(Double needInMoney) {
        this.needInMoney = needInMoney;
    }

    public Double getNeedOutMoneyIncrease() {
        return needOutMoneyIncrease;
    }

    public void setNeedOutMoneyIncrease(Double needOutMoneyIncrease) {
        this.needOutMoneyIncrease = needOutMoneyIncrease;
    }

    public Double getNeedOutMoneyDecrease() {
        return needOutMoneyDecrease;
    }

    public void setNeedOutMoneyDecrease(Double needOutMoneyDecrease) {
        this.needOutMoneyDecrease = needOutMoneyDecrease;
    }

    public Double getNeedOutMoney() {
        return needOutMoney;
    }

    public void setNeedOutMoney(Double needOutMoney) {
        this.needOutMoney = needOutMoney;
    }

    public Double getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(Double balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    @Override
    public String toString() {
        return "ReportManageVo{" +
                "storeId=" + storeId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", sellQuantity=" + sellQuantity +
                ", sellProceedsMoney=" + sellProceedsMoney +
                ", sellCostMoney=" + sellCostMoney +
                ", grossMarginMoney=" + grossMarginMoney +
                ", grossMarginRate=" + grossMarginRate +
                ", cashInMoney=" + cashInMoney +
                ", alipayInMoney=" + alipayInMoney +
                ", wechatInMoney=" + wechatInMoney +
                ", bankCardInMoney=" + bankCardInMoney +
                ", totalInMoney=" + totalInMoney +
                ", totalOutMoney=" + totalOutMoney +
                ", totalInQuantity=" + totalInQuantity +
                ", totalOutQuantity=" + totalOutQuantity +
                ", otherInMoney=" + otherInMoney +
                ", otherOutMoney=" + otherOutMoney +
                ", netMoney=" + netMoney +
                ", needInMoneyIncrease=" + needInMoneyIncrease +
                ", needInMoneyDecrease=" + needInMoneyDecrease +
                ", needInMoney=" + needInMoney +
                ", needOutMoneyIncrease=" + needOutMoneyIncrease +
                ", needOutMoneyDecrease=" + needOutMoneyDecrease +
                ", needOutMoney=" + needOutMoney +
                ", balanceMoney=" + balanceMoney +
                '}';
    }
}
