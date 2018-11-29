package com.yeta.pps.controller;

import com.yeta.pps.service.UserService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.UserRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author YETA
 * @date 2018/11/28/14:30
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //

    /**
     * 新增用户角色关系
     * @param userRoleVo
     * @return
     */
    @PostMapping(value = "/users/roles")
    public CommonResponse addUserRole(@RequestBody @Valid UserRoleVo userRoleVo) {
        return userService.addUserRole(userRoleVo);
    }

    /**
     * 删除用户角色关系
     * @param userRoleId
     * @param storeId
     * @return
     */
    @DeleteMapping(value = "/users/roles/{userRoleId}")
    public CommonResponse deleteUserRole(@PathVariable(value = "userRoleId") Integer userRoleId,
                                         @RequestParam(value = "storeId") Integer storeId) {
        return userService.deleteUserRole(new UserRoleVo(storeId, userRoleId));
    }

    /**
     * 修改用户角色关系
     * @param userRoleVo
     * @return
     */
    @PutMapping(value = "/users/roles")
    public CommonResponse updateUserRole(@RequestBody @Valid UserRoleVo userRoleVo) {
        return userService.updateUserRole(userRoleVo);
    }

}
