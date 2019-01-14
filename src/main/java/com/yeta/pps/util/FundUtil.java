package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyFundMapper;
import com.yeta.pps.mapper.MyMarketingMapper;
import com.yeta.pps.po.ClientDiscountCoupon;
import com.yeta.pps.po.DiscountCoupon;
import com.yeta.pps.vo.FundCheckOrderVo;
import com.yeta.pps.vo.FundOrderVo;
import com.yeta.pps.vo.FundResultOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author YETA
 * @date 2018/12/22/14:55
 */
@Component
public class FundUtil {

    @Autowired
    private MyFundMapper myFundMapper;

    @Autowired
    private MyMarketingMapper myMarketingMapper;

    /**
     * 返回四舍五入2位小数的方法
     * @param number
     * @return
     */
    public double getNumberMethod(double number) {
        return (double) Math.round(number * 100) / 100;
    }

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
        if (vo.getInMoney() != 0 && vo.getOutMoney() == 0) {     //原来是收款
            vo.setBalanceMoney(lastVo.getBalanceMoney() - vo.getInMoney());
        } else if (vo.getInMoney() == 0 && vo.getOutMoney() != 0) {      //原来是付款
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

    /**
     * 获取优惠券金额的方法
     * @param storeId
     * @param discountCouponId
     * @param clientId
     * @param totalMoney
     * @return
     */
    @Transactional
    public double getDiscountCouponMoneyMethod(Integer storeId, Integer discountCouponId, String clientId, double totalMoney) {
        //根据编号获取优惠券
        DiscountCoupon discountCoupon = myMarketingMapper.findDiscountCouponById(new DiscountCoupon(storeId, discountCouponId));
        if (discountCoupon == null) {
            throw new CommonException(CommonResponse.ADD_ERROR, "优惠券相关操作错误");
        }

        //查询可用优惠券
        List<DiscountCoupon> discountCoupons = myMarketingMapper.findCanUseDiscountCouponByStoreIdAndClientId(new ClientDiscountCoupon(storeId, clientId));

        //判断优惠券是否可用
        Optional<DiscountCoupon> optional = discountCoupons.stream().filter(dc -> dc.getId().toString().equals(discountCoupon.getId().toString())).findFirst();
        if (!optional.isPresent()) {
            throw new CommonException(CommonResponse.ADD_ERROR, "优惠券相关操作错误");
        }

        //减少客户优惠券数量
        if (myMarketingMapper.decreaseClientDiscountCouponQuantity(new ClientDiscountCoupon(storeId, clientId, discountCouponId)) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR, "优惠券相关操作错误");
        }

        //增加优惠券已使用数量
        if (myMarketingMapper.increaseUsedQuantity(new DiscountCoupon(storeId, discountCouponId)) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR, "优惠券相关操作错误");
        }

        //判断优惠券类型
        switch (discountCoupon.getType()) {
            case 1:     //现金券
                return discountCoupon.getMoney();

            case 2:     //折扣券
                return getNumberMethod((1 - discountCoupon.getMoney()) * totalMoney);

            case 3:
                if (discountCoupon.getMoney() > totalMoney) {
                    throw new CommonException(CommonResponse.ADD_ERROR, "优惠券相关操作错误");
                }
                return discountCoupon.getDiscountMoney();

            default:
                throw new CommonException(CommonResponse.ADD_ERROR, "优惠券相关操作错误");
        }
    }

    /**
     * 重置优惠券的方法
     * @param storeId
     * @param discountCouponId
     * @param clientId
     */
    @Transactional
    public void backDiscountCouponMethod(Integer storeId, Integer discountCouponId, String clientId) {
        //增加客户优惠券数量
        if (myMarketingMapper.increaseClientDiscountCouponQuantity(new ClientDiscountCoupon(storeId, clientId, discountCouponId)) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR, "优惠券相关操作错误");
        }

        //减少优惠券已使用数量
        if (myMarketingMapper.decreaseUsedQuantity(new DiscountCoupon(storeId, discountCouponId)) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR, "优惠券相关操作错误");
        }
    }

    /**
     * 新增其他收入单/费用单的方法
     * @param fundResultOrderVo
     */
    @Transactional
    public void addFundResultOrderMethod(FundResultOrderVo fundResultOrderVo) {
        //设置库存对账记录
        FundCheckOrderVo fundCheckOrderVo = new FundCheckOrderVo();
        fundCheckOrderVo.setStoreId(fundResultOrderVo.getStoreId());
        fundCheckOrderVo.setCreateTime(new Date());
        fundCheckOrderVo.setOrderStatus((byte) 1);
        fundCheckOrderVo.setTargetId(fundResultOrderVo.getTargetId());
        fundCheckOrderVo.setBankAccountId(fundResultOrderVo.getBankAccountId());
        fundCheckOrderVo.setUserId(fundResultOrderVo.getUserId());

        //查询最新的资金对账记录
        FundCheckOrderVo lastVo = myFundMapper.findLastBalanceMoney(new FundCheckOrderVo(fundResultOrderVo.getStoreId(), null, null, fundResultOrderVo.getBankAccountId()));
        if (lastVo == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //判断新增类型
        byte type = fundResultOrderVo.getType();
        switch (type) {
            case 1:     //其他收入单:
                //判断参数
                if (fundResultOrderVo.getTargetId() == null) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                fundResultOrderVo.setId("QTSRD_" + UUID.randomUUID().toString().replace("-", ""));

                fundCheckOrderVo.setOrderId(fundResultOrderVo.getId());
                fundCheckOrderVo.setInMoney(fundResultOrderVo.getMoney());
                fundCheckOrderVo.setOutMoney(0.0);
                fundCheckOrderVo.setBalanceMoney(lastVo.getBalanceMoney() + fundResultOrderVo.getMoney());
                break;

            case 2:     //费用单:
                fundResultOrderVo.setId("YBFYD_" + UUID.randomUUID().toString().replace("-", ""));

                fundCheckOrderVo.setOrderId(fundResultOrderVo.getId());
                fundCheckOrderVo.setInMoney(0.0);
                fundCheckOrderVo.setOutMoney(fundResultOrderVo.getMoney());
                fundCheckOrderVo.setBalanceMoney(lastVo.getBalanceMoney() - fundResultOrderVo.getMoney());
                break;

            default:
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //设置初始属性
        fundResultOrderVo.setCreateTime(new Date());
        fundResultOrderVo.setOrderStatus((byte) 1);

        //新增
        if (myFundMapper.addFundResultOrder(fundResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //记录资金对账记录
        if (myFundMapper.addFundCheckOrder(fundCheckOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }
    }
}
