package com.yeta.pps.controller;

import com.yeta.pps.po.Role;
import com.yeta.pps.service.RoleService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.FunctionVo;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.RoleVo;
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
 * 角色相关接口
 * @author YETA
 * @date 2018/11/29/18:00
 */
@Api(value = "角色相关接口")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 新增角色接口
     * @param roleVo
     * @return
     */
    @ApiOperation(value = "新增角色")
    @ApiImplicitParam(name = "roleVo", value = "storeId, name必填", required = true, paramType = "body", dataType = "RoleVo")
    @PostMapping(value = "/roles")
    public CommonResponse add(@RequestBody @Valid RoleVo roleVo) {
        return roleService.add(roleVo);
    }

    /**
     * 删除角色接口
     * @param storeId
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "角色id，英文逗号隔开", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/roles")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @RequestParam(value = "ids") String ids) {
        List<RoleVo> roleVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            roleVos.add(new RoleVo(storeId, Integer.valueOf(id)));
        });
        return roleService.delete(roleVos);
    }

    /**
     * 修改角色接口
     * @param roleVo
     * @return
     */
    @ApiOperation(value = "修改角色")
    @ApiImplicitParam(name = "roleVo", value = "storeId, id, name必填", required = true, paramType = "body", dataType = "RoleVo")
    @PutMapping(value = "/roles")
    public CommonResponse update(@RequestBody @Valid RoleVo roleVo) {
        return roleService.update(roleVo);
    }

    /**
     * 查询所有角色接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询角色", notes = "分页用于表格，不分页用于新增、修改用户的时候")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/roles")
    public CommonResponse<CommonResult<List<Role>>> findAll(@RequestParam(value = "storeId") Integer storeId,
                                                            @RequestParam(value = "page", required = false) Integer page,
                                                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return roleService.findAll(new RoleVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询角色接口
     * @param storeId
     * @param roleId
     * @return
     */
    @ApiOperation(value = "根据id查询角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping(value = "/roles/{roleId}")
    public CommonResponse<Role> findById(@RequestParam(value = "storeId") Integer storeId,
                                         @PathVariable(value = "roleId") Integer roleId) {
        return roleService.findById(new RoleVo(storeId, roleId));
    }

    //

    /**
     * 修改角色功能关系接口
     * @param functionVo
     * @return
     */
    @ApiOperation(value = "修改角色对应的功能")
    @ApiImplicitParam(name = "functionVo", value = "storeId, roleId, functionIds必填", required = true, paramType = "body", dataType = "FunctionVo")
    @PutMapping(value = "/roles/functions")
    public CommonResponse updateRoleFunction(@RequestBody @Valid FunctionVo functionVo) {
        return roleService.updateRoleFunction(functionVo);
    }

    /**
     * 根据用户编号或角色编号查询对应的功能接口
     * @param storeId
     * @param userId
     * @param roleId
     * @return
     */
    @ApiOperation(value = "根据用户编号或角色编号查询对应的功能，传用户编号用于登陆之后获取菜单栏，传角色编号用于修改角色对应的权限时先获取")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "roleId", value = "角色编号", required = false, paramType = "query", dataType = "int"),
    })
    @GetMapping(value = "/roles/functions")
    public CommonResponse findRoleFunction(@RequestParam(value = "storeId") Integer storeId,
                                           @RequestParam(value = "userId", required = false) String userId,
                                           @RequestParam(value = "roleId", required = false) Integer roleId) {
        return roleService.findRoleFunction(new FunctionVo(storeId, userId, roleId));
    }
}
