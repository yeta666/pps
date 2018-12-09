package com.yeta.pps.controller;

import com.yeta.pps.service.FunctionService;
import com.yeta.pps.util.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能相关接口
 * @author YETA
 * @date 2018/11/29/19:31
 */
@Api(value = "功能相关接口")
@RestController
public class FunctionController {

    @Autowired
    private FunctionService functionService;

    /**
     * 获取所有功能接口
     * @return
     */
    @ApiOperation(value = "获取所有功能")
    @GetMapping(value = "/functions")
    public CommonResponse findAll() {
        return functionService.findAll();
    }
}
