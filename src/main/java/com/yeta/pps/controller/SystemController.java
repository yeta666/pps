package com.yeta.pps.controller;

import com.yeta.pps.po.SSystem;
import com.yeta.pps.service.SystemService;
import com.yeta.pps.util.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 查询分店设置接口
     * @return
     */
    @ApiOperation(value = "查询分店设置")
    @GetMapping(value = "/system")
    public CommonResponse<SSystem> findSystem() {
        return systemService.findSystem();
    }

    /**
     * 修改分店设置接口
     * @param sSystem
     * @return
     */
    @ApiOperation(value = "修改分店设置")
    @ApiImplicitParam(name = "sSystem", value = "pushMoneyRate(提成比例)必填, signature(短信签名)选填", required = true, paramType = "body", dataType = "SSystem")
    @PutMapping(value = "/system")
    public CommonResponse updateSystem(@RequestBody SSystem sSystem) {
        return systemService.updateSystem(sSystem);
    }

    /**
     * 查询系统开账接口
     * @param storeId
     * @return
     */
    @ApiOperation(value = "查询系统开账")
    @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "path", dataType = "int")
    @GetMapping(value = "/system/startBill/{storeId}")
    public CommonResponse<SSystem> findStartBill(@PathVariable(value = "storeId") Integer storeId) {
        return systemService.findStartBill(new SSystem(storeId));
    }

    /**
     * 修改系统开账接口
     * @param sSystem
     * @return
     */
    @ApiOperation(value = "修改系统开账")
    @ApiImplicitParam(name = "sSystem", value = "storeId, userId必填", required = true, paramType = "body", dataType = "SSystem")
    @PutMapping(value = "/system/startBill")
    public CommonResponse updateStartBill(@RequestBody SSystem sSystem) {
        return systemService.updateStartBill(sSystem);
    }

    /**
     * 查询零售默认设置接口
     * @param storeId
     * @return
     */
    @ApiOperation(value = "查询零售默认设置")
    @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "path", dataType = "int")
    @GetMapping(value = "/system/retail/{storeId}")
    public CommonResponse<SSystem> findRetail(@PathVariable(value = "storeId") Integer storeId) {
        return systemService.findRetail(new SSystem(storeId));
    }

    /**
     * 修改零售默认设置接口
     * @param sSystem
     * @return
     */
    @ApiOperation(value = "修改零售默认设置")
    @ApiImplicitParam(name = "sSystem", value = "storeId, retailWarehouseId, retailBankAccountId必填", required = true, paramType = "body", dataType = "SSystem")
    @PutMapping(value = "/system/retail")
    public CommonResponse updateRetail(@RequestBody SSystem sSystem) {
        return systemService.updateRetail(sSystem);
    }
}
