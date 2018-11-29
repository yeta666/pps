package com.yeta.pps.mapper;

import com.yeta.pps.po.Role;
import com.yeta.pps.po.User;
import com.yeta.pps.vo.UserRoleVo;
import com.yeta.pps.vo.UserVo;

import java.util.List;

public interface MyUserMapper {

    List<User> findAll(UserVo userVo);

    User findById(UserVo userVo);

    User findByUsernameAndPassword(UserVo userVo);

    int add(UserVo userVo);

    int update(UserVo userVo);

    int deleteById(UserVo userVo);

    //

    int addUserRole(UserRoleVo userRoleVo);

    int deleteUserRole(UserRoleVo userRoleVo);

    int updateUserRole(UserRoleVo userRoleVo);

    Role findUserRole(UserRoleVo userRoleVo);
}