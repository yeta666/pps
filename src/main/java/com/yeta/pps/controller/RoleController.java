package com.yeta.pps.controller;

import com.yeta.pps.service.RoleService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.FunctionMapVo;
import com.yeta.pps.vo.RoleVo;
import com.yeta.pps.vo.UserRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 角色相关接口
 * @author YETA
 * @date 2018/11/29/18:00
 */
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 新增角色接口
     * @param roleVo
     * @return
     */
    @PostMapping(value = "/roles")
    public CommonResponse add(@RequestBody @Valid RoleVo roleVo) {
        return roleService.add(roleVo);
    }

    /**
     * 删除角色
     * @param storeId
     * @param roleId
     * @return
     */
    @DeleteMapping(value = "/roles/{roleId}")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @PathVariable(value = "roleId") Integer roleId) {
        return roleService.delete(new RoleVo(storeId, roleId));
    }

    /**
     * 修改角色
     * @param roleVo
     * @return
     */
    @PutMapping(value = "/roles")
    public CommonResponse update(@RequestBody @Valid RoleVo roleVo) {
        return roleService.update(roleVo);
    }

    /**
     * 查询所有角色
     * @param storeId
     * @return
     */
    @GetMapping(value = "/roles")
    public CommonResponse findAll(@RequestParam(value = "storeId") Integer storeId) {
        return roleService.findAll(new RoleVo(storeId));
    }

    //

    /**
     * 修改角色功能关系
     * @param functionMapVo
     * @return
     */
    @PutMapping(value = "/roles/functions")
    public CommonResponse updateRoleFunction(@RequestBody @Valid FunctionMapVo functionMapVo) {
        return roleService.updateRoleFunction(functionMapVo);
    }

    /**
     * 根据用户id查找功能
     * @param userId
     * @param storeId
     * @return
     */
    @GetMapping(value = "/roles/functions/search/userId/{userId}")
    public CommonResponse findRoleFunction(@PathVariable(value = "userId") String userId,
                                        @RequestParam(value = "storeId") Integer storeId) {
        return roleService.findRoleFunction(new UserRoleVo(storeId, userId));
    }

    /**
     * 根据角色id查找功能
     * @param roleId
     * @param storeId
     * @return
     */
    @GetMapping(value = "/roles/functions/search/roleId/{roleId}")
    public CommonResponse findRoleFunction(@PathVariable(value = "roleId") Integer roleId,
                                        @RequestParam(value = "storeId") Integer storeId) {
        return roleService.findRoleFunction(new FunctionMapVo(storeId, roleId));
    }
}
