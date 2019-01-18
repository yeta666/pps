package com.yeta.pps.po;

import java.util.Date;

public class SellApplyOrder {

    /**
     * 单据编号
     */
    private String id;

    /**
     * 单据类型，1：零售单，2：销售订单，3：销售退货申请单，4：销售换货申请单
     */
    private Byte type;

    /**
     * 单据日期
     */
    private Date createTime;

    /**
     * 来源订单，销售退货申请单和销售换货申请单应该有该字段
     */
    private String resultOrderId;

    /**
     * 产生方式，1：线下录单，2：线上下单
     */
    private Byte prodcingWay;

    /**
     * 单据状态，1：未收，2：部分收，3：已收，4：未发，5：部分发，6：已发，7：未收未发，8：未收部分发，9：未收已发，10：部分收未发，11：部分收部分发，12：部分收已发，13：已收未发，14：已收部分发：15：已收已发
     */
    private Byte orderStatus;

    /**
     * 结算状态：0：未完成，1：已完成
     */
    private Byte clearStatus;

    /**
     * 客户编号
     */
    private String clientId;

    /**
     * 入库仓库编号，对应收货
     */
    private Integer inWarehouseId;

    /**
     * 总收货数量
     */
    private Integer inTotalQuantity;

    /**
     * 已收货数量
     */
    private Integer inReceivedQuantity;

    /**
     * 未收货数量
     */
    private Integer inNotReceivedQuantity;

    /**
     * 出库仓库编号，对应发货
     */
    private Integer outWarehouseId;

    /**
     * 总发货数量
     */
    private Integer outTotalQuantity;

    /**
     * 已发货数量
     */
    private Integer outSentQuantity;

    /**
     * 未发货数量
     */
    private Integer outNotSentQuantity;

    /**
     * 总商品金额
     */
    private Double totalMoney;

    /**
     * 直接优惠金额
     */
    private Double discountMoney;

    /**
     * 优惠券编号
     */
    private Integer discountCouponId;

    /**
     * 总优惠金额
     */
    private Double totalDiscountMoney;

    /**
     * 本单金额
     */
    private Double orderMoney;

    /**
     * 已结算金额
     */
    private Double clearedMoney;

    /**
     * 未结算金额
     */
    private Double notClearedMoney;

    /**
     * 经手人编号
     */
    private String userId;

    /**
     * 单据备注
     */
    private String remark;

    /**
     * 零售单现金金额
     */
    private Double cashMoney;

    /**
     * 零售单支付宝金额
     */
    private Double alipayMoney;

    /**
     * 零售单微信金额
     */
    private Double wechatMoney;

    /**
     * 零售单银行卡金额
     */
    private Double bankCardMoney;

    /**
     * 零售单使用预收款金额
     */
    private Double advanceMoney;

    public SellApplyOrder() {
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

    public String getResultOrderId() {
        return resultOrderId;
    }

    public void setResultOrderId(String resultOrderId) {
        this.resultOrderId = resultOrderId;
    }

    public Byte getProdcingWay() {
        return prodcingWay;
    }

    public void setProdcingWay(Byte prodcingWay) {
        this.prodcingWay = prodcingWay;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Byte getClearStatus() {
        return clearStatus;
    }

    public void setClearStatus(Byte clearStatus) {
        this.clearStatus = clearStatus;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getInWarehouseId() {
        return inWarehouseId;
    }

    public void setInWarehouseId(Integer inWarehouseId) {
        this.inWarehouseId = inWarehouseId;
    }

    public Integer getInTotalQuantity() {
        return inTotalQuantity;
    }

    public void setInTotalQuantity(Integer inTotalQuantity) {
        this.inTotalQuantity = inTotalQuantity;
    }

    public Integer getInReceivedQuantity() {
        return inReceivedQuantity;
    }

    public void setInReceivedQuantity(Integer inReceivedQuantity) {
        this.inReceivedQuantity = inReceivedQuantity;
    }

    public Integer getInNotReceivedQuantity() {
        return inNotReceivedQuantity;
    }

    public void setInNotReceivedQuantity(Integer inNotReceivedQuantity) {
        this.inNotReceivedQuantity = inNotReceivedQuantity;
    }

    public Integer getOutWarehouseId() {
        return outWarehouseId;
    }

    public void setOutWarehouseId(Integer outWarehouseId) {
        this.outWarehouseId = outWarehouseId;
    }

    public Integer getOutTotalQuantity() {
        return outTotalQuantity;
    }

    public void setOutTotalQuantity(Integer outTotalQuantity) {
        this.outTotalQuantity = outTotalQuantity;
    }

    public Integer getOutSentQuantity() {
        return outSentQuantity;
    }

    public void setOutSentQuantity(Integer outSentQuantity) {
        this.outSentQuantity = outSentQuantity;
    }

    public Integer getOutNotSentQuantity() {
        return outNotSentQuantity;
    }

    public void setOutNotSentQuantity(Integer outNotSentQuantity) {
        this.outNotSentQuantity = outNotSentQuantity;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Double getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Double discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getDiscountCouponId() {
        return discountCouponId;
    }

    public void setDiscountCouponId(Integer discountCouponId) {
        this.discountCouponId = discountCouponId;
    }

    public Double getTotalDiscountMoney() {
        return totalDiscountMoney;
    }

    public void setTotalDiscountMoney(Double totalDiscountMoney) {
        this.totalDiscountMoney = totalDiscountMoney;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Double getClearedMoney() {
        return clearedMoney;
    }

    public void setClearedMoney(Double clearedMoney) {
        this.clearedMoney = clearedMoney;
    }

    public Double getNotClearedMoney() {
        return notClearedMoney;
    }

    public void setNotClearedMoney(Double notClearedMoney) {
        this.notClearedMoney = notClearedMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getCashMoney() {
        return cashMoney;
    }

    public void setCashMoney(Double cashMoney) {
        this.cashMoney = cashMoney;
    }

    public Double getAlipayMoney() {
        return alipayMoney;
    }

    public void setAlipayMoney(Double alipayMoney) {
        this.alipayMoney = alipayMoney;
    }

    public Double getWechatMoney() {
        return wechatMoney;
    }

    public void setWechatMoney(Double wechatMoney) {
        this.wechatMoney = wechatMoney;
    }

    public Double getBankCardMoney() {
        return bankCardMoney;
    }

    public void setBankCardMoney(Double bankCardMoney) {
        this.bankCardMoney = bankCardMoney;
    }

    public Double getAdvanceMoney() {
        return advanceMoney;
    }

    public void setAdvanceMoney(Double advanceMoney) {
        this.advanceMoney = advanceMoney;
    }

    @Override
    public String toString() {
        return "SellApplyOrder{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", resultOrderId='" + resultOrderId + '\'' +
                ", prodcingWay=" + prodcingWay +
                ", orderStatus=" + orderStatus +
                ", clearStatus=" + clearStatus +
                ", clientId='" + clientId + '\'' +
                ", inWarehouseId=" + inWarehouseId +
                ", inTotalQuantity=" + inTotalQuantity +
                ", inReceivedQuantity=" + inReceivedQuantity +
                ", inNotReceivedQuantity=" + inNotReceivedQuantity +
                ", outWarehouseId=" + outWarehouseId +
                ", outTotalQuantity=" + outTotalQuantity +
                ", outSentQuantity=" + outSentQuantity +
                ", outNotSentQuantity=" + outNotSentQuantity +
                ", totalMoney=" + totalMoney +
                ", discountMoney=" + discountMoney +
                ", discountCouponId=" + discountCouponId +
                ", totalDiscountMoney=" + totalDiscountMoney +
                ", orderMoney=" + orderMoney +
                ", clearedMoney=" + clearedMoney +
                ", notClearedMoney=" + notClearedMoney +
                ", userId='" + userId + '\'' +
                ", remark='" + remark + '\'' +
                ", cashMoney=" + cashMoney +
                ", alipayMoney=" + alipayMoney +
                ", wechatMoney=" + wechatMoney +
                ", bankCardMoney=" + bankCardMoney +
                ", advanceMoney=" + advanceMoney +
                '}';
    }
}