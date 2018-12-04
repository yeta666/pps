package com.yeta.pps.po;

public class BankAccount {

    /**
     * 科目编号
     */
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

    public BankAccount() {
    }

    /**
     * 获取科目编号
     *
     * @return id - 科目编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置科目编号
     *
     * @param id 科目编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取科目名称
     *
     * @return name - 科目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置科目名称
     *
     * @param name 科目名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取账户类型，1：现金，2：银行卡，3：支付宝，4：微信
     *
     * @return type - 账户类型，1：现金，2：银行卡，3：支付宝，4：微信
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置账户类型，1：现金，2：银行卡，3：支付宝，4：微信
     *
     * @param type 账户类型，1：现金，2：银行卡，3：支付宝，4：微信
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取户主名
     *
     * @return head - 户主名
     */
    public String getHead() {
        return head;
    }

    /**
     * 设置户主名
     *
     * @param head 户主名
     */
    public void setHead(String head) {
        this.head = head;
    }

    /**
     * 获取账户
     *
     * @return account - 账户
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账户
     *
     * @param account 账户
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取是否用于商城收款，0：否，1：是
     *
     * @return gathering - 是否用于商城收款，0：否，1：是
     */
    public Byte getGathering() {
        return gathering;
    }

    /**
     * 设置是否用于商城收款，0：否，1：是
     *
     * @param gathering 是否用于商城收款，0：否，1：是
     */
    public void setGathering(Byte gathering) {
        this.gathering = gathering;
    }

    /**
     * 获取收款码
     *
     * @return qrCode - 收款码
     */
    public String getQrCode() {
        return qrCode;
    }

    /**
     * 设置收款码
     *
     * @param qrCode 收款码
     */
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    /**
     * 获取是否用于订货平台，0：否，1：是
     *
     * @return procurement - 是否用于订货平台，0：否，1：是
     */
    public Byte getProcurement() {
        return procurement;
    }

    /**
     * 设置是否用于订货平台，0：否，1：是
     *
     * @param procurement 是否用于订货平台，0：否，1：是
     */
    public void setProcurement(Byte procurement) {
        this.procurement = procurement;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id='" + id + '\'' +
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