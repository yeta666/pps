package com.yeta.pps.vo;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BankAccountVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR) 
    private Integer storeId;

    /**
     * 科目编号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String id;

    /**
     * 科目名称
     */
    private String name;

    /**
     * 账户类型，1：现金，2：银行卡，3：支付宝，4：微信
     */
    private Byte type;

    /**
     * 期初金额
     */
    private Double openingMoney;

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

    /**
     * 借方发生额
     */
    private Double inMoney;

    /**
     * 贷方发生额
     */
    private Double outMoney;

    /**
     * 期末余额
     */
    private Double balanceMoney;

    /**
     * 经手人
     */
    private String userId;

    public BankAccountVo() {
    }

    public BankAccountVo(Integer storeId) {
        this.storeId = storeId;
    }

    public BankAccountVo(Integer storeId, String id) {
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

    public Double getOpeningMoney() {
        return openingMoney;
    }

    public void setOpeningMoney(Double openingMoney) {
        this.openingMoney = openingMoney;
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

    public Double getInMoney() {
        return inMoney;
    }

    public void setInMoney(Double inMoney) {
        this.inMoney = inMoney;
    }

    public Double getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(Double outMoney) {
        this.outMoney = outMoney;
    }

    public Double getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(Double balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BankAccountVo{" +
                "storeId=" + storeId +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", openingMoney=" + openingMoney +
                ", head='" + head + '\'' +
                ", account='" + account + '\'' +
                ", gathering=" + gathering +
                ", qrCode='" + qrCode + '\'' +
                ", procurement=" + procurement +
                ", inMoney=" + inMoney +
                ", outMoney=" + outMoney +
                ", balanceMoney=" + balanceMoney +
                ", userId='" + userId + '\'' +
                '}';
    }
}