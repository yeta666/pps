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
import org.springframework.transaction.annotation.Transactional;

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
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }
        return CommonResponse.success();
    }

    /**
     * 删除收支费用
     * @param incomeExpensesVos
     * @return
     */
    @Transactional
    public CommonResponse delete(List<IncomeExpensesVo> incomeExpensesVos) {
        incomeExpensesVos.stream().forEach(incomeExpensesVo -> {
            //删除收支费用
            if (myIncomeExpensesMapper.delete(incomeExpensesVo) != 1) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
        });
        return CommonResponse.success();
    }

    /**
     * 修改收支费用
     * @param incomeExpensesVo
     * @return
     */
    public CommonResponse update(IncomeExpensesVo incomeExpensesVo) {
        //修改
        if (myIncomeExpensesMapper.update(incomeExpensesVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        return CommonResponse.success();
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
            pageVo.setTotalPage((int) Math.ceil(myIncomeExpensesMapper.findCount(incomeExpensesVo) * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<IncomeExpenses> incomeExpenses = myIncomeExpensesMapper.findAllPaged(incomeExpensesVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("科目编号", "id"));
            titles.add(new Title("科目名称", "name"));
            titles.add(new Title("核算项", "checkItem"));
            titles.add(new Title("借贷", "debitCredit"));
            titles.add(new Title("账户类型", "type"));
            CommonResult commonResult = new CommonResult(titles, incomeExpenses, pageVo);
            return CommonResponse.success(commonResult);
        }
        //不分页
        List<IncomeExpenses> incomeExpenses = myIncomeExpensesMapper.findAll(incomeExpensesVo);
        return CommonResponse.success(incomeExpenses);
    }

    /**
     * 根据id查询收支费用
     * @param incomeExpensesVo
     * @return
     */
    public CommonResponse findById(IncomeExpensesVo incomeExpensesVo) {
        IncomeExpenses incomeExpenses = myIncomeExpensesMapper.findById(incomeExpensesVo);
        if (incomeExpenses == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }
        return CommonResponse.success(incomeExpenses);
    }
}
