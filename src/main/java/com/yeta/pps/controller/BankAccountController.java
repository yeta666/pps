package com.yeta.pps.controller;

import com.yeta.pps.service.BankAccountService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.BankAccountVo;
import com.yeta.pps.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 银行账户相关接口
 * @author YETA
 * @date 2018/12/03/16:53
 */
@RestController
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    /**
     * 新增银行账户接口
     * @param bankAccountVo
     * @return
     */
    @PostMapping(value = "/bankAccounts")
    public CommonResponse add(@RequestBody @Valid BankAccountVo bankAccountVo) {
        return bankAccountService.add(bankAccountVo);
    }

    /**
     * 删除银行账户接口
     * @param storeId
     * @param bankAccountId
     * @return
     */
    @DeleteMapping(value = "/bankAccounts/{bankAccountId}")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @PathVariable(value = "bankAccountId") String bankAccountId) {
        return bankAccountService.delete(new BankAccountVo(storeId, bankAccountId));
    }

    /**
     * 修改银行账户接口
     * @param bankAccountVo
     * @return
     */
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
    @GetMapping(value = "/bankAccounts")
    public CommonResponse findAll(@RequestParam(value = "storeId") Integer storeId,
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
    @GetMapping(value = "/bankAccounts/{bankAccountId}")
    public CommonResponse findById(@RequestParam(value = "storeId") Integer storeId,
                                   @PathVariable(value = "bankAccountId") String bankAccountId) {
        return bankAccountService.findById(new BankAccountVo(storeId, bankAccountId));
    }
}
