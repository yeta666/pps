package com.yeta.pps.vo;

import com.yeta.pps.po.Role;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserVo {

    /**
     * 店铺id
     * 用于接收参数
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    /**
     * 验证码
     * 用于接收参数
     */
    private String identifyingCode;

    /**
     * 角色id
     * 用于接收参数，查询的时候
     */
    private Integer roleId;

    /**
     * 凭证
     * 用于返回数据
     */
    private String token;

    /**
     * 仓库名
     * 用于返回数据
     */
    private String warehouseName;

    /**
     * 角色
     * 用于返回数据和接收参数
     */
    List<Role> roles;

    /////////////////////////////////////////

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
     * 仓库id
     */
    private Integer warehouseId;

    /**
     * 是否禁用，0：不禁用，1：禁用
     */
    private Integer disabled;

    /**
     * 备注
     */
    private String remark;

    public UserVo() {
    }

    public UserVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId) {
        this.storeId = storeId;
    }

    public UserVo(String id) {
        this.id = id;
    }

    public UserVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String id) {
        this.storeId = storeId;
        this.id = id;
    }

    public UserVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Integer roleId, String name) {
        this.storeId = storeId;
        this.roleId = roleId;
        this.name = name;
    }

    public UserVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Integer warehouseId) {
        this.storeId = storeId;
        this.warehouseId = warehouseId;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "storeId=" + storeId +
                ", identifyingCode='" + identifyingCode + '\'' +
                ", roleId=" + roleId +
                ", token='" + token + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", roles=" + roles +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", warehouseId=" + warehouseId +
                ", disabled=" + disabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}