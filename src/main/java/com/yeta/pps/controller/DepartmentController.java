package com.yeta.pps.controller;

import com.yeta.pps.service.DepartmentService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 部门相关接口
 * @author YETA
 * @date 2018/11/30/14:43
 */
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 新增部门接口
     * @param departmentVo
     * @return
     */
    @PostMapping(value = "/departments")
    public CommonResponse add(@RequestBody @Valid DepartmentVo departmentVo) {
        return departmentService.add(departmentVo);
    }

    /**
     * 删除部门接口
     * @param storeId
     * @param departmentId
     * @return
     */
    @DeleteMapping(value = "/departments/{departmentId}")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @PathVariable(value = "departmentId") Integer departmentId) {
        return departmentService.delete(new DepartmentVo(storeId, departmentId));
    }

    /**
     * 修改部门接口
     * @param departmentVo
     * @return
     */
    @PutMapping(value = "/departments")
    public CommonResponse update(@RequestBody @Valid DepartmentVo departmentVo) {
        return departmentService.update(departmentVo);
    }

    /**
     * 查询所有部门接口
     * @param storeId
     * @return
     */
    @GetMapping(value = "/departments")
    public CommonResponse findAll(@RequestParam(value = "storeId") Integer storeId) {
        return departmentService.findAll(new DepartmentVo(storeId));
    }

    /**
     * 根据部门id查询部门接口
     * @param storeId
     * @return
     */
    @GetMapping(value = "/departments/{departmentId}")
    public CommonResponse findAll(@RequestParam(value = "storeId") Integer storeId,
                                  @PathVariable(value = "departmentId") Integer departmentId) {
        return departmentService.findById(new DepartmentVo(storeId, departmentId));
    }
}
