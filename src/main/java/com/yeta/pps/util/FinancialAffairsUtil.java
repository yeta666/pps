package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.FinancialAffairsMapper;
import com.yeta.pps.vo.AccountingDocumentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 财务相关工具类
 * @author YETA
 * @date 2019/01/21/16:17
 */
@Component
public class FinancialAffairsUtil {

    @Autowired
    private FinancialAffairsMapper financialAffairsMapper;

    /**
     * 新增会计凭证的方法
     * @param vo
     */
    @Transactional
    public void addAccountingDocumentMethod(AccountingDocumentVo vo) {
        //判断参数
        if (vo.getStoreId() == null || vo.getOrderId() == null || vo.getSubjectId() == null || (vo.getDebitMoney() == null && vo.getCreditMoney() == null) || vo.getUserId() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR, "新增会计凭证参数错误");
        }

        //设置初始属性
        vo.setId("KJPZ_" + UUID.randomUUID().toString().replace("-", ""));
        vo.setOrderStatus((byte) 1);
        vo.setType((byte) 1);

        //新增
        if (financialAffairsMapper.addAccountingDocument(vo) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR, "新增会计凭证失败");
        }
    }

    /**
     * 红冲会计凭证的方法
     * @param vo
     */
    @Transactional
    public void redDashedAccountingDocumentMethod(AccountingDocumentVo vo) {
        //判断参数
        if (vo.getStoreId() == null || vo.getOrderId() == null || vo.getUserId() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR, "红冲会计凭证参数错误");
        }

        //获取参数
        Integer storeId = vo.getStoreId();
        String userId = vo.getUserId();

        //红冲
        if (financialAffairsMapper.redDashedAccountingDocument(vo) == 0) {
            throw new CommonException(CommonResponse.UPDATE_ERROR, "红冲会计凭证失败");
        }

        //获取红冲蓝单
        List<AccountingDocumentVo> vos = financialAffairsMapper.findAllAccountingDocument(vo);
        if (vos.size() == 0) {
            throw new CommonException(CommonResponse.UPDATE_ERROR, "红冲会计凭证失败");
        }
        vos.stream().forEach(accountingDocumentVo -> {
            //设置红冲红单
            accountingDocumentVo.setStoreId(storeId);
            accountingDocumentVo.setOrderStatus((byte) -2);
            accountingDocumentVo.setDebitMoney(-accountingDocumentVo.getDebitMoney());
            accountingDocumentVo.setCreditMoney(-accountingDocumentVo.getCreditMoney());
            accountingDocumentVo.setUserId(userId);

            //新增红冲红单
            if (financialAffairsMapper.addAccountingDocument(accountingDocumentVo) != 1) {
                throw new CommonException(CommonResponse.UPDATE_ERROR, "红冲会计凭证失败");
            }
        });
    }
}
