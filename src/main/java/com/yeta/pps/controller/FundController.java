package com.yeta.pps.controller;

import com.yeta.pps.service.FundService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.FundOrderVo;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.ProcurementApplyOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 资金相关接口
 * @author YETA
 * @date 2018/12/13/14:55
 */
@Api(value = "资金相关接口")
@RestController
public class FundController {

    @Autowired
    private FundService fundService;

    /**
     * 新增收/付款单接口
     * @param fundOrderVo
     * @return
     */
    @ApiOperation(value = "新增收/付款单", notes = "采购申请订单都是付款，销售申请订单都是收款")
    @ApiImplicitParam(name = "fundOrderVo",
            value = "storeId, type(1：收款单，2：付款单), applyOrderId(来源订单), bankAccountId(银行账户编号), money(金额，注意大于0还是小于0), userId(经手人编号)必填，remark选填",
            required = true,
            paramType = "body",
            dataType = "FundOrderVo")
    @PostMapping(value = "/orders/fund")
    private CommonResponse addFundOrder(@RequestBody @Valid FundOrderVo fundOrderVo) {
        return fundService.addFundOrder(fundOrderVo);
    }

    /**
     * 根据type查询所有收/付款单接口
     * @param storeId
     * @param supplierName
     * @param startTime
     * @param endTime
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据type查询所有收/付款货单", notes = "分页、筛选查询，其中type必填，supplierName为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "单据类型(1：收款单，2：付款单)", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "收/付款单编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/orders/fund/{type}")
    public CommonResponse<CommonResult<List<FundOrderVo>>> findAllFundOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                            @PathVariable(value = "type") Byte type,
                                                                            @RequestParam(value = "supplierName", required = false) String supplierName,
                                                                            @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                            @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                            @RequestParam(value = "id", required = false) String id,
                                                                            @RequestParam(value = "page") Integer page,
                                                                            @RequestParam(value = "pageSize") Integer pageSize) {
        ProcurementApplyOrderVo procurementApplyOrderVo = new ProcurementApplyOrderVo(supplierName, startTime, endTime);
        return fundService.findAllFundOrder(new FundOrderVo(storeId, id, type, procurementApplyOrderVo), new PageVo(page, pageSize));
    }
}
