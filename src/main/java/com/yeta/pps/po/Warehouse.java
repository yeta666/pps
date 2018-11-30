package com.yeta.pps.po;

import javax.persistence.*;

public class Warehouse {

    /**
     * 仓库id
     */
    @Id
    private Integer id;

    /**
     * 仓库名
     */
    private String name;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    @Column(name = "contact_number")
    private String contactNumber;

    /**
     * 地址
     */
    private String address;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 备注
     */
    private String remark;

    public Warehouse() {
    }

    /**
     * 获取仓库id
     *
     * @return id - 仓库id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置仓库id
     *
     * @param id 仓库id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取仓库名
     *
     * @return name - 仓库名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置仓库名
     *
     * @param name 仓库名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取联系人
     *
     * @return contacts - 联系人
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * 设置联系人
     *
     * @param contacts 联系人
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    /**
     * 获取联系电话
     *
     * @return contact_number - 联系电话
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * 设置联系电话
     *
     * @param contactNumber 联系电话
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取邮编
     *
     * @return postcode - 邮编
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * 设置邮编
     *
     * @param postcode 邮编
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", address='" + address + '\'' +
                ", postcode='" + postcode + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}