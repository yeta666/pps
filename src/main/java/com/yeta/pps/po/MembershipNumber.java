package com.yeta.pps.po;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;

public class MembershipNumber {

    /**
     * 会员卡号编号
     */
    private Integer id;

    /**
     * 会员卡号
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String number;

    /**
     * 是否停用，0：否，1：是
     */
    private Byte disabled;

    public MembershipNumber() {
    }

    public MembershipNumber(Integer id) {
        this.id = id;
    }

    public MembershipNumber(@NotBlank(message = CommonResponse.MESSAGE3) String number) {
        this.number = number;
    }

    public MembershipNumber(@NotBlank(message = CommonResponse.MESSAGE3) String number, Byte disabled) {
        this.number = number;
        this.disabled = disabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Byte getDisabled() {
        return disabled;
    }

    public void setDisabled(Byte disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "MembershipNumber{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", disabled=" + disabled +
                '}';
    }
}