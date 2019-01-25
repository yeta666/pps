package com.yeta.pps.util;

import java.util.Date;

/**
 * @author YETA
 * @date 2019/01/25/16:51
 */
public class TemplateParam {

    private Integer code;

    private String clientName;

    private String storeName;

    private String storeAddress;

    private Date time;

    public TemplateParam() {
    }

    public TemplateParam(Integer code) {
        this.code = code;
    }

    public TemplateParam(String clientName) {
        this.clientName = clientName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TemplateParam{" +
                "code=" + code +
                ", clientName='" + clientName + '\'' +
                ", storeName='" + storeName + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", time=" + time +
                '}';
    }
}
