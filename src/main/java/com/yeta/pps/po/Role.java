package com.yeta.pps.po;

import javax.persistence.*;

public class Role {

    /**
     * 角色id
     */
    @Id
    private Integer id;

    /**
     * 角色名
     */
    private String name;

    public Role() {
    }

    /**
     * 获取角色id
     *
     * @return id - 角色id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置角色id
     *
     * @param id 角色id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色名
     *
     * @return name - 角色名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名
     *
     * @param name 角色名
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}