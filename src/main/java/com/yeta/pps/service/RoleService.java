package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyRoleMapper;
import com.yeta.pps.mapper.MyUserMapper;
import com.yeta.pps.po.Function;
import com.yeta.pps.po.Role;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色相关逻辑处理
 * @author YETA
 * @date 2018/11/29/17:43
 */
@Service
public class RoleService {

    @Autowired
    private MyRoleMapper myRoleMapper;

    @Autowired
    private MyUserMapper myUserMapper;

    /**
     * 新增角色
     * @param roleVo
     * @return
     */
    public CommonResponse add(RoleVo roleVo) {
        //判断参数
        if (roleVo.getName() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //新增
        if (myRoleMapper.add(roleVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除角色
     * @param roleVos
     * @return
     */
    @Transactional
    public CommonResponse delete(List<RoleVo> roleVos) {
        roleVos.stream().forEach(roleVo -> {
            //判断角色是否使用
            UserRoleVo userRoleVo = new UserRoleVo(roleVo.getStoreId(), roleVo.getId());
            List<Role> roles = myUserMapper.findUserRole(userRoleVo);
            if (roles != null && roles.size() > 0) {
                throw new CommonException(CommonResponse.CODE18, CommonResponse.MESSAGE18);
            }
            //删除角色
            if (myRoleMapper.delete(roleVo) != 1) {
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
            //删除角色功能关系
            RoleFunctionVo roleFunctionVo = new RoleFunctionVo(roleVo.getStoreId(), roleVo.getId());
            myRoleMapper.deleteRoleFunction(roleFunctionVo);
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改角色
     * @param roleVo
     * @return
     */
    public CommonResponse update(RoleVo roleVo) {
        //判断参数
        if (roleVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改
        if (myRoleMapper.update(roleVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查询所有角色
     * @param roleVo
     * @return
     */
    public CommonResponse findAll(RoleVo roleVo, PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage((int) Math.ceil(myRoleMapper.findCount(roleVo) * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<Role> roles = myRoleMapper.findAllPaged(roleVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("岗位名", "name"));
            CommonResult commonResult = new CommonResult(titles, roles, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<Role> roles = myRoleMapper.findAll(roleVo);
        return new CommonResponse(CommonResponse.CODE1, roles, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询角色
     * @param roleVo
     * @return
     */
    public CommonResponse findById(RoleVo roleVo) {
        Role role = myRoleMapper.findById(roleVo);
        if (role == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, role, CommonResponse.MESSAGE1);
    }

    //

    /**
     * 修改角色功能
     * @param functionVo
     * @return
     */
    @Transactional
    public CommonResponse updateRoleFunction(FunctionVo functionVo) {
        //判断参数
        if (functionVo.getRoleId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //删除角色/功能关系
        RoleFunctionVo roleFunctionVo = new RoleFunctionVo(functionVo.getStoreId(), functionVo.getRoleId());
        myRoleMapper.deleteRoleFunction(roleFunctionVo);
        //新增角色/功能关系
        functionVo.getFunctionIds().stream().forEach(functionId -> {
            roleFunctionVo.setFunctionId(functionId);
            if (myRoleMapper.addRoleFunction(roleFunctionVo) != 1) {
                throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
            }
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 根据用户id查找对应的功能
     * @param functionVo
     * @return
     */
    public CommonResponse findRoleFunction(FunctionVo functionVo) {
        //判断参数
        Set<Integer> functionIds = new HashSet<>();
        if (functionVo.getUserId() != null && functionVo.getRoleId() == null) {
            //获取用户对应的角色
            UserRoleVo userRoleVo = new UserRoleVo(functionVo.getStoreId(), functionVo.getUserId());
            List<Role> roles = myUserMapper.findUserRole(userRoleVo);
            if (roles.size() == 0) {
                return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
            }
            //查询角色对应的功能
            roles.stream().forEach(role -> {
                RoleFunctionVo roleFunctionVo = new RoleFunctionVo(functionVo.getStoreId(), role.getId());
                List<Function> functions = myRoleMapper.findRoleFunction(roleFunctionVo);
                functions.stream().forEach(function -> functionIds.add(function.getId()));
            });
            return new CommonResponse(CommonResponse.CODE1, functionIds, CommonResponse.MESSAGE1);
        } else if (functionVo.getUserId() == null && functionVo.getRoleId() != null) {
            //查询角色对应的功能
            RoleFunctionVo roleFunctionVo = new RoleFunctionVo(functionVo.getStoreId(), functionVo.getRoleId());
            List<Function> functions = myRoleMapper.findRoleFunction(roleFunctionVo);
            functions.stream().forEach(function -> functionIds.add(function.getId()));
            return new CommonResponse(CommonResponse.CODE1, functionIds, CommonResponse.MESSAGE1);
        } else {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
    }
}
