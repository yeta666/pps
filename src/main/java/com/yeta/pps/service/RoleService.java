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
     * @param roleVo
     * @return
     */
    @Transactional
    public CommonResponse delete(RoleVo roleVo) {
        /*//判断角色是否使用

        //删除角色
        if (myRoleMapper.delete(roleVo) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }
        //删除角色用户关系
        UserRoleVo userRoleVo = new UserRoleVo(roleVo.getStoreId(), roleVo.getId());
        myUserMapper.deleteRoleUser(userRoleVo);
        //删除角色功能关系
        RoleFunctionVo roleFunctionVo = new RoleFunctionVo(roleVo.getStoreId(), roleVo.getId());
        myRoleMapper.deleteRoleFunction(roleFunctionVo);*/
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
     * @param functionMapVo
     * @return
     */
    @Transactional
    public CommonResponse updateRoleFunction(FunctionMapVo functionMapVo) {
        //判断参数
        List<Integer> roleIds = functionMapVo.getRoleIds();
        if (roleIds.size() != 1) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //删除该角色对应的功能
        myRoleMapper.deleteRoleFunction(new RoleFunctionVo(functionMapVo.getStoreId(), roleIds.get(0)));
        RoleFunctionVo roleFunctionVo = new RoleFunctionVo(functionMapVo.getStoreId(), roleIds.get(0));
        functionMapVo.getIds().stream().forEach(integer -> {
            roleFunctionVo.setFunctionId(integer);
            if (myRoleMapper.addRoleFunction(roleFunctionVo) != 1) {
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 根据用户id查找对应的功能
     * @param userRoleVo
     * @return
     */
    public CommonResponse findRoleFunction(UserRoleVo userRoleVo) {
        List<Role> roles = myUserMapper.findUserRole(userRoleVo);
        if (roles.size() == 0) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        List<Integer> roleIds = new ArrayList<>();
        roles.stream().forEach(role -> {
            roleIds.add(role.getId());
        });
        return findRoleFunction(new FunctionMapVo(userRoleVo.getStoreId(), roleIds));
    }

    /**
     * 根据角色id查找对应的功能
     * @param functionMapVo
     * @return
     */
    public CommonResponse findRoleFunction(FunctionMapVo functionMapVo) {
        //判断参数
        List<Integer> roleIds = functionMapVo.getRoleIds();
        if (roleIds.size() == 0) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        Set<Integer> ids = new HashSet<>();
        roleIds.stream().forEach(integer -> {
            //根据角色id查询所有功能
            RoleFunctionVo roleFunctionVo = new RoleFunctionVo(functionMapVo.getStoreId(), integer);
            List<Function> functions = myRoleMapper.findRoleFunction(roleFunctionVo);
            functions.stream().forEach(function -> ids.add(function.getId()));
        });
        return new CommonResponse(CommonResponse.CODE1, ids, CommonResponse.MESSAGE1);
    }
}
