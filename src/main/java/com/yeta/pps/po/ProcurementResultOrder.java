package com.yeta.pps.po;

import java.math.BigDecimal;
import java.util.Date;

public class ProcurementResultOrder {

    /**
     * 单据编号
     */
    private String id;

    /**
     * 单据类型，1：采购入库单，2：采购退货单，3,：采购换货单
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
     * 单据状态
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
     * 经手人
     */
    private String userId;

    /**
     * 单据备注
     */
    private String remark;

    public ProcurementResultOrder() {
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

    public String getApplyOrderId() {
        return applyOrderId;
    }

    public void setApplyOrderId(String applyOrderId) {
        this.applyOrderId = applyOrderId;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getTotalDiscountMoney() {
        return totalDiscountMoney;
    }

    public void setTotalDiscountMoney(BigDecimal totalDiscountMoney) {
        this.totalDiscountMoney = totalDiscountMoney;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
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

    @Override
    public String toString() {
        return "ProcurementResultOrder{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", orderStatus=" + orderStatus +
                ", totalQuantity=" + totalQuantity +
                ", totalMoney=" + totalMoney +
                ", totalDiscountMoney=" + totalDiscountMoney +
                ", orderMoney=" + orderMoney +
                ", userId='" + userId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}