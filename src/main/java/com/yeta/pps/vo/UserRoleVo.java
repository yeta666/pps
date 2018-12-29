package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;

public class UserRoleVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    /**
     * id
     */
    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private Integer roleId;

    public UserRoleVo() {
    }

    public UserRoleVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String userId) {
        this.storeId = storeId;
        this.userId = userId;
    }

    public UserRoleVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Integer roleId) {
        this.storeId = storeId;
        this.roleId = roleId;
    }

    public UserRoleVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String userId, Integer roleId) {
        this.storeId = storeId;
        this.userId = userId;
        this.roleId = roleId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRoleVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", userId='" + userId + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}