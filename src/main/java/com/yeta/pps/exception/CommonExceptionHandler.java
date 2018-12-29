package com.yeta.pps.exception;

import com.yeta.pps.util.CommonResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 * @author YETA
 * @date 2018/11/27/13:09
 */
@RestControllerAdvice
public class CommonExceptionHandler  {

    @ExceptionHandler
    public CommonResponse defaultExceptionHandler(Exception e) {
        e.printStackTrace();

        if (e instanceof CommonException) {     //自定异常，当正常错误返回
            CommonException commonException = (CommonException) e;
            return CommonResponse.error(commonException.getMessage(), commonException.getDetail());

        } if (e instanceof DuplicateKeyException) {
            String[] message = e.getMessage().split(":");
            return CommonResponse.error("字段重复：【" + message[message.length - 1] + "】");

        } if (e instanceof HttpMessageNotReadableException) {
            String message = e.getMessage().substring(e.getMessage().lastIndexOf("[") + 2, e.getMessage().lastIndexOf("]") - 1);
            return CommonResponse.error("字段格式不正确：【" + message + "】");

        } if (e instanceof MethodArgumentNotValidException) {
            String message = e.getMessage().substring(e.getMessage().indexOf("default message"), e.getMessage().lastIndexOf("default message"));
            String message1 = message.substring(message.indexOf("[") + 1, message.indexOf("]"));
            return CommonResponse.error("参数不匹配：【" + message1 + "】");

        } else {        //未知异常，当服务器异常返回
            return CommonResponse.exception(e.getMessage());
        }
    }
}
