package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyRoleMapper;
import com.yeta.pps.mapper.MyUserMapper;
import com.yeta.pps.po.Function;
import com.yeta.pps.po.Role;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.FunctionMapVo;
import com.yeta.pps.vo.RoleFunctionVo;
import com.yeta.pps.vo.RoleVo;
import com.yeta.pps.vo.UserRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public CommonResponse delete(RoleVo roleVo) {
        //判断参数
        if (roleVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //删除角色
        if (myRoleMapper.delete(roleVo) != 1) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        //删除角色功能关系
        RoleFunctionVo roleFunctionVo = new RoleFunctionVo(roleVo.getStoreId(), roleVo.getId());
        myRoleMapper.deleteRoleFunction(roleFunctionVo);
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改角色
     * @param roleVo
     * @return
     */
    public CommonResponse update(RoleVo roleVo) {
        //判断参数
        if (roleVo.getId() == null || roleVo.getName() == null) {
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
    public CommonResponse findAll(RoleVo roleVo) {
        List<Role> roles = myRoleMapper.findAll(roleVo);
        if (roles.size() == 0) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, roles, CommonResponse.MESSAGE1);
    }

    //

    /**
     * 修改角色功能
     * @param functionMapVo
     * @return
     */
    @Transactional
    public CommonResponse updateRoleFunction(FunctionMapVo functionMapVo) {
        //删除该角色对应的功能
        RoleFunctionVo roleFunctionVo = new RoleFunctionVo(functionMapVo.getStoreId(), functionMapVo.getRoleId());
        myRoleMapper.deleteRoleFunction(roleFunctionVo);
        //增加该角色对应的一级功能
        if (functionMapVo.getLevel1() != null) {
            for (Function function : functionMapVo.getLevel1()) {
                roleFunctionVo = new RoleFunctionVo(functionMapVo.getStoreId(), functionMapVo.getRoleId(), function.getId());
                if (myRoleMapper.addRoleFunction(roleFunctionVo) != 1) {
                    throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
                }
            }
        }
        //增加该角色对应的二级功能
        if (functionMapVo.getLevel1() != null) {
            for (Function function : functionMapVo.getLevel2()) {
                roleFunctionVo = new RoleFunctionVo(functionMapVo.getStoreId(), functionMapVo.getRoleId(), function.getId());
                if (myRoleMapper.addRoleFunction(roleFunctionVo) != 1) {
                    throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
                }
            }
        }
        //增加该角色对应的三级功能
        if (functionMapVo.getLevel1() != null) {
            for (Function function : functionMapVo.getLevel3()) {
                roleFunctionVo = new RoleFunctionVo(functionMapVo.getStoreId(), functionMapVo.getRoleId(), function.getId());
                if (myRoleMapper.addRoleFunction(roleFunctionVo) != 1) {
                    throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
                }
            }
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 根据用户id查找对应的功能
     * @param userRoleVo
     * @return
     */
    public CommonResponse findRoleFunction(UserRoleVo userRoleVo) {
        Role role = myUserMapper.findUserRole(userRoleVo);
        if (role == null || role.getId() == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return findRoleFunction(new FunctionMapVo(userRoleVo.getStoreId(), role.getId()));
    }

    /**
     * 根据角色id查找对应的功能
     * @param functionMapVo
     * @return
     */
    public CommonResponse findRoleFunction(FunctionMapVo functionMapVo) {
        //根据用户id查询所有功能
        RoleFunctionVo roleFunctionVo = new RoleFunctionVo(functionMapVo.getStoreId(), functionMapVo.getRoleId());
        List<Function> functions = myRoleMapper.findRoleFunction(roleFunctionVo);
        //过滤一级功能
        List<Function> level1 = functions.stream().filter(function -> function.getLevel().toString().equals("1")).collect(Collectors.toList());
        //过滤二级功能
        List<Function> level2 = functions.stream().filter(function -> function.getLevel().toString().equals("2")).collect(Collectors.toList());
        //过滤三级功能
        List<Function> level3 = functions.stream().filter(function -> function.getLevel().toString().equals("3")).collect(Collectors.toList());
        //封装返回结果
        functionMapVo.setLevel1(level1);
        functionMapVo.setLevel2(level2);
        functionMapVo.setLevel3(level3);
        return new CommonResponse(CommonResponse.CODE1, functionMapVo, CommonResponse.MESSAGE1);
    }
}
