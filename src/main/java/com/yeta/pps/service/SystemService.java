package com.yeta.pps.service;

import com.yeta.pps.mapper.SystemMapper;
import com.yeta.pps.po.System;
import com.yeta.pps.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统设置相关逻辑处理
 * @author YETA
 * @date 2019/01/14/19:09
 */
@Service
public class SystemService {

    @Autowired
    private SystemMapper systemMapper;

    /**
     * 查询系统设置
     * @return
     */
    public CommonResponse findSystem() {
        System system = systemMapper.findSystem();
        if (system == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }

        return CommonResponse.success(system);
    }

    /**
     * 修改提成比例
     * @param system
     * @return
     */
    public CommonResponse updatePushMoneyRate(System system) {
        if (systemMapper.updatePushMoneyRate(system) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        
        return CommonResponse.success();
    }
}
