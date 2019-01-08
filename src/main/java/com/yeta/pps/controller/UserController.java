package com.yeta.pps.controller;

import com.yeta.pps.service.UserService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用户相关接口
 * @author YETA
 * @date 2018/11/28/14:30
 */
@Api(value = "用户相关接口")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取验证码接口
     * @param request
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "获取验证码")
    @GetMapping(value = "/identifyingCode")
    public CommonResponse getIdentifyingCode(HttpServletRequest request) throws IOException {
        return userService.getIdentifyingCode(request);
    }

    /**
     * 登陆接口
     * @param userVo
     * @param request
     * @return
     */
    @ApiOperation(value = "登陆")
    @ApiImplicitParam(name = "userVo", value = "storeId, username, password, identifyingCode必填", required = true, paramType = "body", dataType = "UserVo")
    @PostMapping(value = "/login")
    public CommonResponse login(@RequestBody @Valid UserVo userVo,
                                HttpServletRequest request) {
        return userService.login(userVo, request);
    }

    /**
     * 注销接口
     * @param userId
     * @param request
     * @return
     */
    @ApiOperation(value = "注销")
    @ApiImplicitParam(name = "userId", value = "用户编号", required = true, paramType = "path", dataType = "String")
    @GetMapping(value = "/logout/{userId}")
    public CommonResponse logout(@PathVariable(value = "userId") String userId,
                                 HttpServletRequest request) {
        return userService.logout(new UserVo(userId), request);
    }

    //

    /**
     * 增加用户接口
     * @param userVo
     * @return
     */
    @ApiOperation(value = "新增用户")
    @ApiImplicitParam(name = "userVo", value = "storeId, name, username, password, phone, roles必填", required = true, paramType = "body", dataType = "UserVo")
    @PostMapping(value = "/users")
    public CommonResponse add(@RequestBody @Valid UserVo userVo) {
        return userService.add(userVo);
    }

    /**
     * 删除用户接口
     * @param storeId
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户id，英文逗号隔开", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int")
    })
    @DeleteMapping(value = "/users")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @RequestParam(value = "ids") String ids) {
        List<UserVo> userVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            userVos.add(new UserVo(storeId, id));
        });
        return userService.delete(userVos);
    }

    /**
     * 修改用户接口
     * @param userVo
     * @return
     */
    @ApiOperation(value = "修改用户")
    @ApiImplicitParam(name = "userVo", value = "storeId, id, name, password, phone, disabled, roles必填", required = true, paramType = "body", dataType = "UserVo")
    @PutMapping(value = "/users")
    public CommonResponse update(@RequestBody @Valid UserVo userVo) {
        return userService.update(userVo);
    }

    /**
     * 查找所有用户接口
     * @param storeId
     * @param roleId
     * @param name
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询所有用户", notes = "分页、筛选查询，姓名模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "roleId", value = "角色id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "姓名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/users")
    public CommonResponse<CommonResult<List<UserVo>>> findAll(@RequestParam(value = "storeId") Integer storeId,
                                                              @RequestParam(value = "roleId", required = false) Integer roleId,
                                                              @RequestParam(value = "name", required = false) String name,
                                                              @RequestParam(value = "page") Integer page,
                                                              @RequestParam(value = "pageSize") Integer pageSize) {
        return userService.findAll(new UserVo(storeId, roleId, name), new PageVo(page, pageSize));
    }

    /**
     * 根据用户id查找用户接口
     * @param storeId
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据id查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/users/{userId}")
    public CommonResponse<UserVo> findById(@RequestParam(value = "storeId") Integer storeId,
                                           @PathVariable(value = "userId") String userId) {
        return userService.findById(new UserVo(storeId, userId));
    }
}
