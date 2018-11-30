package com.yeta.pps.mapper;

import com.yeta.pps.po.Role;
import com.yeta.pps.po.User;
import com.yeta.pps.vo.UserRoleVo;
import com.yeta.pps.vo.UserVo;

import java.util.List;

public interface MyUserMapper {

    int add(UserVo userVo);

    int delete(UserVo userVo);

    int update(UserVo userVo);

    List<User> findAll(UserVo userVo);

    User findById(UserVo userVo);

    User findByUsernameAndPassword(UserVo userVo);

    //

    int addUserRole(UserRoleVo userRoleVo);

    int deleteUserRole(UserRoleVo userRoleVo);

    int deleteRoleUser(UserRoleVo userRoleVo);

    int updateUserRole(UserRoleVo userRoleVo);

    Role findUserRole(UserRoleVo userRoleVo);
}