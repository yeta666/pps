package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;

public class UserVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 验证码
     */
    @JsonIgnore
    private String identifyingCode;

    /**
     * 凭证
     */
    private String token;

    /**
     * 部门id
     */
    @JsonIgnore
    private Integer departmentId;

    /**
     * 仓库id
     */
    @JsonIgnore
    private Integer warehouseId;

    /**
     * 角色id
     */
    @JsonIgnore
    private Integer roleId;

    /**
     * 用户id
     */
    private String id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户名
     */
    @JsonIgnore
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 备注
     */
    @JsonIgnore
    private String remark;

    /**
     * 是否禁用，0：不禁用，1：禁用
     */
    @JsonIgnore
    private Integer disabled;

    public UserVo() {
    }

    public UserVo(String id) {
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getIdentifyingCode() {
        return identifyingCode;
    }

    public void setIdentifyingCode(String identifyingCode) {
        this.identifyingCode = identifyingCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "storeId=" + storeId +
                ", identifyingCode='" + identifyingCode + '\'' +
                ", token='" + token + '\'' +
                ", departmentId=" + departmentId +
                ", warehouseId=" + warehouseId +
                ", roleId=" + roleId +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", remark='" + remark + '\'' +
                ", disabled=" + disabled +
                '}';
    }
}