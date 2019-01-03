package com.yeta.pps.vo;

public class ClientDiscountCouponVo {

    /**
     * 客户/优惠券关系编号
     */
    private Integer id;

    /**
     * 店铺编号
     */
    private Integer storeId;

    private String storeName;

    /**
     * 客户编号
     */
    private String clientId;

    /**
     * 优惠券编号
     */
    private Integer discountCouponId;

    /**
     * 数量
     */
    private Integer quantity;

    private String name;

    private String typeName;

    private String useMethod;

    private String faceValue;

    private String useOfflineName;

    private String useOnlineName;

    private String statusName;

    public ClientDiscountCouponVo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getDiscountCouponId() {
        return discountCouponId;
    }

    public void setDiscountCouponId(Integer discountCouponId) {
        this.discountCouponId = discountCouponId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUseMethod() {
        return useMethod;
    }

    public void setUseMethod(String useMethod) {
        this.useMethod = useMethod;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public String getUseOfflineName() {
        return useOfflineName;
    }

    public void setUseOfflineName(String useOfflineName) {
        this.useOfflineName = useOfflineName;
    }

    public String getUseOnlineName() {
        return useOnlineName;
    }

    public void setUseOnlineName(String useOnlineName) {
        this.useOnlineName = useOnlineName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "ClientDiscountCouponVo{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", clientId='" + clientId + '\'' +
                ", discountCouponId=" + discountCouponId +
                ", quantity=" + quantity +
                ", name='" + name + '\'' +
                ", typeName='" + typeName + '\'' +
                ", useMethod='" + useMethod + '\'' +
                ", faceValue='" + faceValue + '\'' +
                ", useOfflineName='" + useOfflineName + '\'' +
                ", useOnlineName='" + useOnlineName + '\'' +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}