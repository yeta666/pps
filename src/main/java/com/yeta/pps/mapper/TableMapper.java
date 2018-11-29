package com.yeta.pps.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author YETA
 * @date 2018/11/28/14:38
 */
public interface TableMapper {

    int addUser(@Param(value = "storeId") Integer storeId);

    int deleteUser(@Param(value = "storeId") Integer storeId);

    int addRole(@Param(value = "storeId") Integer storeId);

    int deleteRole(@Param(value = "storeId") Integer storeId);

    int addUserRole(@Param(value = "storeId") Integer storeId);

    int deleteUserRole(@Param(value = "storeId") Integer storeId);

    int addRoleFunction(@Param(value = "storeId") Integer storeId);

    int deleteRoleFunction(@Param(value = "storeId") Integer storeId);
}
