package com.yeta.pps.controller;

import com.yeta.pps.po.Client;
import com.yeta.pps.service.SellService;
import com.yeta.pps.service.SellService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.SellApplyOrderVo;
import com.yeta.pps.vo.SellResultOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 销售相关接口
 * @author YETA
 * @date 2018/12/10/19:26
 */
@Api(value = "销售相关接口")
@RestController
@RequestMapping(value = "/sell")
public class SellController {

    @Autowired
    private SellService sellService;

    /**
     * 新增销售申请订单接口
     * @param sellApplyOrderVo
     * @return
     */
    @ApiOperation(value = "新增销售申请订单", notes = "包括零售单、销售订单、销售退货申请单、销售换货申请单，用type判断。" +
            "details中的OrderGoodsSkuVo对象表示订单关联的商品规格，其中id(销售退货申请和销售换货申请的入库商品规格需要填上此字段), type(1：入库，0：出库), goodsSkuId, quantity, money, discountMoney必填, remark选填")
    @ApiImplicitParam(name = "sellApplyOrderVo",
            value = "storeId, details, type(1：零售单，2：销售订单，3：销售退货申请单，4：销售换货申请单), clientId, 零售单、销售订单填outWarehouseId, outTotalQuantity, 销售退货申请单填inWarehouseId, inTotalQuantity, resultOrderId(来源订单，应该是某一个销售结果订单), 销售换货申请前面五个都要填, totalMoney(销售订单大于0，销售退货申请小于0，销售换货申请-入库的价钱), totalDiscountMoney, discountMoney, orderMoney, userId必填, discount_coupon_id, remark选填",
            required = true,
            paramType = "body",
            dataType = "SellApplyOrderVo"
    )
    @PostMapping(value = "/apply")
    public CommonResponse addApplyOrder(@RequestBody @Valid SellApplyOrderVo sellApplyOrderVo) {
        return sellService.addApplyOrder(sellApplyOrderVo);
    }

    /**
     * 删除销售申请订单接口
     * @param storeId
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除销售申请订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "销售申请订单编号，英文逗号隔开", required = true, paramType = "query", dataType = "String"),
    })
    @DeleteMapping(value = "/apply")
    public CommonResponse deleteApplyOrder(@RequestParam(value = "storeId") Integer storeId,
                                           @RequestParam(value = "ids") String ids) {
        List<SellApplyOrderVo> sellApplyOrderVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            sellApplyOrderVos.add(new SellApplyOrderVo(storeId, id));
        });
        return sellService.deleteApplyOrder(sellApplyOrderVos);
    }

    /**
     * 修改销售申请订单接口
     * @param sellApplyOrderVo
     * @return
     */
    @ApiOperation(value = "修改销售申请订单", notes = "包括销售订单、销售退货申请、销售换货申请，用type判断。" +
            "details中的OrderGoodsSkuVo对象表示订单关联的商品规格，其中type(1：入库，0：出库), goodsSkuId, quantity, money, discountMoney必填, remark选填")
    @ApiImplicitParam(name = "sellApplyOrderVo",
            value = "storeId, id, details, supplierId, 销售订单填outWarehouseId, outTotalQuantity, 销售退货申请填inWarehouseId, inTotalQuantity, 销售换货申请前面四个都要填, totalMoney(销售订单大于0，销售退货申请小于0，销售换货申请出库-入库的价钱), totalDiscountMoney, disCountMoney, orderMoney, userId必填, discount_coupon_id, remark选填",
            required = true,
            paramType = "body",
            dataType = "SellApplyOrderVo"
    )
    @PutMapping(value = "/apply")
    public CommonResponse updateApplyOrder(@RequestBody SellApplyOrderVo sellApplyOrderVo) {
        return sellService.updateApplyOrder(sellApplyOrderVo);
    }

    /**
     * 修改销售申请订单备注接口
     * @param storeId
     * @param id
     * @param userId
     * @param remark
     * @return
     */
    @ApiOperation(value = "修改销售申请订单备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "销售申请订单单据编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = true, paramType = "query", dataType = "String")
    })
    @PutMapping(value = "/apply/remark")
    public CommonResponse updateApplyOrderRemark(@RequestParam(value = "storeId") Integer storeId,
                                                 @RequestParam(value = "id") String id,
                                                 @RequestParam(value = "userId") String userId,
                                                 @RequestParam(value = "remark") String remark) {
        return sellService.updateApplyOrderRemark(new SellApplyOrderVo(storeId, id, userId, remark));
    }

    /**
     * 根据type查询销售申请订单接口
     * @param storeId
     * @param clientName
     * @param phone
     * @param membershipNumber
     * @param startTime
     * @param endTime
     * @param id
     * @param type
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据type查询销售申请订单", notes = "分页、筛选查询，其中clientName, phone, membershipNumber为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "clientName", value = "客户名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "membershipNumber", value = "会员卡号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "id", value = "单据编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "单据类型(1：零售单，2：销售订单，3：销售退货申请单，4：销售换货申请单)", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/apply")
    public CommonResponse<CommonResult<List<SellApplyOrderVo>>> findAllApplyOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                  @RequestParam(value = "clientName", required = false) String clientName,
                                                                                  @RequestParam(value = "phone", required = false) String phone,
                                                                                  @RequestParam(value = "membershipNumber", required = false) String membershipNumber,
                                                                                  @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                  @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                  @RequestParam(value = "id", required = false) String id,
                                                                                  @RequestParam(value = "type") Byte type,
                                                                                  @RequestParam(value = "page") Integer page,
                                                                                  @RequestParam(value = "pageSize") Integer pageSize) {
        Client client = new Client(clientName, phone, membershipNumber);
        return sellService.findAllApplyOrder(new SellApplyOrderVo(storeId, startTime, endTime, id, type, client), new PageVo(page, pageSize));
    }

    /**
     * 根据type查询销售申请订单接口
     * @param storeId
     * @param clientName
     * @param phone
     * @param membershipNumber
     * @param startTime
     * @param endTime
     * @param id
     * @param type
     * @return
     */
    @ApiOperation(value = "根据type查询销售申请订单", notes = "其中clientName, phone, membershipNumber为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "clientName", value = "客户名称", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "客户电话", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "membershipNumber", value = "会员卡号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "id", value = "单据编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "单据类型(1：零售单，2：销售订单，3：销售退货申请单，4：销售换货申请单)", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/apply/export")
    public void exportApplyOrder(@RequestParam(value = "storeId") Integer storeId,
                                 @RequestParam(value = "clientName", required = false) String clientName,
                                 @RequestParam(value = "phone", required = false) String phone,
                                 @RequestParam(value = "membershipNumber", required = false) String membershipNumber,
                                 @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                 @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                 @RequestParam(value = "id", required = false) String id,
                                 @RequestParam(value = "type") Byte type,
                                 HttpServletResponse response) {
        Client client = new Client(clientName, phone, membershipNumber);
        sellService.exportApplyOrder(new SellApplyOrderVo(storeId, startTime, endTime, id, type, client), response);
    }

    /**
     * 根据单据编号查询申请订单详情
     * @param storeId
     * @param id
     * @return
     */
    @ApiOperation(value = "根据单据编号查询申请订单详情", notes = "主要是查关联的商品和商品规格信息，用于点击销售申请订单时")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "销售申请订单编号", required = true, paramType = "path", dataType = "String")
    })
    @GetMapping(value = "/apply/detail/{id}")
    public CommonResponse<SellApplyOrderVo> findApplyOrderDetailById(@RequestParam(value = "storeId") Integer storeId,
                                                                     @PathVariable(value = "id") String id) {
        return sellService.findApplyOrderDetailById(new SellApplyOrderVo(storeId, id));
    }

    //销售结果订单

    /**
     * 红冲销售结果订单接口
     * @param storeId
     * @param id
     * @param userId
     * @param remark
     * @return
     */
    @ApiOperation(value = "红冲销售结果订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "单据编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "String")
    })
    @PostMapping(value = "/result/redDashed")
    public CommonResponse redDashed(@RequestParam(value = "storeId") Integer storeId,
                                    @RequestParam(value = "id") String id,
                                    @RequestParam(value = "userId") String userId,
                                    @RequestParam(value = "remark", required = false) String remark) {
        return sellService.redDashed(new SellResultOrderVo(storeId, id, userId, remark));
    }

    /**
     * 查询销售结果订单接口
     * @param storeId
     * @param clientName
     * @param phone
     * @param membershipNumber
     * @param startTime
     * @param endTime
     * @param id
     * @param type
     * @param flag
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询销售结果订单", notes = "分页、筛选查询，其中clientName, phone, membershipNumber为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "clientName", value = "客户名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "membershipNumber", value = "会员卡号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "id", value = "销售结果订单编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "flag", value = "标志位，1表示申请退换货的时候查询采购入库单", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/result")
    public CommonResponse<CommonResult<List<SellResultOrderVo>>> findAllResultOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                    @RequestParam(value = "clientName", required = false) String clientName,
                                                                                    @RequestParam(value = "phone", required = false) String phone,
                                                                                    @RequestParam(value = "membershipNumber", required = false) String membershipNumber,
                                                                                    @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                    @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                    @RequestParam(value = "id", required = false) String id,
                                                                                    @RequestParam(value = "type", required = false) Byte type,
                                                                                    @RequestParam(value = "flag", required = false) Integer flag,
                                                                                    @RequestParam(value = "page") Integer page,
                                                                                    @RequestParam(value = "pageSize") Integer pageSize) {
        Client client = new Client(clientName, phone, membershipNumber);
        SellApplyOrderVo sellApplyOrderVo = new SellApplyOrderVo(startTime, endTime, client);
        return sellService.findAllResultOrder(new SellResultOrderVo(storeId, sellApplyOrderVo, id, type, flag), new PageVo(page, pageSize));
    }

    /**
     * 导出销售结果订单接口
     * @param storeId
     * @param clientName
     * @param phone
     * @param membershipNumber
     * @param startTime
     * @param endTime
     * @param id
     * @param type
     * @return
     */
    @ApiOperation(value = "导出销售结果订单", notes = "其中clientName为模糊查询, phone, membershipNumber，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "clientName", value = "客户名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "membershipNumber", value = "会员卡号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "id", value = "销售结果订单编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/result/export")
    public void exportResultOrder(@RequestParam(value = "storeId") Integer storeId,
                                  @RequestParam(value = "clientName", required = false) String clientName,
                                  @RequestParam(value = "phone", required = false) String phone,
                                  @RequestParam(value = "membershipNumber", required = false) String membershipNumber,
                                  @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                  @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                  @RequestParam(value = "id", required = false) String id,
                                  @RequestParam(value = "type", required = false) Byte type,
                                  HttpServletResponse response) {
        Client client = new Client(clientName, phone, membershipNumber);
        SellApplyOrderVo sellApplyOrderVo = new SellApplyOrderVo(startTime, endTime, client);
        sellService.exportResultOrder(new SellResultOrderVo(storeId, sellApplyOrderVo, id, type), response);
    }

    /**
     * 根据单据编号查询结果订单详情
     * @param storeId
     * @param id
     * @return
     */
    @ApiOperation(value = "根据单据编号查询结果订单详情", notes = "主要是查关联的商品和商品规格信息，用于点击销售结果订单时")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "销售结果订单编号", required = true, paramType = "path", dataType = "String")
    })
    @GetMapping(value = "/result/detail/{id}")
    public CommonResponse<SellResultOrderVo> findResultOrderDetailById(@RequestParam(value = "storeId") Integer storeId,
                                                                       @PathVariable(value = "id") String id) {
        return sellService.findResultOrderDetailById(new SellResultOrderVo(storeId, id));
    }

}
