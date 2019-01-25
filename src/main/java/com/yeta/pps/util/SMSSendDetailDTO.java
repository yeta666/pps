package com.yeta.pps.util;

import java.util.Date;

/**
 * @author YETA
 * @date 2019/01/25/16:14
 */
public class SMSSendDetailDTO {

    /**
     * 手机号码
     */
    private String phoneNum;

    /**
     * 发送状态 1：等待回执，2：发送失败，3：发送成功
     */
    private Number sendStatus;

    /**
     * 运营商短信错误码
     */
    private String errCode;

    /**
     * 模板ID
     */
    private String templateCode;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date sendDate;

    /**
     * 接收时间
     */
    private Date receiveDate;

    /**
     * 外部流水扩展字段
     */
    private String outId;

    public SMSSendDetailDTO() {
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Number getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Number sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    @Override
    public String toString() {
        return "SMSSendDetailDTO{" +
                "phoneNum='" + phoneNum + '\'' +
                ", sendStatus=" + sendStatus +
                ", errCode='" + errCode + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", content='" + content + '\'' +
                ", sendDate=" + sendDate +
                ", receiveDate=" + receiveDate +
                ", outId='" + outId + '\'' +
                '}';
    }
}
