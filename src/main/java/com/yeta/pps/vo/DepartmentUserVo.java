package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class DepartmentUserVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 部门用户id
     */
    private Integer id;

    /**
     * 部门id
     */
    private Integer departmentId;

    /**
     * 用户id
     */
    private String userId;

    public DepartmentUserVo() {
    }

    public DepartmentUserVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Integer departmentId) {
        this.storeId = storeId;
        this.departmentId = departmentId;
    }

    public DepartmentUserVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String userId) {
        this.storeId = storeId;
        this.userId = userId;
    }

    public DepartmentUserVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Integer departmentId, String userId) {
        this.storeId = storeId;
        this.departmentId = departmentId;
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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DepartmentUserVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", departmentId=" + departmentId +
                ", userId='" + userId + '\'' +
                '}';
    }
}