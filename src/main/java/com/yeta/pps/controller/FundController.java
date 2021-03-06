package com.yeta.pps.controller;

import com.yeta.pps.po.Client;
import com.yeta.pps.po.StoreClient;
import com.yeta.pps.service.FundService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
     * 按单收款接口
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
    @ApiOperation(value = "按单收款", notes = "分页、筛选查询，其中clientName, phone, membershipNumber为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "clientName", value = "客户名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "membershipNumber", value = "会员卡号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "id", value = "销售申请订单编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "销售申请订单类型(2：销售订单，3：销售退货申请单)", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/in")
    public CommonResponse<CommonResult<List<SellApplyOrderVo>>> findNotClearedSellApplyOrder(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                             @RequestParam(value = "clientName", required = false) String clientName,
                                                                                             @RequestParam(value = "phone", required = false) String phone,
                                                                                             @RequestParam(value = "membershipNumber", required = false) String membershipNumber,
                                                                                             @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                             @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                             @RequestParam(value = "id", required = false) String id,
                                                                                             @RequestParam(value = "type", required = false) Byte type,
                                                                                             @RequestParam(value = "page", required = true) Integer page,
                                                                                             @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        Client client = new Client(clientName, phone, membershipNumber);
        return fundService.findNotClearedSellApplyOrder(new SellApplyOrderVo(storeId, startTime, endTime, id, type, client), new PageVo(page, pageSize));
    }

    /**
     * 按单付款接口
     * @param storeId
     * @param supplierName
     * @param startTime
     * @param endTime
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "按单付款", notes = "分页、筛选查询，其中supplierName为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "id", value = "采购申请订单编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "采购申请订单类型(1：采购订单，2：采购退货申请)", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/out")
    public CommonResponse<CommonResult<List<ProcurementApplyOrderVo>>> findNotClearedProcurementApplyOrder(@RequestParam(value = "storeId", required = true) Integer storeId,
                                                                                                           @RequestParam(value = "supplierName", required = false) String supplierName,
                                                                                                           @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                                           @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                                           @RequestParam(value = "id", required = false) String id,
                                                                                                           @RequestParam(value = "type", required = false) Byte type,
                                                                                                           @RequestParam(value = "page", required = true) Integer page,
                                                                                                           @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        return fundService.findNotClearedProcurementApplyOrder(new ProcurementApplyOrderVo(storeId, supplierName, startTime, endTime, id, type), new PageVo(page, pageSize));
    }

    /**
     * 查询预收/付款余额接口
     * @param storeId
     * @param targetId
     * @param type
     * @return
     */
    @ApiOperation(value = "查询预收/付款余额", notes = "在点击收/付款之后，做的第一件事")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "targetId", value = "往来单位编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "单据类型(1：查预收款余额，2：查预付款余额)", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/advance")
    public CommonResponse findLastFundTargetCheckOrder(@RequestParam(value = "storeId") Integer storeId,
                                                       @RequestParam(value = "targetId") String targetId,
                                                       @RequestParam(value = "type") Byte type) {
        return fundService.findLastFundTargetCheckOrder(new FundTargetCheckOrderVo(storeId, targetId), type);
    }

    /**
     * 新增收/付款单、预收/付款单接口
     * @param fundOrderVo
     * @return
     */
    @ApiOperation(value = "新增收/付款单、预收/付款单", notes = "采购申请订单都是付款，销售申请订单都是收款")
    @ApiImplicitParam(name = "fundOrderVo",
            value = "参数详情看资金流程.txt",
            required = true,
            paramType = "body",
            dataType = "FundOrderVo")
    @PostMapping(value = "/fund")
    public CommonResponse addFundOrder(@RequestBody @Valid FundOrderVo fundOrderVo) {
        return fundService.addFundOrder(fundOrderVo);
    }

    /**
     * 红冲收/付款单、预收/付款单接口
     * @param storeId
     * @param id
     * @param userId
     * @param remark
     * @return
     */
    @ApiOperation(value = "红冲收/付款单、预收/付款单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "单据编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "String")
    })
    @PostMapping(value = "/fund/redDashed")
    public CommonResponse redDashed(@RequestParam(value = "storeId") Integer storeId,
                                    @RequestParam(value = "id") String id,
                                    @RequestParam(value = "userId") String userId,
                                    @RequestParam(value = "remark", required = false) String remark) {
        return fundService.redDashed(new FundOrderVo(storeId, id, userId, remark));
    }

    /**
     * 根据type查询收/付款单、预收/付款单接口
     * @param storeId
     * @param id
     * @param type
     * @param startTime
     * @param endTime
     * @param targetName
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据type查询收/付款单、预收/付款单", notes = "分页、筛选查询，其中type必填，targetName为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "单据类型(1：收款单，2：付款单，3：预收款单，4：预付款单)", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "收/付款单编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "targetName", value = "往来单位", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/{type}")
    public CommonResponse<CommonResult<List<FundOrderVo>>> findAllFundOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                            @RequestParam(value = "id", required = false) String id,
                                                                            @PathVariable(value = "type") Byte type,
                                                                            @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                            @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                            @RequestParam(value = "targetName", required = false) String targetName,
                                                                            @RequestParam(value = "page") Integer page,
                                                                            @RequestParam(value = "pageSize") Integer pageSize) {
        return fundService.findAllFundOrder(new FundOrderVo(storeId, id, type, startTime, endTime, targetName), new PageVo(page, pageSize));
    }

    /**
     * 根据type导出收/付款单、预收/付款单接口
     * @param storeId
     * @param id
     * @param type
     * @param startTime
     * @param endTime
     * @param targetName
     * @return
     */
    @ApiOperation(value = "根据type导出收/付款单、预收/付款单", notes = "type必填，targetName为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "单据类型(1：收款单，2：付款单，3：预收款单，4：预付款单)", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "收/付款单编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "targetName", value = "往来单位", required = false, paramType = "query", dataType = "String")
    })
    @GetMapping(value = "/fund/export/{type}")
    public void exportFundOrder(@RequestParam(value = "storeId") Integer storeId,
                                @RequestParam(value = "id", required = false) String id,
                                @PathVariable(value = "type") Byte type,
                                @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                @RequestParam(value = "targetName", required = false) String targetName,
                                HttpServletResponse response) {
        fundService.exportFundOrder(new FundOrderVo(storeId, id, type, startTime, endTime, targetName), response);
    }

    //期初设置

    /**
     * 现金银行期初设置查询接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "现金银行期初设置查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页数，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每次显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/bankAccount/opening")
    public CommonResponse<CommonResult<List<BankAccountVo>>> findBankAccountOpening(@RequestParam(value = "storeId") Integer storeId,
                                                                                    @RequestParam(value = "page") Integer page,
                                                                                    @RequestParam(value = "pageSize") Integer pageSize) {
        return fundService.findBankAccountOpening(new BankAccountVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 现金银行期初设置接口
     * @param bankAccountVo
     * @return
     */
    @ApiOperation(value = "现金银行期初设置")
    @ApiImplicitParam(name = "bankAccountVo", value = "storeId, id, openingMoney, userId必填", required = true, paramType = "body", dataType = "BankAccountVo")
    @PutMapping(value = "/fund/bankAccount/opening")
    public CommonResponse updateBankAccountOpening(@RequestBody @Valid BankAccountVo bankAccountVo) {
        return fundService.updateBankAccountOpening(bankAccountVo);
    }

    /**
     * 应收期初设置查询接口
     * @param storeId
     * @param clientName
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "应收期初设置查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "clientName", value = "客户名称", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每次显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/needIn/opening")
    public CommonResponse<CommonResult<List<StoreClientVo>>> findNeedInOpening(@RequestParam(value = "storeId") Integer storeId,
                                                                               @RequestParam(value = "clientName", required = false) String clientName,
                                                                               @RequestParam(value = "page") Integer page,
                                                                               @RequestParam(value = "pageSize") Integer pageSize) {
        StoreClientVo storeClientVo = new StoreClientVo();
        storeClientVo.setStoreId(storeId);
        storeClientVo.setClientName(clientName);
        return fundService.findNeedInOpening(storeClientVo, new PageVo(page, pageSize));
    }

    /**
     * 应收期初设置接口
     * @param storeClient
     * @return
     */
    @ApiOperation(value = "应收期初设置")
    @ApiImplicitParam(name = "storeClient", value = "storeId, clientId, advanceInMoneyOpening, needInMoneyOpening, userId必填", required = true, paramType = "body", dataType = "StoreClient")
    @PutMapping(value = "/fund/needIn/opening")
    public CommonResponse updateNeedInOpening(@RequestBody StoreClient storeClient) {
        return fundService.updateNeedInOpening(storeClient);
    }

    /**
     * 应付期初设置查询接口
     * @param storeId
     * @param supplierName
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "应付期初设置查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名称", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每次显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/needOut/opening")
    public CommonResponse<CommonResult<List<SupplierVo>>> findNeedOutOpening(@RequestParam(value = "storeId") Integer storeId,
                                                                             @RequestParam(value = "supplierName", required = false) String supplierName,
                                                                             @RequestParam(value = "page") Integer page,
                                                                             @RequestParam(value = "pageSize") Integer pageSize) {
        return fundService.findNeedOutOpening(new SupplierVo(storeId, null, supplierName), new PageVo(page, pageSize));
    }

    /**
     * 应付期初设置接口
     * @param supplierVo
     * @return
     */
    @ApiOperation(value = "应付期初设置")
    @ApiImplicitParam(name = "supplierVo", value = "storeId, id, advanceOutMoneyOpening, needOutMoneyOpening, userId必填", required = true, paramType = "body", dataType = "SupplierVo")
    @PutMapping(value = "/fund/needOut/opening")
    public CommonResponse updateNeedOutOpening(@RequestBody SupplierVo supplierVo) {
        return fundService.updateNeedOutOpening(supplierVo);
    }

    //资金对账

    /**
     * 查资金-资金余额接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查资金-资金余额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/balance")
    public CommonResponse<CommonResult<List<BankAccountVo>>> findSumFundCheckOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                   @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                   @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                   @RequestParam(value = "page") Integer page,
                                                                                   @RequestParam(value = "pageSize") Integer pageSize) {
        return fundService.findFundCheckOrderBalanceMoney(new FundCheckOrderVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    /**
     * 查资金-资金流水/资金对账接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param bankAccountId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查资金-资金流水/资金对账", notes = "查资金-资金流水不传bankAccountId，查资金对账必传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "bankAccountId", value = "银行账户编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/check")
    public CommonResponse<CommonResult<List<FundCheckOrderVo>>> findFundCheckOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                   @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                   @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                   @RequestParam(value = "bankAccountId", required = false) String bankAccountId,
                                                                                   @RequestParam(value = "page") Integer page,
                                                                                   @RequestParam(value = "pageSize") Integer pageSize) {
        return fundService.findFundCheckOrder(new FundCheckOrderVo(storeId, startTime, endTime, bankAccountId), new PageVo(page, pageSize));
    }

    //往来对账

    /**
     * 往来对账-查应收-按往来单位接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param targetId
     * @param targetName
     * @param targetPhone
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "往来对账-查应收-按往来单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "targetId", value = "往来单位编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "targetName", value = "往来单位名称", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "targetPhone", value = "往来单位电话", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/needIn/byTarget")
    public CommonResponse<CommonResult<List<FundTargetCheckOrderVo>>> findFundTargetCheckOrderNeedInByClient(@RequestParam(value = "storeId") Integer storeId,
                                                                                                             @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                                             @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                                             @RequestParam(value = "targetId", required = false) String targetId,
                                                                                                             @RequestParam(value = "targetName", required = false) String targetName,
                                                                                                             @RequestParam(value = "targetPhone", required = false) String targetPhone,
                                                                                                             @RequestParam(value = "page") Integer page,
                                                                                                             @RequestParam(value = "pageSize") Integer pageSize) {
        return fundService.findFundTargetCheckOrderNeedInByClient(new FundTargetCheckOrderVo(storeId, startTime, endTime, targetId, targetName, targetPhone), new PageVo(page, pageSize));
    }

    /**
     * 往来对账-查应收-按往来单位-对账到单据接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param targetId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "往来对账-查应收-按往来单位-对账到单据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "targetId", value = "往来单位编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/needIn/byTargetOrder")
    public CommonResponse<CommonResult<List<FundTargetCheckOrderVo>>> findFundTargetCheckOrderNeedInByClientOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                                                  @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                                                  @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                                                  @RequestParam(value = "targetId") String targetId,
                                                                                                                  @RequestParam(value = "page") Integer page,
                                                                                                                  @RequestParam(value = "pageSize") Integer pageSize) {
        return fundService.findFundTargetCheckOrderNeedInByClientOrder(new FundTargetCheckOrderVo(storeId, startTime, endTime, targetId), new PageVo(page, pageSize));
    }

    /**
     * 往来对账-查应付-按往来单位接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param targetId
     * @param targetName
     * @param targetPhone
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "往来对账-查应付-按往来单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "targetId", value = "往来单位编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "targetName", value = "往来单位名称", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "targetPhone", value = "往来单位电话", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/needOut/byTarget")
    public CommonResponse<CommonResult<List<FundTargetCheckOrderVo>>> findFundTargetCheckOrderNeedOutByClient(@RequestParam(value = "storeId") Integer storeId,
                                                                                                              @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                                              @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                                              @RequestParam(value = "targetId", required = false) String targetId,
                                                                                                              @RequestParam(value = "targetName", required = false) String targetName,
                                                                                                              @RequestParam(value = "targetPhone", required = false) String targetPhone,
                                                                                                              @RequestParam(value = "page") Integer page,
                                                                                                              @RequestParam(value = "pageSize") Integer pageSize) {
        return fundService.findFundTargetCheckOrderNeedOutByClient(new FundTargetCheckOrderVo(storeId, startTime, endTime, targetId, targetName, targetPhone), new PageVo(page, pageSize));
    }

    /**
     * 往来对账-查应付-按往来单位-对账到单据接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param targetId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "往来对账-查应付-按往来单位-对账到单据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "targetId", value = "往来单位编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/needOut/byTargetOrder")
    public CommonResponse<CommonResult<List<FundTargetCheckOrderVo>>> findFundTargetCheckOrderNeedOutByClientOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                                                   @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                                                   @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                                                   @RequestParam(value = "targetId") String targetId,
                                                                                                                   @RequestParam(value = "page") Integer page,
                                                                                                                   @RequestParam(value = "pageSize") Integer pageSize) {
        return fundService.findFundTargetCheckOrderNeedOutByClientOrder(new FundTargetCheckOrderVo(storeId, startTime, endTime, targetId), new PageVo(page, pageSize));
    }

    /**
     * 往来对账-职员部门应收款接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "往来对账-职员部门应收款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/needIn/byUser")
    public CommonResponse<CommonResult<List<FundTargetCheckOrderVo>>> findFundTargetCheckOrderNeedInByUser(@RequestParam(value = "storeId") Integer storeId,
                                                                                                           @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                                           @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                                           @RequestParam(value = "page") Integer page,
                                                                                                             @RequestParam(value = "pageSize") Integer pageSize) {
        return fundService.findFundTargetCheckOrderNeedInByUser(new FundTargetCheckOrderVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    /**
     * 往来对账-职员部门应付款接口
     * @param storeId
     * @param startTime
     * @param endTime
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "往来对账-职员部门应付款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/needOut/byUser")
    public CommonResponse<CommonResult<List<FundTargetCheckOrderVo>>> findFundTargetCheckOrderNeedOutByUser(@RequestParam(value = "storeId") Integer storeId,
                                                                                                            @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                                            @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                                            @RequestParam(value = "page") Integer page,
                                                                                                            @RequestParam(value = "pageSize") Integer pageSize) {
        return fundService.findFundTargetCheckOrderNeedOutByUser(new FundTargetCheckOrderVo(storeId, startTime, endTime), new PageVo(page, pageSize));
    }

    //其他收入单/费用单

    /**
     * 新增其他收入单/费用单接口
     * @param fundResultOrderVo
     * @return
     */
    @ApiOperation(value = "新增其他收入单/费用单")
    @ApiImplicitParam(name = "fundResultOrderVo", value = "参数详情看资金流程.txt", required = true, paramType = "body", dataType = "FundResultOrderVo")
    @PostMapping(value = "/fund/result")
    public CommonResponse addFundOrder(@RequestBody @Valid FundResultOrderVo fundResultOrderVo) {
        return fundService.addFundResultOrder(fundResultOrderVo);
    }

    /**
     * 红冲其他收入单/费用单接口
     * @param storeId
     * @param id
     * @param userId
     * @param remark
     * @return
     */
    @ApiOperation(value = "其他收入单/费用单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "单据编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "String")
    })
    @PostMapping(value = "/fund/result/redDashed")
    public CommonResponse redDashedResultOrder(@RequestParam(value = "storeId") Integer storeId,
                                               @RequestParam(value = "id") String id,
                                               @RequestParam(value = "userId") String userId,
                                               @RequestParam(value = "remark", required = false) String remark) {
        return fundService.redDashedResultOrder(new FundResultOrderVo(storeId, id, userId, remark));
    }

    /**
     * 根据type查询其他收入单/费用单接口
     * @param storeId
     * @param id
     * @param type
     * @param startTime
     * @param endTime
     * @param targetName
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据type查询其他收入单/费用单", notes = "分页、筛选查询，其中type必填，targetName为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "单据类型(1：其他收入单，2：费用单)", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "单据编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "targetName", value = "往来单位", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "bankAccountId", value = "银行账户编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "incomeExpensesId", value = "收支费用编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/fund/result/{type}")
    public CommonResponse<CommonResult<List<FundOrderVo>>> findAllFundResultOrder(@RequestParam(value = "storeId") Integer storeId,
                                                                                  @RequestParam(value = "id", required = false) String id,
                                                                                  @PathVariable(value = "type") Byte type,
                                                                                  @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                  @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                  @RequestParam(value = "targetName", required = false) String targetName,
                                                                                  @RequestParam(value = "bankAccountId", required = false) String bankAccountId,
                                                                                  @RequestParam(value = "incomeExpensesId", required = false) String incomeExpensesId,
                                                                                  @RequestParam(value = "page") Integer page,
                                                                                  @RequestParam(value = "pageSize") Integer pageSize) {
        return fundService.findAllFundResultOrder(new FundResultOrderVo(storeId, id, type, startTime, endTime, targetName, bankAccountId, incomeExpensesId), new PageVo(page, pageSize));
    }

    /**
     * 根据type查询其他收入单/费用单接口
     * @param storeId
     * @param id
     * @param type
     * @param startTime
     * @param endTime
     * @param targetName
     * @return
     */
    @ApiOperation(value = "根据type查询其他收入单/费用单", notes = "type必填，targetName为模糊查询，startTime和endTime要么都传，要么都不传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "单据类型(1：其他收入单，2：费用单)", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "单据编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "targetName", value = "往来单位", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "bankAccountId", value = "银行账户编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "incomeExpensesId", value = "收支费用编号", required = false, paramType = "query", dataType = "String")
    })
    @GetMapping(value = "/fund/result/export/{type}")
    public void exportFundResultOrder(@RequestParam(value = "storeId") Integer storeId,
                                      @RequestParam(value = "id", required = false) String id,
                                      @PathVariable(value = "type") Byte type,
                                      @RequestParam(value = "startTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                      @RequestParam(value = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                      @RequestParam(value = "targetName", required = false) String targetName,
                                      @RequestParam(value = "bankAccountId", required = false) String bankAccountId,
                                      @RequestParam(value = "incomeExpensesId", required = false) String incomeExpensesId,
                                      HttpServletResponse response) {
        fundService.exportFundResultOrder(new FundResultOrderVo(storeId, id, type, startTime, endTime, targetName, bankAccountId, incomeExpensesId), response);
    }
}
