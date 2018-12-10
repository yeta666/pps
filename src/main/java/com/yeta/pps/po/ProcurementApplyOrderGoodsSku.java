package com.yeta.pps.po;

import java.math.BigDecimal;

public class ProcurementApplyOrderGoodsSku {

    /**
     * 采购申请订单/商品规格关系编号
     */
    private Integer id;

    /**
     * 入库或出库，1：入库，0：出库
     */
    private Byte type;

    /**
     * 采购申请订单编号
     */
    private String applyOrderId;

    /**
     * 商品规格编号
     */
    private Integer goodsSkuId;

    /**
     * 采购数量
     */
    private Integer quantity;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 优惠金额
     */
    private BigDecimal discountMoney;

    /**
     * 备注
     */
    private String remark;

    public ProcurementApplyOrderGoodsSku() {
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

    public String getApplyOrderId() {
        return applyOrderId;
    }

    public void setApplyOrderId(String applyOrderId) {
        this.applyOrderId = applyOrderId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ProcurementApplyOrderGoodsSku{" +
                "id=" + id +
                ", type=" + type +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", goodsSkuId=" + goodsSkuId +
                ", quantity=" + quantity +
                ", money=" + money +
                ", discountMoney=" + discountMoney +
                ", remark='" + remark + '\'' +
                '}';
    }
}