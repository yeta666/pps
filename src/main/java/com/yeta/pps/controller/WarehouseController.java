package com.yeta.pps.controller;

import com.yeta.pps.service.WarehouseService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.WarehouseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 仓库相关接口
 * @author YETA
 * @date 2018/11/30/15:02
 */
@RestController
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    /**
     * 新增仓库接口
     * @param warehouseVo
     * @return
     */
    @PostMapping(value = "/warehouses")
    public CommonResponse add(@RequestBody @Valid WarehouseVo warehouseVo) {
        return warehouseService.add(warehouseVo);
    }

    /**
     * 删除仓库接口
     * @param storeId
     * @param warehousesId
     * @return
     */
    @DeleteMapping(value = "/warehouses/{warehousesId}")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @PathVariable(value = "warehousesId") Integer warehousesId) {
        return warehouseService.delete(new WarehouseVo(storeId, warehousesId));
    }

    /**
     * 修改仓库接口
     * @param warehouseVo
     * @return
     */
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
    @GetMapping(value = "/warehouses")
    public CommonResponse findAll(@RequestParam(value = "storeId") Integer storeId,
                                  @RequestParam(value = "page") Integer page,
                                  @RequestParam(value = "pageSize") Integer pageSize) {
        return warehouseService.findAll(new WarehouseVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据仓库id查询仓库接口
     * @param storeId
     * @return
     */
    @GetMapping(value = "/warehouses/{warehousesId}")
    public CommonResponse findAll(@RequestParam(value = "storeId") Integer storeId,
                                  @PathVariable(value = "warehousesId") Integer warehousesId) {
        return warehouseService.findById(new WarehouseVo(storeId, warehousesId));
    }
}
