package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class FunctionVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 功能id集合
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Set<Integer> functionIds;

    public FunctionVo() {
    }

    public FunctionVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String userId, Integer roleId) {
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

    public Set<Integer> getFunctionIds() {
        return functionIds;
    }

    public void setFunctionIds(Set<Integer> functionIds) {
        this.functionIds = functionIds;
    }

    @Override
    public String toString() {
        return "FunctionVo{" +
                "storeId=" + storeId +
                ", userId='" + userId + '\'' +
                ", roleId=" + roleId +
                ", functionIds=" + functionIds +
                '}';
    }
}