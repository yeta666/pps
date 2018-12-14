package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeta.pps.po.Client;
import com.yeta.pps.util.CommonResponse;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SellApplyOrderVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    @NotNull(message = CommonResponse.MESSAGE3)
    List<ApplyOrderGoodsSkuVo> details;

    private String inWarehouseName;

    private String outWarehouseName;

    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 单据编号
     */
    private String id;

    /**
     * 单据类型，1：零售单，2：销售订单，3：销售退货申请单，4：销售换货申请单
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Byte type;

    /**
     * 单据日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    private Client client;

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
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal totalMoney;

    /**
     * 直接优惠金额
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal discountMoney;

    /**
     * 优惠券编号
     */
    private Integer discountCouponId;

    /**
     * 总优惠金额
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal totalDiscountMoney;

    /**
     * 本单金额
     */
    @NotNull(message = CommonResponse.MESSAGE3)
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
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String userId;

    /**
     * 单据备注
     */
    private String remark;

    public SellApplyOrderVo() {
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public List<ApplyOrderGoodsSkuVo> getDetails() {
        return details;
    }

    public void setDetails(List<ApplyOrderGoodsSkuVo> details) {
        this.details = details;
    }

    public String getInWarehouseName() {
        return inWarehouseName;
    }

    public void setInWarehouseName(String inWarehouseName) {
        this.inWarehouseName = inWarehouseName;
    }

    public String getOutWarehouseName() {
        return outWarehouseName;
    }

    public void setOutWarehouseName(String outWarehouseName) {
        this.outWarehouseName = outWarehouseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getDiscountCouponId() {
        return discountCouponId;
    }

    public void setDiscountCouponId(Integer discountCouponId) {
        this.discountCouponId = discountCouponId;
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

    public BigDecimal getClearedMoney() {
        return clearedMoney;
    }

    public void setClearedMoney(BigDecimal clearedMoney) {
        this.clearedMoney = clearedMoney;
    }

    public BigDecimal getNotClearedMoney() {
        return notClearedMoney;
    }

    public void setNotClearedMoney(BigDecimal notClearedMoney) {
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

    @Override
    public String toString() {
        return "SellApplyOrderVo{" +
                "storeId=" + storeId +
                ", details=" + details +
                ", inWarehouseName='" + inWarehouseName + '\'' +
                ", outWarehouseName='" + outWarehouseName + '\'' +
                ", userName='" + userName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", resultOrderId='" + resultOrderId + '\'' +
                ", prodcingWay=" + prodcingWay +
                ", orderStatus=" + orderStatus +
                ", clearStatus=" + clearStatus +
                ", clientId='" + clientId + '\'' +
                ", client=" + client +
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