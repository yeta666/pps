package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * @author YETA
 * @date 2019/01/07/13:40
 */
public class ReportProcurementVo {

    /**
     * 店铺编号
     */
    @JsonIgnore
    private Integer storeId;

    /**
     * 开始时间
     */
    @JsonIgnore
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonIgnore
    private Date endTime;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 单据编号
     */
    private String orderId;

    /**
     * 单据类型
     */
    private Byte type;

    /**
     * 单据类型名称
     */
    private String typeName;

    /**
     * 结算状态
     */
    private Byte clearStatus;

    /**
     * 结算状态名称
     */
    private String clearStatusName;

    /**
     * 仓库编号
     */
    private Integer warehouseId;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 供应商编号
     */
    private String supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 商品货号
     */
    private String goodsId;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 商品条码
     */
    private String goodsBarCode;

    /**
     * 商品分类编号
     */
    private Integer goodsTypeId;

    /**
     * 商品分类
     */
    private String goodsTypeName;

    /**
     * 商品图片
     */
    private String goodsImage;

    /**
     * 商品规格编号
     */
    private Integer goodsSkuId;

    /**
     * 商品规格
     */
    private String goodsSku;

    /**
     * 采购数量
     */
    private Integer procurementQuantity;

    /**
     * 采购金额
     */
    private Double procurementMoney;

    //发货

    /**
     * 已发货数量
     */
    private Integer outFinishQuantity;

    /**
     * 待发货数量
     */
    private Integer outNotFinishQuantity;

    //收货

    /**
     * 已收货数量
     */
    private Integer inFinishQuantity;

    /**
     * 待收货数量
     */
    private Integer inNotFinishQuantity;

    //退货

    /**
     * 采购入库数量
     */
    private Integer procurementInQuantity;

    /**
     * 退货数量
     */
    private Integer returnQuantity;

    /**
     * 退货金额
     */
    private Double returnMoney;

    /**
     * 退货率
     */
    private Double returnRate;

    /**
     * 经手人编号
     */
    private String userId;

    /**
     * 经手人名称
     */
    private String userName;

    /**
     * 备注
     */
    private String remark;

    public ReportProcurementVo() {
    }

    public ReportProcurementVo(Integer storeId, Date startTime, Date endTime) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ReportProcurementVo(Integer storeId, Date startTime, Date endTime, String goodsId, String goodsName, String goodsBarCode, Integer goodsTypeId) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsBarCode = goodsBarCode;
        this.goodsTypeId = goodsTypeId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Byte getClearStatus() {
        return clearStatus;
    }

    public void setClearStatus(Byte clearStatus) {
        this.clearStatus = clearStatus;
    }

    public String getClearStatusName() {
        return clearStatusName;
    }

    public void setClearStatusName(String clearStatusName) {
        this.clearStatusName = clearStatusName;
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public Integer getGoodsSkuId() {
        return goodsSkuId;
    }

    public void setGoodsSkuId(Integer goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }

    public String getGoodsSku() {
        return goodsSku;
    }

    public void setGoodsSku(String goodsSku) {
        this.goodsSku = goodsSku;
    }

    public Integer getProcurementQuantity() {
        return procurementQuantity;
    }

    public void setProcurementQuantity(Integer procurementQuantity) {
        this.procurementQuantity = procurementQuantity;
    }

    public Double getProcurementMoney() {
        return procurementMoney;
    }

    public void setProcurementMoney(Double procurementMoney) {
        this.procurementMoney = procurementMoney;
    }

    public Integer getOutFinishQuantity() {
        return outFinishQuantity;
    }

    public void setOutFinishQuantity(Integer outFinishQuantity) {
        this.outFinishQuantity = outFinishQuantity;
    }

    public Integer getOutNotFinishQuantity() {
        return outNotFinishQuantity;
    }

    public void setOutNotFinishQuantity(Integer outNotFinishQuantity) {
        this.outNotFinishQuantity = outNotFinishQuantity;
    }

    public Integer getInFinishQuantity() {
        return inFinishQuantity;
    }

    public void setInFinishQuantity(Integer inFinishQuantity) {
        this.inFinishQuantity = inFinishQuantity;
    }

    public Integer getInNotFinishQuantity() {
        return inNotFinishQuantity;
    }

    public void setInNotFinishQuantity(Integer inNotFinishQuantity) {
        this.inNotFinishQuantity = inNotFinishQuantity;
    }

    public Integer getProcurementInQuantity() {
        return procurementInQuantity;
    }

    public void setProcurementInQuantity(Integer procurementInQuantity) {
        this.procurementInQuantity = procurementInQuantity;
    }

    public Integer getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Integer returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public Double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Double getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(Double returnRate) {
        this.returnRate = returnRate;
    }

    @Override
    public String toString() {
        return "ReportProcurementVo{" +
                "storeId=" + storeId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", orderId='" + orderId + '\'' +
                ", type=" + type +
                ", typeName='" + typeName + '\'' +
                ", clearStatus=" + clearStatus +
                ", clearStatusName=" + clearStatusName +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsBarCode='" + goodsBarCode + '\'' +
                ", goodsTypeId=" + goodsTypeId +
                ", goodsTypeName='" + goodsTypeName + '\'' +
                ", goodsImage='" + goodsImage + '\'' +
                ", goodsSkuId=" + goodsSkuId +
                ", goodsSku='" + goodsSku + '\'' +
                ", procurementQuantity=" + procurementQuantity +
                ", procurementMoney=" + procurementMoney +
                ", outFinishQuantity=" + outFinishQuantity +
                ", outNotFinishQuantity=" + outNotFinishQuantity +
                ", inFinishQuantity=" + inFinishQuantity +
                ", inNotFinishQuantity=" + inNotFinishQuantity +
                ", procurementInQuantity=" + procurementInQuantity +
                ", returnQuantity=" + returnQuantity +
                ", returnMoney=" + returnMoney +
                ", returnRate=" + returnRate +
                '}';
    }
}
