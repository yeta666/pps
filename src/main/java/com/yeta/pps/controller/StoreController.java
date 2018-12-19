package com.yeta.pps.controller;

import com.yeta.pps.po.Store;
import com.yeta.pps.service.StoreService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 店铺相关接口
 * @author YETA
 * @date 2018/11/28/18:59
 */
@Api(value = "店铺相关接口")
@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    /**
     * 新增店铺接口
     * @param store
     * @return
     */
    @ApiOperation(value = "新增店铺", notes = "插入一条店铺数据到店铺表，并且创建一套分店用表")
    @ApiImplicitParam(name = "store", value = "name, address, address, boss, phone必填", required = true, paramType = "body", dataType = "Store")
    @PostMapping(value = "/stores")
    public CommonResponse add(@RequestBody @Valid Store store) {
        return storeService.add(store);
    }

    /**
     * 初始化接口
     * @param storeId
     * @return
     */
    @ApiOperation(value = "初始化店铺数据", notes = "在分店对应的表中插入一些固定数据")
    @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "path", dataType = "int")
    @GetMapping(value = "/stores/initialization/{storeId}")
    public CommonResponse initialize(@PathVariable(value = "storeId") Integer storeId) {
        return storeService.initialize(storeId);
    }

    /**
     * 修改店铺接口
     * @param store
     * @return
     */
    @ApiOperation(value = "修改店铺")
    @ApiImplicitParam(name = "store", value = "id, name, address, address, boss, phone必填", required = true, paramType = "body", dataType = "Store")
    @PutMapping(value = "/stores")
    public CommonResponse update(@RequestBody @Valid Store store) {
        return storeService.update(store);
    }

    /**
     * 查询店铺接口
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询所有店铺", notes = "分页用于表格，不分页用于登陆时选择店铺")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/stores")
    public CommonResponse findAll(@RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return storeService.findAll(new PageVo(page, pageSize));
    }
}
