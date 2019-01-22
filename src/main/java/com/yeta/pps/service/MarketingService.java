package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyClientMapper;
import com.yeta.pps.mapper.MyMarketingMapper;
import com.yeta.pps.mapper.StoreMapper;
import com.yeta.pps.mapper.SystemMapper;
import com.yeta.pps.po.*;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.StoreClientUtil;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 营销相关逻辑处理
 * @author YETA
 * @date 2019/01/03/13:37
 */
@Service
public class MarketingService {

    @Autowired
    private MyMarketingMapper myMarketingMapper;

    @Autowired
    private StoreClientUtil storeClientUtil;

    @Autowired
    private MyClientMapper myClientMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private StoreMapper storeMapper;

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

    //短信模版

    /**
     * 新增短信模版
     * @param smsTemplate
     * @param check
     * @return
     */
    public CommonResponse addSMSTemplate(SMSTemplate smsTemplate, String check) {
        //判断权限
        if (!storeClientUtil.checkMethod(check)) {
            return CommonResponse.error(CommonResponse.PERMISSION_ERROR);
        }

        //新增
        if (myMarketingMapper.addSMSTemplate(smsTemplate) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }

        return CommonResponse.success();
    }

    /**
     * 删除短信模版
     * @param smsTemplates
     * @param check
     * @return
     */
    @Transactional
    public CommonResponse deleteSMSTemplate(List<SMSTemplate> smsTemplates, String check) {
        //判断权限
        if (!storeClientUtil.checkMethod(check)) {
            return CommonResponse.error(CommonResponse.PERMISSION_ERROR);
        }

        //删除
        smsTemplates.stream().forEach(smsTemplate -> {
            if (myMarketingMapper.deleteSMSTemplate(smsTemplate) != 1) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
        });

        return CommonResponse.success();
    }

    /**
     * 修改短信模版
     * @param smsTemplate
     * @param check
     * @return
     */
    public CommonResponse updateSMSTemplate(SMSTemplate smsTemplate, String check) {
        //判断权限
        if (!storeClientUtil.checkMethod(check)) {
            return CommonResponse.error(CommonResponse.PERMISSION_ERROR);
        }

        //修改
        if (myMarketingMapper.updateSMSTemplate(smsTemplate) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        return CommonResponse.success();
    }

    /**
     * 查询短信模版
     * @param smsTemplate
     * @param pageVo
     * @return
     */
    public CommonResponse findSMSTemplate(SMSTemplate smsTemplate, PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage((int) Math.ceil(myMarketingMapper.findCountSMSTemplate(smsTemplate) * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<SMSTemplate> smsTemplates = myMarketingMapper.findPagedSMSTemplate(smsTemplate, pageVo);

            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("标题", "title"));
            titles.add(new Title("内容", "content"));
            titles.add(new Title("类型", "typeName"));
            titles.add(new Title("创建时间", "createTime"));
            titles.add(new Title("修改时间", "updateTime"));
            CommonResult commonResult = new CommonResult(titles, smsTemplates, pageVo);
            return CommonResponse.success(commonResult);
        }

        //不分页
        List<SMSTemplate> smsTemplates = myMarketingMapper.findAllSMSTemplate(smsTemplate);
        return CommonResponse.success(smsTemplates);
    }

    //短信历史

    /**
     * 发短信/新增短信历史
     * @param smsHistories
     * @return
     */
    @Transactional
    public CommonResponse addSMSHistory(List<SMSHistory> smsHistories) {
        //查询所有客户
        List<ClientVo> clientVos = myClientMapper.findAll(new ClientVo());

        //判断参数
        smsHistories.stream().forEach(smsHistory -> {
            if (smsHistory.getStoreId() == null || smsHistory.getClientId() == null || smsHistory.getUserId() == null || smsHistory.getContent() == null) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }

            //过滤客户手机号
            Optional<ClientVo> optional = clientVos.stream().filter(clientVo -> clientVo.getId().equals(smsHistory.getClientId())).findFirst();
            if (!optional.isPresent()) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR, "客户编号错误");
            }
            smsHistory.setClientPhone(optional.get().getPhone());
        });

        //判断是否能发送短息
        SSystem sSystem = systemMapper.findSystem();
        if (sSystem == null || sSystem.getSignature() == null) {
            return CommonResponse.error(CommonResponse.ADD_ERROR, "短信签名未设置");
        }
        Integer smsQuantity = smsHistories.size();
        if (sSystem.getSmsQuantity() < smsQuantity) {
            return CommonResponse.error(CommonResponse.ADD_ERROR, "系统剩余短信条数不足");
        }
        Integer storeId = smsHistories.get(0).getStoreId();
        StoreVo storeVo = storeMapper.findById(new Store(storeId));
        if (storeVo == null || storeVo.getSmsQuantity() < smsQuantity) {
            return CommonResponse.error(CommonResponse.ADD_ERROR, "店铺剩余短信条数不足");
        }

        //发短信
        smsHistories.stream().forEach(smsHistory -> {

            smsHistory.setStatus((byte) 1);

            smsHistory.setStatus((byte) 0);
            smsHistory.setRemark(null);

            //新增短信历史
            myMarketingMapper.addSMSHistory(smsHistory);
        });

        //减少店铺剩余短信条数
        if (storeMapper.decreaseSMSQuantityById(new Store(storeId, smsQuantity)) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR, "减少店铺剩余短信条数失败");
        }

        //减少系统剩余短信条数
        if (systemMapper.decreaseSMSQuantity(new SSystem(storeId, smsQuantity)) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR, "减少系统剩余短信条数失败");
        }

        return CommonResponse.success();
    }

    /**
     * 查询短信历史
     * @param smsHistory
     * @param pageVo
     * @return
     */
    public CommonResponse findSMSHistory(SMSHistory smsHistory, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myMarketingMapper.findCountSMSHistory(smsHistory) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<SMSHistory> smsHistories = myMarketingMapper.findPagedSMSHistory(smsHistory, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("店铺编号", "storeId"));
        titles.add(new Title("店铺名称", "storeName"));
        titles.add(new Title("客户编号", "clientId"));
        titles.add(new Title("客户名称", "clientName"));
        titles.add(new Title("客户电话", "clientPhone"));
        titles.add(new Title("发送时间", "createTime"));
        titles.add(new Title("经手人编号", "userId"));
        titles.add(new Title("经手人名称", "userName"));
        titles.add(new Title("内容", "content"));
        titles.add(new Title("发送状态", "status"));
        titles.add(new Title("失败原因", "remark"));
        CommonResult commonResult = new CommonResult(titles, smsHistories, pageVo);
        return CommonResponse.success(commonResult);
    }
}
