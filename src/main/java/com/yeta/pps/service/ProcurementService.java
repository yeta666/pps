package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyOrderGoodsSkuMapper;
import com.yeta.pps.mapper.MyProcurementMapper;
import com.yeta.pps.mapper.MyWarehouseMapper;
import com.yeta.pps.po.ProcurementApplyOrder;
import com.yeta.pps.po.Warehouse;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.InventoryUtil;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 采购相关逻辑处理
 * @author YETA
 * @date 2018/12/10/15:04
 */
@Service
public class ProcurementService {

    @Autowired
    private MyProcurementMapper myProcurementMapper;

    @Autowired
    private MyOrderGoodsSkuMapper myOrderGoodsSkuMapper;

    @Autowired
    private MyWarehouseMapper myWarehouseMapper;

    @Autowired
    private InventoryUtil inventoryUtil;

    //采购申请订单

    /**
     * 新增采购申请订单
     * @param procurementApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //获取商品规格
        List<OrderGoodsSkuVo> orderGoodsSkuVos = procurementApplyOrderVo.getDetails();

        //判断新增类型
        Byte type = procurementApplyOrderVo.getType();
        if (type == 2 || type == 3) {
            ProcurementResultOrderVo prVo = myProcurementMapper.findResultOrderDetailById(new ProcurementResultOrderVo(procurementApplyOrderVo.getStoreId(), procurementApplyOrderVo.getResultOrderId()));
            if (prVo == null || prVo.getOrderStatus() < 0) {
                return CommonResponse.error(CommonResponse.ADD_ERROR, CommonResponse.STATUS_ERROR);
            }
        }
        switch (type) {
            case 1:     //采购订单
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }

                //设置初始属性
                procurementApplyOrderVo.setId("CGDD_" + UUID.randomUUID().toString().replace("-", ""));
                procurementApplyOrderVo.setOrderStatus((byte) 1);       //未收
                procurementApplyOrderVo.setInReceivedQuantity(0);
                procurementApplyOrderVo.setInNotReceivedQuantity(procurementApplyOrderVo.getInTotalQuantity());
                break;

            case 2:     //采购退货申请单
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null ||
                        procurementApplyOrderVo.getResultOrderId() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                //设置初始属性
                procurementApplyOrderVo.setId("CGTHSQD_" + UUID.randomUUID().toString().replace("-", ""));
                procurementApplyOrderVo.setOrderStatus((byte) 4);       //未发
                procurementApplyOrderVo.setOutSentQuantity(0);
                procurementApplyOrderVo.setOutNotSentQuantity(procurementApplyOrderVo.getOutTotalQuantity());
                break;

            case 3:     //采购换货申请单
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null ||
                        procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null ||
                        procurementApplyOrderVo.getResultOrderId() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                //设置初始属性
                procurementApplyOrderVo.setId("CGHHSQD_" + UUID.randomUUID().toString().replace("-", ""));
                procurementApplyOrderVo.setOrderStatus((byte) 7);       //未收未发
                procurementApplyOrderVo.setInReceivedQuantity(0);
                procurementApplyOrderVo.setInNotReceivedQuantity(procurementApplyOrderVo.getInTotalQuantity());
                procurementApplyOrderVo.setOutSentQuantity(0);
                procurementApplyOrderVo.setOutNotSentQuantity(procurementApplyOrderVo.getOutTotalQuantity());
                break;

            default:
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //设置初始属性
        procurementApplyOrderVo.setCreateTime(new Date());
        procurementApplyOrderVo.setClearStatus((byte) 0);
        procurementApplyOrderVo.setClearedMoney(0.0);
        procurementApplyOrderVo.setNotClearedMoney(procurementApplyOrderVo.getOrderMoney());

        //新增采购申请单
        if (myProcurementMapper.addApplyOrder(procurementApplyOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }

        //新增采购申请单/商品规格关系
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            //判断参数
            if (orderGoodsSkuVo.getType() == null || orderGoodsSkuVo.getGoodsSkuId() == null || orderGoodsSkuVo.getQuantity() == null || orderGoodsSkuVo.getMoney() == null || orderGoodsSkuVo.getDiscountMoney() == null) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }

            Integer storeId = procurementApplyOrderVo.getStoreId();
            Integer quantity = orderGoodsSkuVo.getQuantity();
            Integer goodsSkuId = orderGoodsSkuVo.getGoodsSkuId();

            //设置初始属性
            orderGoodsSkuVo.setStoreId(storeId);
            orderGoodsSkuVo.setOrderId(procurementApplyOrderVo.getId());
            orderGoodsSkuVo.setFinishQuantity(0);
            orderGoodsSkuVo.setNotFinishQuantity(quantity);
            orderGoodsSkuVo.setOperatedQuantity(0);

            //新增
            if (myOrderGoodsSkuMapper.addOrderGoodsSku(orderGoodsSkuVo) != 1) {
                throw new CommonException(CommonResponse.ADD_ERROR);
            }

            //修改采购入库单对应的商品规格已操作数量
            if (type == 2 || (type == 3 && orderGoodsSkuVo.getType() == 0)) {
                if (orderGoodsSkuVo.getId() == null) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }
                if (myOrderGoodsSkuMapper.updateOperatedQuantity(new OrderGoodsSkuVo(storeId, orderGoodsSkuVo.getId(), goodsSkuId, quantity)) != 1) {
                    throw new CommonException(CommonResponse.ADD_ERROR);
                }
            }

            //修改库存相关
            Integer warehouseId = null;
            int notSentQuantity = 0;
            int notReceivedQuantity = 0;
            if (orderGoodsSkuVo.getType() == 1) {
                warehouseId = procurementApplyOrderVo.getInWarehouseId();
                notReceivedQuantity = orderGoodsSkuVo.getQuantity();
            } else if (orderGoodsSkuVo.getType() == 0) {
                warehouseId = procurementApplyOrderVo.getOutWarehouseId();
                notSentQuantity = orderGoodsSkuVo.getQuantity();

                //减少可用库存
                inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, warehouseId, goodsSkuId, 0, notSentQuantity, 0));
            }
            //增加待发货数量、待收货数量
            inventoryUtil.updateNotQuantityMethod(1, new WarehouseGoodsSkuVo(storeId, warehouseId, goodsSkuId, notSentQuantity, notReceivedQuantity));
        });

        return CommonResponse.success();
    }

    /**
     * 删除采购申请订单
     * @param procurementApplyOrderVos
     * @return
     */
    @Transactional
    public CommonResponse deleteApplyOrder(List<ProcurementApplyOrderVo> procurementApplyOrderVos) {
        procurementApplyOrderVos.stream().forEach(procurementApplyOrderVo -> {
            //获取采购申请订单
            ProcurementApplyOrderVo applyOrderVo =  myProcurementMapper.findApplyOrderDetailById(procurementApplyOrderVo);
            if (applyOrderVo == null || applyOrderVo.getDetails().size() == 0) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
            //判断单据状态
            Byte orderStatus = applyOrderVo.getOrderStatus();
            if (orderStatus != 1 && orderStatus != 4 && orderStatus != 7) {     //1：未收，4：未发，7：未收未发
                throw new CommonException(CommonResponse.STATUS_ERROR);
            }
            //判断结算状态
            Byte clearStatus = applyOrderVo.getClearStatus();
            if (clearStatus != 0) {     //0：未完成
                throw new CommonException(CommonResponse.STATUS_ERROR);
            }
            //修改商品规格可操作数量
            if (applyOrderVo.getType() == 2 || applyOrderVo.getType() == 3) {
                List<OrderGoodsSkuVo> applyOrderGoodsSkuVos = applyOrderVo.getDetails().stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("0")).collect(Collectors.toList());        //采购退换申请单或采购换货申请单中出库的的商品规格
                ProcurementResultOrderVo resultOrderVo = myProcurementMapper.findResultOrderDetailById(new ProcurementResultOrderVo(procurementApplyOrderVo.getStoreId(), applyOrderVo.getResultOrderId()));
                List<OrderGoodsSkuVo> resultOrderGoodsSkuVos = resultOrderVo.getDetails();      //采购入库单的商品规格
                resultOrderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
                    Optional<OrderGoodsSkuVo> optional = applyOrderGoodsSkuVos.stream().filter(vo -> vo.getGoodsSkuId().equals(orderGoodsSkuVo.getGoodsSkuId())).findFirst();
                    if (optional.isPresent()) {
                        int changeQuantity = optional.get().getQuantity();
                        orderGoodsSkuVo.setStoreId(procurementApplyOrderVo.getStoreId());
                        orderGoodsSkuVo.setOperatedQuantity(-changeQuantity);
                        if (myOrderGoodsSkuMapper.updateOperatedQuantity(orderGoodsSkuVo) != 1) {
                            throw new CommonException(CommonResponse.DELETE_ERROR);
                        }
                    }
                });
            }
            //删除采购申请订单
            if (myProcurementMapper.deleteApplyOrder(procurementApplyOrderVo) != 1) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
            //删除采购申请订单/商品规格关系
            OrderGoodsSkuVo orderGoodsSkuVo = new OrderGoodsSkuVo(procurementApplyOrderVo.getStoreId(), applyOrderVo.getId());
            myOrderGoodsSkuMapper.deleteOrderGoodsSku(orderGoodsSkuVo);
        });
        return CommonResponse.success();
    }

    /**
     * 修改采购申请订单
     * @param procurementApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse updateApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //获取采购申请订单
        ProcurementApplyOrder pao = myProcurementMapper.findApplyOrderById(procurementApplyOrderVo);
        if (pao == null) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //判断单据状态
        Byte orderStatus = pao.getOrderStatus();
        if (orderStatus != 1 && orderStatus != 4 && orderStatus != 7) {     //1：未收，4：未发，7：未收未发
            return CommonResponse.error(CommonResponse.STATUS_ERROR);
        }

        //判断结算状态
        Byte clearStatus = pao.getClearStatus();
        if (clearStatus != 0) {     //0：未完成
            return CommonResponse.error(CommonResponse.STATUS_ERROR);
        }

        //判断是采购订单/采购退货申请/采购换货申请
        List<OrderGoodsSkuVo> orderGoodsSkuVos = procurementApplyOrderVo.getDetails();
        Byte type = pao.getType();
        switch (type) {
            case 1:     //采购订单
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                break;

            case 2:     //采购退货申请
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                break;

            case 3:     //采购换货申请
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null ||
                        procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }

            default:
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //修改采购申请订单
        if (myProcurementMapper.updateApplyOrder(procurementApplyOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //删除采购申请订单/商品规格关系
        myOrderGoodsSkuMapper.deleteOrderGoodsSku(new OrderGoodsSkuVo(procurementApplyOrderVo.getStoreId(), procurementApplyOrderVo.getId()));

        //新增采购申请订单/商品规格关系
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            //判断参数
            if (orderGoodsSkuVo.getType() == null || orderGoodsSkuVo.getGoodsSkuId() == null ||
                    orderGoodsSkuVo.getQuantity() == null || orderGoodsSkuVo.getMoney() == null || orderGoodsSkuVo.getDiscountMoney() == null) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }

            //设置初始属性
            orderGoodsSkuVo.setStoreId(procurementApplyOrderVo.getStoreId());
            orderGoodsSkuVo.setOrderId(procurementApplyOrderVo.getId());
            orderGoodsSkuVo.setFinishQuantity(0);
            orderGoodsSkuVo.setNotFinishQuantity(orderGoodsSkuVo.getQuantity());
            orderGoodsSkuVo.setOperatedQuantity(0);

            //新增
            if (myOrderGoodsSkuMapper.addOrderGoodsSku(orderGoodsSkuVo) != 1) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
        });

        return CommonResponse.success();
    }

    /**
     * 修改采购申请订单备注
     * @param procurementApplyOrderVo
     * @return
     */
    public CommonResponse updateApplyOrderRemark(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //修改备注
        if (myProcurementMapper.updateApplyOrderRemark(procurementApplyOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        return CommonResponse.success();
    }

    /**
     * 查询所有采购申请订单
     * @param procurementApplyOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myProcurementMapper.findCountApplyOrder(procurementApplyOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ProcurementApplyOrderVo> procurementApplyOrderVos = myProcurementMapper.findAllPagedApplyOrder(procurementApplyOrderVo, pageVo);

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
        titles.add(new Title("单据类型", "type"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("单据状态", "orderStatus"));
        titles.add(new Title("供应商", "supplierName"));
        byte type = procurementApplyOrderVo.getType();
        if (type == 1 || type == 3) {
            titles.add(new Title("入库仓库", "inWarehouseName"));
            titles.add(new Title("总收货数量", "inTotalQuantity"));
            titles.add(new Title("已收货数量", "inReceivedQuantity"));
            titles.add(new Title("未收货数量", "inNotReceivedQuantity"));
        } else if (type == 2 || type == 3) {
            titles.add(new Title("来源订单", "resultOrderId"));
            titles.add(new Title("出库仓库", "outWarehouseName"));
            titles.add(new Title("总发货数量", "outTotalQuantity"));
            titles.add(new Title("已发货数量", "outSentQuantity"));
            titles.add(new Title("未发货数量数量", "outNotSentQuantity"));
        }
        //仓库收发货不关心金额
        if (procurementApplyOrderVo.getOrderStatus() == null && procurementApplyOrderVo.getClearStatus() != null) {
            titles.add(new Title("总商品金额", "totalMoney"));
            titles.add(new Title("总优惠金额", "totalDiscountMoney"));
            titles.add(new Title("本单金额", "orderMoney"));
            titles.add(new Title("结算状态", "clearStatus"));
            titles.add(new Title("已结金额", "clearedMoney"));
            titles.add(new Title("未结金额", "notClearedMoney"));
        }
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, procurementApplyOrderVos, pageVo);

        return CommonResponse.success(commonResult);
    }

    /**
     * 根据单据编号查询申请订单详情
     * @param procurementApplyOrderVo
     * @return
     */
    public CommonResponse findApplyOrderDetailById(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //根据单据编号查询单据详情
        procurementApplyOrderVo = myProcurementMapper.findApplyOrderDetailById(procurementApplyOrderVo);
        if (procurementApplyOrderVo == null || procurementApplyOrderVo.getDetails().size() == 0) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }

        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(procurementApplyOrderVo.getStoreId()));
        for (Warehouse warehouse : warehouses) {
            if (procurementApplyOrderVo.getInWarehouseId() == warehouse.getId()) {
                procurementApplyOrderVo.setInWarehouseName(warehouse.getName());
            }
            if (procurementApplyOrderVo.getOutWarehouseId() == warehouse.getId()) {
                procurementApplyOrderVo.setOutWarehouseName(warehouse.getName());
            }
        }

        return CommonResponse.success(procurementApplyOrderVo);
    }

    //采购结果订单

    /**
     * 红冲采购结果订单
     * @param procurementResultOrderVo
     * @return
     */
    @Transactional
    public CommonResponse redDashed(ProcurementResultOrderVo procurementResultOrderVo) {
        //获取参数
        Integer storeId = procurementResultOrderVo.getStoreId();
        String userId = procurementResultOrderVo.getUserId();
        String remark = procurementResultOrderVo.getRemark();

        //红冲
        if (myProcurementMapper.redDashed(procurementResultOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //获取红冲蓝单
        procurementResultOrderVo = myProcurementMapper.findResultOrderDetailById(procurementResultOrderVo);

        //设置红冲红单
        procurementResultOrderVo.setStoreId(storeId);
        String oldResultOrderId = procurementResultOrderVo.getId();
        procurementResultOrderVo.setId("HC_" + oldResultOrderId);
        procurementResultOrderVo.setCreateTime(new Date());
        procurementResultOrderVo.setOrderStatus((byte) -2);
        procurementResultOrderVo.setTotalQuantity(-procurementResultOrderVo.getTotalQuantity());
        procurementResultOrderVo.setTotalMoney(-procurementResultOrderVo.getTotalMoney());
        procurementResultOrderVo.setTotalDiscountMoney(-procurementResultOrderVo.getTotalDiscountMoney());
        procurementResultOrderVo.setOrderMoney(-procurementResultOrderVo.getOrderMoney());
        procurementResultOrderVo.setUserId(userId);
        procurementResultOrderVo.setRemark(remark);

        //新增红冲红单
        if (myProcurementMapper.addResultOrder(procurementResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //查询该结果单对应的申请单
        ProcurementApplyOrderVo applyOrderVo = myProcurementMapper.findApplyOrderDetailById(new ProcurementApplyOrderVo(storeId, procurementResultOrderVo.getApplyOrderId()));
        List<OrderGoodsSkuVo> applyOrderDetails = applyOrderVo.getDetails();

        //红冲采购换货单要先入库再出库
        List<OrderGoodsSkuVo> orderGoodsSkuVos = procurementResultOrderVo.getDetails();
        List<OrderGoodsSkuVo> inVos = orderGoodsSkuVos.stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("1")).collect(Collectors.toList());
        List<OrderGoodsSkuVo> applyOrderInVos = applyOrderDetails.stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("1")).collect(Collectors.toList());
        for (OrderGoodsSkuVo inVo : inVos) {
            //已经退换货的结果单不能红冲
            if (inVo.getOperatedQuantity() > 0) {
                throw new CommonException(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
            }

            //减少商品规格库存
            inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, procurementResultOrderVo.getProcurementApplyOrderVo().getInWarehouseId(), inVo.getGoodsSkuId(), inVo.getQuantity(), inVo.getQuantity(), inVo.getQuantity()));

            //红冲库存记账记录
            inventoryUtil.redDashedStorageCheckOrderMethod(1, new StorageCheckOrderVo(storeId, oldResultOrderId, inVo.getGoodsSkuId(), userId));

            //减少完成数量
            Optional<OrderGoodsSkuVo> optional = applyOrderInVos.stream().filter(applyOrderInVo -> applyOrderInVo.getGoodsSkuId().toString().equals(inVo.getGoodsSkuId().toString())).findFirst();
            if (!optional.isPresent()) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
            OrderGoodsSkuVo applyOrderInVo = optional.get();
            applyOrderInVo.setStoreId(storeId);
            applyOrderInVo.setChangeQuantity(-inVo.getQuantity());
            if (myOrderGoodsSkuMapper.updateOrderGoodsSku(applyOrderInVo) != 1) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
            applyOrderVo.setInReceivedQuantity(applyOrderVo.getInReceivedQuantity() - inVo.getQuantity());
            applyOrderVo.setInNotReceivedQuantity(applyOrderVo.getInNotReceivedQuantity() + inVo.getQuantity());
        }

        List<OrderGoodsSkuVo> outVos = orderGoodsSkuVos.stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("0")).collect(Collectors.toList());
        List<OrderGoodsSkuVo> applyOrderOutVos = applyOrderDetails.stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("0")).collect(Collectors.toList());
        for (OrderGoodsSkuVo outVo : outVos) {
            //已经退换货的结果单不能红冲
            if (outVo.getOperatedQuantity() > 0) {
                throw new CommonException(CommonResponse.STATUS_ERROR);
            }

            //增加商品规格库存
            inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, procurementResultOrderVo.getProcurementApplyOrderVo().getOutWarehouseId(), outVo.getGoodsSkuId(), outVo.getQuantity(), outVo.getQuantity(), outVo.getQuantity()));

            //红冲库存记账记录
            inventoryUtil.redDashedStorageCheckOrderMethod(0, new StorageCheckOrderVo(storeId, oldResultOrderId, outVo.getGoodsSkuId(), userId));

            //减少完成数量
            Optional<OrderGoodsSkuVo> optional = applyOrderOutVos.stream().filter(applyOrderInVo -> applyOrderInVo.getGoodsSkuId().toString().equals(outVo.getGoodsSkuId().toString())).findFirst();
            if (!optional.isPresent()) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
            OrderGoodsSkuVo applyOrderInVo = optional.get();
            applyOrderInVo.setStoreId(storeId);
            applyOrderInVo.setChangeQuantity(-outVo.getQuantity());
            if (myOrderGoodsSkuMapper.updateOrderGoodsSku(applyOrderInVo) != 1) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
            applyOrderVo.setOutSentQuantity(applyOrderVo.getOutSentQuantity() - outVo.getQuantity());
            applyOrderVo.setOutNotSentQuantity(applyOrderVo.getOutNotSentQuantity() + outVo.getQuantity());
        }

        //修改申请订单的单据状态和完成数量
        Byte orderStatus = null;
        switch (applyOrderVo.getType()) {
            case 1:     //采购订单
                if (applyOrderVo.getInReceivedQuantity() == 0 && applyOrderVo.getInNotReceivedQuantity() == applyOrderVo.getInTotalQuantity()) {        //未收
                    orderStatus = 1;
                } else if (applyOrderVo.getInReceivedQuantity() > 0 && applyOrderVo.getInNotReceivedQuantity() < applyOrderVo.getInTotalQuantity()) {        //部分收
                    orderStatus = 2;
                } else if (applyOrderVo.getInReceivedQuantity() == applyOrderVo.getInTotalQuantity() && applyOrderVo.getInNotReceivedQuantity() == 0) {        //已收
                    orderStatus = 3;
                }
                if (myProcurementMapper.updateApplyOrderOrderStatusAndQuantity(new StorageOrderVo(storeId, (byte) 1, applyOrderVo.getId(), orderStatus, applyOrderVo.getInReceivedQuantity() - applyOrderVo.getInTotalQuantity())) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                break;
            case 2:     //采购退货申请单
                if (applyOrderVo.getOutSentQuantity() == 0 && applyOrderVo.getOutTotalQuantity() == applyOrderVo.getOutNotSentQuantity()) {        //未发
                    orderStatus = 4;
                } else if (applyOrderVo.getOutSentQuantity() > 0 && applyOrderVo.getOutNotSentQuantity() < applyOrderVo.getOutTotalQuantity()) {        //部分发
                    orderStatus = 5;
                } else if (applyOrderVo.getOutSentQuantity() == applyOrderVo.getOutTotalQuantity() && applyOrderVo.getOutNotSentQuantity() == 0) {        //已发
                    orderStatus = 6;
                }
                if (myProcurementMapper.updateApplyOrderOrderStatusAndQuantity(new StorageOrderVo(storeId, (byte) 4, applyOrderVo.getId(), orderStatus, applyOrderVo.getOutSentQuantity() - applyOrderVo.getOutTotalQuantity())) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                break;
            case 3:     //采购换货申请单
                if (applyOrderVo.getInReceivedQuantity() == 0 && applyOrderVo.getInNotReceivedQuantity() == applyOrderVo.getInTotalQuantity()) {        //未收
                    if (applyOrderVo.getOutSentQuantity() == 0 && applyOrderVo.getOutTotalQuantity() == applyOrderVo.getOutNotSentQuantity()) {        //未收未发
                        orderStatus = 7;
                    } else if (applyOrderVo.getOutSentQuantity() > 0 && applyOrderVo.getOutNotSentQuantity() < applyOrderVo.getOutTotalQuantity()) {        //未收部分发
                        orderStatus = 8;
                    } else if (applyOrderVo.getOutSentQuantity() == applyOrderVo.getOutTotalQuantity() && applyOrderVo.getOutNotSentQuantity() == 0) {        //未收已发
                        orderStatus = 9;
                    }
                } else if (applyOrderVo.getInReceivedQuantity() > 0 && applyOrderVo.getInNotReceivedQuantity() < applyOrderVo.getInTotalQuantity()) {        //部分收
                    if (applyOrderVo.getOutSentQuantity() == 0 && applyOrderVo.getOutTotalQuantity() == applyOrderVo.getOutNotSentQuantity()) {        //部分收未发
                        orderStatus = 10;
                    } else if (applyOrderVo.getOutSentQuantity() > 0 && applyOrderVo.getOutNotSentQuantity() < applyOrderVo.getOutTotalQuantity()) {        //部分收部分发
                        orderStatus = 11;
                    } else if (applyOrderVo.getOutSentQuantity() == applyOrderVo.getOutTotalQuantity() && applyOrderVo.getOutNotSentQuantity() == 0) {        //部分收已发
                        orderStatus = 12;
                    }
                } else if (applyOrderVo.getInReceivedQuantity() == applyOrderVo.getInTotalQuantity() && applyOrderVo.getInNotReceivedQuantity() == 0) {        //已收
                    if (applyOrderVo.getOutSentQuantity() == 0 && applyOrderVo.getOutTotalQuantity() == applyOrderVo.getOutNotSentQuantity()) {        //已收未发
                        orderStatus = 13;
                    } else if (applyOrderVo.getOutSentQuantity() > 0 && applyOrderVo.getOutNotSentQuantity() < applyOrderVo.getOutTotalQuantity()) {        //已收部分发
                        orderStatus = 14;
                    } else if (applyOrderVo.getOutSentQuantity() == applyOrderVo.getOutTotalQuantity() && applyOrderVo.getOutNotSentQuantity() == 0) {        //已收已发
                        orderStatus = 15;
                    }
                }
                if (myProcurementMapper.updateApplyOrderOrderStatusAndQuantity(new StorageOrderVo(storeId, (byte) 2, applyOrderVo.getId(), orderStatus, applyOrderVo.getInReceivedQuantity() - applyOrderVo.getInTotalQuantity())) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                if (myProcurementMapper.updateApplyOrderOrderStatusAndQuantity(new StorageOrderVo(storeId, (byte) 4, applyOrderVo.getId(), orderStatus, applyOrderVo.getOutSentQuantity() - applyOrderVo.getOutTotalQuantity())) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                break;
            default:
                throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //修改商品规格可操作数量
        if (procurementResultOrderVo.getType() == 2 || procurementResultOrderVo.getType() == 3) {
            List<OrderGoodsSkuVo> applyOrderGoodsSkuVos = applyOrderVo.getDetails().stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("0")).collect(Collectors.toList());        //采购退换申请单或采购换货申请单中出库的的商品规格

            //查询该采购退货申请单或采购换货申请单对应的采购入库单
            ProcurementResultOrderVo resultOrderVo = myProcurementMapper.findResultOrderDetailById(new ProcurementResultOrderVo(storeId, applyOrderVo.getResultOrderId()));
            List<OrderGoodsSkuVo> resultOrderGoodsSkuVos = resultOrderVo.getDetails();      //采购入库单的商品规格
            resultOrderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
                Optional<OrderGoodsSkuVo> optional = applyOrderGoodsSkuVos.stream().filter(vo -> vo.getGoodsSkuId().equals(orderGoodsSkuVo.getGoodsSkuId())).findFirst();
                if (optional.isPresent()) {
                    int changeQuantity = optional.get().getQuantity();
                    orderGoodsSkuVo.setStoreId(storeId);
                    orderGoodsSkuVo.setOperatedQuantity(-changeQuantity);
                    if (myOrderGoodsSkuMapper.updateOperatedQuantity(orderGoodsSkuVo) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                }
            });
        }

        //修改

        return CommonResponse.success();
    }

    /**
     * 查询所有采购结果订单
     * @param procurementResultOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllResultOrder(ProcurementResultOrderVo procurementResultOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myProcurementMapper.findCountResultOrder(procurementResultOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ProcurementResultOrderVo> procurementResultOrderVos = myProcurementMapper.findAllPagedResultOrder(procurementResultOrderVo, pageVo);

        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(procurementResultOrderVo.getStoreId()));
        procurementResultOrderVos.stream().forEach(vo -> {
            warehouses.stream().forEach(warehouse -> {
                if (vo.getProcurementApplyOrderVo().getInWarehouseId() == warehouse.getId()) {
                    vo.getProcurementApplyOrderVo().setInWarehouseName(warehouse.getName());
                }
                if (vo.getProcurementApplyOrderVo().getOutWarehouseId() == warehouse.getId()) {
                    vo.getProcurementApplyOrderVo().setOutWarehouseName(warehouse.getName());
                }
            });
        });
        
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("单据编号", "id"));
        titles.add(new Title("单据类型", "type"));
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("来源订单", "applyOrderId"));
        titles.add(new Title("结算状态", "procurementApplyOrderVo.clearStatus"));
        titles.add(new Title("供应商", "procurementApplyOrderVo.supplierName"));
        titles.add(new Title("入库仓库", "procurementApplyOrderVo.inWarehouseName"));
        titles.add(new Title("出库仓库", "procurementApplyOrderVo.outWarehouseName"));
        titles.add(new Title("总商品数量", "totalQuantity"));
        titles.add(new Title("总订单金额", "totalMoney"));
        titles.add(new Title("总优惠金额", "totalDiscountMoney"));
        titles.add(new Title("本单金额", "orderMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, procurementResultOrderVos, pageVo);
        
        return CommonResponse.success(commonResult);
    }

    /**
     * 根据单据编号查询结果订单详情
     * @param procurementResultOrderVo
     * @return
     */
    public CommonResponse findResultOrderDetailById(ProcurementResultOrderVo procurementResultOrderVo) {
        //根据单据编号查询单据详情
        procurementResultOrderVo = myProcurementMapper.findResultOrderDetailById(procurementResultOrderVo);
        if (procurementResultOrderVo == null || procurementResultOrderVo.getDetails().size() == 0) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }

        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(procurementResultOrderVo.getStoreId()));
        for (Warehouse warehouse : warehouses) {
            if (procurementResultOrderVo.getProcurementApplyOrderVo().getInWarehouseId() == warehouse.getId()) {
                procurementResultOrderVo.getProcurementApplyOrderVo().setInWarehouseName(warehouse.getName());
            }
            if (procurementResultOrderVo.getProcurementApplyOrderVo().getOutWarehouseId() == warehouse.getId()) {
                procurementResultOrderVo.getProcurementApplyOrderVo().setOutWarehouseName(warehouse.getName());
            }
        }

        return CommonResponse.success(procurementResultOrderVo);
    }
}
