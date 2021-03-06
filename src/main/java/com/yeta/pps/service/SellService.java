package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.*;
import com.yeta.pps.po.GoodsSku;
import com.yeta.pps.po.SSystem;
import com.yeta.pps.po.Warehouse;
import com.yeta.pps.util.*;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 销售相关逻辑处理
 * @author YETA
 * @date 2018/12/10/15:04
 */
@Service
public class SellService {

    @Autowired
    private MySellMapper mySellMapper;

    @Autowired
    private MyOrderGoodsSkuMapper myOrderGoodsSkuMapper;

    @Autowired
    private MyWarehouseMapper myWarehouseMapper;

    @Autowired
    private MyGoodsMapper myGoodsMapper;

    @Autowired
    private MyClientMapper myClientMapper;

    @Autowired
    private MyFundMapper myFundMapper;

    @Autowired
    private InventoryUtil inventoryUtil;

    @Autowired
    private StoreClientUtil storeClientUtil;

    @Autowired
    private FundUtil fundUtil;

    @Autowired
    private SystemUtil systemUtil;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private FinancialAffairsUtil financialAffairsUtil;

    @Autowired
    private PrimaryKeyUtil primaryKeyUtil;

    //销售申请订单

    /**
     * 零售单
     * @param sellApplyOrderVo
     * @param sSystem
     */
    @Transactional
    public void lsd(SellApplyOrderVo sellApplyOrderVo, SSystem sSystem) {
        //获取参数
        Integer storeId = sellApplyOrderVo.getStoreId();
        List<OrderGoodsSkuVo> orderGoodsSkuVos = sellApplyOrderVo.getDetails();
        String userId = sellApplyOrderVo.getUserId();
        
        //判断参数
        if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getOutTotalQuantity() == null ||
                (sellApplyOrderVo.getAdvanceMoney() > 0 && sellApplyOrderVo.getClientId() == null) ||
                (sellApplyOrderVo.getDiscountCouponId() != null && sellApplyOrderVo.getClientId() == null)) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //判断收款金额
        if (sellApplyOrderVo.getCashMoney() +
                sellApplyOrderVo.getAlipayMoney() +
                sellApplyOrderVo.getWechatMoney() +
                sellApplyOrderVo.getBankCardMoney() +
                sellApplyOrderVo.getAdvanceMoney() != sellApplyOrderVo.getOrderMoney()) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR, "收款金额错误");
        }

        Integer warehouseId = sSystem.getRetailWarehouseId();
        String bankAccountId = sSystem.getRetailBankAccountId();

        //设置初始属性
        sellApplyOrderVo.setId(primaryKeyUtil.getOrderPrimaryKeyMethod(mySellMapper.findApplyOrderPrimaryKey(sellApplyOrderVo), "LSD"));
        sellApplyOrderVo.setCreateTime(new Date());
        sellApplyOrderVo.setOrderStatus((byte) 6);       //已发
        sellApplyOrderVo.setClearStatus((byte) 1);      //已完成
        sellApplyOrderVo.setOutSentQuantity(sellApplyOrderVo.getOutTotalQuantity());
        sellApplyOrderVo.setOutNotSentQuantity(0);
        sellApplyOrderVo.setClearedMoney(sellApplyOrderVo.getOrderMoney());
        sellApplyOrderVo.setNotClearedMoney(0.0);

        //判断优惠券金额
        if (sellApplyOrderVo.getDiscountCouponId() != null) {
            double discountCouponMoney = fundUtil.getDiscountCouponMoneyMethod(storeId, sellApplyOrderVo.getDiscountCouponId(), sellApplyOrderVo.getClientId(), sellApplyOrderVo.getTotalMoney());
            if (sellApplyOrderVo.getDiscountMoney() + discountCouponMoney != sellApplyOrderVo.getTotalDiscountMoney()) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }
        }

        //商品规格相关操作
        SellResultOrderVo sellResultOrderVo = new SellResultOrderVo(0.0);
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            //设置初始属性
            orderGoodsSkuVo.setStoreId(storeId);
            orderGoodsSkuVo.setOrderId(sellApplyOrderVo.getId());
            orderGoodsSkuVo.setFinishQuantity(orderGoodsSkuVo.getQuantity());
            orderGoodsSkuVo.setNotFinishQuantity(0);
            orderGoodsSkuVo.setOperatedQuantity(0);

            //减库存
            inventoryUtil.updateInventoryMethod((byte) 0, new WarehouseGoodsSkuVo(storeId, warehouseId, orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getQuantity(), orderGoodsSkuVo.getQuantity(), orderGoodsSkuVo.getQuantity()));

            //库存记账
            StorageCheckOrderVo storageCheckOrderVo = new StorageCheckOrderVo();
            storageCheckOrderVo.setStoreId(storeId);
            storageCheckOrderVo.setOrderId(sellApplyOrderVo.getId());
            storageCheckOrderVo.setTargetId(sellApplyOrderVo.getClientId());
            storageCheckOrderVo.setCreateTime(new Date());
            storageCheckOrderVo.setOrderStatus((byte) 1);
            storageCheckOrderVo.setGoodsSkuId(orderGoodsSkuVo.getGoodsSkuId());
            storageCheckOrderVo.setWarehouseId(warehouseId);
            storageCheckOrderVo.setOutQuantity(orderGoodsSkuVo.getQuantity());
            storageCheckOrderVo.setUserId(userId);
            double costMoney = inventoryUtil.addStorageCheckOrderMethod(0, storageCheckOrderVo, null);
            sellResultOrderVo.setCostMoney(sellResultOrderVo.getCostMoney() + costMoney * orderGoodsSkuVo.getQuantity());

            //新增申请单/商品规格关系
            if (myOrderGoodsSkuMapper.addOrderGoodsSku(orderGoodsSkuVo) != 1) {
                throw new CommonException(CommonResponse.ADD_ERROR);
            }
        });

        //新增结果单
        sellResultOrderVo.setStoreId(storeId);
        sellResultOrderVo.setId(sellApplyOrderVo.getId());
        sellResultOrderVo.setType((byte) 1);
        sellResultOrderVo.setCreateTime(new Date());
        sellResultOrderVo.setApplyOrderId(sellApplyOrderVo.getId());
        sellResultOrderVo.setOrderStatus((byte) 1);
        sellResultOrderVo.setTotalQuantity(sellApplyOrderVo.getOutTotalQuantity());
        sellResultOrderVo.setTotalMoney(sellApplyOrderVo.getTotalMoney());
        sellResultOrderVo.setTotalDiscountMoney(sellApplyOrderVo.getTotalDiscountMoney());
        sellResultOrderVo.setOrderMoney(sellApplyOrderVo.getOrderMoney());
        sellResultOrderVo.setGrossMarginMoney(sellResultOrderVo.getOrderMoney() - sellResultOrderVo.getCostMoney());
        sellResultOrderVo.setUserId(userId);
        sellResultOrderVo.setRemark(sellApplyOrderVo.getRemark());
        if (mySellMapper.addResultOrder(sellResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }

        //增加客户积分和该客户邀请人的提成
        if (sellApplyOrderVo.getClientId() != null) {
            //增加客户积分
            List<GoodsSku> goodsSkus = myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(storeId));
            storeClientUtil.updateIntegralMethod(1, storeId, sellApplyOrderVo.getClientId(), sellResultOrderVo.getId(), goodsSkus, orderGoodsSkuVos, userId);

            //增加该客户邀请人提成
            storeClientUtil.updatePushMoneyMethod(1, storeId, sellApplyOrderVo.getClientId(), sellResultOrderVo.getId(), userId, sellResultOrderVo.getOrderMoney());
        }

        //资金记账
        FundCheckOrderVo vo = new FundCheckOrderVo();
        vo.setStoreId(storeId);
        vo.setOrderId(sellApplyOrderVo.getId());
        vo.setCreateTime(new Date());
        vo.setOrderStatus((byte) 1);
        vo.setTargetId(sellApplyOrderVo.getClientId());
        vo.setUserId(userId);

        //新增会计凭证
        AccountingDocumentVo adVo = new AccountingDocumentVo();
        adVo.setStoreId(storeId);
        adVo.setTargetId(sellApplyOrderVo.getClientId());
        adVo.setUserId(userId);
        adVo.setOrderId(sellApplyOrderVo.getId());

        //现金收款
        double cashMoney = sellApplyOrderVo.getCashMoney();
        if (cashMoney > 0) {
            vo.setBankAccountId("1001");
            inMoneyMethod(vo, cashMoney);

            adVo.setSubjectId("1001");
            adVo.setDebitMoney(cashMoney);
            adVo.setCreditMoney(0.0);
            financialAffairsUtil.addAccountingDocumentMethod(adVo);
        }

        //支付宝收款
        double alipayMoney = sellApplyOrderVo.getAlipayMoney();
        if (alipayMoney > 0) {
            vo.setBankAccountId("1002");
            inMoneyMethod(vo, alipayMoney);

            adVo.setSubjectId("1002");
            adVo.setDebitMoney(alipayMoney);
            adVo.setCreditMoney(0.0);
            financialAffairsUtil.addAccountingDocumentMethod(adVo);
        }

        //微信收款
        double wechatMoney = sellApplyOrderVo.getWechatMoney();
        if (wechatMoney > 0) {
            vo.setBankAccountId("1003");
            inMoneyMethod(vo, wechatMoney);

            adVo.setSubjectId("1003");
            adVo.setDebitMoney(wechatMoney);
            adVo.setCreditMoney(0.0);
            financialAffairsUtil.addAccountingDocumentMethod(adVo);
        }

        //银行卡收款
        double bankCardMoney = sellApplyOrderVo.getBankCardMoney();
        if (bankCardMoney > 0) {
            vo.setBankAccountId(bankAccountId);
            inMoneyMethod(vo, bankCardMoney);

            adVo.setSubjectId(bankAccountId);
            adVo.setDebitMoney(bankCardMoney);
            adVo.setCreditMoney(0.0);
            financialAffairsUtil.addAccountingDocumentMethod(adVo);
        }

        //预收款收款
        double advanceMoney = sellApplyOrderVo.getAdvanceMoney();
        if (advanceMoney > 0) {
            //往来记账
            FundTargetCheckOrderVo ftcoVo = new FundTargetCheckOrderVo();
            ftcoVo.setStoreId(storeId);
            ftcoVo.setOrderId(sellApplyOrderVo.getId());
            ftcoVo.setCreateTime(new Date());
            ftcoVo.setOrderStatus((byte) 1);
            ftcoVo.setTargetId(sellApplyOrderVo.getClientId());
            ftcoVo.setUserId(userId);
            ftcoVo.setAdvanceInMoneyDecrease(advanceMoney);     //设置预收减少
            FundTargetCheckOrderVo lastFTCOVo = myFundMapper.findLastFundTargetCheckOrder(ftcoVo);
            if (lastFTCOVo == null || lastFTCOVo.getAdvanceInMoney() < advanceMoney) {
                throw new CommonException(CommonResponse.ADD_ERROR, "使用预收款金额错误");
            }
            ftcoVo.setNeedInMoney(lastFTCOVo.getNeedInMoney());       //设置期末应收
            ftcoVo.setAdvanceInMoney(lastFTCOVo.getAdvanceInMoney() - ftcoVo.getAdvanceInMoneyDecrease());      //设置期末预收
            if (myFundMapper.addFundTargetCheckOrder(ftcoVo) != 1) {
                throw new CommonException(CommonResponse.ADD_ERROR);
            }

            adVo.setSubjectId("2203");
            adVo.setDebitMoney(advanceMoney);
            adVo.setCreditMoney(0.0);
            financialAffairsUtil.addAccountingDocumentMethod(adVo);
        }

        adVo.setSubjectId("6401");
        adVo.setDebitMoney(sellResultOrderVo.getCostMoney());
        adVo.setCreditMoney(0.0);
        financialAffairsUtil.addAccountingDocumentMethod(adVo);
        adVo.setSubjectId("1405");
        adVo.setDebitMoney(0.0);
        adVo.setCreditMoney(sellResultOrderVo.getCostMoney());
        financialAffairsUtil.addAccountingDocumentMethod(adVo);
        adVo.setSubjectId("6001");
        adVo.setDebitMoney(0.0);
        adVo.setCreditMoney(sellResultOrderVo.getOrderMoney());
        financialAffairsUtil.addAccountingDocumentMethod(adVo);
    }

    /**
     * 收款的方法
     * @param vo
     * @param money
     */
    @Transactional
    public void inMoneyMethod(FundCheckOrderVo vo, double money) {
        FundCheckOrderVo lastVo = myFundMapper.findLastBalanceMoney(vo);
        if (lastVo == null) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }
        vo.setInMoney(money);
        vo.setOutMoney(0.0);
        vo.setBalanceMoney(lastVo.getBalanceMoney() + money);
        if (myFundMapper.addFundCheckOrder(vo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }
    }

    /**
     * 销售订单
     * @param sellApplyOrderVo
     */
    @Transactional
    public void xsdd(SellApplyOrderVo sellApplyOrderVo) {
        //获取参数
        Integer storeId = sellApplyOrderVo.getStoreId();
        List<OrderGoodsSkuVo> orderGoodsSkuVos = sellApplyOrderVo.getDetails();

        //判断参数
        if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getOutWarehouseId() == null || sellApplyOrderVo.getOutTotalQuantity() == null || sellApplyOrderVo.getClientId() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //设置初始属性
        sellApplyOrderVo.setId(primaryKeyUtil.getOrderPrimaryKeyMethod(mySellMapper.findApplyOrderPrimaryKey(sellApplyOrderVo), "XSDD"));
        sellApplyOrderVo.setCreateTime(new Date());
        sellApplyOrderVo.setOrderStatus((byte) 4);       //未发
        sellApplyOrderVo.setClearStatus((byte) 0);      //未完成
        sellApplyOrderVo.setOutSentQuantity(0);
        sellApplyOrderVo.setOutNotSentQuantity(sellApplyOrderVo.getOutTotalQuantity());
        sellApplyOrderVo.setClearedMoney(0.0);
        sellApplyOrderVo.setNotClearedMoney(sellApplyOrderVo.getOrderMoney());

        //判断优惠券金额
        if (sellApplyOrderVo.getDiscountCouponId() != null) {
            double discountCouponMoney = fundUtil.getDiscountCouponMoneyMethod(storeId, sellApplyOrderVo.getDiscountCouponId(), sellApplyOrderVo.getClientId(), sellApplyOrderVo.getTotalMoney());
            if (sellApplyOrderVo.getDiscountMoney() + discountCouponMoney != sellApplyOrderVo.getTotalDiscountMoney()) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }
        }

        //商品规格相关操作
        inventoryMethod(sellApplyOrderVo, null);
    }

    /**
     * 销售退货申请单
     * @param sellApplyOrderVo
     */
    @Transactional
    public void xsthsqd(SellApplyOrderVo sellApplyOrderVo) {
        //获取参数
        Integer storeId = sellApplyOrderVo.getStoreId();
        List<OrderGoodsSkuVo> orderGoodsSkuVos = sellApplyOrderVo.getDetails();

        //判断参数
        if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getInWarehouseId() == null || sellApplyOrderVo.getInTotalQuantity() == null ||
                sellApplyOrderVo.getClientId() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //已红冲的销售出库单不能退换货
        if (sellApplyOrderVo.getResultOrderId() != null) {
            SellResultOrderVo srVo = mySellMapper.findResultOrderDetailById(new SellResultOrderVo(storeId, sellApplyOrderVo.getResultOrderId()));
            if (srVo == null || srVo.getOrderStatus() < 0) {
                throw new CommonException(CommonResponse.ADD_ERROR, CommonResponse.STATUS_ERROR);
            }
        }

        //设置初始属性
        sellApplyOrderVo.setId(primaryKeyUtil.getOrderPrimaryKeyMethod(mySellMapper.findApplyOrderPrimaryKey(sellApplyOrderVo), "XSTHSQD"));
        sellApplyOrderVo.setCreateTime(new Date());
        sellApplyOrderVo.setOrderStatus((byte) 1);       //未收
        sellApplyOrderVo.setClearStatus((byte) 0);      //未完成
        sellApplyOrderVo.setInReceivedQuantity(0);
        sellApplyOrderVo.setInNotReceivedQuantity(sellApplyOrderVo.getInTotalQuantity());
        sellApplyOrderVo.setClearedMoney(0.0);
        sellApplyOrderVo.setNotClearedMoney(sellApplyOrderVo.getOrderMoney());

        //商品规格相关操作
        inventoryMethod(sellApplyOrderVo, null);
    }

    /**
     * 销售换货申请单
     * @param sellApplyOrderVo
     */
    @Transactional
    public void xshhsqd(SellApplyOrderVo sellApplyOrderVo) {
        //获取参数
        Integer storeId = sellApplyOrderVo.getStoreId();
        List<OrderGoodsSkuVo> orderGoodsSkuVos = sellApplyOrderVo.getDetails();

        //判断参数
        if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getInWarehouseId() == null || sellApplyOrderVo.getInTotalQuantity() == null ||
                sellApplyOrderVo.getOutWarehouseId() == null || sellApplyOrderVo.getOutTotalQuantity() == null ||
                sellApplyOrderVo.getClientId() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //已红冲的销售出库单不能退换货
        if (sellApplyOrderVo.getResultOrderId() != null) {
            SellResultOrderVo srVo = mySellMapper.findResultOrderDetailById(new SellResultOrderVo(storeId, sellApplyOrderVo.getResultOrderId()));
            if (srVo == null || srVo.getOrderStatus() < 0) {
                throw new CommonException(CommonResponse.ADD_ERROR, CommonResponse.STATUS_ERROR);
            }
        }

        //设置初始属性
        sellApplyOrderVo.setId(primaryKeyUtil.getOrderPrimaryKeyMethod(mySellMapper.findApplyOrderPrimaryKey(sellApplyOrderVo), "XSHHSQD"));
        sellApplyOrderVo.setCreateTime(new Date());
        sellApplyOrderVo.setOrderStatus((byte) 7);       //未收未发
        sellApplyOrderVo.setClearStatus(sellApplyOrderVo.getOrderMoney() == 0 ? (byte) 1 : (byte) 0);
        sellApplyOrderVo.setInReceivedQuantity(0);
        sellApplyOrderVo.setInNotReceivedQuantity(sellApplyOrderVo.getInTotalQuantity());
        sellApplyOrderVo.setOutSentQuantity(0);
        sellApplyOrderVo.setOutNotSentQuantity(sellApplyOrderVo.getOutTotalQuantity());
        sellApplyOrderVo.setClearedMoney(0.0);
        sellApplyOrderVo.setNotClearedMoney(sellApplyOrderVo.getOrderMoney());

        //商品规格相关操作
        inventoryMethod(sellApplyOrderVo, null);
    }

    /**
     * 库存相关操作共用方法
     * @param sellApplyOrderVo
     * @param oldVo
     */
    @Transactional
    public void inventoryMethod(SellApplyOrderVo sellApplyOrderVo, SellApplyOrderVo oldVo) {
        List<OrderGoodsSkuVo> orderGoodsSkuVos = sellApplyOrderVo.getDetails();
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            //设置初始属性
            orderGoodsSkuVo.setStoreId(sellApplyOrderVo.getStoreId());
            orderGoodsSkuVo.setOrderId(sellApplyOrderVo.getId());
            orderGoodsSkuVo.setFinishQuantity(0);
            orderGoodsSkuVo.setNotFinishQuantity(orderGoodsSkuVo.getQuantity());
            orderGoodsSkuVo.setOperatedQuantity(0);

            Integer warehouseId = null;
            int notSentQuantity = 0;
            int notReceivedQuantity = 0;
            if (orderGoodsSkuVo.getType() == 1) {
                warehouseId = sellApplyOrderVo.getInWarehouseId() == null ? oldVo.getInWarehouseId() : sellApplyOrderVo.getInWarehouseId();
                notReceivedQuantity = orderGoodsSkuVo.getQuantity();
            } else if (orderGoodsSkuVo.getType() == 0) {
                warehouseId = sellApplyOrderVo.getOutWarehouseId() == null ? oldVo.getOutWarehouseId() : sellApplyOrderVo.getOutWarehouseId();
                notSentQuantity = orderGoodsSkuVo.getQuantity();

                //减少可用库存
                inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(sellApplyOrderVo.getStoreId(), warehouseId, orderGoodsSkuVo.getGoodsSkuId(), 0, notSentQuantity, 0));
            }
            //增加待发货数量、待收货数量
            inventoryUtil.updateNotQuantityMethod(1, new WarehouseGoodsSkuVo(sellApplyOrderVo.getStoreId(), warehouseId, orderGoodsSkuVo.getGoodsSkuId(), notSentQuantity, notReceivedQuantity));

            //新增申请单/商品规格关系
            if (myOrderGoodsSkuMapper.addOrderGoodsSku(orderGoodsSkuVo) != 1) {
                throw new CommonException(CommonResponse.ADD_ERROR);
            }

            //修改销售出库单对应的商品规格已操作数量
            if (sellApplyOrderVo.getResultOrderId() != null) {
                byte type = sellApplyOrderVo.getType() == null ? oldVo.getType() : sellApplyOrderVo.getType();
                if (type == 3 || (type == 4 && orderGoodsSkuVo.getType() == 1)) {
                    if (orderGoodsSkuVo.getId() == null) {
                        throw new CommonException(CommonResponse.PARAMETER_ERROR);
                    }
                    if (myOrderGoodsSkuMapper.updateOperatedQuantity(new OrderGoodsSkuVo(sellApplyOrderVo.getStoreId(), orderGoodsSkuVo.getId(), orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getQuantity())) != 1) {
                        throw new CommonException(CommonResponse.ADD_ERROR);
                    }
                }
            }
        });
    }

    /**
     * 新增销售申请订单
     * @param sellApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addApplyOrder(SellApplyOrderVo sellApplyOrderVo) {
        //判断系统是否开账
        if (!systemUtil.judgeStartBillMethod(sellApplyOrderVo.getStoreId())) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR, "系统未开账");
        }

        //获取参数
        List<OrderGoodsSkuVo> orderGoodsSkuVos = sellApplyOrderVo.getDetails();
        Byte type = sellApplyOrderVo.getType();

        //获取零售默认仓库和银行卡
        SSystem sSystem = null;
        if (type == 1) {
            sSystem = systemMapper.findRetail(new SSystem(sellApplyOrderVo.getStoreId()));
            if (sSystem == null || sSystem.getRetailWarehouseId() == null || sSystem.getRetailBankAccountId() == null) {
                throw new CommonException(CommonResponse.ADD_ERROR, "零售默认仓库或零售默认银行卡未设置");
            }
            sellApplyOrderVo.setOutWarehouseId(sSystem.getRetailWarehouseId());
        }

        //判断参数、总商品数量、总商品金额、总优惠金额
        SellApplyOrderVo check = new SellApplyOrderVo(0, 0, 0.0, 0.0);
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            //判断参数
            if (orderGoodsSkuVo.getType() == null || orderGoodsSkuVo.getGoodsSkuId() == null || orderGoodsSkuVo.getQuantity() == null || orderGoodsSkuVo.getMoney() == null || orderGoodsSkuVo.getDiscountMoney() == null) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }

            //判断商品规格是否能出库
            if (sellApplyOrderVo.getOutWarehouseId() != null && !inventoryUtil.judgeCanOutMethod(sellApplyOrderVo.getStoreId(), orderGoodsSkuVo.getGoodsSkuId(), sellApplyOrderVo.getOutWarehouseId())) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR, "存在不能出库的商品规格");
            }

            //统计总商品数量、总商品金额、总优惠金额
            if (orderGoodsSkuVo.getType() == 0) {
                check.setOutTotalQuantity(check.getOutTotalQuantity() + orderGoodsSkuVo.getQuantity());
                check.setTotalMoney(check.getTotalMoney() + orderGoodsSkuVo.getMoney());
                check.setTotalDiscountMoney(check.getTotalDiscountMoney() + orderGoodsSkuVo.getDiscountMoney());
            } else if (orderGoodsSkuVo.getType() == 1) {
                check.setInTotalQuantity(check.getInTotalQuantity() + orderGoodsSkuVo.getQuantity());
                check.setTotalMoney(check.getTotalMoney() - orderGoodsSkuVo.getMoney());
                check.setTotalDiscountMoney(check.getTotalDiscountMoney() - orderGoodsSkuVo.getDiscountMoney());
            } else {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }
        });
        int inTotalQuantity = sellApplyOrderVo.getInTotalQuantity() == null ? 0 : sellApplyOrderVo.getInTotalQuantity();
        int outTotalQuantity = sellApplyOrderVo.getOutTotalQuantity() == null ? 0 : sellApplyOrderVo.getOutTotalQuantity();
        if (check.getInTotalQuantity() != inTotalQuantity || check.getOutTotalQuantity() != outTotalQuantity ||
                check.getTotalMoney().doubleValue() != sellApplyOrderVo.getTotalMoney().doubleValue() || check.getTotalDiscountMoney().doubleValue() != sellApplyOrderVo.getTotalDiscountMoney().doubleValue() ||
                sellApplyOrderVo.getOrderMoney() + sellApplyOrderVo.getTotalDiscountMoney() != sellApplyOrderVo.getTotalMoney()) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //判断新增类型
        switch (type) {
            case 1:     //零售单
                lsd(sellApplyOrderVo, sSystem);
                break;

            case 2:     //销售订单
                xsdd(sellApplyOrderVo);
                break;

            case 3:     //销售退货申请单
                xsthsqd(sellApplyOrderVo);
                break;

            case 4:     //销售换货申请单
                xshhsqd(sellApplyOrderVo);
                break;

            default:
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //新增申请单
        if (mySellMapper.addApplyOrder(sellApplyOrderVo) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }

        //修改客户最近交易时间
        if (sellApplyOrderVo.getClientId() != null) {
            if (myClientMapper.updateLastDealTime(new ClientVo(sellApplyOrderVo.getClientId())) != 1) {
                throw new CommonException(CommonResponse.ADD_ERROR);
            }
        }
        return CommonResponse.success();
    }

    /**
     * 删除销售申请订单
     * @param sellApplyOrderVos
     * @return
     */
    @Transactional
    public CommonResponse deleteApplyOrder(List<SellApplyOrderVo> sellApplyOrderVos) {
        sellApplyOrderVos.stream().forEach(sellApplyOrderVo -> {
            //重置库存和优惠券
            backMethod(sellApplyOrderVo);

            //删除销售申请订单
            if (mySellMapper.deleteApplyOrder(sellApplyOrderVo) != 1) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
        });

        return CommonResponse.success();
    }

    /**
     * 重置库存和优惠券的方法
     * @param sellApplyOrderVo
     */
    @Transactional
    public SellApplyOrderVo backMethod(SellApplyOrderVo sellApplyOrderVo) {
        Integer storeId = sellApplyOrderVo.getStoreId();

        //获取销售申请订单
        List<SellApplyOrderVo> oldVos =  mySellMapper.findAllApplyOrderDetail(sellApplyOrderVo);
        if (oldVos.size() == 0 || oldVos.get(0).getDetails().size() == 0) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }
        SellApplyOrderVo oldVo = oldVos.get(0);

        //判断单据状态
        Byte orderStatus = oldVo.getOrderStatus();
        if (orderStatus != 1 && orderStatus != 4 && orderStatus != 7) {     //1：未收，4：未发，7：未收未发
            throw new CommonException(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
        }

        //判断结算状态
        Byte clearStatus = oldVo.getClearStatus();
        if (orderStatus != 7 && clearStatus != 0) {     //0：未完成
            throw new CommonException(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
        }

        //修改商品规格可操作数量
        if (oldVo.getResultOrderId() != null) {
            if (oldVo.getType() == 3 || oldVo.getType() == 4) {
                List<OrderGoodsSkuVo> applyOrderGoodsSkuVos = oldVo.getDetails().stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("1")).collect(Collectors.toList());        //采购退换申请单或采购换货申请单中入库的的商品规格
                SellResultOrderVo resultOrderVo = mySellMapper.findResultOrderDetailById(new SellResultOrderVo(sellApplyOrderVo.getStoreId(), oldVo.getResultOrderId()));
                List<OrderGoodsSkuVo> resultOrderGoodsSkuVos = resultOrderVo.getDetails();
                resultOrderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
                    Optional<OrderGoodsSkuVo> optional = applyOrderGoodsSkuVos.stream().filter(vo -> vo.getGoodsSkuId().equals(orderGoodsSkuVo.getGoodsSkuId())).findFirst();
                    if (optional.isPresent()) {
                        int changeQuantity = optional.get().getQuantity();
                        orderGoodsSkuVo.setStoreId(sellApplyOrderVo.getStoreId());
                        orderGoodsSkuVo.setOperatedQuantity(-changeQuantity);
                        if (myOrderGoodsSkuMapper.updateOperatedQuantity(orderGoodsSkuVo) != 1) {
                            throw new CommonException(CommonResponse.UPDATE_ERROR);
                        }
                    }
                });
            }
        }

        //重置库存
        oldVo.getDetails().stream().forEach(orderGoodsSkuVo -> {
            Integer warehouseId = null;
            int notSentQuantity = 0;
            int notReceivedQuantity = 0;
            if (orderGoodsSkuVo.getType() == 1) {
                warehouseId = oldVo.getInWarehouseId();
                notReceivedQuantity = orderGoodsSkuVo.getQuantity();
            } else if (orderGoodsSkuVo.getType() == 0) {
                warehouseId = oldVo.getOutWarehouseId();
                notSentQuantity = orderGoodsSkuVo.getQuantity();

                //增加可用库存
                inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, warehouseId, orderGoodsSkuVo.getGoodsSkuId(), 0, notSentQuantity, 0));
            }
            //减少待发货数量、待收货数量
            inventoryUtil.updateNotQuantityMethod(0, new WarehouseGoodsSkuVo(storeId, warehouseId, orderGoodsSkuVo.getGoodsSkuId(), notSentQuantity, notReceivedQuantity));
        });

        //销售订单重置优惠券
        if (oldVo.getType() == 2 && oldVo.getDiscountCouponId() != null) {
            fundUtil.backDiscountCouponMethod(storeId, oldVo.getDiscountCouponId(), oldVo.getClient().getId());
        }

        //删除销售申请订单/商品规格关系
        myOrderGoodsSkuMapper.deleteOrderGoodsSku(new OrderGoodsSkuVo(sellApplyOrderVo.getStoreId(), oldVo.getId()));

        return oldVo;
    }

    /**
     * 修改销售申请订单
     * @param sellApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse updateApplyOrder(SellApplyOrderVo sellApplyOrderVo) {
        //判断参数
        if (sellApplyOrderVo.getStoreId() == null || sellApplyOrderVo.getId() == null ||
                sellApplyOrderVo.getDetails() == null || sellApplyOrderVo.getDetails().size() == 0 ||
                sellApplyOrderVo.getTotalMoney() == null || sellApplyOrderVo.getDiscountMoney() == null || sellApplyOrderVo.getTotalDiscountMoney() == null || sellApplyOrderVo.getOrderMoney() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //重置库存和优惠券
        SellApplyOrderVo oldVo = backMethod(sellApplyOrderVo);

        //修改销售申请订单
        if (mySellMapper.updateApplyOrder(sellApplyOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //判断优惠券金额
        if (sellApplyOrderVo.getDiscountCouponId() != null) {
            double discountCouponMoney = fundUtil.getDiscountCouponMoneyMethod(sellApplyOrderVo.getStoreId(), sellApplyOrderVo.getDiscountCouponId(), oldVo.getClient().getId(), sellApplyOrderVo.getTotalMoney());
            if (sellApplyOrderVo.getDiscountMoney() + discountCouponMoney != sellApplyOrderVo.getTotalDiscountMoney()) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }
        }

        //商品规格相关操作
        inventoryMethod(sellApplyOrderVo, oldVo);

        return CommonResponse.success();
    }

    /**
     * 修改销售申请订单备注
     * @param sellApplyOrderVo
     * @return
     */
    public CommonResponse updateApplyOrderRemark(SellApplyOrderVo sellApplyOrderVo) {
        //修改备注
        if (mySellMapper.updateApplyOrderRemark(sellApplyOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        return CommonResponse.success();
    }

    /**
     * 根据type查询销售申请订单
     * @param saoVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllApplyOrder(SellApplyOrderVo saoVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(mySellMapper.findCountApplyOrder(saoVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<SellApplyOrderVo> sellApplyOrderVos = mySellMapper.findAllPagedApplyOrder(saoVo, pageVo);
        
        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(saoVo.getStoreId()));
        sellApplyOrderVos.stream().forEach(vo -> {
            warehouses.stream().forEach(warehouse -> {
                if (vo.getInWarehouseId() != null && vo.getInWarehouseId().intValue() == warehouse.getId()) {
                    vo.setInWarehouseName(warehouse.getName());
                }
                if (vo.getOutWarehouseId() != null && vo.getOutWarehouseId().intValue() == warehouse.getId()) {
                    vo.setOutWarehouseName(warehouse.getName());
                }
            });
        });
        
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "id"));
        titles.add(new Title("单据类型", "type"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("产生方式", "prodcingWay"));
        titles.add(new Title("单据状态", "orderStatus"));
        if (saoVo.getType() == 3 || saoVo.getType() == 4) {
            titles.add(new Title("来源订单", "resultOrderId"));
            titles.add(new Title("入库仓库", "inWarehouseName"));
            titles.add(new Title("总收货数量", "inTotalQuantity"));
            titles.add(new Title("已收货数量", "inReceivedQuantity"));
            titles.add(new Title("未收货数量", "inNotReceivedQuantity"));
        }
        if (saoVo.getType() == 1 || saoVo.getType() == 2 || saoVo.getType() == 4) {
            titles.add(new Title("出库仓库", "outWarehouseName"));
            titles.add(new Title("总发货数量", "outTotalQuantity"));
            titles.add(new Title("已发货数量", "outSentQuantity"));
            titles.add(new Title("未发货数量数量", "outNotSentQuantity"));
        }
        titles.add(new Title("客户编号", "client.id"));
        titles.add(new Title("客户名称", "client.name"));
        titles.add(new Title("客户电话", "client.phone"));
        titles.add(new Title("会员卡号", "client.membershipNumber"));
        titles.add(new Title("总商品金额", "totalMoney"));
        titles.add(new Title("直接优惠金额", "discountMoney"));
        titles.add(new Title("优惠券编号", "discountCouponId"));
        titles.add(new Title("总优惠金额", "totalDiscountMoney"));
        titles.add(new Title("本单金额", "orderMoney"));
        titles.add(new Title("结算状态", "clearStatus"));
        titles.add(new Title("已结金额", "clearedMoney"));
        titles.add(new Title("未结金额", "notClearedMoney"));
        if (saoVo.getType() == 1) {
            titles.add(new Title("现金金额", "cashMoney"));
            titles.add(new Title("支付宝金额", "alipayMoney"));
            titles.add(new Title("微信金额", "wechatMoney"));
            titles.add(new Title("银行卡金额", "bankCardMoney"));
            titles.add(new Title("使用预收款金额", "advanceMoney"));
        }
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, sellApplyOrderVos, pageVo);
        
        return CommonResponse.success(commonResult);
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 根据type导出销售申请订单
     * @param saoVo
     * @param response
     * @return
     */
    public void exportApplyOrder(SellApplyOrderVo saoVo, HttpServletResponse response) {
        try {
            //获取参数
            Byte type = saoVo.getType();

            //根据条件查询单据
            List<SellApplyOrderVo> vos = mySellMapper.findAllApplyOrder(saoVo);

            //补上仓库名
            List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(saoVo.getStoreId()));
            vos.stream().forEach(vo -> {
                warehouses.stream().forEach(warehouse -> {
                    if (vo.getInWarehouseId() != null && vo.getInWarehouseId().intValue() == warehouse.getId()) {
                        vo.setInWarehouseName(warehouse.getName());
                    }
                    if (vo.getOutWarehouseId() != null && vo.getOutWarehouseId().intValue() == warehouse.getId()) {
                        vo.setOutWarehouseName(warehouse.getName());
                    }
                });
            });

            //备注
            String remark = "【筛选条件】" +
                    "\n单据编号：" + (saoVo.getId() == null ? "无" : saoVo.getId()) +
                    " 开始时间：" + (saoVo.getStartTime() == null ? "无" : sdf.format(saoVo.getStartTime())) +
                    " 结束时间：" + (saoVo.getEndTime() == null ? "无" : sdf.format(saoVo.getEndTime())) +
                    " 客户名称：" + (saoVo.getClient().getName() == null ? "无" : saoVo.getClient().getName()) +
                    " 客户电话：" + (saoVo.getClient().getPhone() == null ? "无" : saoVo.getClient().getPhone()) +
                    " 会员卡号：" + (saoVo.getClient().getMembershipNumber() == null ? "无" : saoVo.getClient().getMembershipNumber());

            //标题行
            List<String> titleRowCell1 = Arrays.asList(new String[]{
                    "单据编号", "单据类型", "单据日期", "产生方式", "单据状态", "出库仓库", "总发货数量", "已发货数量", "未发货数量", "客户编号", "客户名称", "客户电话", "会员卡号", "总商品金额", "直接优惠金额", "优惠券编号", "总优惠金额", "本单金额", "结算状态", "已结金额", "未结金额", "现金金额", "支付宝金额", "微信金额", "银行卡金额", "使用预收款金额", "经手人", "备注"
            });

            List<String> titleRowCell2 = Arrays.asList(new String[]{
                    "单据编号", "单据类型", "单据日期", "产生方式", "单据状态", "出库仓库", "总发货数量", "已发货数量", "未发货数量", "客户编号", "客户名称", "客户电话", "会员卡号", "总商品金额", "直接优惠金额", "优惠券编号", "总优惠金额", "本单金额", "结算状态", "已结金额", "未结金额", "经手人", "备注"
            });

            List<String> titleRowCell3 = Arrays.asList(new String[]{
                    "单据编号", "单据类型", "单据日期", "产生方式", "单据状态", "来源订单", "入库仓库", "总收货数量", "已收货数量", "未收货数量", "客户编号", "客户名称", "客户电话", "会员卡号", "总商品金额", "直接优惠金额", "优惠券编号", "总优惠金额", "本单金额", "结算状态", "已结金额", "未结金额", "经手人", "备注"
            });

            List<String> titleRowCell4 = Arrays.asList(new String[]{
                    "单据编号", "单据类型", "单据日期", "产生方式", "单据状态", "来源订单", "入库仓库", "总收货数量", "已收货数量", "未收货数量", "出库仓库", "总发货数量", "已发货数量", "未发货数量", "客户编号", "客户名称", "客户电话", "会员卡号", "总商品金额", "直接优惠金额", "优惠券编号", "总优惠金额", "本单金额", "结算状态", "已结金额", "未结金额", "经手人", "备注"
            });

            //最后一个必填列列数
            int lastRequiredCol = -1;

            String typeName = type == 1 ? "零售单" : type == 2 ? "销售订单" : type == 3 ? "销售退货申请单" : "销售换货申请单";

            //数据行
            List<List<String>> dataRowCells = new ArrayList<>();
            vos.stream().forEach(vo -> {
                List<String> dataRowCell = new ArrayList<>();
                dataRowCell.add(vo.getId());
                dataRowCell.add(typeName);
                dataRowCell.add(sdf.format(vo.getCreateTime()));
                dataRowCell.add(vo.getProdcingWay() == 1 ? "线下录单" : "线上下单");
                dataRowCell.add(vo.getOrderStatus() == 1 ? "未收" : vo.getOrderStatus() == 2 ? "部分收" : vo.getOrderStatus() == 3 ? "已收" : vo.getOrderStatus() == 4 ? "未发" : vo.getOrderStatus() == 5 ? "部分发" : vo.getOrderStatus() == 6 ? "已发" : vo.getOrderStatus() == 7 ? "未收未发" : vo.getOrderStatus() == 8 ? "未收部分发" : vo.getOrderStatus() == 9 ? "未收已发" : vo.getOrderStatus() == 10 ? "部分收未发" : vo.getOrderStatus() == 11 ? "部分收部分发" : vo.getOrderStatus() == 12 ? "部分收已发" : vo.getOrderStatus() == 13 ? "已收未发" : vo.getOrderStatus() == 14 ? "已收部分发" : vo.getOrderStatus() == 15 ? "已收已发" : null);
                if (type == 3 || type == 4) {
                    dataRowCell.add(vo.getResultOrderId());
                    dataRowCell.add(vo.getInWarehouseName());
                    dataRowCell.add(vo.getInTotalQuantity().toString());
                    dataRowCell.add(vo.getInReceivedQuantity().toString());
                    dataRowCell.add(vo.getInNotReceivedQuantity().toString());
                }
                if (type == 1 || type == 2 || type == 4) {
                    dataRowCell.add(vo.getOutWarehouseName());
                    dataRowCell.add(vo.getOutTotalQuantity().toString());
                    dataRowCell.add(vo.getOutSentQuantity().toString());
                    dataRowCell.add(vo.getOutNotSentQuantity().toString());
                }
                dataRowCell.add(vo.getClient() != null ? vo.getClient().getId() : null);
                dataRowCell.add(vo.getClient() != null ? vo.getClient().getName() : null);
                dataRowCell.add(vo.getClient() != null ? vo.getClient().getPhone() : null);
                dataRowCell.add(vo.getClient() != null ? vo.getClient().getMembershipNumber() : null);
                dataRowCell.add(vo.getTotalMoney().toString());
                dataRowCell.add(vo.getDiscountMoney().toString());
                dataRowCell.add(vo.getDiscountCouponId() != null ? vo.getDiscountCouponId().toString() : null);
                dataRowCell.add(vo.getTotalDiscountMoney().toString());
                dataRowCell.add(vo.getClearedMoney().toString());
                dataRowCell.add(vo.getClearStatus() == 0 ? "未完成" : vo.getClearStatus() == 1 ? "已完成" : null);
                dataRowCell.add(vo.getClearedMoney().toString());
                dataRowCell.add(vo.getNotClearedMoney().toString());
                if (type == 1) {
                    dataRowCell.add(vo.getCashMoney().toString());
                    dataRowCell.add(vo.getAlipayMoney().toString());
                    dataRowCell.add(vo.getWechatMoney().toString());
                    dataRowCell.add(vo.getBankCardMoney().toString());
                    dataRowCell.add(vo.getAdvanceMoney().toString());
                }
                dataRowCell.add(vo.getUserName());
                dataRowCell.add(vo.getRemark());
                dataRowCells.add(dataRowCell);
            });

            //输出excel
            String fileName = "【" + typeName + "导出】_" + System.currentTimeMillis() + ".xls";
            CommonUtil.outputExcelMethod(remark, type == 1 ? titleRowCell1 : type == 2 ? titleRowCell2 : type == 3 ? titleRowCell3 : titleRowCell4, lastRequiredCol, dataRowCells, fileName, response);
        } catch (Exception e) {
            throw new CommonException(CommonResponse.EXPORT_ERROR, e.getMessage());
        }
    }

    /**
     * 根据单据编号查询销售申请订单详情
     * @param sellApplyOrderVo
     * @return
     */
    public CommonResponse findApplyOrderDetailById(SellApplyOrderVo sellApplyOrderVo) {
        //获取参数
        Integer storeId = sellApplyOrderVo.getStoreId();

        //根据单据编号查询单据详情
        List<SellApplyOrderVo> saoVos = mySellMapper.findAllApplyOrderDetail(sellApplyOrderVo);
        if (saoVos.size() == 0 || saoVos.get(0).getDetails().size() == 0) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }
        sellApplyOrderVo = saoVos.get(0);

        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(storeId));
        for (Warehouse warehouse : warehouses) {
            if (sellApplyOrderVo.getInWarehouseId() != null && sellApplyOrderVo.getInWarehouseId().intValue() == warehouse.getId()) {
                sellApplyOrderVo.setInWarehouseName(warehouse.getName());
            }
            if (sellApplyOrderVo.getOutWarehouseId() != null && sellApplyOrderVo.getOutWarehouseId().intValue() == warehouse.getId()) {
                sellApplyOrderVo.setOutWarehouseName(warehouse.getName());
            }
        }
        
        return CommonResponse.success(sellApplyOrderVo);
    }

    //销售结果订单

    /**
     * 红冲销售结果订单
     * @param sellResultOrderVo
     * @return
     */
    @Transactional
    public CommonResponse redDashed(SellResultOrderVo sellResultOrderVo) {
        //获取参数
        Integer storeId = sellResultOrderVo.getStoreId();
        String userId = sellResultOrderVo.getUserId();
        String remark = sellResultOrderVo.getRemark();

        //红冲
        if (mySellMapper.redDashed(sellResultOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //获取红冲蓝单
        sellResultOrderVo = mySellMapper.findResultOrderDetailById(sellResultOrderVo);

        //设置红冲红单
        sellResultOrderVo.setStoreId(storeId);
        String oldResultOrderId = sellResultOrderVo.getId();
        sellResultOrderVo.setId("HC-" + oldResultOrderId);
        sellResultOrderVo.setCreateTime(new Date());
        sellResultOrderVo.setOrderStatus((byte) -2);
        sellResultOrderVo.setTotalQuantity(-sellResultOrderVo.getTotalQuantity());
        sellResultOrderVo.setTotalMoney(-sellResultOrderVo.getTotalMoney());
        sellResultOrderVo.setTotalDiscountMoney(-sellResultOrderVo.getTotalDiscountMoney());
        sellResultOrderVo.setOrderMoney(-sellResultOrderVo.getOrderMoney());
        sellResultOrderVo.setCostMoney(-sellResultOrderVo.getCostMoney());
        sellResultOrderVo.setGrossMarginMoney(-sellResultOrderVo.getGrossMarginMoney());
        sellResultOrderVo.setUserId(userId);
        sellResultOrderVo.setRemark(remark);

        //新增红冲红单
        if (mySellMapper.addResultOrder(sellResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //查询该结果单对应的申请单
        SellApplyOrderVo applyOrderVo = mySellMapper.findAllApplyOrderDetail(new SellApplyOrderVo(storeId, sellResultOrderVo.getApplyOrderId())).get(0);
        List<OrderGoodsSkuVo> applyOrderDetails = applyOrderVo.getDetails();
        List<OrderGoodsSkuVo> applyOrderInVos = applyOrderDetails.stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("1")).collect(Collectors.toList());
        List<OrderGoodsSkuVo> applyOrderOutVos = applyOrderDetails.stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("0")).collect(Collectors.toList());

        //修改库存相关
        List<OrderGoodsSkuVo> orderGoodsSkuVos = sellResultOrderVo.getDetails();
        for (OrderGoodsSkuVo orderGoodsSkuVo : orderGoodsSkuVos) {
            //已经退换货的结果单不能红冲
            if (orderGoodsSkuVo.getOperatedQuantity() > 0) {
                throw new CommonException(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
            }

            Integer goodsSkuId = orderGoodsSkuVo.getGoodsSkuId();
            Integer quantity = orderGoodsSkuVo.getQuantity();

            if (orderGoodsSkuVo.getType() == 0) {        //原来是出库
                Integer outWarehouseId = sellResultOrderVo.getSellApplyOrderVo().getOutWarehouseId();

                if (applyOrderVo.getType() == 1) {
                    //增加实物库存、可用库存、账面库存
                    inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, outWarehouseId, goodsSkuId, quantity, quantity, quantity));
                } else if (applyOrderVo.getType() == 2) {
                    //增加实物库存、账面库存
                    inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, outWarehouseId, goodsSkuId, quantity, 0, quantity));

                    //增加待发货数量
                    inventoryUtil.updateNotQuantityMethod(1, new WarehouseGoodsSkuVo(storeId, outWarehouseId, goodsSkuId, quantity, 0));
                } else if (applyOrderVo.getType() == 4) {
                    //增加实物库存、账面库存
                    inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, outWarehouseId, goodsSkuId, quantity, 0, quantity));

                    //增加待发货数量
                    inventoryUtil.updateNotQuantityMethod(1, new WarehouseGoodsSkuVo(storeId, outWarehouseId, goodsSkuId, quantity, 0));
                }

                //红冲库存记账记录
                inventoryUtil.redDashedStorageCheckOrderMethod(0, new StorageCheckOrderVo(storeId, oldResultOrderId, goodsSkuId, outWarehouseId, userId));

                if (applyOrderVo.getType() != 1) {
                    //减少完成数量
                    Optional<OrderGoodsSkuVo> optional = applyOrderOutVos.stream().filter(applyOrderInVo -> applyOrderInVo.getGoodsSkuId().toString().equals(goodsSkuId.toString())).findFirst();
                    if (!optional.isPresent()) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    OrderGoodsSkuVo applyOrderInVo = optional.get();
                    applyOrderInVo.setStoreId(storeId);
                    applyOrderInVo.setChangeQuantity(-quantity);
                    if (myOrderGoodsSkuMapper.updateOrderGoodsSku(applyOrderInVo) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    applyOrderVo.setOutSentQuantity(applyOrderVo.getOutSentQuantity() - quantity);
                    applyOrderVo.setOutNotSentQuantity(applyOrderVo.getOutNotSentQuantity() + quantity);
                }

            } else if (orderGoodsSkuVo.getType() == 1) {     //原来是入库
                Integer inWarehouseId = sellResultOrderVo.getSellApplyOrderVo().getInWarehouseId();

                if (applyOrderVo.getType() == 3) {
                    //减少实物库存、可用库存、账面库存
                    inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, inWarehouseId, goodsSkuId, quantity, quantity, quantity));

                    //增加待收货数量
                    inventoryUtil.updateNotQuantityMethod(1, new WarehouseGoodsSkuVo(storeId, inWarehouseId, goodsSkuId, 0, quantity));
                } else if (applyOrderVo.getType() == 4) {
                    //减少实物库存、可用库存、账面库存
                    inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, inWarehouseId, goodsSkuId, quantity, quantity, quantity));

                    //增加待收货数量
                    inventoryUtil.updateNotQuantityMethod(1, new WarehouseGoodsSkuVo(storeId, inWarehouseId, goodsSkuId, 0, quantity));
                }

                //红冲库存记账记录
                inventoryUtil.redDashedStorageCheckOrderMethod(1, new StorageCheckOrderVo(storeId, oldResultOrderId, goodsSkuId, inWarehouseId, userId));

                if (applyOrderVo.getType() != 1) {
                    //减少完成数量
                    Optional<OrderGoodsSkuVo> optional = applyOrderInVos.stream().filter(applyOrderInVo -> applyOrderInVo.getGoodsSkuId().toString().equals(goodsSkuId.toString())).findFirst();
                    if (!optional.isPresent()) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    OrderGoodsSkuVo applyOrderInVo = optional.get();
                    applyOrderInVo.setStoreId(storeId);
                    applyOrderInVo.setChangeQuantity(-quantity);
                    if (myOrderGoodsSkuMapper.updateOrderGoodsSku(applyOrderInVo) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    applyOrderVo.setInReceivedQuantity(applyOrderVo.getInReceivedQuantity() - quantity);
                    applyOrderVo.setInNotReceivedQuantity(applyOrderVo.getInNotReceivedQuantity() + quantity);
                }
            } else {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
        }

        //修改申请订单的单据状态和完成数量
        Byte orderStatus = null;
        List<GoodsSku> goodsSkus = myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(storeId));
        switch (applyOrderVo.getType()) {
            case 1:     //零售单
                //红冲资金对账记录
                fundUtil.redDashedFundCheckOrderMethod(new FundCheckOrderVo(storeId, sellResultOrderVo.getApplyOrderId(), userId));

                //减少客户积分
                if (applyOrderVo.getClient() != null && applyOrderVo.getClient().getId() != null) {
                    storeClientUtil.updateIntegralMethod(0, storeId, applyOrderVo.getClient().getId(), sellResultOrderVo.getId(), goodsSkus, orderGoodsSkuVos, userId);

                    //减少该客户邀请人提成
                    storeClientUtil.updatePushMoneyMethod(0, storeId, applyOrderVo.getClient().getId(), sellResultOrderVo.getId(), userId, sellResultOrderVo.getOrderMoney());
                }

                break;

            case 2:     //销售订单
                //减少客户积分
                storeClientUtil.updateIntegralMethod(0, storeId, applyOrderVo.getClient().getId(), sellResultOrderVo.getId(), goodsSkus, orderGoodsSkuVos, userId);

                //减少该客户邀请人提成
                storeClientUtil.updatePushMoneyMethod(0, storeId, applyOrderVo.getClient().getId(), sellResultOrderVo.getId(), userId, sellResultOrderVo.getOrderMoney());

                //修改申请订单的单据状态和完成数量
                if (applyOrderVo.getOutSentQuantity() == 0 && applyOrderVo.getOutTotalQuantity().intValue() == applyOrderVo.getOutNotSentQuantity()) {        //未发
                    orderStatus = 4;
                } else if (applyOrderVo.getOutSentQuantity() > 0 && applyOrderVo.getOutNotSentQuantity() < applyOrderVo.getOutTotalQuantity()) {        //部分发
                    orderStatus = 5;
                } else if (applyOrderVo.getOutSentQuantity().intValue() == applyOrderVo.getOutTotalQuantity() && applyOrderVo.getOutNotSentQuantity() == 0) {        //已发
                    orderStatus = 6;
                }
                if (mySellMapper.updateApplyOrderOrderStatusAndQuantity(new StorageOrderVo(storeId, (byte) 3, applyOrderVo.getId(), orderStatus, applyOrderVo.getOutSentQuantity() - applyOrderVo.getOutTotalQuantity())) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                break;

            case 3:     //销售退货申请单
                //增加客户积分
                storeClientUtil.updateIntegralMethod(1, storeId, applyOrderVo.getClient().getId(), sellResultOrderVo.getId(), goodsSkus, orderGoodsSkuVos, userId);

                //增加该客户邀请人提成
                storeClientUtil.updatePushMoneyMethod(1, storeId, applyOrderVo.getClient().getId(), sellResultOrderVo.getId(), userId, sellResultOrderVo.getOrderMoney());

                //修改申请订单的单据状态和完成数量
                if (applyOrderVo.getInReceivedQuantity() == 0 && applyOrderVo.getInNotReceivedQuantity().intValue() == applyOrderVo.getInTotalQuantity()) {        //未收
                    orderStatus = 1;
                } else if (applyOrderVo.getInReceivedQuantity() > 0 && applyOrderVo.getInNotReceivedQuantity() < applyOrderVo.getInTotalQuantity()) {        //部分收
                    orderStatus = 2;
                } else if (applyOrderVo.getInReceivedQuantity().intValue() == applyOrderVo.getInTotalQuantity() && applyOrderVo.getInNotReceivedQuantity() == 0) {        //已收
                    orderStatus = 3;
                }
                if (mySellMapper.updateApplyOrderOrderStatusAndQuantity(new StorageOrderVo(storeId, (byte) 2, applyOrderVo.getId(), orderStatus, applyOrderVo.getInReceivedQuantity() - applyOrderVo.getInTotalQuantity())) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                break;

            case 4:     //销售换货申请单
                //修改申请订单的单据状态和完成数量
                if (applyOrderVo.getInReceivedQuantity() == 0 && applyOrderVo.getInNotReceivedQuantity().intValue() == applyOrderVo.getInTotalQuantity()) {        //未收
                    if (applyOrderVo.getOutSentQuantity() == 0 && applyOrderVo.getOutTotalQuantity().intValue() == applyOrderVo.getOutNotSentQuantity()) {        //未收未发
                        orderStatus = 7;
                    } else if (applyOrderVo.getOutSentQuantity() > 0 && applyOrderVo.getOutNotSentQuantity() < applyOrderVo.getOutTotalQuantity()) {        //未收部分发
                        orderStatus = 8;
                    } else if (applyOrderVo.getOutSentQuantity().intValue() == applyOrderVo.getOutTotalQuantity() && applyOrderVo.getOutNotSentQuantity() == 0) {        //未收已发
                        orderStatus = 9;
                    }
                } else if (applyOrderVo.getInReceivedQuantity() > 0 && applyOrderVo.getInNotReceivedQuantity() < applyOrderVo.getInTotalQuantity()) {        //部分收
                    if (applyOrderVo.getOutSentQuantity() == 0 && applyOrderVo.getOutTotalQuantity().intValue() == applyOrderVo.getOutNotSentQuantity()) {        //部分收未发
                        orderStatus = 10;
                    } else if (applyOrderVo.getOutSentQuantity() > 0 && applyOrderVo.getOutNotSentQuantity() < applyOrderVo.getOutTotalQuantity()) {        //部分收部分发
                        orderStatus = 11;
                    } else if (applyOrderVo.getOutSentQuantity().intValue() == applyOrderVo.getOutTotalQuantity() && applyOrderVo.getOutNotSentQuantity() == 0) {        //部分收已发
                        orderStatus = 12;
                    }
                } else if (applyOrderVo.getInReceivedQuantity().intValue() == applyOrderVo.getInTotalQuantity() && applyOrderVo.getInNotReceivedQuantity() == 0) {        //已收
                    if (applyOrderVo.getOutSentQuantity() == 0 && applyOrderVo.getOutTotalQuantity().intValue() == applyOrderVo.getOutNotSentQuantity()) {        //已收未发
                        orderStatus = 13;
                    } else if (applyOrderVo.getOutSentQuantity() > 0 && applyOrderVo.getOutNotSentQuantity() < applyOrderVo.getOutTotalQuantity()) {        //已收部分发
                        orderStatus = 14;
                    } else if (applyOrderVo.getOutSentQuantity().intValue() == applyOrderVo.getOutTotalQuantity() && applyOrderVo.getOutNotSentQuantity() == 0) {        //已收已发
                        orderStatus = 15;
                    }
                }
                if (mySellMapper.updateApplyOrderOrderStatusAndQuantity(new StorageOrderVo(storeId, (byte) 2, applyOrderVo.getId(), orderStatus, applyOrderVo.getInReceivedQuantity() - applyOrderVo.getInTotalQuantity())) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                if (mySellMapper.updateApplyOrderOrderStatusAndQuantity(new StorageOrderVo(storeId, (byte) 4, applyOrderVo.getId(), orderStatus, applyOrderVo.getOutSentQuantity() - applyOrderVo.getOutTotalQuantity())) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                break;

            default:
                throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //红冲往来对账记录
        fundUtil.redDashedFundTargetCheckOrderMethod(new FundTargetCheckOrderVo(storeId, oldResultOrderId, userId));

        //红冲会计凭证
        financialAffairsUtil.redDashedAccountingDocumentMethod(new AccountingDocumentVo(storeId, oldResultOrderId, userId));

        return CommonResponse.success();
    }

    /**
     * 查询销售结果订单
     * @param sellResultOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllResultOrder(SellResultOrderVo sellResultOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(mySellMapper.findCountResultOrder(sellResultOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<SellResultOrderVo> sellResultOrderVos = mySellMapper.findAllPagedResultOrder(sellResultOrderVo, pageVo);
        
        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(sellResultOrderVo.getStoreId()));
        sellResultOrderVos.stream().forEach(vo -> {
            warehouses.stream().forEach(warehouse -> {
                if (vo.getSellApplyOrderVo() != null && vo.getSellApplyOrderVo().getInWarehouseId() != null &&
                        vo.getSellApplyOrderVo().getInWarehouseId().intValue() == warehouse.getId()) {
                    vo.getSellApplyOrderVo().setInWarehouseName(warehouse.getName());
                }
                if (vo.getSellApplyOrderVo() != null && vo.getSellApplyOrderVo().getOutWarehouseId() != null &&
                        vo.getSellApplyOrderVo().getOutWarehouseId().intValue() == warehouse.getId()) {
                    vo.getSellApplyOrderVo().setOutWarehouseName(warehouse.getName());
                }
            });
        });
        
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "id"));
        titles.add(new Title("单据类型", "type"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("来源订单", "applyOrderId"));
        titles.add(new Title("结算状态", "sellApplyOrderVo.clearStatus"));
        titles.add(new Title("客户编号", "sellApplyOrderVo.client.id"));
        titles.add(new Title("客户名称", "sellApplyOrderVo.client.name"));
        titles.add(new Title("客户电话", "sellApplyOrderVo.client.phone"));
        titles.add(new Title("会员卡号", "sellApplyOrderVo.client.membershipNumber"));
        titles.add(new Title("入库仓库", "sellApplyOrderVo.inWarehouseName"));
        titles.add(new Title("出库仓库", "sellApplyOrderVo.outWarehouseName"));
        titles.add(new Title("总商品数量", "totalQuantity"));
        titles.add(new Title("总商品金额", "totalMoney"));
        titles.add(new Title("总优惠金额", "totalDiscountMoney"));
        titles.add(new Title("本单金额", "orderMoney"));
        titles.add(new Title("成本", "costMoney"));
        titles.add(new Title("毛利", "grossMarginMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, sellResultOrderVos, pageVo);
        
        return CommonResponse.success(commonResult);
    }

    /**
     * 导出销售结果订单
     * @param sroVo
     * @param response
     * @return
     */
    public void exportResultOrder(SellResultOrderVo sroVo, HttpServletResponse response) {
        try {
            //根据条件查询单据
            List<SellResultOrderVo> sroVos = mySellMapper.findAllResultOrder(sroVo);

            //补上仓库名
            List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(sroVo.getStoreId()));
            sroVos.stream().forEach(vo -> {
                warehouses.stream().forEach(warehouse -> {
                    if (vo.getSellApplyOrderVo() != null && vo.getSellApplyOrderVo().getInWarehouseId() != null &&
                            vo.getSellApplyOrderVo().getInWarehouseId().intValue() == warehouse.getId()) {
                        vo.getSellApplyOrderVo().setInWarehouseName(warehouse.getName());
                    }
                    if (vo.getSellApplyOrderVo() != null && vo.getSellApplyOrderVo().getOutWarehouseId() != null &&
                            vo.getSellApplyOrderVo().getOutWarehouseId().intValue() == warehouse.getId()) {
                        vo.getSellApplyOrderVo().setOutWarehouseName(warehouse.getName());
                    }
                });
            });

            //备注
            String remark = "【筛选条件】" +
                    "\n单据编号：" + (sroVo.getId() == null ? "无" : sroVo.getId()) +
                    " 单据类型：" + (sroVo.getType() == null ? "无" : sroVos.get(0).getType() == 1 ? "零售单" : sroVos.get(0).getType() == 2 ? "销售订单" : sroVos.get(0).getType() == 3 ? "销售退货单" : "销售换货单") +
                    " 开始时间：" + (sroVo.getSellApplyOrderVo().getStartTime() == null ? "无" : sdf.format(sroVo.getSellApplyOrderVo().getStartTime())) +
                    " 结束时间：" + (sroVo.getSellApplyOrderVo().getEndTime() == null ? "无" : sdf.format(sroVo.getSellApplyOrderVo().getEndTime())) +
                    " 客户名称：" + (sroVo.getSellApplyOrderVo().getClient().getName() == null ? "无" : sroVo.getSellApplyOrderVo().getClient().getName()) +
                    " 客户电话：" + (sroVo.getSellApplyOrderVo().getClient().getPhone() == null ? "无" : sroVo.getSellApplyOrderVo().getClient().getPhone()) +
                    " 会员卡号：" + (sroVo.getSellApplyOrderVo().getClient().getMembershipNumber() == null ? "无" : sroVo.getSellApplyOrderVo().getClient().getMembershipNumber());

            //标题行
            List<String> titleRowCell = Arrays.asList(new String[]{
                    "单据编号", "单据类型", "单据日期", "来源订单", "结算状态", "客户编号", "客户名称", "客户电话", "会员卡号", "入库仓库", "出库仓库", "总商品数量", "总商品金额", "总优惠金额", "本单金额", "成本", "毛利", "经手人", "备注"
            });

            //最后一个必填列列数
            int lastRequiredCol = -1;

            //数据行
            List<List<String>> dataRowCells = new ArrayList<>();
            sroVos.stream().forEach(vo -> {
                List<String> dataRowCell = new ArrayList<>();
                dataRowCell.add(vo.getId());
                dataRowCell.add(vo.getType() == 1 ? "零售单" : vo.getType() == 2 ? "销售订单" : vo.getType() == 3 ? "销售退货单" : "销售换货单");
                dataRowCell.add(sdf.format(vo.getCreateTime()));
                dataRowCell.add(vo.getApplyOrderId());
                SellApplyOrderVo saoVo = vo.getSellApplyOrderVo();
                dataRowCell.add(saoVo.getClearStatus() == 0 ? "未完成" : saoVo.getClearStatus() == 1 ? "已完成" : null);
                dataRowCell.add(saoVo.getClient() != null ? saoVo.getClient().getId() : null);
                dataRowCell.add(saoVo.getClient() != null ? saoVo.getClient().getName() : null);
                dataRowCell.add(saoVo.getClient() != null ? saoVo.getClient().getPhone() : null);
                dataRowCell.add(saoVo.getClient() != null ? saoVo.getClient().getMembershipNumber() : null);
                dataRowCell.add(saoVo.getInWarehouseName());
                dataRowCell.add(saoVo.getOutWarehouseName());
                dataRowCell.add(vo.getTotalQuantity().toString());
                dataRowCell.add(vo.getTotalMoney().toString());
                dataRowCell.add(vo.getTotalDiscountMoney().toString());
                dataRowCell.add(vo.getOrderMoney().toString());
                dataRowCell.add(vo.getCostMoney().toString());
                dataRowCell.add(vo.getGrossMarginMoney().toString());
                dataRowCell.add(vo.getUserName());
                dataRowCell.add(vo.getRemark());
                if (vo.getOrderStatus() < 1) {
                    dataRowCell.add(vo.getOrderStatus() == -1 ? "红冲蓝单" : vo.getOrderStatus() == -2 ? "红冲红单" : null);
                }
                dataRowCells.add(dataRowCell);
            });

            //输出excel
            String fileName = "【销售历史导出】_" + System.currentTimeMillis() + ".xls";
            CommonUtil.outputExcelMethod(remark, titleRowCell, lastRequiredCol, dataRowCells, fileName, response);
        } catch (Exception e) {
            throw new CommonException(CommonResponse.EXPORT_ERROR, e.getMessage());
        }
    }

    /**
     * 根据单据编号查询销售结果订单详情
     * @param sellResultOrderVo
     * @return
     */
    public CommonResponse findResultOrderDetailById(SellResultOrderVo sellResultOrderVo) {
        //获取参数
        Integer storeId = sellResultOrderVo.getStoreId();

        //根据单据编号查询单据详情
        sellResultOrderVo = mySellMapper.findResultOrderDetailById(sellResultOrderVo);
        if (sellResultOrderVo == null || sellResultOrderVo.getDetails().size() == 0) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }

        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(storeId));
        for (Warehouse warehouse : warehouses) {
            if (sellResultOrderVo.getSellApplyOrderVo() != null && sellResultOrderVo.getSellApplyOrderVo().getInWarehouseId() != null &&
                    sellResultOrderVo.getSellApplyOrderVo().getInWarehouseId().intValue() == warehouse.getId()) {
                sellResultOrderVo.getSellApplyOrderVo().setInWarehouseName(warehouse.getName());
            }
            if (sellResultOrderVo.getSellApplyOrderVo() != null && sellResultOrderVo.getSellApplyOrderVo().getOutWarehouseId() != null
                    && sellResultOrderVo.getSellApplyOrderVo().getOutWarehouseId().intValue() == warehouse.getId()) {
                sellResultOrderVo.getSellApplyOrderVo().setOutWarehouseName(warehouse.getName());
            }
        }

        return CommonResponse.success(sellResultOrderVo);
    }
}
