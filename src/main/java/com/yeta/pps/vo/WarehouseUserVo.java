package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class WarehouseUserVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 仓库用户id
     */
    private Integer id;

    /**
     * 仓库id
     */
    private Integer warehouseId;

    /**
     * 用户id
     */
    private String userId;

    public WarehouseUserVo() {
    }

    public WarehouseUserVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Integer warehouseId) {
        this.storeId = storeId;
        this.warehouseId = warehouseId;
    }

    public WarehouseUserVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String userId) {
        this.storeId = storeId;
        this.userId = userId;
    }

    public WarehouseUserVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Integer warehouseId, String userId) {
        this.storeId = storeId;
        this.warehouseId = warehouseId;
        this.userId = userId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "WarehouseUserVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", warehouseId=" + warehouseId +
                ", userId='" + userId + '\'' +
                '}';
    }
}