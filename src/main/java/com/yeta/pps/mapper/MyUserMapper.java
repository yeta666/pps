package com.yeta.pps.mapper;

import com.yeta.pps.po.Role;
import com.yeta.pps.po.User;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.UserRoleVo;
import com.yeta.pps.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyUserMapper {

    int add(UserVo userVo);

    int delete(UserVo userVo);

    int update(UserVo userVo);

    int findCount(UserVo userVo);

    List<UserVo> findAllPaged(@Param(value = "userVo") UserVo userVo, @Param(value = "pageVo") PageVo pageVo);

    List<UserVo> findAll(UserVo userVo);

    UserVo findById(UserVo userVo);

    User findByUsernameAndPassword(UserVo userVo);

    //

    int addUserRole(UserRoleVo userRoleVo);

    int deleteAllUserRole(UserRoleVo userRoleVo);

    List<Role> findUserRole(UserRoleVo userRoleVo);
}