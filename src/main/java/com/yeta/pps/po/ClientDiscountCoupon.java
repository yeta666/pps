package com.yeta.pps.po;

import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClientDiscountCoupon {

    /**
     * 客户/优惠券关系编号
     */
    private Integer id;

    /**
     * 店铺编号
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    /**
     * 客户编号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String clientId;

    /**
     * 优惠券编号
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer discountCouponId;

    /**
     * 数量
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer quantity;

    public ClientDiscountCoupon() {
    }

    public ClientDiscountCoupon(Integer storeId, String clientId) {
        this.storeId = storeId;
        this.clientId = clientId;
    }

    public ClientDiscountCoupon(Integer storeId, String clientId, Integer discountCouponId) {
        this.storeId = storeId;
        this.clientId = clientId;
        this.discountCouponId = discountCouponId;
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

    @Override
    public String toString() {
        return "ClientDiscountCoupon{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", clientId='" + clientId + '\'' +
                ", discountCouponId=" + discountCouponId +
                ", quantity=" + quantity +
                '}';
    }
}