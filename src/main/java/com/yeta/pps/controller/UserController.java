package com.yeta.pps.controller;

import com.yeta.pps.service.UserService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.PageVo;
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

    //

    /**
     * 增加用户接口
     * @param userVo
     * @return
     */
    @PostMapping(value = "/users")
    public CommonResponse add(@RequestBody @Valid UserVo userVo) {
        return userService.add(userVo);
    }

    /**
     * 删除用户接口
     * @param storeId
     * @param userId
     * @return
     */
    @DeleteMapping(value = "/users/{userId}")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @PathVariable(value = "userId") String userId) {
        return userService.delete(new UserVo(storeId, userId));
    }

    /**
     * 修改用户接口
     * @param userVo
     * @return
     */
    @PutMapping(value = "/users")
    public CommonResponse update(@RequestBody @Valid UserVo userVo) {
        return userService.update(userVo);
    }

    /**
     * 查找所有用户接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/users")
    public CommonResponse findAll(@RequestParam(value = "storeId") Integer storeId,
                                  @RequestParam(value = "page") Integer page,
                                  @RequestParam(value = "pageSize") Integer pageSize) {
        return userService.findAll(new UserVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据用户id查找用户接口
     * @param storeId
     * @param userId
     * @return
     */
    @GetMapping(value = "/users/{userId}")
    public CommonResponse findById(@RequestParam(value = "storeId") Integer storeId,
                              @PathVariable(value = "userId") String userId) {
        return userService.findById(new UserVo(storeId, userId));
    }
}
