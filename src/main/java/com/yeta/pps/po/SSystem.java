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
     * 零售默认仓库编号
     */
    private Integer retailWarehouseId;

    /**
     * 零售默认银行账户编号
     */
    private String retailBankAccountId;

    /**
     * 经手人
     */
    private String userId;

    public SSystem() {
    }

    public SSystem(Integer storeId) {
        this.storeId = storeId;
    }

    public SSystem(Integer storeId, Integer retailWarehouseId) {
        this.storeId = storeId;
        this.retailWarehouseId = retailWarehouseId;
    }

    public SSystem(Integer storeId, String retailBankAccountId) {
        this.storeId = storeId;
        this.retailBankAccountId = retailBankAccountId;
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

    public Integer getRetailWarehouseId() {
        return retailWarehouseId;
    }

    public void setRetailWarehouseId(Integer retailWarehouseId) {
        this.retailWarehouseId = retailWarehouseId;
    }

    public String getRetailBankAccountId() {
        return retailBankAccountId;
    }

    public void setRetailBankAccountId(String retailBankAccountId) {
        this.retailBankAccountId = retailBankAccountId;
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
                ", retailWarehouseId=" + retailWarehouseId +
                ", retailBankAccountId='" + retailBankAccountId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
