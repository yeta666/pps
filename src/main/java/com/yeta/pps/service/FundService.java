package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.*;
import com.yeta.pps.po.ProcurementApplyOrder;
import com.yeta.pps.util.*;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private FundUtil fundUtil;

    /**
     * 查询预收/付款余额
     * @param fundOrderVo
     * @return
     */
    public CommonResponse findAdvanceMoney(FundOrderVo fundOrderVo) {
        //获取参数
        Integer storeId = fundOrderVo.getStoreId();
        String targetId = fundOrderVo.getTargetId();
        byte type = fundOrderVo.getType();

        //预收/付款余额
        double advanceMoney = 0;

        //判断查询类型
        switch (type) {
            case 1:     //收款单
                StoreIntegralVo storeIntegralVo = myClientMapper.findStoreIntegralByStoreIdAndClientId(new StoreIntegralVo(storeId, targetId));
                if (storeIntegralVo != null) {
                    advanceMoney = storeIntegralVo.getAdvanceMoney();
                }
                break;

            case 2:     //付款单:
                SupplierVo supplierVo = mySupplierMapper.findById(new SupplierVo(storeId, targetId));
                if (supplierVo != null) {
                    advanceMoney = supplierVo.getAdvanceMoney();
                }
                break;

            default:
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
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
        byte type = fundOrderVo.getType();

        //判断新增类型
        switch (type) {
            case 1:     //收款单
                //判断参数
                if (fundOrderVo.getOrderId() == null || fundOrderVo.getDiscountMoney() == null || fundOrderVo.getAdvanceMoney() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }

                fundOrderVo.setId("SKD_" + UUID.randomUUID().toString().replace("-", ""));

                //获取结算单据
                SellApplyOrderVo sVo = mySellMapper.findApplyOrderById(new SellApplyOrderVo(storeId, fundOrderVo.getOrderId()));
                if (sVo == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }

                //判断结算金额
                judgeMoneyMethod(fundOrderVo, sVo, null);

                //修改结算单据结算状态和金额
                if (mySellMapper.updateApplyOrderClearStatusAndMoney(fundOrderVo) != 1) {
                    return CommonResponse.error(CommonResponse.ADD_ERROR);
                }

                //修改该客户在该店铺的预收款余额
                advanceMoney = fundOrderVo.getAdvanceMoney();
                if (advanceMoney != 0) {
                    if (myClientMapper.updateAdvanceMoney(new StoreIntegralVo(storeId, targetId, -advanceMoney)) != 1) {
                        throw new CommonException(CommonResponse.ADD_ERROR);
                    }
                }

                break;

            case 2:     //付款单
                //判断参数
                if (fundOrderVo.getOrderId() == null || fundOrderVo.getDiscountMoney() == null || fundOrderVo.getAdvanceMoney() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }

                fundOrderVo.setId("FKD_" + UUID.randomUUID().toString().replace("-", ""));

                //获取结算单据
                ProcurementApplyOrder procurementApplyOrder = myProcurementMapper.findApplyOrderById(new ProcurementApplyOrderVo(storeId, fundOrderVo.getOrderId()));
                if (procurementApplyOrder == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }

                //判断结算金额
                judgeMoneyMethod(fundOrderVo, null, procurementApplyOrder);

                //修改结算单据结算状态和金额
                if (myProcurementMapper.updateApplyOrderClearStatusAndMoney(fundOrderVo) != 1) {
                    return CommonResponse.error(CommonResponse.ADD_ERROR);
                }

                //修改该供应商的预付款余额
                advanceMoney = fundOrderVo.getAdvanceMoney();
                if (advanceMoney != 0) {
                    if (mySupplierMapper.updateAdvanceMoney(new SupplierVo(storeId, targetId, -advanceMoney)) != 1) {
                        throw new CommonException(CommonResponse.ADD_ERROR);
                    }
                }

                break;

            case 3:     //预收款单
                //判断参数
                if (money <= 0) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }

                fundOrderVo.setId("YSKD_" + UUID.randomUUID().toString().replace("-", ""));
                fundOrderVo.setDiscountMoney(0.0);
                fundOrderVo.setAdvanceMoney(0.0);

                //增加该客户在该店铺的预收款余额
                if (myClientMapper.findStoreIntegralByStoreIdAndClientId(new StoreIntegralVo(storeId, targetId)) != null) {
                    if (myClientMapper.updateAdvanceMoney(new StoreIntegralVo(storeId, targetId, money)) != 1) {
                        throw new CommonException(CommonResponse.ADD_ERROR);
                    }
                } else {
                    if (myClientMapper.addStoreIntegral(new StoreIntegralVo(storeId, targetId, 0, money)) != 1) {
                        throw new CommonException(CommonResponse.ADD_ERROR);
                    }
                }
                break;

            case 4:     //预付款单
                //判断参数
                if (money <= 0) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }

                fundOrderVo.setId("YFKD_" + UUID.randomUUID().toString().replace("-", ""));
                fundOrderVo.setDiscountMoney(0.0);
                fundOrderVo.setAdvanceMoney(0.0);

                //增加该供应商的预付款余额
                if (mySupplierMapper.updateAdvanceMoney(new SupplierVo(storeId, targetId, money)) != 1) {
                    throw new CommonException(CommonResponse.ADD_ERROR);
                }
                break;

            default:
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //设置初始属性
        fundOrderVo.setCreateTime(new Date());
        fundOrderVo.setOrderStatus((byte) 1);

        //新增收/付款单、预收/付款单
        if (myFundMapper.addFundOrder(fundOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }

        //记录资金对账记录
        FundCheckOrderVo vo = new FundCheckOrderVo();
        vo.setStoreId(storeId);
        vo.setOrderId(fundOrderVo.getId());
        vo.setCreateTime(new Date());
        vo.setOrderStatus((byte) 1);
        vo.setTargetId(fundOrderVo.getTargetId());
        vo.setBankAccountId(fundOrderVo.getBankAccountId());
        vo.setUserId(fundOrderVo.getUserId());
        vo.setRemark(fundOrderVo.getRemark());

        //查询最新的资金对账记录
        FundCheckOrderVo lastVo = myFundMapper.findLastBalanceMoney(vo);
        if (lastVo == null) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }

        if (type == 1 || type == 3) {       //收款单/预收款单
            vo.setInMoney(money);
            vo.setOutMoney(0.0);
            vo.setBalanceMoney(lastVo.getBalanceMoney() + money);
        } else if (type == 2 || type == 4) {        //付款单/预付款单
            vo.setInMoney(0.0);
            vo.setOutMoney(money);
            vo.setBalanceMoney(lastVo.getBalanceMoney() - money);
        }
        if (myFundMapper.addFundCheckOrder(vo) != 1) {
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
        fundOrderVo = myFundMapper.findFundOrderById(fundOrderVo);

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

                    //修改该客户在该店铺的预收款余额
                    if (myClientMapper.updateAdvanceMoney(new StoreIntegralVo(storeId, fundOrderVo.getTargetId(), -fundOrderVo.getAdvanceMoney())) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    break;

                case 2:     //付款单
                    //修改结算单据结算状态和金额
                    if (myProcurementMapper.updateApplyOrderClearStatusAndMoney(fundOrderVo) != 1) {
                        return CommonResponse.error(CommonResponse.UPDATE_ERROR);
                    }

                    //修改该供应商的预付款余额
                    if (mySupplierMapper.updateAdvanceMoney(new SupplierVo(storeId, fundOrderVo.getTargetId(), -fundOrderVo.getAdvanceMoney())) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    break;

                case 3:     //预收款单
                    //减少该客户在该店铺的预收款余额
                    if (myClientMapper.updateAdvanceMoney(new StoreIntegralVo(storeId, fundOrderVo.getTargetId(), fundOrderVo.getMoney())) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    break;

                case 4:     //预付款单
                    //减少该供应商的预付款余额
                    if (mySupplierMapper.updateAdvanceMoney(new SupplierVo(storeId, fundOrderVo.getTargetId(), fundOrderVo.getMoney())) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    break;

                default:
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
        }

        //红冲资金记账记录
        fundUtil.redDashedFundCheckOrderMethod(new FundCheckOrderVo(storeId, fundOrderVo.getId(), userId));

        //新增红冲红单
        fundOrderVo.setId("HC_" + fundOrderVo.getId());
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
     * 根据type查询所有收/付款单、预收/付款单
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
        titles.add(new Title("往来单位", "targetName"));
        byte type = fundOrderVo.getType();
        if (type == 1 || type == 2) {
            titles.add(new Title("结算单据编号", "orderId"));
            titles.add(new Title("优惠金额", "discountMoney"));
            titles.add(new Title("使用预收/付款", "advanceMoney"));
        }
        titles.add(new Title("金额", "money"));
        titles.add(new Title("银行账户", "bankAccount"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, fundOrderVos, pageVo);

        return CommonResponse.success(commonResult);
    }

    //期初设置

    /**
     * 现金银行期初设置
     * @param bankAccountVo
     * @return
     */
    @Transactional
    public CommonResponse updateOpening(BankAccountVo bankAccountVo) {
        //设置期初
        if (myBankAccountMapper.updateOpening(bankAccountVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //记录资金对账记录
        FundCheckOrderVo fundCheckOrderVo = new FundCheckOrderVo();
        fundCheckOrderVo.setStoreId(bankAccountVo.getStoreId());
        fundCheckOrderVo.setOrderId("期初调整");
        fundCheckOrderVo.setCreateTime(new Date());
        fundCheckOrderVo.setOrderStatus((byte) 1);
        fundCheckOrderVo.setBankAccountId(bankAccountVo.getId());
        fundCheckOrderVo.setInMoney(0.0);
        fundCheckOrderVo.setOutMoney(0.0);
        fundCheckOrderVo.setBalanceMoney(bankAccountVo.getOpeningMoney());
        if (myFundMapper.addFundCheckOrder(fundCheckOrderVo) != 1) {
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
    public CommonResponse findSumFundCheckOrder(FundCheckOrderVo fundCheckOrderVo, PageVo pageVo) {
        //查询所有页数
        BankAccountVo bankAccountVo = new BankAccountVo(fundCheckOrderVo.getStoreId());
        pageVo.setTotalPage((int) Math.ceil(myBankAccountMapper.findCount(bankAccountVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<BankAccountVo> bankAccountVos = myBankAccountMapper.findAllPaged(bankAccountVo, pageVo);

        //补上金额
        bankAccountVos.forEach(vo -> {
            fundCheckOrderVo.setBankAccountId(vo.getId());
            FundCheckOrderVo fVo = myFundMapper.findSumFundCheckOrder(fundCheckOrderVo);
            if (fVo != null) {
                vo.setInMoney(fVo.getInMoney());
                vo.setOutMoney(fVo.getOutMoney());
                vo.setBalanceMoney(fVo.getBalanceMoney());
            }
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("科目编号", "id"));
        titles.add(new Title("科目名称", "name"));
        titles.add(new Title("期初金额", "openingMoney"));
        titles.add(new Title("借方发生额", "inMoney"));
        titles.add(new Title("贷方发生额", "outMoney"));
        titles.add(new Title("期末余额", "balanceMoney"));
        CommonResult commonResult = new CommonResult(titles, bankAccountVos, pageVo);
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
        titles.add(new Title("往来单位", "targetName"));
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


    //其他收入单/费用单

    /**
     * 新增其他收入单/费用单
     * @param fundResultOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addFundResultOrder(FundResultOrderVo fundResultOrderVo) {

        //设置库存对账记录
        FundCheckOrderVo fundCheckOrderVo = new FundCheckOrderVo();
        fundCheckOrderVo.setStoreId(fundResultOrderVo.getStoreId());
        fundCheckOrderVo.setCreateTime(new Date());
        fundCheckOrderVo.setOrderStatus((byte) 1);
        fundCheckOrderVo.setTargetId(fundResultOrderVo.getTargetId());
        fundCheckOrderVo.setBankAccountId(fundResultOrderVo.getBankAccountId());

        //查询最新的资金对账记录
        FundCheckOrderVo lastVo = myFundMapper.findLastBalanceMoney(new FundCheckOrderVo(fundResultOrderVo.getStoreId(), null, null, fundResultOrderVo.getBankAccountId()));
        if (lastVo == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //判断新增类型
        byte type = fundResultOrderVo.getType();
        switch (type) {
            case 1:     //其他收入单:
                //判断参数
                if (fundResultOrderVo.getTargetId() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }

                fundResultOrderVo.setId("QTSRD_" + UUID.randomUUID().toString().replace("-", ""));

                fundCheckOrderVo.setOrderId(fundResultOrderVo.getId());
                fundCheckOrderVo.setInMoney(fundResultOrderVo.getMoney());
                fundCheckOrderVo.setOutMoney(0.0);
                fundCheckOrderVo.setBalanceMoney(lastVo.getBalanceMoney() + fundResultOrderVo.getMoney());
                break;

            case 2:     //费用单:
                fundResultOrderVo.setId("YBFYD_" + UUID.randomUUID().toString().replace("-", ""));

                fundCheckOrderVo.setOrderId(fundResultOrderVo.getId());
                fundCheckOrderVo.setInMoney(0.0);
                fundCheckOrderVo.setOutMoney(fundResultOrderVo.getMoney());
                fundCheckOrderVo.setBalanceMoney(lastVo.getBalanceMoney() - fundResultOrderVo.getMoney());
                break;

            default:
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //设置初始属性
        fundResultOrderVo.setCreateTime(new Date());
        fundResultOrderVo.setOrderStatus((byte) 1);

        //新增
        if (myFundMapper.addFundResultOrder(fundResultOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }

        //记录资金对账记录
        if (myFundMapper.addFundCheckOrder(fundCheckOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }
        
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
        fundResultOrderVo = myFundMapper.findFundResultOrderById(fundResultOrderVo);

        //红冲资金记账记录
        fundUtil.redDashedFundCheckOrderMethod(new FundCheckOrderVo(storeId, fundResultOrderVo.getId(), userId));


        //设置红冲红单
        fundResultOrderVo.setStoreId(storeId);
        fundResultOrderVo.setId("HC_" + fundResultOrderVo.getId());
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
     * 查询其他收入单/费用单
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
        titles.add(new Title("创建时间", "createTime"));
        titles.add(new Title("往来单位", "targetName"));
        titles.add(new Title("账户", "bankAccount"));
        titles.add(new Title("金额", "money"));
        titles.add(new Title("收支名称", "incomeExpenses"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, fundCheckOrderVos, pageVo);

        return CommonResponse.success(commonResult);
    }
}
