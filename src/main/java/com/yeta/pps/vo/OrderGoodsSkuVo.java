package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OrderGoodsSkuVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    private Integer changeQuantity;

    private String goodsName;

    private String goodsId;

    private String goodsBarCode;

    private String goodsSkuSku;

    private BigDecimal goodsSkuPurchasePrice;

    private BigDecimal goodsSkuRetailPrice;

    private BigDecimal goodsSkuVipPrice;

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
    private Integer operatedQuantity = 0;

    /**
     * 备注
     */
    private String remark;

    public OrderGoodsSkuVo() {
    }

    public OrderGoodsSkuVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String orderId) {
        this.storeId = storeId;
        this.orderId = orderId;
    }

    public OrderGoodsSkuVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Integer id, Integer goodsSkuId, Integer operatedQuantity) {
        this.storeId = storeId;
        this.id = id;
        this.goodsSkuId = goodsSkuId;
        this.operatedQuantity = operatedQuantity;
    }

    public OrderGoodsSkuVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String orderId, Integer notFinishQuantity) {
        this.storeId = storeId;
        this.orderId = orderId;
        this.notFinishQuantity = notFinishQuantity;
    }

    public OrderGoodsSkuVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Byte type, String orderId, Integer goodsSkuId, Integer quantity, Integer finishQuantity, Integer notFinishQuantity, BigDecimal money, BigDecimal discountMoney, String remark) {
        this.storeId = storeId;
        this.type = type;
        this.orderId = orderId;
        this.goodsSkuId = goodsSkuId;
        this.quantity = quantity;
        this.finishQuantity = finishQuantity;
        this.notFinishQuantity = notFinishQuantity;
        this.money = money;
        this.discountMoney = discountMoney;
        this.remark = remark;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getChangeQuantity() {
        return changeQuantity;
    }

    public void setChangeQuantity(Integer changeQuantity) {
        this.changeQuantity = changeQuantity;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public String getGoodsSkuSku() {
        return goodsSkuSku;
    }

    public void setGoodsSkuSku(String goodsSkuSku) {
        this.goodsSkuSku = goodsSkuSku;
    }

    public BigDecimal getGoodsSkuPurchasePrice() {
        return goodsSkuPurchasePrice;
    }

    public void setGoodsSkuPurchasePrice(BigDecimal goodsSkuPurchasePrice) {
        this.goodsSkuPurchasePrice = goodsSkuPurchasePrice;
    }

    public BigDecimal getGoodsSkuRetailPrice() {
        return goodsSkuRetailPrice;
    }

    public void setGoodsSkuRetailPrice(BigDecimal goodsSkuRetailPrice) {
        this.goodsSkuRetailPrice = goodsSkuRetailPrice;
    }

    public BigDecimal getGoodsSkuVipPrice() {
        return goodsSkuVipPrice;
    }

    public void setGoodsSkuVipPrice(BigDecimal goodsSkuVipPrice) {
        this.goodsSkuVipPrice = goodsSkuVipPrice;
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
        return "OrderGoodsSkuVo{" +
                "storeId=" + storeId +
                ", changeQuantity=" + changeQuantity +
                ", goodsName='" + goodsName + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsBarCode='" + goodsBarCode + '\'' +
                ", goodsSkuSku='" + goodsSkuSku + '\'' +
                ", goodsSkuPurchasePrice=" + goodsSkuPurchasePrice +
                ", goodsSkuRetailPrice=" + goodsSkuRetailPrice +
                ", goodsSkuVipPrice=" + goodsSkuVipPrice +
                ", id=" + id +
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