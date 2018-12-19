package com.yeta.pps.vo;

import com.yeta.pps.po.Store;

/**
 * @author YETA
 * @date 2018/12/18/21:35
 */
public class StoreIntegralVo {

    private Store store;

    private ClientVo clientVo;

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

    public StoreIntegralVo() {
    }

    public StoreIntegralVo(Integer storeId, String clientId) {
        this.storeId = storeId;
        this.clientId = clientId;
    }

    public StoreIntegralVo(Integer storeId, String clientId, Integer integral) {
        this.storeId = storeId;
        this.clientId = clientId;
        this.integral = integral;
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
        return "StoreIntegralVo{" +
                "store=" + store +
                ", clientVo=" + clientVo +
                ", id='" + id + '\'' +
                ", storeId=" + storeId +
                ", clientId='" + clientId + '\'' +
                ", integral=" + integral +
                '}';
    }
}
