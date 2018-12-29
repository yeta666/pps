package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class SellResultOrderVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    private SellApplyOrderVo sellApplyOrderVo;

    private List<OrderGoodsSkuVo> details;

    private String userName;

    /**
     * 单据编号
     */
    private String id;

    /**
     * 单据类型，1：零售单，2：销售出库单，3：销售退货单，4：销售换货单
     */
    private Byte type;

    /**
     * 单据日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 来源订单
     */
    private String applyOrderId;

    /**
     * 单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲
     */
    private Byte orderStatus;

    /**
     * 总商品数量
     */
    private Integer totalQuantity;

    /**
     * 总商品金额
     */
    private Double totalMoney;

    /**
     * 总优惠金额
     */
    private Double totalDiscountMoney;

    /**
     * 本单金额
     */
    private Double orderMoney;

    /**
     * 成本
     */
    private Double costMoney;

    /**
     * 毛利
     */
    private Double grossMarginMoney;

    /**
     * 经手人
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String userId;

    /**
     * 单据备注
     */
    private String remark;

    public SellResultOrderVo() {
    }

    public SellResultOrderVo(Double costMoney) {
        this.costMoney = costMoney;
    }

    public SellResultOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String id) {
        this.storeId = storeId;
        this.id = id;
    }

    public SellResultOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String id, @NotBlank(message = CommonResponse.PARAMETER_ERROR) String userId, String remark) {
        this.storeId = storeId;
        this.id = id;
        this.userId = userId;
        this.remark = remark;
    }

    public SellResultOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, SellApplyOrderVo sellApplyOrderVo, String id) {
        this.storeId = storeId;
        this.sellApplyOrderVo = sellApplyOrderVo;
        this.id = id;
    }

    public SellResultOrderVo(@NotNull(message = CommonResponse.PARAMETER_ERROR) Integer storeId, String id, Byte type, Date createTime, String applyOrderId, Byte orderStatus, Integer totalQuantity, Double totalMoney, Double totalDiscountMoney, Double orderMoney, Double costMoney, Double grossMarginMoney, @NotBlank(message = CommonResponse.PARAMETER_ERROR) String userId, String remark) {
        this.storeId = storeId;
        this.id = id;
        this.type = type;
        this.createTime = createTime;
        this.applyOrderId = applyOrderId;
        this.orderStatus = orderStatus;
        this.totalQuantity = totalQuantity;
        this.totalMoney = totalMoney;
        this.totalDiscountMoney = totalDiscountMoney;
        this.orderMoney = orderMoney;
        this.costMoney = costMoney;
        this.grossMarginMoney = grossMarginMoney;
        this.userId = userId;
        this.remark = remark;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public SellApplyOrderVo getSellApplyOrderVo() {
        return sellApplyOrderVo;
    }

    public void setSellApplyOrderVo(SellApplyOrderVo sellApplyOrderVo) {
        this.sellApplyOrderVo = sellApplyOrderVo;
    }

    public List<OrderGoodsSkuVo> getDetails() {
        return details;
    }

    public void setDetails(List<OrderGoodsSkuVo> details) {
        this.details = details;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getApplyOrderId() {
        return applyOrderId;
    }

    public void setApplyOrderId(String applyOrderId) {
        this.applyOrderId = applyOrderId;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Double getTotalDiscountMoney() {
        return totalDiscountMoney;
    }

    public void setTotalDiscountMoney(Double totalDiscountMoney) {
        this.totalDiscountMoney = totalDiscountMoney;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Double getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(Double costMoney) {
        this.costMoney = costMoney;
    }

    public Double getGrossMarginMoney() {
        return grossMarginMoney;
    }

    public void setGrossMarginMoney(Double grossMarginMoney) {
        this.grossMarginMoney = grossMarginMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SellResultOrderVo{" +
                "storeId=" + storeId +
                ", sellApplyOrderVo=" + sellApplyOrderVo +
                ", details=" + details +
                ", userName='" + userName + '\'' +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", orderStatus=" + orderStatus +
                ", totalQuantity=" + totalQuantity +
                ", totalMoney=" + totalMoney +
                ", totalDiscountMoney=" + totalDiscountMoney +
                ", orderMoney=" + orderMoney +
                ", costMoney=" + costMoney +
                ", grossMarginMoney=" + grossMarginMoney +
                ", userId='" + userId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}