package com.yeta.pps.po;

public class ClientClientMembershipNumber {

    /**
     * 客户会员卡号id
     */
    private Integer id;

    /**
     * 客户编号
     */
    private String clientId;

    /**
     * 会员卡号id
     */
    private Integer memebershipNumberId;

    public ClientClientMembershipNumber() {
    }

    public ClientClientMembershipNumber(String clientId) {
        this.clientId = clientId;
    }

    public ClientClientMembershipNumber(Integer memebershipNumberId) {
        this.memebershipNumberId = memebershipNumberId;
    }

    public ClientClientMembershipNumber(String clientId, Integer memebershipNumberId) {
        this.clientId = clientId;
        this.memebershipNumberId = memebershipNumberId;
    }

    /**
     * 获取客户客户级别id
     *
     * @return id - 客户客户级别id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置客户客户级别id
     *
     * @param id 客户客户级别id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取客户编号
     *
     * @return client_id - 客户编号
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 设置客户编号
     *
     * @param clientId 客户编号
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getMemebershipNumberId() {
        return memebershipNumberId;
    }

    public void setMemebershipNumberId(Integer memebershipNumberId) {
        this.memebershipNumberId = memebershipNumberId;
    }

    @Override
    public String toString() {
        return "ClientClientMembershipNumber{" +
                "id=" + id +
                ", clientId='" + clientId + '\'' +
                ", memebershipNumberId=" + memebershipNumberId +
                '}';
    }
}