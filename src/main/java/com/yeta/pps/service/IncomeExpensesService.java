package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyIncomeExpensesMapper;
import com.yeta.pps.po.IncomeExpenses;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.IncomeExpensesVo;
import com.yeta.pps.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 收支费用逻辑处理
 * @author YETA
 * @date 2018/12/03/17:48
 */
@Service
public class IncomeExpensesService {

    @Autowired
    private MyIncomeExpensesMapper myIncomeExpensesMapper;

    /**
     * 新增收支费用
     * @param incomeExpensesVo
     * @return
     */
    public CommonResponse add(IncomeExpensesVo incomeExpensesVo) {
        //新增
        if (myIncomeExpensesMapper.add(incomeExpensesVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除收支费用
     * @param incomeExpensesVo
     * @return
     */
    public CommonResponse delete(IncomeExpensesVo incomeExpensesVo) {
        //删除收支费用
        if (myIncomeExpensesMapper.delete(incomeExpensesVo) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改收支费用
     * @param incomeExpensesVo
     * @return
     */
    public CommonResponse update(IncomeExpensesVo incomeExpensesVo) {
        //修改
        if (myIncomeExpensesMapper.update(incomeExpensesVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查询所有收支费用
     * @param incomeExpensesVo
     * @return
     */
    public CommonResponse findAll(IncomeExpensesVo incomeExpensesVo, PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage(myIncomeExpensesMapper.findCount(incomeExpensesVo) / pageVo.getPageSize());
            List<IncomeExpenses> incomeExpenses = myIncomeExpensesMapper.findAllPaged(incomeExpensesVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("id", "科目编号"));
            titles.add(new Title("name", "科目名称"));
            titles.add(new Title("checkItem", "核算项"));
            titles.add(new Title("debitCredit", "借贷"));
            titles.add(new Title("type", "账户类型"));
            CommonResult commonResult = new CommonResult(titles, incomeExpenses, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<IncomeExpenses> incomeExpenses = myIncomeExpensesMapper.findAll(incomeExpensesVo);
        return new CommonResponse(CommonResponse.CODE1, incomeExpenses, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询收支费用
     * @param incomeExpensesVo
     * @return
     */
    public CommonResponse findById(IncomeExpensesVo incomeExpensesVo) {
        IncomeExpenses incomeExpenses = myIncomeExpensesMapper.findById(incomeExpensesVo);
        if (incomeExpenses == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, incomeExpenses, CommonResponse.MESSAGE1);
    }
}
