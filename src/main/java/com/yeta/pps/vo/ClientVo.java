package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ClientVo {

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastDealTime;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 是否停用，0：否，1：是
     */
    private Byte disabled;

    /**
     * 备注
     */
    private String remark;

    /**
     * 客户级别id
     */
    private Integer levelId;

    /**
     * 客户级别
     */
    private String levelName;

    /**
     * 会员卡号id
     */
    private Integer membershipNumberId;

    /**
     * 会员卡号
     */
    private String membershipNumber;

    public ClientVo() {
    }

    public ClientVo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClientVo(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public ClientVo(String id, String name, String phone, Integer levelId, String membershipNumber) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.levelId = levelId;
        this.membershipNumber = membershipNumber;
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

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getMembershipNumberId() {
        return membershipNumberId;
    }

    public void setMembershipNumberId(Integer membershipNumberId) {
        this.membershipNumberId = membershipNumberId;
    }

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(String membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    @Override
    public String toString() {
        return "ClientVo{" +
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
                ", levelId=" + levelId +
                ", levelName='" + levelName + '\'' +
                ", membershipNumberId=" + membershipNumberId +
                ", membershipNumber='" + membershipNumber + '\'' +
                '}';
    }
}