package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author YETA
 * @date 2019/01/14/21:11
 */
public class SubordinateVo {

    /**
     * 店铺编号
     */
    private Integer storeId;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 客户编号
     */
    private String clientId;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 消费
     */
    private Double totalMoney;

    /**
     * 提成
     */
    private Double totalPushMoney;

    public SubordinateVo() {
    }

    public SubordinateVo(String clientId) {
        this.clientId = clientId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Double getTotalPushMoney() {
        return totalPushMoney;
    }

    public void setTotalPushMoney(Double totalPushMoney) {
        this.totalPushMoney = totalPushMoney;
    }

    @Override
    public String toString() {
        return "SubordinateVo{" +
                "storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", createTime=" + createTime +
                ", totalMoney=" + totalMoney +
                ", totalPushMoney=" + totalPushMoney +
                '}';
    }
}
