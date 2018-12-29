package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyBankAccountMapper;
import com.yeta.pps.po.BankAccount;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.BankAccountVo;
import com.yeta.pps.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 银行账户相关逻辑处理
 * @author YETA
 * @date 2018/12/03/16:45
 */
@Service
public class BankAccountService {

    @Autowired
    private MyBankAccountMapper myBankAccountMapper;

    /**
     * 新增银行账户
     * @param bankAccountVo
     * @return
     */
    public CommonResponse add(BankAccountVo bankAccountVo) {
        //新增
        if (myBankAccountMapper.add(bankAccountVo) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }
        return CommonResponse.success();
    }

    /**
     * 删除银行账户
     * @param bankAccountVos
     * @return
     */
    @Transactional
    public CommonResponse delete(List<BankAccountVo> bankAccountVos) {
        bankAccountVos.stream().forEach(bankAccountVo -> {
            //删除银行账户
            if (myBankAccountMapper.delete(bankAccountVo) != 1) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
        });
        return CommonResponse.success();
    }

    /**
     * 修改银行账户
     * @param bankAccountVo
     * @return
     */
    public CommonResponse update(BankAccountVo bankAccountVo) {
        //修改
        if (myBankAccountMapper.update(bankAccountVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        return CommonResponse.success();
    }

    /**
     * 查询所有银行账户
     * @param bankAccountVo
     * @return
     */
    public CommonResponse findAll(BankAccountVo bankAccountVo, PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage((int) Math.ceil(myBankAccountMapper.findCount(bankAccountVo) * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<BankAccount> bankAccounts = myBankAccountMapper.findAllPaged(bankAccountVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("科目编号", "id"));
            titles.add(new Title("科目名称", "name"));
            titles.add(new Title("账户类型", "type"));
            titles.add(new Title("户主名", "head"));
            titles.add(new Title("账户", "account"));
            titles.add(new Title("是否用于商城收款", "gathering"));
            titles.add(new Title("收款码", "qrCode"));
            titles.add(new Title("是否用于订货平台", "procurement"));
            CommonResult commonResult = new CommonResult(titles, bankAccounts, pageVo);
            return CommonResponse.success(commonResult);
        }
        //不分页
        List<BankAccount> bankAccounts = myBankAccountMapper.findAll(bankAccountVo);
        return CommonResponse.success(bankAccounts);
    }

    /**
     * 根据id查询银行账户
     * @param bankAccountVo
     * @return
     */
    public CommonResponse findById(BankAccountVo bankAccountVo) {
        BankAccount bankAccount = myBankAccountMapper.findById(bankAccountVo);
        if (bankAccount == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }
        return CommonResponse.success(bankAccount);
    }
}
