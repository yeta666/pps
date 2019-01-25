package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;

public class StoreVo {

    /**
     * 店铺编号
     */
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
     * 店长姓名
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String clientName;

    /**
     * 店长电话
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String clientPhone;

    /**
     * 店长会员卡号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String clientMembershipNumber;

    public StoreVo() {
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientMembershipNumber() {
        return clientMembershipNumber;
    }

    public void setClientMembershipNumber(String clientMembershipNumber) {
        this.clientMembershipNumber = clientMembershipNumber;
    }

    @Override
    public String toString() {
        return "StoreVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", clientMembershipNumber='" + clientMembershipNumber + '\'' +
                '}';
    }
}