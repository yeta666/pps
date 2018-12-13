package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeta.pps.util.CommonResponse;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ProcurementApplyOrderVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    @NotNull(message = CommonResponse.MESSAGE3)
    List<ProcurementApplyOrderGoodsSkuVo> details;

    private String supplierName;

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
     * 单据类型，1：采购订单，2：采购退货申请，3,：采购换货申请
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
     * 来源订单
     */
    private String applyOrderId;

    /**
     * 单据状态，1：未收，2：部分收，3：已收，4：未发，5：部分发，6：已发，7：未收未发，8：未收部分发，9：未收已发，10：部分收未发，11：部分收部分发，12：部分收已发，13：已收未发，14：已收部分发：15：已收已发
     */
    private Byte orderStatus;

    /**
     * 结算状态：0：未完成，1：已完成
     */
    private Byte clearStatus;

    /**
     * 供应商编号
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String supplierId;

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

    public ProcurementApplyOrderVo() {
    }

    public ProcurementApplyOrderVo(String supplierName, Date startTime, Date endTime) {
        this.supplierName = supplierName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ProcurementApplyOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id) {
        this.storeId = storeId;
        this.id = id;
    }

    public ProcurementApplyOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id, String remark) {
        this.storeId = storeId;
        this.id = id;
        this.remark = remark;
    }

    public ProcurementApplyOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String supplierName, Date startTime, Date endTime, String id, @NotNull(message = CommonResponse.MESSAGE3) Byte type, Byte orderStatus, Byte clearStatus) {
        this.storeId = storeId;
        this.supplierName = supplierName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
        this.type = type;
        this.orderStatus = orderStatus;
        this.clearStatus = clearStatus;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public List<ProcurementApplyOrderGoodsSkuVo> getDetails() {
        return details;
    }

    public void setDetails(List<ProcurementApplyOrderGoodsSkuVo> details) {
        this.details = details;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public Byte getClearStatus() {
        return clearStatus;
    }

    public void setClearStatus(Byte clearStatus) {
        this.clearStatus = clearStatus;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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
        return "ProcurementApplyOrderVo{" +
                "storeId=" + storeId +
                ", details=" + details +
                ", supplierName='" + supplierName + '\'' +
                ", inWarehouseName='" + inWarehouseName + '\'' +
                ", outWarehouseName='" + outWarehouseName + '\'' +
                ", userName='" + userName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", orderStatus=" + orderStatus +
                ", clearStatus=" + clearStatus +
                ", supplierId='" + supplierId + '\'' +
                ", inWarehouseId=" + inWarehouseId +
                ", inTotalQuantity=" + inTotalQuantity +
                ", inReceivedQuantity=" + inReceivedQuantity +
                ", inNotReceivedQuantity=" + inNotReceivedQuantity +
                ", outWarehouseId=" + outWarehouseId +
                ", outTotalQuantity=" + outTotalQuantity +
                ", outSentQuantity=" + outSentQuantity +
                ", outNotSentQuantity=" + outNotSentQuantity +
                ", totalMoney=" + totalMoney +
                ", totalDiscountMoney=" + totalDiscountMoney +
                ", orderMoney=" + orderMoney +
                ", clearedMoney=" + clearedMoney +
                ", notClearedMoney=" + notClearedMoney +
                ", userId='" + userId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}