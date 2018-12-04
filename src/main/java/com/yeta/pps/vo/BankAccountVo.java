package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BankAccountVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 科目编号
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String id;

    /**
     * 科目名称
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String name;

    /**
     * 账户类型，1：现金，2：银行卡，3：支付宝，4：微信
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Byte type;

    /**
     * 户主名
     */
    private String head;

    /**
     * 账户
     */
    private String account;

    /**
     * 是否用于商城收款，0：否，1：是
     */
    private Byte gathering;

    /**
     * 收款码
     */
    private String qrCode;

    /**
     * 是否用于订货平台，0：否，1：是
     */
    private Byte procurement;

    public BankAccountVo() {
    }

    public BankAccountVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId) {
        this.storeId = storeId;
    }

    public BankAccountVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, @NotBlank(message = CommonResponse.MESSAGE3) String id) {
        this.storeId = storeId;
        this.id = id;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Byte getGathering() {
        return gathering;
    }

    public void setGathering(Byte gathering) {
        this.gathering = gathering;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Byte getProcurement() {
        return procurement;
    }

    public void setProcurement(Byte procurement) {
        this.procurement = procurement;
    }

    @Override
    public String toString() {
        return "BankAccountVo{" +
                "storeId=" + storeId +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", head='" + head + '\'' +
                ", account='" + account + '\'' +
                ", gathering=" + gathering +
                ", qrCode='" + qrCode + '\'' +
                ", procurement=" + procurement +
                '}';
    }
}