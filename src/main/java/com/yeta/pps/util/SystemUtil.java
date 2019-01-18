package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.SystemMapper;
import com.yeta.pps.po.SSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        if (sSystem != null && sSystem.getStartBill() == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断仓库、银行账户是否在系统设置中使用的方法
     * @param sSystem
     * @return
     */
    @Transactional
    public void judgeRetailMethod(SSystem sSystem) {
        //判断参数
        if (sSystem.getStoreId() == null || (sSystem.getRetailWarehouseId() == null && sSystem.getRetailBankAccountId() == null)) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        sSystem = systemMapper.findRetail(sSystem);
        if (sSystem != null) {
            throw new CommonException(CommonResponse.USED_ERROR);
        }
    }
}
