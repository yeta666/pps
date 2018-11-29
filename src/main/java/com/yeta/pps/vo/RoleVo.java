package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;

public class RoleVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名
     */
    private String name;

    public RoleVo() {
    }

    public RoleVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId) {
        this.storeId = storeId;
    }

    public RoleVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Integer id) {
        this.storeId = storeId;
        this.id = id;
    }

    public RoleVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String name) {
        this.storeId = storeId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}