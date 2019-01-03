package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyMarketingMapper;
import com.yeta.pps.po.ClientDiscountCoupon;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.po.DiscountCoupon;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.ClientDiscountCouponVo;
import com.yeta.pps.vo.DiscountCouponVo;
import com.yeta.pps.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 营销相关逻辑处理
 * @author YETA
 * @date 2019/01/03/13:37
 */
@Service
public class MarketingService {

    @Autowired
    private MyMarketingMapper myMarketingMapper;

    //优惠券

    /**
     * 新增优惠券
     * @param discountCoupon
     * @return
     */
    public CommonResponse addDiscountCoupon(DiscountCoupon discountCoupon) {
        //判断参数
        byte type = discountCoupon.getType();
        if (type != 1 && type != 2 && type != 3) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //设置初始属性
        discountCoupon.setGivenQuantity(0);
        discountCoupon.setUsedQuantity(0);
        discountCoupon.setStatus((byte) 1);

        //新增优惠券
        if (myMarketingMapper.addDiscountCoupon(discountCoupon) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }

        return CommonResponse.success();
    }

    /**
     * 修改优惠券
     * @param discountCoupon
     * @return
     */
    public CommonResponse updateDiscountCoupon(DiscountCoupon discountCoupon) {
        //判断参数
        if (discountCoupon.getId() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //查询原始优惠券
        DiscountCoupon old = myMarketingMapper.findDiscountCouponById(discountCoupon);

        //判断是否可以修改
        if (old.getGivenQuantity() != 0) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //修改优惠券
        if (myMarketingMapper.updateDiscountCoupon(discountCoupon) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        return CommonResponse.success();
    }

    /**
     * 作废优惠券
     * @param discountCoupon
     * @return
     */
    public CommonResponse invalidDiscountCoupon(DiscountCoupon discountCoupon) {
        //判断参数
        if (discountCoupon.getStoreId() == null || discountCoupon.getId() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //作废优惠券
        if (myMarketingMapper.invalidDiscountCoupon(discountCoupon) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        return CommonResponse.success();
    }

    /**
     * 分页查询所有优惠券
     * @param discountCoupon
     * @param pageVo
     * @return
     */
    public CommonResponse findPagedDiscountCoupon(DiscountCoupon discountCoupon, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myMarketingMapper.findCountDiscountCoupon(discountCoupon) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<DiscountCouponVo> discountCouponVos = myMarketingMapper.findPagedDiscountCoupon(discountCoupon, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("名称", "name"));
        titles.add(new Title("类型", "typeName"));
        titles.add(new Title("使用规则", "useMethod"));
        titles.add(new Title("面值", "faceValue"));
        titles.add(new Title("数量", "quantity"));
        titles.add(new Title("已发放数量", "givenQuantity"));
        titles.add(new Title("已使用数量", "usedQuantity"));
        titles.add(new Title("开始时间", "startTime"));
        titles.add(new Title("结束时间", "endTime"));
        titles.add(new Title("状态", "statusName"));
        titles.add(new Title("线下使用", "useOfflineName"));
        titles.add(new Title("线上使用", "useOnlineName"));
        CommonResult commonResult = new CommonResult(titles, discountCouponVos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 根据编号查询优惠券
     * @param discountCoupon
     * @return
     */
    public CommonResponse findDiscountCouponById(DiscountCoupon discountCoupon) {
        discountCoupon = myMarketingMapper.findDiscountCouponById(discountCoupon);

        if (discountCoupon == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }

        return CommonResponse.success(discountCoupon);
    }

    /**
     * 发优惠券
     * @param clientDiscountCoupon
     * @return
     */
    @Transactional
    public CommonResponse giveDiscountCoupon(ClientDiscountCoupon clientDiscountCoupon) {
        //关联客户/优惠券
        if (myMarketingMapper.addClientDiscountCoupon(clientDiscountCoupon) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //增加优惠券已发数量
        if (myMarketingMapper.increaseGivenQuantity(new DiscountCoupon(clientDiscountCoupon.getStoreId(), clientDiscountCoupon.getDiscountCouponId(), clientDiscountCoupon.getQuantity())) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        return CommonResponse.success();
    }

    /**
     * 根据客户编号分页查询所有优惠券
     * @param clientDiscountCoupon
     * @param pageVo
     * @return
     */
    public CommonResponse findPagedDiscountCouponByClientId(ClientDiscountCoupon clientDiscountCoupon, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myMarketingMapper.findCountDiscountCouponByClientId(clientDiscountCoupon) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ClientDiscountCouponVo> clientDiscountCouponVos = myMarketingMapper.findPagedDiscountCouponByClientId(clientDiscountCoupon, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("店铺名", "storeName"));
        titles.add(new Title("优惠券名称", "name"));
        titles.add(new Title("类型", "typeName"));
        titles.add(new Title("使用规则", "useMethod"));
        titles.add(new Title("面值", "faceValue"));
        titles.add(new Title("数量", "quantity"));
        titles.add(new Title("开始时间", "startTime"));
        titles.add(new Title("结束时间", "endTime"));
        titles.add(new Title("状态", "statusName"));
        titles.add(new Title("线下使用", "useOfflineName"));
        titles.add(new Title("线上使用", "useOnlineName"));
        CommonResult commonResult = new CommonResult(titles, clientDiscountCouponVos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 根据店铺编号和客户编号查询可用优惠券
     * @param clientDiscountCoupon
     * @return
     */
    public CommonResponse findCanUseDiscountCouponByStoreIdAndClientId(ClientDiscountCoupon clientDiscountCoupon) {
        List<DiscountCoupon> discountCoupons = myMarketingMapper.findCanUseDiscountCouponByStoreIdAndClientId(clientDiscountCoupon);
        return CommonResponse.success(discountCoupons);
    }
}
