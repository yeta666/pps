package com.yeta.pps.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用Mapper
 * Created by YETA666 on 2018/4/19 0019.
 */
public interface CommonMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
