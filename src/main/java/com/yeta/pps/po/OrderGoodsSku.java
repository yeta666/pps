package com.yeta.pps.po;

import java.math.BigDecimal;

public class OrderGoodsSku {

    /**
     * 采购申请订单/商品规格关系编号
     */
    private Integer id;

    /**
     * 入库或出库，1：入库，0：出库
     */
    private Byte type;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 商品规格编号
     */
    private Integer goodsSkuId;

    /**
     * 总数量
     */
    private Integer quantity;

    /**
     * 完成数量
     */
    private Integer finishQuantity;

    /**
     * 未完成数量
     */
    private Integer notFinishQuantity;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 优惠金额
     */
    private BigDecimal discountMoney;

    /**
     * 已操作数量
     */
    private Integer operatedQuantity;

    /**
     * 备注
     */
    private String remark;

    public OrderGoodsSku() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getFinishQuantity() {
        return finishQuantity;
    }

    public void setFinishQuantity(Integer finishQuantity) {
        this.finishQuantity = finishQuantity;
    }

    public Integer getNotFinishQuantity() {
        return notFinishQuantity;
    }

    public void setNotFinishQuantity(Integer notFinishQuantity) {
        this.notFinishQuantity = notFinishQuantity;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getOperatedQuantity() {
        return operatedQuantity;
    }

    public void setOperatedQuantity(Integer operatedQuantity) {
        this.operatedQuantity = operatedQuantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OrderGoodsSku{" +
                "id=" + id +
                ", type=" + type +
                ", orderId='" + orderId + '\'' +
                ", goodsSkuId=" + goodsSkuId +
                ", quantity=" + quantity +
                ", finishQuantity=" + finishQuantity +
                ", notFinishQuantity=" + notFinishQuantity +
                ", money=" + money +
                ", discountMoney=" + discountMoney +
                ", operatedQuantity=" + operatedQuantity +
                ", remark='" + remark + '\'' +
                '}';
    }
}