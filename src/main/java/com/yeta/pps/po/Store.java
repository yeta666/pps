package com.yeta.pps.po;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Store {

    /**
     * 店铺编号
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer id;

    /**
     * 店铺名
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String name;

    /**
     * 地址
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String address;

    /**
     * 店长会员编号
     */
    private String clientId;

    /**
     * 剩余短信条数
     */
    private Integer smsQuantity;

    public Store() {
    }

    public Store(Integer id) {
        this.id = id;
    }

    public Store(Integer id, Integer smsQuantity) {
        this.id = id;
        this.smsQuantity = smsQuantity;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getSmsQuantity() {
        return smsQuantity;
    }

    public void setSmsQuantity(Integer smsQuantity) {
        this.smsQuantity = smsQuantity;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", clientId='" + clientId + '\'' +
                ", smsQuantity=" + smsQuantity +
                '}';
    }
}