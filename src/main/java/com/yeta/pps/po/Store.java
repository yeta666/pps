package com.yeta.pps.po;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;

public class Store {

    /**
     * 店铺编号
     */
    private Integer id;

    /**
     * 店铺名
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String name;

    /**
     * 地址
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String address;

    /**
     * 店长
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String boss;

    /**
     * 电话
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String phone;

    public Store() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", boss='" + boss + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}