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

    /**
     * 获取客户编号
     *
     * @return id - 客户编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置客户编号
     *
     * @param id 客户编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取客户姓名
     *
     * @return name - 客户姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置客户姓名
     *
     * @param name 客户姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取客户用户名
     *
     * @return username - 客户用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置客户用户名
     *
     * @param username 客户用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取客户密码
     *
     * @return password - 客户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置客户密码
     *
     * @param password 客户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取客户电话
     *
     * @return phone - 客户电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置客户电话
     *
     * @param phone 客户电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取客户生日
     *
     * @return birthday - 客户生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置客户生日
     *
     * @param birthday 客户生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取邀请人
     *
     * @return inviter_id - 邀请人
     */
    public String getInviterId() {
        return inviterId;
    }

    /**
     * 设置邀请人
     *
     * @param inviterId 邀请人
     */
    public void setInviterId(String inviterId) {
        this.inviterId = inviterId;
    }

    /**
     * 获取邀请人姓名
     *
     * @return inviter_name - 邀请人姓名
     */
    public String getInviterName() {
        return inviterName;
    }

    /**
     * 设置邀请人姓名
     *
     * @param inviterName 邀请人姓名
     */
    public void setInviterName(String inviterName) {
        this.inviterName = inviterName;
    }

    /**
     * 获取积分
     *
     * @return integral - 积分
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * 设置积分
     *
     * @param integral 积分
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * 获取客户地址
     *
     * @return address - 客户地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置客户地址
     *
     * @param address 客户地址
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
     * 获取最近交易时间
     *
     * @return last_deal_time - 最近交易时间
     */
    public Date getLastDealTime() {
        return lastDealTime;
    }

    /**
     * 设置最近交易时间
     *
     * @param lastDealTime 最近交易时间
     */
    public void setLastDealTime(Date lastDealTime) {
        this.lastDealTime = lastDealTime;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取是否停用，0：否，1：是
     *
     * @return disabled - 是否停用，0：否，1：是
     */
    public Byte getDisabled() {
        return disabled;
    }

    /**
     * 设置是否停用，0：否，1：是
     *
     * @param disabled 是否停用，0：否，1：是
     */
    public void setDisabled(Byte disabled) {
        this.disabled = disabled;
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
                ", lastDealTime=" + lastDealTime +
                ", createTime=" + createTime +
                ", disabled=" + disabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}