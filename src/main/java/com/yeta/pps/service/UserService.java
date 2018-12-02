package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyDepartmentMapper;
import com.yeta.pps.mapper.MyRoleMapper;
import com.yeta.pps.mapper.MyUserMapper;
import com.yeta.pps.mapper.MyWarehouseMapper;
import com.yeta.pps.po.User;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.CommonUtil;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;
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

    @Autowired
    private MyDepartmentMapper myDepartmentMapper;

    @Autowired
    private MyWarehouseMapper myWarehouseMapper;

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

    /**
     * 新增用户
     * @param userVo
     * @return
     */
    @Transactional
    public CommonResponse add(UserVo userVo) {
        //判断参数
        if (userVo.getName() == null || userVo.getUsername() == null || userVo.getPassword() == null || userVo.getPhone() == null ||
                /*userVo.getDepartmentId() == null ||*/ userVo.getWarehouseId() == null || userVo.getRoleId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //判断部门id是否存在
        /*DepartmentVo departmentVo = new DepartmentVo(userVo.getStoreId(), userVo.getDepartmentId());
        if (myDepartmentMapper.findById(departmentVo) == null) {
            LOG.info("部门id【{}】不存在，新增用户失败...", userVo.getDepartmentId());
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }*/
        //判断仓库id是否存在
        WarehouseVo warehouseVo = new WarehouseVo(userVo.getStoreId(), userVo.getWarehouseId());
        if (myWarehouseMapper.findById(warehouseVo) == null) {
            LOG.info("仓库id【{}】不存在，新增用户失败...", userVo.getWarehouseId());
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        //判断角色id是否存在
        RoleVo roleVo = new RoleVo(userVo.getStoreId(), userVo.getRoleId());
        if (myRoleMapper.findById(roleVo) == null) {
            LOG.info("角色id【{}】不存在，新增用户失败...", userVo.getRoleId());
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        //新增用户
        userVo.setId(UUID.randomUUID().toString());
        userVo.setDisabled(0);
        if (myUserMapper.add(userVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //新增部门用户关系
        /*DepartmentUserVo departmentUserVo = new DepartmentUserVo(userVo.getStoreId(), userVo.getDepartmentId(), userVo.getId());
        if (myDepartmentMapper.addDepartmentUser(departmentUserVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }*/
        //新增仓库用户关系
        WarehouseUserVo warehouseUserVo = new WarehouseUserVo(userVo.getStoreId(), userVo.getWarehouseId(), userVo.getId());
        if (myWarehouseMapper.addWarehouseUser(warehouseUserVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //新增用户角色关系
        UserRoleVo userRoleVo = new UserRoleVo(userVo.getStoreId(), userVo.getId(), userVo.getRoleId());
        if (myUserMapper.addUserRole(userRoleVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除用户
     * @param userVo
     * @return
     */
    @Transactional
    public CommonResponse delete(UserVo userVo) {
        //判断参数
        if (userVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //删除用户
        if (myUserMapper.delete(userVo) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }
        //删除用户部门关系
        /*DepartmentUserVo departmentUserVo = new DepartmentUserVo(userVo.getStoreId(), userVo.getId());
        if (myDepartmentMapper.deleteUserDepartment(departmentUserVo) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }*/
        //删除用户仓库关系
        WarehouseUserVo warehouseUserVo = new WarehouseUserVo(userVo.getStoreId(), userVo.getId());
        if (myWarehouseMapper.deleteUserWarehouse(warehouseUserVo) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }
        //删除用户角色关系
        UserRoleVo userRoleVo = new UserRoleVo(userVo.getStoreId(), userVo.getId());
        if (myUserMapper.deleteUserRole(userRoleVo) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改用户
     * @param userVo
     * @return
     */
    public CommonResponse update(UserVo userVo) {
        //判断参数
        if (userVo.getId() == null || userVo.getName() == null || userVo.getUsername() == null || userVo.getPassword() == null || userVo.getPhone() == null || userVo.getDisabled() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改用户信息
        if (myUserMapper.update(userVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        //TODO
        //不能修改用户部门关系、用户仓库关系、用户角色关系
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查找所有用户
     * @param userVo
     * @return
     */
    public CommonResponse findAll(UserVo userVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage(myUserMapper.findCount(userVo) / pageVo.getPageSize());
        //查询
        List<UserVo> userVos = myUserMapper.findAll(userVo, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("姓名", "name"));
        titles.add(new Title("用户名", "username"));
        titles.add(new Title("电话", "phone"));
        /*titles.add(new Title("部门", "departmentName"));*/
        titles.add(new Title("岗位", "roleName"));
        titles.add(new Title("仓库", "warehouseName"));
        titles.add(new Title("是否禁用", "disabled"));
        titles.add(new Title("备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, userVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据用户id查找用户
     * @param userVo
     * @return
     */
    public CommonResponse findById(UserVo userVo) {
        userVo = myUserMapper.findById(userVo);
        if (userVo == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, userVo, CommonResponse.MESSAGE1);
    }
}
