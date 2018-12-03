package com.yeta.pps.mapper;

import com.yeta.pps.po.IncomeExpenses;
import com.yeta.pps.vo.IncomeExpensesVo;
import com.yeta.pps.vo.PageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyIncomeExpensesMapper {

    int add(IncomeExpensesVo incomeExpensesVo);

    int delete(IncomeExpensesVo incomeExpensesVo);

    int update(IncomeExpensesVo incomeExpensesVo);

    int findCount(IncomeExpensesVo incomeExpensesVo);

    List<IncomeExpenses> findAll(IncomeExpensesVo incomeExpensesVo);

    List<IncomeExpenses> findAllPaged(@Param(value = "incomeExpensesVo") IncomeExpensesVo incomeExpensesVo, @Param(value = "pageVo") PageVo pageVo);

    IncomeExpenses findById(IncomeExpensesVo incomeExpensesVo);
}