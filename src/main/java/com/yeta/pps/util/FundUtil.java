package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyFundMapper;
import com.yeta.pps.vo.FundCheckOrderVo;
import com.yeta.pps.vo.FundOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author YETA
 * @date 2018/12/22/14:55
 */
@Component
public class FundUtil {

    @Autowired
    private MyFundMapper myFundMapper;

    /**
     * 红冲资金对账记录的方法
     * @param vo
     */
    @Transactional
    public void redDashedFundCheckOrderMethod(FundCheckOrderVo vo) {
        //判断参数
        if (vo.getStoreId() == null || vo.getOrderId() == null) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //获取参数
        Integer storeId = vo.getStoreId();
        String userId = vo.getUserId();

        //红冲
        if (myFundMapper.redDashedFundCheckOrder(vo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //获取红冲蓝单
        vo = myFundMapper.findFundCheckOrderByOrderId(vo);
        vo.setStoreId(storeId);

        //查询最新余额
        FundCheckOrderVo lastVo = myFundMapper.findLastBalanceMoney(vo);

        //设置红冲红单
        vo.setCreateTime(new Date());
        vo.setOrderStatus((byte) -2);
        if (vo.getInMoney() > 0 && vo.getOutMoney() == 0) {     //原来是正向收款
            vo.setBalanceMoney(lastVo.getBalanceMoney() - vo.getInMoney());
        } else if (vo.getInMoney() < 0 && vo.getOutMoney() == 0) {      //原来是负向收款
            vo.setBalanceMoney(lastVo.getBalanceMoney() + vo.getInMoney());
        } else if (vo.getInMoney() == 0 && vo.getOutMoney() > 0) {      //原来是正向付款
            vo.setBalanceMoney(lastVo.getBalanceMoney() + vo.getOutMoney());
        } else if (vo.getInMoney() == 0 && vo.getOutMoney() < 0) {      //原来是负向付款
            vo.setBalanceMoney(lastVo.getBalanceMoney() + vo.getOutMoney());
        }
        vo.setInMoney(-vo.getInMoney());
        vo.setOutMoney(-vo.getOutMoney());
        vo.setUserId(userId);

        //新增红冲红单
        if (myFundMapper.addFundCheckOrder(vo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }
    }
}
