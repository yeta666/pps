package com.yeta.pps.service;

import com.yeta.pps.mapper.MyRoleMapper;
import com.yeta.pps.mapper.MyUserMapper;
import com.yeta.pps.po.User;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonUtil;
import com.yeta.pps.vo.RoleVo;
import com.yeta.pps.vo.UserRoleVo;
import com.yeta.pps.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 用户相关逻辑处理
 * @author YETA
 * @date 2018/11/28/14:28
 */
@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private MyUserMapper myUserMapper;

    @Autowired
    private MyRoleMapper myRoleMapper;

    /**
     * 获取验证码
     * @param request
     * @return
     * @throws IOException
     */
    public CommonResponse getIdentifyingCode(HttpServletRequest request) throws IOException {
        //定义验证码图片大小
        int w = 24, h = 52, size = 5;
        //获取验证码
        String iCode = CommonUtil.generateVerifyCode(size).toUpperCase();
        //将验证码存入session
        request.getSession().setAttribute("identifyingCode", iCode);
        //创建验证码图片
        File file = new File(FileService.download, System.currentTimeMillis() + ".jpg");
        CommonUtil.outputImage(w * iCode.length(), h, file, iCode);
        return new CommonResponse(CommonResponse.CODE1, "/download/" + file.getName(), CommonResponse.MESSAGE1);
    }

    /**
     * 登陆
     * @param userVo
     * @param request
     * @return
     */
    public CommonResponse login(UserVo userVo, HttpServletRequest request) {
        //判断参数
        if (userVo.getIdentifyingCode() == null || userVo.getUsername() == null || userVo.getPassword() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //判断验证码
        String iCode = userVo.getIdentifyingCode().toUpperCase();
        HttpSession session = request.getSession();
        Object siCode = session.getAttribute("identifyingCode");
        if (siCode == null || !siCode.equals(iCode)) {
            return new CommonResponse(CommonResponse.CODE4, null, CommonResponse.MESSAGE4);
        }
        //判断用户名、密码
        User user = myUserMapper.findByUsernameAndPassword(userVo);
        if (user == null) {
            return new CommonResponse(CommonResponse.CODE5, null, CommonResponse.MESSAGE5);
        }
        //判断用户是否已禁用
        if (user.getDisabled() == 1) {
            return new CommonResponse(CommonResponse.CODE6, null, CommonResponse.MESSAGE6);
        }
        //判断用户是否已登陆
        String userId = user.getId();
        ServletContext servletContext = session.getServletContext();
        ConcurrentSkipListSet<String> onlineIds = (ConcurrentSkipListSet<String>) servletContext.getAttribute("onlineIds");
        for (String onlineId: onlineIds) {
            if (onlineId.equals(userId)) {
                return new CommonResponse(CommonResponse.CODE11, null, CommonResponse.MESSAGE11);
            }
        }
        //设置已登陆
        onlineIds.add(userId);
        session.setAttribute("userId", userId);
        session.setMaxInactiveInterval(60 * 60);      //60分钟
        userVo.setId(userId);
        userVo.setToken(CommonUtil.getMd5(userId));        //token就是md5加密后的用户id
        return new CommonResponse(CommonResponse.CODE1, userVo, CommonResponse.MESSAGE1);
    }

    /**
     * 注销
     * @param userVo
     * @param request
     * @return
     */
    public CommonResponse logout(UserVo userVo, HttpServletRequest request) {
        //获取在线用户id
        HttpSession session = request.getSession();
        ServletContext servletContext = session.getServletContext();
        ConcurrentSkipListSet<String> onlineIds = (ConcurrentSkipListSet<String>) servletContext.getAttribute("onlineIds");
        //注销
        for (String onlineId : onlineIds) {
            if (onlineId.equals(userVo.getId())) {
                onlineIds.remove(onlineId);
                break;
            }
        }
        session.invalidate();
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    //

    /**
     * 新增用户角色关系
     * @param userRoleVo
     * @return
     */
    public CommonResponse addUserRole(UserRoleVo userRoleVo) {
        //判断参数
        if (userRoleVo.getUserId() == null || userRoleVo.getRoleId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //判断用户是否已经有角色
        if (myUserMapper.findUserRole(userRoleVo) != null) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        //判断角色是否存在
        if (myRoleMapper.findById(new RoleVo(userRoleVo.getStoreId(), userRoleVo.getId())) == null) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        //新增
        if (myUserMapper.addUserRole(userRoleVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除用户角色关系
     * @param userRoleVo
     * @return
     */
    public CommonResponse deleteUserRole(UserRoleVo userRoleVo) {
        //判断参数
        if (userRoleVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //删除
        if (myUserMapper.deleteUserRole(userRoleVo) != 1) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改用户角色关系
     * @param userRoleVo
     * @return
     */
    public CommonResponse updateUserRole(UserRoleVo userRoleVo) {
        //判断参数
        if (userRoleVo.getId() == null || userRoleVo.getRoleId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //判断角色是否存在
        if (myRoleMapper.findById(new RoleVo(userRoleVo.getStoreId(), userRoleVo.getId())) == null) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        //修改
        if (myUserMapper.updateUserRole(userRoleVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }
}
