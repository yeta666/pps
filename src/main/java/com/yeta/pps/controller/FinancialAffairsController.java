package com.yeta.pps.controller;

import com.yeta.pps.po.AccountingSubject;
import com.yeta.pps.service.FinancialAffairsService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.AccountingDocumentVo;
import com.yeta.pps.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 财务相关接口
 * @author YETA
 * @date 2019/01/21/13:14
 */
@Api(value = "财务相关接口")
@RestController
@RequestMapping(value = "/financialAffairs")
public class FinancialAffairsController {

    @Autowired
    private FinancialAffairsService financialAffairsService;

    /**
     * 新增会计科目接口
     * @param accountingSubject
     * @return
     */
    @ApiOperation(value = "新增会计科目")
    @ApiImplicitParam(name = "accountingSubject", value = "storeId, id, name必填", required = true, paramType = "body", dataType = "AccountingSubject")
    @PostMapping(value = "/accountingSubjects")
    public CommonResponse addAccountingSubject(@RequestBody @Valid AccountingSubject accountingSubject) {
        return financialAffairsService.addAccountingSubject(accountingSubject);
    }

    /**
     * 删除会计科目接口
     * @param storeId
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除会计科目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "会计科目编号，英文逗号隔开", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/accountingSubjects")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @RequestParam(value = "ids") String ids) {
        List<AccountingSubject> accountingSubjects = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            accountingSubjects.add(new AccountingSubject(storeId, id));
        });
        return financialAffairsService.deleteAccountingSubject(accountingSubjects);
    }

    /**
     * 修改会计科目接口
     * @param accountingSubject
     * @return
     */
    @ApiOperation(value = "修改会计科目")
    @ApiImplicitParam(name = "accountingSubject", value = "storeId, id, name必填", required = true, paramType = "body", dataType = "AccountingSubject")
    @PutMapping(value = "/accountingSubjects")
    public CommonResponse updateAccountingSubject(@RequestBody @Valid AccountingSubject accountingSubject) {
        return financialAffairsService.updateAccountingSubject(accountingSubject);
    }

    /**
     * 查询会计科目接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询会计科目", notes = "可选是否分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页数，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每次显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/accountingSubjects")
    public CommonResponse<CommonResult<AccountingSubject>> findAllAccountingSubject(@RequestParam(value = "storeId") Integer storeId,
                                                                                    @RequestParam(value = "page", required = false) Integer page,
                                                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return financialAffairsService.findAllAccountingSubject(new AccountingSubject(storeId), new PageVo(page, pageSize));
    }

    //财务期初

    /**
     * 查询固定资产期初接口
     * @param storeId
     * @return
     */
    @ApiOperation(value = "查询固定资产期初")
    @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int")
    @GetMapping(value = "/opening/fixedAssets")
    public CommonResponse<CommonResult<AccountingSubject>> findFixedAssetsOpening(@RequestParam(value = "storeId") Integer storeId) {
        return financialAffairsService.findFixedAssetsOpening(new AccountingSubject(storeId));
    }

    /**
     * 设置资产负债期初接口
     * @param accountingSubject
     * @return
     */
    @ApiOperation(value = "设置资产负债期初")
    @ApiImplicitParam(name = "accountingSubject", value = "storeId, fixedAssetsOpening必填", required = true, paramType = "body", dataType = "AccountingSubject")
    @PutMapping(value = "/opening/fixedAssets")
    public CommonResponse updateFixedAssetsOpening(@RequestBody AccountingSubject accountingSubject) {
        return financialAffairsService.updateFixedAssetsOpening(accountingSubject);
    }

    /**
     * 查询资产负债期初接口
     * @param storeId
     * @return
     */
    @ApiOperation(value = "查询资产负债期初")
    @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int")
    @GetMapping(value = "/opening/assetsLiabilities")
    public CommonResponse<CommonResult<AccountingSubject>> findAssetsLiabilitiesOpening(@RequestParam(value = "storeId") Integer storeId) {
        return financialAffairsService.findAssetsLiabilitiesOpening(new AccountingSubject(storeId));
    }

    /**
     * 设置资产负债期初接口
     * @param accountingSubject
     * @return
     */
    @ApiOperation(value = "设置资产负债期初")
    @ApiImplicitParam(name = "accountingSubject", value = "storeId, assetsLiabilitiesOpening必填", required = true, paramType = "body", dataType = "AccountingSubject")
    @PutMapping(value = "/opening/assetsLiabilities")
    public CommonResponse updateAssetsLiabilitiesOpening(@RequestBody AccountingSubject accountingSubject) {
        return financialAffairsService.updateAssetsLiabilitiesOpening(accountingSubject);
    }

    //会计凭证

    /**
     * 查询会计凭证接口
     * @param storeId
     * @param id
     * @param startTime
     * @param endTime
     * @param subjectId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询会计凭证", notes = "默认分页，必传startTime, endTime")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺编号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "单据编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "subjectId", value = "科目编号", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "当前页数，从1开始", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每次显示条数", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/accountingDocuments")
    public CommonResponse<CommonResult<AccountingDocumentVo>> findAllAccountingDocument(@RequestParam(value = "storeId") Integer storeId,
                                                                                        @RequestParam(value = "id", required = false) String id,
                                                                                        @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
                                                                                        @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
                                                                                        @RequestParam(value = "subjectId", required = false) String subjectId,
                                                                                        @RequestParam(value = "page") Integer page,
                                                                                        @RequestParam(value = "pageSize") Integer pageSize) {
        return financialAffairsService.findAllAccountingDocument(new AccountingDocumentVo(storeId, id, startTime, endTime, subjectId), new PageVo(page, pageSize));
    }
}
