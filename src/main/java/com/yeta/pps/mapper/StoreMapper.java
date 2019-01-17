package com.yeta.pps.mapper;

import com.yeta.pps.po.Store;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.StoreVo;

import java.util.List;

public interface StoreMapper {

    int add(Store store);

    int delete(Store store);

    int update(Store store);

    int findCount();

    List<StoreVo> findPaged(PageVo pageVo);

    List<Store> findAll();

    StoreVo findById(Store store);
}