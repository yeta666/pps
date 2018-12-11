package com.yeta.pps.po;

import java.util.Date;

public class StorageOrder {

    /**
     * 单据编号
     */
    private String id;

    /**
     * 单据类型，1：采购订单收货单，2：退换货申请收货单，3：销售订单发货单，4：退换货申请发货单
     */
    private Byte type;

    /**
     * 单据日期
     */
    private Date createTime;

    /**
     * 来源订单
     */
    private String applyOrderId;

    /**
     * 单据状态
     */
    private Byte orderStatus;

    /**
     * 经手人
     */
    private String userId;

    /**
     * 单据备注
     */
    private String remark;

    public StorageOrder() {
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
        return "StorageOrder{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", orderStatus=" + orderStatus +
                ", userId='" + userId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}