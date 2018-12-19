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

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}