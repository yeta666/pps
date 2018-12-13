package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeta.pps.po.BankAccount;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class FundOrderVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 单据编号
     */
    private String id;

    /**
     * 单单据类型，1：付款单，2：收款单
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Byte type;

    /**
     * 单据日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 来源订单
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String applyOrderId;

    private ProcurementApplyOrderVo procurementApplyOrderVo;

    /**
     * 单据状态
     */
    private Byte orderStatus;

    /**
     * 银行账户编号
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String bankAccountId;

    private BankAccount bankAccount;

    /**
     * 金额
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private BigDecimal money;

    /**
     * 经手人
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String userId;

    private String userName;

    /**
     * 单据备注
     */
    private String remark;

    public FundOrderVo() {
    }

    public FundOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id, @NotNull(message = CommonResponse.MESSAGE3) Byte type, ProcurementApplyOrderVo procurementApplyOrderVo) {
        this.storeId = storeId;
        this.id = id;
        this.type = type;
        this.procurementApplyOrderVo = procurementApplyOrderVo;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getApplyOrderId() {
        return applyOrderId;
    }

    public void setApplyOrderId(String applyOrderId) {
        this.applyOrderId = applyOrderId;
    }

    public ProcurementApplyOrderVo getProcurementApplyOrderVo() {
        return procurementApplyOrderVo;
    }

    public void setProcurementApplyOrderVo(ProcurementApplyOrderVo procurementApplyOrderVo) {
        this.procurementApplyOrderVo = procurementApplyOrderVo;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FundOrderVo{" +
                "storeId=" + storeId +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", procurementApplyOrderVo=" + procurementApplyOrderVo +
                ", orderStatus=" + orderStatus +
                ", bankAccountId='" + bankAccountId + '\'' +
                ", money=" + money +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}