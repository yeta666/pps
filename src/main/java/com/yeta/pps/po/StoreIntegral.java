package com.yeta.pps.po;

/**
 * @author YETA
 * @date 2018/12/18/21:35
 */
public class StoreIntegral {

    /**
     * 店铺/客户积分关系编号
     */
    private String id;

    /**
     * 店铺编号
     */
    private Integer storeId;

    /**
     * 客户编号
     */
    private String clientId;

    /**
     * 积分
     */
    private Integer integral;

    public StoreIntegral() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    @Override
    public String toString() {
        return "StoreIntegral{" +
                "id='" + id + '\'' +
                ", storeId=" + storeId +
                ", clientId='" + clientId + '\'' +
                ", integral=" + integral +
                '}';
    }
}
