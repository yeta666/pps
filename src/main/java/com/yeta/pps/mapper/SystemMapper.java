package com.yeta.pps.mapper;

import com.yeta.pps.po.System;

public interface SystemMapper {

    System findSystem();

    int updatePushMoneyRate(System system);
}