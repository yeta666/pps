package com.yeta.pps.po;

/**
 * @author YETA
 * @date 2018/12/18/21:35
 */
public class StoreClient {

    /**
     * 店铺/客户关系编号
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

    /**
     * 预收款余额
     */
    private Double advanceMoney;

    /**
     * 提成
     */
    private Double pushMoney;

    public StoreClient() {
    }

    public StoreClient(String clientId) {
        this.clientId = clientId;
    }

    public StoreClient(Integer storeId, String clientId) {
        this.storeId = storeId;
        this.clientId = clientId;
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

    public Double getAdvanceMoney() {
        return advanceMoney;
    }

    public void setAdvanceMoney(Double advanceMoney) {
        this.advanceMoney = advanceMoney;
    }

    public Double getPushMoney() {
        return pushMoney;
    }

    public void setPushMoney(Double pushMoney) {
        this.pushMoney = pushMoney;
    }

    @Override
    public String toString() {
        return "StoreClient{" +
                "id='" + id + '\'' +
                ", storeId=" + storeId +
                ", clientId='" + clientId + '\'' +
                ", integral=" + integral +
                ", advanceMoney=" + advanceMoney +
                ", pushMoney=" + pushMoney +
                '}';
    }
}
