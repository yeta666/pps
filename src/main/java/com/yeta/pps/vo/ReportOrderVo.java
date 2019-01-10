package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * @author YETA
 * @date 2019/01/07/13:40
 */
public class ReportOrderVo {

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
     * 订单数
     */
    private Integer orderQuantity;

    /**
     * 退货单数
     */
    private Integer returnQuantity;

    /**
     * 换货单数
     */
    private Integer exchangeQuantity;

    /**
     * 订单金额
     */
    private Double orderMoney;

    /**
     * 退货金额
     */
    private Double returnMoney;

    /**
     * 平均订单金额
     */
    private Double averageOrderMoney;

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
     * 金额合计
     */
    private Double netMoney;

    /**
     * 消费客户数
     */
    private Integer clientQuantity;

    /**
     * 平均客单量
     */
    private Double averageClientOrderQuantity;

    /**
     * 平均客单价
     */
    private Double averageClientOrderMoney;

    public ReportOrderVo() {
    }

    public ReportOrderVo(Integer storeId, Date startTime, Date endTime) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ReportOrderVo(Integer storeId, Date startTime, Date endTime, String goodsId, String goodsName, String goodsBarCode, Integer goodsTypeId) {
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

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Integer getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Integer returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public Integer getExchangeQuantity() {
        return exchangeQuantity;
    }

    public void setExchangeQuantity(Integer exchangeQuantity) {
        this.exchangeQuantity = exchangeQuantity;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Double getAverageOrderMoney() {
        return averageOrderMoney;
    }

    public void setAverageOrderMoney(Double averageOrderMoney) {
        this.averageOrderMoney = averageOrderMoney;
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

    public Double getNetMoney() {
        return netMoney;
    }

    public void setNetMoney(Double netMoney) {
        this.netMoney = netMoney;
    }

    public Integer getClientQuantity() {
        return clientQuantity;
    }

    public void setClientQuantity(Integer clientQuantity) {
        this.clientQuantity = clientQuantity;
    }

    public Double getAverageClientOrderQuantity() {
        return averageClientOrderQuantity;
    }

    public void setAverageClientOrderQuantity(Double averageClientOrderQuantity) {
        this.averageClientOrderQuantity = averageClientOrderQuantity;
    }

    public Double getAverageClientOrderMoney() {
        return averageClientOrderMoney;
    }

    public void setAverageClientOrderMoney(Double averageClientOrderMoney) {
        this.averageClientOrderMoney = averageClientOrderMoney;
    }

    @Override
    public String toString() {
        return "ReportOrderVo{" +
                "storeId=" + storeId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", orderQuantity=" + orderQuantity +
                ", returnQuantity=" + returnQuantity +
                ", exchangeQuantity=" + exchangeQuantity +
                ", orderMoney=" + orderMoney +
                ", returnMoney=" + returnMoney +
                ", averageOrderMoney=" + averageOrderMoney +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsBarCode='" + goodsBarCode + '\'' +
                ", goodsTypeId=" + goodsTypeId +
                ", goodsTypeName='" + goodsTypeName + '\'' +
                ", goodsImage='" + goodsImage + '\'' +
                ", netMoney=" + netMoney +
                ", clientQuantity=" + clientQuantity +
                ", averageClientOrderQuantity=" + averageClientOrderQuantity +
                ", averageClientOrderMoney=" + averageClientOrderMoney +
                '}';
    }
}
