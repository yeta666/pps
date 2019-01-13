package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * @author YETA
 * @date 2019/01/07/13:40
 */
public class ReportSellVo {

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

    private String createMonth;

    /**
     * 单据编号
     */
    private String orderId;

    /**
     * 单据类型
     */
    private Byte orderType;

    /**
     * 单据类型名称
     */
    private String orderTypeName;

    /**
     * 客户编号
     */
    private String clientId;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 经手人编号
     */
    private String userId;

    /**
     * 经手人名称
     */
    private String userName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 商品货号
     */
    private String goodsId;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 商品条码
     */
    private String goodsBarCode;

    /**
     * 商品分类编号
     */
    private Integer goodsTypeId;

    /**
     * 商品分类
     */
    private String goodsTypeName;

    /**
     * 商品图片
     */
    private String goodsImage;

    //款项

    /**
     * 销售回款
     */
    private Double sellInMoney;

    /**
     * 新增应收款
     */
    private Double addNeedInMoney;

    /**
     * 预收收款
     */
    private Double advanceMoney;

    /**
     * 使用预收款
     */
    private Double advanceInMoney;

    //销售

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

    //退货

    /**
     * 销售数量
     */
    private Integer sellQuantity;

    /**
     * 销售出库数量
     */
    private Integer sellOutQuantity;

    /**
     * 退货数量
     */
    private Integer returnQuantity;

    /**
     * 退货金额
     */
    private Double returnMoney;

    /**
     * 退货率
     */
    private Double returnRate;

    //订单

    /**
     * 销售单数量
     */
    private Integer sellOrderQuantity;

    /**
     * 退货单数量
     */
    private Integer returnOrderQuantity;

    /**
     * 换货单数量
     */
    private Integer exchangeOrderQuantity;

    //客户销售分析-按客户特有

    /**
     * 其他收入
     */
    private Double otherInMoney;

    /**
     * 优惠
     */
    private Double discountMoney;

    /**
     * 营业业绩
     */
    private Double sellPerformance;

    /**
     * 营业利润
     */
    private Double sellProfit;

    //回款

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

    public ReportSellVo() {
    }

    public ReportSellVo(Integer storeId, Date createTime) {
        this.storeId = storeId;
        this.createTime = createTime;
    }

    public ReportSellVo(Integer storeId, Date startTime, Date endTime) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ReportSellVo(Integer storeId, Date startTime, Date endTime, String goodsId, String goodsName, String goodsBarCode, Integer goodsTypeId) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsBarCode = goodsBarCode;
        this.goodsTypeId = goodsTypeId;
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

    public String getCreateMonth() {
        return createMonth;
    }

    public void setCreateMonth(String createMonth) {
        this.createMonth = createMonth;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
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

    public Double getSellInMoney() {
        return sellInMoney;
    }

    public void setSellInMoney(Double sellInMoney) {
        this.sellInMoney = sellInMoney;
    }

    public Double getAdvanceInMoney() {
        return advanceInMoney;
    }

    public void setAdvanceInMoney(Double advanceInMoney) {
        this.advanceInMoney = advanceInMoney;
    }

    public Double getAddNeedInMoney() {
        return addNeedInMoney;
    }

    public void setAddNeedInMoney(Double addNeedInMoney) {
        this.addNeedInMoney = addNeedInMoney;
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

    public Integer getSellOrderQuantity() {
        return sellOrderQuantity;
    }

    public void setSellOrderQuantity(Integer sellOrderQuantity) {
        this.sellOrderQuantity = sellOrderQuantity;
    }

    public Integer getReturnOrderQuantity() {
        return returnOrderQuantity;
    }

    public void setReturnOrderQuantity(Integer returnOrderQuantity) {
        this.returnOrderQuantity = returnOrderQuantity;
    }

    public Integer getExchangeOrderQuantity() {
        return exchangeOrderQuantity;
    }

    public void setExchangeOrderQuantity(Integer exchangeOrderQuantity) {
        this.exchangeOrderQuantity = exchangeOrderQuantity;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public Integer getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(Integer sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public Integer getSellOutQuantity() {
        return sellOutQuantity;
    }

    public void setSellOutQuantity(Integer sellOutQuantity) {
        this.sellOutQuantity = sellOutQuantity;
    }

    public Integer getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Integer returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public Double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Double getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(Double returnRate) {
        this.returnRate = returnRate;
    }

    public Double getAdvanceMoney() {
        return advanceMoney;
    }

    public void setAdvanceMoney(Double advanceMoney) {
        this.advanceMoney = advanceMoney;
    }

    public Double getOtherInMoney() {
        return otherInMoney;
    }

    public void setOtherInMoney(Double otherInMoney) {
        this.otherInMoney = otherInMoney;
    }

    public Double getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Double discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Double getSellPerformance() {
        return sellPerformance;
    }

    public void setSellPerformance(Double sellPerformance) {
        this.sellPerformance = sellPerformance;
    }

    public Double getSellProfit() {
        return sellProfit;
    }

    public void setSellProfit(Double sellProfit) {
        this.sellProfit = sellProfit;
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

    @Override
    public String toString() {
        return "ReportSellVo{" +
                "storeId=" + storeId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", createMonth='" + createMonth + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderType=" + orderType +
                ", orderTypeName='" + orderTypeName + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", remark='" + remark + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsBarCode='" + goodsBarCode + '\'' +
                ", goodsTypeId=" + goodsTypeId +
                ", goodsTypeName='" + goodsTypeName + '\'' +
                ", goodsImage='" + goodsImage + '\'' +
                ", sellInMoney=" + sellInMoney +
                ", addNeedInMoney=" + addNeedInMoney +
                ", advanceMoney=" + advanceMoney +
                ", advanceInMoney=" + advanceInMoney +
                ", sellProceedsMoney=" + sellProceedsMoney +
                ", sellCostMoney=" + sellCostMoney +
                ", grossMarginMoney=" + grossMarginMoney +
                ", grossMarginRate=" + grossMarginRate +
                ", sellQuantity=" + sellQuantity +
                ", sellOutQuantity=" + sellOutQuantity +
                ", returnQuantity=" + returnQuantity +
                ", returnMoney=" + returnMoney +
                ", returnRate=" + returnRate +
                ", sellOrderQuantity=" + sellOrderQuantity +
                ", returnOrderQuantity=" + returnOrderQuantity +
                ", exchangeOrderQuantity=" + exchangeOrderQuantity +
                ", otherInMoney=" + otherInMoney +
                ", discountMoney=" + discountMoney +
                ", sellPerformance=" + sellPerformance +
                ", sellProfit=" + sellProfit +
                ", orderQuantity=" + orderQuantity +
                ", totalInMoney=" + totalInMoney +
                ", cashInMoney=" + cashInMoney +
                ", alipayInMoney=" + alipayInMoney +
                ", wechatInMoney=" + wechatInMoney +
                ", bankCardInMoney=" + bankCardInMoney +
                '}';
    }
}
