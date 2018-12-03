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
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除银行账户
     * @param bankAccountVo
     * @return
     */
    public CommonResponse delete(BankAccountVo bankAccountVo) {
        //删除银行账户
        if (myBankAccountMapper.delete(bankAccountVo) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改银行账户
     * @param bankAccountVo
     * @return
     */
    public CommonResponse update(BankAccountVo bankAccountVo) {
        //修改
        if (myBankAccountMapper.update(bankAccountVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
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
            pageVo.setTotalPage(myBankAccountMapper.findCount(bankAccountVo) / pageVo.getPageSize());
            List<BankAccount> bankAccounts = myBankAccountMapper.findAllPaged(bankAccountVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("id", "科目编号"));
            titles.add(new Title("name", "科目名称"));
            titles.add(new Title("type", "账户类型"));
            titles.add(new Title("head", "户主名"));
            titles.add(new Title("account", "账户"));
            titles.add(new Title("gathering", "是否用于商城收款"));
            titles.add(new Title("qrcode", "收款码"));
            titles.add(new Title("procurement", "是否用于订货平台"));
            CommonResult commonResult = new CommonResult(titles, bankAccounts, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<BankAccount> bankAccounts = myBankAccountMapper.findAll(bankAccountVo);
        return new CommonResponse(CommonResponse.CODE1, bankAccounts, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询银行账户
     * @param bankAccountVo
     * @return
     */
    public CommonResponse findById(BankAccountVo bankAccountVo) {
        BankAccount bankAccount = myBankAccountMapper.findById(bankAccountVo);
        if (bankAccount == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, bankAccount, CommonResponse.MESSAGE1);
    }
}
