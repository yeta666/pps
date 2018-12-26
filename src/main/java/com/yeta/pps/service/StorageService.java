package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.*;
import com.yeta.pps.po.GoodsSku;
import com.yeta.pps.po.OrderGoodsSku;
import com.yeta.pps.po.SellResultOrder;
import com.yeta.pps.po.Warehouse;
import com.yeta.pps.util.*;
import com.yeta.pps.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    private InventoryUtil inventoryUtil;

    @Autowired
    private IntegralUtil integralUtil;

    /**
     * 获取申请订单应该改为哪种单据状态的方法
     * @param type
     * @param change
     * @param total
     * @param finish
     * @param notFinish
     * @param oldApplyOrderStatus
     * @return
     */
    @Transactional
    public byte getApplyOrderStatusMethod(int type, int change, int total, int finish, int notFinish, int oldApplyOrderStatus) {
        byte applyOrderStatus;
        //判断完成情况
        if (change == notFinish && change + finish == total) {      //全部完成
            switch (type) {
                case 1:
                    if (oldApplyOrderStatus == 1 || oldApplyOrderStatus == 2) {     //采购订单
                        applyOrderStatus = 3;
                    } else {
                        throw new CommonException(CommonResponse.CODE0, "申请订单状态错误");
                    }
                    break;
                case 2:
                    if (oldApplyOrderStatus == 1 || oldApplyOrderStatus == 2) {     //销售：退
                        applyOrderStatus = 3;
                    } else if (oldApplyOrderStatus == 8 || oldApplyOrderStatus == 11) {     //采购：换/销售：换
                        applyOrderStatus = 14;
                    } else if (oldApplyOrderStatus == 9 || oldApplyOrderStatus == 12) {     //采购：换/销售：换
                        applyOrderStatus = 15;
                    } else if (oldApplyOrderStatus == 7 || oldApplyOrderStatus == 10) {     //采购：换/销售：换
                        applyOrderStatus = 13;
                    } else {
                        throw new CommonException(CommonResponse.CODE0, "申请订单状态错误");
                    }
                    break;
                case 3:
                    if (oldApplyOrderStatus == 4 || oldApplyOrderStatus == 5) {     //销售订单
                        applyOrderStatus = 6;
                    } else {
                        throw new CommonException(CommonResponse.CODE0, "申请订单状态错误");
                    }
                    break;
                case 4:
                    if (oldApplyOrderStatus == 4 || oldApplyOrderStatus == 5) {      //采购：退
                        applyOrderStatus = 6;
                    } else if (oldApplyOrderStatus == 7 || oldApplyOrderStatus == 8) {      //采购：换/销售：换
                        applyOrderStatus = 9;
                    } else if (oldApplyOrderStatus == 10 || oldApplyOrderStatus == 11) {        //采购：换/销售：换
                        applyOrderStatus = 12;
                    } else if (oldApplyOrderStatus == 13 || oldApplyOrderStatus == 14) {        //采购：换/销售：换
                        applyOrderStatus = 15;
                    } else {
                        throw new CommonException(CommonResponse.CODE0, "申请订单状态错误");
                    }
                    break;
                default:
                    throw new CommonException(CommonResponse.CODE0, "收/发货单据类型错误");
            }
        } else if (change < notFinish && change + finish < total) {     //部分完成
            switch (type) {
                case 1:
                    if (oldApplyOrderStatus == 1 || oldApplyOrderStatus == 2) {        //采购订单
                        applyOrderStatus = 2;
                    } else {
                        throw new CommonException(CommonResponse.CODE0, "申请订单状态错误");
                    }
                    break;
                case 2:
                    if (oldApplyOrderStatus == 1 || oldApplyOrderStatus == 2) {     //销售：退
                        applyOrderStatus = 2;
                    } else if (oldApplyOrderStatus == 7 || oldApplyOrderStatus == 10) {     //采购：换/销售：换
                        applyOrderStatus = 10;
                    } else if (oldApplyOrderStatus == 8 || oldApplyOrderStatus == 11) {     //采购：换/销售：换
                        applyOrderStatus = 11;
                    } else if (oldApplyOrderStatus == 9 || oldApplyOrderStatus == 12) {     //采购：换/销售：换
                        applyOrderStatus = 12;
                    } else {
                        throw new CommonException(CommonResponse.CODE0, "申请订单状态错误");
                    }
                    break;
                case 3:
                    if (oldApplyOrderStatus == 4 || oldApplyOrderStatus == 5) {      //销售订单
                        applyOrderStatus = 5;
                    } else {
                        throw new CommonException(CommonResponse.CODE0, "申请订单状态错误");
                    }
                    break;
                case 4:
                    if (oldApplyOrderStatus == 4 || oldApplyOrderStatus == 5) {     //采购：退
                        applyOrderStatus = 5;
                    } else if (oldApplyOrderStatus == 7 || oldApplyOrderStatus == 8) {      //采购：换/销售：换
                        applyOrderStatus = 8;
                    } else if (oldApplyOrderStatus == 10 || oldApplyOrderStatus == 11) {        //采购：换/销售：换
                        applyOrderStatus = 11;
                    } else if (oldApplyOrderStatus == 13 || oldApplyOrderStatus == 14) {        //采购：换/销售：换
                        applyOrderStatus = 14;
                    } else {
                        throw new CommonException(CommonResponse.CODE0, "申请订单状态错误");
                    }
                    break;
                default:
                    throw new CommonException(CommonResponse.CODE0, "收/发货单据类型错误");
            }
        } else {        //错误
            throw new CommonException(CommonResponse.CODE0, "收/发货数量错误");
        }
        return applyOrderStatus;
    }

    /**
     * 修改申请订单单据状态和完成数量的方法
     * @param flag 1：采购，2：销售
     * @param applyOrderStatus
     * @param storageOrderVo
     */
    @Transactional
    public void updateApplyOrderMethod(int flag, byte applyOrderStatus, StorageOrderVo storageOrderVo) {
        storageOrderVo.setOrderStatus(applyOrderStatus);
        switch (flag) {
            case 1:
                if (myProcurementMapper.updateApplyOrderOrderStatusAndQuantity(storageOrderVo) != 1) {
                    throw new CommonException(CommonResponse.CODE0, "修改申请订单单据状态和完成数量失败");
                }
                break;
            case 2:
                if (mySellMapper.updateApplyOrderOrderStatusAndQuantity(storageOrderVo) != 1) {
                    throw new CommonException(CommonResponse.CODE0, "修改申请订单单据状态和完成数量失败");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 修改商品规格完成情况的方法
     * @param storeId
     * @param quantity
     * @param vos
     */
    @Transactional
    public void updateOrderGoodsSkuMethod(int storeId, int quantity, List<OrderGoodsSkuVo> vos) {
        OrderGoodsSku check = new OrderGoodsSku(quantity, 0);
        vos.stream().forEach(vo -> {
            //判断参数
            if (vo.getId() == null || vo.getGoodsSkuId() == null || vo.getChangeQuantity() == null) {
                throw new CommonException(CommonResponse.CODE0, "商品规格参数不匹配");
            }
            vo.setStoreId(storeId);
            //修改商品规格完成情况
            if (myOrderGoodsSkuMapper.updateOrderGoodsSku(vo) != 1) {
                throw new CommonException(CommonResponse.CODE0, "修改商品规格完成情况失败");
            }
            //统计数量
            check.setFinishQuantity(check.getFinishQuantity() + vo.getChangeQuantity());
        });
        if (check.getQuantity() != check.getFinishQuantity()) {
            throw new CommonException(CommonResponse.CODE0, "商品规格数量之和与总数量不对应");
        }
    }

    /**
     * 修改账面库存的方法
     * @param storageOrderVo
     * @param pVo
     * @param sVo
     */
    @Transactional
    public void updateBookInventoryMethod(StorageOrderVo storageOrderVo, ProcurementApplyOrderVo pVo, SellApplyOrderVo sVo) {
        List<OrderGoodsSkuVo> vos = null;
        Integer inWarehouseId = null;
        Integer outWarehouseId = null;
        if (pVo != null && sVo == null) {       //采购相关
            vos = pVo.getDetails();
            inWarehouseId = pVo.getInWarehouseId();
            outWarehouseId = pVo.getOutWarehouseId();
        } else if (pVo == null && sVo != null) {        //销售相关
            vos = sVo.getDetails();
            inWarehouseId = sVo.getInWarehouseId();
            outWarehouseId = sVo.getOutWarehouseId();
        }
        for (OrderGoodsSkuVo vo : vos) {
            if (vo.getType() == 1) {        //入库
                inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storageOrderVo.getStoreId(), inWarehouseId, vo.getGoodsSkuId(), 0, 0, vo.getQuantity()));
            } else if (vo.getType() == 0) {     //出库
                inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storageOrderVo.getStoreId(), outWarehouseId, vo.getGoodsSkuId(), 0, 0, vo.getQuantity()));
            }
        }
    }

    /**
     * 获取各种金额的方法
     * @param flag
     * @param orderGoodsSkuVos
     * @param vos
     * @return
     */
    @Transactional
    public SellResultOrder getMoneyMethod(int flag, List<OrderGoodsSkuVo> orderGoodsSkuVos, List<OrderGoodsSkuVo> vos) {
        SellResultOrder money = new SellResultOrder(new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0));
        //遍历这次要修改的商品规格
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            Optional<OrderGoodsSkuVo> optional = vos.stream().filter(vo -> vo.getId().toString().equals(orderGoodsSkuVo.getId().toString())).findFirst();
            if (!optional.isPresent()) {
                throw new CommonException("商品规格编号错误");
            }
            OrderGoodsSkuVo vo = optional.get();
            int finalQuantity = orderGoodsSkuVo.getChangeQuantity() == null ? vo.getQuantity() : orderGoodsSkuVo.getChangeQuantity();
            //修改金额和数量
            orderGoodsSkuVo.setQuantity(finalQuantity);
            orderGoodsSkuVo.setFinishQuantity(finalQuantity);
            orderGoodsSkuVo.setNotFinishQuantity(0);
            orderGoodsSkuVo.setMoney(new BigDecimal(vo.getMoney().doubleValue() * finalQuantity / vo.getQuantity()));
            orderGoodsSkuVo.setDiscountMoney(new BigDecimal(vo.getDiscountMoney().doubleValue() * finalQuantity / vo.getQuantity()));
            if (flag == 1) {        //采购
                if (orderGoodsSkuVo.getType() == 1) {       //入库
                    money.setTotalMoney(new BigDecimal(money.getTotalMoney().doubleValue() + (vo.getMoney().doubleValue() * finalQuantity / vo.getQuantity())));
                    money.setTotalDiscountMoney(new BigDecimal(money.getTotalDiscountMoney().doubleValue() + (vo.getDiscountMoney().doubleValue() * finalQuantity / vo.getQuantity())));
                    money.setCostMoney(new BigDecimal(money.getCostMoney().doubleValue() + vo.getGoodsSkuPurchasePrice().doubleValue() * finalQuantity));
                } else if (orderGoodsSkuVo.getType() == 0) {        //出库
                    money.setTotalMoney(new BigDecimal(money.getTotalMoney().doubleValue() - (vo.getMoney().doubleValue() * finalQuantity / vo.getQuantity())));
                    money.setTotalDiscountMoney(new BigDecimal(money.getTotalDiscountMoney().doubleValue() - (vo.getDiscountMoney().doubleValue() * finalQuantity / vo.getQuantity())));
                    money.setCostMoney(new BigDecimal(money.getCostMoney().doubleValue() - vo.getGoodsSkuPurchasePrice().doubleValue() * finalQuantity));
                }
            } else if (flag == 2) {     //销售
                if (orderGoodsSkuVo.getType() == 1) {       //入库
                    money.setTotalMoney(new BigDecimal(money.getTotalMoney().doubleValue() - (vo.getMoney().doubleValue() * finalQuantity / vo.getQuantity())));
                    money.setTotalDiscountMoney(new BigDecimal(money.getTotalDiscountMoney().doubleValue() - (vo.getDiscountMoney().doubleValue() * finalQuantity / vo.getQuantity())));
                    money.setCostMoney(new BigDecimal(money.getCostMoney().doubleValue() - vo.getGoodsSkuPurchasePrice().doubleValue() * finalQuantity));
                } else if (orderGoodsSkuVo.getType() == 0) {        //出库
                    money.setTotalMoney(new BigDecimal(money.getTotalMoney().doubleValue() + (vo.getMoney().doubleValue() * finalQuantity / vo.getQuantity())));
                    money.setTotalDiscountMoney(new BigDecimal(money.getTotalDiscountMoney().doubleValue() + (vo.getDiscountMoney().doubleValue() * finalQuantity / vo.getQuantity())));
                    money.setCostMoney(new BigDecimal(money.getCostMoney().doubleValue() + vo.getGoodsSkuPurchasePrice().doubleValue() * finalQuantity));
                }
            }
        });
        money.setOrderMoney(new BigDecimal(money.getTotalMoney().doubleValue() - money.getTotalDiscountMoney().doubleValue()));
        money.setGrossMarginMoney(new BigDecimal(money.getOrderMoney().doubleValue() - money.getCostMoney().doubleValue()));
        return money;
    }

    /**
     * 新增结果订单的方法
     * @param storageOrderVo
     * @param pVo
     * @param sVo
     * @return
     */
    @Transactional
    public String addResultOrderMethod(StorageOrderVo storageOrderVo, ProcurementApplyOrderVo pVo, SellApplyOrderVo sVo) {
        List<OrderGoodsSkuVo> orderGoodsSkuVos;
        SellResultOrder money;
        String resultOrderId = null;
        Integer quantity = null;

        if (pVo != null && sVo == null) {       //采购相关
            orderGoodsSkuVos = storageOrderVo.getProcurementApplyOrderVo().getDetails();
            if (pVo.getType() == 1) {       //采购订单
                resultOrderId = "CGRKD_" + UUID.randomUUID().toString().replace("-", "");
                quantity = storageOrderVo.getQuantity();
            } else if (pVo.getType() == 2) {        //采购退货申请单
                resultOrderId = "CGTHD_" + UUID.randomUUID().toString().replace("-", "");
                quantity = -storageOrderVo.getQuantity();
            } else if (pVo.getType() == 3) {        //采购换货申请单
                resultOrderId = "CGHHD_" + UUID.randomUUID().toString().replace("-", "");
                quantity = pVo.getInTotalQuantity() - pVo.getOutTotalQuantity();
                orderGoodsSkuVos = pVo.getDetails();
            }
            money = getMoneyMethod(1, orderGoodsSkuVos, pVo.getDetails());
            if (myProcurementMapper.addResultOrder(
                    new ProcurementResultOrderVo(
                            storageOrderVo.getStoreId(),
                            resultOrderId,
                            pVo.getType(),
                            new Date(),
                            pVo.getId(),
                            (byte) 1,
                            quantity,
                            money.getTotalMoney(),
                            money.getTotalDiscountMoney(),
                            money.getOrderMoney(),
                            storageOrderVo.getUserId(),
                            storageOrderVo.getRemark()
                    )) != 1) {
                throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
            }
        } else if (pVo == null && sVo != null) {        //销售相关
            orderGoodsSkuVos = storageOrderVo.getSellApplyOrderVo().getDetails();
            if (sVo.getType() == 2) {       //销售订单
                resultOrderId = "XXCKD_" + UUID.randomUUID().toString().replace("-", "");
                quantity = storageOrderVo.getQuantity();
            } else if (sVo.getType() == 3) {        //销售退货申请单
                resultOrderId = "XXTHD_" + UUID.randomUUID().toString().replace("-", "");
                quantity = -storageOrderVo.getQuantity();
            } else if (sVo.getType() == 4) {        //销售换货申请单
                resultOrderId = "XXHHD_" + UUID.randomUUID().toString().replace("-", "");
                quantity = sVo.getOutTotalQuantity() - sVo.getInTotalQuantity();
                orderGoodsSkuVos = sVo.getDetails();
            }
            money = getMoneyMethod(2, orderGoodsSkuVos, sVo.getDetails());
            if (mySellMapper.addResultOrder(
                    new SellResultOrderVo(
                            storageOrderVo.getStoreId(),
                            resultOrderId,
                            sVo.getType(),
                            new Date(),
                            sVo.getId(),
                            (byte) 1,
                            quantity,
                            money.getTotalMoney(),
                            money.getTotalDiscountMoney(),
                            money.getOrderMoney(),
                            money.getCostMoney(),
                            money.getGrossMarginMoney(),
                            storageOrderVo.getUserId(),
                            storageOrderVo.getRemark()
                    )) != 1) {
                throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
            }
        }
        return resultOrderId;
    }

    /**
     * 新增结果订单/商品规格关系的方法
     * @param storeId
     * @param resultOrderId
     * @param orderGoodsSkuVos
     */
    @Transactional
    public void addOrderGoodsSkuMethod(Integer storeId, String resultOrderId, List<OrderGoodsSkuVo> orderGoodsSkuVos) {
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            orderGoodsSkuVo.setStoreId(storeId);
            orderGoodsSkuVo.setOrderId(resultOrderId);
            myOrderGoodsSkuMapper.addOrderGoodsSku(orderGoodsSkuVo);
        });
    }

    /**
     * 新增采购订单收货单
     * @param storageOrderVo
     * @param pVo
     * @param sVo
     */
    @Transactional
    public void cgshd(StorageOrderVo storageOrderVo, ProcurementApplyOrderVo pVo, SellApplyOrderVo sVo) {
        //获取参数
        int storeId = storageOrderVo.getStoreId();
        List<OrderGoodsSkuVo> orderGoodsSkuVos = storageOrderVo.getProcurementApplyOrderVo().getDetails();
        int quantity = storageOrderVo.getQuantity();
        byte type = storageOrderVo.getType();

        //设置单据编号
        storageOrderVo.setId("CGDD_SHD_" + UUID.randomUUID().toString().replace("-", ""));

        //获取申请订单应该改为哪种单据状态
        byte applyOrderStatus = getApplyOrderStatusMethod(type, quantity, pVo.getInTotalQuantity(), pVo.getInReceivedQuantity(), pVo.getInNotReceivedQuantity(), pVo.getOrderStatus());

        //修改申请订单
        updateApplyOrderMethod(1, applyOrderStatus, storageOrderVo);

        //修改商品规格完成情况
        updateOrderGoodsSkuMethod(storeId, quantity, orderGoodsSkuVos);

        //修改库存相关
        for (OrderGoodsSkuVo vo : orderGoodsSkuVos) {
            Integer warehouseId = pVo.getInWarehouseId();
            Integer goodsSkuId = vo.getGoodsSkuId();
            Integer changeQuantity = vo.getChangeQuantity();

            //增加实物库存、可用库存、账面库存
            inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, warehouseId, goodsSkuId, changeQuantity, changeQuantity, changeQuantity));

            //减少待收货数量
            inventoryUtil.updateNotQuantityMethod(0, new WarehouseGoodsSkuVo(storeId, warehouseId, goodsSkuId, 0, changeQuantity));
        }

        //新增结果订单
        String resultOrderId = addResultOrderMethod(storageOrderVo, pVo, sVo);

        //新增结果订单/商品规格关系
        addOrderGoodsSkuMethod(storeId, resultOrderId, orderGoodsSkuVos);
    }

    /**
     * 新增退货或申请收货单
     * @param storageOrderVo
     * @param pVo
     * @param sVo
     */
    @Transactional
    public void thhsqshd(StorageOrderVo storageOrderVo, ProcurementApplyOrderVo pVo, SellApplyOrderVo sVo) {
        //获取参数
        int storeId = storageOrderVo.getStoreId();
        int quantity = storageOrderVo.getQuantity();
        byte type = storageOrderVo.getType();

        //判断具体是哪种收货
        if (pVo != null && sVo == null) {       //采购换货收货
            //采购换货收货前必须完成发货
            if (pVo.getOrderStatus() != 9 && pVo.getOrderStatus() != 12) {
                throw new CommonException("采购换货申请单收货前必须完成发货");
            }

            //获取商品规格
            List<OrderGoodsSkuVo> orderGoodsSkuVos = storageOrderVo.getProcurementApplyOrderVo().getDetails();

            //获取申请订单应该改为哪种单据状态
            byte applyOrderStatus = getApplyOrderStatusMethod(type, quantity, pVo.getInTotalQuantity(), pVo.getInReceivedQuantity(), pVo.getInNotReceivedQuantity(), pVo.getOrderStatus());

            //修改申请订单
            updateApplyOrderMethod(1, applyOrderStatus, storageOrderVo);

            //修改商品规格完成情况
            updateOrderGoodsSkuMethod(storeId, quantity, orderGoodsSkuVos);

            //修改库存相关
            for (OrderGoodsSkuVo vo : orderGoodsSkuVos) {
                //增加实物库存、可用库存
                inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, pVo.getInWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), vo.getChangeQuantity(), 0));

                //减少待收货数量
                inventoryUtil.updateNotQuantityMethod(0, new WarehouseGoodsSkuVo(storeId, pVo.getInWarehouseId(), vo.getGoodsSkuId(), 0, vo.getChangeQuantity()));
            }

            //如果换货完成
            if (applyOrderStatus == 15) {
                //修改商品规格账面库存
                updateBookInventoryMethod(storageOrderVo, pVo, sVo);

                //新增结果订单
                String resultOrderId = addResultOrderMethod(storageOrderVo, pVo, sVo);

                //新增结果订单/商品规格关系
                addOrderGoodsSkuMethod(storeId, resultOrderId, pVo.getDetails());
            }

        } else if (pVo == null && sVo != null) {        //销售相关
            //获取商品规格
            List<OrderGoodsSkuVo> orderGoodsSkuVos = storageOrderVo.getSellApplyOrderVo().getDetails();

            //获取申请订单应该改为哪种单据状态
            byte applyOrderStatus = getApplyOrderStatusMethod(type, quantity, sVo.getInTotalQuantity(), sVo.getInReceivedQuantity(), sVo.getInNotReceivedQuantity(), sVo.getOrderStatus());

            //修改申请订单
            updateApplyOrderMethod(2, applyOrderStatus, storageOrderVo);

            //修改商品规格完成情况
            updateOrderGoodsSkuMethod(storeId, quantity, orderGoodsSkuVos);

            //判断销售类型
            if (sVo.getType() == 3) {       //销售退货收货
                //修改库存相关
                for (OrderGoodsSkuVo vo : orderGoodsSkuVos) {
                    //增加实物库存、可用库存、账面库存
                    inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, sVo.getInWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), vo.getChangeQuantity(), vo.getChangeQuantity()));

                    //减少待收货数量
                    inventoryUtil.updateNotQuantityMethod(0, new WarehouseGoodsSkuVo(storeId, sVo.getInWarehouseId(), vo.getGoodsSkuId(), 0, vo.getChangeQuantity()));
                }

                //新增结果订单
                String resultOrderId = addResultOrderMethod(storageOrderVo, pVo, sVo);

                //新增结果订单/商品规格关系
                addOrderGoodsSkuMethod(storeId, resultOrderId, orderGoodsSkuVos);

            } else if (sVo.getType() == 4) {        //销售换货收货
                //修改库存相关
                for (OrderGoodsSkuVo vo : orderGoodsSkuVos) {
                    //增加实物库存、可用库存
                    inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, sVo.getInWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), vo.getChangeQuantity(), 0));

                    //减少待收货数量
                    inventoryUtil.updateNotQuantityMethod(0, new WarehouseGoodsSkuVo(storeId, sVo.getInWarehouseId(), vo.getGoodsSkuId(), 0, vo.getChangeQuantity()));
                }
            }
        }

        //设置单据编号
        storageOrderVo.setId("THHSQD_SHD_" + UUID.randomUUID().toString().replace("-", ""));
    }

    /**
     * 新增销售订单发货单
     * @param storageOrderVo
     * @param pVo
     * @param sVo
     */
    @Transactional
    public void xsfhd(StorageOrderVo storageOrderVo, ProcurementApplyOrderVo pVo, SellApplyOrderVo sVo) {
        //销售订单发货前必须完成结算
        if (sVo.getClearStatus() != 1) {
            throw new CommonException("销售订单发货前必须完成结算");
        }

        //获取参数
        int storeId = storageOrderVo.getStoreId();
        List<OrderGoodsSkuVo> orderGoodsSkuVos = storageOrderVo.getSellApplyOrderVo().getDetails();
        int quantity = storageOrderVo.getQuantity();
        byte type = storageOrderVo.getType();

        //设置单据编号
        storageOrderVo.setId("XXDD_FHD_" + UUID.randomUUID().toString().replace("-", ""));

        //获取申请订单应该改为哪种单据状态
        byte applyOrderStatus = getApplyOrderStatusMethod(type, quantity, sVo.getOutTotalQuantity(), sVo.getOutSentQuantity(), sVo.getOutNotSentQuantity(), sVo.getOrderStatus());

        //修改申请订单
        updateApplyOrderMethod(2, applyOrderStatus, storageOrderVo);

        //修改商品规格完成情况
        updateOrderGoodsSkuMethod(storeId, quantity, orderGoodsSkuVos);

        //修改库存相关
        for (OrderGoodsSkuVo vo : orderGoodsSkuVos) {
            //减少实物库存、账面库存
            inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, sVo.getOutWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), 0, vo.getChangeQuantity()));

            //减少待发货数量
            inventoryUtil.updateNotQuantityMethod(0, new WarehouseGoodsSkuVo(storeId, sVo.getOutWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), 0));
        }

        //新增结果订单
        String resultOrderId = addResultOrderMethod(storageOrderVo, pVo, sVo);

        //新增结果订单/商品规格关系
        addOrderGoodsSkuMethod(storeId, resultOrderId, orderGoodsSkuVos);

        //修改客户积分信息
        if (applyOrderStatus == 6) {        //发货完成
            integralUtil.updateIntegralMethod(storeId, sVo.getClient().getId(), resultOrderId, myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(storeId)), orderGoodsSkuVos);
        }
    }

    /**
     * 新增退换货申请发货单
     * @param storageOrderVo
     * @param pVo
     * @param sVo
     */
    @Transactional
    public void thhsqfhd(StorageOrderVo storageOrderVo, ProcurementApplyOrderVo pVo, SellApplyOrderVo sVo) {
        //获取参数
        int storeId = storageOrderVo.getStoreId();
        int quantity = storageOrderVo.getQuantity();
        byte type = storageOrderVo.getType();

        //判断具体是哪种发货
        if (pVo != null && sVo == null) {       //采购相关
            //获取商品规格
            List<OrderGoodsSkuVo> orderGoodsSkuVos = storageOrderVo.getProcurementApplyOrderVo().getDetails();

            //获取申请订单应该改为哪种单据状态
            byte applyOrderStatus = getApplyOrderStatusMethod(type, quantity, pVo.getOutTotalQuantity(), pVo.getOutSentQuantity(), pVo.getOutNotSentQuantity(), pVo.getOrderStatus());

            //修改申请订单
            updateApplyOrderMethod(1, applyOrderStatus, storageOrderVo);

            //修改商品规格完成情况
            updateOrderGoodsSkuMethod(storeId, quantity, orderGoodsSkuVos);

            //判断采购类型
            if (pVo.getType() == 2) {       //采购退货发货
                //修改库存相关
                for (OrderGoodsSkuVo vo : orderGoodsSkuVos) {
                    //减少实物库存、账面库存
                    inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, pVo.getOutWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), 0, vo.getChangeQuantity()));

                    //减少待发货数量
                    inventoryUtil.updateNotQuantityMethod(0, new WarehouseGoodsSkuVo(storeId, pVo.getOutWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), 0));
                }

                //新增结果订单
                String resultOrderId = addResultOrderMethod(storageOrderVo, pVo, sVo);

                //新增结果订单/商品规格关系
                addOrderGoodsSkuMethod(storeId, resultOrderId, orderGoodsSkuVos);

            } else if (pVo.getType() == 3) {        //采购换货发货
                //修改库存相关
                for (OrderGoodsSkuVo vo : orderGoodsSkuVos) {
                    //减少实物库存
                    inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, pVo.getOutWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), 0, 0));

                    //减少待发货数量
                    inventoryUtil.updateNotQuantityMethod(0, new WarehouseGoodsSkuVo(storeId, pVo.getOutWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), 0));
                }
            }
        } else if (pVo == null && sVo != null) {        //销售换货发货
            //销售换货发货前必须完成收货
            if (sVo.getOrderStatus() != 13 && sVo.getOrderStatus() != 14) {
                throw new CommonException("销售换货申请单发货前必须完成收货");
            }

            //获取商品规格
            List<OrderGoodsSkuVo> orderGoodsSkuVos = storageOrderVo.getSellApplyOrderVo().getDetails();

            //获取申请订单应该改为哪种单据状态
            byte applyOrderStatus = getApplyOrderStatusMethod(type, quantity, sVo.getOutTotalQuantity(), sVo.getOutSentQuantity(), sVo.getOutNotSentQuantity(), sVo.getOrderStatus());

            //修改申请订单
            updateApplyOrderMethod(2, applyOrderStatus, storageOrderVo);

            //修改商品规格完成情况
            updateOrderGoodsSkuMethod(storeId, quantity, orderGoodsSkuVos);

            //修改库存相关
            for (OrderGoodsSkuVo vo : orderGoodsSkuVos) {
                //减少实物库存
                inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, sVo.getOutWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), 0, 0));

                //减少待发货数量
                inventoryUtil.updateNotQuantityMethod(0, new WarehouseGoodsSkuVo(storeId, sVo.getOutWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), 0));
            }
            if (applyOrderStatus == 15) {
                //修改商品规格账面库存
                updateBookInventoryMethod(storageOrderVo, pVo, sVo);

                //新增结果订单
                String resultOrderId = addResultOrderMethod(storageOrderVo, pVo, sVo);

                //新增结果订单/商品规格关系
                addOrderGoodsSkuMethod(storeId, resultOrderId, sVo.getDetails());
            }
        }
        //设置单据编号
        storageOrderVo.setId("THHSQD_FHD_" + UUID.randomUUID().toString().replace("-", ""));
    }

    /**
     * 新增收/发货单
     * @param storageOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addStorageOrder(StorageOrderVo storageOrderVo) {
        //获取参数
        int storeId = storageOrderVo.getStoreId();
        String applyOrderId = storageOrderVo.getApplyOrderId();
        //判断参数、查询申请订单
        ProcurementApplyOrderVo pVo = null;
        SellApplyOrderVo sVo = null;
        if (storageOrderVo.getProcurementApplyOrderVo() != null && storageOrderVo.getSellApplyOrderVo() == null) {      //采购相关
            //判断参数
            if (storageOrderVo.getProcurementApplyOrderVo().getDetails() == null || storageOrderVo.getProcurementApplyOrderVo().getDetails().size() == 0) {
                throw new CommonException("参数不匹配：【申请订单商品规格为空】");
            }
            //查询申请订单
            pVo = myProcurementMapper.findApplyOrderDetailById(new ProcurementApplyOrderVo(storeId, applyOrderId));
            if (pVo == null || pVo.getDetails() == null || pVo.getDetails().size() == 0) {
                throw new CommonException("申请订单编号错误");
            }
        } else if (storageOrderVo.getProcurementApplyOrderVo() == null && storageOrderVo.getSellApplyOrderVo() != null) {       //销售相关
            //判断参数
            if (storageOrderVo.getSellApplyOrderVo().getDetails() == null || storageOrderVo.getSellApplyOrderVo().getDetails().size() == 0) {
                throw new CommonException("参数不匹配：【申请订单商品规格为空】");
            }
            //查询申请订单
            sVo = mySellMapper.findApplyOrderDetailById(new SellApplyOrderVo(storeId, applyOrderId));
            if (sVo == null || sVo.getDetails() == null || sVo.getDetails().size() == 0) {
                throw new CommonException("申请订单编号错误");
            }
        } else {
            throw new CommonException("参数不匹配：【申请订单为空】");
        }
        //判断新增收/发货单类型
        Byte type = storageOrderVo.getType();
        switch (type) {
            case 1:     //采购订单收货单
                cgshd(storageOrderVo, pVo, sVo);
                break;
            case 2:     //退换货申请收货单
                thhsqshd(storageOrderVo, pVo, sVo);
                break;
            case 3:     //销售订单发货单
                xsfhd(storageOrderVo, pVo, sVo);
                break;
            case 4:     //退换货申请发货单
                thhsqfhd(storageOrderVo, pVo, sVo);
                break;
            default:
                return new CommonResponse(CommonResponse.CODE0, null, "收/发货单类型错误");
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
        //TODO
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

    //其他入/出库单

    /**
     * 新增其他入/出库单、报溢/损单
     * @param storageResultOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addStorageResultOrder(StorageResultOrderVo storageResultOrderVo) {
        //判断参数
        if (storageResultOrderVo.getDetails() == null || storageResultOrderVo.getDetails().size() == 0 ||
                storageResultOrderVo.getType() == null || storageResultOrderVo.getWarehouseId() == null ||
                storageResultOrderVo.getTotalQuantity() == null || storageResultOrderVo.getTotalMoney() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //获取参数
        Integer storeId = storageResultOrderVo.getStoreId();
        List<OrderGoodsSkuVo> orderGoodsSkuVos = storageResultOrderVo.getDetails();
        byte type = storageResultOrderVo.getType();
        int totalQuantity = storageResultOrderVo.getTotalQuantity();
        double totalMoney = storageResultOrderVo.getTotalMoney().doubleValue();
        //判断新增单据类型
        switch (type) {
            //其他入库单
            case 1:
                //判断参数
                if (storageResultOrderVo.getTargetId() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                storageResultOrderVo.setId("QTRKD_" + UUID.randomUUID().toString().replace("-", ""));
                break;
            //其他出库单
            case 2:
                //判断参数
                if (storageResultOrderVo.getTargetId() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                storageResultOrderVo.setId("QTCKD_" + UUID.randomUUID().toString().replace("-", ""));
                break;
            //报溢单
            case 3:
                storageResultOrderVo.setId("BYD_" + UUID.randomUUID().toString().replace("-", ""));
                break;
            //报损单
            case 4:
                storageResultOrderVo.setId("BSD_" + UUID.randomUUID().toString().replace("-", ""));
                break;
            default:
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //设置初始属性
        storageResultOrderVo.setCreateTime(new Date());
        storageResultOrderVo.setOrderStatus((byte) 1);
        //新增单据
        if (myStorageMapper.addStorageResultOrder(storageResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //新增单据/商品规格关系
        byte flag = (type == 1 || type == 3) ? (byte) 1 : (byte) 0;
        Map<String, Object> check = addOrderGoodsSku(flag, orderGoodsSkuVos, storeId, storageResultOrderVo.getId(), storageResultOrderVo.getWarehouseId());
        //验证数量和金额是否对应
        if (totalQuantity != (int) check.get("totalQuantity") || totalMoney != (double) check.get("totalMoney")) {
            LOG.info("新增其他入/出库单、报溢/损单，商品数量或金额与所有商品规格数量、金额之和不对应");
            throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 新增订单对应的商品规格关系的方法
     * @param flag 0：出库，1：入库
     * @param vos
     * @param storeId
     * @param orderId
     * @param warehouseId
     * @return 返回验证数量和金额
     */
    @Transactional
    public Map<String, Object> addOrderGoodsSku(byte flag, List<OrderGoodsSkuVo> vos, int storeId, String orderId, int warehouseId) {
        //验证
        Map<String, Object> check = new HashMap<>();
        check.put("totalQuantity", 0);
        check.put("totalMoney", 0.0);
        check.put("totalDiscountMoney", 0.0);
        check.put("orderMoney", 0.0);
        //新增其他入/出库单 商品规格关系
        vos.stream().forEach(vo -> {
            //判断参数
            if (vo.getGoodsSkuId() == null || vo.getType() == null || vo.getType() != flag ||
                    vo.getQuantity() == null || vo.getMoney() == null){
                throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
            }
            //设置初始属性
            vo.setStoreId(storeId);
            vo.setOrderId(orderId);
            vo.setFinishQuantity(vo.getQuantity());
            vo.setNotFinishQuantity(0);
            vo.setOperatedQuantity(vo.getQuantity());
            //插入数据
            if (myOrderGoodsSkuMapper.addOrderGoodsSku(vo) != 1) {
                throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
            }
            //修改库存
            inventoryUtil.updateInventoryMethod(flag, new WarehouseGoodsSkuVo(storeId, warehouseId, vo.getGoodsSkuId(), vo.getQuantity(), vo.getQuantity(), vo.getQuantity()));
            //统计数量
            check.put("totalQuantity", (int) check.get("totalQuantity") + vo.getQuantity());
            //统计金额
            check.put("totalMoney", (double) check.get("totalMoney") + vo.getMoney().doubleValue());
            double discountMoney = vo.getDiscountMoney() == null ? 0.0 : vo.getDiscountMoney().doubleValue();
            check.put("totalDiscountMoney", (double) check.get("totalDiscountMoney") + discountMoney);
        });
        check.put("orderMoney", (double) check.get("totalMoney") - (double) check.get("totalDiscountMoney"));
        return check;
    }

    /**
     * 红冲其他入/出库单、报溢/损单
     * @param storageResultOrderVo
     * @return
     */
    @Transactional
    public CommonResponse redDashedStorageResultOrder(StorageResultOrderVo storageResultOrderVo) {
        //判断参数
        if (storageResultOrderVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        Integer storeId = storageResultOrderVo.getStoreId();
        //修改单据状态
        if (myStorageMapper.redDashedStorageResultOrder(storageResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        //获取该订单
        storageResultOrderVo = myStorageMapper.findStorageResultOrderDetailById(storageResultOrderVo);
        //新增红冲单
        storageResultOrderVo.setStoreId(storeId);
        storageResultOrderVo.setId("HC_" + storageResultOrderVo.getId());
        storageResultOrderVo.setCreateTime(new Date());
        storageResultOrderVo.setOrderStatus((byte) -2);
        storageResultOrderVo.setTotalQuantity(-storageResultOrderVo.getTotalQuantity());
        storageResultOrderVo.setTotalMoney(new BigDecimal(-storageResultOrderVo.getTotalMoney().doubleValue()));
        if (myStorageMapper.addStorageResultOrder(storageResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        //修改库存
        List<OrderGoodsSkuVo> details = storageResultOrderVo.getDetails();
        WarehouseGoodsSkuVo warehouseGoodsSkuVo = new WarehouseGoodsSkuVo(storeId, storageResultOrderVo.getWarehouseId());
        details.stream().forEach(orderGoodsSkuVo -> {
            byte flag = orderGoodsSkuVo.getType() == 0 ? (byte) 1 : (byte) 0;
            warehouseGoodsSkuVo.setGoodsSkuId(orderGoodsSkuVo.getGoodsSkuId());
            warehouseGoodsSkuVo.setRealInventory(orderGoodsSkuVo.getQuantity());
            warehouseGoodsSkuVo.setCanUseInventory(orderGoodsSkuVo.getQuantity());
            warehouseGoodsSkuVo.setBookInventory(orderGoodsSkuVo.getQuantity());
            inventoryUtil.updateInventoryMethod(flag, warehouseGoodsSkuVo);
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 根据条件查询其他入/出库单、报溢/损单
     * @param storageResultOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllStorageResultOrder(StorageResultOrderVo storageResultOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountStorageResultOrder(storageResultOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<StorageResultOrderVo> storageResultOrderVos = myStorageMapper.findAllPagedStorageResultOrder(storageResultOrderVo, pageVo);
        //设置往来单位
        storageResultOrderVos.stream().forEach(vo -> {
            setTargetNameMethod(vo);
        });
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "id"));
        titles.add(new Title("单据类型", "type"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("往来单位", "targetName"));
        titles.add(new Title("仓库", "warehouseName"));
        titles.add(new Title("总商品数量", "totalQuantity"));
        titles.add(new Title("总订单金额", "totalMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, storageResultOrderVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据单据编号查询其他入/出库单、报溢/损单详情
     * @param storageResultOrderVo
     * @return
     */
    public CommonResponse findStorageResultOrderDetailById(StorageResultOrderVo storageResultOrderVo) {
        storageResultOrderVo = myStorageMapper.findStorageResultOrderDetailById(storageResultOrderVo);
        //设置往来单位
        setTargetNameMethod(storageResultOrderVo);
        return new CommonResponse(CommonResponse.CODE1, storageResultOrderVo, CommonResponse.MESSAGE1);
    }

    /**
     * 设置往来单位的方法
     * @param storageResultOrderVo
     */
    public void setTargetNameMethod(StorageResultOrderVo storageResultOrderVo) {
        byte targetType = storageResultOrderVo.getTargetType();
        byte type = storageResultOrderVo.getType();
        if (type == 1 || type == 2) {
            if (targetType == 1) {      //供应商
                storageResultOrderVo.setTargetName(storageResultOrderVo.getSupplierName());
            } else if (targetType == 2) {       //客户
                storageResultOrderVo.setTargetName(storageResultOrderVo.getClientName());
            }
        }
    }

    //查库存

    /**
     * 查当前库存
     * @param goodsWarehouseSkuVo
     * @param pageVo
     * @return
     */
    public CommonResponse findCurrentInventory(GoodsWarehouseSkuVo goodsWarehouseSkuVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountCurrentInventory(goodsWarehouseSkuVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<GoodsWarehouseSkuVo> goodsWarehouseSkuVos = myStorageMapper.findPagedCurrentInventory(goodsWarehouseSkuVo, pageVo);

        //查询所有商品规格
        List<GoodsSku> goodsSkus = myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(goodsWarehouseSkuVo.getStoreId()));
        goodsWarehouseSkuVos.stream().forEach(vo -> {
            //根据商品货号过滤商品规格
            List<GoodsSku> goodsSkuList = goodsSkus.stream().filter(goodsSku -> goodsSku.getGoodsId().equals(vo.getId())).collect(Collectors.toList());
            double quantity = 0;
            double totalMoney = 0;
            for (GoodsSku goodsSku : goodsSkuList) {
                //根据商品规格编号查询最新库存记账记录
                StorageCheckOrderVo storageCheckOrderVo = myStorageMapper.findLastCheckMoney(new StorageCheckOrderVo(goodsWarehouseSkuVo.getStoreId(), goodsSku.getId()));
                quantity += storageCheckOrderVo.getCheckQuantity();
                totalMoney += storageCheckOrderVo.getCheckTotalMoney().doubleValue();
            }
            vo.setCostMoney(new BigDecimal(totalMoney / quantity));
            vo.setTotalCostMoney(new BigDecimal(totalMoney));
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("分类", "typeName"));
        titles.add(new Title("图片", "image"));
        titles.add(new Title("实物库存", "realInventory"));
        titles.add(new Title("待发货数量", "notSentQuantity"));
        titles.add(new Title("待收货数量", "notReceivedQuantity"));
        titles.add(new Title("可用库存", "canUseInventory"));
        titles.add(new Title("账面库存", "bookInventory"));
        titles.add(new Title("成本单价", "costMoney"));
        titles.add(new Title("成本金额", "totalCostMoney"));
        CommonResult commonResult = new CommonResult(titles, goodsWarehouseSkuVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据商品货号查分布
     * @param goodsWarehouseSkuVo
     * @param pageVo
     * @return
     */
    public CommonResponse findDistributionByGoodsId(GoodsWarehouseSkuVo goodsWarehouseSkuVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountDistributionByGoodsId(goodsWarehouseSkuVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<GoodsWarehouseSkuVo> goodsWarehouseSkuVos = myStorageMapper.findPagedDistributionByGoodsId(goodsWarehouseSkuVo, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("仓库", "warehouseName"));
        titles.add(new Title("实物库存", "realInventory"));
        titles.add(new Title("待发货数量", "notSentQuantity"));
        titles.add(new Title("待收货数量", "notReceivedQuantity"));
        titles.add(new Title("可用库存", "canUseInventory"));
        titles.add(new Title("账面库存", "bookInventory"));
        CommonResult commonResult = new CommonResult(titles, goodsWarehouseSkuVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据商品货号查对账
     * @param storageCheckOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findStorageCheckOrder(StorageCheckOrderVo storageCheckOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountOrderByGoodsId(storageCheckOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<StorageCheckOrderVo> storageCheckOrderVos = myStorageMapper.findPagedOrderByGoodsId(storageCheckOrderVo, pageVo);
        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(storageCheckOrderVo.getStoreId()));
        storageCheckOrderVos.stream().forEach(vo -> {
            if (vo.getInWarehouseId() != null) {
                vo.setInWarehouseName(warehouses.stream().filter(warehouse -> warehouse.getId().toString().equals(vo.getInWarehouseId().toString())).findFirst().get().getName());
            }
            if (vo.getOutWarehouseId() != null) {
                vo.setOutWarehouseName(warehouses.stream().filter(warehouse -> warehouse.getId().toString().equals(vo.getOutWarehouseId().toString())).findFirst().get().getName());
            }
        });
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "orderId"));
        titles.add(new Title("单据类型", "typeName"));
        titles.add(new Title("创建时间", "createTime"));
        titles.add(new Title("来源订单", "applyOrderId"));
        titles.add(new Title("往来单位", "targetName"));
        titles.add(new Title("商品规格", "sku"));
        titles.add(new Title("入库仓库", "inWarehouseName"));
        titles.add(new Title("入库数量", "inQuantity"));
        titles.add(new Title("入库成本单价", "inMoney"));
        titles.add(new Title("入库成本金额", "inTotalMoney"));
        titles.add(new Title("出库仓库", "outWarehouseName"));
        titles.add(new Title("出库数量", "outQuantity"));
        titles.add(new Title("出库成本单价", "outMoney"));
        titles.add(new Title("出库成本金额", "outTotalMoney"));
        titles.add(new Title("结存数量", "checkQuantity"));
        titles.add(new Title("结存成本单价", "checkMoney"));
        titles.add(new Title("结存成本金额", "checkTotalMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, storageCheckOrderVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据商品货号查属性
     * @param goodsWarehouseSkuVo
     * @param pageVo
     * @return
     */
    public CommonResponse findSkuByGoodsId(GoodsWarehouseSkuVo goodsWarehouseSkuVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountSkuByGoodsId(goodsWarehouseSkuVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<GoodsWarehouseSkuVo> goodsWarehouseSkuVos = myStorageMapper.findPagedSkuByGoodsId(goodsWarehouseSkuVo, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        /*titles.add(new Title("skus", "skus"));*/
        titles.add(new Title("商品规格", "sku"));
        titles.add(new Title("实物库存", "realInventory"));
        titles.add(new Title("待发货数量", "notSentQuantity"));
        titles.add(new Title("待收货数量", "notReceivedQuantity"));
        titles.add(new Title("可用库存", "canUseInventory"));
        titles.add(new Title("账面库存", "bookInventory"));
        CommonResult commonResult = new CommonResult(titles, goodsWarehouseSkuVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 按属性查库存
     * @param goodsWarehouseSkuVo
     * @param pageVo
     * @return
     */
    public CommonResponse findBySku(GoodsWarehouseSkuVo goodsWarehouseSkuVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountBySku(goodsWarehouseSkuVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<GoodsWarehouseSkuVo> goodsWarehouseSkuVos = myStorageMapper.findPagedBySku(goodsWarehouseSkuVo, pageVo);

        //补上成本
        goodsWarehouseSkuVos.stream().forEach(vo -> {
            StorageCheckOrderVo storageCheckOrderVo = myStorageMapper.findLastCheckMoney(new StorageCheckOrderVo(goodsWarehouseSkuVo.getStoreId(), vo.getGoodsSkuId()));
            vo.setCostMoney(storageCheckOrderVo.getCheckMoney());
            vo.setTotalCostMoney(storageCheckOrderVo.getCheckTotalMoney());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("分类", "typeName"));
        titles.add(new Title("图片", "image"));
        /*titles.add(new Title("skus", "skus"));*/
        titles.add(new Title("商品规格", "sku"));
        titles.add(new Title("实物库存", "realInventory"));
        titles.add(new Title("待发货数量", "notSentQuantity"));
        titles.add(new Title("待收货数量", "notReceivedQuantity"));
        titles.add(new Title("可用库存", "canUseInventory"));
        titles.add(new Title("账面库存", "bookInventory"));
        titles.add(new Title("成本单价", "costMoney"));
        titles.add(new Title("成本金额", "totalCostMoney"));
        CommonResult commonResult = new CommonResult(titles, goodsWarehouseSkuVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 按仓库查库存
     * @param goodsWarehouseSkuVo
     * @param pageVo
     * @return
     */
    public CommonResponse findByWarehouse(GoodsWarehouseSkuVo goodsWarehouseSkuVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountByWarehouse(goodsWarehouseSkuVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<GoodsWarehouseSkuVo> goodsWarehouseSkuVos = myStorageMapper.findPagedByWarehouse(goodsWarehouseSkuVo, pageVo);

        //补上成本
        goodsWarehouseSkuVos.stream().forEach(vo -> {
            StorageCheckOrderVo storageCheckOrderVo = myStorageMapper.findLastCheckMoney(new StorageCheckOrderVo(goodsWarehouseSkuVo.getStoreId(), vo.getGoodsSkuId()));
            vo.setCostMoney(storageCheckOrderVo.getCheckMoney());
            vo.setTotalCostMoney(storageCheckOrderVo.getCheckTotalMoney());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("分类", "typeName"));
        titles.add(new Title("图片", "image"));
        titles.add(new Title("仓库", "warehouseName"));
        /*titles.add(new Title("skus", "skus"));*/
        titles.add(new Title("商品规格", "sku"));
        switch (goodsWarehouseSkuVo.getFlag()) {
            case 0:     //按仓库查库存
                titles.add(new Title("实物库存", "realInventory"));
                titles.add(new Title("待发货数量", "notSentQuantity"));
                titles.add(new Title("待收货数量", "notReceivedQuantity"));
                titles.add(new Title("可用库存", "canUseInventory"));
                titles.add(new Title("账面库存", "bookInventory"));
                break;
            case 1:     //库存预警设置
                titles.add(new Title("库存上限", "inventoryUpperLimit"));
                titles.add(new Title("库存下限", "inventoryLowLimit"));
                break;
            case 2:     //库存预警查询
                titles.add(new Title("库存上限", "inventoryUpperLimit"));
                titles.add(new Title("库存下限", "inventoryLowLimit"));
                titles.add(new Title("账面库存", "bookInventory"));
                break;
            case 3:     //缺货查询
                titles.add(new Title("缺货数量", "needQuantity"));
                titles.add(new Title("库存下限", "inventoryLowLimit"));
                titles.add(new Title("待发货数量", "notSentQuantity"));
                titles.add(new Title("实物库存", "realInventory"));
                titles.add(new Title("待收货数量", "notReceivedQuantity"));
                break;
            case 4:     //设置库存期初
                if (goodsWarehouseSkuVo.getWarehouseId() == null) {
                    throw new CommonException(CommonResponse.MESSAGE3);
                }
                titles.add(new Title("期初数量", "openingQuantity"));
                titles.add(new Title("期初成本单价", "openingMoney"));
                titles.add(new Title("期初金额", "openingTotalMoney"));
                break;
            default:
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        CommonResult commonResult = new CommonResult(titles, goodsWarehouseSkuVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    //库存预警

    /**
     * 库存预警设置
     * @param vo
     * @return
     */
    @Transactional
    public CommonResponse updateLimitInventory(WarehouseGoodsSkuVo vo) {
        inventoryUtil.updateLimitInventoryMethod(vo);
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    //库存期初

    /**
     * 库存期初设置
     * @param vo
     * @return
     */
    @Transactional
    public CommonResponse addWarehouseGoodsSku(WarehouseGoodsSkuVo vo) {
        //库存期初设置
        inventoryUtil.addOrUpdateWarehouseGoodsSku(vo);
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }
}
