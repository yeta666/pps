package com.yeta.pps.po;

import java.math.BigDecimal;
import java.util.Date;

public class ProcurementApplyOrder {

    /**
     * 单据编号
     */
    private String id;

    /**
     * 单据类型，1：采购订单，2：采购退货申请，3,：采购换货申请
     */
    private Byte type;

    /**
     * 单据日期
     */
    private Date createTime;

    /**
     * 单据状态，1：未收，2：已收，3：未发，4：已发，5：未收未发，6：未收已发，7：已收未发，8：已收已发
     */
    private Byte orderStatus;

    /**
     * 结算状态：0：未完成，1：已完成
     */
    private Byte clearStatus;

    /**
     * 供应商编号
     */
    private String supplierId;

    /**
     * 入库仓库编号，对应收货
     */
    private Integer inWarehouseId;

    /**
     * 收货总数量
     */
    private Integer inTotalQuantity;

    /**
     * 出库仓库编号，对应发货
     */
    private Integer outWarehouseId;

    /**
     * 发货总数量
     */
    private Integer outTotalQuantity;

    /**
     * 订单金额
     */
    private BigDecimal totalMoney;

    /**
     * 总优惠金额
     */
    private BigDecimal totalDiscountMoney;

    /**
     * 结算金额
     */
    private BigDecimal clearMoney;

    /**
     * 经手人编号
     */
    private String userId;

    /**
     * 单据备注
     */
    private String remark;

    public ProcurementApplyOrder() {
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

    public BigDecimal getClearMoney() {
        return clearMoney;
    }

    public void setClearMoney(BigDecimal clearMoney) {
        this.clearMoney = clearMoney;
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
        return "ProcurementApplyOrder{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", orderStatus=" + orderStatus +
                ", clearStatus=" + clearStatus +
                ", supplierId=" + supplierId +
                ", inWarehouseId=" + inWarehouseId +
                ", inTotalQuantity=" + inTotalQuantity +
                ", outWarehouseId=" + outWarehouseId +
                ", outTotalQuantity=" + outTotalQuantity +
                ", totalMoney=" + totalMoney +
                ", totalDiscountMoney=" + totalDiscountMoney +
                ", clearMoney=" + clearMoney +
                ", userId='" + userId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}