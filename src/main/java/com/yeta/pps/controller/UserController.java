package com.yeta.pps.controller;

import com.yeta.pps.service.UserService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.UserRoleVo;
import com.yeta.pps.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

/**
 * 用户相关接口
 * @author YETA
 * @date 2018/11/28/14:30
 */
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
    @GetMapping(value = "/logout/{userId}")
    public CommonResponse logout(@PathVariable(value = "userId") String userId,
                                 HttpServletRequest request) {
        return userService.logout(new UserVo(userId), request);
    }
}
