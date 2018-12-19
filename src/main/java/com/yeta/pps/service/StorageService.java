package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.*;
import com.yeta.pps.po.*;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 仓库存储相关逻辑处理
 * @author YETA
 * @date 2018/12/11/16:22
 */
@Service
public class StorageService {

    private static final Logger LOG = LoggerFactory.getLogger(StorageService.class);

    @Autowired
    private MyStorageMapper myStorageMapper;

    @Autowired
    private MyProcurementMapper myProcurementMapper;

    @Autowired
    private MySellMapper mySellMapper;

    @Autowired
    private MyOrderGoodsSkuMapper myOrderGoodsSkuMapper;

    @Autowired
    private MyWarehouseMapper myWarehouseMapper;

    @Autowired
    private MyGoodsMapper myGoodsMapper;

    @Autowired
    private SellService sellService;

    /**
     * 修改商品规格完成情况和修改商品规格库存
     * @param storeId
     * @param orderGoodsSkuVos
     */
    @Transactional
    public void updateGoodsSku(byte type, int storeId, int quantity, List<OrderGoodsSkuVo> orderGoodsSkuVos) {
        OrderGoodsSku orderGoodsSku = new OrderGoodsSku();
        orderGoodsSku.setQuantity(quantity);
        orderGoodsSku.setFinishQuantity(0);
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            //判断参数
            if (orderGoodsSkuVo.getId() == null || orderGoodsSkuVo.getGoodsSkuId() == null || orderGoodsSkuVo.getChangeQuantity() == null) {
                throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
            }
            orderGoodsSkuVo.setStoreId(storeId);
            //修改商品规格完成情况
            if (myOrderGoodsSkuMapper.updateOrderGoodsSku(orderGoodsSkuVo) != 1) {
                throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
            }
            if ((type == 1 || type == 2) && orderGoodsSkuVo.getType() == 1) {        //入库
                //增加商品规格库存
                if (myGoodsMapper.increaseGoodsSkuInventory(new GoodsSkuVo(storeId, orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getChangeQuantity())) != 1) {
                    throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                }
            } else if ((type == 3 || type == 4) && orderGoodsSkuVo.getType() == 0) {     //出库
                //减少商品规格库存
                if (myGoodsMapper.decreaseGoodsSkuInventory(new GoodsSkuVo(storeId, orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getChangeQuantity())) != 1) {
                    throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                }
            } else {
                LOG.info("商品规格类型不正确【{}】", orderGoodsSkuVo.getType());
                throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
            }
            orderGoodsSku.setFinishQuantity(orderGoodsSku.getFinishQuantity() + orderGoodsSkuVo.getChangeQuantity());
        });
        if (orderGoodsSku.getQuantity() != orderGoodsSku.getFinishQuantity()) {
            LOG.info("总收/发货数量与所有商品规格数量之和不对应");
            throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
        }
    }

    /**
     * 获取各种金额的方法
     * @param flag
     * @param storeId
     * @param orderGoodsSkuVos
     * @param orderGoodsSkuses
     * @return
     */
    @Transactional
    public SellResultOrder getMoneyMethod(int flag, int storeId, List<OrderGoodsSkuVo> orderGoodsSkuVos, List<OrderGoodsSku> orderGoodsSkuses) {
        SellResultOrder money = new SellResultOrder(new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0));
        List<GoodsSku> goodsSkus = myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(storeId));
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            Optional<GoodsSku> goodsSkuOptional = goodsSkus.stream().filter(goodsSku -> goodsSku.getId().equals(orderGoodsSkuVo.getGoodsSkuId())).findFirst();
            if (!goodsSkuOptional.isPresent()) {
                LOG.info("获取商品规格进价，商品规格编号不存在【{}】", orderGoodsSkuVo.getGoodsSkuId());
                throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
            }
            GoodsSku goodsSku = goodsSkuOptional.get();
            Optional<OrderGoodsSku> applyOrderGoodsSkuOptional = orderGoodsSkuses.stream().filter(orderGoodsSku -> orderGoodsSku.getId().equals(orderGoodsSkuVo.getId())).findFirst();
            if (!applyOrderGoodsSkuOptional.isPresent()) {
                LOG.info("获取申请单/商品规格关系，申请单/商品规格关系编号不存在【{}】", orderGoodsSkuVo.getGoodsSkuId());
                throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
            }
            OrderGoodsSku orderGoodsSku = applyOrderGoodsSkuOptional.get();
            if (flag == 1) {        //采购换货
                if (orderGoodsSkuVo.getType() == 1) {       //入库
                    money.setTotalMoney(new BigDecimal(money.getTotalMoney().doubleValue() + (orderGoodsSku.getMoney().doubleValue() * (orderGoodsSkuVo.getChangeQuantity() == null ? orderGoodsSku.getQuantity() : orderGoodsSkuVo.getChangeQuantity()) / orderGoodsSku.getQuantity())));
                    money.setTotalDiscountMoney(new BigDecimal(money.getTotalDiscountMoney().doubleValue() + (orderGoodsSku.getDiscountMoney().doubleValue() * (orderGoodsSkuVo.getChangeQuantity() == null ? orderGoodsSku.getQuantity() : orderGoodsSkuVo.getChangeQuantity()) / orderGoodsSku.getQuantity())));
                    money.setCostMoney(new BigDecimal(money.getCostMoney().doubleValue() + goodsSku.getPurchasePrice().doubleValue() * (orderGoodsSkuVo.getChangeQuantity() == null ? orderGoodsSku.getQuantity() : orderGoodsSkuVo.getChangeQuantity())));
                } else if (orderGoodsSkuVo.getType() == 0) {        //出库
                    money.setTotalMoney(new BigDecimal(money.getTotalMoney().doubleValue() - (orderGoodsSku.getMoney().doubleValue() * (orderGoodsSkuVo.getChangeQuantity() == null ? orderGoodsSku.getQuantity() : orderGoodsSkuVo.getChangeQuantity()) / orderGoodsSku.getQuantity())));
                    money.setTotalDiscountMoney(new BigDecimal(money.getTotalDiscountMoney().doubleValue() - (orderGoodsSku.getDiscountMoney().doubleValue() * (orderGoodsSkuVo.getChangeQuantity() == null ? orderGoodsSku.getQuantity() : orderGoodsSkuVo.getChangeQuantity()) / orderGoodsSku.getQuantity())));
                    money.setCostMoney(new BigDecimal(money.getCostMoney().doubleValue() - goodsSku.getPurchasePrice().doubleValue() * (orderGoodsSkuVo.getChangeQuantity() == null ? orderGoodsSku.getQuantity() : orderGoodsSkuVo.getChangeQuantity())));
                }
            } else if (flag == 2) {     //销售换货
                if (orderGoodsSkuVo.getType() == 1) {       //入库
                    money.setTotalMoney(new BigDecimal(money.getTotalMoney().doubleValue() - (orderGoodsSku.getMoney().doubleValue() * (orderGoodsSkuVo.getChangeQuantity() == null ? orderGoodsSku.getQuantity() : orderGoodsSkuVo.getChangeQuantity()) / orderGoodsSku.getQuantity())));
                    money.setTotalDiscountMoney(new BigDecimal(money.getTotalDiscountMoney().doubleValue() - (orderGoodsSku.getDiscountMoney().doubleValue() * (orderGoodsSkuVo.getChangeQuantity() == null ? orderGoodsSku.getQuantity() : orderGoodsSkuVo.getChangeQuantity()) / orderGoodsSku.getQuantity())));
                    money.setCostMoney(new BigDecimal(money.getCostMoney().doubleValue() - goodsSku.getPurchasePrice().doubleValue() * (orderGoodsSkuVo.getChangeQuantity() == null ? orderGoodsSku.getQuantity() : orderGoodsSkuVo.getChangeQuantity())));
                } else if (orderGoodsSkuVo.getType() == 0) {        //出库
                    money.setTotalMoney(new BigDecimal(money.getTotalMoney().doubleValue() + (orderGoodsSku.getMoney().doubleValue() * (orderGoodsSkuVo.getChangeQuantity() == null ? orderGoodsSku.getQuantity() : orderGoodsSkuVo.getChangeQuantity()) / orderGoodsSku.getQuantity())));
                    money.setTotalDiscountMoney(new BigDecimal(money.getTotalDiscountMoney().doubleValue() + (orderGoodsSku.getDiscountMoney().doubleValue() * (orderGoodsSkuVo.getChangeQuantity() == null ? orderGoodsSku.getQuantity() : orderGoodsSkuVo.getChangeQuantity()) / orderGoodsSku.getQuantity())));
                    money.setCostMoney(new BigDecimal(money.getCostMoney().doubleValue() + goodsSku.getPurchasePrice().doubleValue() * (orderGoodsSkuVo.getChangeQuantity() == null ? orderGoodsSku.getQuantity() : orderGoodsSkuVo.getChangeQuantity())));
                }
            }
        });
        money.setOrderMoney(new BigDecimal(money.getTotalMoney().doubleValue() - money.getTotalDiscountMoney().doubleValue()));
        money.setGrossMarginMoney(new BigDecimal(money.getOrderMoney().doubleValue() - money.getCostMoney().doubleValue()));
        return money;
    }

    /**
     * 新增结果订单/商品规格关系
     * @param storeId
     * @param resultOrderId
     * @param flag
     * @param orderGoodsSkuVos
     * @param orderGoodsSkuses
     */
    @Transactional
    public void addResultOrderGoodsSku(int storeId, String resultOrderId, int flag, List<OrderGoodsSkuVo> orderGoodsSkuVos, List<OrderGoodsSku> orderGoodsSkuses) {
        //新增结果单/商品规格关系
        OrderGoodsSkuVo ogsv = new OrderGoodsSkuVo(storeId, resultOrderId, 0);
        if (flag == 1) {
            orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
                OrderGoodsSku ogs = orderGoodsSkuses.stream().filter(orderGoodsSku -> orderGoodsSku.getId().equals(orderGoodsSkuVo.getId())).findFirst().get();
                ogsv.setType(ogs.getType());
                ogsv.setGoodsSkuId(ogs.getGoodsSkuId());
                ogsv.setQuantity(orderGoodsSkuVo.getChangeQuantity());
                ogsv.setFinishQuantity(orderGoodsSkuVo.getChangeQuantity());
                ogsv.setMoney(new BigDecimal(ogs.getMoney().doubleValue() * orderGoodsSkuVo.getChangeQuantity() / ogs.getQuantity()));
                ogsv.setDiscountMoney(new BigDecimal(ogs.getDiscountMoney().doubleValue() * orderGoodsSkuVo.getChangeQuantity() / ogs.getQuantity()));
                ogsv.setRemark(ogs.getRemark());
                if (myOrderGoodsSkuMapper.addOrderGoodsSku(ogsv) != 1) {
                    throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                }
            });
        } else if (flag == 2) {     //换货
            orderGoodsSkuses.stream().forEach(orderGoodsSku -> {
                ogsv.setType(orderGoodsSku.getType());
                ogsv.setGoodsSkuId(orderGoodsSku.getGoodsSkuId());
                ogsv.setQuantity(orderGoodsSku.getQuantity());
                ogsv.setFinishQuantity(orderGoodsSku.getQuantity());
                ogsv.setMoney(orderGoodsSku.getMoney());
                ogsv.setDiscountMoney(orderGoodsSku.getDiscountMoney());
                ogsv.setRemark(orderGoodsSku.getRemark());
                if (myOrderGoodsSkuMapper.addOrderGoodsSku(ogsv) != 1) {
                    throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                }
            });
        }

    }

    /**
     * 新增换货订单
     * @param flag
     * @param storeId
     * @param applyOrderType
     * @param applyOrderId
     * @param procurementApplyOrder
     * @param sellApplyOrder
     * @param storageOrderVo
     * @param orderGoodsSkuVos
     * @param orderGoodsSkuses
     */
    @Transactional
    public void addResultOrder(int flag, int storeId, byte applyOrderType, String applyOrderId, ProcurementApplyOrder procurementApplyOrder, SellApplyOrder sellApplyOrder, StorageOrderVo storageOrderVo, List<OrderGoodsSkuVo> orderGoodsSkuVos, List<OrderGoodsSku> orderGoodsSkuses) {
        String resultOrderId = "";
        SellResultOrder money;
        if (flag == 1) {
            //新增采购换货单
            resultOrderId = "CGHHD_" + UUID.randomUUID().toString().replace("-", "");
            money = getMoneyMethod(flag, storeId, myProcurementMapper.findApplyOrderDetailById(new ProcurementApplyOrderVo(storeId, applyOrderId)).getDetails(), orderGoodsSkuses);
            if (myProcurementMapper.addResultOrder(new ProcurementResultOrderVo(
                    storeId,
                    resultOrderId,
                    applyOrderType,
                    new Date(),
                    applyOrderId,
                    (byte) 1,
                    procurementApplyOrder.getInTotalQuantity() - procurementApplyOrder.getOutTotalQuantity(),
                    money.getTotalMoney(),
                    money.getTotalDiscountMoney(),
                    money.getOrderMoney(),
                    storageOrderVo.getUserId(),
                    storageOrderVo.getRemark())) != 1) {
                throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
            }
        } else if (flag == 2) {
            //新增销售换货单
            resultOrderId = "XXHHD_" + UUID.randomUUID().toString().replace("-", "");
            money = getMoneyMethod(flag, storeId, mySellMapper.findApplyOrderDetailById(new SellApplyOrderVo(storeId, applyOrderId)).getDetails(), orderGoodsSkuses);
            if (mySellMapper.addResultOrder(new SellResultOrderVo(
                    storeId,
                    resultOrderId,
                    applyOrderType,
                    new Date(),
                    applyOrderId,
                    (byte) 1,
                    sellApplyOrder.getOutTotalQuantity() - sellApplyOrder.getInTotalQuantity(),
                    money.getTotalMoney(),
                    money.getTotalDiscountMoney(),
                    money.getOrderMoney(),
                    money.getCostMoney(),
                    money.getGrossMarginMoney(),
                    storageOrderVo.getUserId(),
                    storageOrderVo.getRemark())) != 1) {
                throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
            }
        }
        //新增换货单/商品规格关系
        addResultOrderGoodsSku(storeId, resultOrderId, 2, orderGoodsSkuVos, orderGoodsSkuses);
    }

    /**
     * 新增收/发货单
     * @param storageOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addStorageOrder(StorageOrderVo storageOrderVo) {
        //获取参数
        Integer storeId = storageOrderVo.getStoreId();
        String applyOrderId = storageOrderVo.getApplyOrderId();
        String resultOrderId = "";
        Integer quantity = storageOrderVo.getQuantity();
        ProcurementApplyOrderVo procurementApplyOrderVo = new ProcurementApplyOrderVo();        //参数
        ProcurementApplyOrder procurementApplyOrder = new ProcurementApplyOrder();      //数据库
        SellApplyOrderVo sellApplyOrderVo = new SellApplyOrderVo();     //参数
        SellApplyOrder sellApplyOrder = new SellApplyOrder();       //数据库
        SellResultOrder money = new SellResultOrder();
        List<OrderGoodsSkuVo> orderGoodsSkuVos;
        byte applyOrderType;
        byte orderStatus;
        byte applyOrderStatus;
        Integer inTotalQuantity;
        Integer inReceivedQuantity;
        Integer inNotReceivedQuantity;
        Integer outTotalQuantity;
        Integer outSentQuantity;
        Integer outNotSentQuantity;
        //判断参数
        if (storageOrderVo.getProcurementApplyOrderVo() != null && storageOrderVo.getSellApplyOrderVo() == null) {
            procurementApplyOrderVo = storageOrderVo.getProcurementApplyOrderVo();
            orderGoodsSkuVos = procurementApplyOrderVo.getDetails();
            //获取对应的采购申请单
            procurementApplyOrder = myProcurementMapper.findApplyOrderById(new ProcurementApplyOrderVo(storeId, applyOrderId));
            if (procurementApplyOrder == null || orderGoodsSkuVos.size() == 0 || quantity <= 0) {
                LOG.info("新增收/发货单，参数不匹配，来源订单：【{}】，商品规格数量：【{}】，收/发货数量：【{}】", procurementApplyOrder, orderGoodsSkuVos.size(), quantity);
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
            }
            applyOrderType = procurementApplyOrder.getType();
            orderStatus = procurementApplyOrder.getOrderStatus();
            inTotalQuantity = procurementApplyOrder.getInTotalQuantity();
            inReceivedQuantity = procurementApplyOrder.getInReceivedQuantity();
            inNotReceivedQuantity = procurementApplyOrder.getInNotReceivedQuantity();
            outTotalQuantity = procurementApplyOrder.getOutTotalQuantity();
            outSentQuantity = procurementApplyOrder.getOutSentQuantity();
            outNotSentQuantity = procurementApplyOrder.getOutNotSentQuantity();
        } else if (storageOrderVo.getProcurementApplyOrderVo() == null && storageOrderVo.getSellApplyOrderVo() != null) {
            sellApplyOrderVo = storageOrderVo.getSellApplyOrderVo();
            orderGoodsSkuVos = sellApplyOrderVo.getDetails();
            //获取对应的销售申请单
            sellApplyOrder = mySellMapper.findApplyOrderById(new SellApplyOrderVo(storeId, applyOrderId));
            if (sellApplyOrder == null || orderGoodsSkuVos.size() == 0 || quantity <= 0) {
                LOG.info("新增收/发货单，参数不匹配，来源订单：【{}】，商品规格数量：【{}】，收/发货数量：【{}】", sellApplyOrder, orderGoodsSkuVos.size(), quantity);
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
            }
            applyOrderType = sellApplyOrder.getType();
            orderStatus = sellApplyOrder.getOrderStatus();
            inTotalQuantity = sellApplyOrder.getInTotalQuantity();
            inReceivedQuantity = sellApplyOrder.getInReceivedQuantity();
            inNotReceivedQuantity = sellApplyOrder.getInNotReceivedQuantity();
            outTotalQuantity = sellApplyOrder.getOutTotalQuantity();
            outSentQuantity = sellApplyOrder.getOutSentQuantity();
            outNotSentQuantity = sellApplyOrder.getOutNotSentQuantity();
        } else {
            LOG.info("新增收/发货单，参数不匹配，采购/销售申请单实体为空");
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //数据库获取申请订单/商品规格关系
        List<OrderGoodsSku> orderGoodsSkuses = myOrderGoodsSkuMapper.findAllOrderGoodsSku(new OrderGoodsSkuVo(storeId, applyOrderId));
        //判断新增收/发货单类型
        Byte type = storageOrderVo.getType();
        switch (type) {
            //采购订单收货单
            case 1:
                storageOrderVo.setId("CGDD_SHD_" + UUID.randomUUID().toString().replace("-", ""));
                //判断完成情况
                if (quantity == inNotReceivedQuantity && quantity + inReceivedQuantity == inTotalQuantity) {        //全部完成
                    applyOrderStatus = 3;
                } else if (quantity < inNotReceivedQuantity && quantity + inReceivedQuantity < inTotalQuantity) {      //部分完成
                    applyOrderStatus = 2;
                } else {        //错误
                    LOG.info("新增采购订单收货单，收货数量不正确【{}】", quantity);
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                //修改对应的采购订单单据状态和商品完成数量
                if (applyOrderType == 1 && (orderStatus == 1 || orderStatus == 2)) {
                    storageOrderVo.setOrderStatus(applyOrderStatus);
                    if (myProcurementMapper.updateApplyOrderOrderStatusAndQuantity(storageOrderVo) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                } else {
                    LOG.info("新增采购订单收货单，来源订单类型或状态不正确【{}】, 【{}】, 【{}】", applyOrderId, applyOrderType, applyOrderStatus);
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                //修改商品规格完成情况和修改商品规格库存
                updateGoodsSku(type, storeId, quantity, orderGoodsSkuVos);
                //新增采购入库单
                resultOrderId = "CGRKD_" + UUID.randomUUID().toString().replace("-", "");
                money = getMoneyMethod(1, storeId, orderGoodsSkuVos, orderGoodsSkuses);
                if (myProcurementMapper.addResultOrder(new ProcurementResultOrderVo(
                        storeId,
                        resultOrderId,
                        applyOrderType,
                        new Date(),
                        applyOrderId,
                        (byte) 1,
                        quantity,
                        money.getTotalMoney(),
                        money.getTotalDiscountMoney(),
                        money.getOrderMoney(),
                        storageOrderVo.getUserId(),
                        storageOrderVo.getRemark())) != 1) {
                    throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                }
                //新增采购入库单/商品规格关系
                addResultOrderGoodsSku(storeId, resultOrderId, 1, orderGoodsSkuVos, orderGoodsSkuses);
                break;
            //【销售：退】【采购：换/销售：换】货申请收货单
            case 2:
                storageOrderVo.setId("THHSQD_SHD_" + UUID.randomUUID().toString().replace("-", ""));
                //判断完成情况
                if (quantity == inNotReceivedQuantity && quantity + inReceivedQuantity == inTotalQuantity) {        //全部完成
                    if (orderStatus == 1 || orderStatus == 2) {     //销售：退
                        applyOrderStatus = 3;
                    } else if (orderStatus == 8 || orderStatus == 11) {     //采购：换/销售：换
                        applyOrderStatus = 14;
                    } else if (orderStatus == 9 || orderStatus == 12) {     //采购：换/销售：换
                        applyOrderStatus = 15;
                    } else if (orderStatus == 7 || orderStatus == 10) {     //采购：换/销售：换
                        applyOrderStatus = 13;
                    } else {
                        return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                    }
                } else if (quantity < inNotReceivedQuantity && quantity + inReceivedQuantity < inTotalQuantity) {      //部分完成
                    if (orderStatus == 1 || orderStatus == 2) {     //销售：退
                        applyOrderStatus = 2;
                    } else if (orderStatus == 7 || orderStatus == 10) {     //采购：换/销售：换
                        applyOrderStatus = 10;
                    } else if (orderStatus == 8 || orderStatus == 11) {     //采购：换/销售：换
                        applyOrderStatus = 11;
                    } else if (orderStatus == 9 || orderStatus == 12) {     //采购：换/销售：换
                        applyOrderStatus = 12;
                    } else {
                        return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                    }
                } else {        //错误
                    LOG.info("新增退换货申请单收货单，收货数量不正确【{}】", quantity);
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                //修改对应的采购/销售申请单单据状态和商品完成数量
                storageOrderVo.setOrderStatus(applyOrderStatus);
                if (applyOrderType == 3 && (orderStatus == 7 || orderStatus == 8 || orderStatus == 9 || orderStatus == 10 || orderStatus == 11 || orderStatus == 12)) {       //单据类型为：采购换货申请单 并且 单据状态为：未收未发、未收部分发、未收已发、部分收未发、部分收部分发、部分收已发
                    //修改采购换货申请单
                    if (myProcurementMapper.updateApplyOrderOrderStatusAndQuantity(storageOrderVo) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                } else if ((applyOrderType == 3 && (orderStatus == 1 || orderStatus == 2)) ||      //单据类型为：销售退货申请单 并且 单据状态为：未收或部分收
                        (applyOrderType == 4 && (orderStatus == 7 || orderStatus == 8 || orderStatus == 9 || orderStatus == 10 || orderStatus == 11 || orderStatus == 12))) {       //单据类型为：销售换货申请单 并且 单据状态为：未收未发、未收部分发、未收已发、部分收未发、部分收部分发、部分收已发
                    //修改销售退货/换货申请单
                    if (mySellMapper.updateApplyOrderOrderStatusAndQuantity(storageOrderVo) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                } else {
                    LOG.info("新增退换货申请单收货单，来源订单类型或状态不正确【{}】, 【{}】, 【{}】", applyOrderId, applyOrderType, applyOrderStatus);
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                //修改商品规格完成情况和增加商品规格库存
                updateGoodsSku(type, storeId, quantity, orderGoodsSkuVos);
                if (applyOrderType == 3 && applyOrderStatus == 15) {        //采购换货申请单
                    //新增采购换货单
                    addResultOrder(1, storeId, applyOrderType, applyOrderId, procurementApplyOrder, sellApplyOrder, storageOrderVo, orderGoodsSkuVos, orderGoodsSkuses);
                } else if (applyOrderType == 3 && (applyOrderStatus == 2 || applyOrderStatus == 3)) {      //销售退货申请单
                    //新增销售退货单
                    resultOrderId = "XXTHD_" + UUID.randomUUID().toString().replace("-", "");
                    money = getMoneyMethod(2, storeId, orderGoodsSkuVos, orderGoodsSkuses);
                    if (mySellMapper.addResultOrder(new SellResultOrderVo(
                            storeId,
                            resultOrderId,
                            applyOrderType,
                            new Date(),
                            applyOrderId,
                            (byte) 1,
                            -quantity,
                            money.getTotalMoney(),
                            money.getTotalDiscountMoney(),
                            money.getOrderMoney(),
                            money.getCostMoney(),
                            money.getGrossMarginMoney(),
                            storageOrderVo.getUserId(),
                            storageOrderVo.getRemark())) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                    //新增销售退货单/商品规格关系
                    addResultOrderGoodsSku(storeId, resultOrderId, 1, orderGoodsSkuVos, orderGoodsSkuses);
                } else if (applyOrderType == 4 && applyOrderStatus == 15) {     //销售换货申请单
                    //新增销售换货单
                    addResultOrder(2, storeId, applyOrderType, applyOrderId, procurementApplyOrder, sellApplyOrder, storageOrderVo, orderGoodsSkuVos, orderGoodsSkuses);
                }
                break;
            //销售订单发货单
            case 3:
                if (sellApplyOrder.getClearStatus() != 1) {
                    return new CommonResponse(CommonResponse.CODE9, null, "销售订单需要结算完成之后发货");
                }
                storageOrderVo.setId("XXDD_FHD_" + UUID.randomUUID().toString().replace("-", ""));
                //判断完成情况
                if (quantity == outNotSentQuantity && quantity + outSentQuantity == outTotalQuantity) {        //全部完成
                    applyOrderStatus = 6;
                } else if (quantity < outNotSentQuantity && quantity + outSentQuantity < outTotalQuantity) {      //部分完成
                    applyOrderStatus = 5;
                } else {        //错误
                    LOG.info("新增销售订单发货单，发货数量不正确【{}】", quantity);
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                //修改对应的销售订单单据状态和商品完成数量
                if (applyOrderType == 2 && (orderStatus == 4 || orderStatus == 5)) {
                    storageOrderVo.setOrderStatus(applyOrderStatus);
                    if (mySellMapper.updateApplyOrderOrderStatusAndQuantity(storageOrderVo) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                } else {
                    LOG.info("新增销售订单发货单，来源订单类型或状态不正确【{}】, 【{}】, 【{}】", applyOrderId, applyOrderType, applyOrderStatus);
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                //修改商品规格完成情况和修改商品规格库存
                updateGoodsSku(type, storeId, quantity, orderGoodsSkuVos);
                //新增销售出库单
                resultOrderId = "XXCKD_" + UUID.randomUUID().toString().replace("-", "");
                money = getMoneyMethod(2, storeId, orderGoodsSkuVos, orderGoodsSkuses);
                if (mySellMapper.addResultOrder(new SellResultOrderVo(
                        storeId,
                        resultOrderId,
                        applyOrderType,
                        new Date(),
                        applyOrderId,
                        (byte) 1,
                        quantity,
                        money.getTotalMoney(),
                        money.getTotalDiscountMoney(),
                        money.getOrderMoney(),
                        money.getCostMoney(),
                        money.getGrossMarginMoney(),
                        storageOrderVo.getUserId(),
                        storageOrderVo.getRemark())) != 1) {
                    throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                }
                //新增销售出库单/商品规格关系
                addResultOrderGoodsSku(storeId, resultOrderId, 1, orderGoodsSkuVos, orderGoodsSkuses);
                //修改客户积分信息
                if (applyOrderStatus == 6) {        //发货完成
                    sellService.clientIntegralMethod(storeId, sellApplyOrder.getClientId(), resultOrderId, myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(storeId)), orderGoodsSkuVos);
                }
                break;
            //【采购：退】【采购：换/销售：换】货申请发货单
            case 4:
                storageOrderVo.setId("THHSQD_FHD_" + UUID.randomUUID().toString().replace("-", ""));
                //判断完成情况
                if (quantity == outNotSentQuantity && quantity + outSentQuantity == outTotalQuantity) {        //全部完成
                    if (orderStatus == 4 || orderStatus == 5) {      //采购：退
                        applyOrderStatus = 6;
                    } else if (orderStatus == 7 || orderStatus == 8) {      //采购：换/销售：换
                        applyOrderStatus = 9;
                    } else if (orderStatus == 10 || orderStatus == 11) {        //采购：换/销售：换
                        applyOrderStatus = 12;
                    } else if (orderStatus == 13 || orderStatus == 14) {        //采购：换/销售：换
                        applyOrderStatus = 15;
                    } else {
                        return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                    }
                } else if (quantity < outNotSentQuantity && quantity + outSentQuantity < outTotalQuantity) {      //部分完成
                    if (orderStatus == 4 || orderStatus == 5) {     //采购：退
                        applyOrderStatus = 5;
                    } else if (orderStatus == 7 || orderStatus == 8) {      //采购：换/销售：换
                        applyOrderStatus = 8;
                    } else if (orderStatus == 10 || orderStatus == 11) {        //采购：换/销售：换
                        applyOrderStatus = 11;
                    } else if (orderStatus == 13 || orderStatus == 14) {        //采购：换/销售：换
                        applyOrderStatus = 14;
                    } else {
                        return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                    }
                } else {        //错误
                    LOG.info("新增退换货申请单发货单，发货数量不正确【{}】", quantity);
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                storageOrderVo.setOrderStatus(applyOrderStatus);
                if ((applyOrderType == 2 && (orderStatus == 4 || orderStatus == 5)) ||      //单据类型：采购退货申请单 并且 单据状态：未发或部分发
                        (applyOrderType == 3 && (orderStatus == 7 || orderStatus == 8 || orderStatus == 10 || orderStatus == 11 || orderStatus == 13 || orderStatus == 14))) {      //单据类型为：采购换货申请单 并且 单据状态为：未收未发、未收部分发、部分收未发、部分收部分发、已收未发、已收部分发
                    //修改采购退/换货申请单
                    if (myProcurementMapper.updateApplyOrderOrderStatusAndQuantity(storageOrderVo) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                } else if (applyOrderType == 4 && (orderStatus == 7 || orderStatus == 8 || orderStatus == 10 || orderStatus == 11 || orderStatus == 13 || orderStatus == 14)) {
                    //修改销售换货申请单
                    if (mySellMapper.updateApplyOrderOrderStatusAndQuantity(storageOrderVo) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                } else {
                    LOG.info("新增退换货申请单发货单，来源订单类型或状态不正确【{}】, 【{}】, 【{}】", applyOrderId, applyOrderType, applyOrderStatus);
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                //修改商品规格完成情况和增加商品规格库存
                updateGoodsSku(type, storeId, quantity, orderGoodsSkuVos);
                if (applyOrderType == 3 && applyOrderStatus == 15) {        //采购换货申请单
                    //新增采购换货单
                    addResultOrder(1, storeId, applyOrderType, applyOrderId, procurementApplyOrder, sellApplyOrder, storageOrderVo, orderGoodsSkuVos, orderGoodsSkuses);
                } else if (applyOrderType == 2 && (applyOrderStatus == 5 || applyOrderStatus == 6)) {       //采购退货申请单
                    //新增采购退货单
                    resultOrderId = "CGTHD_" + UUID.randomUUID().toString().replace("-", "");
                    money = getMoneyMethod(1, storeId, orderGoodsSkuVos, orderGoodsSkuses);
                    if (myProcurementMapper.addResultOrder(new ProcurementResultOrderVo(
                            storeId,
                            resultOrderId,
                            applyOrderType,
                            new Date(),
                            applyOrderId,
                            (byte) 1,
                            -quantity,
                            money.getTotalMoney(),
                            money.getTotalDiscountMoney(),
                            money.getOrderMoney(),
                            storageOrderVo.getUserId(),
                            storageOrderVo.getRemark())) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                    //新增采购退货单/商品规格关系
                    addResultOrderGoodsSku(storeId, resultOrderId, 1, orderGoodsSkuVos, orderGoodsSkuses);
                } else if (applyOrderType == 4 && applyOrderStatus == 15) {     //销售换货申请单
                    //新增销售换货单
                    addResultOrder(2, storeId, applyOrderType, applyOrderId, procurementApplyOrder, sellApplyOrder, storageOrderVo, orderGoodsSkuVos, orderGoodsSkuses);
                }
                break;
            default:
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        storageOrderVo.setCreateTime(new Date());
        storageOrderVo.setOrderStatus((byte) 1);
        //新增收/发货单
        if (myStorageMapper.addStorageOrder(storageOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }


    /**
     * 红冲收/发货单
     * @param storageOrderVo
     * @return
     */
    @Transactional
    public CommonResponse redDashed(StorageOrderVo storageOrderVo) {
        Integer storeId = storageOrderVo.getStoreId();
        //修改单据状态
        if (myStorageMapper.redDashed(storageOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        //获取该订单
        storageOrderVo = myStorageMapper.findStorageOrderById(storageOrderVo);
        //新增红冲单
        storageOrderVo.setStoreId(storeId);
        storageOrderVo.setId("HC_" + storageOrderVo.getId());
        storageOrderVo.setCreateTime(new Date());
        storageOrderVo.setOrderStatus((byte) -2);
        storageOrderVo.setQuantity(-storageOrderVo.getQuantity());
        if (myStorageMapper.addStorageOrder(storageOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }


    /**
     * 根据type查询所有收/发单
     * @param storageOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllStorageOrder(StorageOrderVo storageOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountStorageOrder(storageOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<StorageOrderVo> storageOrderVos = myStorageMapper.findAllPagedStorageOrder(storageOrderVo, pageVo);
        if (storageOrderVos.size() > 0) {
            //补上仓库名
            List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(storageOrderVo.getStoreId()));
            storageOrderVos.stream().forEach(vo -> {
                warehouses.stream().forEach(warehouse -> {
                    if (vo.getProcurementApplyOrderVo().getInWarehouseId() == warehouse.getId()) {
                        vo.getProcurementApplyOrderVo().setInWarehouseName(warehouse.getName());
                    }
                    if (vo.getProcurementApplyOrderVo().getOutWarehouseId() == warehouse.getId()) {
                        vo.getProcurementApplyOrderVo().setOutWarehouseName(warehouse.getName());
                    }
                });
            });
        }
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "id"));
        titles.add(new Title("单据类型", "type"));
        titles.add(new Title("单据日期", "createTime"));
        /*titles.add(new Title("单据状态", "orderStatus"));*/
        titles.add(new Title("来源订单", "applyOrderId"));
        titles.add(new Title("供应商", "procurementApplyOrderVo.supplierName"));
        if (storageOrderVo.getType() == 1 || storageOrderVo.getType() == 2) {
            titles.add(new Title("入库仓库", "procurementApplyOrderVo.inWarehouseName"));
        } else if (storageOrderVo.getType() == 3 || storageOrderVo.getType() == 4) {
            titles.add(new Title("出库仓库", "procurementApplyOrderVo.outWarehouseName"));
        }
        titles.add(new Title("本单数量", "quantity"));
        titles.add(new Title("物流公司", "logisticsCompany"));
        titles.add(new Title("运单号", "waybillNumber"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, storageOrderVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }
}
