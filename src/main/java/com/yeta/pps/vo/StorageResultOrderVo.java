package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class StorageResultOrderVo {

    /**
     * 店铺编号
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    private List<OrderGoodsSkuVo> details;

    /**
     * 单据编号
     */
    private String id;

    /**
     * 单据类型，1：其他入库单，2：其他出库单，3：报溢单，4：报损单，5：成本调价单
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Byte type;

    /**
     * 单据日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Date startTime;

    private Date endTime;

    /**
     * 单据状态
     */
    private Byte orderStatus;

    /**
     * 往来单位类型，1：供应商，2：客户
     */
    public Byte targetType;

    /**
     * 往来单位编号
     */
    public String targetId;

    public String targetName;

    @JsonIgnore
    public String clientName;

    @JsonIgnore
    public String supplierName;

    /**
     * 仓库编号
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer warehouseId;

    private String warehouseName;

    /**
     * 总商品数量
     */
    private Integer totalQuantity;

    /**
     * 总商品金额
     */
    private BigDecimal totalMoney;

    /**
     * 经手人
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String userId;

    private String userName;

    /**
     * 单据备注
     */
    private String remark;

    /**
     * 商品规格编号
     */
    private Integer goodsSkuId;

    private String sku;

    /**
     * 结存数量
     */
    private Integer checkQuantity;

    /**
     * 结存成本单价
     */
    private BigDecimal checkMoney;

    /**
     * 结存金额
     */
    private BigDecimal checkTotalMoney;

    /**
     * 调整后成本单价
     */
    private BigDecimal afterChangeCheckMoney;

    /**
     * 调整金额
     */
    private BigDecimal changeCheckTotalMoney;

    public StorageResultOrderVo() {
    }

    public StorageResultOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id) {
        this.storeId = storeId;
        this.id = id;
    }

    public StorageResultOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id, @NotBlank(message = CommonResponse.MESSAGE3) String userId, String remark) {
        this.storeId = storeId;
        this.id = id;
        this.userId = userId;
        this.remark = remark;
    }

    public StorageResultOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id, Byte type, Date startTime, Date endTime, String targetName) {
        this.storeId = storeId;
        this.id = id;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.targetName = targetName;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public List<OrderGoodsSkuVo> getDetails() {
        return details;
    }

    public void setDetails(List<OrderGoodsSkuVo> details) {
        this.details = details;
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

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Byte getTargetType() {
        return targetType;
    }

    public void setTargetType(Byte targetType) {
        this.targetType = targetType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getCheckQuantity() {
        return checkQuantity;
    }

    public void setCheckQuantity(Integer checkQuantity) {
        this.checkQuantity = checkQuantity;
    }

    public BigDecimal getCheckMoney() {
        return checkMoney;
    }

    public void setCheckMoney(BigDecimal checkMoney) {
        this.checkMoney = checkMoney;
    }

    public BigDecimal getCheckTotalMoney() {
        return checkTotalMoney;
    }

    public void setCheckTotalMoney(BigDecimal checkTotalMoney) {
        this.checkTotalMoney = checkTotalMoney;
    }

    public BigDecimal getAfterChangeCheckMoney() {
        return afterChangeCheckMoney;
    }

    public void setAfterChangeCheckMoney(BigDecimal afterChangeCheckMoney) {
        this.afterChangeCheckMoney = afterChangeCheckMoney;
    }

    public BigDecimal getChangeCheckTotalMoney() {
        return changeCheckTotalMoney;
    }

    public void setChangeCheckTotalMoney(BigDecimal changeCheckTotalMoney) {
        this.changeCheckTotalMoney = changeCheckTotalMoney;
    }

    @Override
    public String toString() {
        return "StorageResultOrderVo{" +
                "storeId=" + storeId +
                ", details=" + details +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", orderStatus=" + orderStatus +
                ", targetType=" + targetType +
                ", targetId='" + targetId + '\'' +
                ", targetName='" + targetName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", totalQuantity=" + totalQuantity +
                ", totalMoney=" + totalMoney +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", remark='" + remark + '\'' +
                ", goodsSkuId=" + goodsSkuId +
                ", sku='" + sku + '\'' +
                ", checkQuantity=" + checkQuantity +
                ", checkMoney=" + checkMoney +
                ", checkTotalMoney=" + checkTotalMoney +
                ", afterChangeCheckMoney=" + afterChangeCheckMoney +
                ", changeCheckTotalMoney=" + changeCheckTotalMoney +
                '}';
    }
}