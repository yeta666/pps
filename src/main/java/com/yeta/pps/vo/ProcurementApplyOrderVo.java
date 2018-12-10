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
    @NotNull(message = CommonResponse.MESSAGE3)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    @NotBlank(message = CommonResponse.MESSAGE3)
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
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal totalMoney;

    /**
     * 总优惠金额
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal totalDiscountMoney;

    /**
     * 结算金额
     */
    private BigDecimal clearMoney;

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

    public ProcurementApplyOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id) {
        this.storeId = storeId;
        this.id = id;
    }

    public ProcurementApplyOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id, @NotNull(message = CommonResponse.MESSAGE3) Byte type) {
        this.storeId = storeId;
        this.id = id;
        this.type = type;
    }

    public ProcurementApplyOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String supplierName, Date startTime, Date endTime, String id) {
        this.storeId = storeId;
        this.supplierName = supplierName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
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
                ", orderStatus=" + orderStatus +
                ", clearStatus=" + clearStatus +
                ", supplierId='" + supplierId + '\'' +
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