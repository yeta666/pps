package com.yeta.pps.util;

/**
 * 统一返回实体
 * @author YETA
 * @date 2018/11/27/18:31
 */
public class CommonResponse {

    public static final Integer CODE1 = 1001;
    public static final Integer CODE2 = 1002;
    public static final Integer CODE3 = 1003;
    public static final Integer CODE4 = 1004;
    public static final Integer CODE5 = 1005;
    public static final Integer CODE6 = 1006;
    public static final Integer CODE7 = 1007;
    public static final Integer CODE8 = 1008;
    public static final Integer CODE9 = 1009;
    public static final Integer CODE10 = 1010;
    public static final Integer CODE11 = 1011;
    public static final Integer CODE12 = 1012;
    public static final Integer CODE13 = 1013;
    public static final Integer CODE14 = 1014;
    public static final Integer CODE15 = 1015;
    public static final Integer CODE16 = 1016;

    //...

    public static final String MESSAGE1 = "请求成功";
    public static final String MESSAGE2 = "出现异常";
    public static final String MESSAGE3 = "参数不匹配";
    public static final String MESSAGE4 = "验证码错误";
    public static final String MESSAGE5 = "用户名或密码错误";
    public static final String MESSAGE6 = "权限不足";
    public static final String MESSAGE7 = "新增数据失败";
    public static final String MESSAGE8 = "删除数据失败";
    public static final String MESSAGE9 = "修改数据失败";
    public static final String MESSAGE10 = "查询数据失败";
    public static final String MESSAGE11 = "重复登陆";
    public static final String MESSAGE12 = "文件上传失败";
    public static final String MESSAGE13 = "下载失败";
    public static final String MESSAGE14 = "token错误";
    public static final String MESSAGE15 = "会员卡号错误";
    public static final String MESSAGE16 = "邀请人错误";

    private Integer code;

    private Object data;

    private String message;

    public CommonResponse() {
    }

    public CommonResponse(Integer code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
