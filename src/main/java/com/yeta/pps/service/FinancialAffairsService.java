package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.FinancialAffairsMapper;
import com.yeta.pps.po.AccountingSubject;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.SystemUtil;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.AccountingDocumentVo;
import com.yeta.pps.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 财务相关逻辑处理
 * @author YETA
 * @date 2019/01/21/13:13
 */
@Service
public class FinancialAffairsService {

    @Autowired
    private FinancialAffairsMapper financialAffairsMapper;

    @Autowired
    private SystemUtil systemUtil;

    //会计科目

    /**
     * 新增会计科目
     * @param accountingSubject
     * @return
     */
    public CommonResponse addAccountingSubject(AccountingSubject accountingSubject) {
        if (financialAffairsMapper.addAccountingSubject(accountingSubject) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }
        return CommonResponse.success();
    }

    /**
     * 删除会计科目
     * @param accountingSubjects
     * @return
     */
    @Transactional
    public CommonResponse deleteAccountingSubject(List<AccountingSubject> accountingSubjects) {
        accountingSubjects.stream().forEach(accountingSubject -> {
            //判断会计科目是否使用

            //删除
            if (financialAffairsMapper.deleteAccountingSubject(accountingSubject) != 1) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
        });

        return CommonResponse.success();
    }

    /**
     * 修改会计科目
     * @param accountingSubject
     * @return
     */
    public CommonResponse updateAccountingSubject(AccountingSubject accountingSubject) {
        if (financialAffairsMapper.updateAccountingSubject(accountingSubject) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        return CommonResponse.success();
    }

    /**
     * 查询会计科目
     * @param accountingSubject
     * @param pageVo
     * @return
     */
    public CommonResponse findAllAccountingSubject(AccountingSubject accountingSubject, PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage((int) Math.ceil(financialAffairsMapper.findCountAccountingSubject(accountingSubject) * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<AccountingSubject> accountingSubjects = financialAffairsMapper.findPagedAccountingSubject(accountingSubject, pageVo);

            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("科目编号", "id"));
            titles.add(new Title("科目名称", "name"));
            titles.add(new Title("核算项", "checkItem"));
            titles.add(new Title("借贷", "debitCredit"));
            CommonResult commonResult = new CommonResult(titles, accountingSubjects, pageVo);
            return CommonResponse.success(commonResult);
        }

        //不分页
        List<AccountingSubject> accountingSubjects = financialAffairsMapper.findAllAccountingSubject(accountingSubject);
        return CommonResponse.success(accountingSubjects);
    }

    //财务期初

    /**
     * 查询固定资产期初
     * @param accountingSubject
     * @return
     */
    public CommonResponse findFixedAssetsOpening(AccountingSubject accountingSubject) {
        accountingSubject = financialAffairsMapper.findFixedAssetsOpening(accountingSubject);
        if (accountingSubject == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }

        return CommonResponse.success(accountingSubject);
    }

    /**
     * 设置固定资产期初
     * @param accountingSubject
     * @return
     */
    public CommonResponse updateFixedAssetsOpening(AccountingSubject accountingSubject) {
        //判断参数
        if (accountingSubject.getStoreId() == null || accountingSubject.getFixedAssetsOpening() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //判断系统是否开账
        if (systemUtil.judgeStartBillMethod(accountingSubject.getStoreId())) {
            throw new CommonException(CommonResponse.UPDATE_ERROR, "系统已开账");
        }

        //设置
        if (financialAffairsMapper.updateFixedAssetsOpening(accountingSubject) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        return CommonResponse.success();
    }

    /**
     * 查询资产负债期初
     * @param accountingSubject
     * @return
     */
    public CommonResponse findAssetsLiabilitiesOpening(AccountingSubject accountingSubject) {
        List<AccountingSubject> accountingSubjects = financialAffairsMapper.findAssetsLiabilitiesOpening(accountingSubject);
        return CommonResponse.success(accountingSubjects);
    }

    /**
     * 设置资产负债期初
     * @param accountingSubject
     * @return
     */
    public CommonResponse updateAssetsLiabilitiesOpening(AccountingSubject accountingSubject) {
        //判断参数
        if (accountingSubject.getStoreId() == null || accountingSubject.getAssetsLiabilitiesOpening() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //判断系统是否开账
        if (systemUtil.judgeStartBillMethod(accountingSubject.getStoreId())) {
            throw new CommonException(CommonResponse.UPDATE_ERROR, "系统已开账");
        }

        //设置
        if (financialAffairsMapper.updateAssetsLiabilitiesOpening(accountingSubject) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        return CommonResponse.success();
    }

    //会计凭证

    /**
     * 查询会计凭证
     * @param accountingDocumentVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllAccountingDocument(AccountingDocumentVo accountingDocumentVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(financialAffairsMapper.findCountAccountingDocument(accountingDocumentVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<AccountingDocumentVo> vos = financialAffairsMapper.findPagedAccountingDocument(accountingDocumentVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "id"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("来源单据编号", "orderId"));
        titles.add(new Title("科目编号", "subjectId"));
        titles.add(new Title("科目名称", "subjectName"));
        titles.add(new Title("单据类型", "typeName"));
        titles.add(new Title("核算单位编号", "targetId"));
        titles.add(new Title("核算单位名称", "targetName"));
        titles.add(new Title("借方金额", "debitMoney"));
        titles.add(new Title("贷方金额", "creditMoney"));
        titles.add(new Title("经手人", "userName"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }
}
