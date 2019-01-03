package com.yeta.pps.controller;

import com.yeta.pps.po.ClientDiscountCoupon;
import com.yeta.pps.po.DiscountCoupon;
import com.yeta.pps.service.MarketingService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.ClientDiscountCouponVo;
import com.yeta.pps.vo.DiscountCouponVo;
import com.yeta.pps.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 营销相关接口
 * @author YETA
 * @date 2019/01/03/15:12
 */
@Api(value = "营销相关接口")
@RestController
public class MarketingController {

    @Autowired
    private MarketingService marketingService;

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
    @PostMapping(value = "/marketing/discountCoupon")
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
    @PutMapping(value = "/marketing/discountCoupon")
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
    @PutMapping(value = "/marketing/discountCoupon/invalid")
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
    @GetMapping(value = "/marketing/discountCoupon")
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
    @GetMapping(value = "/marketing/discountCoupon/{id}")
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
    @PutMapping(value = "/marketing/discountCoupon/give")
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
    @GetMapping(value = "/marketing/discountCoupon/clientId")
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
    @GetMapping(value = "/marketing/discountCoupon/canUse")
    public CommonResponse<DiscountCoupon> findCanUseDiscountCouponByStoreIdAndClientId(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                       @RequestParam(value = "clientId", required = true) String clientId) {
        return marketingService.findCanUseDiscountCouponByStoreIdAndClientId(new ClientDiscountCoupon(storeId, clientId));
    }
}
