package com.yeta.pps.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeta.pps.util.CommonResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author YETA
 * @date 2019/01/22/20:24
 */
public class SMSHistory {

    /**
     * 短信历史编号
     */
    private Integer id;

    /**
     * 店铺编号
     */
    @NotNull(message = CommonResponse.PARAMETER_ERROR)
    private Integer storeId;

    private String storeName;

    /**
     * 客户编号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String clientId;

    private String clientName;

    @JsonIgnore
    private Integer code;

    private String clientPhone;

    /**
     * 短信模板ID
     */
    private String templateCode;

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 经手人编号
     */
    @NotBlank(message = CommonResponse.PARAMETER_ERROR)
    private String userId;

    private String userName;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送状态
     */
    private Byte status;

    /**
     * 失败原因
     */
    private String remark;

    public SMSHistory() {
    }

    public SMSHistory(Integer storeId, String clientId, String clientName, String clientPhone) {
        this.storeId = storeId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SMSHistory{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", createTime=" + createTime +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }
}
