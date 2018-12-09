package com.yeta.pps.controller;

import com.yeta.pps.po.Supplier;
import com.yeta.pps.service.SupplierService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.SupplierVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 供应商相关接口
 * @author YETA
 * @date 2018/12/04/10:37
 */
@Api(value = "供应商相关接口")
@RestController
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    /**
     * 新增供应商接口
     * @param supplierVo
     * @return
     */
    @ApiOperation(value = "新增供应商")
    @ApiImplicitParam(name = "supplierVo", value = "storeId, id, name, contacts, contact_number必填", required = true, paramType = "body", dataType = "SupplierVo")
    @PostMapping(value = "/suppliers")
    public CommonResponse add(@RequestBody @Valid SupplierVo supplierVo) {
        return supplierService.add(supplierVo);
    }

    /**
     * 删除供应商接口
     * @param storeId
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除供应商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "供应商编号，英文逗号隔开", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/suppliers")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @RequestParam(value = "ids") String ids) {
        List<SupplierVo> supplierVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            supplierVos.add(new SupplierVo(storeId, id, null));
        });
        return supplierService.delete(supplierVos);
    }

    /**
     * 修改供应商接口
     * @param supplierVo
     * @return
     */
    @ApiOperation(value = "新增供应商")
    @ApiImplicitParam(name = "supplierVo", value = "storeId, id, name, contacts, contact_number必填", required = true, paramType = "body", dataType = "SupplierVo")
    @PutMapping(value = "/suppliers")
    public CommonResponse update(@RequestBody @Valid SupplierVo supplierVo) {
        return supplierService.update(supplierVo);
    }

    /**
     * 查询所有供应商接口
     * @param storeId
     * @param name
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询所有供应商", notes = "根据供应商名模糊查询，分页查询用于表格，不分页不筛选查询用于其他地方")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "供应商名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每次显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/suppliers")
    public CommonResponse<CommonResult<List<Supplier>>> findAll(@RequestParam(value = "storeId") Integer storeId,
                                                                @RequestParam(value = "name", required = false) String name,
                                                                @RequestParam(value = "page", required = false) Integer page,
                                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return supplierService.findAll(new SupplierVo(storeId, null, name), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询供应商接口
     * @param storeId
     * @param supplierId
     * @return
     */
    @ApiOperation(value = "根据id查询供应商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "supplierId", value = "供应商id", required = true, paramType = "path", dataType = "String")
    })
    @GetMapping(value = "/suppliers/{supplierId}")
    public CommonResponse<Supplier> findById(@RequestParam(value = "storeId") Integer storeId,
                                             @PathVariable(value = "supplierId") String supplierId) {
        return supplierService.findById(new SupplierVo(storeId, supplierId, null));
    }
}
