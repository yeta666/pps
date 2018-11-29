package com.yeta.pps.exception;

import com.yeta.pps.util.CommonResponse;
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
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            return new CommonResponse(commonException.getCode(), null, commonException.getMessage());
        } else {
            return new CommonResponse(CommonResponse.CODE2, null, e.getMessage());
        }
    }
}
