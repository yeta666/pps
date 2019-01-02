package com.yeta.pps.controller;

import com.yeta.pps.service.BankAccountService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.BankAccountVo;
import com.yeta.pps.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 银行账户相关接口
 * @author YETA
 * @date 2018/12/03/16:53
 */
@Api(value = "银行账户相关接口")
@RestController
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    /**
     * 新增银行账户接口
     * @param bankAccountVo
     * @return
     */
    @ApiOperation(value = "新增银行账户")
    @ApiImplicitParam(name = "bankAccountVo", value = "storeId, id, name, type(1：现金，2：银行卡，3：支付宝，4：微信)必填", required = true, paramType = "body", dataType = "BankAccountVo")
    @PostMapping(value = "/bankAccounts")
    public CommonResponse add(@RequestBody @Valid BankAccountVo bankAccountVo) {
        return bankAccountService.add(bankAccountVo);
    }

    /**
     * 删除银行账户接口
     * @param storeId
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除银行账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "银行账户id，英文逗号隔开", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/bankAccounts")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @RequestParam(value = "ids") String ids) {
        List<BankAccountVo> bankAccountVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            bankAccountVos.add(new BankAccountVo(storeId, id));
        });
        return bankAccountService.delete(bankAccountVos);
    }

    /**
     * 修改银行账户接口
     * @param bankAccountVo
     * @return
     */
    @ApiOperation(value = "修改银行账户")
    @ApiImplicitParam(name = "bankAccountVo", value = "storeId, id, name, type(1：现金，2：银行卡，3：支付宝，4：微信)必填", required = true, paramType = "body", dataType = "BankAccountVo")
    @PutMapping(value = "/bankAccounts")
    public CommonResponse update(@RequestBody @Valid BankAccountVo bankAccountVo) {
        return bankAccountService.update(bankAccountVo);
    }

    /**
     * 查询所有银行账户接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询所有银行账户", notes = "分页查询用于表格，不分页查询用于其他")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页数，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每次显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/bankAccounts")
    public CommonResponse<CommonResult<List<BankAccountVo>>> findAll(@RequestParam(value = "storeId") Integer storeId,
                                                                     @RequestParam(value = "page", required = false) Integer page,
                                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return bankAccountService.findAll(new BankAccountVo(storeId), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询银行账户接口
     * @param storeId
     * @param bankAccountId
     * @return
     */
    @ApiOperation(value = "根据id查询银行账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "bankAccountId", value = "银行账户id", required = true, paramType = "path", dataType = "String")
    })
    @GetMapping(value = "/bankAccounts/{bankAccountId}")
    public CommonResponse<BankAccountVo> findById(@RequestParam(value = "storeId") Integer storeId,
                                                  @PathVariable(value = "bankAccountId") String bankAccountId) {
        return bankAccountService.findById(new BankAccountVo(storeId, bankAccountId));
    }
}
