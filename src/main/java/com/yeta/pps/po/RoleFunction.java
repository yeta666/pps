package com.yeta.pps.po;

public class RoleFunction {

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

    public RoleFunction() {
    }

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取功能id
     *
     * @return function_id - 功能id
     */
    public Integer getFunctionId() {
        return functionId;
    }

    /**
     * 设置功能id
     *
     * @param functionId 功能id
     */
    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    @Override
    public String toString() {
        return "RoleFunction{" +
                ", id=" + id +
                ", roleId=" + roleId +
                ", functionId=" + functionId +
                '}';
    }
}