package com.yeta.pps.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeta.pps.util.CommonResponse;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class Client {

    /**
     * 客户编号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String id;

    /**
     * 姓名
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String password;

    /**
     * 电话
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String phone;

    /**
     * 客户级别编号
     */
    private Integer levelId;

    /**
     * 客户生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 邀请人编号
     */
    private String inviterId;

    /**
     * 地址
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastDealTime;

    /**
     * 创建时间
     */
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

    public Client() {
    }

    public Client(String id, String phone) {
        this.id = id;
        this.phone = phone;
    }

    public Client(String name, String phone, String membershipNumber) {
        this.name = name;
        this.phone = phone;
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

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
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
                ", levelId=" + levelId +
                ", birthday=" + birthday +
                ", inviterId='" + inviterId + '\'' +
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