package com.yeta.pps.controller;

import com.yeta.pps.service.IncomeExpensesService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.IncomeExpensesVo;
import com.yeta.pps.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 收支费用相关接口
 * @author YETA
 * @date 2018/12/03/17:53
 */
@RestController
public class IncomeExpensesController {

    @Autowired
    private IncomeExpensesService incomeExpensesService;

    /**
     * 新增收支费用接口
     * @param incomeExpensesVo
     * @return
     */
    @PostMapping(value = "/incomeExpenses")
    public CommonResponse add(@RequestBody @Valid IncomeExpensesVo incomeExpensesVo) {
        return incomeExpensesService.add(incomeExpensesVo);
    }

    /**
     * 删除收支费用接口
     * @param storeId
     * @param incomeExpensesId
     * @return
     */
    @DeleteMapping(value = "/incomeExpenses/{incomeExpensesId}")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @PathVariable(value = "incomeExpensesId") String incomeExpensesId) {
        return incomeExpensesService.delete(new IncomeExpensesVo(storeId, incomeExpensesId));
    }

    /**
     * 修改收支费用接口
     * @param incomeExpensesVo
     * @return
     */
    @PutMapping(value = "/incomeExpenses")
    public CommonResponse update(@RequestBody @Valid IncomeExpensesVo incomeExpensesVo) {
        return incomeExpensesService.update(incomeExpensesVo);
    }

    /**
     * 查询所有收支费用接口
     * @param storeId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/incomeExpenses")
    public CommonResponse findAll(@RequestParam(value = "storeId") Integer storeId,
                                  @RequestParam(value = "type", required = false) Byte type,
                                  @RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return incomeExpensesService.findAll(new IncomeExpensesVo(storeId, type), new PageVo(page, pageSize));
    }

    /**
     * 根据id查询收支费用接口
     * @param storeId
     * @param incomeExpensesId
     * @return
     */
    @GetMapping(value = "/incomeExpenses/{incomeExpensesId}")
    public CommonResponse findById(@RequestParam(value = "storeId") Integer storeId,
                                   @PathVariable(value = "incomeExpensesId") String incomeExpensesId) {
        return incomeExpensesService.findById(new IncomeExpensesVo(storeId, incomeExpensesId));
    }
}
