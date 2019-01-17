package com.yeta.pps.po;

/**
 * @author YETA
 * @date 2019/01/14/19:06
 */
public class SSystem {

    /**
     * 店铺编号
     */
    private Integer storeId;

    /**
     * 提成比例
     */
    private Double pushMoneyRate;

    /**
     * 系统开账
     */
    private Byte startBill;

    /**
     * 经手人
     */
    private String userId;

    public SSystem() {
    }

    public SSystem(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Double getPushMoneyRate() {
        return pushMoneyRate;
    }

    public void setPushMoneyRate(Double pushMoneyRate) {
        this.pushMoneyRate = pushMoneyRate;
    }

    public Byte getStartBill() {
        return startBill;
    }

    public void setStartBill(Byte startBill) {
        this.startBill = startBill;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SSystem{" +
                "storeId=" + storeId +
                ", pushMoneyRate=" + pushMoneyRate +
                ", startBill=" + startBill +
                ", userId='" + userId + '\'' +
                '}';
    }
}
