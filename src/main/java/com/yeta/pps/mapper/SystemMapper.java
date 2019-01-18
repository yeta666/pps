package com.yeta.pps.mapper;

import com.yeta.pps.po.SSystem;

public interface SystemMapper {

    int addStoreSystem(SSystem sSystem);

    int deleteStoreSystem(SSystem sSystem);

    SSystem findPushMoneyRate();

    int updatePushMoneyRate(SSystem sSystem);

    SSystem findStartBill(SSystem sSystem);

    int updateStartBill(SSystem sSystem);

    SSystem findRetail(SSystem sSystem);

    int updateRetail(SSystem sSystem);
}