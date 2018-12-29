package com.yeta.pps.exception;

/**
 * 自定义异常
 * @author YETA
 * @date 2018/11/27/13:04
 */
public class CommonException extends RuntimeException {

    private String message;

    private String detail;

    public CommonException() {
    }

    public CommonException(String message) {
        this.message = message;
    }

    public CommonException(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "CommonException{" +
                ", message='" + message + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
