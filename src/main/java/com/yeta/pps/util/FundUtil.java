package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyFundMapper;
import com.yeta.pps.mapper.MyMarketingMapper;
import com.yeta.pps.po.ClientDiscountCoupon;
import com.yeta.pps.po.DiscountCoupon;
import com.yeta.pps.vo.FundCheckOrderVo;
import com.yeta.pps.vo.FundResultOrderVo;
import com.yeta.pps.vo.FundTargetCheckOrderVo;
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

    @Autowired
    private PrimaryKeyUtil primaryKeyUtil;

    /**
     * 返回四舍五入2位小数的方法
     * @param number
     * @return
     */
    public double getNumberMethod(double number) {
        return (double) Math.round(number * 100) / 100;
    }

    /**
     * 新增期初资金对账记录的方法
     * @param storeId
     * @param bankAccountId
     * @param openingMoney
     * @param userId
     */
    @Transactional
    public void addOpeningFundCheckOrderMethod(Integer storeId, String bankAccountId, double openingMoney, String userId) {
        //资金记账
        FundCheckOrderVo fundCheckOrderVo = new FundCheckOrderVo();
        fundCheckOrderVo.setStoreId(storeId);
        fundCheckOrderVo.setOrderId("期初调整");
        fundCheckOrderVo.setCreateTime(new Date());
        fundCheckOrderVo.setOrderStatus((byte) 1);
        fundCheckOrderVo.setBankAccountId(bankAccountId);
        fundCheckOrderVo.setInMoney(0.0);
        fundCheckOrderVo.setOutMoney(0.0);
        fundCheckOrderVo.setBalanceMoney(openingMoney);
        fundCheckOrderVo.setUserId(userId);
        if (myFundMapper.addFundCheckOrder(fundCheckOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }
    }

    /**
     * 新增期初往来对账记录的方法
     * @param flag
     * @param storeId
     * @param targetId
     * @param needMoneyOpening
     * @param advanceMoneyOpening
     * @param userId
     */
    @Transactional
    public void addOpeningFundTargetCheckOrderMethod(int flag, Integer storeId, String targetId, double needMoneyOpening, double advanceMoneyOpening, String userId) {
        //往来记账
        FundTargetCheckOrderVo fundTargetCheckOrderVo = new FundTargetCheckOrderVo();
        fundTargetCheckOrderVo.setStoreId(storeId);
        fundTargetCheckOrderVo.setOrderId("期初调整");
        fundTargetCheckOrderVo.setCreateTime(new Date());
        fundTargetCheckOrderVo.setOrderStatus((byte) 1);
        fundTargetCheckOrderVo.setTargetId(targetId);
        if (flag == 1) {
            fundTargetCheckOrderVo.setNeedInMoneyIncrease(needMoneyOpening);       //设置应收增加
            fundTargetCheckOrderVo.setNeedInMoney(fundTargetCheckOrderVo.getNeedInMoneyIncrease());       //设置期末应收
            fundTargetCheckOrderVo.setAdvanceInMoneyIncrease(advanceMoneyOpening);       //设置预收增加
            fundTargetCheckOrderVo.setAdvanceInMoney(fundTargetCheckOrderVo.getAdvanceInMoneyIncrease());       //设置期末预收
        } else if (flag == 0) {
            fundTargetCheckOrderVo.setNeedOutMoneyIncrease(needMoneyOpening);       //设置应付增加
            fundTargetCheckOrderVo.setNeedOutMoney(fundTargetCheckOrderVo.getNeedOutMoneyIncrease());       //设置期末应付
            fundTargetCheckOrderVo.setAdvanceOutMoneyIncrease(advanceMoneyOpening);       //设置预付增加
            fundTargetCheckOrderVo.setAdvanceOutMoney(fundTargetCheckOrderVo.getAdvanceOutMoneyIncrease());       //设置期末预付
        }
        fundTargetCheckOrderVo.setUserId(userId);
        if (myFundMapper.addFundTargetCheckOrder(fundTargetCheckOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }
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
        myFundMapper.redDashedFundCheckOrder(vo);

        //获取红冲蓝单
        List<FundCheckOrderVo> vos = myFundMapper.findFundCheckOrderByOrderId(vo);
        vos.stream().forEach(vo1 -> {
            vo1.setStoreId(storeId);

            //查询最新余额
            FundCheckOrderVo lastVo = myFundMapper.findLastBalanceMoney(vo1);
            if (lastVo == null) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }

            //设置红冲红单
            vo1.setOrderId("HC-" + vo1.getOrderId());
            vo1.setCreateTime(new Date());
            vo1.setOrderStatus((byte) -2);
            if (vo1.getInMoney() != 0 && vo1.getOutMoney() == 0) {     //原来是收款
                vo1.setBalanceMoney(lastVo.getBalanceMoney() - vo1.getInMoney());
            } else if (vo1.getInMoney() == 0 && vo1.getOutMoney() != 0) {      //原来是付款
                vo1.setBalanceMoney(lastVo.getBalanceMoney() + vo1.getOutMoney());
            }
            vo1.setInMoney(-vo1.getInMoney());
            vo1.setOutMoney(-vo1.getOutMoney());
            vo1.setUserId(userId);

            //新增红冲红单
            if (myFundMapper.addFundCheckOrder(vo1) != 1) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
        });

    }

    /**
     * 红冲往来对账记录的方法
     * @param vo
     */
    @Transactional
    public void redDashedFundTargetCheckOrderMethod(FundTargetCheckOrderVo vo) {
        //判断参数
        if (vo.getStoreId() == null || vo.getOrderId() == null) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //获取参数
        Integer storeId = vo.getStoreId();
        String userId = vo.getUserId();

        //红冲
        myFundMapper.redDashedFundTargetCheckOrder(vo);

        //获取红冲蓝单
        List<FundTargetCheckOrderVo> vos = myFundMapper.findFundTargetCheckOrder(vo);
        vos.stream().forEach(vo1 -> {
            vo1.setStoreId(storeId);

            //查询最新往来对账记录
            FundTargetCheckOrderVo lastVo = myFundMapper.findLastFundTargetCheckOrder(vo1);
            if (lastVo == null) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }

            //设置红冲红单
            vo1.setOrderId("HC-" + vo1.getOrderId());
            vo1.setCreateTime(new Date());
            vo1.setOrderStatus((byte) -2);
            if (vo1.getNeedInMoneyIncrease() != 0 && vo1.getNeedInMoneyDecrease() == 0) {     //原来是增加
                vo1.setNeedInMoney(lastVo.getNeedInMoney() - vo1.getNeedInMoneyIncrease());
            } else if (vo1.getNeedInMoneyIncrease() == 0 && vo1.getNeedInMoneyDecrease() != 0) {      //原来是减少
                vo1.setNeedInMoney(lastVo.getNeedInMoney() + vo1.getNeedInMoneyDecrease());
            } else if (vo1.getNeedInMoneyIncrease() == 0 && vo1.getNeedInMoneyDecrease() == 0) {      //不变
                vo1.setNeedInMoney(lastVo.getNeedInMoney());
            }
            vo1.setNeedInMoneyIncrease(-vo1.getNeedInMoneyIncrease());
            vo1.setNeedInMoneyDecrease(-vo1.getNeedInMoneyDecrease());
            if (vo1.getAdvanceInMoneyIncrease() != 0 && vo1.getAdvanceInMoneyDecrease() == 0) {     //原来是增加
                vo1.setAdvanceInMoney(lastVo.getAdvanceInMoney() - vo1.getAdvanceInMoneyIncrease());
            } else if (vo1.getAdvanceInMoneyIncrease() == 0 && vo1.getAdvanceInMoneyDecrease() != 0) {      //原来是减少
                vo1.setAdvanceInMoney(lastVo.getAdvanceInMoney() + vo1.getAdvanceInMoneyDecrease());
            } else if (vo1.getAdvanceInMoneyIncrease() == 0 && vo1.getAdvanceInMoneyDecrease() == 0) {      //不变
                vo1.setAdvanceInMoney(lastVo.getAdvanceInMoney());
            }
            vo1.setAdvanceInMoneyIncrease(-vo1.getAdvanceInMoneyIncrease());
            vo1.setAdvanceInMoneyDecrease(-vo1.getAdvanceInMoneyDecrease());
            if (vo1.getNeedOutMoneyIncrease() != 0 && vo1.getNeedOutMoneyDecrease() == 0) {     //原来是增加
                vo1.setNeedOutMoney(lastVo.getNeedOutMoney() - vo1.getNeedOutMoneyIncrease());
            } else if (vo1.getNeedOutMoneyIncrease() == 0 && vo1.getNeedOutMoneyDecrease() != 0) {      //原来是减少
                vo1.setNeedOutMoney(lastVo.getNeedOutMoney() + vo1.getNeedOutMoneyDecrease());
            } else if (vo1.getNeedOutMoneyIncrease() == 0 && vo1.getNeedOutMoneyDecrease() == 0) {      //不变
                vo1.setNeedOutMoney(lastVo.getNeedOutMoney());
            }
            vo1.setNeedOutMoneyIncrease(-vo1.getNeedOutMoneyIncrease());
            vo1.setNeedOutMoneyDecrease(-vo1.getNeedOutMoneyDecrease());
            if (vo1.getAdvanceOutMoneyIncrease() != 0 && vo1.getAdvanceOutMoneyDecrease() == 0) {     //原来是增加
                vo1.setAdvanceOutMoney(lastVo.getAdvanceOutMoney() - vo1.getAdvanceOutMoneyIncrease());
            } else if (vo1.getAdvanceOutMoneyIncrease() == 0 && vo1.getAdvanceOutMoneyDecrease() != 0) {      //原来是减少
                vo1.setAdvanceOutMoney(lastVo.getAdvanceOutMoney() + vo1.getAdvanceOutMoneyDecrease());
            } else if (vo1.getAdvanceOutMoneyIncrease() == 0 && vo1.getAdvanceOutMoneyDecrease() == 0) {      //不变
                vo1.setAdvanceOutMoney(lastVo.getAdvanceOutMoney());
            }
            vo1.setAdvanceOutMoneyIncrease(-vo1.getAdvanceOutMoneyIncrease());
            vo1.setAdvanceOutMoneyDecrease(-vo1.getAdvanceOutMoneyDecrease());
            vo1.setUserId(userId);

            //新增红冲红单
            if (myFundMapper.addFundTargetCheckOrder(vo1) != 1) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
        });
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

                fundResultOrderVo.setId(primaryKeyUtil.getOrderPrimaryKeyMethod(myFundMapper.findFundResultOrderPrimaryKey(fundResultOrderVo), "QTSRD"));

                fundCheckOrderVo.setOrderId(fundResultOrderVo.getId());
                fundCheckOrderVo.setInMoney(fundResultOrderVo.getMoney());
                fundCheckOrderVo.setOutMoney(0.0);
                fundCheckOrderVo.setBalanceMoney(lastVo.getBalanceMoney() + fundResultOrderVo.getMoney());
                break;

            case 2:     //费用单:
                fundResultOrderVo.setId(primaryKeyUtil.getOrderPrimaryKeyMethod(myFundMapper.findFundResultOrderPrimaryKey(fundResultOrderVo), "YBFYD"));

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
