package com.yeta.pps.vo;

/**
 * @author YETA
 * @date 2018/12/18/21:35
 */
public class StoreClientVo {

    /**
     * 店铺/客户关系编号
     */
    private String id;

    /**
     * 店铺编号
     */
    private Integer storeId;

    private String storeName;

    /**
     * 客户编号
     */
    private String clientId;

    private String clientName;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 预收款期初
     */
    private Double advanceInMoneyOpening;

    /**
     * 应收款期初
     */
    private Double needInMoneyOpening;

    /**
     * 提成
     */
    private Double pushMoney;

    public StoreClientVo() {
    }

    public StoreClientVo(Integer storeId) {
        this.storeId = storeId;
    }

    public StoreClientVo(Integer storeId, String clientId) {
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

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Double getAdvanceInMoneyOpening() {
        return advanceInMoneyOpening;
    }

    public void setAdvanceInMoneyOpening(Double advanceInMoneyOpening) {
        this.advanceInMoneyOpening = advanceInMoneyOpening;
    }

    public Double getNeedInMoneyOpening() {
        return needInMoneyOpening;
    }

    public void setNeedInMoneyOpening(Double needInMoneyOpening) {
        this.needInMoneyOpening = needInMoneyOpening;
    }

    public Double getPushMoney() {
        return pushMoney;
    }

    public void setPushMoney(Double pushMoney) {
        this.pushMoney = pushMoney;
    }

    @Override
    public String toString() {
        return "StoreClientVo{" +
                "id='" + id + '\'' +
                ", storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", integral=" + integral +
                ", advanceInMoneyOpening=" + advanceInMoneyOpening +
                ", needInMoneyOpening=" + needInMoneyOpening +
                ", pushMoney=" + pushMoney +
                '}';
    }
}
