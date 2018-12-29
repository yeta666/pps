package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;

public class RoleFunctionVo {

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
     * 角色id
     */
    private Integer roleId;

    /**
     * 功能id
     */
    private Integer functionId;

    public RoleFunctionVo() {
    }

    public RoleFunctionVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Integer roleId) {
        this.storeId = storeId;
        this.roleId = roleId;
    }

    public RoleFunctionVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Integer roleId, Integer functionId) {
        this.storeId = storeId;
        this.roleId = roleId;
        this.functionId = functionId;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    @Override
    public String toString() {
        return "RoleFunctionVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", roleId=" + roleId +
                ", functionId=" + functionId +
                '}';
    }
}