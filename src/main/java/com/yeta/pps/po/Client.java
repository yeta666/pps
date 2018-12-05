package com.yeta.pps.po;

import java.util.Date;

public class Client {

    /**
     * 客户编号
     */
    private String id;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 客户用户名
     */
    private String username;

    /**
     * 客户密码
     */
    private String password;

    /**
     * 客户电话
     */
    private String phone;

    /**
     * 客户生日
     */
    private Date birthday;

    /**
     * 邀请人
     */
    private String inviterId;

    /**
     * 邀请人姓名
     */
    private String inviterName;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 客户地址
     */
    private String address;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 会员卡号
     */
    private String membershipNumber;

    /**
     * 最近交易时间
     */
    private Date lastDealTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否停用，0：否，1：是
     */
    private Byte disabled;

    /**
     * 备注
     */
    private String remark;

    public Client() {
    }

    public Client(String id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getInviterId() {
        return inviterId;
    }

    public void setInviterId(String inviterId) {
        this.inviterId = inviterId;
    }

    public String getInviterName() {
        return inviterName;
    }

    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public Date getLastDealTime() {
        return lastDealTime;
    }

    public void setLastDealTime(Date lastDealTime) {
        this.lastDealTime = lastDealTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getDisabled() {
        return disabled;
    }

    public void setDisabled(Byte disabled) {
        this.disabled = disabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday +
                ", inviterId='" + inviterId + '\'' +
                ", inviterName='" + inviterName + '\'' +
                ", integral=" + integral +
                ", address='" + address + '\'' +
                ", postcode='" + postcode + '\'' +
                ", membershipNumber='" + membershipNumber + '\'' +
                ", lastDealTime=" + lastDealTime +
                ", createTime=" + createTime +
                ", disabled=" + disabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}