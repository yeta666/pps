package com.yeta.pps.mapper;

import com.yeta.pps.po.BankAccount;
import com.yeta.pps.vo.BankAccountVo;
import com.yeta.pps.vo.PageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyBankAccountMapper {

    int add(BankAccountVo bankAccountVo);

    int delete(BankAccountVo bankAccountVo);

    int update(BankAccountVo bankAccountVo);

    int findCount(BankAccountVo bankAccountVo);

    List<BankAccount> findAll(BankAccountVo bankAccountVo);

    List<BankAccount> findAllPaged(@Param(value = "bankAccountVo") BankAccountVo bankAccountVo, @Param(value = "pageVo") PageVo pageVo);

    BankAccount findById(BankAccountVo bankAccountVo);
}