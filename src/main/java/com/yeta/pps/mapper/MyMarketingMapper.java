package com.yeta.pps.mapper;

import com.yeta.pps.po.ClientDiscountCoupon;
import com.yeta.pps.po.DiscountCoupon;
import com.yeta.pps.vo.BankAccountVo;
import com.yeta.pps.vo.ClientDiscountCouponVo;
import com.yeta.pps.vo.DiscountCouponVo;
import com.yeta.pps.vo.PageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyMarketingMapper {

    //优惠券

    int addDiscountCoupon(DiscountCoupon discountCoupon);

    int updateDiscountCoupon(DiscountCoupon discountCoupon);

    int invalidDiscountCoupon(DiscountCoupon discountCoupon);

    int increaseGivenQuantity(DiscountCoupon discountCoupon);

    int increaseUsedQuantity(DiscountCoupon discountCoupon);

    int decreaseUsedQuantity(DiscountCoupon discountCoupon);

    int findCountDiscountCoupon(DiscountCoupon discountCoupon);

    List<DiscountCouponVo> findPagedDiscountCoupon(@Param(value = "vo") DiscountCoupon discountCoupon,
                                                   @Param(value = "pageVo") PageVo pageVo);

    DiscountCoupon findDiscountCouponById(DiscountCoupon discountCoupon);

    int findCountDiscountCouponByClientId(ClientDiscountCoupon clientDiscountCoupon);

    List<ClientDiscountCouponVo> findPagedDiscountCouponByClientId(@Param(value = "vo") ClientDiscountCoupon clientDiscountCoupon,
                                                                   @Param(value = "pageVo") PageVo pageVo);

    List<DiscountCoupon> findCanUseDiscountCouponByStoreIdAndClientId(ClientDiscountCoupon clientDiscountCoupon);

    //客户/优惠券关系

    int addClientDiscountCoupon(ClientDiscountCoupon clientDiscountCoupon);

    int increaseClientDiscountCouponQuantity(ClientDiscountCoupon clientDiscountCoupon);

    int decreaseClientDiscountCouponQuantity(ClientDiscountCoupon clientDiscountCoupon);
}