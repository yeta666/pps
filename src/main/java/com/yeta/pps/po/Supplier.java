package com.yeta.pps.po;

public class Supplier {

    /**
     * 供应商编号
     */
    private String id;

    /**
     * 供应商名称
     */
    private String name;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String contactNumber;

    /**
     * 联系地址
     */
    private String contactAddress;

    /**
     * 传真
     */
    private String fax;

    /**
     * 备注
     */
    private String remark;

    public Supplier() {
    }

    /**
     * 获取供应商编号
     *
     * @return id - 供应商编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置供应商编号
     *
     * @param id 供应商编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取供应商名称
     *
     * @return name - 供应商名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置供应商名称
     *
     * @param name 供应商名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取联系人
     *
     * @return contacts - 联系人
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * 设置联系人
     *
     * @param contacts 联系人
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    /**
     * 获取联系电话
     *
     * @return contact_number - 联系电话
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * 设置联系电话
     *
     * @param contactNumber 联系电话
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * 获取联系地址
     *
     * @return contact_address - 联系地址
     */
    public String getContactAddress() {
        return contactAddress;
    }

    /**
     * 设置联系地址
     *
     * @param contactAddress 联系地址
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    /**
     * 获取传真
     *
     * @return fax - 传真
     */
    public String getFax() {
        return fax;
    }

    /**
     * 设置传真
     *
     * @param fax 传真
     */
    public void setFax(String fax) {
        this.fax = fax;
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
        return "Supplier{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", fax='" + fax + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}