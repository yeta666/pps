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
    //private Integer departmentId;

    /**
     * 部门名
     */
    //private String departmentName;

    /**
     * 仓库id
     */
    private Integer warehouseId;

    /**
     * 仓库名
     */
    private String warehouseName;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色名
     */
    private String roleName;

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
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话
     */
    private String phone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否禁用，0：不禁用，1：禁用
     */
    private Integer disabled;

    public UserVo() {
    }

    public UserVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, Integer roleId, String name) {
        this.storeId = storeId;
        this.roleId = roleId;
        this.name = name;
    }

    public UserVo(String id) {
        this.id = id;
    }

    public UserVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id) {
        this.storeId = storeId;
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

    /*public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }*/

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
                /*", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +*/
                ", warehouseId=" + warehouseId +
                ", warehouseName='" + warehouseName + '\'' +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", remark='" + remark + '\'' +
                ", disabled=" + disabled +
                '}';
    }
}