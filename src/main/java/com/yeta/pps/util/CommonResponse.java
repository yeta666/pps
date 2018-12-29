package com.yeta.pps.util;

/**
 * 统一返回实体
 * @author YETA
 * @date 2018/11/27/18:31
 */
public class CommonResponse<T> {

    public static final Integer SUCCESS_CODE = 1001;
    public static final Integer ERROR_CODE = 1002;
    public static final Integer EXCEPTION_CODE = 1003;

    public static final String SUCCESS_MESSAGE = "请求成功";
    public static final String PARAMETER_ERROR = "参数不匹配";
    public static final String ADD_ERROR = "新增数据失败";
    public static final String DELETE_ERROR = "删除数据失败";
    public static final String USED_ERROR = "已被使用，无法完成操作";
    public static final String STATUS_ERROR = "单据状态或结算状态已进入不可操作阶段";
    public static final String UPDATE_ERROR = "修改数据失败";
    public static final String FIND_ERROR = "查询数据失败";
    public static final String PERMISSION_ERROR = "权限不足";
    public static final String IMPORT_ERROR = "导入数据失败";
    public static final String NEED_ERROR = "导入数据标红列必填";
    public static final String EXPORT_ERROR = "导出数据失败";
    public static final String LOGIN_ERROR = "登陆失败";
    public static final String EXCEPTION_ERROR = "服务器内部错误";
    public static final String INVENTORY_ERROR = "库存相关操作错误";

    private Integer code;

    private T data;

    private String message;

    private String detail;

    public CommonResponse() {
    }

    private CommonResponse(Integer code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public static CommonResponse success() {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(SUCCESS_CODE);
        commonResponse.setMessage(SUCCESS_MESSAGE);
        return commonResponse;
    }

    public static CommonResponse success(Object data) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(SUCCESS_CODE);
        commonResponse.setData(data);
        commonResponse.setMessage(SUCCESS_MESSAGE);
        return commonResponse;
    }

    public static CommonResponse error(String message) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(ERROR_CODE);
        commonResponse.setMessage(message);
        return commonResponse;
    }

    public static CommonResponse error(String message, String detail) {
        return new CommonResponse(ERROR_CODE, message, detail);
    }

    public static CommonResponse exception(String detail) {
        return new CommonResponse(EXCEPTION_CODE, EXCEPTION_ERROR, detail);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

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
        return "CommonResponse{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
