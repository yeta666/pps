package com.yeta.pps.controller;

import com.yeta.pps.po.Store;
import com.yeta.pps.service.StoreService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.StoreVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
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
     * @param storeVo
     * @param check
     * @return
     */
    @ApiOperation(value = "新增店铺", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeVo", value = "name, address, clientName, clientPhone, clientMembershipNumber必填", required = true, paramType = "body", dataType = "StoreVo"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @PostMapping(value = "/stores/{check}")
    public CommonResponse add(@RequestBody @Valid StoreVo storeVo,
                              @PathVariable(value = "check") String check) {
        return storeService.add(storeVo, check);
    }

    /**
     * 删除店铺接口
     * @param id
     * @return
     */
    @ApiOperation(value = "删除店铺", notes = "只用于测试的接口")
    @ApiImplicitParam(name = "id", value = "店铺编号", required = true, paramType = "path", dataType = "int")
    @DeleteMapping(value = "/stores/{id}")
    public CommonResponse delete(@PathVariable(value = "id") Integer id) {
        return storeService.delete(new Store(id));
    }

    /**
     * 修改店铺接口
     * @param store
     * @param check
     * @return
     */
    @ApiOperation(value = "修改店铺", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", value = "id, name, address必填", required = true, paramType = "body", dataType = "Store"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @PutMapping(value = "/stores/{check}")
    public CommonResponse update(@RequestBody @Valid Store store,
                                 @PathVariable(value = "check") String check) {
        return storeService.update(store, check);
    }

    /**
     * 增加店铺剩余短信条数接口
     * @param id
     * @param check
     * @return
     */
    @ApiOperation(value = "增加店铺剩余短信条数", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "店铺编号，不填表示增加所有店铺，填了表示增加指定店铺", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @PutMapping(value = "/stores/smsQuantity/{check}")
    public CommonResponse increaseSMSQuantity(@RequestParam(value = "id", required = false) Integer id,
                                              @PathVariable(value = "check") String check) {
        return storeService.increaseSMSQuantity(new Store(id), check);
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
    public CommonResponse<CommonResult<StoreVo>> findAll(@RequestParam(value = "page", required = false) Integer page,
                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return storeService.findAll(new PageVo(page, pageSize));
    }

    /**
     * 根据编号查询店铺接口
     * @param id
     * @return
     */
    @ApiOperation(value = "根据编号查询店铺")
    @ApiImplicitParam(name = "id", value = "店铺编号", required = true, paramType = "path", dataType = "int")
    @GetMapping(value = "/stores/{id}")
    public CommonResponse<CommonResult<StoreVo>> findAll(@PathVariable(value = "id") Integer id) {
        return storeService.findById(new Store(id));
    }
}
