package com.yeta.pps.po;

public class DepartmentUser {

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

    public DepartmentUser() {
    }

    /**
     * 获取部门用户id
     *
     * @return id - 部门用户id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置部门用户id
     *
     * @param id 部门用户id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取部门id
     *
     * @return department_id - 部门id
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     * 设置部门id
     *
     * @param departmentId 部门id
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DepartmentUser{" +
                "id=" + id +
                ", departmentId=" + departmentId +
                ", userId='" + userId + '\'' +
                '}';
    }
}