package com.yeta.pps.controller;

import com.yeta.pps.service.SupplierService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.SupplierVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 供应商相关接口
 * @author YETA
 * @date 2018/12/04/10:37
 */
@RestController
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    /**
     * 新增供应商接口
     * @param supplierVo
     * @return
     */
    @PostMapping(value = "/suppliers")
    public CommonResponse add(@RequestBody @Valid SupplierVo supplierVo) {
        return supplierService.add(supplierVo);
    }

    /**
     * 删除供应商接口
     * @param storeId
     * @param supplierId
     * @return
     */
    @DeleteMapping(value = "/suppliers/{supplierId}")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @PathVariable(value = "supplierId") String supplierId) {
        return supplierService.delete(new SupplierVo(storeId, supplierId));
    }

    /**
     * 修改供应商接口
     * @param supplierVo
     * @return
     */
    @PutMapping(value = "/suppliers")
    public CommonResponse update(@RequestBody @Valid SupplierVo supplierVo) {
        return supplierService.update(supplierVo);
    }

    /**
     * 查询所有供应商接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/suppliers")
    public CommonResponse findAll(@RequestParam(value = "storeId") Integer storeId,
                                  @RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return supplierService.findAll(new SupplierVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询供应商接口
     * @param storeId
     * @param supplierId
     * @return
     */
    @GetMapping(value = "/suppliers/{supplierId}")
    public CommonResponse findById(@RequestParam(value = "storeId") Integer storeId,
                                   @PathVariable(value = "supplierId") String supplierId) {
        return supplierService.findById(new SupplierVo(storeId, supplierId));
    }
}
