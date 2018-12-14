package com.yeta.pps.po;

import java.math.BigDecimal;
import java.util.Date;

public class SellResultOrder {

    /**
     * 单据编号
     */
    private String id;

    /**
     * 单据类型，1：零售单，2：销售出库单，3：销售退货单，4：销售换货单
     */
    private Byte type;

    /**
     * 单据日期
     */
    private Date createTime;

    /**
     * 来源订单
     */
    private String applyOrderId;

    /**
     * 单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲
     */
    private Byte orderStatus;

    /**
     * 总商品数量
     */
    private Integer totalQuantity;

    /**
     * 总商品金额
     */
    private BigDecimal totalMoney;

    /**
     * 总优惠金额
     */
    private BigDecimal totalDiscountMoney;

    /**
     * 本单金额
     */
    private BigDecimal orderMoney;

    /**
     * 成本
     */
    private BigDecimal costMoney;

    /**
     * 毛利
     */
    private BigDecimal grossMarginMoney;

    /**
     * 经手人
     */
    private String userId;

    /**
     * 单据备注
     */
    private String remark;

    public SellResultOrder() {
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
     * 获取单据类型，1：零售单，2：销售出库单，3：销售退货单，4：销售换货单
     *
     * @return type - 单据类型，1：零售单，2：销售出库单，3：销售退货单，4：销售换货单
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置单据类型，1：零售单，2：销售出库单，3：销售退货单，4：销售换货单
     *
     * @param type 单据类型，1：零售单，2：销售出库单，3：销售退货单，4：销售换货单
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
     * 获取来源订单
     *
     * @return apply_order_id - 来源订单
     */
    public String getApplyOrderId() {
        return applyOrderId;
    }

    /**
     * 设置来源订单
     *
     * @param applyOrderId 来源订单
     */
    public void setApplyOrderId(String applyOrderId) {
        this.applyOrderId = applyOrderId;
    }

    /**
     * 获取单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲
     *
     * @return order_status - 单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲
     */
    public Byte getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲
     *
     * @param orderStatus 单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲
     */
    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取总商品数量
     *
     * @return total_quantity - 总商品数量
     */
    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * 设置总商品数量
     *
     * @param totalQuantity 总商品数量
     */
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
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
     * 获取成本
     *
     * @return cost_money - 成本
     */
    public BigDecimal getCostMoney() {
        return costMoney;
    }

    /**
     * 设置成本
     *
     * @param costMoney 成本
     */
    public void setCostMoney(BigDecimal costMoney) {
        this.costMoney = costMoney;
    }

    /**
     * 获取毛利
     *
     * @return gross_margin_money - 毛利
     */
    public BigDecimal getGrossMarginMoney() {
        return grossMarginMoney;
    }

    /**
     * 设置毛利
     *
     * @param grossMarginMoney 毛利
     */
    public void setGrossMarginMoney(BigDecimal grossMarginMoney) {
        this.grossMarginMoney = grossMarginMoney;
    }

    /**
     * 获取经手人
     *
     * @return user_id - 经手人
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置经手人
     *
     * @param userId 经手人
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
        return "SellResultOrder{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", orderStatus=" + orderStatus +
                ", totalQuantity=" + totalQuantity +
                ", totalMoney=" + totalMoney +
                ", totalDiscountMoney=" + totalDiscountMoney +
                ", orderMoney=" + orderMoney +
                ", costMoney=" + costMoney +
                ", grossMarginMoney=" + grossMarginMoney +
                ", userId='" + userId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}