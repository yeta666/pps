package com.yeta.pps.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeta.pps.po.SellApplyOrder;
import com.yeta.pps.util.CommonResponse;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class StorageOrderVo {

    /**
     * 店铺id
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer storeId;

    /**
     * 单据编号
     */
    private String id;

    /**
     * 单据类型，1：采购订单收货单，2：退换货申请收货单，3：销售订单发货单，4：退换货申请发货单
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Byte type;

    /**
     * 单据日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 来源订单
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String applyOrderId;

    private ProcurementApplyOrderVo procurementApplyOrderVo;

    private SellApplyOrderVo sellApplyOrderVo;

    /**
     * 单据状态
     */
    private Byte orderStatus;

    /**
     * 数量
     */
    @NotNull(message = CommonResponse.MESSAGE3)
    private Integer quantity;

    /**
     * 物流公司
     */
    private String logisticsCompany;

    /**
     * 运单号
     */
    private String waybillNumber;

    /**
     * 经手人
     */
    @NotBlank(message = CommonResponse.MESSAGE3)
    private String userId;

    private String userName;

    /**
     * 单据备注
     */
    private String remark;

    public StorageOrderVo() {
    }

    public StorageOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id, @NotBlank(message = CommonResponse.MESSAGE3) String userId, String remark) {
        this.storeId = storeId;
        this.id = id;
        this.userId = userId;
        this.remark = remark;
    }

    public StorageOrderVo(@NotNull(message = CommonResponse.MESSAGE3) Integer storeId, String id, @NotNull(message = CommonResponse.MESSAGE3) Byte type, ProcurementApplyOrderVo procurementApplyOrderVo, SellApplyOrderVo sellApplyOrderVo) {
        this.storeId = storeId;
        this.id = id;
        this.type = type;
        this.procurementApplyOrderVo = procurementApplyOrderVo;
        this.sellApplyOrderVo = sellApplyOrderVo;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public ProcurementApplyOrderVo getProcurementApplyOrderVo() {
        return procurementApplyOrderVo;
    }

    public void setProcurementApplyOrderVo(ProcurementApplyOrderVo procurementApplyOrderVo) {
        this.procurementApplyOrderVo = procurementApplyOrderVo;
    }

    public SellApplyOrderVo getSellApplyOrderVo() {
        return sellApplyOrderVo;
    }

    public void setSellApplyOrderVo(SellApplyOrderVo sellApplyOrderVo) {
        this.sellApplyOrderVo = sellApplyOrderVo;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(String waybillNumber) {
        this.waybillNumber = waybillNumber;
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
        return "StorageOrderVo{" +
                "storeId=" + storeId +
                ", procurementApplyOrderVo=" + procurementApplyOrderVo +
                ", sellApplyOrderVo=" + sellApplyOrderVo +
                ", userName='" + userName + '\'' +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", applyOrderId='" + applyOrderId + '\'' +
                ", orderStatus=" + orderStatus +
                ", quantity=" + quantity +
                ", logisticsCompany='" + logisticsCompany + '\'' +
                ", waybillNumber='" + waybillNumber + '\'' +
                ", userId='" + userId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}