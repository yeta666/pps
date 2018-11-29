package com.yeta.pps.mapper;

import com.yeta.pps.po.Store;
import com.yeta.pps.util.CommonMapper;

public interface StoreMapper extends CommonMapper<Store> {

    int add(Store store);

    int updateUserId(Store store);
}