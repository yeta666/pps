package com.yeta.pps.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeta.pps.util.CommonResponse;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DiscountCoupon {

    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    /**
     * 优惠券编号
     */
    private Integer id;

    /**
     * 名称
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String name;

    /**
     * 类型，1：现金券，2：折扣券，3：满减券
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Byte type;

    /**
     * 金额/折扣
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Double money;

    /**
     * 满减券与money搭配使用
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Double discountMoney;

    /**
     * 开始时间
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 线下使用，0：否，1：是
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Byte useOffline;

    /**
     * 线上使用，0：否，1：是
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Byte useOnline;

    /**
     * 数量
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer quantity;

    /**
     * 已发放数量
     */
    private Integer givenQuantity;

    /**
     * 已使用数量
     */
    private Integer usedQuantity;

    /**
     * 状态，0：已作废，1：正常
     */
    private Byte status;

    public DiscountCoupon() {
    }

    public DiscountCoupon(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Integer id) {
        this.storeId = storeId;
        this.id = id;
    }

    public DiscountCoupon(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, Integer id, Integer givenQuantity) {
        this.storeId = storeId;
        this.id = id;
        this.givenQuantity = givenQuantity;
    }

    public DiscountCoupon(Integer storeId, String name, Byte type) {
        this.storeId = storeId;
        this.name = name;
        this.type = type;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Double discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Byte getUseOffline() {
        return useOffline;
    }

    public void setUseOffline(Byte useOffline) {
        this.useOffline = useOffline;
    }

    public Byte getUseOnline() {
        return useOnline;
    }

    public void setUseOnline(Byte useOnline) {
        this.useOnline = useOnline;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getGivenQuantity() {
        return givenQuantity;
    }

    public void setGivenQuantity(Integer givenQuantity) {
        this.givenQuantity = givenQuantity;
    }

    public Integer getUsedQuantity() {
        return usedQuantity;
    }

    public void setUsedQuantity(Integer usedQuantity) {
        this.usedQuantity = usedQuantity;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DiscountCoupon{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", money=" + money +
                ", discountMoney=" + discountMoney +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", useOffline=" + useOffline +
                ", useOnline=" + useOnline +
                ", quantity=" + quantity +
                ", givenQuantity=" + givenQuantity +
                ", usedQuantity=" + usedQuantity +
                ", status=" + status +
                '}';
    }
}