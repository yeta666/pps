package com.yeta.pps.po;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;

public class ClientMembershipNumber {

    /**
     * 会员卡号id
     */
    private Integer id;

    /**
     * 会员卡号
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String number;

    public ClientMembershipNumber() {
    }

    public ClientMembershipNumber(Integer id) {
        this.id = id;
    }

    public ClientMembershipNumber(String number) {
        this.number = number;
    }

    /**
     * 获取会员卡号id
     *
     * @return id - 会员卡号id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置会员卡号id
     *
     * @param id 会员卡号id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取会员卡号
     *
     * @return number - 会员卡号
     */
    public String getNumber() {
        return number;
    }

    /**
     * 设置会员卡号
     *
     * @param number 会员卡号
     */
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ClientMembershipNumber{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}