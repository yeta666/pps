package com.yeta.pps.controller;

import com.yeta.pps.po.Warehouse;
import com.yeta.pps.service.WarehouseService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.WarehouseVo;
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
 * 仓库相关接口
 * @author YETA
 * @date 2018/11/30/15:02
 */
@Api(value = "仓库相关接口")
@RestController
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    /**
     * 新增仓库接口
     * @param warehouseVo
     * @return
     */
    @ApiOperation(value = "新增仓库")
    @ApiImplicitParam(name = "warehouseVo", value = "storeId, name必填", required = true, paramType = "body", dataType = "WarehouseVo")
    @PostMapping(value = "/warehouses")
    public CommonResponse add(@RequestBody @Valid WarehouseVo warehouseVo) {
        return warehouseService.add(warehouseVo);
    }

    /**
     * 删除仓库接口
     * @param storeId
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除仓库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "仓库id，英文逗号隔开", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/warehouses")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @RequestParam(value = "ids") String ids) {
        List<WarehouseVo> warehouseVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            warehouseVos.add(new WarehouseVo(storeId, Integer.valueOf(id)));
        });
        return warehouseService.delete(warehouseVos);
    }

    /**
     * 修改仓库接口
     * @param warehouseVo
     * @return
     */
    @ApiOperation(value = "修改仓库")
    @ApiImplicitParam(name = "warehouseVo", value = "storeId, id, name必填", required = true, paramType = "body", dataType = "WarehouseVo")
    @PutMapping(value = "/warehouses")
    public CommonResponse update(@RequestBody @Valid WarehouseVo warehouseVo) {
        return warehouseService.update(warehouseVo);
    }

    /**
     * 查询所有仓库接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询所有仓库", notes = "根据仓库名模糊查询，分页查询用于表格，不分页不筛选查询用于新增或修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "仓库名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每次显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/warehouses")
    public CommonResponse<CommonResult<List<Warehouse>>> findAll(@RequestParam(value = "storeId") Integer storeId,
                                                                 @RequestParam(value = "name", required = false) String name,
                                                                 @RequestParam(value = "page", required = false) Integer page,
                                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return warehouseService.findAll(new WarehouseVo(storeId, name), new PageVo(page, pageSize));
    }

    /**
     * 根据仓库id查询仓库接口
     * @param storeId
     * @return
     */
    @ApiOperation(value = "根据id查询仓库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "warehousesId", value = "仓库id", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping(value = "/warehouses/{warehousesId}")
    public CommonResponse<Warehouse> findById(@RequestParam(value = "storeId") Integer storeId,
                                              @PathVariable(value = "warehousesId") Integer warehousesId) {
        return warehouseService.findById(new WarehouseVo(storeId, warehousesId));
    }
}
