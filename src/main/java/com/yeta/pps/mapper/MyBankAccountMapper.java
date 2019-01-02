package com.yeta.pps.mapper;

import com.yeta.pps.vo.BankAccountVo;
import com.yeta.pps.vo.PageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyBankAccountMapper {

    int add(BankAccountVo bankAccountVo);

    int delete(BankAccountVo bankAccountVo);

    int update(BankAccountVo bankAccountVo);

    int updateOpening(BankAccountVo bankAccountVo);

    int findCount(BankAccountVo bankAccountVo);

    List<BankAccountVo> findAll(BankAccountVo bankAccountVo);

    List<BankAccountVo> findAllPaged(@Param(value = "bankAccountVo") BankAccountVo bankAccountVo, @Param(value = "pageVo") PageVo pageVo);

    BankAccountVo findById(BankAccountVo bankAccountVo);
}