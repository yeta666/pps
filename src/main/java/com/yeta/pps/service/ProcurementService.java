package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyOrderGoodsSkuMapper;
import com.yeta.pps.mapper.MyProcurementMapper;
import com.yeta.pps.mapper.MyWarehouseMapper;
import com.yeta.pps.po.ProcurementApplyOrder;
import com.yeta.pps.po.Warehouse;
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
 * 采购相关逻辑处理
 * @author YETA
 * @date 2018/12/10/15:04
 */
@Service
public class ProcurementService {

    private static final Logger LOG = LoggerFactory.getLogger(ProcurementService.class);

    @Autowired
    private MyProcurementMapper myProcurementMapper;

    @Autowired
    private MyOrderGoodsSkuMapper myOrderGoodsSkuMapper;

    @Autowired
    private MyWarehouseMapper myWarehouseMapper;

    //采购申请订单

    /**
     * 新增采购申请订单
     * @param procurementApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo) {
        List<OrderGoodsSkuVo> orderGoodsSkuVos = procurementApplyOrderVo.getDetails();
        //判断是采购订单/采购退货申请单/采购换货申请单
        Byte type = procurementApplyOrderVo.getType();
        switch (type) {
            //采购订单
            case 1:
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                //设置初始属性
                procurementApplyOrderVo.setId("CGDD_" + UUID.randomUUID().toString());
                procurementApplyOrderVo.setOrderStatus((byte) 1);       //未收
                procurementApplyOrderVo.setInReceivedQuantity(0);
                procurementApplyOrderVo.setInNotReceivedQuantity(procurementApplyOrderVo.getInTotalQuantity());
                break;
            //采购退货申请单
            case 2:
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null ||
                        procurementApplyOrderVo.getResultOrderId() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                //设置初始属性
                procurementApplyOrderVo.setId("CGTHSQD_" + UUID.randomUUID().toString());
                procurementApplyOrderVo.setOrderStatus((byte) 4);       //未发
                procurementApplyOrderVo.setOutSentQuantity(0);
                procurementApplyOrderVo.setOutNotSentQuantity(procurementApplyOrderVo.getOutTotalQuantity());
                break;
            //采购换货申请单
            case 3:
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null ||
                        procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null ||
                        procurementApplyOrderVo.getResultOrderId() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                //设置初始属性
                procurementApplyOrderVo.setId("CGHHSQD_" + UUID.randomUUID().toString());
                procurementApplyOrderVo.setOrderStatus((byte) 7);       //未收未发
                procurementApplyOrderVo.setInReceivedQuantity(0);
                procurementApplyOrderVo.setInNotReceivedQuantity(procurementApplyOrderVo.getInTotalQuantity());
                procurementApplyOrderVo.setOutSentQuantity(0);
                procurementApplyOrderVo.setOutNotSentQuantity(procurementApplyOrderVo.getOutTotalQuantity());
                break;
            default:
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        procurementApplyOrderVo.setCreateTime(new Date());
        procurementApplyOrderVo.setClearStatus((byte) 0);
        procurementApplyOrderVo.setClearedMoney(new BigDecimal(0));
        procurementApplyOrderVo.setNotClearedMoney(procurementApplyOrderVo.getOrderMoney());
        //新增采购申请单
        if (myProcurementMapper.addApplyOrder(procurementApplyOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //新增采购申请单/商品规格关系
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            //判断参数
            if (orderGoodsSkuVo.getType() == null || orderGoodsSkuVo.getGoodsSkuId() == null || orderGoodsSkuVo.getQuantity() == null || orderGoodsSkuVo.getMoney() == null || orderGoodsSkuVo.getDiscountMoney() == null) {
                throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
            }
            //设置初始属性
            orderGoodsSkuVo.setStoreId(procurementApplyOrderVo.getStoreId());
            orderGoodsSkuVo.setOrderId(procurementApplyOrderVo.getId());
            orderGoodsSkuVo.setFinishQuantity(0);
            orderGoodsSkuVo.setNotFinishQuantity(orderGoodsSkuVo.getQuantity());
            //新增
            if (myOrderGoodsSkuMapper.addOrderGoodsSku(orderGoodsSkuVo) != 1) {
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }
            //修改采购入库单对应的商品规格已操作数量
            if (type == 2 || (type == 3 && orderGoodsSkuVo.getType() == 0)) {
                if (orderGoodsSkuVo.getId() == null) {
                    throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
                }
                if (myOrderGoodsSkuMapper.updateOperatedQuantity(new OrderGoodsSkuVo(procurementApplyOrderVo.getStoreId(), orderGoodsSkuVo.getId(), orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getQuantity())) != 1) {
                    throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
                }
            }
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
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
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
            //判断单据状态
            Byte orderStatus = applyOrderVo.getOrderStatus();
            if (orderStatus != 1 && orderStatus != 4 && orderStatus != 7) {     //1：未收，4：未发，7：未收未发
                throw new CommonException(CommonResponse.CODE19, "【 " + applyOrderVo.getId() + "】" + CommonResponse.MESSAGE19);
            }
            //判断结算状态
            Byte clearStatus = applyOrderVo.getClearStatus();
            if (clearStatus != 0) {     //0：未完成
                throw new CommonException(CommonResponse.CODE19, "【 " + applyOrderVo.getId() + "】" + CommonResponse.MESSAGE19);
            }
            //修改商品规格可操作数量
            if (applyOrderVo.getType() == 2 || applyOrderVo.getType() == 3) {
                List<OrderGoodsSkuVo> applyOrderGoodsSkuVos = applyOrderVo.getDetails();
                ProcurementResultOrderVo resultOrderVo = myProcurementMapper.findResultOrderDetailById(new ProcurementResultOrderVo(procurementApplyOrderVo.getStoreId(), applyOrderVo.getResultOrderId()));
                List<OrderGoodsSkuVo> resultOrderGoodsSkuVos = resultOrderVo.getDetails();
                resultOrderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
                    Optional<OrderGoodsSkuVo> optional = applyOrderGoodsSkuVos.stream().filter(vo -> vo.getGoodsSkuId().equals(orderGoodsSkuVo.getGoodsSkuId())).findFirst();
                    if (optional.isPresent()) {
                        int changeQuantity = optional.get().getQuantity();
                        orderGoodsSkuVo.setStoreId(procurementApplyOrderVo.getStoreId());
                        orderGoodsSkuVo.setOperatedQuantity(-changeQuantity);
                        if (myOrderGoodsSkuMapper.updateOperatedQuantity(orderGoodsSkuVo) != 1) {
                            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
                        }
                    }
                });
            }
            //删除采购申请订单
            if (myProcurementMapper.deleteApplyOrder(procurementApplyOrderVo) != 1) {
                throw new CommonException(CommonResponse.CODE8, "【 " + applyOrderVo.getId() + "】" + CommonResponse.MESSAGE8);
            }
            //删除采购申请订单/商品规格关系
            OrderGoodsSkuVo orderGoodsSkuVo = new OrderGoodsSkuVo(procurementApplyOrderVo.getStoreId(), applyOrderVo.getId());
            myOrderGoodsSkuMapper.deleteOrderGoodsSku(orderGoodsSkuVo);
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
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
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        //判断单据状态
        Byte orderStatus = pao.getOrderStatus();
        if (orderStatus != 1 && orderStatus != 4 && orderStatus != 7) {     //1：未收，4：未发，7：未收未发
            return new CommonResponse(CommonResponse.CODE19, null, "【 " + pao.getId() + "】" + CommonResponse.MESSAGE19);
        }
        //判断结算状态
        Byte clearStatus = pao.getClearStatus();
        if (clearStatus != 0) {     //0：未完成
            return new CommonResponse(CommonResponse.CODE19, null, "【 " + pao.getId() + "】" + CommonResponse.MESSAGE19);
        }
        List<OrderGoodsSkuVo> orderGoodsSkuVos = procurementApplyOrderVo.getDetails();
        //判断是采购订单/采购退货申请/采购换货申请
        Byte type = pao.getType();
        switch (type) {
            //采购订单
            case 1:
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                break;
            //采购退货申请
            case 2:
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                break;
            //采购换货申请
            case 3:
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null ||
                        procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
            default:
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改采购申请订单
        if (myProcurementMapper.updateApplyOrder(procurementApplyOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        //删除采购申请订单/商品规格关系
        myOrderGoodsSkuMapper.deleteOrderGoodsSku(new OrderGoodsSkuVo(procurementApplyOrderVo.getStoreId(), procurementApplyOrderVo.getId()));
        //新增采购申请订单/商品规格关系
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            //判断参数
            if (orderGoodsSkuVo.getType() == null || orderGoodsSkuVo.getGoodsSkuId() == null || orderGoodsSkuVo.getQuantity() == null || orderGoodsSkuVo.getMoney() == null || orderGoodsSkuVo.getDiscountMoney() == null) {
                throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
            }
            //设置初始属性
            orderGoodsSkuVo.setStoreId(procurementApplyOrderVo.getStoreId());
            orderGoodsSkuVo.setOrderId(procurementApplyOrderVo.getId());
            orderGoodsSkuVo.setFinishQuantity(0);
            orderGoodsSkuVo.setNotFinishQuantity(orderGoodsSkuVo.getQuantity());
            //新增
            if (myOrderGoodsSkuMapper.addOrderGoodsSku(orderGoodsSkuVo) != 1) {
                throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
            }
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改采购申请订单备注
     * @param procurementApplyOrderVo
     * @return
     */
    public CommonResponse updateApplyOrderRemark(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //修改备注
        if (myProcurementMapper.updateApplyOrderRemark(procurementApplyOrderVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
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
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据单据编号查询申请订单详情
     * @param procurementApplyOrderVo
     * @return
     */
    public CommonResponse findApplyOrderDetailById(ProcurementApplyOrderVo procurementApplyOrderVo) {
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(procurementApplyOrderVo.getStoreId()));
        //根据单据编号查询单据详情
        procurementApplyOrderVo = myProcurementMapper.findApplyOrderDetailById(procurementApplyOrderVo);
        if (procurementApplyOrderVo == null || procurementApplyOrderVo.getDetails().size() == 0) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        //补上仓库名
        for (Warehouse warehouse : warehouses) {
            if (procurementApplyOrderVo.getInWarehouseId() == warehouse.getId()) {
                procurementApplyOrderVo.setInWarehouseName(warehouse.getName());
            }
            if (procurementApplyOrderVo.getOutWarehouseId() == warehouse.getId()) {
                procurementApplyOrderVo.setOutWarehouseName(warehouse.getName());
            }
        }
        return new CommonResponse(CommonResponse.CODE1, procurementApplyOrderVo, CommonResponse.MESSAGE1);
    }

    //采购结果订单

    /**
     * 红冲采购结果订单
     * @param procurementResultOrderVo
     * @return
     */
    @Transactional
    public CommonResponse updateResultOrder(ProcurementResultOrderVo procurementResultOrderVo) {
        //判断参数
        if (procurementResultOrderVo.getId() == null || procurementResultOrderVo.getUserId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        Integer storeId = procurementResultOrderVo.getStoreId();
        //修改单据状态
        if (myProcurementMapper.updateResultOrder(procurementResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        procurementResultOrderVo = myProcurementMapper.findResultOrderById(procurementResultOrderVo);
        //新增红冲单
        procurementResultOrderVo.setStoreId(storeId);
        procurementResultOrderVo.setId("HC_" + procurementResultOrderVo.getId());
        procurementResultOrderVo.setCreateTime(new Date());
        procurementResultOrderVo.setOrderStatus((byte) -2);
        procurementResultOrderVo.setTotalQuantity(-procurementResultOrderVo.getTotalQuantity());
        procurementResultOrderVo.setTotalMoney(new BigDecimal(-procurementResultOrderVo.getTotalMoney().doubleValue()));
        procurementResultOrderVo.setTotalDiscountMoney(new BigDecimal(-procurementResultOrderVo.getTotalDiscountMoney().doubleValue()));
        procurementResultOrderVo.setOrderMoney(new BigDecimal(-procurementResultOrderVo.getOrderMoney().doubleValue()));
        if (myProcurementMapper.addResultOrder(procurementResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
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
        titles.add(new Title("单据状态", "orderStatus"));
        titles.add(new Title("来源订单", "applyOrderId"));
        titles.add(new Title("来源订单单据状态", "procurementApplyOrderVo.orderStatus"));
        titles.add(new Title("来源订单结算状态", "procurementApplyOrderVo.clearStatus"));
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
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据单据编号查询结果订单详情
     * @param procurementResultOrderVo
     * @return
     */
    public CommonResponse findResultOrderDetailById(ProcurementResultOrderVo procurementResultOrderVo) {
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(procurementResultOrderVo.getStoreId()));
        //根据单据编号查询单据详情
        procurementResultOrderVo = myProcurementMapper.findResultOrderDetailById(procurementResultOrderVo);
        if (procurementResultOrderVo == null || procurementResultOrderVo.getDetails().size() == 0) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        //补上仓库名
        for (Warehouse warehouse : warehouses) {
            if (procurementResultOrderVo.getProcurementApplyOrderVo().getInWarehouseId() == warehouse.getId()) {
                procurementResultOrderVo.getProcurementApplyOrderVo().setInWarehouseName(warehouse.getName());
            }
            if (procurementResultOrderVo.getProcurementApplyOrderVo().getOutWarehouseId() == warehouse.getId()) {
                procurementResultOrderVo.getProcurementApplyOrderVo().setOutWarehouseName(warehouse.getName());
            }
        }
        return new CommonResponse(CommonResponse.CODE1, procurementResultOrderVo, CommonResponse.MESSAGE1);
    }
}
