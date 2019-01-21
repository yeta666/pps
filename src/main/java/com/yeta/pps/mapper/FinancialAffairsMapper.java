package com.yeta.pps.mapper;

import com.yeta.pps.po.AccountingSubject;
import com.yeta.pps.vo.AccountingDocumentVo;
import com.yeta.pps.vo.PageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YETA
 * @date 2019/01/21/13:15
 */
public interface FinancialAffairsMapper {

    //会计科目

    int addAccountingSubject(AccountingSubject accountingSubject);

    int deleteAccountingSubject(AccountingSubject accountingSubject);

    int updateAccountingSubject(AccountingSubject accountingSubject);

    int findCountAccountingSubject(AccountingSubject accountingSubject);

    List<AccountingSubject> findPagedAccountingSubject(@Param(value = "vo") AccountingSubject accountingSubject,
                                                       @Param(value = "pageVo")PageVo pageVo);

    List<AccountingSubject> findAllAccountingSubject(AccountingSubject accountingSubject);

    //财务期初

    AccountingSubject findFixedAssetsOpening(AccountingSubject accountingSubject);

    int updateFixedAssetsOpening(AccountingSubject accountingSubject);

    List<AccountingSubject> findAssetsLiabilitiesOpening(AccountingSubject accountingSubject);

    int updateAssetsLiabilitiesOpening(AccountingSubject accountingSubject);

    //会计凭证

    int addAccountingDocument(AccountingDocumentVo accountingDocumentVo);

    int redDashedAccountingDocument(AccountingDocumentVo accountingDocumentVo);

    int findCountAccountingDocument(AccountingDocumentVo accountingDocumentVo);

    List<AccountingDocumentVo> findPagedAccountingDocument(@Param(value = "vo") AccountingDocumentVo accountingDocumentVo,
                                                           @Param(value = "pageVo")PageVo pageVo);

    List<AccountingDocumentVo> findAllAccountingDocument(AccountingDocumentVo accountingDocumentVo);
}
