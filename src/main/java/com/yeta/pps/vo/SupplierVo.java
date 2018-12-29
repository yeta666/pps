package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SupplierVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    /**
     * 供应商编号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String id;

    /**
     * 供应商名称
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String name;

    /**
     * 联系人
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String contacts;

    /**
     * 联系电话
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String contactNumber;

    /**
     * 联系地址
     */
    private String contactAddress;

    /**
     * 传真
     */
    private String fax;

    /**
     * 备注
     */
    private String remark;

    public SupplierVo() {
    }

    public SupplierVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, @NotBlank(message = CommonResponse.PARAMETER_ERROR) String id, @NotBlank(message = CommonResponse.PARAMETER_ERROR) String name) {
        this.storeId = storeId;
        this.id = id;
        this.name = name;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SupplierVo{" +
                "storeId=" + storeId +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", fax='" + fax + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}