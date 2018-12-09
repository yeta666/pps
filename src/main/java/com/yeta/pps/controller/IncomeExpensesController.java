package com.yeta.pps.controller;

import com.yeta.pps.po.IncomeExpenses;
import com.yeta.pps.service.IncomeExpensesService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.vo.IncomeExpensesVo;
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
 * 收支费用相关接口
 * @author YETA
 * @date 2018/12/03/17:53
 */
@Api(value = "收支费用相关接口")
@RestController
public class IncomeExpensesController {

    @Autowired
    private IncomeExpensesService incomeExpensesService;

    /**
     * 新增收支费用接口
     * @param incomeExpensesVo
     * @return
     */
    @ApiOperation(value = "新增收支费用")
    @ApiImplicitParam(name = "incomeExpensesVo", value = "storeId, id, name, checkItem(1：供应商，2：客户，3：往来单位，4：职员，5：部门), debit_credit(1：贷，2：借), type(1：收入，2：支出)必填", required = true, paramType = "body", dataType = "IncomeExpensesVo")
    @PostMapping(value = "/incomeExpenses")
    public CommonResponse add(@RequestBody @Valid IncomeExpensesVo incomeExpensesVo) {
        return incomeExpensesService.add(incomeExpensesVo);
    }

    /**
     * 删除收支费用接口
     * @param storeId
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除收支费用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "收支费用id，英文逗号隔开", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/incomeExpenses")
    public CommonResponse delete(@RequestParam(value = "storeId") Integer storeId,
                                 @RequestParam(value = "ids") String ids) {
        List<IncomeExpensesVo> incomeExpensesVos = new ArrayList<>();
        Arrays.asList(ids.split(",")).stream().forEach(id -> {
            incomeExpensesVos.add(new IncomeExpensesVo(storeId, id));
        });
        return incomeExpensesService.delete(incomeExpensesVos);
    }

    /**
     * 修改收支费用接口
     * @param incomeExpensesVo
     * @return
     */
    @ApiOperation(value = "修改收支费用")
    @ApiImplicitParam(name = "incomeExpensesVo", value = "storeId, id, name, checkItem(1：供应商，2：客户，3：往来单位，4：职员，5：部门), debit_credit(1：贷，2：借), type(1：收入，2：支出)必填", required = true, paramType = "body", dataType = "IncomeExpensesVo")
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
    @ApiOperation(value = "查询所有收支费用", notes = "分页查询用于表格，不分页不筛选查询用于其他")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "类型(1：收入，2：支出)", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page", value = "当前页数，从1开始", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每次显示条数", required = false, paramType = "query", dataType = "int")
    })
    @GetMapping(value = "/incomeExpenses")
    public CommonResponse<CommonResult<List<IncomeExpenses>>> findAll(@RequestParam(value = "storeId") Integer storeId,
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
    @ApiOperation(value = "根据id查询收支费用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "店铺id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "incomeExpensesId", value = "收支费用id", required = true, paramType = "path", dataType = "String")
    })
    @GetMapping(value = "/incomeExpenses/{incomeExpensesId}")
    public CommonResponse findById(@RequestParam(value = "storeId") Integer storeId,
                                   @PathVariable(value = "incomeExpensesId") String incomeExpensesId) {
        return incomeExpensesService.findById(new IncomeExpensesVo(storeId, incomeExpensesId));
    }
}
