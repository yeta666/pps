package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class StorageCheckOrderVo {

    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    /**
     * 库存对账记录编号
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String orderId;

    private String typeName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonIgnore
    private Date startTime;

    @JsonIgnore
    private Date endTime;

    /**
     * 单据状态
     */
    private Byte orderStatus;

    private String applyOrderId;

    /**
     * 往来单位编号
     */
    private String targetId;

    private String targetName;

    @JsonIgnore
    private String goodsId;

    /**
     * 商品规格编号
     */
    @JsonIgnore
    private Integer goodsSkuId;

    private String sku;

    /**
     * 仓库编号
     */
    private Integer warehouseId;

    private String warehouseName;

    /**
     * 入库数量
     */
    private Integer inQuantity = 0;

    /**
     * 入库成本单价
     */
    private Double inMoney = 0.0;

    /**
     * 入库成本金额
     */
    private Double inTotalMoney = 0.0;

    /**
     * 出库数量
     */
    private Integer outQuantity = 0;

    /**
     * 出库成本单价
     */
    private Double outMoney = 0.0;

    /**
     * 出库成本金额
     */
    private Double outTotalMoney = 0.0;

    /**
     * 商品规格-仓库结存数量
     */
    private Integer checkQuantity;

    /**
     * 商品规格-仓库结存成本单价
     */
    private Double checkMoney;

    /**
     * 商品规格-仓库结存成本金额
     */
    private Double checkTotalMoney;

    /**
     * 商品规格结存数量
     */
    private Integer checkQuantity1;

    /**
     * 商品规格结存成本单价
     */
    private Double checkMoney1;

    /**
     * 商品规格结存成本金额
     */
    private Double checkTotalMoney1;

    /**
     * 商品结存数量
     */
    private Integer checkQuantity2;

    /**
     * 商品结存成本单价
     */
    private Double checkMoney2;

    /**
     * 商品结存成本金额
     */
    private Double checkTotalMoney2;

    /**
     * 经手人编号
     */
    private String userId;

    private String userName;

    private String remark;

    @JsonIgnore
    private Byte flag;

    public StorageCheckOrderVo() {
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String goodsId) {
        this.storeId = storeId;
        this.goodsId = goodsId;
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Integer goodsSkuId) {
        this.storeId = storeId;
        this.goodsSkuId = goodsSkuId;
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Integer goodsSkuId, Integer warehouseId) {
        this.storeId = storeId;
        this.goodsSkuId = goodsSkuId;
        this.warehouseId = warehouseId;
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String orderId, Integer goodsSkuId, Integer warehouseId, String userId) {
        this.storeId = storeId;
        this.orderId = orderId;
        this.goodsSkuId = goodsSkuId;
        this.warehouseId = warehouseId;
        this.userId = userId;
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Date startTime, Date endTime, String goodsId) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goodsId = goodsId;
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Date startTime, Date endTime, Integer goodsSkuId) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goodsSkuId = goodsSkuId;
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Date startTime, Date endTime, Integer goodsSkuId, Integer warehouseId) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goodsSkuId = goodsSkuId;
        this.warehouseId = warehouseId;
    }

    public StorageCheckOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Date startTime, Date endTime, Integer goodsSkuId, Byte flag) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goodsSkuId = goodsSkuId;
        this.flag = flag;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getApplyOrderId() {
        return applyOrderId;
    }

    public void setApplyOrderId(String applyOrderId) {
        this.applyOrderId = applyOrderId;
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public Double getInTotalMoney() {
        return inTotalMoney;
    }

    public void setInTotalMoney(Double inTotalMoney) {
        this.inTotalMoney = inTotalMoney;
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

    public Double getOutTotalMoney() {
        return outTotalMoney;
    }

    public void setOutTotalMoney(Double outTotalMoney) {
        this.outTotalMoney = outTotalMoney;
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

    public Integer getCheckQuantity1() {
        return checkQuantity1;
    }

    public void setCheckQuantity1(Integer checkQuantity1) {
        this.checkQuantity1 = checkQuantity1;
    }

    public Double getCheckMoney1() {
        return checkMoney1;
    }

    public void setCheckMoney1(Double checkMoney1) {
        this.checkMoney1 = checkMoney1;
    }

    public Double getCheckTotalMoney1() {
        return checkTotalMoney1;
    }

    public void setCheckTotalMoney1(Double checkTotalMoney1) {
        this.checkTotalMoney1 = checkTotalMoney1;
    }

    public Integer getCheckQuantity2() {
        return checkQuantity2;
    }

    public void setCheckQuantity2(Integer checkQuantity2) {
        this.checkQuantity2 = checkQuantity2;
    }

    public Double getCheckMoney2() {
        return checkMoney2;
    }

    public void setCheckMoney2(Double checkMoney2) {
        this.checkMoney2 = checkMoney2;
    }

    public Double getCheckTotalMoney2() {
        return checkTotalMoney2;
    }

    public void setCheckTotalMoney2(Double checkTotalMoney2) {
        this.checkTotalMoney2 = checkTotalMoney2;
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

    public Byte getFlag() {
        return flag;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "StorageCheckOrderVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", orderId='" + orderId + '\'' +
                ", typeName='" + typeName + '\'' +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", orderStatus=" + orderStatus +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", targetId='" + targetId + '\'' +
                ", targetName='" + targetName + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsSkuId=" + goodsSkuId +
                ", sku='" + sku + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", inQuantity=" + inQuantity +
                ", inMoney=" + inMoney +
                ", inTotalMoney=" + inTotalMoney +
                ", outQuantity=" + outQuantity +
                ", outMoney=" + outMoney +
                ", outTotalMoney=" + outTotalMoney +
                ", checkQuantity=" + checkQuantity +
                ", checkMoney=" + checkMoney +
                ", checkTotalMoney=" + checkTotalMoney +
                ", checkQuantity1=" + checkQuantity1 +
                ", checkMoney1=" + checkMoney1 +
                ", checkTotalMoney1=" + checkTotalMoney1 +
                ", checkQuantity2=" + checkQuantity2 +
                ", checkMoney2=" + checkMoney2 +
                ", checkTotalMoney2=" + checkTotalMoney2 +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", remark='" + remark + '\'' +
                ", flag=" + flag +
                '}';
    }
}