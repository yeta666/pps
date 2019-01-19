package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyOrderGoodsSkuMapper;
import com.yeta.pps.mapper.MyProcurementMapper;
import com.yeta.pps.mapper.MyWarehouseMapper;
import com.yeta.pps.po.ProcurementApplyOrder;
import com.yeta.pps.po.Warehouse;
import com.yeta.pps.util.*;
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

    @Autowired
    private FundUtil fundUtil;

    @Autowired
    private SystemUtil systemUtil;

    //采购申请订单

    @Transactional
    public void cgdd(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //获取参数
        List<OrderGoodsSkuVo> orderGoodsSkuVos = procurementApplyOrderVo.getDetails();

        //判断参数
        if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //设置初始属性
        procurementApplyOrderVo.setId("CGDD_" + UUID.randomUUID().toString().replace("-", ""));
        procurementApplyOrderVo.setCreateTime(new Date());
        procurementApplyOrderVo.setOrderStatus((byte) 1);       //未收
        procurementApplyOrderVo.setClearStatus((byte) 0);       //未完成
        procurementApplyOrderVo.setInReceivedQuantity(0);
        procurementApplyOrderVo.setInNotReceivedQuantity(procurementApplyOrderVo.getInTotalQuantity());
        procurementApplyOrderVo.setClearedMoney(0.0);
        procurementApplyOrderVo.setNotClearedMoney(procurementApplyOrderVo.getOrderMoney());

        //商品规格相关操作
        inventoryMethod(procurementApplyOrderVo, null);
    }

    @Transactional
    public void cgthsqd(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //获取参数
        List<OrderGoodsSkuVo> orderGoodsSkuVos = procurementApplyOrderVo.getDetails();

        //判断参数
        if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null ||
                procurementApplyOrderVo.getResultOrderId() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //已红冲的采购入库单不能退换货
        ProcurementResultOrderVo prVo = myProcurementMapper.findResultOrderDetailById(new ProcurementResultOrderVo(procurementApplyOrderVo.getStoreId(), procurementApplyOrderVo.getResultOrderId()));
        if (prVo == null || prVo.getOrderStatus() < 0) {
            throw new CommonException(CommonResponse.ADD_ERROR, CommonResponse.STATUS_ERROR);
        }

        //设置初始属性
        procurementApplyOrderVo.setId("CGTHSQD_" + UUID.randomUUID().toString().replace("-", ""));
        procurementApplyOrderVo.setCreateTime(new Date());
        procurementApplyOrderVo.setOrderStatus((byte) 4);       //未发
        procurementApplyOrderVo.setClearStatus((byte) 0);       //未完成
        procurementApplyOrderVo.setOutSentQuantity(0);
        procurementApplyOrderVo.setOutNotSentQuantity(procurementApplyOrderVo.getOutTotalQuantity());
        procurementApplyOrderVo.setClearedMoney(0.0);
        procurementApplyOrderVo.setNotClearedMoney(procurementApplyOrderVo.getOrderMoney());

        //商品规格相关操作
        inventoryMethod(procurementApplyOrderVo, null);
    }

    @Transactional
    public void cghhsqd(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //获取参数
        List<OrderGoodsSkuVo> orderGoodsSkuVos = procurementApplyOrderVo.getDetails();

        //判断参数
        if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null ||
                procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null ||
                procurementApplyOrderVo.getInTotalQuantity() != procurementApplyOrderVo.getOutTotalQuantity() ||
                procurementApplyOrderVo.getTotalMoney() != 0 || procurementApplyOrderVo.getTotalDiscountMoney() != 0 || procurementApplyOrderVo.getOrderMoney() != 0 ||
                procurementApplyOrderVo.getResultOrderId() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //已红冲的采购入库单不能退换货
        ProcurementResultOrderVo prVo = myProcurementMapper.findResultOrderDetailById(new ProcurementResultOrderVo(procurementApplyOrderVo.getStoreId(), procurementApplyOrderVo.getResultOrderId()));
        if (prVo == null || prVo.getOrderStatus() < 0) {
            throw new CommonException(CommonResponse.ADD_ERROR, CommonResponse.STATUS_ERROR);
        }

        //设置初始属性
        procurementApplyOrderVo.setId("CGHHSQD_" + UUID.randomUUID().toString().replace("-", ""));
        procurementApplyOrderVo.setCreateTime(new Date());
        procurementApplyOrderVo.setOrderStatus((byte) 7);       //未收未发
        procurementApplyOrderVo.setClearStatus((byte) 1);       //已完成
        procurementApplyOrderVo.setInReceivedQuantity(0);
        procurementApplyOrderVo.setInNotReceivedQuantity(procurementApplyOrderVo.getInTotalQuantity());
        procurementApplyOrderVo.setOutSentQuantity(0);
        procurementApplyOrderVo.setOutNotSentQuantity(procurementApplyOrderVo.getOutTotalQuantity());
        procurementApplyOrderVo.setClearedMoney(procurementApplyOrderVo.getOrderMoney());
        procurementApplyOrderVo.setNotClearedMoney(0.0);

        //商品规格相关操作
        inventoryMethod(procurementApplyOrderVo, null);
    }

    /**
     * 库存相关操作共用方法
     * @param procurementApplyOrderVo
     * @param oldVo
     */
    @Transactional
    public void inventoryMethod(ProcurementApplyOrderVo procurementApplyOrderVo, ProcurementApplyOrderVo oldVo) {
        List<OrderGoodsSkuVo> orderGoodsSkuVos = procurementApplyOrderVo.getDetails();
        Integer storeId = procurementApplyOrderVo.getStoreId();
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {

            Integer quantity = orderGoodsSkuVo.getQuantity();
            Integer goodsSkuId = orderGoodsSkuVo.getGoodsSkuId();

            //设置初始属性
            orderGoodsSkuVo.setStoreId(storeId);
            orderGoodsSkuVo.setOrderId(procurementApplyOrderVo.getId());
            orderGoodsSkuVo.setFinishQuantity(0);
            orderGoodsSkuVo.setNotFinishQuantity(quantity);
            orderGoodsSkuVo.setOperatedQuantity(0);

            //修改库存相关
            Integer warehouseId = null;
            int notSentQuantity = 0;
            int notReceivedQuantity = 0;
            if (orderGoodsSkuVo.getType() == 1) {
                warehouseId = procurementApplyOrderVo.getInWarehouseId() == null ? oldVo.getInWarehouseId() : procurementApplyOrderVo.getInWarehouseId();
                notReceivedQuantity = quantity;
            } else if (orderGoodsSkuVo.getType() == 0) {
                warehouseId = procurementApplyOrderVo.getOutWarehouseId() == null ? oldVo.getOutWarehouseId() : procurementApplyOrderVo.getOutWarehouseId();
                notSentQuantity = quantity;

                //减少可用库存
                inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, warehouseId, goodsSkuId, 0, notSentQuantity, 0));
            }
            //增加待发货数量、待收货数量
            inventoryUtil.updateNotQuantityMethod(1, new WarehouseGoodsSkuVo(storeId, warehouseId, goodsSkuId, notSentQuantity, notReceivedQuantity));

            //新增申请单/商品规格关系
            if (myOrderGoodsSkuMapper.addOrderGoodsSku(orderGoodsSkuVo) != 1) {
                throw new CommonException(CommonResponse.ADD_ERROR);
            }

            //修改采购入库单对应的商品规格已操作数量
            byte type = procurementApplyOrderVo.getType() == null ? oldVo.getType() : procurementApplyOrderVo.getType();
            if (type == 2 || (type == 3 && orderGoodsSkuVo.getType() == 0)) {
                if (orderGoodsSkuVo.getId() == null) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }
                if (myOrderGoodsSkuMapper.updateOperatedQuantity(new OrderGoodsSkuVo(storeId, orderGoodsSkuVo.getId(), goodsSkuId, quantity)) != 1) {
                    throw new CommonException(CommonResponse.ADD_ERROR);
                }
            }
        });
    }

    /**
     * 新增采购申请订单
     * @param procurementApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //判断系统是否开账
        if (!systemUtil.judgeStartBillMethod(procurementApplyOrderVo.getStoreId())) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR, "系统未开账");
        }

        //获取参数
        List<OrderGoodsSkuVo> orderGoodsSkuVos = procurementApplyOrderVo.getDetails();
        Byte type = procurementApplyOrderVo.getType();

        //判断参数、总商品数量、总商品金额、总优惠金额
        ProcurementApplyOrderVo check = new ProcurementApplyOrderVo(0, 0, 0.0, 0.0);
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            //判断参数
            if (orderGoodsSkuVo.getType() == null || orderGoodsSkuVo.getGoodsSkuId() == null || orderGoodsSkuVo.getQuantity() == null || orderGoodsSkuVo.getMoney() == null || orderGoodsSkuVo.getDiscountMoney() == null) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }

            //统计总商品数量、总商品金额、总优惠金额
            if (orderGoodsSkuVo.getType() == 0) {
                check.setOutTotalQuantity(check.getOutTotalQuantity() + orderGoodsSkuVo.getQuantity());
                check.setTotalMoney(check.getTotalMoney() - orderGoodsSkuVo.getMoney());
                check.setTotalDiscountMoney(check.getTotalDiscountMoney() - orderGoodsSkuVo.getDiscountMoney());
            } else if (orderGoodsSkuVo.getType() == 1) {
                check.setInTotalQuantity(check.getInTotalQuantity() + orderGoodsSkuVo.getQuantity());
                check.setTotalMoney(check.getTotalMoney() + orderGoodsSkuVo.getMoney());
                check.setTotalDiscountMoney(check.getTotalDiscountMoney() + orderGoodsSkuVo.getDiscountMoney());
            } else {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }
        });
        int inTotalQuantity = procurementApplyOrderVo.getInTotalQuantity() == null ? 0 : procurementApplyOrderVo.getInTotalQuantity();
        int outTotalQuantity = procurementApplyOrderVo.getOutTotalQuantity() == null ? 0 : procurementApplyOrderVo.getOutTotalQuantity();
        if (check.getInTotalQuantity() != inTotalQuantity || check.getOutTotalQuantity() != outTotalQuantity ||
                check.getTotalMoney().doubleValue() != procurementApplyOrderVo.getTotalMoney().doubleValue() || check.getTotalDiscountMoney().doubleValue() != procurementApplyOrderVo.getTotalDiscountMoney().doubleValue() ||
                procurementApplyOrderVo.getOrderMoney() + procurementApplyOrderVo.getTotalDiscountMoney() != procurementApplyOrderVo.getTotalMoney()) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //判断新增类型
        switch (type) {
            case 1:     //采购订单
                cgdd(procurementApplyOrderVo);
                break;

            case 2:     //采购退货申请单
                cgthsqd(procurementApplyOrderVo);
                break;

            case 3:     //采购换货申请单
                cghhsqd(procurementApplyOrderVo);
                break;

            default:
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //新增申请单
        if (myProcurementMapper.addApplyOrder(procurementApplyOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }

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
            //重置库存
            backMethod(procurementApplyOrderVo);

            //删除采购申请订单
            if (myProcurementMapper.deleteApplyOrder(procurementApplyOrderVo) != 1) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
        });
        return CommonResponse.success();
    }

    /**
     * 重置库存
     * @param procurementApplyOrderVo
     */
    @Transactional
    public ProcurementApplyOrderVo backMethod(ProcurementApplyOrderVo procurementApplyOrderVo) {
        Integer storeId = procurementApplyOrderVo.getStoreId();

        //获取采购申请订单
        ProcurementApplyOrderVo oldVo =  myProcurementMapper.findApplyOrderDetailById(procurementApplyOrderVo);
        if (oldVo == null || oldVo.getDetails().size() == 0) {
            throw new CommonException(CommonResponse.DELETE_ERROR);
        }

        //判断单据状态
        Byte orderStatus = oldVo.getOrderStatus();
        if (orderStatus != 1 && orderStatus != 4 && orderStatus != 7) {     //1：未收，4：未发，7：未收未发
            throw new CommonException(CommonResponse.STATUS_ERROR);
        }

        //判断结算状态
        Byte clearStatus = oldVo.getClearStatus();
        if (orderStatus != 7 && clearStatus != 0) {     //0：未完成
            throw new CommonException(CommonResponse.STATUS_ERROR);
        }

        //修改商品规格可操作数量
        if (oldVo.getType() == 2 || oldVo.getType() == 3) {
            List<OrderGoodsSkuVo> applyOrderGoodsSkuVos = oldVo.getDetails().stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("0")).collect(Collectors.toList());        //采购退换申请单或采购换货申请单中出库的的商品规格
            ProcurementResultOrderVo resultOrderVo = myProcurementMapper.findResultOrderDetailById(new ProcurementResultOrderVo(storeId, oldVo.getResultOrderId()));
            List<OrderGoodsSkuVo> resultOrderGoodsSkuVos = resultOrderVo.getDetails();      //采购入库单的商品规格
            resultOrderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
                Optional<OrderGoodsSkuVo> optional = applyOrderGoodsSkuVos.stream().filter(vo -> vo.getGoodsSkuId().equals(orderGoodsSkuVo.getGoodsSkuId())).findFirst();
                if (optional.isPresent()) {
                    int changeQuantity = optional.get().getQuantity();
                    orderGoodsSkuVo.setStoreId(storeId);
                    orderGoodsSkuVo.setOperatedQuantity(-changeQuantity);
                    if (myOrderGoodsSkuMapper.updateOperatedQuantity(orderGoodsSkuVo) != 1) {
                        throw new CommonException(CommonResponse.DELETE_ERROR);
                    }
                }
            });
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

        //删除采购申请订单/商品规格关系
        myOrderGoodsSkuMapper.deleteOrderGoodsSku(new OrderGoodsSkuVo(storeId, oldVo.getId()));

        return oldVo;
    }

    /**
     * 修改采购申请订单
     * @param procurementApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse updateApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //判断参数
        if (procurementApplyOrderVo.getStoreId() == null || procurementApplyOrderVo.getId() == null ||
                procurementApplyOrderVo.getDetails() == null || procurementApplyOrderVo.getDetails().size() == 0 ||
                procurementApplyOrderVo.getTotalMoney() == null || procurementApplyOrderVo.getTotalDiscountMoney() == null || procurementApplyOrderVo.getOrderMoney() == null ||
                procurementApplyOrderVo.getInTotalQuantity() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //重置库存
        ProcurementApplyOrderVo oldVo = backMethod(procurementApplyOrderVo);

        //修改采购申请订单
        if (myProcurementMapper.updateApplyOrder(procurementApplyOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //商品规格相关操作
        inventoryMethod(procurementApplyOrderVo, oldVo);

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
        titles.add(new Title("来源订单", "resultOrderId"));
        titles.add(new Title("供应商", "supplierName"));
        titles.add(new Title("入库仓库", "inWarehouseName"));
        titles.add(new Title("总收货数量", "inTotalQuantity"));
        titles.add(new Title("已收货数量", "inReceivedQuantity"));
        titles.add(new Title("未收货数量", "inNotReceivedQuantity"));
        titles.add(new Title("出库仓库", "outWarehouseName"));
        titles.add(new Title("总发货数量", "outTotalQuantity"));
        titles.add(new Title("已发货数量", "outSentQuantity"));
        titles.add(new Title("未发货数量数量", "outNotSentQuantity"));
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
     * 根据单据编号查询申请订单详情
     * @param procurementApplyOrderVo
     * @return
     */
    public CommonResponse findApplyOrderDetailById(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //获取参数
        Integer storeId = procurementApplyOrderVo.getStoreId();

        //根据单据编号查询单据详情
        procurementApplyOrderVo = myProcurementMapper.findApplyOrderDetailById(procurementApplyOrderVo);
        if (procurementApplyOrderVo == null || procurementApplyOrderVo.getDetails().size() == 0) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }

        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(storeId));
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
        Integer inWarehouseId = procurementResultOrderVo.getProcurementApplyOrderVo().getInWarehouseId();
        for (OrderGoodsSkuVo inVo : inVos) {
            //已经退换货的结果单不能红冲
            if (inVo.getOperatedQuantity() > 0) {
                throw new CommonException(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
            }

            Integer goodsSkuId = inVo.getGoodsSkuId();
            Integer quantity = inVo.getQuantity();

            if (applyOrderVo.getType() == 1) {
                //减少实物库存、可用库存、账面库存
                inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, inWarehouseId, goodsSkuId, quantity, quantity, quantity));

                //增加待收货数量
                inventoryUtil.updateNotQuantityMethod(1, new WarehouseGoodsSkuVo(storeId, inWarehouseId, goodsSkuId, 0, quantity));
            } else if (applyOrderVo.getType() == 3) {
                //减少实物库存、可用库存、账面库存
                inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, inWarehouseId, goodsSkuId, quantity, quantity, quantity));

                //增加待收货数量
                inventoryUtil.updateNotQuantityMethod(1, new WarehouseGoodsSkuVo(storeId, inWarehouseId, goodsSkuId, 0, quantity));
            }

            //红冲库存记账记录
            inventoryUtil.redDashedStorageCheckOrderMethod(1, new StorageCheckOrderVo(storeId, oldResultOrderId, goodsSkuId, inWarehouseId, userId));

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

        List<OrderGoodsSkuVo> outVos = orderGoodsSkuVos.stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("0")).collect(Collectors.toList());
        List<OrderGoodsSkuVo> applyOrderOutVos = applyOrderDetails.stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("0")).collect(Collectors.toList());
        Integer outWarehouseId = procurementResultOrderVo.getProcurementApplyOrderVo().getOutWarehouseId();
        for (OrderGoodsSkuVo outVo : outVos) {
            //已经退换货的结果单不能红冲
            if (outVo.getOperatedQuantity() > 0) {
                throw new CommonException(CommonResponse.STATUS_ERROR);
            }

            Integer goodsSkuId = outVo.getGoodsSkuId();
            Integer quantity = outVo.getQuantity();

            if (applyOrderVo.getType() == 2) {
                //增加实物库存、账面库存
                inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, outWarehouseId, goodsSkuId, quantity, 0, quantity));

                //增加待发货数量
                inventoryUtil.updateNotQuantityMethod(1, new WarehouseGoodsSkuVo(storeId, outWarehouseId, goodsSkuId, quantity, 0));
            } else if (applyOrderVo.getType() == 3) {
                //增加实物库存、账面库存
                inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, outWarehouseId, goodsSkuId, quantity, 0, quantity));

                //增加待发货数量
                inventoryUtil.updateNotQuantityMethod(1, new WarehouseGoodsSkuVo(storeId, outWarehouseId, goodsSkuId, quantity, 0));
            }

            //红冲库存记账记录
            inventoryUtil.redDashedStorageCheckOrderMethod(0, new StorageCheckOrderVo(storeId, oldResultOrderId, goodsSkuId, outWarehouseId, userId));

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

        //红冲往来对账记录
        fundUtil.redDashedFundTargetCheckOrderMethod(new FundTargetCheckOrderVo(storeId, oldResultOrderId, userId));

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
                if (vo.getProcurementApplyOrderVo().getInWarehouseId() != null && vo.getProcurementApplyOrderVo().getInWarehouseId().intValue() == warehouse.getId()) {
                    vo.getProcurementApplyOrderVo().setInWarehouseName(warehouse.getName());
                }
                if (vo.getProcurementApplyOrderVo().getOutWarehouseId() != null && vo.getProcurementApplyOrderVo().getOutWarehouseId().intValue() == warehouse.getId()) {
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
        //获取参数
        Integer storeId = procurementResultOrderVo.getStoreId();

        //根据单据编号查询单据详情
        procurementResultOrderVo = myProcurementMapper.findResultOrderDetailById(procurementResultOrderVo);
        if (procurementResultOrderVo == null || procurementResultOrderVo.getDetails().size() == 0) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }

        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(storeId));
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
