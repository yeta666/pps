package com.yeta.pps.service;

import com.yeta.pps.mapper.MyDepartmentMapper;
import com.yeta.pps.po.Department;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.DepartmentUserVo;
import com.yeta.pps.vo.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门相关逻辑处理
 * @author YETA
 * @date 2018/11/30/14:12
 */
@Service
public class DepartmentService {

    @Autowired
    private MyDepartmentMapper myDepartmentMapper;

    /**
     * 新增部门
     * @param departmentVo
     * @return
     */
    public CommonResponse add(DepartmentVo departmentVo) {
        //判断参数
        if (departmentVo.getName() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //新增
        if (myDepartmentMapper.add(departmentVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除部门
     * @param departmentVo
     * @return
     */
    @Transactional
    public CommonResponse delete(DepartmentVo departmentVo) {
        //判断参数
        if (departmentVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //删除部门
        if (myDepartmentMapper.delete(departmentVo) != 1) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        //删除部门用户关系
        DepartmentUserVo departmentUserVo = new DepartmentUserVo(departmentVo.getStoreId(), departmentVo.getId());
        myDepartmentMapper.deleteDepartmentUser(departmentUserVo);
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改部门
     * @param departmentVo
     * @return
     */
    public CommonResponse update(DepartmentVo departmentVo) {
        //判断参数
        if (departmentVo.getId() == null || departmentVo.getName() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改
        if (myDepartmentMapper.update(departmentVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查询所有部门
     * @param departmentVo
     * @return
     */
    public CommonResponse findAll(DepartmentVo departmentVo) {
        List<Department> departments = myDepartmentMapper.findAll(departmentVo);
        if (departments.size() == 0) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, departments, CommonResponse.MESSAGE1);
    }

    /**
     * 根据部门id查询部门
     * @param departmentVo
     * @return
     */
    public CommonResponse findById(DepartmentVo departmentVo) {
        //判断参数
        if (departmentVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //根据部门id查询部门
        Department department = myDepartmentMapper.findById(departmentVo);
        if (department == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, department, CommonResponse.MESSAGE1);
    }
}
