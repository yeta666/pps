package com.yeta.pps.po;

import javax.persistence.Column;
import javax.persistence.Id;

public class Store {

    /**
     * 店铺id
     */
    @Id
    private Integer id;

    /**
     * 店铺名
     */
    private String name;

    /**
     * 店铺老板id
     */
    @Column(name = "user_id")
    private String userId;

    public Store() {
    }

    public Store(String name) {
        this.name = name;
    }

    public Store(Integer id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public Store(Integer id, String name, String userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    /**
     * 获取店铺id
     *
     * @return id - 店铺id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置店铺id
     *
     * @param id 店铺id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取店铺名
     *
     * @return name - 店铺名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置店铺名
     *
     * @param name 店铺名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取店铺老板id
     *
     * @return user_id - 店铺老板id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置店铺老板id
     *
     * @param userId 店铺老板id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "StoreVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}