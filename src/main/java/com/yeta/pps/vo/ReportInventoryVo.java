package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * @author YETA
 * @date 2019/01/07/13:40
 */
public class ReportInventoryVo {

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
     * 商品货号
     */
    private String id;

    /**
     * 商品名
     */
    private String name;

    /**
     * 条码
     */
    private String barCode;

    /**
     * 分类编号
     */
    private Integer typeId;

    /**
     * 分类
     */
    private String typeName;

    /**
     * 图片
     */
    private String image;

    /**
     * 仓库编号
     */
    private Integer warehouseId;

    /**
     * 仓库
     */
    private String warehouseName;

    /**
     * 此前数量
     */
    private Integer beforeQuantity = 0;

    /**
     * 此前金额
     */
    private Double beforeMoney = 0.00;

    /**
     * 采购入库数量
     */
    private Integer procurementInQuantity = 0;

    /**
     * 采购入库金额
     */
    private Double procurementInMoney = 0.00;

    /**
     * 其他入库数量
     */
    private Integer otherInQuantity = 0;

    /**
     * 其他入库金额
     */
    private Double otherInMoney = 0.00;

    /**
     * 总入库数量
     */
    private Integer totalInQuantity = 0;

    /**
     * 总入库金额
     */
    private Double totalInMoney = 0.00;

    /**
     * 销售出库数量
     */
    private Integer sellOutQuantity = 0;

    /**
     * 销售出库金额
     */
    private Double sellOutMoney = 0.00;

    /**
     * 其他出库数量
     */
    private Integer otherOutQuantity = 0;

    /**
     * 其他出库金额
     */
    private Double otherOutMoney = 0.00;

    /**
     * 总出库数量
     */
    private Integer totalOutQuantity = 0;

    /**
     * 总出库金额
     */
    private Double totalOutMoney = 0.00;

    /**
     * 净入库数量
     */
    private Integer netInQuantity = 0;

    /**
     * 净入库金额
     */
    private Double netInMoney = 0.00;

    /**
     * 期末数量
     */
    private Integer endingQuantity = 0;

    /**
     * 期末金额
     */
    private Double endingMoney = 0.00;

    @JsonIgnore
    private Integer flag;

    public ReportInventoryVo() {
    }

    public ReportInventoryVo(Integer storeId, Date startTime, Date endTime, String id, String name, String barCode, Integer typeId) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
        this.name = name;
        this.barCode = barCode;
        this.typeId = typeId;
    }

    public ReportInventoryVo(Integer storeId, Date startTime, Date endTime, String id, String name, String barCode, Integer typeId, Integer warehouseId) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
        this.name = name;
        this.barCode = barCode;
        this.typeId = typeId;
        this.warehouseId = warehouseId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Integer getBeforeQuantity() {
        return beforeQuantity;
    }

    public void setBeforeQuantity(Integer beforeQuantity) {
        this.beforeQuantity = beforeQuantity;
    }

    public Double getBeforeMoney() {
        return beforeMoney;
    }

    public void setBeforeMoney(Double beforeMoney) {
        this.beforeMoney = beforeMoney;
    }

    public Integer getProcurementInQuantity() {
        return procurementInQuantity;
    }

    public void setProcurementInQuantity(Integer procurementInQuantity) {
        this.procurementInQuantity = procurementInQuantity;
    }

    public Double getProcurementInMoney() {
        return procurementInMoney;
    }

    public void setProcurementInMoney(Double procurementInMoney) {
        this.procurementInMoney = procurementInMoney;
    }

    public Integer getOtherInQuantity() {
        return otherInQuantity;
    }

    public void setOtherInQuantity(Integer otherInQuantity) {
        this.otherInQuantity = otherInQuantity;
    }

    public Double getOtherInMoney() {
        return otherInMoney;
    }

    public void setOtherInMoney(Double otherInMoney) {
        this.otherInMoney = otherInMoney;
    }

    public Integer getTotalInQuantity() {
        return totalInQuantity;
    }

    public void setTotalInQuantity(Integer totalInQuantity) {
        this.totalInQuantity = totalInQuantity;
    }

    public Double getTotalInMoney() {
        return totalInMoney;
    }

    public void setTotalInMoney(Double totalInMoney) {
        this.totalInMoney = totalInMoney;
    }

    public Integer getSellOutQuantity() {
        return sellOutQuantity;
    }

    public void setSellOutQuantity(Integer sellOutQuantity) {
        this.sellOutQuantity = sellOutQuantity;
    }

    public Double getSellOutMoney() {
        return sellOutMoney;
    }

    public void setSellOutMoney(Double sellOutMoney) {
        this.sellOutMoney = sellOutMoney;
    }

    public Integer getOtherOutQuantity() {
        return otherOutQuantity;
    }

    public void setOtherOutQuantity(Integer otherOutQuantity) {
        this.otherOutQuantity = otherOutQuantity;
    }

    public Double getOtherOutMoney() {
        return otherOutMoney;
    }

    public void setOtherOutMoney(Double otherOutMoney) {
        this.otherOutMoney = otherOutMoney;
    }

    public Integer getTotalOutQuantity() {
        return totalOutQuantity;
    }

    public void setTotalOutQuantity(Integer totalOutQuantity) {
        this.totalOutQuantity = totalOutQuantity;
    }

    public Double getTotalOutMoney() {
        return totalOutMoney;
    }

    public void setTotalOutMoney(Double totalOutMoney) {
        this.totalOutMoney = totalOutMoney;
    }

    public Integer getNetInQuantity() {
        return netInQuantity;
    }

    public void setNetInQuantity(Integer netInQuantity) {
        this.netInQuantity = netInQuantity;
    }

    public Double getNetInMoney() {
        return netInMoney;
    }

    public void setNetInMoney(Double netInMoney) {
        this.netInMoney = netInMoney;
    }

    public Integer getEndingQuantity() {
        return endingQuantity;
    }

    public void setEndingQuantity(Integer endingQuantity) {
        this.endingQuantity = endingQuantity;
    }

    public Double getEndingMoney() {
        return endingMoney;
    }

    public void setEndingMoney(Double endingMoney) {
        this.endingMoney = endingMoney;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "ReportInventoryVo{" +
                "storeId=" + storeId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", barCode='" + barCode + '\'' +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", image='" + image + '\'' +
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", beforeQuantity=" + beforeQuantity +
                ", beforeMoney=" + beforeMoney +
                ", procurementInQuantity=" + procurementInQuantity +
                ", procurementInMoney=" + procurementInMoney +
                ", otherInQuantity=" + otherInQuantity +
                ", otherInMoney=" + otherInMoney +
                ", totalInQuantity=" + totalInQuantity +
                ", totalInMoney=" + totalInMoney +
                ", sellOutQuantity=" + sellOutQuantity +
                ", sellOutMoney=" + sellOutMoney +
                ", otherOutQuantity=" + otherOutQuantity +
                ", otherOutMoney=" + otherOutMoney +
                ", totalOutQuantity=" + totalOutQuantity +
                ", totalOutMoney=" + totalOutMoney +
                ", netInQuantity=" + netInQuantity +
                ", netInMoney=" + netInMoney +
                ", endingQuantity=" + endingQuantity +
                ", endingMoney=" + endingMoney +
                '}';
    }
}
