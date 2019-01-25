package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.*;
import com.yeta.pps.po.ProcurementApplyOrder;
import com.yeta.pps.po.StoreClient;
import com.yeta.pps.util.*;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 资金相关逻辑处理
 * @author YETA
 * @date 2018/12/13/14:22
 */
@Service
public class FundService {

    @Autowired
    private MyFundMapper myFundMapper;

    @Autowired
    private MyProcurementMapper myProcurementMapper;

    @Autowired
    private MySellMapper mySellMapper;

    @Autowired
    private MyClientMapper myClientMapper;

    @Autowired
    private MySupplierMapper mySupplierMapper;

    @Autowired
    private MyBankAccountMapper myBankAccountMapper;

    @Autowired
    private SystemUtil systemUtil;

    @Autowired
    private FundUtil fundUtil;

    @Autowired
    private FinancialAffairsUtil financialAffairsUtil;

    @Autowired
    private PrimaryKeyUtil primaryKeyUtil;

    /**
     * 按单收款
     * @param sellApplyOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findNotClearedSellApplyOrder(SellApplyOrderVo sellApplyOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myFundMapper.findCountNotClearedSellApplyOrder(sellApplyOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<SellApplyOrderVo> sellApplyOrderVos = myFundMapper.findPagedNotClearedSellApplyOrder(sellApplyOrderVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "id"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("产生方式", "prodcingWay"));
        titles.add(new Title("单据状态", "orderStatus"));
        titles.add(new Title("客户编号", "client.id"));
        titles.add(new Title("客户", "client.name"));
        titles.add(new Title("电话", "client.phone"));
        titles.add(new Title("会员卡号", "client.membershipNumber"));
        titles.add(new Title("总商品金额", "totalMoney"));
        titles.add(new Title("直接优惠金额", "discountMoney"));
        titles.add(new Title("优惠券编号", "discountCouponId"));
        titles.add(new Title("总优惠金额", "totalDiscountMoney"));
        titles.add(new Title("本单金额", "orderMoney"));
        titles.add(new Title("结算状态", "clearStatus"));
        titles.add(new Title("已结金额", "clearedMoney"));
        titles.add(new Title("未结金额", "notClearedMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, sellApplyOrderVos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 按单付款
     * @param procurementApplyOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findNotClearedProcurementApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myFundMapper.findCountNotClearedProcurementApplyOrder(procurementApplyOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ProcurementApplyOrderVo> procurementApplyOrderVos = myFundMapper.findPagedNotClearedProcurementApplyOrder(procurementApplyOrderVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "id"));
        titles.add(new Title("单据类型", "type"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("单据状态", "orderStatus"));
        titles.add(new Title("供应商", "supplierName"));
        titles.add(new Title("总商品金额", "totalMoney"));
        titles.add(new Title("总优惠金额", "totalDiscountMoney"));
        titles.add(new Title("本单金额", "orderMoney"));
        titles.add(new Title("结算状态", "clearStatus"));
        titles.add(new Title("已结金额", "clearedMoney"));
        titles.add(new Title("未结金额", "notClearedMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, procurementApplyOrderVos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 查询预收/付款余额
     * @param fundTargetCheckOrderVo
     * @param type
     * @return
     */
    public CommonResponse findLastFundTargetCheckOrder(FundTargetCheckOrderVo fundTargetCheckOrderVo, int type) {
        fundTargetCheckOrderVo = myFundMapper.findLastFundTargetCheckOrder(fundTargetCheckOrderVo);
        double advanceMoney;
        if (fundTargetCheckOrderVo == null) {
            advanceMoney = 0;
        } else {
            if (type == 1) {
                advanceMoney = fundTargetCheckOrderVo.getAdvanceInMoney();
            } else if (type == 2) {
                advanceMoney = fundTargetCheckOrderVo.getAdvanceOutMoney();
            } else {
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
            }
        }
        return CommonResponse.success(advanceMoney);
    }

    /**
     * 判断结算金额的方法
     * @param fundOrderVo
     * @param so
     * @param po
     */
    @Transactional
    public void judgeMoneyMethod(FundOrderVo fundOrderVo, SellApplyOrderVo so, ProcurementApplyOrder po) {
        //判断参数
        if (fundOrderVo.getMoney() >= 0 && fundOrderVo.getDiscountMoney() >= 0 && fundOrderVo.getAdvanceMoney() >= 0) {

        } else if (fundOrderVo.getMoney() <= 0 && fundOrderVo.getDiscountMoney() <= 0 && fundOrderVo.getAdvanceMoney() <= 0) {

        } else {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }
        double changeMoney = Math.abs(fundOrderVo.getMoney() + fundOrderVo.getDiscountMoney() + fundOrderVo.getAdvanceMoney());

        //结算状态和金额
        byte clearStatus = -1;
        double orderMoney = -1;
        double clearedMoney = -1;
        double notClearedMoney = -1;

        if (so != null && po == null) {     //结算销售申请单
            //获取结算单据结算状态和金额
            clearStatus = so.getClearStatus();
            orderMoney = Math.abs(so.getOrderMoney());
            clearedMoney = Math.abs(so.getClearedMoney());
            notClearedMoney = Math.abs(so.getNotClearedMoney());
        } else if (so == null && po != null) {      //结算采购申请单
            clearStatus = po.getClearStatus();
            orderMoney = Math.abs(po.getOrderMoney());
            clearedMoney = Math.abs(po.getClearedMoney());
            notClearedMoney = Math.abs(po.getNotClearedMoney());
        }

        //判断结算单据结算状态
        if (clearStatus != 0 || orderMoney != clearedMoney + notClearedMoney) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }

        //判断结算金额是否正确
        if (changeMoney == notClearedMoney && changeMoney + clearedMoney == orderMoney) {       //全部结算
            fundOrderVo.setOrderStatus((byte) 1);
        } else if (changeMoney < notClearedMoney && changeMoney + clearedMoney < orderMoney) {      //部分结算
            fundOrderVo.setOrderStatus((byte) 0);
        } else {        //错误
            throw new CommonException(CommonResponse.ADD_ERROR);
        }

    }

    /**
     * 新增收/付款单、预收/付款单
     * @param fundOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addFundOrder(FundOrderVo fundOrderVo) {
        //获取参数
        Integer storeId = fundOrderVo.getStoreId();
        String targetId = fundOrderVo.getTargetId();
        double money = fundOrderVo.getMoney();
        double advanceMoney;
        double discountMoney;
        double orderMoney;
        byte type = fundOrderVo.getType();

        //判断系统是否开账
        if (!systemUtil.judgeStartBillMethod(storeId)) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR, "系统未开账");
        }

        //设置资金记账初始属性
        FundCheckOrderVo fcoVo = new FundCheckOrderVo();
        fcoVo.setStoreId(storeId);
        fcoVo.setCreateTime(new Date());
        fcoVo.setOrderStatus((byte) 1);
        fcoVo.setTargetId(targetId);
        fcoVo.setBankAccountId(fundOrderVo.getBankAccountId());
        fcoVo.setUserId(fundOrderVo.getUserId());

        //查询最新的资金对账记录
        FundCheckOrderVo lastFCOVo = myFundMapper.findLastBalanceMoney(fcoVo);
        if (lastFCOVo == null) {
            throw new CommonException(CommonResponse.ADD_ERROR, "现金银行期初未设置");
        }

        //设置往来记账初始属性
        FundTargetCheckOrderVo ftcoVo = new FundTargetCheckOrderVo();
        ftcoVo.setStoreId(storeId);
        ftcoVo.setCreateTime(new Date());
        ftcoVo.setOrderStatus((byte) 1);
        ftcoVo.setTargetId(targetId);
        //查询最新的往来对账记录
        FundTargetCheckOrderVo lastFTCOVo = myFundMapper.findLastFundTargetCheckOrder(ftcoVo);
        ftcoVo.setUserId(fundOrderVo.getUserId());

        //设置会计凭证对象
        AccountingDocumentVo adVo = new AccountingDocumentVo();
        adVo.setStoreId(storeId);
        adVo.setTargetId(targetId);
        adVo.setUserId(fcoVo.getUserId());

        //判断新增类型
        switch (type) {
            case 1:     //收款单
                //判断参数
                if (fundOrderVo.getOrderId() == null || fundOrderVo.getAdvanceMoney() == null || fundOrderVo.getDiscountMoney() == null) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                advanceMoney = fundOrderVo.getAdvanceMoney();
                discountMoney = fundOrderVo.getDiscountMoney();
                orderMoney = money + discountMoney + advanceMoney;
                if (orderMoney == 0) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                fundOrderVo.setId(primaryKeyUtil.getOrderPrimaryKeyMethod(myFundMapper.findFundOrderPrimaryKey(fundOrderVo), "SKD"));

                //获取结算单据
                SellApplyOrderVo sVo = mySellMapper.findApplyOrderById(new SellApplyOrderVo(storeId, fundOrderVo.getOrderId()));
                if (sVo == null) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                //判断结算金额
                judgeMoneyMethod(fundOrderVo, sVo, null);

                //修改结算单据结算状态和金额
                if (mySellMapper.updateApplyOrderClearStatusAndMoney(fundOrderVo) != 1) {
                    throw new CommonException(CommonResponse.ADD_ERROR);
                }

                fcoVo.setOrderId(fundOrderVo.getId());
                fcoVo.setInMoney(money);
                fcoVo.setOutMoney(0.0);
                fcoVo.setBalanceMoney(lastFCOVo.getBalanceMoney() + money);

                ftcoVo.setOrderId(fundOrderVo.getId());
                if (advanceMoney != 0 && (lastFTCOVo == null || lastFTCOVo.getAdvanceInMoney() < advanceMoney)) {
                    throw new CommonException(CommonResponse.ADD_ERROR, "使用预收款金额错误");
                }

                if (orderMoney > 0) {
                    ftcoVo.setNeedInMoneyDecrease(orderMoney);       //设置应收减少
                    if (lastFTCOVo == null) {
                        ftcoVo.setNeedInMoney(-ftcoVo.getNeedInMoneyDecrease());       //设置期末应收
                    } else {
                        ftcoVo.setNeedInMoney(lastFTCOVo.getNeedInMoney() - ftcoVo.getNeedInMoneyDecrease());       //设置期末应收
                    }
                    if (advanceMoney != 0) {
                        ftcoVo.setAdvanceInMoneyDecrease(advanceMoney);     //设置预收减少
                        ftcoVo.setAdvanceInMoney(lastFTCOVo.getAdvanceInMoney() - ftcoVo.getAdvanceInMoneyDecrease());      //设置期末预收
                    }
                } else if (orderMoney < 0){
                    ftcoVo.setNeedInMoneyIncrease(-orderMoney);       //设置应收增加
                    if (lastFTCOVo == null) {
                        ftcoVo.setNeedInMoney(ftcoVo.getNeedInMoneyIncrease());       //设置期末应收
                    } else {
                        ftcoVo.setNeedInMoney(lastFTCOVo.getNeedInMoney() + ftcoVo.getNeedInMoneyIncrease());       //设置期末应收
                    }
                    if (advanceMoney != 0) {
                        ftcoVo.setAdvanceInMoneyIncrease(-advanceMoney);     //设置预收增加
                        ftcoVo.setAdvanceInMoney(lastFTCOVo.getAdvanceInMoney() + ftcoVo.getAdvanceInMoneyIncrease());      //设置期末预收
                    }
                }

                adVo.setOrderId(fundOrderVo.getId());
                adVo.setSubjectId(fundOrderVo.getBankAccountId());
                adVo.setDebitMoney(money);
                adVo.setCreditMoney(0.0);
                financialAffairsUtil.addAccountingDocumentMethod(adVo);
                if (advanceMoney != 0) {
                    adVo.setSubjectId("2203");
                    adVo.setDebitMoney(advanceMoney);
                    adVo.setCreditMoney(0.0);
                    financialAffairsUtil.addAccountingDocumentMethod(adVo);
                }
                if (discountMoney != 0) {
                    adVo.setSubjectId("660101");
                    adVo.setDebitMoney(discountMoney);
                    adVo.setCreditMoney(0.0);
                    financialAffairsUtil.addAccountingDocumentMethod(adVo);
                }
                adVo.setSubjectId("1122");
                adVo.setDebitMoney(0.0);
                adVo.setCreditMoney(orderMoney);
                financialAffairsUtil.addAccountingDocumentMethod(adVo);
                break;

            case 2:     //付款单
                //判断参数
                if (fundOrderVo.getOrderId() == null || fundOrderVo.getAdvanceMoney() == null || fundOrderVo.getDiscountMoney() == null) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                advanceMoney = fundOrderVo.getAdvanceMoney();
                discountMoney = fundOrderVo.getDiscountMoney();
                orderMoney = money + discountMoney + advanceMoney;
                if (orderMoney == 0) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                fundOrderVo.setId(primaryKeyUtil.getOrderPrimaryKeyMethod(myFundMapper.findFundOrderPrimaryKey(fundOrderVo), "FKD"));

                //获取结算单据
                ProcurementApplyOrder procurementApplyOrder = myProcurementMapper.findApplyOrderById(new ProcurementApplyOrderVo(storeId, fundOrderVo.getOrderId()));
                if (procurementApplyOrder == null) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);

                }

                //判断结算金额
                judgeMoneyMethod(fundOrderVo, null, procurementApplyOrder);

                //修改结算单据结算状态和金额
                if (myProcurementMapper.updateApplyOrderClearStatusAndMoney(fundOrderVo) != 1) {
                    throw new CommonException(CommonResponse.ADD_ERROR);
                }

                fcoVo.setOrderId(fundOrderVo.getId());
                fcoVo.setInMoney(0.0);
                fcoVo.setOutMoney(money);
                fcoVo.setBalanceMoney(lastFCOVo.getBalanceMoney() - money);

                ftcoVo.setOrderId(fundOrderVo.getId());
                if (advanceMoney != 0 && (lastFTCOVo == null || lastFTCOVo.getAdvanceOutMoney() < advanceMoney)) {
                    throw new CommonException(CommonResponse.ADD_ERROR, "使用预付款金额错误");
                }
                if (orderMoney > 0) {
                    ftcoVo.setNeedOutMoneyDecrease(orderMoney);      //设置应付减少
                    if (lastFTCOVo == null) {
                        ftcoVo.setNeedOutMoney(-ftcoVo.getNeedOutMoneyDecrease());        //设置期末应付
                    } else {
                        ftcoVo.setNeedOutMoney(lastFTCOVo.getNeedOutMoney() - ftcoVo.getNeedOutMoneyDecrease());        //设置期末应付
                    }
                    if (advanceMoney != 0) {
                        ftcoVo.setAdvanceOutMoneyDecrease(advanceMoney);        //设置预付减少
                        ftcoVo.setAdvanceOutMoney(lastFTCOVo.getAdvanceOutMoney() - lastFTCOVo.getAdvanceOutMoneyDecrease());       //设置期末预付
                    }
                } else if (orderMoney < 0) {
                    ftcoVo.setNeedOutMoneyIncrease(-orderMoney);      //设置应付增加
                    if (lastFTCOVo == null) {
                        ftcoVo.setNeedOutMoney(ftcoVo.getNeedOutMoneyIncrease());        //设置期末应付
                    } else {
                        ftcoVo.setNeedOutMoney(lastFTCOVo.getNeedOutMoney() + ftcoVo.getNeedOutMoneyIncrease());        //设置期末应付
                    }
                    if (advanceMoney != 0) {
                        ftcoVo.setAdvanceOutMoneyIncrease(-advanceMoney);        //设置预付增加
                        ftcoVo.setAdvanceOutMoney(lastFTCOVo.getAdvanceOutMoney() + lastFTCOVo.getAdvanceOutMoneyIncrease());       //设置期末预付
                    }
                }

                adVo.setOrderId(fundOrderVo.getId());
                adVo.setSubjectId(fundOrderVo.getBankAccountId());
                adVo.setDebitMoney(0.0);
                adVo.setCreditMoney(money);
                financialAffairsUtil.addAccountingDocumentMethod(adVo);
                if (advanceMoney != 0) {
                    adVo.setSubjectId("1123");
                    adVo.setDebitMoney(0.0);
                    adVo.setCreditMoney(advanceMoney);
                    financialAffairsUtil.addAccountingDocumentMethod(adVo);
                }
                if (discountMoney != 0) {
                    adVo.setSubjectId("660101");
                    adVo.setDebitMoney(0.0);
                    adVo.setCreditMoney(discountMoney);
                    financialAffairsUtil.addAccountingDocumentMethod(adVo);
                }
                adVo.setSubjectId("2202");
                adVo.setDebitMoney(orderMoney);
                adVo.setCreditMoney(0.0);
                financialAffairsUtil.addAccountingDocumentMethod(adVo);
                break;

            case 3:     //预收款单
                //判断参数
                if (money <= 0) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                fundOrderVo.setId(primaryKeyUtil.getOrderPrimaryKeyMethod(myFundMapper.findFundOrderPrimaryKey(fundOrderVo), "YSKD"));
                fundOrderVo.setDiscountMoney(0.0);
                fundOrderVo.setAdvanceMoney(0.0);

                fcoVo.setOrderId(fundOrderVo.getId());
                fcoVo.setInMoney(money);
                fcoVo.setOutMoney(0.0);
                fcoVo.setBalanceMoney(lastFCOVo.getBalanceMoney() + money);

                ftcoVo.setOrderId(fundOrderVo.getId());
                ftcoVo.setAdvanceInMoneyIncrease(money);        //设置预收增加
                if (lastFTCOVo == null) {
                    ftcoVo.setNeedInMoney(0.0);        //设置期末应收
                    ftcoVo.setAdvanceInMoney(ftcoVo.getAdvanceInMoneyIncrease());      //设置期末预收
                } else {
                    ftcoVo.setNeedInMoney(lastFTCOVo.getNeedInMoney());        //设置期末应收
                    ftcoVo.setAdvanceInMoney(lastFTCOVo.getAdvanceInMoney() + ftcoVo.getAdvanceInMoneyIncrease());      //设置期末预收
                }

                adVo.setOrderId(fundOrderVo.getId());
                adVo.setSubjectId("2203");
                adVo.setDebitMoney(0.0);
                adVo.setCreditMoney(money);
                financialAffairsUtil.addAccountingDocumentMethod(adVo);
                adVo.setSubjectId(fundOrderVo.getBankAccountId());
                adVo.setDebitMoney(money);
                adVo.setCreditMoney(0.0);
                financialAffairsUtil.addAccountingDocumentMethod(adVo);
                break;

            case 4:     //预付款单
                //判断参数
                if (money <= 0) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                fundOrderVo.setId(primaryKeyUtil.getOrderPrimaryKeyMethod(myFundMapper.findFundOrderPrimaryKey(fundOrderVo), "YFKD"));
                fundOrderVo.setDiscountMoney(0.0);
                fundOrderVo.setAdvanceMoney(0.0);

                fcoVo.setOrderId(fundOrderVo.getId());
                fcoVo.setInMoney(0.0);
                fcoVo.setOutMoney(money);
                fcoVo.setBalanceMoney(lastFCOVo.getBalanceMoney() - money);

                ftcoVo.setOrderId(fundOrderVo.getId());
                ftcoVo.setAdvanceOutMoneyIncrease(money);        //设置预付增加
                if (lastFTCOVo == null) {
                    ftcoVo.setNeedOutMoney(0.0);        //设置期末应付
                    ftcoVo.setAdvanceOutMoney(ftcoVo.getAdvanceOutMoneyIncrease());      //设置期末预付
                } else {
                    ftcoVo.setNeedOutMoney(lastFTCOVo.getNeedOutMoney());        //设置期末应付
                    ftcoVo.setAdvanceOutMoney(lastFTCOVo.getAdvanceOutMoney() + ftcoVo.getAdvanceOutMoneyIncrease());      //设置期末预付
                }

                adVo.setOrderId(fundOrderVo.getId());
                adVo.setSubjectId("1123");
                adVo.setDebitMoney(money);
                adVo.setCreditMoney(0.0);
                financialAffairsUtil.addAccountingDocumentMethod(adVo);
                adVo.setSubjectId(fundOrderVo.getBankAccountId());
                adVo.setDebitMoney(0.0);
                adVo.setCreditMoney(money);
                financialAffairsUtil.addAccountingDocumentMethod(adVo);
                break;

            default:
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //设置初始属性
        fundOrderVo.setCreateTime(new Date());
        fundOrderVo.setOrderStatus((byte) 1);

        //新增收/付款单、预收/付款单
        if (myFundMapper.addFundOrder(fundOrderVo) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }

        //资金记账
        if (myFundMapper.addFundCheckOrder(fcoVo) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }

        //往来记账
        if (myFundMapper.addFundTargetCheckOrder(ftcoVo) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }

        return CommonResponse.success();
    }


    /**
     * 红冲收/付款单、预收/付款单
     * @param fundOrderVo
     * @return
     */
    @Transactional
    public CommonResponse redDashed(FundOrderVo fundOrderVo) {
        //获取参数
        Integer storeId = fundOrderVo.getStoreId();
        String userId = fundOrderVo.getUserId();
        String remark = fundOrderVo.getRemark();

        //红冲
        if (myFundMapper.redDashed(fundOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //获取红冲蓝单
        fundOrderVo = myFundMapper.findFundOrder(fundOrderVo).get(0);

        //设置红冲红单
        fundOrderVo.setStoreId(storeId);
        fundOrderVo.setOrderStatus((byte) 0);
        fundOrderVo.setDiscountMoney(-fundOrderVo.getDiscountMoney());
        fundOrderVo.setAdvanceMoney(-fundOrderVo.getAdvanceMoney());
        fundOrderVo.setMoney(-fundOrderVo.getMoney());

        //判断之前是否使用预收/付款
        if (fundOrderVo.getAdvanceMoney() != 0) {
            //判断红冲类型
            byte type = fundOrderVo.getType();
            switch (type) {
                case 1:     //收款单
                    //修改结算单据结算状态和金额
                    if (mySellMapper.updateApplyOrderClearStatusAndMoney(fundOrderVo) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    break;

                case 2:     //付款单
                    //修改结算单据结算状态和金额
                    if (myProcurementMapper.updateApplyOrderClearStatusAndMoney(fundOrderVo) != 1) {
                        return CommonResponse.error(CommonResponse.UPDATE_ERROR);
                    }
                    break;

                case 3:     //预收款单
                    break;

                case 4:     //预付款单
                    break;

                default:
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
        }

        //红冲资金记账记录
        fundUtil.redDashedFundCheckOrderMethod(new FundCheckOrderVo(storeId, fundOrderVo.getId(), userId));

        //红冲往来记账记录
        fundUtil.redDashedFundTargetCheckOrderMethod(new FundTargetCheckOrderVo(storeId, fundOrderVo.getId(), userId));

        //红冲会计凭证
        financialAffairsUtil.redDashedAccountingDocumentMethod(new AccountingDocumentVo(storeId, fundOrderVo.getId(), userId));

        //新增红冲红单
        fundOrderVo.setId("HC-" + fundOrderVo.getId());
        fundOrderVo.setCreateTime(new Date());
        fundOrderVo.setOrderStatus((byte) -2);
        fundOrderVo.setUserId(userId);
        fundOrderVo.setRemark(remark);
        if (myFundMapper.addFundOrder(fundOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        return CommonResponse.success();
    }


    /**
     * 根据type查询收/付款单、预收/付款单
     * @param fundOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllFundOrder(FundOrderVo fundOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myFundMapper.findCountFundOrder(fundOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<FundOrderVo> fundOrderVos = myFundMapper.findAllPagedFundOrder(fundOrderVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "id"));
        titles.add(new Title("单据类型", "type"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("往来单位编号", "targetId"));
        titles.add(new Title("往来单位名称", "targetName"));
        byte type = fundOrderVo.getType();
        if (type == 1 || type == 2) {
            titles.add(new Title("结算单据编号", "orderId"));
            titles.add(new Title("优惠金额", "discountMoney"));
            titles.add(new Title("使用预收/付款", "advanceMoney"));
        }
        titles.add(new Title("金额", "money"));
        titles.add(new Title("银行账户编号", "bankAccountId"));
        titles.add(new Title("银行账户名称", "bankAccount"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, fundOrderVos, pageVo);

        return CommonResponse.success(commonResult);
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 根据type导出收/付款单、预收/付款单
     * @param fundOrderVo
     * @param response
     */
    public void exportFundOrder(FundOrderVo fundOrderVo, HttpServletResponse response) {
        try {
            //获取参数
            Byte type = fundOrderVo.getType();

            //根据条件查询单据
            List<FundOrderVo> vos = myFundMapper.findAllFundOrder(fundOrderVo);

            //备注
            String remark = "【筛选条件】" +
                    "\n单据编号：" + (fundOrderVo.getId() == null ? "无" : fundOrderVo.getId()) +
                    " 开始时间：" + (fundOrderVo.getStartTime() == null ? "无" : fundOrderVo.getStartTime()) +
                    " 结束时间：" + (fundOrderVo.getEndTime() == null ? "无" : fundOrderVo.getEndTime()) +
                    " 往来单位名称：" + (fundOrderVo.getTargetName() == null ? "无" : fundOrderVo.getTargetName());

            //标题行
            List<String> titleRowCell1 = Arrays.asList(new String[]{
                    "单据编号", "单据类型", "单据日期", "往来单位编号", "往来单位名称", "被结算单据", "优惠金额", "使用预收/付款", "金额", "银行账户编号", "银行账户名称", "经手人", "备注"
            });

            List<String> titleRowCell2 = Arrays.asList(new String[]{
                    "单据编号", "单据类型", "单据日期", "往来单位编号", "往来单位名称", "金额", "银行账户编号", "银行账户名称", "经手人", "备注"
            });

            //最后一个必填列列数
            int lastRequiredCol = -1;

            String typeName = type == 1 ? "收款单" : type == 2 ? "付款单" : type == 3 ? "预收款单" : type == 4 ? "预付款单" : null;

            //数据行
            List<List<String>> dataRowCells = new ArrayList<>();
            vos.stream().forEach(vo -> {
                List<String> dataRowCell = new ArrayList<>();
                dataRowCell.add(vo.getId());
                dataRowCell.add(typeName);
                dataRowCell.add(sdf.format(vo.getCreateTime()));
                dataRowCell.add(vo.getTargetId());
                dataRowCell.add(vo.getTargetName());
                if (type == 1 || type == 2) {
                    dataRowCell.add(vo.getOrderId());
                    dataRowCell.add(vo.getDiscountMoney().toString());
                    dataRowCell.add(vo.getAdvanceMoney().toString());
                }
                dataRowCell.add(vo.getMoney().toString());
                dataRowCell.add(vo.getBankAccountId());
                dataRowCell.add(vo.getBankAccount());
                dataRowCell.add(vo.getUserName());
                dataRowCell.add(vo.getRemark());
                if (vo.getOrderStatus() < 1) {
                    dataRowCell.add(vo.getOrderStatus() == -1 ? "红冲蓝单" : vo.getOrderStatus() == -2 ? "红冲红单" : null);
                }
                dataRowCells.add(dataRowCell);
            });

            //输出excel
            String fileName = "【" + typeName + "导出】_" + System.currentTimeMillis() + ".xls";
            CommonUtil.outputExcelMethod(remark, type == 1 || type == 2 ? titleRowCell1 : titleRowCell2, lastRequiredCol, dataRowCells, fileName, response);
        } catch (Exception e) {
            throw new CommonException(CommonResponse.EXPORT_ERROR, e.getMessage());
        }
    }

    //期初设置

    /**
     * 现金银行期初设置查询
     * @param bankAccountVo
     * @param pageVo
     * @return
     */
    public CommonResponse findBankAccountOpening(BankAccountVo bankAccountVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myBankAccountMapper.findCount(bankAccountVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<BankAccountVo> bankAccountVos = myBankAccountMapper.findAllPaged(bankAccountVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("科目编号", "id"));
        titles.add(new Title("科目名称", "name"));
        titles.add(new Title("期初金额", "openingMoney"));
        CommonResult commonResult = new CommonResult(titles, bankAccountVos, pageVo);
        return CommonResponse.success(commonResult);
    }

    /**
     * 现金银行期初设置
     * @param bankAccountVo
     * @return
     */
    @Transactional
    public CommonResponse updateBankAccountOpening(BankAccountVo bankAccountVo) {
        //判断参数
        if (bankAccountVo.getOpeningMoney() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //判断系统是否开账
        if (systemUtil.judgeStartBillMethod(bankAccountVo.getStoreId())) {
            throw new CommonException(CommonResponse.UPDATE_ERROR, "系统已开账");
        }

        //设置期初
        if (myBankAccountMapper.updateOpening(bankAccountVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        return CommonResponse.success();
    }

    /**
     * 应收期初设置查询
     * @param storeClientVo
     * @param pageVo
     * @return
     */
    @Transactional
    public CommonResponse findNeedInOpening(StoreClientVo storeClientVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myClientMapper.findCountStoreClient(storeClientVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<StoreClientVo> vos = myClientMapper.findPagedStoreClient(storeClientVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("客户编号", "clientId"));
        titles.add(new Title("客户名称", "clientName"));
        titles.add(new Title("预收款期初", "advanceInMoneyOpening"));
        titles.add(new Title("应收款期初", "needInMoneyOpening"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);
        return CommonResponse.success(commonResult);
    }

    /**
     * 应收期初设置
     * @param storeClient
     * @return
     */
    @Transactional
    public CommonResponse updateNeedInOpening(StoreClient storeClient) {
        //判断参数
        if (storeClient.getStoreId() == null || storeClient.getClientId() == null ||
                storeClient.getAdvanceInMoneyOpening() == null || storeClient.getNeedInMoneyOpening() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //判断系统是否开账
        if (systemUtil.judgeStartBillMethod(storeClient.getStoreId())) {
            throw new CommonException(CommonResponse.UPDATE_ERROR, "系统已开账");
        }

        //设置期初
        if (myClientMapper.updateStoreClientOpening(storeClient) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        return CommonResponse.success();
    }

    /**
     * 应付期初设置查询
     * @param supplierVo
     * @param pageVo
     * @return
     */
    @Transactional
    public CommonResponse findNeedOutOpening(SupplierVo supplierVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(mySupplierMapper.findCount(supplierVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<SupplierVo> vos = mySupplierMapper.findAllPaged(supplierVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("供应商编号", "id"));
        titles.add(new Title("供应商名称", "name"));
        titles.add(new Title("预付款期初", "advanceOutMoneyOpening"));
        titles.add(new Title("应付款期初", "needOutMoneyOpening"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);
        return CommonResponse.success(commonResult);
    }

    /**
     * 应付期初设置
     * @param supplierVo
     * @return
     */
    @Transactional
    public CommonResponse updateNeedOutOpening(SupplierVo supplierVo) {
        //判断参数
        if (supplierVo.getStoreId() == null || supplierVo.getId() == null ||
                supplierVo.getAdvanceOutMoneyOpening() == null || supplierVo.getNeedOutMoneyOpening() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //判断系统是否开账
        if (systemUtil.judgeStartBillMethod(supplierVo.getStoreId())) {
            throw new CommonException(CommonResponse.UPDATE_ERROR, "系统已开账");
        }

        //设置期初
        if (mySupplierMapper.updateSupplierOpening(supplierVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        return CommonResponse.success();
    }

    //资金对账

    /**
     * 查资金-资金余额
     * @param fundCheckOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findFundCheckOrderBalanceMoney(FundCheckOrderVo fundCheckOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myFundMapper.findCountFundCheckOrderBalanceMoney(fundCheckOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<BankAccountVo> vos = myFundMapper.findPagedFundCheckOrderBalanceMoney(fundCheckOrderVo, pageVo);

        vos.stream().forEach(vo -> {
            fundCheckOrderVo.setBankAccountId(vo.getId());
            //期初金额
            FundCheckOrderVo lastVo = myFundMapper.findFirstBalanceMoney(fundCheckOrderVo);
            vo.setOpeningMoney(lastVo != null ? lastVo.getBalanceMoney() : 0);

            //期末余额
            lastVo = myFundMapper.findLastBalanceMoney(fundCheckOrderVo);
            vo.setBalanceMoney(lastVo != null ? lastVo.getBalanceMoney() : 0);
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("科目编号", "id"));
        titles.add(new Title("科目名称", "name"));
        titles.add(new Title("期初金额", "openingMoney"));
        titles.add(new Title("借方发生额", "inMoney"));
        titles.add(new Title("贷方发生额", "outMoney"));
        titles.add(new Title("期末余额", "balanceMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);
        return CommonResponse.success(commonResult);
    }

    /**
     * 查资金-资金流水/资金对账
     * @param fundCheckOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findFundCheckOrder(FundCheckOrderVo fundCheckOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myFundMapper.findCountFundCheckOrder(fundCheckOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<FundCheckOrderVo> fundCheckOrderVos = myFundMapper.findAllPagedFundCheckOrder(fundCheckOrderVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "orderId"));
        titles.add(new Title("单据类型", "typeName"));
        titles.add(new Title("创建时间", "createTime"));
        titles.add(new Title("往来单位编号", "targetId"));
        titles.add(new Title("往来单位名称", "targetName"));
        titles.add(new Title("结算单据", "applyOrderId"));
        titles.add(new Title("账户", "bankAccount"));
        titles.add(new Title("收入", "inMoney"));
        titles.add(new Title("支出", "outMoney"));
        titles.add(new Title("当前余额", "balanceMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, fundCheckOrderVos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //往来对账

    /**
     * 往来对账-查应收-按往来单位
     * @param fundTargetCheckOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findFundTargetCheckOrderNeedInByClient(FundTargetCheckOrderVo fundTargetCheckOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myFundMapper.findCountFundTargetCheckOrderNeedInByClient(fundTargetCheckOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<FundTargetCheckOrderVo> vos = myFundMapper.findPagedFundTargetCheckOrderNeedInByClient(fundTargetCheckOrderVo, pageVo);

        vos.stream().forEach(vo -> {
            //期初应收、期初预收
            fundTargetCheckOrderVo.setTargetId(vo.getTargetId());
            fundTargetCheckOrderVo.setFlag(1);
            FundTargetCheckOrderVo lastVo = myFundMapper.findLastFundTargetCheckOrder(fundTargetCheckOrderVo);
            if (lastVo != null) {
                vo.setNeedInMoneyOpening(lastVo.getNeedInMoney());
                vo.setAdvanceInMoneyOpening(lastVo.getAdvanceInMoney());
            }

            //期末应收、期末预收
            fundTargetCheckOrderVo.setFlag(2);
            lastVo = myFundMapper.findLastFundTargetCheckOrder(fundTargetCheckOrderVo);
            if (lastVo != null) {
                vo.setNeedInMoney(lastVo.getNeedInMoney());
                vo.setAdvanceInMoney(lastVo.getAdvanceInMoney());
            }

            //期末余额
            vo.setEndingMoney(vo.getNeedInMoney() - vo.getAdvanceInMoney());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("往来单位编号", "targetId"));
        titles.add(new Title("往来单位名称", "targetName"));
        titles.add(new Title("往来单位电话", "targetPhone"));
        titles.add(new Title("期初应收", "needInMoneyOpening"));
        titles.add(new Title("应收增加", "needInMoneyIncrease"));
        titles.add(new Title("应收减少", "needInMoneyDecrease"));
        titles.add(new Title("期末应收", "needInMoney"));
        titles.add(new Title("期初预收", "advanceInMoneyOpening"));
        titles.add(new Title("预收增加", "advanceInMoneyIncrease"));
        titles.add(new Title("预收减少", "advanceInMoneyDecrease"));
        titles.add(new Title("期末预收", "advanceInMoney"));
        titles.add(new Title("期末余额", "endingMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 往来对账-查应收-按往来单位-对账到单据
     * @param fundTargetCheckOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findFundTargetCheckOrderNeedInByClientOrder(FundTargetCheckOrderVo fundTargetCheckOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myFundMapper.findCountFundTargetCheckOrderNeedInByClientOrder(fundTargetCheckOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<FundTargetCheckOrderVo> vos = myFundMapper.findPagedFundTargetCheckOrderNeedInByClientOrder(fundTargetCheckOrderVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "orderId"));
        titles.add(new Title("单据类型", "typeName"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("被结算单据", "clearOrderId"));
        titles.add(new Title("来源单据", "applyOrderId"));
        titles.add(new Title("应收增加", "needInMoneyIncrease"));
        titles.add(new Title("应收减少", "needInMoneyDecrease"));
        titles.add(new Title("期末应收", "needInMoney"));
        titles.add(new Title("预收增加", "advanceInMoneyIncrease"));
        titles.add(new Title("预收减少", "advanceInMoneyDecrease"));
        titles.add(new Title("期末预收", "advanceInMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 往来对账-查应付-按往来单位
     * @param fundTargetCheckOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findFundTargetCheckOrderNeedOutByClient(FundTargetCheckOrderVo fundTargetCheckOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myFundMapper.findCountFundTargetCheckOrderNeedOutByClient(fundTargetCheckOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<FundTargetCheckOrderVo> vos = myFundMapper.findPagedFundTargetCheckOrderNeedOutByClient(fundTargetCheckOrderVo, pageVo);

        vos.stream().forEach(vo -> {
            //期初应付、期初预付
            fundTargetCheckOrderVo.setTargetId(vo.getTargetId());
            fundTargetCheckOrderVo.setFlag(1);
            FundTargetCheckOrderVo lastVo = myFundMapper.findLastFundTargetCheckOrder(fundTargetCheckOrderVo);
            if (lastVo != null) {
                vo.setNeedOutMoneyOpening(lastVo.getNeedOutMoney());
                vo.setAdvanceOutMoneyOpening(lastVo.getAdvanceOutMoney());
            }

            //期末应付、期末预付
            fundTargetCheckOrderVo.setFlag(2);
            lastVo = myFundMapper.findLastFundTargetCheckOrder(fundTargetCheckOrderVo);
            if (lastVo != null) {
                vo.setNeedOutMoney(lastVo.getNeedOutMoney());
                vo.setAdvanceOutMoney(lastVo.getAdvanceOutMoney());
            }

            //期末余额
            vo.setEndingMoney(vo.getNeedOutMoney() - vo.getAdvanceOutMoney());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("往来单位编号", "targetId"));
        titles.add(new Title("往来单位名称", "targetName"));
        titles.add(new Title("往来单位电话", "targetPhone"));
        titles.add(new Title("期初应付", "needOutMoneyOpening"));
        titles.add(new Title("应付增加", "needOutMoneyIncrease"));
        titles.add(new Title("应付减少", "needOutMoneyDecrease"));
        titles.add(new Title("期末应付", "needOutMoney"));
        titles.add(new Title("期初预付", "advanceOutMoneyOpening"));
        titles.add(new Title("预付增加", "advanceOutMoneyIncrease"));
        titles.add(new Title("预付减少", "advanceOutMoneyDecrease"));
        titles.add(new Title("期末预付", "advanceOutMoney"));
        titles.add(new Title("期末余额", "endingMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 往来对账-查应付-按往来单位-对账到单据
     * @param fundTargetCheckOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findFundTargetCheckOrderNeedOutByClientOrder(FundTargetCheckOrderVo fundTargetCheckOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myFundMapper.findCountFundTargetCheckOrderNeedOutByClientOrder(fundTargetCheckOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<FundTargetCheckOrderVo> vos = myFundMapper.findPagedFundTargetCheckOrderNeedOutByClientOrder(fundTargetCheckOrderVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "orderId"));
        titles.add(new Title("单据类型", "typeName"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("被结算单据", "clearOrderId"));
        titles.add(new Title("来源单据", "applyOrderId"));
        titles.add(new Title("应付增加", "needOutMoneyIncrease"));
        titles.add(new Title("应付减少", "needOutMoneyDecrease"));
        titles.add(new Title("期末应付", "needOutMoney"));
        titles.add(new Title("预付增加", "advanceOutMoneyIncrease"));
        titles.add(new Title("预付减少", "advanceOutMoneyDecrease"));
        titles.add(new Title("期末预付", "advanceOutMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 往来对账-职员部门应收款
     * @param fundTargetCheckOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findFundTargetCheckOrderNeedInByUser(FundTargetCheckOrderVo fundTargetCheckOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myFundMapper.findCountFundTargetCheckOrderNeedInByUser(fundTargetCheckOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<FundTargetCheckOrderVo> vos = myFundMapper.findPagedFundTargetCheckOrderNeedInByUser(fundTargetCheckOrderVo, pageVo);

        vos.stream().forEach(vo -> {
            //期初应收、期初预收
            fundTargetCheckOrderVo.setUserId(vo.getUserId());
            fundTargetCheckOrderVo.setFlag(1);
            FundTargetCheckOrderVo lastVo = myFundMapper.findLastFundTargetCheckOrder(fundTargetCheckOrderVo);
            if (lastVo != null) {
                vo.setNeedInMoneyOpening(lastVo.getNeedInMoney());
                vo.setAdvanceInMoneyOpening(lastVo.getAdvanceInMoney());
            }

            //期末应收、期末预收
            fundTargetCheckOrderVo.setFlag(2);
            lastVo = myFundMapper.findLastFundTargetCheckOrder(fundTargetCheckOrderVo);
            if (lastVo != null) {
                vo.setNeedInMoney(lastVo.getNeedInMoney());
                vo.setAdvanceInMoney(lastVo.getAdvanceInMoney());
            }

            //期末余额
            vo.setEndingMoney(vo.getNeedInMoney() - vo.getAdvanceInMoney());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("职员编号", "userId"));
        titles.add(new Title("职员名称", "userName"));
        titles.add(new Title("期初应收", "needInMoneyOpening"));
        titles.add(new Title("应收增加", "needInMoneyIncrease"));
        titles.add(new Title("应收减少", "needInMoneyDecrease"));
        titles.add(new Title("期末应收", "needInMoney"));
        titles.add(new Title("期初预收", "advanceInMoneyOpening"));
        titles.add(new Title("预收增加", "advanceInMoneyIncrease"));
        titles.add(new Title("预收减少", "advanceInMoneyDecrease"));
        titles.add(new Title("期末预收", "advanceInMoney"));
        titles.add(new Title("期末余额", "endingMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 往来对账-职员部门应付款
     * @param fundTargetCheckOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findFundTargetCheckOrderNeedOutByUser(FundTargetCheckOrderVo fundTargetCheckOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myFundMapper.findCountFundTargetCheckOrderNeedOutByUser(fundTargetCheckOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<FundTargetCheckOrderVo> vos = myFundMapper.findPagedFundTargetCheckOrderNeedOutByUser(fundTargetCheckOrderVo, pageVo);

        vos.stream().forEach(vo -> {
            //期初应付、期初预付
            fundTargetCheckOrderVo.setUserId(vo.getUserId());
            fundTargetCheckOrderVo.setFlag(1);
            FundTargetCheckOrderVo lastVo = myFundMapper.findLastFundTargetCheckOrder(fundTargetCheckOrderVo);
            if (lastVo != null) {
                vo.setNeedOutMoneyOpening(lastVo.getNeedOutMoney());
                vo.setAdvanceOutMoneyOpening(lastVo.getAdvanceOutMoney());
            }

            //期末应付、期末预付
            fundTargetCheckOrderVo.setFlag(2);
            lastVo = myFundMapper.findLastFundTargetCheckOrder(fundTargetCheckOrderVo);
            if (lastVo != null) {
                vo.setNeedOutMoney(lastVo.getNeedOutMoney());
                vo.setAdvanceOutMoney(lastVo.getAdvanceOutMoney());
            }

            //期末余额
            vo.setEndingMoney(vo.getNeedOutMoney() - vo.getAdvanceOutMoney());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("职员编号", "userId"));
        titles.add(new Title("职员名称", "userName"));
        titles.add(new Title("期初应付", "needOutMoneyOpening"));
        titles.add(new Title("应付增加", "needOutMoneyIncrease"));
        titles.add(new Title("应付减少", "needOutMoneyDecrease"));
        titles.add(new Title("期末应付", "needOutMoney"));
        titles.add(new Title("期初预付", "advanceOutMoneyOpening"));
        titles.add(new Title("预付增加", "advanceOutMoneyIncrease"));
        titles.add(new Title("预付减少", "advanceOutMoneyDecrease"));
        titles.add(new Title("期末预付", "advanceOutMoney"));
        titles.add(new Title("期末余额", "endingMoney"));
        CommonResult commonResult = new CommonResult(titles, vos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //其他收入单/费用单

    /**
     * 新增其他收入单/费用单
     * @param fundResultOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addFundResultOrder(FundResultOrderVo fundResultOrderVo) {
        //判断系统是否开账
        if (!systemUtil.judgeStartBillMethod(fundResultOrderVo.getStoreId())) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR, "系统未开账");
        }

        fundUtil.addFundResultOrderMethod(fundResultOrderVo);
        return CommonResponse.success();
    }

    /**
     * 红冲其他收入单/费用单
     * @param fundResultOrderVo
     * @return
     */
    @Transactional
    public CommonResponse redDashedResultOrder(FundResultOrderVo fundResultOrderVo) {
        //获取参数
        Integer storeId = fundResultOrderVo.getStoreId();
        String userId = fundResultOrderVo.getUserId();
        String remark = fundResultOrderVo.getRemark();

        //红冲
        if (myFundMapper.redDashedFundResultOrder(fundResultOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //获取红冲蓝单
        fundResultOrderVo = myFundMapper.findFundResultOrder(fundResultOrderVo).get(0);

        //红冲资金记账记录
        fundUtil.redDashedFundCheckOrderMethod(new FundCheckOrderVo(storeId, fundResultOrderVo.getId(), userId));

        //设置红冲红单
        fundResultOrderVo.setStoreId(storeId);
        fundResultOrderVo.setId("HC-" + fundResultOrderVo.getId());
        fundResultOrderVo.setCreateTime(new Date());
        fundResultOrderVo.setOrderStatus((byte) -2);
        fundResultOrderVo.setMoney(-fundResultOrderVo.getMoney());
        fundResultOrderVo.setUserId(userId);
        fundResultOrderVo.setRemark(remark);

        //新增红冲红单
        if (myFundMapper.addFundResultOrder(fundResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        return CommonResponse.success();
    }

    /**
     * 根据type查询其他收入单/费用单
     * @param fundResultOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllFundResultOrder(FundResultOrderVo fundResultOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myFundMapper.findCountFundResultOrder(fundResultOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<FundCheckOrderVo> fundCheckOrderVos = myFundMapper.findAllPagedFundResultOrder(fundResultOrderVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "id"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("往来单位编号", "targetId"));
        titles.add(new Title("往来单位名称", "targetName"));
        titles.add(new Title("银行账户编号", "bankAccountId"));
        titles.add(new Title("银行账户名称", "bankAccount"));
        titles.add(new Title("金额", "money"));
        titles.add(new Title("收支编号", "incomeExpensesId"));
        titles.add(new Title("收支名称", "incomeExpenses"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, fundCheckOrderVos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 根据type导出其他收入单/费用单
     * @param fundResultOrderVo
     * @param response
     */
    public void exportFundResultOrder(FundResultOrderVo fundResultOrderVo, HttpServletResponse response) {
        try {
            //获取参数
            Byte type = fundResultOrderVo.getType();

            //根据条件查询单据
            List<FundResultOrderVo> vos = myFundMapper.findAllFundResultOrder(fundResultOrderVo);

            //备注
            String remark = "【筛选条件】" +
                    "\n单据编号：" + (fundResultOrderVo.getId() == null ? "无" : fundResultOrderVo.getId()) +
                    " 开始时间：" + (fundResultOrderVo.getStartTime() == null ? "无" : fundResultOrderVo.getStartTime()) +
                    " 结束时间：" + (fundResultOrderVo.getEndTime() == null ? "无" : fundResultOrderVo.getEndTime()) +
                    " 往来单位名称：" + (fundResultOrderVo.getTargetName() == null ? "无" : fundResultOrderVo.getTargetName()) +
                    " 银行账户名称：" + (fundResultOrderVo.getBankAccountId() == null ? "无" : vos.get(0).getBankAccount()) +
                    " 收支名称名称" + (fundResultOrderVo.getIncomeExpensesId() == null ? "无" : vos.get(0).getIncomeExpenses());

            //标题行
            List<String> titleRowCell = Arrays.asList(new String[]{
                    "单据编号", "单据类型", "单据日期", "往来单位编号", "往来单位名称", "银行账户编号", "银行账户名称", "金额", "收支编号", "收支名称", "经手人", "备注"
            });

            //最后一个必填列列数
            int lastRequiredCol = -1;

            String typeName = type == 1 ? "其他收入单" : type == 2 ? "费用单" : null;

            //数据行
            List<List<String>> dataRowCells = new ArrayList<>();
            vos.stream().forEach(vo -> {
                List<String> dataRowCell = new ArrayList<>();
                dataRowCell.add(vo.getId());
                dataRowCell.add(typeName);
                dataRowCell.add(sdf.format(vo.getCreateTime()));
                dataRowCell.add(vo.getTargetId());
                dataRowCell.add(vo.getTargetName());
                dataRowCell.add(vo.getBankAccountId());
                dataRowCell.add(vo.getBankAccount());
                dataRowCell.add(vo.getMoney().toString());
                dataRowCell.add(vo.getIncomeExpensesId());
                dataRowCell.add(vo.getIncomeExpenses());
                dataRowCell.add(vo.getUserName());
                dataRowCell.add(vo.getRemark());
                if (vo.getOrderStatus() < 1) {
                    dataRowCell.add(vo.getOrderStatus() == -1 ? "红冲蓝单" : vo.getOrderStatus() == -2 ? "红冲红单" : null);
                }
                dataRowCells.add(dataRowCell);
            });

            //输出excel
            String fileName = "【" + typeName + "导出】_" + System.currentTimeMillis() + ".xls";
            CommonUtil.outputExcelMethod(remark, titleRowCell, lastRequiredCol, dataRowCells, fileName, response);
        } catch (Exception e) {
            throw new CommonException(CommonResponse.EXPORT_ERROR, e.getMessage());
        }
    }
}
