package com.yeta.pps.util;

import java.util.List;

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

    /**
     * 发送总条数
     */
    private Integer TotalCount;

    /**
     * 发送明细
     */
    private List<SMSSendDetailDTO> smsSendDetailDTOs;

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

    public Integer getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(Integer totalCount) {
        TotalCount = totalCount;
    }

    public List<SMSSendDetailDTO> getSmsSendDetailDTOs() {
        return smsSendDetailDTOs;
    }

    public void setSmsSendDetailDTOs(List<SMSSendDetailDTO> smsSendDetailDTOs) {
        this.smsSendDetailDTOs = smsSendDetailDTOs;
    }

    @Override
    public String toString() {
        return "SMSOut{" +
                "RequestId='" + RequestId + '\'' +
                ", Code='" + Code + '\'' +
                ", Message='" + Message + '\'' +
                ", BizId='" + BizId + '\'' +
                ", TotalCount=" + TotalCount +
                ", smsSendDetailDTOs=" + smsSendDetailDTOs +
                '}';
    }
}
