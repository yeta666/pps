package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyGoodsMapper;
import com.yeta.pps.mapper.MyProcurementMapper;
import com.yeta.pps.mapper.MyStorageMapper;
import com.yeta.pps.mapper.MyWarehouseMapper;
import com.yeta.pps.po.ProcurementApplyOrder;
import com.yeta.pps.po.Warehouse;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private MyWarehouseMapper myWarehouseMapper;

    @Autowired
    private MyGoodsMapper myGoodsMapper;

    /**
     * 修改商品规格完成情况和修改商品规格库存
     * @param storeId
     * @param paogsvs
     */
    @Transactional
    public void updateGoodsSku(byte type, int storeId, List<ProcurementApplyOrderGoodsSkuVo> paogsvs) {
        for (ProcurementApplyOrderGoodsSkuVo paogsv : paogsvs) {
            //判断参数
            if (paogsv.getId() == null || paogsv.getGoodsSkuId() == null || paogsv.getChangeQuantity() == null) {
                throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
            }
            paogsv.setStoreId(storeId);
            //修改商品规格完成情况
            if (myProcurementMapper.updateApplyOrderGoodsSku(paogsv) != 1) {
                throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
            }
            if ((type == 1 || type == 2) && paogsv.getType() == 1) {        //入库
                //增加商品规格库存
                if (myGoodsMapper.increaseGoodsSkuInventory(new GoodsSkuVo(storeId, paogsv.getGoodsSkuId(), paogsv.getChangeQuantity())) != 1) {
                    throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                }
            } else if ((type == 3 || type == 4) && paogsv.getType() == 0) {     //出库
                //减少商品规格库存
                if (myGoodsMapper.decreaseGoodsSkuInventory(new GoodsSkuVo(storeId, paogsv.getGoodsSkuId(), paogsv.getChangeQuantity())) != 1) {
                    throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                }
            }
        }
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
        Integer quantity = storageOrderVo.getQuantity();
        ProcurementApplyOrderVo procurementApplyOrderVo = storageOrderVo.getProcurementApplyOrderVo();
        List<ProcurementApplyOrderGoodsSkuVo> paogsvs = procurementApplyOrderVo.getDetails();
        //获取对应的采购申请订单
        ProcurementApplyOrder procurementApplyOrder = myProcurementMapper.findApplyOrderById(new ProcurementApplyOrderVo(storeId, applyOrderId));
        if (procurementApplyOrder == null || paogsvs.size() == 0 || quantity <= 0 ||
                procurementApplyOrderVo.getTotalMoney() == null || procurementApplyOrderVo.getTotalDiscountMoney() == null || procurementApplyOrderVo.getOrderMoney() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //单据类型
        byte applyOrderType = procurementApplyOrder.getType();
        //单据状态
        byte orderStatus = procurementApplyOrder.getOrderStatus();
        byte applyOrderStatus;
        Integer inTotalQuantity = procurementApplyOrder.getInTotalQuantity();
        Integer inReceivedQuantity = procurementApplyOrder.getInReceivedQuantity();
        Integer inNotReceivedQuantity = procurementApplyOrder.getInNotReceivedQuantity();
        Integer outTotalQuantity = procurementApplyOrder.getOutTotalQuantity();
        Integer outSentQuantity = procurementApplyOrder.getOutSentQuantity();
        Integer outNotSentQuantity = procurementApplyOrder.getOutNotSentQuantity();
        //判断新增收/发货单类型
        Byte type = storageOrderVo.getType();
        switch (type) {
            //采购订单收货单
            case 1:
                storageOrderVo.setId("CGDD_SHD_" + UUID.randomUUID().toString());
                //判断完成情况
                if (quantity == inNotReceivedQuantity && quantity + inReceivedQuantity == inTotalQuantity) {        //全部完成
                    applyOrderStatus = 3;
                } else if (quantity < inNotReceivedQuantity && quantity + inReceivedQuantity < inTotalQuantity) {      //部分完成
                    applyOrderStatus = 2;
                } else {        //错误
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                //修改对应的采购订单单据状态和商品完成数量
                if (applyOrderType == 1 && (orderStatus == 1 || orderStatus == 2)) {
                    storageOrderVo.setOrderStatus(applyOrderStatus);
                    if (myProcurementMapper.updateApplyOrderOrderStatusAndQuantity(storageOrderVo) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                } else {
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                //修改商品规格完成情况和修改商品规格库存
                updateGoodsSku(type, storeId, paogsvs);
                //新增采购入库单
                if (myProcurementMapper.addResultOrder(new ProcurementResultOrderVo(storeId, "CGRKD_" + UUID.randomUUID().toString(), applyOrderType, new Date(), applyOrderId, (byte) 1, quantity, procurementApplyOrderVo.getTotalMoney(), procurementApplyOrderVo.getTotalDiscountMoney(), procurementApplyOrderVo.getOrderMoney(), storageOrderVo.getUserId())) != 1) {
                    throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                }
                break;
            //退【换】货申请收货单
            case 2:
                storageOrderVo.setId("THHSQD_SHD_" + UUID.randomUUID().toString());
                //判断完成情况
                if (quantity == inNotReceivedQuantity && quantity + inReceivedQuantity == inTotalQuantity) {        //全部完成
                    if (orderStatus == 8 || orderStatus == 11) {
                        applyOrderStatus = 14;
                    } else if (orderStatus == 9 || orderStatus == 12) {
                        applyOrderStatus = 15;
                    } else if (orderStatus == 7 || orderStatus == 10) {
                        applyOrderStatus = 13;
                    } else {
                        return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                    }
                } else if (quantity < inNotReceivedQuantity && quantity + inReceivedQuantity < inTotalQuantity) {      //部分完成
                    if (orderStatus == 7 || orderStatus == 10) {
                        applyOrderStatus = 10;
                    } else if (orderStatus == 8 || orderStatus == 11) {
                        applyOrderStatus = 11;
                    } else if (orderStatus == 9 || orderStatus == 12) {
                        applyOrderStatus = 12;
                    } else {
                        return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                    }
                } else {        //错误
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                //修改对应的采购换货单单据状态和商品完成数量
                if (applyOrderType == 3 && (orderStatus == 7 || orderStatus == 8 || orderStatus == 9 || orderStatus == 10 || orderStatus == 11 || orderStatus == 12)) {
                    storageOrderVo.setOrderStatus(applyOrderStatus);
                    if (myProcurementMapper.updateApplyOrderOrderStatusAndQuantity(storageOrderVo) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                } else {
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                //修改商品规格完成情况和增加商品规格库存
                updateGoodsSku(type, storeId, paogsvs);
                if (applyOrderStatus == 15) {
                    //新增采购换货单
                    if (myProcurementMapper.addResultOrder(new ProcurementResultOrderVo(storeId, "CGHHD_" + UUID.randomUUID().toString(), applyOrderType, new Date(), applyOrderId, (byte) 1, quantity, procurementApplyOrderVo.getTotalMoney(), procurementApplyOrderVo.getTotalDiscountMoney(), procurementApplyOrderVo.getOrderMoney(), storageOrderVo.getUserId())) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                }
                break;
            //销售订单发货单
            case 3:
                storageOrderVo.setId("XXDD_FHD_" + UUID.randomUUID().toString());
                break;
            //【退】【换】货申请发货单
            case 4:
                storageOrderVo.setId("THHSQD_FHD_" + UUID.randomUUID().toString());
                //判断完成情况
                if (quantity == outNotSentQuantity && quantity + outSentQuantity == outTotalQuantity) {        //全部完成
                    if (orderStatus == 4 || orderStatus == 5) {
                        applyOrderStatus = 6;
                    } else if (orderStatus == 7 || orderStatus == 8) {
                        applyOrderStatus = 9;
                    } else if (orderStatus == 10 || orderStatus == 11) {
                        applyOrderStatus = 12;
                    } else if (orderStatus == 13 || orderStatus == 14) {
                        applyOrderStatus = 15;
                    } else {
                        return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                    }
                } else if (quantity < outNotSentQuantity && quantity + outSentQuantity < outTotalQuantity) {      //部分完成
                    if (orderStatus == 4 || orderStatus == 5) {
                        applyOrderStatus = 5;
                    } else if (orderStatus == 7 || orderStatus == 8) {
                        applyOrderStatus = 8;
                    } else if (orderStatus == 10 || orderStatus == 11) {
                        applyOrderStatus = 11;
                    } else if (orderStatus == 13 || orderStatus == 14) {
                        applyOrderStatus = 14;
                    } else {
                        return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                    }
                } else {        //错误
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                //修改对应的采购换货单单据状态和商品完成数量
                if ((applyOrderType == 2 && (orderStatus == 4 || orderStatus == 5)) ||
                        (applyOrderType == 3 && (orderStatus == 7 || orderStatus == 8 || orderStatus == 10 || orderStatus == 11 || orderStatus == 13 || orderStatus == 14))) {
                    storageOrderVo.setOrderStatus(applyOrderStatus);
                    if (myProcurementMapper.updateApplyOrderOrderStatusAndQuantity(storageOrderVo) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                } else {
                    return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
                }
                //修改商品规格完成情况和增加商品规格库存
                updateGoodsSku(type, storeId, paogsvs);
                if (applyOrderStatus == 15) {
                    //新增采购换货单
                    if (myProcurementMapper.addResultOrder(new ProcurementResultOrderVo(storeId, "CGHHD_" + UUID.randomUUID().toString(), applyOrderType, new Date(), applyOrderId, (byte) 1, procurementApplyOrder.getInTotalQuantity() - procurementApplyOrder.getOutTotalQuantity(), procurementApplyOrder.getTotalMoney(), procurementApplyOrder.getTotalDiscountMoney(), procurementApplyOrder.getOrderMoney(), storageOrderVo.getUserId())) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                } else if (applyOrderStatus == 5 || applyOrderStatus == 6) {
                    //新增采购退货单
                    if (myProcurementMapper.addResultOrder(new ProcurementResultOrderVo(storeId, "CGTHD_" + UUID.randomUUID().toString(), applyOrderType, new Date(), applyOrderId, (byte) 1, -quantity, procurementApplyOrderVo.getTotalMoney(), procurementApplyOrderVo.getTotalDiscountMoney(), procurementApplyOrderVo.getOrderMoney(), storageOrderVo.getUserId())) != 1) {
                        throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                    }
                }
                break;
            default:
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        storageOrderVo.setCreateTime(new Date());
        storageOrderVo.setOrderStatus((byte) 0);
        //新增收货单
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
        titles.add(new Title("单据状态", "orderStatus"));
        titles.add(new Title("来源订单", "applyOrderId"));
        titles.add(new Title("供应商", "procurementApplyOrderVo.supplierName"));
        if (storageOrderVo.getType() == 1 || storageOrderVo.getType() == 2) {
            titles.add(new Title("入库仓库", "procurementApplyOrderVo.inWarehouseName"));
        } else if (storageOrderVo.getType() == 3 || storageOrderVo.getType() == 4) {
            titles.add(new Title("出库仓库", "procurementApplyOrderVo.outWarehouseName"));
        }
        titles.add(new Title("本单数量", "quantity"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, storageOrderVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }
}
