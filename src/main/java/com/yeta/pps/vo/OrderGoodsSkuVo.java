package com.yeta.pps.vo;

public class OrderGoodsSkuVo {

    private Integer storeId;

    /**
     * 订单/商品规格关系编号
     */
    private Integer id;

    /**
     * 类型，1：入库，0：出库
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

    private String goodsSkuSku;

    private Double goodsSkuPurchasePrice;

    private Double goodsSkuRetailPrice;

    private Double goodsSkuVipPrice;

    private Double goodsSkuBossPrice;

    private Integer integral;

    private String goodsId;

    private String goodsName;

    private String goodsBarCode;

    private Integer changeQuantity;

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
    private Double money;

    /**
     * 优惠金额
     */
    private Double discountMoney;

    /**
     * 已操作数量
     */
    private Integer operatedQuantity;

    /**
     * 结存数量/盘点数量
     */
    private Integer checkQuantity;

    /**
     * 结存成本单价
     */
    private Double checkMoney;

    /**
     * 结存金额
     */
    private Double checkTotalMoney;

    /**
     * 调整后成本单价
     */
    private Double afterChangeCheckMoney;

    /**
     * 调整金额
     */
    private Double changeCheckTotalMoney;

    /**
     * 盘盈数量
     */
    private Integer inQuantity;

    /**
     * 盘盈金额
     */
    private Double inMoney;

    /**
     * 盘亏数量
     */
    private Integer outQuantity;

    /**
     * 盘亏金额
     */
    private Double outMoney;

    /**
     * 备注
     */
    private String remark;

    public OrderGoodsSkuVo() {
    }

    public OrderGoodsSkuVo(Integer storeId) {
        this.storeId = storeId;
    }

    public OrderGoodsSkuVo(Integer quantity, Integer finishQuantity) {
        this.quantity = quantity;
        this.finishQuantity = finishQuantity;
    }

    public OrderGoodsSkuVo(Integer storeId, String orderId) {
        this.storeId = storeId;
        this.orderId = orderId;
    }

    public OrderGoodsSkuVo(Integer storeId, Integer id, Integer goodsSkuId, Integer operatedQuantity) {
        this.storeId = storeId;
        this.id = id;
        this.goodsSkuId = goodsSkuId;
        this.operatedQuantity = operatedQuantity;
    }

    public OrderGoodsSkuVo(Integer storeId, String orderId, Integer notFinishQuantity) {
        this.storeId = storeId;
        this.orderId = orderId;
        this.notFinishQuantity = notFinishQuantity;
    }

    public OrderGoodsSkuVo(Integer storeId, Byte type, String orderId, Integer goodsSkuId, Integer quantity, Integer finishQuantity, Integer notFinishQuantity, Double money, Double discountMoney, String remark) {
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

    public String getGoodsSkuSku() {
        return goodsSkuSku;
    }

    public void setGoodsSkuSku(String goodsSkuSku) {
        this.goodsSkuSku = goodsSkuSku;
    }

    public Double getGoodsSkuPurchasePrice() {
        return goodsSkuPurchasePrice;
    }

    public void setGoodsSkuPurchasePrice(Double goodsSkuPurchasePrice) {
        this.goodsSkuPurchasePrice = goodsSkuPurchasePrice;
    }

    public Double getGoodsSkuRetailPrice() {
        return goodsSkuRetailPrice;
    }

    public void setGoodsSkuRetailPrice(Double goodsSkuRetailPrice) {
        this.goodsSkuRetailPrice = goodsSkuRetailPrice;
    }

    public Double getGoodsSkuVipPrice() {
        return goodsSkuVipPrice;
    }

    public void setGoodsSkuVipPrice(Double goodsSkuVipPrice) {
        this.goodsSkuVipPrice = goodsSkuVipPrice;
    }

    public Double getGoodsSkuBossPrice() {
        return goodsSkuBossPrice;
    }

    public void setGoodsSkuBossPrice(Double goodsSkuBossPrice) {
        this.goodsSkuBossPrice = goodsSkuBossPrice;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
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

    public Integer getChangeQuantity() {
        return changeQuantity;
    }

    public void setChangeQuantity(Integer changeQuantity) {
        this.changeQuantity = changeQuantity;
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Double discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getOperatedQuantity() {
        return operatedQuantity;
    }

    public void setOperatedQuantity(Integer operatedQuantity) {
        this.operatedQuantity = operatedQuantity;
    }

    public Integer getCheckQuantity() {
        return checkQuantity;
    }

    public void setCheckQuantity(Integer checkQuantity) {
        this.checkQuantity = checkQuantity;
    }

    public Double getCheckMoney() {
        return checkMoney;
    }

    public void setCheckMoney(Double checkMoney) {
        this.checkMoney = checkMoney;
    }

    public Double getCheckTotalMoney() {
        return checkTotalMoney;
    }

    public void setCheckTotalMoney(Double checkTotalMoney) {
        this.checkTotalMoney = checkTotalMoney;
    }

    public Double getAfterChangeCheckMoney() {
        return afterChangeCheckMoney;
    }

    public void setAfterChangeCheckMoney(Double afterChangeCheckMoney) {
        this.afterChangeCheckMoney = afterChangeCheckMoney;
    }

    public Double getChangeCheckTotalMoney() {
        return changeCheckTotalMoney;
    }

    public void setChangeCheckTotalMoney(Double changeCheckTotalMoney) {
        this.changeCheckTotalMoney = changeCheckTotalMoney;
    }

    public Integer getInQuantity() {
        return inQuantity;
    }

    public void setInQuantity(Integer inQuantity) {
        this.inQuantity = inQuantity;
    }

    public Double getInMoney() {
        return inMoney;
    }

    public void setInMoney(Double inMoney) {
        this.inMoney = inMoney;
    }

    public Integer getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(Integer outQuantity) {
        this.outQuantity = outQuantity;
    }

    public Double getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(Double outMoney) {
        this.outMoney = outMoney;
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
                ", id=" + id +
                ", type=" + type +
                ", orderId='" + orderId + '\'' +
                ", goodsSkuId=" + goodsSkuId +
                ", goodsSkuSku='" + goodsSkuSku + '\'' +
                ", goodsSkuPurchasePrice=" + goodsSkuPurchasePrice +
                ", goodsSkuRetailPrice=" + goodsSkuRetailPrice +
                ", goodsSkuVipPrice=" + goodsSkuVipPrice +
                ", goodsSkuBossPrice=" + goodsSkuBossPrice +
                ", integral=" + integral +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsBarCode='" + goodsBarCode + '\'' +
                ", changeQuantity=" + changeQuantity +
                ", quantity=" + quantity +
                ", finishQuantity=" + finishQuantity +
                ", notFinishQuantity=" + notFinishQuantity +
                ", money=" + money +
                ", discountMoney=" + discountMoney +
                ", operatedQuantity=" + operatedQuantity +
                ", checkQuantity=" + checkQuantity +
                ", checkMoney=" + checkMoney +
                ", checkTotalMoney=" + checkTotalMoney +
                ", afterChangeCheckMoney=" + afterChangeCheckMoney +
                ", changeCheckTotalMoney=" + changeCheckTotalMoney +
                ", inQuantity=" + inQuantity +
                ", inMoney=" + inMoney +
                ", outQuantity=" + outQuantity +
                ", outMoney=" + outMoney +
                ", remark='" + remark + '\'' +
                '}';
    }
}