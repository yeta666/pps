package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.*;
import com.yeta.pps.po.Role;
import com.yeta.pps.po.User;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.CommonUtil;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 用户相关逻辑处理
 * @author YETA
 * @date 2018/11/28/14:28
 */
@Service
public class UserService {

    @Autowired
    private MyUserMapper myUserMapper;

    @Autowired
    private MyRoleMapper myRoleMapper;

    @Autowired
    private MyProcurementMapper myProcurementMapper;

    @Autowired
    private MySellMapper mySellMapper;

    @Autowired
    private MyStorageMapper myStorageMapper;

    @Autowired
    private MyFundMapper myFundMapper;

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
        String iCode = CommonUtil.generateVerifyCode(size, "0123456789").toUpperCase();
        //将验证码存入session
        request.getSession().setAttribute("identifyingCode", iCode);
        //创建验证码图片
        File file = new File(FileService.download, System.currentTimeMillis() + ".jpg");
        CommonUtil.outputImage(w * iCode.length(), h, file, iCode);
        return CommonResponse.success("/download/" + file.getName());
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
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //判断验证码
        String iCode = userVo.getIdentifyingCode().toUpperCase();
        HttpSession session = request.getSession();
        Object siCode = session.getAttribute("identifyingCode");
        if (siCode == null || !siCode.equals(iCode)) {
            return CommonResponse.error(CommonResponse.LOGIN_ERROR, "验证码错误");
        }

        //判断用户名、密码
        User user = myUserMapper.findByUsernameAndPassword(userVo);
        if (user == null) {
            return CommonResponse.error(CommonResponse.LOGIN_ERROR, "用户名或密码错误");
        }

        //判断用户是否已禁用
        if (user.getDisabled() == 1) {
            return CommonResponse.error(CommonResponse.LOGIN_ERROR, "该账号已禁用");
        }

        //判断用户是否已登陆
        String userId = user.getId();
        ServletContext servletContext = session.getServletContext();
        ConcurrentSkipListSet<String> onlineIds = (ConcurrentSkipListSet<String>) servletContext.getAttribute("onlineIds");
        for (String onlineId: onlineIds) {
            if (onlineId.equals(userId + userVo.getStoreId())) {
                return CommonResponse.error(CommonResponse.LOGIN_ERROR, "重复登陆");
            }
        }
        //设置已登陆
        onlineIds.add(userId + userVo.getStoreId());
        session.setAttribute("userId", userId + userVo.getStoreId());
        session.setMaxInactiveInterval(60 * 60);      //60分钟
        userVo.setId(userId);
        userVo.setToken(CommonUtil.getMd5(userId + userVo.getStoreId()));        //token就是md5加密后的用户id
        return CommonResponse.success(userVo);
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
            if (onlineId.equals(userVo.getId() + userVo.getStoreId())) {
                onlineIds.remove(onlineId);
                break;
            }
        }
        session.invalidate();
        return CommonResponse.success();
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
                userVo.getRoles() == null || userVo.getRoles().size() == 0) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //新增用户
        userVo.setId(UUID.randomUUID().toString().replace("-", ""));
        userVo.setDisabled(0);
        if (myUserMapper.add(userVo) != 1) {
           return CommonResponse.error(CommonResponse.ADD_ERROR);
        }

        //新增用户角色关系
        List<Role> roles = userVo.getRoles();
        roles.stream().forEach(role -> {
            RoleVo roleVo = new RoleVo(userVo.getStoreId(), role.getId());
            if (myRoleMapper.findById(roleVo) == null) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }

            //新增用户角色关系
            UserRoleVo userRoleVo = new UserRoleVo(userVo.getStoreId(), userVo.getId(), role.getId());
            if (myUserMapper.addUserRole(userRoleVo) != 1) {
                throw new CommonException(CommonResponse.ADD_ERROR);
            }
        });
        return CommonResponse.success();
    }

    /**
     * 删除用户
     * @param userVos
     * @return
     */
    @Transactional
    public CommonResponse delete(List<UserVo> userVos) {
        userVos.stream().forEach(userVo -> {
            //获取参数
            Integer storeId = userVo.getStoreId();
            String id = userVo.getId();

            //判断用户是否使用
            //1. procurement_apply_order
            ProcurementApplyOrderVo paoVo = new ProcurementApplyOrderVo();
            paoVo.setStoreId(storeId);
            paoVo.setUserId(id);
            if (myProcurementMapper.findAllApplyOrderDetail(paoVo).size() > 0) {
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.USED_ERROR);
            }
            //2. sell_apply_order
            SellApplyOrderVo saoVo = new SellApplyOrderVo();
            saoVo.setStoreId(storeId);
            saoVo.setUserId(id);
            if (mySellMapper.findAllApplyOrderDetail(saoVo).size() > 0) {
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.USED_ERROR);
            }
            //3. storage_check_order
            StorageCheckOrderVo scoVo = new StorageCheckOrderVo();
            scoVo.setStoreId(storeId);
            scoVo.setUserId(id);
            if (myStorageMapper.findAllStorageCheckOrder(scoVo).size() > 0) {
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.USED_ERROR);
            }
            //4. storage_result_order
            StorageResultOrderVo sroVo = new StorageResultOrderVo();
            sroVo.setStoreId(storeId);
            sroVo.setUserId(id);
            if (myStorageMapper.findStorageResultOrderDetail(sroVo).size() > 0) {
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.USED_ERROR);
            }
            //5. fund_check_order
            FundCheckOrderVo fcoVo = new FundCheckOrderVo();
            fcoVo.setStoreId(storeId);
            fcoVo.setUserId(id);
            if (myFundMapper.findAllFundCheckOrder(fcoVo).size() > 0) {
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.USED_ERROR);
            }
            //6. fund_order
            FundOrderVo foVo = new FundOrderVo();
            foVo.setStoreId(storeId);
            foVo.setUserId(id);
            if (myFundMapper.findFundOrder(foVo).size() > 0) {
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.USED_ERROR);
            }
            //7. fund_result_order
            FundResultOrderVo froVo = new FundResultOrderVo();
            froVo.setStoreId(storeId);
            froVo.setUserId(id);
            if (myFundMapper.findFundResultOrder(froVo).size() > 0) {
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.USED_ERROR);
            }
            //8. fund_target_check_order
            FundTargetCheckOrderVo ftcoVo = new FundTargetCheckOrderVo();
            ftcoVo.setStoreId(storeId);
            ftcoVo.setUserId(id);
            if (myFundMapper.findFundTargetCheckOrder(ftcoVo).size() > 0) {
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.USED_ERROR);
            }

            //删除用户
            if (myUserMapper.delete(userVo) != 1) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }

            //删除用户角色关系
            myUserMapper.deleteAllUserRole(new UserRoleVo(userVo.getStoreId(), userVo.getId()));
        });
        return CommonResponse.success();
    }

    /**
     * 修改用户
     * @param userVo
     * @return
     */
    @Transactional
    public CommonResponse update(UserVo userVo) {
        //判断参数
        if (userVo.getId() == null || userVo.getName() == null || userVo.getPassword() == null || userVo.getPhone() == null || userVo.getDisabled() == null ||
                userVo.getRoles() == null || userVo.getRoles().size() == 0) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //修改用户信息
        if (myUserMapper.update(userVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //删除用户角色关系
        UserRoleVo userRoleVo = new UserRoleVo(userVo.getStoreId(), userVo.getId());
        if (myUserMapper.deleteAllUserRole(userRoleVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //新增用户角色关系
        List<Role> roles = userVo.getRoles();
        roles.stream().forEach(role -> {
            RoleVo roleVo = new RoleVo(userVo.getStoreId(), role.getId());
            if (myRoleMapper.findById(roleVo) == null) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
            
            //新增用户角色关系
            UserRoleVo userRoleVo1 = new UserRoleVo(userVo.getStoreId(), userVo.getId(), role.getId());
            if (myUserMapper.addUserRole(userRoleVo1) != 1) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
        });

        return CommonResponse.success();
    }

    /**
     * 查找所有用户
     * @param userVo
     * @return
     */
    public CommonResponse findAll(UserVo userVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myUserMapper.findCount(userVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        //查询
        List<UserVo> userVos = myUserMapper.findAllPaged(userVo, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("用户编号", "id"));
        titles.add(new Title("姓名", "name"));
        titles.add(new Title("用户名", "username"));
        titles.add(new Title("密码", "password"));
        titles.add(new Title("电话", "phone"));
        titles.add(new Title("是否禁用", "disabled"));
        titles.add(new Title("岗位", "roles"));
        titles.add(new Title("备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, userVos, pageVo);
        
        return CommonResponse.success(commonResult);
    }

    /**
     * 根据用户id查找用户
     * @param userVo
     * @return
     */
    public CommonResponse findById(UserVo userVo) {
        userVo = myUserMapper.findById(userVo);
        if (userVo == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }
        return CommonResponse.success(userVo);
    }
}
