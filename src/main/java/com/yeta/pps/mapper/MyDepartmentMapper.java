package com.yeta.pps.mapper;

import com.yeta.pps.po.Department;
import com.yeta.pps.po.User;
import com.yeta.pps.vo.DepartmentUserVo;
import com.yeta.pps.vo.DepartmentVo;

import java.util.List;

public interface MyDepartmentMapper {

    int add(DepartmentVo departmentVo);

    int delete(DepartmentVo departmentVo);

    int update(DepartmentVo departmentVo);

    List<Department> findAll(DepartmentVo departmentVo);

    Department findById(DepartmentVo departmentVo);

    //

    int addDepartmentUser(DepartmentUserVo departmentUserVo);

    int deleteDepartmentUser(DepartmentUserVo departmentUserVo);

    int deleteUserDepartment(DepartmentUserVo departmentUserVo);

    List<User> findDepartmentUser(DepartmentUserVo departmentUserVo);
}