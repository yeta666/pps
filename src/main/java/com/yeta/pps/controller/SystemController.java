package com.yeta.pps.controller;

import com.yeta.pps.po.System;
import com.yeta.pps.service.SystemService;
import com.yeta.pps.util.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统设置相关接口
 * @author YETA
 * @date 2019/01/14/19:14
 */
@Api(value = "系统设置相关接口")
@RestController
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * 查询系统设置接口
     * @return
     */
    @ApiOperation(value = "查询系统设置")
    @GetMapping(value = "/system")
    public CommonResponse<System> findSystem() {
        return systemService.findSystem();
    }

    /**
     * 修改提成比例接口
     * @return
     */
    @ApiOperation(value = "修改提成比例")
    @ApiImplicitParam(name = "system", value = "pushMoneyRate必填", required = true, paramType = "body", dataType = "System")
    @PutMapping(value = "/system/pushMoneyRate")
    public CommonResponse updatePushMoneyRate(@RequestBody System system) {
        return systemService.updatePushMoneyRate(system);
    }
}
