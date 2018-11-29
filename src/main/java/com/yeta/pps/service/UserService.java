package com.yeta.pps.service;

import com.yeta.pps.mapper.MyRoleMapper;
import com.yeta.pps.mapper.MyUserMapper;
import com.yeta.pps.po.Role;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.RoleVo;
import com.yeta.pps.vo.UserRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YETA
 * @date 2018/11/28/14:28
 */
@Service
public class UserService {

    @Autowired
    private MyUserMapper myUserMapper;

    @Autowired
    private MyRoleMapper myRoleMapper;

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
