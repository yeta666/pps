package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class FundTargetCheckOrderVo {

    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    /**
     * 往来对账记录编号
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String orderId;

    private String typeName;

    /**
     * 单据日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @JsonIgnore
    private Date startTime;

    @JsonIgnore
    private Date endTime;

    /**
     * 单据状态
     */
    private Byte orderStatus;

    private String clearOrderId;

    private String applyOrderId;

    /**
     * 往来单位编号
     */
    private String targetId;

    private String targetName;

    private String targetPhone;

    /**
     * 期初应收
     */
    private Double needInMoneyOpening = 0.0;

    /**
     * 应收增加
     */
    private Double needInMoneyIncrease = 0.0;

    /**
     * 应收减少
     */
    private Double needInMoneyDecrease = 0.0;

    /**
     * 期末应收
     */
    private Double needInMoney = 0.0;

    /**
     * 期初预收
     */
    private Double advanceInMoneyOpening = 0.0;

    /**
     * 预收增加
     */
    private Double advanceInMoneyIncrease = 0.0;

    /**
     * 预收减少
     */
    private Double advanceInMoneyDecrease = 0.0;

    /**
     * 期末预收
     */
    private Double advanceInMoney = 0.0;

    /**
     * 期初应付
     */
    private Double needOutMoneyOpening = 0.0;

    /**
     * 应付增加
     */
    private Double needOutMoneyIncrease = 0.0;

    /**
     * 应付减少
     */
    private Double needOutMoneyDecrease = 0.0;

    /**
     * 期末应付
     */
    private Double needOutMoney = 0.0;

    /**
     * 期初预付
     */
    private Double advanceOutMoneyOpening = 0.0;

    /**
     * 预付增加
     */
    private Double advanceOutMoneyIncrease = 0.0;

    /**
     * 预付减少
     */
    private Double advanceOutMoneyDecrease = 0.0;

    /**
     * 期末预付
     */
    private Double advanceOutMoney = 0.0;

    /**
     * 期末余额
     */
    private Double endingMoney;

    /**
     * 经手人编号
     */
    private String userId;

    private String userName;

    private String remark;

    @JsonIgnore
    private Integer flag;

    public FundTargetCheckOrderVo() {
    }

    public FundTargetCheckOrderVo(Integer storeId, String targetId) {
        this.storeId = storeId;
        this.targetId = targetId;
    }

    public FundTargetCheckOrderVo(Integer storeId, Date startTime, Date endTime) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public FundTargetCheckOrderVo(Integer storeId, Date startTime, Date endTime, String targetId) {
        this.storeId = storeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.targetId = targetId;
    }

    public FundTargetCheckOrderVo(Integer storeId, String orderId, String userId) {
        this.storeId = storeId;
        this.orderId = orderId;
        this.userId = userId;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getClearOrderId() {
        return clearOrderId;
    }

    public void setClearOrderId(String clearOrderId) {
        this.clearOrderId = clearOrderId;
    }

    public String getApplyOrderId() {
        return applyOrderId;
    }

    public void setApplyOrderId(String applyOrderId) {
        this.applyOrderId = applyOrderId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetPhone() {
        return targetPhone;
    }

    public void setTargetPhone(String targetPhone) {
        this.targetPhone = targetPhone;
    }

    public Double getNeedInMoneyOpening() {
        return needInMoneyOpening;
    }

    public void setNeedInMoneyOpening(Double needInMoneyOpening) {
        this.needInMoneyOpening = needInMoneyOpening;
    }

    public Double getNeedInMoneyIncrease() {
        return needInMoneyIncrease;
    }

    public void setNeedInMoneyIncrease(Double needInMoneyIncrease) {
        this.needInMoneyIncrease = needInMoneyIncrease;
    }

    public Double getNeedInMoneyDecrease() {
        return needInMoneyDecrease;
    }

    public void setNeedInMoneyDecrease(Double needInMoneyDecrease) {
        this.needInMoneyDecrease = needInMoneyDecrease;
    }

    public Double getNeedInMoney() {
        return needInMoney;
    }

    public void setNeedInMoney(Double needInMoney) {
        this.needInMoney = needInMoney;
    }

    public Double getAdvanceInMoneyOpening() {
        return advanceInMoneyOpening;
    }

    public void setAdvanceInMoneyOpening(Double advanceInMoneyOpening) {
        this.advanceInMoneyOpening = advanceInMoneyOpening;
    }

    public Double getAdvanceInMoneyIncrease() {
        return advanceInMoneyIncrease;
    }

    public void setAdvanceInMoneyIncrease(Double advanceInMoneyIncrease) {
        this.advanceInMoneyIncrease = advanceInMoneyIncrease;
    }

    public Double getAdvanceInMoneyDecrease() {
        return advanceInMoneyDecrease;
    }

    public void setAdvanceInMoneyDecrease(Double advanceInMoneyDecrease) {
        this.advanceInMoneyDecrease = advanceInMoneyDecrease;
    }

    public Double getAdvanceInMoney() {
        return advanceInMoney;
    }

    public void setAdvanceInMoney(Double advanceInMoney) {
        this.advanceInMoney = advanceInMoney;
    }

    public Double getNeedOutMoneyOpening() {
        return needOutMoneyOpening;
    }

    public void setNeedOutMoneyOpening(Double needOutMoneyOpening) {
        this.needOutMoneyOpening = needOutMoneyOpening;
    }

    public Double getNeedOutMoneyIncrease() {
        return needOutMoneyIncrease;
    }

    public void setNeedOutMoneyIncrease(Double needOutMoneyIncrease) {
        this.needOutMoneyIncrease = needOutMoneyIncrease;
    }

    public Double getNeedOutMoneyDecrease() {
        return needOutMoneyDecrease;
    }

    public void setNeedOutMoneyDecrease(Double needOutMoneyDecrease) {
        this.needOutMoneyDecrease = needOutMoneyDecrease;
    }

    public Double getNeedOutMoney() {
        return needOutMoney;
    }

    public void setNeedOutMoney(Double needOutMoney) {
        this.needOutMoney = needOutMoney;
    }

    public Double getAdvanceOutMoneyOpening() {
        return advanceOutMoneyOpening;
    }

    public void setAdvanceOutMoneyOpening(Double advanceOutMoneyOpening) {
        this.advanceOutMoneyOpening = advanceOutMoneyOpening;
    }

    public Double getAdvanceOutMoneyIncrease() {
        return advanceOutMoneyIncrease;
    }

    public void setAdvanceOutMoneyIncrease(Double advanceOutMoneyIncrease) {
        this.advanceOutMoneyIncrease = advanceOutMoneyIncrease;
    }

    public Double getAdvanceOutMoneyDecrease() {
        return advanceOutMoneyDecrease;
    }

    public void setAdvanceOutMoneyDecrease(Double advanceOutMoneyDecrease) {
        this.advanceOutMoneyDecrease = advanceOutMoneyDecrease;
    }

    public Double getAdvanceOutMoney() {
        return advanceOutMoney;
    }

    public void setAdvanceOutMoney(Double advanceOutMoney) {
        this.advanceOutMoney = advanceOutMoney;
    }

    public Double getEndingMoney() {
        return endingMoney;
    }

    public void setEndingMoney(Double endingMoney) {
        this.endingMoney = endingMoney;
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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "FundTargetCheckOrderVo{" +
                "storeId=" + storeId +
                ", id=" + id +
                ", orderId='" + orderId + '\'' +
                ", typeName='" + typeName + '\'' +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", orderStatus=" + orderStatus +
                ", clearOrderId='" + clearOrderId + '\'' +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", targetId='" + targetId + '\'' +
                ", targetName='" + targetName + '\'' +
                ", targetPhone='" + targetPhone + '\'' +
                ", needInMoneyOpening=" + needInMoneyOpening +
                ", needInMoneyIncrease=" + needInMoneyIncrease +
                ", needInMoneyDecrease=" + needInMoneyDecrease +
                ", needInMoney=" + needInMoney +
                ", advanceInMoneyOpening=" + advanceInMoneyOpening +
                ", advanceInMoneyIncrease=" + advanceInMoneyIncrease +
                ", advanceInMoneyDecrease=" + advanceInMoneyDecrease +
                ", advanceInMoney=" + advanceInMoney +
                ", needOutMoneyOpening=" + needOutMoneyOpening +
                ", needOutMoneyIncrease=" + needOutMoneyIncrease +
                ", needOutMoneyDecrease=" + needOutMoneyDecrease +
                ", needOutMoney=" + needOutMoney +
                ", advanceOutMoneyOpening=" + advanceOutMoneyOpening +
                ", advanceOutMoneyIncrease=" + advanceOutMoneyIncrease +
                ", advanceOutMoneyDecrease=" + advanceOutMoneyDecrease +
                ", advanceOutMoney=" + advanceOutMoney +
                ", endingMoney=" + endingMoney +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}