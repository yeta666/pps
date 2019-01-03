package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeta.pps.util.CommonResponse;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class DiscountCouponVo {

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

    private String typeName;

    /**
     * 金额/折扣
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Double money;

    /**
     * 满减券与money搭配使用
     */
    private Double discountMoney;

    private String useMethod;

    private String faceValue;

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

    private String useOfflineName;

    /**
     * 线上使用，0：否，1：是
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Byte useOnline;

    private String useOnlineName;

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

    private String statusName;

    public DiscountCouponVo() {
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getUseOfflineName() {
        return useOfflineName;
    }

    public void setUseOfflineName(String useOfflineName) {
        this.useOfflineName = useOfflineName;
    }

    public Byte getUseOnline() {
        return useOnline;
    }

    public void setUseOnline(Byte useOnline) {
        this.useOnline = useOnline;
    }

    public String getUseOnlineName() {
        return useOnlineName;
    }

    public void setUseOnlineName(String useOnlineName) {
        this.useOnlineName = useOnlineName;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "DiscountCouponVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", typeName='" + typeName + '\'' +
                ", money=" + money +
                ", discountMoney=" + discountMoney +
                ", useMethod='" + useMethod + '\'' +
                ", faceValue='" + faceValue + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", useOffline=" + useOffline +
                ", useOfflineName='" + useOfflineName + '\'' +
                ", useOnline=" + useOnline +
                ", useOnlineName='" + useOnlineName + '\'' +
                ", quantity=" + quantity +
                ", givenQuantity=" + givenQuantity +
                ", usedQuantity=" + usedQuantity +
                ", status=" + status +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}