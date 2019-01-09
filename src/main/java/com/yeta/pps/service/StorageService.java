package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.*;
import com.yeta.pps.po.GoodsSku;
import com.yeta.pps.po.SellResultOrder;
import com.yeta.pps.po.Warehouse;
import com.yeta.pps.util.*;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 仓库存储相关逻辑处理
 * @author YETA
 * @date 2018/12/11/16:22
 */
@Service
public class StorageService {

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
     * 待收/发货
     * @param procurementApplyOrderVo
     * @param pageVo
     * @param flag
     * @return
     */
    public CommonResponse findNotFinishedApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo, PageVo pageVo, Integer flag) {
        List<ProcurementApplyOrderVo> procurementApplyOrderVos;

        switch (flag) {
            case 1:     //采购订单待收货
                //查询所有页数
                pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountInCGApplyOrder(procurementApplyOrderVo) * 1.0 / pageVo.getPageSize()));
                pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
                procurementApplyOrderVos = myStorageMapper.findPagedInCGApplyOrder(procurementApplyOrderVo, pageVo);
                break;

            case 2:     //退换货申请待收货
                //查询所有页数
                pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountInTHHApplyOrder(procurementApplyOrderVo) * 1.0 / pageVo.getPageSize()));
                pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
                procurementApplyOrderVos = myStorageMapper.findPagedInTHHApplyOrder(procurementApplyOrderVo, pageVo);
                break;

            case 3:     //销售订单待发货
                //查询所有页数
                pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountOutCGApplyOrder(procurementApplyOrderVo) * 1.0 / pageVo.getPageSize()));
                pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
                procurementApplyOrderVos = myStorageMapper.findPagedOutCGApplyOrder(procurementApplyOrderVo, pageVo);
                break;

            case 4:     //退换货申请待发货
                //查询所有页数
                pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountOutTHHApplyOrder(procurementApplyOrderVo) * 1.0 / pageVo.getPageSize()));
                pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
                procurementApplyOrderVos = myStorageMapper.findPagedOutTHHApplyOrder(procurementApplyOrderVo, pageVo);
                break;

            default:
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(procurementApplyOrderVo.getStoreId()));
        procurementApplyOrderVos.stream().forEach(vo -> {
            warehouses.stream().forEach(warehouse -> {
                if (vo.getInWarehouseId() == warehouse.getId()) {
                    vo.setInWarehouseName(warehouse.getName());
                }
                if (vo.getOutWarehouseId() == warehouse.getId()) {
                    vo.setOutWarehouseName(warehouse.getName());
                }
            });
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "id"));
        titles.add(new Title("单据类型", "typeName"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("单据状态", "orderStatus"));
        titles.add(new Title("来源订单", "resultOrderId"));
        titles.add(new Title("往来单位", "supplierName"));
        titles.add(new Title("入库仓库", "inWarehouseName"));
        titles.add(new Title("总收货数量", "inTotalQuantity"));
        titles.add(new Title("已收货数量", "inReceivedQuantity"));
        titles.add(new Title("未收货数量", "inNotReceivedQuantity"));
        titles.add(new Title("出库仓库", "outWarehouseName"));
        titles.add(new Title("总发货数量", "outTotalQuantity"));
        titles.add(new Title("已发货数量", "outSentQuantity"));
        titles.add(new Title("未发货数量数量", "outNotSentQuantity"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, procurementApplyOrderVos, pageVo);

        return CommonResponse.success(commonResult);
    }

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
                        throw new CommonException(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
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
                        throw new CommonException(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
                    }
                    break;

                case 3:
                    if (oldApplyOrderStatus == 4 || oldApplyOrderStatus == 5) {     //销售订单
                        applyOrderStatus = 6;
                    } else {
                        throw new CommonException(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
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
                        throw new CommonException(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
                    }
                    break;

                default:
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }
        } else if (change < notFinish && change + finish < total) {     //部分完成
            switch (type) {
                case 1:
                    if (oldApplyOrderStatus == 1 || oldApplyOrderStatus == 2) {        //采购订单
                        applyOrderStatus = 2;
                    } else {
                        throw new CommonException(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
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
                        throw new CommonException(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
                    }
                    break;

                case 3:
                    if (oldApplyOrderStatus == 4 || oldApplyOrderStatus == 5) {      //销售订单
                        applyOrderStatus = 5;
                    } else {
                        throw new CommonException(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
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
                        throw new CommonException(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
                    }
                    break;

                default:
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }
        } else {        //错误
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
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
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                break;

            case 2:
                if (mySellMapper.updateApplyOrderOrderStatusAndQuantity(storageOrderVo) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
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
        OrderGoodsSkuVo check = new OrderGoodsSkuVo(quantity, 0);
        vos.stream().forEach(vo -> {
            //判断参数
            if (vo.getId() == null || vo.getGoodsSkuId() == null || vo.getChangeQuantity() == null) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }
            vo.setStoreId(storeId);
            //修改商品规格完成情况
            if (myOrderGoodsSkuMapper.updateOrderGoodsSku(vo) != 1) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
            //统计数量
            check.setFinishQuantity(check.getFinishQuantity() + vo.getChangeQuantity());
        });
        if (check.getQuantity() != check.getFinishQuantity()) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }
    }

    /**
     * 修改账面库存的方法
     * @param storageOrderVo
     * @param pVo
     * @param sVo
     * @param resultOrderId
     * @return
     */
    @Transactional
    public double updateBookInventoryMethod(StorageOrderVo storageOrderVo, ProcurementApplyOrderVo pVo, SellApplyOrderVo sVo, String resultOrderId) {
        List<OrderGoodsSkuVo> vos = null;
        Integer inWarehouseId = null;
        Integer outWarehouseId = null;
        String targetId = null;
        if (pVo != null && sVo == null) {       //采购相关
            vos = pVo.getDetails();
            inWarehouseId = pVo.getInWarehouseId();
            outWarehouseId = pVo.getOutWarehouseId();
            targetId = pVo.getSupplierId();
        } else if (pVo == null && sVo != null) {        //销售相关
            vos = sVo.getDetails();
            inWarehouseId = sVo.getInWarehouseId();
            outWarehouseId = sVo.getOutWarehouseId();
            targetId = sVo.getClient().getId();
        }

        //统计成本
        double costMoney = 0;

        //采购换货需要先记账出库，再记账入库
        List<OrderGoodsSkuVo> outVos = vos.stream().filter(vo -> vo.getType() == 0).collect(Collectors.toList());
        for (OrderGoodsSkuVo vo : outVos) {
            //减少账面库存
            inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storageOrderVo.getStoreId(), outWarehouseId, vo.getGoodsSkuId(), 0, 0, vo.getQuantity()));

            //库存记账
            StorageCheckOrderVo storageCheckOrderVo = new StorageCheckOrderVo();
            storageCheckOrderVo.setStoreId(storageOrderVo.getStoreId());
            storageCheckOrderVo.setOrderId(resultOrderId);
            storageCheckOrderVo.setTargetId(targetId);
            storageCheckOrderVo.setCreateTime(new Date());
            storageCheckOrderVo.setOrderStatus((byte) 1);
            storageCheckOrderVo.setGoodsSkuId(vo.getGoodsSkuId());
            storageCheckOrderVo.setWarehouseId(outWarehouseId);
            storageCheckOrderVo.setOutQuantity(vo.getQuantity());
            storageCheckOrderVo.setUserId(storageOrderVo.getUserId());
            costMoney += inventoryUtil.addStorageCheckOrderMethod(0, storageCheckOrderVo, null) * vo.getQuantity();
        }

        List<OrderGoodsSkuVo> inVos = vos.stream().filter(vo -> vo.getType() == 1).collect(Collectors.toList());
        for (OrderGoodsSkuVo vo : inVos) {
            //增加账面库存
            inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storageOrderVo.getStoreId(), inWarehouseId, vo.getGoodsSkuId(), 0, 0, vo.getQuantity()));

            //库存记账
            StorageCheckOrderVo storageCheckOrderVo = new StorageCheckOrderVo();
            storageCheckOrderVo.setStoreId(storageOrderVo.getStoreId());
            storageCheckOrderVo.setOrderId(resultOrderId);
            storageCheckOrderVo.setTargetId(targetId);
            storageCheckOrderVo.setCreateTime(new Date());
            storageCheckOrderVo.setOrderStatus((byte) 1);
            storageCheckOrderVo.setGoodsSkuId(vo.getGoodsSkuId());
            storageCheckOrderVo.setWarehouseId(inWarehouseId);
            storageCheckOrderVo.setInQuantity(vo.getQuantity());
            storageCheckOrderVo.setUserId(storageOrderVo.getUserId());
            if (pVo != null && sVo == null) {       //采购相关
                storageCheckOrderVo.setInMoney(inventoryUtil.getNumberMethod((vo.getMoney() - vo.getDiscountMoney()) / vo.getQuantity()));
                storageCheckOrderVo.setInTotalMoney(inventoryUtil.getNumberMethod(storageCheckOrderVo.getInQuantity() * storageCheckOrderVo.getInMoney()));
                inventoryUtil.addStorageCheckOrderMethod(1, storageCheckOrderVo, null);
            } else if (pVo == null && sVo != null) {        //销售相关
                costMoney -= inventoryUtil.addStorageCheckOrderMethod(2, storageCheckOrderVo, null) * vo.getQuantity();
            }
        }

        return costMoney;
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
        SellResultOrder money = new SellResultOrder(0.0, 0.0, 0.0);
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
            orderGoodsSkuVo.setMoney(vo.getMoney() * finalQuantity / vo.getQuantity());
            orderGoodsSkuVo.setDiscountMoney(vo.getDiscountMoney() * finalQuantity / vo.getQuantity());
            if (flag == 1) {        //采购
                if (orderGoodsSkuVo.getType() == 1) {       //入库
                    money.setTotalMoney(money.getTotalMoney() + (vo.getMoney() * finalQuantity / vo.getQuantity()));
                    money.setTotalDiscountMoney(money.getTotalDiscountMoney() + (vo.getDiscountMoney() * finalQuantity / vo.getQuantity()));
                } else if (orderGoodsSkuVo.getType() == 0) {        //出库
                    money.setTotalMoney(money.getTotalMoney() - (vo.getMoney() * finalQuantity / vo.getQuantity()));
                    money.setTotalDiscountMoney(money.getTotalDiscountMoney() - (vo.getDiscountMoney() * finalQuantity / vo.getQuantity()));
                }
            } else if (flag == 2) {     //销售
                if (orderGoodsSkuVo.getType() == 1) {       //入库
                    money.setTotalMoney(money.getTotalMoney() - (vo.getMoney() * finalQuantity / vo.getQuantity()));
                    money.setTotalDiscountMoney(money.getTotalDiscountMoney() - (vo.getDiscountMoney() * finalQuantity / vo.getQuantity()));
                } else if (orderGoodsSkuVo.getType() == 0) {        //出库
                    money.setTotalMoney(money.getTotalMoney() + (vo.getMoney() * finalQuantity / vo.getQuantity()));
                    money.setTotalDiscountMoney(money.getTotalDiscountMoney() + (vo.getDiscountMoney() * finalQuantity / vo.getQuantity()));
                }
            }
        });
        money.setOrderMoney(money.getTotalMoney() - money.getTotalDiscountMoney());
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
    public String addResultOrderMethod(StorageOrderVo storageOrderVo, ProcurementApplyOrderVo pVo, SellApplyOrderVo sVo, String resultOrderId, double costMoney) {
        List<OrderGoodsSkuVo> orderGoodsSkuVos;
        SellResultOrder money;
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
                throw new CommonException(CommonResponse.ADD_ERROR);
            }
        } else if (pVo == null && sVo != null) {        //销售相关
            orderGoodsSkuVos = storageOrderVo.getSellApplyOrderVo().getDetails();
            if (sVo.getType() == 2) {       //销售订单
                quantity = storageOrderVo.getQuantity();
            } else if (sVo.getType() == 3) {        //销售退货申请单
                quantity = -storageOrderVo.getQuantity();
            } else if (sVo.getType() == 4) {        //销售换货申请单
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
                            costMoney,
                            money.getOrderMoney() - costMoney,
                            storageOrderVo.getUserId(),
                            storageOrderVo.getRemark()
                    )) != 1) {
                throw new CommonException(CommonResponse.ADD_ERROR);
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
            orderGoodsSkuVo.setOperatedQuantity(0);
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

        //新增结果订单
        String resultOrderId = addResultOrderMethod(storageOrderVo, pVo, sVo, null, 0);

        //新增结果订单/商品规格关系
        addOrderGoodsSkuMethod(storeId, resultOrderId, orderGoodsSkuVos);

        //修改库存相关
        for (OrderGoodsSkuVo vo : orderGoodsSkuVos) {
            Integer warehouseId = pVo.getInWarehouseId();
            Integer goodsSkuId = vo.getGoodsSkuId();
            Integer changeQuantity = vo.getChangeQuantity();

            //增加实物库存、可用库存、账面库存
            inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, warehouseId, goodsSkuId, changeQuantity, changeQuantity, changeQuantity));

            //减少待收货数量
            inventoryUtil.updateNotQuantityMethod(0, new WarehouseGoodsSkuVo(storeId, warehouseId, goodsSkuId, 0, changeQuantity));

            //库存记账
            StorageCheckOrderVo storageCheckOrderVo = new StorageCheckOrderVo();
            storageCheckOrderVo.setStoreId(storeId);
            storageCheckOrderVo.setOrderId(resultOrderId);
            storageCheckOrderVo.setTargetId(pVo.getSupplierId());
            storageCheckOrderVo.setCreateTime(new Date());
            storageCheckOrderVo.setOrderStatus((byte) 1);
            storageCheckOrderVo.setGoodsSkuId(vo.getGoodsSkuId());
            storageCheckOrderVo.setWarehouseId(pVo.getInWarehouseId());
            storageCheckOrderVo.setInQuantity(vo.getChangeQuantity());
            storageCheckOrderVo.setInMoney(inventoryUtil.getNumberMethod((vo.getMoney() - vo.getDiscountMoney()) / vo.getChangeQuantity()));
            storageCheckOrderVo.setInTotalMoney(inventoryUtil.getNumberMethod(storageCheckOrderVo.getInQuantity() * storageCheckOrderVo.getInMoney()));
            storageCheckOrderVo.setUserId(storageOrderVo.getUserId());
            inventoryUtil.addStorageCheckOrderMethod(1, storageCheckOrderVo, null);
        }
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
                //新增结果订单
                String resultOrderId = addResultOrderMethod(storageOrderVo, pVo, sVo, null, 0);

                //新增结果订单/商品规格关系
                addOrderGoodsSkuMethod(storeId, resultOrderId, pVo.getDetails());

                //修改商品规格账面库存
                updateBookInventoryMethod(storageOrderVo, pVo, sVo, resultOrderId);
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
                //销售结果订单单据编号
                String resultOrderId = "XXTHD_" + UUID.randomUUID().toString().replace("-", "");

                //统计成本
                double costMoney = 0;

                //修改库存相关
                for (OrderGoodsSkuVo vo : orderGoodsSkuVos) {
                    //增加实物库存、可用库存、账面库存
                    inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, sVo.getInWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), vo.getChangeQuantity(), vo.getChangeQuantity()));

                    //减少待收货数量
                    inventoryUtil.updateNotQuantityMethod(0, new WarehouseGoodsSkuVo(storeId, sVo.getInWarehouseId(), vo.getGoodsSkuId(), 0, vo.getChangeQuantity()));

                    //库存记账
                    StorageCheckOrderVo storageCheckOrderVo = new StorageCheckOrderVo();
                    storageCheckOrderVo.setStoreId(storeId);
                    storageCheckOrderVo.setOrderId(resultOrderId);
                    storageCheckOrderVo.setTargetId(sVo.getClient().getId());
                    storageCheckOrderVo.setCreateTime(new Date());
                    storageCheckOrderVo.setOrderStatus((byte) 1);
                    storageCheckOrderVo.setGoodsSkuId(vo.getGoodsSkuId());
                    storageCheckOrderVo.setWarehouseId(sVo.getInWarehouseId());
                    storageCheckOrderVo.setInQuantity(vo.getChangeQuantity());
                    storageCheckOrderVo.setUserId(storageOrderVo.getUserId());
                    costMoney += inventoryUtil.addStorageCheckOrderMethod(2, storageCheckOrderVo, null) * vo.getChangeQuantity();
                }

                //新增结果订单
                addResultOrderMethod(storageOrderVo, pVo, sVo, resultOrderId, -costMoney);

                //新增结果订单/商品规格关系
                addOrderGoodsSkuMethod(storeId, resultOrderId, orderGoodsSkuVos);

                //修改客户积分信息
                if (applyOrderStatus == 3) {        //收货完成
                    integralUtil.updateIntegralMethod(0, storeId, sVo.getClient().getId(), resultOrderId, myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(storeId)), orderGoodsSkuVos);
                }

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

        //销售结果订单单据编号
        String resultOrderId = "XXCKD_" + UUID.randomUUID().toString().replace("-", "");

        //统计成本
        double costMoney = 0;

        //修改库存相关
        for (OrderGoodsSkuVo vo : orderGoodsSkuVos) {
            //减少实物库存、账面库存
            inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, sVo.getOutWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), 0, vo.getChangeQuantity()));

            //减少待发货数量
            inventoryUtil.updateNotQuantityMethod(0, new WarehouseGoodsSkuVo(storeId, sVo.getOutWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), 0));

            //库存记账
            StorageCheckOrderVo storageCheckOrderVo = new StorageCheckOrderVo();
            storageCheckOrderVo.setStoreId(storeId);
            storageCheckOrderVo.setOrderId(resultOrderId);
            storageCheckOrderVo.setTargetId(sVo.getClient().getId());
            storageCheckOrderVo.setCreateTime(new Date());
            storageCheckOrderVo.setOrderStatus((byte) 1);
            storageCheckOrderVo.setGoodsSkuId(vo.getGoodsSkuId());
            storageCheckOrderVo.setWarehouseId(sVo.getOutWarehouseId());
            storageCheckOrderVo.setOutQuantity(vo.getChangeQuantity());
            storageCheckOrderVo.setUserId(storageOrderVo.getUserId());
            costMoney += inventoryUtil.addStorageCheckOrderMethod(0, storageCheckOrderVo, null) * vo.getChangeQuantity();
        }

        //新增结果订单
        addResultOrderMethod(storageOrderVo, pVo, sVo, resultOrderId, costMoney);

        //新增结果订单/商品规格关系
        addOrderGoodsSkuMethod(storeId, resultOrderId, orderGoodsSkuVos);

        //修改客户积分信息
        if (applyOrderStatus == 6) {        //发货完成
            integralUtil.updateIntegralMethod(1, storeId, sVo.getClient().getId(), resultOrderId, myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(storeId)), orderGoodsSkuVos);
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
                //新增结果订单
                String resultOrderId = addResultOrderMethod(storageOrderVo, pVo, sVo, null, 0);

                //新增结果订单/商品规格关系
                addOrderGoodsSkuMethod(storeId, resultOrderId, orderGoodsSkuVos);

                //修改库存相关
                for (OrderGoodsSkuVo vo : orderGoodsSkuVos) {
                    //减少实物库存、账面库存
                    inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, pVo.getOutWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), 0, vo.getChangeQuantity()));

                    //减少待发货数量
                    inventoryUtil.updateNotQuantityMethod(0, new WarehouseGoodsSkuVo(storeId, pVo.getOutWarehouseId(), vo.getGoodsSkuId(), vo.getChangeQuantity(), 0));

                    //库存记账
                    StorageCheckOrderVo storageCheckOrderVo = new StorageCheckOrderVo();
                    storageCheckOrderVo.setStoreId(storeId);
                    storageCheckOrderVo.setOrderId(resultOrderId);
                    storageCheckOrderVo.setTargetId(pVo.getSupplierId());
                    storageCheckOrderVo.setCreateTime(new Date());
                    storageCheckOrderVo.setOrderStatus((byte) 1);
                    storageCheckOrderVo.setGoodsSkuId(vo.getGoodsSkuId());
                    storageCheckOrderVo.setWarehouseId(pVo.getOutWarehouseId());
                    storageCheckOrderVo.setOutQuantity(vo.getChangeQuantity());
                    storageCheckOrderVo.setUserId(storageOrderVo.getUserId());
                    inventoryUtil.addStorageCheckOrderMethod(0, storageCheckOrderVo, null);
                }
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
                String resultOrderId = "XXHHD_" + UUID.randomUUID().toString().replace("-", "");

                //修改商品规格账面库存
                double costMoney = updateBookInventoryMethod(storageOrderVo, pVo, sVo, resultOrderId);

                //新增结果订单
                addResultOrderMethod(storageOrderVo, pVo, sVo, resultOrderId, costMoney);

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
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //设置初始属性
        storageOrderVo.setCreateTime(new Date());
        storageOrderVo.setOrderStatus((byte) 1);

        //新增收/发货单
        if (myStorageMapper.addStorageOrder(storageOrderVo) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }

        return CommonResponse.success();
    }

    /**
     * 红冲收/发货单
     * @param storageOrderVo
     * @return
     */
    @Transactional
    public CommonResponse redDashed(StorageOrderVo storageOrderVo) {
        //获取参数
        Integer storeId = storageOrderVo.getStoreId();
        String userId = storageOrderVo.getUserId();
        String remark = storageOrderVo.getRemark();

        //红冲
        if (myStorageMapper.redDashed(storageOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //查询红冲蓝单
        storageOrderVo = myStorageMapper.findStorageOrderById(storageOrderVo);

        //设置红冲红单
        storageOrderVo.setStoreId(storeId);
        storageOrderVo.setId("HC_" + storageOrderVo.getId());
        storageOrderVo.setCreateTime(new Date());
        storageOrderVo.setOrderStatus((byte) -2);
        storageOrderVo.setQuantity(-storageOrderVo.getQuantity());
        storageOrderVo.setUserId(userId);
        storageOrderVo.setRemark(remark);

        //新增红冲红单
        if (myStorageMapper.addStorageOrder(storageOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }
        return CommonResponse.success();
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
        
        //补上仓库名
        if (storageOrderVos.size() > 0) {
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
        
        return CommonResponse.success(commonResult);
    }

    //其他入/出库单、报溢/损单、成本调价单、库存盘点单

    /**
     * 新增其他入/出库单、报溢/损单、成本调价单、库存盘点单
     * @param storageResultOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addStorageResultOrder(StorageResultOrderVo storageResultOrderVo) {
        //判断新增单据类型
        byte type = storageResultOrderVo.getType();
        int flag;
        switch (type) {
            case 1:     //其他入库单
                //判断参数
                if (storageResultOrderVo.getDetails().size() == 0 ||
                        storageResultOrderVo.getTargetId() == null || storageResultOrderVo.getTotalMoney() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                storageResultOrderVo.setId("QTRKD_" + UUID.randomUUID().toString().replace("-", ""));
                flag = 1;
                break;

            case 2:     //其他出库单
                //判断参数
                if (storageResultOrderVo.getDetails().size() == 0 ||
                        storageResultOrderVo.getTargetId() == null || storageResultOrderVo.getTotalMoney() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                storageResultOrderVo.setId("QTCKD_" + UUID.randomUUID().toString().replace("-", ""));
                flag = 0;
                break;

            case 3:     //报溢单
                //判断参数
                if (storageResultOrderVo.getDetails().size() == 0 || storageResultOrderVo.getTotalMoney() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                storageResultOrderVo.setId("BYD_" + UUID.randomUUID().toString().replace("-", ""));
                flag = 1;
                break;

            case 4:     //报损单
                //判断参数
                if (storageResultOrderVo.getDetails().size() == 0 || storageResultOrderVo.getTotalMoney() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                storageResultOrderVo.setId("BSD_" + UUID.randomUUID().toString().replace("-", ""));
                flag = 0;
                break;

            case 5:     //成本调价单
                //判断参数
                if (storageResultOrderVo.getDetails().size() == 0 || storageResultOrderVo.getTotalMoney() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                storageResultOrderVo.setId("CBTJD_" + UUID.randomUUID().toString().replace("-", ""));
                flag = 2;
                break;

            case 6:     //库存盘点单
                //判断参数
                if (storageResultOrderVo.getDetails().size() == 0 || storageResultOrderVo.getTotalCheckQuantity() == null ||
                        storageResultOrderVo.getTotalInQuantity() == null || storageResultOrderVo.getTotalInMoney() == null ||
                        storageResultOrderVo.getTotalOutQuantity() == null || storageResultOrderVo.getTotalOutMoney() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                storageResultOrderVo.setId("KCPDD_" + UUID.randomUUID().toString().replace("-", ""));
                flag = 3;
                break;

            default:
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //设置初始属性
        storageResultOrderVo.setCreateTime(new Date());
        storageResultOrderVo.setOrderStatus((byte) 1);

        //新增单据
        if (myStorageMapper.addStorageResultOrder(storageResultOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }

        //新增单据/商品规格关系
        addOrderGoodsSku(flag, storageResultOrderVo);

        return CommonResponse.success();
    }

    /**
     * 新增单据对应的商品规格关系的方法
     * @param flag 0：出库，1：入库，2：成本调价单，3：库存盘点单
     * @param storageResultOrderVo
     */
    @Transactional
    public void addOrderGoodsSku(int flag, StorageResultOrderVo storageResultOrderVo) {
        //验证
        StorageResultOrderVo check = new StorageResultOrderVo();
        check.setTotalQuantity(0);      //总数量
        check.setTotalCheckQuantity(0);     //总库存数量
        check.setTotalInQuantity(0);        //总报溢数量
        check.setTotalInMoney(0.0);       //总报溢金额
        check.setTotalOutQuantity(0);       //总报损数量
        check.setTotalOutMoney(0.0);      //总报损金额
        check.setTotalMoney(0.0);

        //获取参数
        List<OrderGoodsSkuVo> vos = storageResultOrderVo.getDetails();
        Integer storeId = storageResultOrderVo.getStoreId();
        String orderId = storageResultOrderVo.getId();
        Integer warehouseId = storageResultOrderVo.getWarehouseId();

        //设置库存记账对象
        StorageCheckOrderVo storageCheckOrderVo = new StorageCheckOrderVo();
        storageCheckOrderVo.setStoreId(storeId);
        storageCheckOrderVo.setOrderId(orderId);
        storageCheckOrderVo.setTargetId(storageResultOrderVo.getTargetId());
        storageCheckOrderVo.setCreateTime(new Date());
        storageCheckOrderVo.setOrderStatus((byte) 1);
        storageCheckOrderVo.setUserId(storageResultOrderVo.getUserId());

        if (flag == 0 || flag == 1) {
            vos.stream().forEach(vo -> {
                //判断参数
                if (vo.getGoodsSkuId() == null || vo.getType() == null || vo.getType() != flag ||
                        vo.getQuantity() == null || vo.getMoney() == null ||
                        vo.getCheckQuantity() == null || vo.getCheckMoney() == null || vo.getCheckTotalMoney() == null){
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                //设置初始属性
                vo.setStoreId(storeId);
                vo.setOrderId(orderId);

                //新增单据/商品规格关系
                if (myOrderGoodsSkuMapper.addOrderGoodsSku(vo) != 1) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                //修改库存
                inventoryUtil.updateInventoryMethod(flag, new WarehouseGoodsSkuVo(storeId, warehouseId, vo.getGoodsSkuId(), vo.getQuantity(), vo.getQuantity(), vo.getQuantity()));

                //统计数量和金额
                check.setTotalQuantity(check.getTotalQuantity() + vo.getQuantity());        //出/入库数量
                check.setTotalMoney(check.getTotalMoney() + vo.getMoney());     //出/入库金额

                //库存记账
                storageCheckOrderVo.setGoodsSkuId(vo.getGoodsSkuId());
                if (flag == 0) {
                    storageCheckOrderVo.setWarehouseId(warehouseId);
                    storageCheckOrderVo.setOutQuantity(vo.getQuantity());
                    inventoryUtil.addStorageCheckOrderMethod(flag, storageCheckOrderVo, vo);
                } else if (flag == 1) {
                    storageCheckOrderVo.setWarehouseId(warehouseId);
                    storageCheckOrderVo.setInQuantity(vo.getQuantity());
                    storageCheckOrderVo.setInMoney((vo.getMoney()) / vo.getQuantity());
                    storageCheckOrderVo.setInTotalMoney(storageCheckOrderVo.getInQuantity() * storageCheckOrderVo.getInMoney());
                    inventoryUtil.addStorageCheckOrderMethod(flag, storageCheckOrderVo, vo);
                }
            });

        } else if (flag == 2) {     //成本调价单
            vos.stream().forEach(vo -> {
                //判断参数
                if (vo.getGoodsSkuId() == null ||
                        vo.getCheckQuantity() == null || vo.getCheckMoney() == null || vo.getCheckTotalMoney() == null ||
                        vo.getAfterChangeCheckMoney() == null || vo.getChangeCheckTotalMoney() == null){
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                //设置初始属性
                vo.setStoreId(storeId);
                vo.setOrderId(orderId);

                //新增单据/商品规格关系
                if (myOrderGoodsSkuMapper.addOrderGoodsSku(vo) != 1) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                //统计数量和金额
                check.setTotalCheckQuantity(check.getTotalCheckQuantity() + vo.getCheckQuantity());       //库存数量
                check.setTotalMoney(check.getTotalMoney() + vo.getChangeCheckTotalMoney());     //调整金额

                //库存记账
                storageCheckOrderVo.setGoodsSkuId(vo.getGoodsSkuId());
                if (vo.getChangeCheckTotalMoney() > 0) {
                    storageCheckOrderVo.setWarehouseId(storageResultOrderVo.getWarehouseId());
                    storageCheckOrderVo.setInTotalMoney(vo.getChangeCheckTotalMoney());
                    storageCheckOrderVo.setOutTotalMoney(0.0);
                } else {
                    storageCheckOrderVo.setInTotalMoney(0.0);
                    storageCheckOrderVo.setWarehouseId(storageResultOrderVo.getWarehouseId());
                    storageCheckOrderVo.setOutTotalMoney(-vo.getChangeCheckTotalMoney());
                }
                inventoryUtil.addStorageCheckOrderMethod(3, storageCheckOrderVo, vo);
            });

        } else if (flag == 3) {     //库存盘点单
            vos.stream().forEach(vo -> {
                //判断参数
                if (vo.getGoodsSkuId() == null || vo.getQuantity() == null ||
                        vo.getCheckQuantity() == null || vo.getCheckMoney() == null || vo.getCheckTotalMoney() == null ||
                        vo.getInQuantity() == null || vo.getInMoney() == null ||
                        vo.getOutQuantity() == null || vo.getOutMoney() == null){
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                //设置初始属性
                vo.setStoreId(storeId);
                vo.setOrderId(orderId);

                //新增单据/商品规格关系
                if (myOrderGoodsSkuMapper.addOrderGoodsSku(vo) != 1) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }

                //统计数量和金额
                check.setTotalCheckQuantity(check.getTotalCheckQuantity() + vo.getCheckQuantity());       //库存数量
                check.setTotalQuantity(check.getTotalQuantity() + vo.getQuantity());        //盘点数量
                check.setTotalInQuantity(check.getTotalInQuantity() + vo.getInQuantity());      //盘盈数量
                check.setTotalInMoney(check.getTotalInMoney() + vo.getInMoney());       //盘盈金额
                check.setTotalOutQuantity(check.getTotalOutQuantity() + vo.getOutQuantity());       //盘亏数量
                check.setTotalOutMoney(check.getTotalOutMoney() + vo.getOutMoney());        //盘亏金额

                //验证盘点数量、库存数量、盘盈/盘亏数量是否正确
                if (vo.getInQuantity() > 0 && vo.getInQuantity() != vo.getQuantity() - vo.getCheckQuantity()) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }
                if (vo.getOutQuantity() > 0 && vo.getOutQuantity() != vo.getCheckQuantity() - vo.getQuantity()) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }
            });

            //过滤盘盈的商品规格
            List<OrderGoodsSkuVo> inVos = new ArrayList<>(vos.stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getInQuantity() > 0).filter(orderGoodsSkuVo -> orderGoodsSkuVo.getInMoney() > 0)
                    .filter(orderGoodsSkuVo -> orderGoodsSkuVo.getOutQuantity() == 0).filter(orderGoodsSkuVo -> orderGoodsSkuVo.getOutMoney() == 0).collect(Collectors.toList()));

            //过滤盘亏的商品规格
            List<OrderGoodsSkuVo> outVos = vos.stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getInQuantity() == 0).filter(orderGoodsSkuVo -> orderGoodsSkuVo.getInMoney() == 0)
                    .filter(orderGoodsSkuVo -> orderGoodsSkuVo.getOutQuantity() > 0).filter(orderGoodsSkuVo -> orderGoodsSkuVo.getOutMoney() > 0).collect(Collectors.toList());

            if (inVos.size() > 0) {
                //新增报溢单
                StorageResultOrderVo sVo = new StorageResultOrderVo();
                sVo.setStoreId(storeId);
                sVo.setDetails(inVos);
                sVo.setType((byte) 3);
                sVo.setWarehouseId(warehouseId);
                sVo.setTotalQuantity(0);
                sVo.setTotalMoney(0.0);
                sVo.setUserId(storageResultOrderVo.getUserId());
                inVos.stream().forEach(vo -> {
                    sVo.setTotalQuantity(sVo.getTotalQuantity() + vo.getInQuantity());
                    sVo.setTotalMoney(sVo.getTotalMoney() + vo.getInMoney());
                    vo.setStoreId(null);
                    vo.setOrderId(null);
                    vo.setType((byte) 1);
                    vo.setQuantity(vo.getInQuantity());
                    vo.setMoney(vo.getInMoney());
                    vo.setInQuantity(null);
                    vo.setInMoney(null);
                    vo.setOutQuantity(null);
                    vo.setOutMoney(null);
                });
                addStorageResultOrder(sVo);
            }

            if (outVos.size() > 0) {
                //新增报损单
                StorageResultOrderVo sVo = new StorageResultOrderVo();
                sVo.setStoreId(storeId);
                sVo.setDetails(outVos);
                sVo.setType((byte) 4);
                sVo.setWarehouseId(warehouseId);
                sVo.setTotalQuantity(0);
                sVo.setTotalMoney(0.0);
                sVo.setUserId(storageResultOrderVo.getUserId());
                outVos.stream().forEach(vo -> {
                    sVo.setTotalQuantity(sVo.getTotalQuantity() + vo.getOutQuantity());
                    sVo.setTotalMoney(sVo.getTotalMoney() + vo.getOutMoney());
                    vo.setStoreId(null);
                    vo.setOrderId(null);
                    vo.setType((byte) 0);
                    vo.setQuantity(vo.getOutQuantity());
                    vo.setMoney(vo.getOutMoney());
                    vo.setOutQuantity(null);
                    vo.setOutMoney(null);
                    vo.setInQuantity(null);
                    vo.setInMoney(null);
                });
                addStorageResultOrder(sVo);
            }
        }
    }

    /**
     * 红冲其他入/出库单、报溢/损单、成本调价单、库存盘点单
     * @param storageResultOrderVo
     * @return
     */
    @Transactional
    public CommonResponse redDashedStorageResultOrder(StorageResultOrderVo storageResultOrderVo) {
        //判断参数
        if (storageResultOrderVo.getId() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //获取参数
        Integer storeId = storageResultOrderVo.getStoreId();
        String userId = storageResultOrderVo.getUserId();
        String remark = storageResultOrderVo.getRemark();

        //红冲
        if (myStorageMapper.redDashedStorageResultOrder(storageResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //查询红冲蓝单
        storageResultOrderVo = myStorageMapper.findStorageResultOrderDetailById(storageResultOrderVo);
        List<OrderGoodsSkuVo> vos = storageResultOrderVo.getDetails();
        Integer warehouseId = storageResultOrderVo.getWarehouseId();
        byte type  = storageResultOrderVo.getType();

        //设置红冲红单
        storageResultOrderVo.setStoreId(storeId);
        String oldResultOrderId = storageResultOrderVo.getId();
        storageResultOrderVo.setId("HC_" + oldResultOrderId);
        storageResultOrderVo.setCreateTime(new Date());
        storageResultOrderVo.setOrderStatus((byte) -2);
        storageResultOrderVo.setUserId(userId);
        storageResultOrderVo.setRemark(remark);

        //判断红冲类型
        if (type == 1 || type == 2 || type == 3 || type == 4) {     //其他入/出库单、报溢/损单
            storageResultOrderVo.setTotalQuantity(-storageResultOrderVo.getTotalQuantity());
            storageResultOrderVo.setTotalMoney(-storageResultOrderVo.getTotalMoney());

            //修改库存相关
            WarehouseGoodsSkuVo warehouseGoodsSkuVo = new WarehouseGoodsSkuVo(storeId, warehouseId);
            vos.stream().forEach(vo -> {
                warehouseGoodsSkuVo.setGoodsSkuId(vo.getGoodsSkuId());
                warehouseGoodsSkuVo.setRealInventory(vo.getQuantity());
                warehouseGoodsSkuVo.setCanUseInventory(vo.getQuantity());
                warehouseGoodsSkuVo.setBookInventory(vo.getQuantity());

                if (vo.getType() == 0) {        //原来是出库
                    //增加商品规格库存
                    inventoryUtil.updateInventoryMethod(1, warehouseGoodsSkuVo);

                    //红冲库存记账记录
                    inventoryUtil.redDashedStorageCheckOrderMethod(0, new StorageCheckOrderVo(storeId, oldResultOrderId, vo.getGoodsSkuId(), warehouseId, userId));
                } else if (vo.getType() == 1) {     //原来是入库
                    //减少商品规格库存
                    inventoryUtil.updateInventoryMethod(0, warehouseGoodsSkuVo);

                    //红冲库存记账记录
                    inventoryUtil.redDashedStorageCheckOrderMethod(1, new StorageCheckOrderVo(storeId, oldResultOrderId, vo.getGoodsSkuId(), warehouseId, userId));
                } else {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
            });

        } else if (type == 5) {     //成本调价单
            storageResultOrderVo.setTotalQuantity(-storageResultOrderVo.getTotalQuantity());
            storageResultOrderVo.setTotalMoney(-storageResultOrderVo.getTotalMoney());

            vos.stream().forEach(vo -> {
                if (vo.getChangeCheckTotalMoney() > 0) {        //原来是入库
                    //红冲库存记账记录
                    inventoryUtil.redDashedStorageCheckOrderMethod(2, new StorageCheckOrderVo(storeId, oldResultOrderId, vo.getGoodsSkuId(), warehouseId, userId));
                } else if (vo.getChangeCheckTotalMoney() < 0) {     //原来是出库
                    //红冲库存记账记录
                    inventoryUtil.redDashedStorageCheckOrderMethod(3, new StorageCheckOrderVo(storeId, oldResultOrderId, vo.getGoodsSkuId(), warehouseId, userId));
                } else {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
            });
        } else if (type == 6) {     //库存盘点单
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //新增红冲红单
        if (myStorageMapper.addStorageResultOrder(storageResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        return CommonResponse.success();
    }

    /**
     * 根据条件查询其他入/出库单、报溢/损单、成本调价单、库存盘点单
     * @param storageResultOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllStorageResultOrder(StorageResultOrderVo storageResultOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountStorageResultOrder(storageResultOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<StorageResultOrderVo> storageResultOrderVos = myStorageMapper.findAllPagedStorageResultOrder(storageResultOrderVo, pageVo);
        
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "id"));
        titles.add(new Title("单据类型", "type"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("仓库", "warehouseName"));
        byte type = storageResultOrderVo.getType();
        if (type == 1 || type == 2) {
            titles.add(new Title("往来单位", "targetName"));
        } else if (type == 1 || type == 2 || type == 3 || type == 4 || type == 5) {
            titles.add(new Title("总数量", "totalQuantity"));
            titles.add(new Title("总金额", "totalMoney"));
        } else if (type == 6) {
            titles.add(new Title("总库存数量", "totalCheckQuantity"));
            titles.add(new Title("总盘点数量", "totalQuantity"));
            titles.add(new Title("总报溢数量", "totalInQuantity"));
            titles.add(new Title("总报溢金额", "totalInMoney"));
            titles.add(new Title("总报损数量", "totalOutQuantity"));
            titles.add(new Title("总报损金额", "totalOutMoney"));
        }
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, storageResultOrderVos, pageVo);
        
        return CommonResponse.success(commonResult);
    }

    /**
     * 根据单据编号查询其他入/出库单、报溢/损单、成本调价单、库存盘点单
     * @param storageResultOrderVo
     * @return
     */
    public CommonResponse findStorageResultOrderDetailById(StorageResultOrderVo storageResultOrderVo) {
        storageResultOrderVo = myStorageMapper.findStorageResultOrderDetailById(storageResultOrderVo);
        return CommonResponse.success(storageResultOrderVo);
    }

    //查库存

    /**
     * 查当前库存
     * @param goodsWarehouseSkuVo
     * @param pageVo
     * @return
     */
    public CommonResponse findCurrentInventory(GoodsWarehouseSkuVo goodsWarehouseSkuVo, PageVo pageVo) {
        //获取参数
        Integer storeId = goodsWarehouseSkuVo.getStoreId();

        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountCurrentInventory(goodsWarehouseSkuVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<GoodsWarehouseSkuVo> goodsWarehouseSkuVos = myStorageMapper.findPagedCurrentInventory(goodsWarehouseSkuVo, pageVo);

        goodsWarehouseSkuVos.stream().forEach(vo -> {
            //根据商品规格编号查询最新库存记账记录
            StorageCheckOrderVo scoVo = myStorageMapper.findLastCheckMoneyByGoodsId(new StorageCheckOrderVo(storeId, vo.getId()));
            if (scoVo == null) {
                throw new CommonException(CommonResponse.FIND_ERROR);
            }
            vo.setCostMoney(scoVo.getCheckMoney2());
            vo.setTotalCostMoney(scoVo.getCheckTotalMoney2());
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
        
        return CommonResponse.success(commonResult);
    }

    /**
     * 根据商品货号查分布
     * @param goodsWarehouseSkuVo
     * @param pageVo
     * @return
     */
    public CommonResponse findDistributionByGoodsId(GoodsWarehouseSkuVo goodsWarehouseSkuVo, PageVo pageVo) {
        //获取参数
        Integer storeId = goodsWarehouseSkuVo.getStoreId();

        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountDistributionByGoodsId(goodsWarehouseSkuVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<GoodsWarehouseSkuVo> goodsWarehouseSkuVos = myStorageMapper.findPagedDistributionByGoodsId(goodsWarehouseSkuVo, pageVo);

        //查询所有商品规格
        List<GoodsSku> goodsSkus = myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(storeId));

        goodsWarehouseSkuVos.stream().forEach(vo -> {
            //根据商品货号过滤商品规格
            List<GoodsSku> goodsSkuList = goodsSkus.stream().filter(goodsSku -> goodsSku.getGoodsId().equals(vo.getId())).collect(Collectors.toList());
            double quantity = 0;
            double totalMoney = 0;
            for (GoodsSku goodsSku : goodsSkuList) {
                //根据商品规格编号和仓库编号查询最新库存记账记录
                StorageCheckOrderVo scoVo = myStorageMapper.findLastCheckMoneyByGoodsSkuIdAndWarehouseId(new StorageCheckOrderVo(storeId, goodsSku.getId(), vo.getWarehouseId()));
                if (scoVo != null) {
                    quantity += scoVo.getCheckQuantity();
                    totalMoney += scoVo.getCheckTotalMoney();
                }
            }
            vo.setCostMoney(totalMoney / quantity);
            vo.setTotalCostMoney(totalMoney);
        });
        
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
        titles.add(new Title("成本单价", "costMoney"));
        titles.add(new Title("成本金额", "totalCostMoney"));
        CommonResult commonResult = new CommonResult(titles, goodsWarehouseSkuVos, pageVo);
        
        return CommonResponse.success(commonResult);
    }

    /**
     * 根据商品货号查对账
     * @param storageCheckOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findStorageCheckOrderByGoodsId(StorageCheckOrderVo storageCheckOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountOrderByGoodsId(storageCheckOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<StorageCheckOrderVo> storageCheckOrderVos = myStorageMapper.findPagedOrderByGoodsId(storageCheckOrderVo, pageVo);
        
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "orderId"));
        titles.add(new Title("单据类型", "typeName"));
        titles.add(new Title("创建时间", "createTime"));
        titles.add(new Title("来源订单", "applyOrderId"));
        titles.add(new Title("往来单位", "targetName"));
        titles.add(new Title("商品规格", "sku"));
        titles.add(new Title("仓库", "warehouseName"));
        titles.add(new Title("入库数量", "inQuantity"));
        titles.add(new Title("入库成本单价", "inMoney"));
        titles.add(new Title("入库成本金额", "inTotalMoney"));
        titles.add(new Title("出库数量", "outQuantity"));
        titles.add(new Title("出库成本单价", "outMoney"));
        titles.add(new Title("出库成本金额", "outTotalMoney"));
        titles.add(new Title("结存数量", "checkQuantity2"));
        titles.add(new Title("结存成本单价", "checkMoney2"));
        titles.add(new Title("结存成本金额", "checkTotalMoney2"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, storageCheckOrderVos, pageVo);

        return CommonResponse.success(commonResult);
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
        titles.add(new Title("商品规格", "sku"));
        titles.add(new Title("实物库存", "realInventory"));
        titles.add(new Title("待发货数量", "notSentQuantity"));
        titles.add(new Title("待收货数量", "notReceivedQuantity"));
        titles.add(new Title("可用库存", "canUseInventory"));
        titles.add(new Title("账面库存", "bookInventory"));
        CommonResult commonResult = new CommonResult(titles, goodsWarehouseSkuVos, pageVo);
        
        return CommonResponse.success(commonResult);
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

        //补上成本和售价
        goodsWarehouseSkuVos.stream().forEach(vo -> {
            StorageCheckOrderVo storageCheckOrderVo = myStorageMapper.findLastCheckMoneyByGoodsSkuId(new StorageCheckOrderVo(goodsWarehouseSkuVo.getStoreId(), vo.getGoodsSkuId()));
            vo.setCostMoney(storageCheckOrderVo.getCheckMoney1());
            vo.setTotalCostMoney(storageCheckOrderVo.getCheckTotalMoney1());
            vo.setTotalRetailPrice(vo.getRetailPrice() * storageCheckOrderVo.getCheckQuantity1());
            vo.setTotalVipPrice(vo.getVipPrice() * storageCheckOrderVo.getCheckQuantity1());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("分类", "typeName"));
        titles.add(new Title("图片", "image"));
        titles.add(new Title("商品规格", "sku"));
        titles.add(new Title("实物库存", "realInventory"));
        titles.add(new Title("待发货数量", "notSentQuantity"));
        titles.add(new Title("待收货数量", "notReceivedQuantity"));
        titles.add(new Title("可用库存", "canUseInventory"));
        titles.add(new Title("账面库存", "bookInventory"));
        titles.add(new Title("成本单价", "costMoney"));
        titles.add(new Title("成本金额", "totalCostMoney"));
        titles.add(new Title("零售单价", "retailPrice"));
        titles.add(new Title("零售价值", "totalRetailPrice"));
        titles.add(new Title("vip单价", "vipPrice"));
        titles.add(new Title("vip价值", "totalVipPrice"));
        CommonResult commonResult = new CommonResult(titles, goodsWarehouseSkuVos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 根据商品规格编号查分布
     * @param goodsWarehouseSkuVo
     * @param pageVo
     * @return
     */
    public CommonResponse findDistributionByGoodsSkuId(GoodsWarehouseSkuVo goodsWarehouseSkuVo, PageVo pageVo) {
        //获取参数
        Integer storeId = goodsWarehouseSkuVo.getStoreId();

        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountDistributionByGoodsSkuId(goodsWarehouseSkuVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<GoodsWarehouseSkuVo> goodsWarehouseSkuVos = myStorageMapper.findPagedDistributionByGoodsSkuId(goodsWarehouseSkuVo, pageVo);

        //补上成本和售价
        goodsWarehouseSkuVos.stream().forEach(vo -> {
            //根据商品规格编号和仓库编号查询最新库存记账记录
            StorageCheckOrderVo scoVo = myStorageMapper.findLastCheckMoneyByGoodsSkuIdAndWarehouseId(new StorageCheckOrderVo(storeId, vo.getGoodsSkuId(), vo.getWarehouseId()));
            vo.setCostMoney(scoVo.getCheckMoney());
            vo.setTotalCostMoney(scoVo.getCheckTotalMoney());
            vo.setTotalRetailPrice(vo.getRetailPrice() * scoVo.getCheckQuantity());
            vo.setTotalVipPrice(vo.getVipPrice() * scoVo.getCheckQuantity());
        });

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("仓库", "warehouseName"));
        titles.add(new Title("商品规格", "sku"));
        titles.add(new Title("实物库存", "realInventory"));
        titles.add(new Title("待发货数量", "notSentQuantity"));
        titles.add(new Title("待收货数量", "notReceivedQuantity"));
        titles.add(new Title("可用库存", "canUseInventory"));
        titles.add(new Title("账面库存", "bookInventory"));
        titles.add(new Title("成本单价", "costMoney"));
        titles.add(new Title("成本金额", "totalCostMoney"));
        titles.add(new Title("零售单价", "retailPrice"));
        titles.add(new Title("零售价值", "totalRetailPrice"));
        titles.add(new Title("vip单价", "vipPrice"));
        titles.add(new Title("vip价值", "totalVipPrice"));
        CommonResult commonResult = new CommonResult(titles, goodsWarehouseSkuVos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 根据商品规格编号查对账
     * @param storageCheckOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findStorageCheckOrderByGoodsSkuId(StorageCheckOrderVo storageCheckOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountOrderByGoodsSkuId(storageCheckOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<StorageCheckOrderVo> storageCheckOrderVos = myStorageMapper.findPagedOrderByGoodsSkuId(storageCheckOrderVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "orderId"));
        titles.add(new Title("单据类型", "typeName"));
        titles.add(new Title("创建时间", "createTime"));
        titles.add(new Title("来源订单", "applyOrderId"));
        titles.add(new Title("往来单位", "targetName"));
        titles.add(new Title("商品规格", "sku"));
        titles.add(new Title("仓库", "warehouseName"));
        titles.add(new Title("入库数量", "inQuantity"));
        titles.add(new Title("入库成本单价", "inMoney"));
        titles.add(new Title("入库成本金额", "inTotalMoney"));
        titles.add(new Title("出库数量", "outQuantity"));
        titles.add(new Title("出库成本单价", "outMoney"));
        titles.add(new Title("出库成本金额", "outTotalMoney"));
        titles.add(new Title("结存数量", "checkQuantity1"));
        titles.add(new Title("结存成本单价", "checkMoney1"));
        titles.add(new Title("结存成本金额", "checkTotalMoney1"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, storageCheckOrderVos, pageVo);

        return CommonResponse.success(commonResult);
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

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("商品货号", "id"));
        titles.add(new Title("商品名", "name"));
        titles.add(new Title("条码", "barCode"));
        titles.add(new Title("分类", "typeName"));
        titles.add(new Title("图片", "image"));
        titles.add(new Title("仓库", "warehouseName"));
        titles.add(new Title("商品规格", "sku"));
        switch (goodsWarehouseSkuVo.getFlag()) {
            case 0:     //按仓库查库存
                //补上成本和售价
                goodsWarehouseSkuVos.stream().forEach(vo -> {
                    StorageCheckOrderVo scoVo = myStorageMapper.findLastCheckMoneyByGoodsSkuIdAndWarehouseId(new StorageCheckOrderVo(goodsWarehouseSkuVo.getStoreId(), vo.getGoodsSkuId(), vo.getWarehouseId()));
                    vo.setCostMoney(scoVo.getCheckMoney());
                    vo.setTotalCostMoney(scoVo.getCheckTotalMoney());
                    vo.setTotalRetailPrice(vo.getRetailPrice() * scoVo.getCheckQuantity());
                    vo.setTotalVipPrice(vo.getVipPrice() * scoVo.getCheckQuantity());
                });
                titles.add(new Title("实物库存", "realInventory"));
                titles.add(new Title("待发货数量", "notSentQuantity"));
                titles.add(new Title("待收货数量", "notReceivedQuantity"));
                titles.add(new Title("可用库存", "canUseInventory"));
                titles.add(new Title("账面库存", "bookInventory"));
                titles.add(new Title("成本单价", "costMoney"));
                titles.add(new Title("成本金额", "totalCostMoney"));
                titles.add(new Title("零售单价", "retailPrice"));
                titles.add(new Title("零售价值", "totalRetailPrice"));
                titles.add(new Title("vip单价", "vipPrice"));
                titles.add(new Title("vip价值", "totalVipPrice"));
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
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                titles.add(new Title("期初数量", "openingQuantity"));
                titles.add(new Title("期初成本单价", "openingMoney"));
                titles.add(new Title("期初金额", "openingTotalMoney"));
                break;
            default:
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }
        CommonResult commonResult = new CommonResult(titles, goodsWarehouseSkuVos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 根据商品规格编号和仓库编号查对账
     * @param storageCheckOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findStorageCheckOrderByGoodsSkuIdAndWarehouseId(StorageCheckOrderVo storageCheckOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myStorageMapper.findCountOrderByGoodsSkuIdAndWarehouseId(storageCheckOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<StorageCheckOrderVo> storageCheckOrderVos = myStorageMapper.findPagedOrderByGoodsSkuIdAndWarehouseId(storageCheckOrderVo, pageVo);

        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "orderId"));
        titles.add(new Title("单据类型", "typeName"));
        titles.add(new Title("创建时间", "createTime"));
        titles.add(new Title("来源订单", "applyOrderId"));
        titles.add(new Title("往来单位", "targetName"));
        titles.add(new Title("商品规格", "sku"));
        titles.add(new Title("仓库", "warehouseName"));
        titles.add(new Title("入库数量", "inQuantity"));
        titles.add(new Title("入库成本单价", "inMoney"));
        titles.add(new Title("入库成本金额", "inTotalMoney"));
        titles.add(new Title("出库数量", "outQuantity"));
        titles.add(new Title("出库成本单价", "outMoney"));
        titles.add(new Title("出库成本金额", "outTotalMoney"));
        titles.add(new Title("结存数量", "checkQuantity"));
        titles.add(new Title("结存成本单价", "checkMoney"));
        titles.add(new Title("结存成本金额", "checkTotalMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, storageCheckOrderVos, pageVo);

        return CommonResponse.success(commonResult);
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
        return CommonResponse.success();
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
        inventoryUtil.addOrUpdateWarehouseGoodsSkuMethod(vo);
        return CommonResponse.success();
    }
}
