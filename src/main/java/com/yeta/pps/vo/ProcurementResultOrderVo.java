package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class ProcurementResultOrderVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

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
     * 出库仓库编号，对应发货
     */
    private Integer outWarehouseId;

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

    public ProcurementResultOrderVo() {
    }

    public ProcurementResultOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Date createTime, String applyOrderId, Byte clearStatus, String supplierId, Integer inWarehouseId, Integer outWarehouseId, Integer totalQuantity, BigDecimal totalMoney, BigDecimal totalDiscountMoney, BigDecimal clearMoney, String userId, String remark) {
        this.storeId = storeId;
        this.createTime = createTime;
        this.applyOrderId = applyOrderId;
        this.clearStatus = clearStatus;
        this.supplierId = supplierId;
        this.inWarehouseId = inWarehouseId;
        this.outWarehouseId = outWarehouseId;
        this.totalQuantity = totalQuantity;
        this.totalMoney = totalMoney;
        this.totalDiscountMoney = totalDiscountMoney;
        this.clearMoney = clearMoney;
        this.userId = userId;
        this.remark = remark;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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

    public Integer getOutWarehouseId() {
        return outWarehouseId;
    }

    public void setOutWarehouseId(Integer outWarehouseId) {
        this.outWarehouseId = outWarehouseId;
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
        return "ProcurementResultOrderVo{" +
                "storeId=" + storeId +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", clearStatus=" + clearStatus +
                ", supplierId=" + supplierId +
                ", inWarehouseId=" + inWarehouseId +
                ", outWarehouseId=" + outWarehouseId +
                ", totalQuantity=" + totalQuantity +
                ", totalMoney=" + totalMoney +
                ", totalDiscountMoney=" + totalDiscountMoney +
                ", clearMoney=" + clearMoney +
                ", userId='" + userId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}