package com.yeta.pps.util;

import com.yeta.pps.mapper.SystemMapper;
import com.yeta.pps.po.SSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author YETA
 * @date 2019/01/17/13:00
 */
@Component
public class SystemUtil {

    @Autowired
    private SystemMapper systemMapper;

    /**
     * 判断系统是否开账的方法
     * @param storeId
     * @return
     */
    public boolean judgeStartBillMethod(Integer storeId) {
        SSystem sSystem = systemMapper.findStartBill(new SSystem(storeId));
        if (sSystem.getStartBill() == 1) {
            return true;
        } else {
            return false;
        }
    }
}
