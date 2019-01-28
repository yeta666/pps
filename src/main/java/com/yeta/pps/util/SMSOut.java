package com.yeta.pps.util;

/**
 * @author YETA
 * @date 2019/01/25/16:14
 */
public class SMSOut {

    /**
     * 请求ID
     */
    private String RequestId;

    /**
     * 状态码
     */
    private String Code;

    /**
     * 状态码
     */
    private String Message;

    /**
     * 发送回执ID,可根据该ID查询具体的发送状态
     */
    private String BizId;

    public SMSOut() {
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getBizId() {
        return BizId;
    }

    public void setBizId(String bizId) {
        BizId = bizId;
    }

    @Override
    public String toString() {
        return "SMSOut{" +
                "RequestId='" + RequestId + '\'' +
                ", Code='" + Code + '\'' +
                ", Message='" + Message + '\'' +
                ", BizId='" + BizId + '\'' +
                '}';
    }
}
