package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeta.pps.po.Store;

import java.util.Date;

public class ClientIntegralDetailVo {

    private Store store;

    private ClientVo clientVo;

    /**
     * 客户/积分明细关系编号
     */
    private Integer id;

    /**
     * 店铺编号
     */
    public Integer storeId;

    /**
     * 客户编号
     */
    private String clientId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 操作类型，0：减少，1：增加
     */
    private Byte type;

    /**
     * 改变积分
     */
    private Integer changeIntegral;

    /**
     * 改变后的积分
     */
    private Integer afterChangeIntegral;

    /**
     * 单据编号
     */
    private String orderId;

    public ClientIntegralDetailVo() {
    }

    public ClientIntegralDetailVo(Integer storeId, String clientId) {
        this.storeId = storeId;
        this.clientId = clientId;
    }

    public ClientIntegralDetailVo(Integer storeId, String clientId, Byte type) {
        this.storeId = storeId;
        this.clientId = clientId;
        this.type = type;
    }

    public ClientIntegralDetailVo(Integer storeId, String clientId, Date createTime, Byte type, Integer changeIntegral, Integer afterChangeIntegral, String orderId) {
        this.storeId = storeId;
        this.clientId = clientId;
        this.createTime = createTime;
        this.type = type;
        this.changeIntegral = changeIntegral;
        this.afterChangeIntegral = afterChangeIntegral;
        this.orderId = orderId;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public ClientVo getClientVo() {
        return clientVo;
    }

    public void setClientVo(ClientVo clientVo) {
        this.clientVo = clientVo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getChangeIntegral() {
        return changeIntegral;
    }

    public void setChangeIntegral(Integer changeIntegral) {
        this.changeIntegral = changeIntegral;
    }

    public Integer getAfterChangeIntegral() {
        return afterChangeIntegral;
    }

    public void setAfterChangeIntegral(Integer afterChangeIntegral) {
        this.afterChangeIntegral = afterChangeIntegral;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "ClientIntegralDetailVo{" +
                "store=" + store +
                ", clientVo=" + clientVo +
                ", id=" + id +
                ", storeId='" + storeId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", createTime=" + createTime +
                ", type=" + type +
                ", changeIntegral=" + changeIntegral +
                ", afterChangeIntegral=" + afterChangeIntegral +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}