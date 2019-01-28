package com.yeta.pps.controller;

import com.aliyuncs.exceptions.ClientException;
import com.yeta.pps.po.ClientDiscountCoupon;
import com.yeta.pps.po.DiscountCoupon;
import com.yeta.pps.po.SMSHistory;
import com.yeta.pps.po.SMSTemplate;
import com.yeta.pps.service.MarketingService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.ClientDiscountCouponVo;
import com.yeta.pps.vo.DiscountCouponVo;
import com.yeta.pps.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 营销相关接口
 * @author YETA
 * @date 2019/01/03/15:12
 */
@Api(value = "营销相关接口")
@RestController
@RequestMapping(value = "/marketing")
public class MarketingController {

    @Autowired
    private MarketingService marketingService;

    //优惠券

    /**
     * 新增优惠券接口
     * @param discountCoupon
     * @return
     */
    @ApiOperation(value = "新增优惠券")
    @ApiImplicitParam(
            name = "discountCoupon",
            value = "storeId, name, type, money, discountMoney, startTime, endTime, useOffline, useOnline, quantity必填",
            required = true,
            paramType = "body",
            dataType = "DiscountCoupon"
    )
    @PostMapping(value = "/discountCoupon")
    public CommonResponse addDiscountCoupon(@RequestBody @Valid DiscountCoupon discountCoupon) {
        return marketingService.addDiscountCoupon(discountCoupon);
    }

    /**
     * 修改优惠券接口
     * @param discountCoupon
     * @return
     */
    @ApiOperation(value = "修改优惠券")
    @ApiImplicitParam(
            name = "discountCoupon",
            value = "storeId, id, name, type, money, discountMoney, startTime, endTime, useOffline, useOnline, quantity必填",
            required = true,
            paramType = "body",
            dataType = "DiscountCoupon"
    )
    @PutMapping(value = "/discountCoupon")
    public CommonResponse updateDiscountCoupon(@RequestBody @Valid DiscountCoupon discountCoupon) {
        return marketingService.updateDiscountCoupon(discountCoupon);
    }

    /**
     * 作废优惠券接口
     * @param discountCoupon
     * @return
     */
    @ApiOperation(value = "作废优惠券")
    @ApiImplicitParam(
            name = "discountCoupon",
            value = "storeId, id必填",
            required = true,
            paramType = "body",
            dataType = "DiscountCoupon"
    )
    @PutMapping(value = "/discountCoupon/invalid")
    public CommonResponse invalidDiscountCoupon(@RequestBody DiscountCoupon discountCoupon) {
        return marketingService.invalidDiscountCoupon(discountCoupon);
    }

    /**
     * 分页查询优惠券接口
     * @param storeId
     * @param name
     * @param type
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页查询优惠券", notes = "name模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "优惠券名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "优惠券类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页数，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每次显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/discountCoupon")
    public CommonResponse<CommonResponse<List<DiscountCouponVo>>> findPagedDiscountCoupon(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                          @RequestParam(value = "name", required = false) String name,
                                                                                          @RequestParam(value = "type", required = false) Byte type,
                                                                                          @RequestParam(value = "page", required = true) Integer page,
                                                                                          @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return marketingService.findPagedDiscountCoupon(new DiscountCoupon(storeId, name, type), new PageVo(page, pageSize));
    }

    /**
     * 根据编号查询优惠券接口
     * @param storeId
     * @param id
     * @return
     */
    @ApiOperation(value = "根据编号查询优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "优惠券编号", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping(value = "/discountCoupon/{id}")
    public CommonResponse<DiscountCoupon> findDiscountCouponById(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                 @PathVariable(value = "id", required = true) Integer id) {
        return marketingService.findDiscountCouponById(new DiscountCoupon(storeId, id));
    }

    @ApiOperation(value = "发优惠券接口")
    @ApiImplicitParam(
            name = "clientDiscountCoupon",
            value = "storeId, clientId, discountCouponId, quantity必填",
            required = true,
            paramType = "body",
            dataType = "ClientDiscountCoupon"
    )
    @PutMapping(value = "/discountCoupon/give")
    public CommonResponse giveDiscountCoupon(@RequestBody @Valid ClientDiscountCoupon clientDiscountCoupon) {
        return marketingService.giveDiscountCoupon(clientDiscountCoupon);
    }

    /**
     * 根据客户编号分页查询所有优惠券接口
     * @param storeId
     * @param clientId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据客户编号分页查询所有优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "clientId", value = "客户编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每次显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/discountCoupon/clientId")
    public CommonResponse<CommonResponse<List<ClientDiscountCouponVo>>> findPagedDiscountCouponByClientId(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                                          @RequestParam(value = "clientId", required = true) String clientId,
                                                                                                          @RequestParam(value = "page", required = true) Integer page,
                                                                                                          @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return marketingService.findPagedDiscountCouponByClientId(new ClientDiscountCoupon(storeId, clientId), new PageVo(page, pageSize));
    }

    /**
     * 根据店铺编号和客户编号查询可用优惠券接口
     * @param storeId
     * @param clientId
     * @return
     */
    @ApiOperation(value = "根据店铺编号和客户编号查询可用优惠券")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "clientId", value = "客户编号", required = true, paramType = "query", dataType = "String"),
    })
    @GetMapping(value = "/discountCoupon/canUse")
    public CommonResponse<DiscountCoupon> findCanUseDiscountCouponByStoreIdAndClientId(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                       @RequestParam(value = "clientId", required = true) String clientId) {
        return marketingService.findCanUseDiscountCouponByStoreIdAndClientId(new ClientDiscountCoupon(storeId, clientId));
    }

    //短信模版

    /**
     * 新增短信模版接口
     * @param smsTemplate
     * @param check
     * @return
     */
    @ApiOperation(value = "新增短信模版", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsTemplate", value = "id, title, content, type必填", required = true, paramType = "body", dataType = "SMSTemplate"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @PostMapping(value = "/sms/template/{check}")
    public CommonResponse addSMSTemplate(@RequestBody @Valid SMSTemplate smsTemplate,
                                         @PathVariable(value = "check") String check) {
        return marketingService.addSMSTemplate(smsTemplate, check);
    }

    /**
     * 删除短信模版接口
     * @param ids
     * @param check
     * @return
     */
    @ApiOperation(value = "删除短信模版", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "短信模版编号，多个英文逗号隔开", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @DeleteMapping(value = "/sms/template/{check}")
    public CommonResponse deleteSMSTemplate(@RequestParam String ids,
                                            @PathVariable(value = "check") String check) {
        List<SMSTemplate> smsTemplates = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> smsTemplates.add(new SMSTemplate(id)));
        return marketingService.deleteSMSTemplate(smsTemplates, check);
    }

    /**
     * 修改短信模版接口
     * @param smsTemplate
     * @param check
     * @return
     */
    @ApiOperation(value = "修改短信模版", notes = "特权账号使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsTemplate", value = "id, title, content, type必填", required = true, paramType = "body", dataType = "SMSTemplate"),
            @ApiImplicitParam(name = "check", value = "特权账号编号", required = true, paramType = "path", dataType = "String")
    })
    @PutMapping(value = "/sms/template/{check}")
    public CommonResponse updateSMSTemplate(@RequestBody @Valid SMSTemplate smsTemplate,
                                            @PathVariable(value = "check") String check) {
        return marketingService.updateSMSTemplate(smsTemplate, check);
    }

    /**
     * 查询短信模版接口
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询短信模版")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每次显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/sms/template")
    public CommonResponse<CommonResult<SMSTemplate>> findSMSTemplate(@RequestParam(value = "page", required = false) Integer page,
                                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return marketingService.findSMSTemplate(new SMSTemplate(), new PageVo(page, pageSize));
    }

    //短信历史

    /**
     * 发短信/新增短信历史接口
     * @param smsHistories
     * @return
     */
    @ApiOperation(value = "发短信/新增短信历史", notes = "以集合形式入参")
    @ApiImplicitParam(name = "smsHistories", value = "storeId, clientId, templateCode, userId必填", required = true, paramType = "body", dataType = "SMSHistory")
    @PostMapping(value = "/sms/history")
    public CommonResponse addSMSHistory(@RequestBody List<SMSHistory> smsHistories) throws ClientException {
        return marketingService.addSMSHistory(smsHistories);
    }

    /**
     * 查询短信历史接口
     * @param storeId
     * @param clientId
     * @param clientName
     * @param clientPhone
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询短信历史")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "clientId", value = "客户编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "clientName", value = "客户名称，模糊查询", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "clientPhone", value = "客户手机号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每次显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/sms/history")
    public CommonResponse<CommonResult<SMSHistory>> findSMSHistory(@RequestParam(value = "storeId", required = false) Integer storeId,
                                                                   @RequestParam(value = "clientId", required = false) String clientId,
                                                                   @RequestParam(value = "clientName", required = false) String clientName,
                                                                   @RequestParam(value = "clientPhone", required = false) String clientPhone,
                                                                   @RequestParam(value = "page") Integer page,
                                                                   @RequestParam(value = "pageSize") Integer pageSize) {
        return marketingService.findSMSHistory(new SMSHistory(storeId, clientId, clientName, clientPhone), new PageVo(page, pageSize));
    }
}
