package com.yeta.pps.mapper;

import com.yeta.pps.po.Function;
import com.yeta.pps.po.Role;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.RoleFunctionVo;
import com.yeta.pps.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyRoleMapper {

    int add(RoleVo roleVo);

    int delete(RoleVo roleVo);

    int update(RoleVo roleVo);

    int findCount(RoleVo roleVo);

    List<Role> findAll(@Param(value = "roleVo") RoleVo roleVo, @Param(value = "pageVo") PageVo pageVo);

    Role findById(RoleVo roleVo);

    //

    int addRoleFunction(RoleFunctionVo roleFunctionVo);

    int deleteRoleFunction(RoleFunctionVo roleFunctionVo);

    List<Function> findRoleFunction(RoleFunctionVo roleFunctionVo);
}