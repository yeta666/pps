package com.yeta.pps.po;

import java.math.BigDecimal;
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
    private BigDecimal totalMoney;

    /**
     * 直接优惠金额
     */
    private BigDecimal discountMoney;

    /**
     * 优惠券编号
     */
    private Integer discountCouponId;

    /**
     * 总优惠金额
     */
    private BigDecimal totalDiscountMoney;

    /**
     * 本单金额
     */
    private BigDecimal orderMoney;

    /**
     * 已结算金额
     */
    private BigDecimal clearedMoney;

    /**
     * 未结算金额
     */
    private BigDecimal notClearedMoney;

    /**
     * 经手人编号
     */
    private String userId;

    /**
     * 单据备注
     */
    private String remark;

    public SellApplyOrder() {
    }

    /**
     * 获取单据编号
     *
     * @return id - 单据编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置单据编号
     *
     * @param id 单据编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取单据类型，1：零售单，2：销售订单，3：销售退货申请单，4：销售换货申请单
     *
     * @return type - 单据类型，1：零售单，2：销售订单，3：销售退货申请单，4：销售换货申请单
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置单据类型，1：零售单，2：销售订单，3：销售退货申请单，4：销售换货申请单
     *
     * @param type 单据类型，1：零售单，2：销售订单，3：销售退货申请单，4：销售换货申请单
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取单据日期
     *
     * @return create_time - 单据日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置单据日期
     *
     * @param createTime 单据日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取来源订单，销售退货申请单和销售换货申请单应该有该字段
     *
     * @return result_order_id - 来源订单，销售退货申请单和销售换货申请单应该有该字段
     */
    public String getResultOrderId() {
        return resultOrderId;
    }

    /**
     * 设置来源订单，销售退货申请单和销售换货申请单应该有该字段
     *
     * @param resultOrderId 来源订单，销售退货申请单和销售换货申请单应该有该字段
     */
    public void setResultOrderId(String resultOrderId) {
        this.resultOrderId = resultOrderId;
    }

    /**
     * 获取产生方式，1：线下录单，2：线上下单
     *
     * @return prodcing_way - 产生方式，1：线下录单，2：线上下单
     */
    public Byte getProdcingWay() {
        return prodcingWay;
    }

    /**
     * 设置产生方式，1：线下录单，2：线上下单
     *
     * @param prodcingWay 产生方式，1：线下录单，2：线上下单
     */
    public void setProdcingWay(Byte prodcingWay) {
        this.prodcingWay = prodcingWay;
    }

    /**
     * 获取单据状态，1：未收，2：部分收，3：已收，4：未发，5：部分发，6：已发，7：未收未发，8：未收部分发，9：未收已发，10：部分收未发，11：部分收部分发，12：部分收已发，13：已收未发，14：已收部分发：15：已收已发
     *
     * @return order_status - 单据状态，1：未收，2：部分收，3：已收，4：未发，5：部分发，6：已发，7：未收未发，8：未收部分发，9：未收已发，10：部分收未发，11：部分收部分发，12：部分收已发，13：已收未发，14：已收部分发：15：已收已发
     */
    public Byte getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置单据状态，1：未收，2：部分收，3：已收，4：未发，5：部分发，6：已发，7：未收未发，8：未收部分发，9：未收已发，10：部分收未发，11：部分收部分发，12：部分收已发，13：已收未发，14：已收部分发：15：已收已发
     *
     * @param orderStatus 单据状态，1：未收，2：部分收，3：已收，4：未发，5：部分发，6：已发，7：未收未发，8：未收部分发，9：未收已发，10：部分收未发，11：部分收部分发，12：部分收已发，13：已收未发，14：已收部分发：15：已收已发
     */
    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取结算状态：0：未完成，1：已完成
     *
     * @return clear_status - 结算状态：0：未完成，1：已完成
     */
    public Byte getClearStatus() {
        return clearStatus;
    }

    /**
     * 设置结算状态：0：未完成，1：已完成
     *
     * @param clearStatus 结算状态：0：未完成，1：已完成
     */
    public void setClearStatus(Byte clearStatus) {
        this.clearStatus = clearStatus;
    }

    /**
     * 获取客户编号
     *
     * @return client_id - 客户编号
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 设置客户编号
     *
     * @param clientId 客户编号
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 获取入库仓库编号，对应收货
     *
     * @return in_warehouse_id - 入库仓库编号，对应收货
     */
    public Integer getInWarehouseId() {
        return inWarehouseId;
    }

    /**
     * 设置入库仓库编号，对应收货
     *
     * @param inWarehouseId 入库仓库编号，对应收货
     */
    public void setInWarehouseId(Integer inWarehouseId) {
        this.inWarehouseId = inWarehouseId;
    }

    /**
     * 获取总收货数量
     *
     * @return in_total_quantity - 总收货数量
     */
    public Integer getInTotalQuantity() {
        return inTotalQuantity;
    }

    /**
     * 设置总收货数量
     *
     * @param inTotalQuantity 总收货数量
     */
    public void setInTotalQuantity(Integer inTotalQuantity) {
        this.inTotalQuantity = inTotalQuantity;
    }

    /**
     * 获取已收货数量
     *
     * @return in_received_quantity - 已收货数量
     */
    public Integer getInReceivedQuantity() {
        return inReceivedQuantity;
    }

    /**
     * 设置已收货数量
     *
     * @param inReceivedQuantity 已收货数量
     */
    public void setInReceivedQuantity(Integer inReceivedQuantity) {
        this.inReceivedQuantity = inReceivedQuantity;
    }

    /**
     * 获取未收货数量
     *
     * @return in_not_received_quantity - 未收货数量
     */
    public Integer getInNotReceivedQuantity() {
        return inNotReceivedQuantity;
    }

    /**
     * 设置未收货数量
     *
     * @param inNotReceivedQuantity 未收货数量
     */
    public void setInNotReceivedQuantity(Integer inNotReceivedQuantity) {
        this.inNotReceivedQuantity = inNotReceivedQuantity;
    }

    /**
     * 获取出库仓库编号，对应发货
     *
     * @return out_warehouse_id - 出库仓库编号，对应发货
     */
    public Integer getOutWarehouseId() {
        return outWarehouseId;
    }

    /**
     * 设置出库仓库编号，对应发货
     *
     * @param outWarehouseId 出库仓库编号，对应发货
     */
    public void setOutWarehouseId(Integer outWarehouseId) {
        this.outWarehouseId = outWarehouseId;
    }

    /**
     * 获取总发货数量
     *
     * @return out_total_quantity - 总发货数量
     */
    public Integer getOutTotalQuantity() {
        return outTotalQuantity;
    }

    /**
     * 设置总发货数量
     *
     * @param outTotalQuantity 总发货数量
     */
    public void setOutTotalQuantity(Integer outTotalQuantity) {
        this.outTotalQuantity = outTotalQuantity;
    }

    /**
     * 获取已发货数量
     *
     * @return out_sent_quantity - 已发货数量
     */
    public Integer getOutSentQuantity() {
        return outSentQuantity;
    }

    /**
     * 设置已发货数量
     *
     * @param outSentQuantity 已发货数量
     */
    public void setOutSentQuantity(Integer outSentQuantity) {
        this.outSentQuantity = outSentQuantity;
    }

    /**
     * 获取未发货数量
     *
     * @return out_not_sent_quantity - 未发货数量
     */
    public Integer getOutNotSentQuantity() {
        return outNotSentQuantity;
    }

    /**
     * 设置未发货数量
     *
     * @param outNotSentQuantity 未发货数量
     */
    public void setOutNotSentQuantity(Integer outNotSentQuantity) {
        this.outNotSentQuantity = outNotSentQuantity;
    }

    /**
     * 获取总商品金额
     *
     * @return total_money - 总商品金额
     */
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    /**
     * 设置总商品金额
     *
     * @param totalMoney 总商品金额
     */
    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    /**
     * 获取直接优惠金额
     *
     * @return discount_money - 直接优惠金额
     */
    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    /**
     * 设置直接优惠金额
     *
     * @param discountMoney 直接优惠金额
     */
    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    /**
     * 获取优惠券编号
     *
     * @return discount_coupon_id - 优惠券编号
     */
    public Integer getDiscountCouponId() {
        return discountCouponId;
    }

    /**
     * 设置优惠券编号
     *
     * @param discountCouponId 优惠券编号
     */
    public void setDiscountCouponId(Integer discountCouponId) {
        this.discountCouponId = discountCouponId;
    }

    /**
     * 获取总优惠金额
     *
     * @return total_discount_money - 总优惠金额
     */
    public BigDecimal getTotalDiscountMoney() {
        return totalDiscountMoney;
    }

    /**
     * 设置总优惠金额
     *
     * @param totalDiscountMoney 总优惠金额
     */
    public void setTotalDiscountMoney(BigDecimal totalDiscountMoney) {
        this.totalDiscountMoney = totalDiscountMoney;
    }

    /**
     * 获取本单金额
     *
     * @return order_money - 本单金额
     */
    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    /**
     * 设置本单金额
     *
     * @param orderMoney 本单金额
     */
    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    /**
     * 获取已结算金额
     *
     * @return cleared_money - 已结算金额
     */
    public BigDecimal getClearedMoney() {
        return clearedMoney;
    }

    /**
     * 设置已结算金额
     *
     * @param clearedMoney 已结算金额
     */
    public void setClearedMoney(BigDecimal clearedMoney) {
        this.clearedMoney = clearedMoney;
    }

    /**
     * 获取未结算金额
     *
     * @return not_cleared_money - 未结算金额
     */
    public BigDecimal getNotClearedMoney() {
        return notClearedMoney;
    }

    /**
     * 设置未结算金额
     *
     * @param notClearedMoney 未结算金额
     */
    public void setNotClearedMoney(BigDecimal notClearedMoney) {
        this.notClearedMoney = notClearedMoney;
    }

    /**
     * 获取经手人编号
     *
     * @return user_id - 经手人编号
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置经手人编号
     *
     * @param userId 经手人编号
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取单据备注
     *
     * @return remark - 单据备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置单据备注
     *
     * @param remark 单据备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
                '}';
    }
}