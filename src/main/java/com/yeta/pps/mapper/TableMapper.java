package com.yeta.pps.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author YETA
 * @date 2018/11/28/14:38
 */
public interface TableMapper {

    //创建表

    void add(@Param(value = "storeId") Integer storeId);

    //删除表

    void delete(@Param(value = "storeId") Integer storeId);
}
