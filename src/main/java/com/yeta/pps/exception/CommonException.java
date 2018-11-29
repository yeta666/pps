package com.yeta.pps.exception;

/**
 * 自定义异常
 * @author YETA
 * @date 2018/11/27/13:04
 */
public class CommonException extends RuntimeException {

    private Integer code;

    private String message;

    public CommonException() {
    }

    public CommonException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CommonException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
