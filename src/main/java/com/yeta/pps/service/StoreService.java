package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.*;
import com.yeta.pps.po.*;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.RoleFunctionVo;
import com.yeta.pps.vo.RoleVo;
import com.yeta.pps.vo.UserRoleVo;
import com.yeta.pps.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 店铺相关逻辑处理
 * @author YETA
 * @date 2018/11/28/18:06
 */
@Service
public class StoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private MyUserMapper myUserMapper;

    @Autowired
    private MyRoleMapper myRoleMapper;

    @Autowired
    private FunctionMapper functionMapper;

    /**
     * 新增分店
     * @param store
     * @return
     */
    @Transactional      //create语句不支持事务回滚，所有需要手动事务回滚
    public CommonResponse add(Store store) {
        //插入一条分店数据，还未插入店长id
        if (storeMapper.add(store) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        Integer storeId = store.getId();
        try {
            //创建该分店的一套表
            tableMapper.addUser(storeId);
            tableMapper.addRole(storeId);
            tableMapper.addUserRole(storeId);
            tableMapper.addRoleFunction(storeId);
        } catch (Exception e) {
            //手动尽量回滚事务，如果这部分出现异常会导致回滚不成功
            storeMapper.deleteByPrimaryKey(storeId);
            tableMapper.deleteUser(storeId);
            tableMapper.deleteRole(storeId);
            tableMapper.deleteUserRole(storeId);
            tableMapper.addRoleFunction(storeId);
            throw new CommonException(CommonResponse.CODE2, e.getMessage());
        }
        return new CommonResponse(CommonResponse.CODE1, storeId, CommonResponse.MESSAGE1);
    }

    /**
     * 初始化分店信息
     * @param userVo
     * @return
     */
    @Transactional
    public CommonResponse initialize(UserVo userVo) {
        //判断参数
        if (userVo.getName() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        Integer storeId = userVo.getStoreId();
        //插入店长信息到该分店的user表
        userVo.setId(UUID.randomUUID().toString().replace("-", ""));
        if (myUserMapper.add(userVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //插入角色信息到该分店的role表
        RoleVo roleVo = new RoleVo(storeId, "老板");
        if (myRoleMapper.add(roleVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //插入用户角色关系到该分店的user_role表
        UserRoleVo userRoleVo = new UserRoleVo(storeId, userVo.getId(), roleVo.getId());
        if (myUserMapper.addUserRole(userRoleVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //插入角色功能关系到高分店的role_function表
        List<Function> functions = functionMapper.selectAll();
        for (Function function : functions) {
            RoleFunctionVo roleFunctionVo = new RoleFunctionVo(storeId, roleVo.getId(), function.getId());
            if (myRoleMapper.addRoleFunction(roleFunctionVo) != 1) {
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }
        }
        //插入店长id到店铺表
        if (storeMapper.updateUserId(new Store(storeId, userVo.getId())) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }
}
