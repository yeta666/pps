package com.yeta.pps.mapper;

import com.yeta.pps.po.Store;
import com.yeta.pps.vo.PageVo;

import java.util.List;

public interface StoreMapper {

    int add(Store store);

    void delete(Store store);

    int update(Store store);

    int findCount();

    List<Store> findAllPaged(PageVo pageVo);

    List<Store> findAll();
}