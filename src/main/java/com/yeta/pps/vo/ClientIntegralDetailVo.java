package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClientIntegralDetailVo {

    /**
     * 用户id
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String userId;

    /**
     * 客户编号
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String clientId;

    /**
     * 操作类型，0：减少，1：增加
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Byte type;

    /**
     * 改变积分
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer changeIntegral;

    /**
     * 备注
     */
    private String remark;

    public ClientIntegralDetailVo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getChangeIntegral() {
        return changeIntegral;
    }

    public void setChangeIntegral(Integer changeIntegral) {
        this.changeIntegral = changeIntegral;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ClientIntegralDetailVo{" +
                "userId='" + userId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", type=" + type +
                ", changeIntegral=" + changeIntegral +
                ", remark='" + remark + '\'' +
                '}';
    }
}