package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyProcurementMapper;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private MyWarehouseMapper myWarehouseMapper;

    //采购申请订单

    /**
     * 新增采购申请订单
     * @param procurementApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo) {
        List<ProcurementApplyOrderGoodsSkuVo> paogsvs = procurementApplyOrderVo.getDetails();
        //判断是采购订单/采购退货申请/采购换货申请
        Byte type = procurementApplyOrderVo.getType();
        switch (type) {
            //采购订单
            case 1:
                //判断参数
                if (paogsvs.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                //设置初始属性
                procurementApplyOrderVo.setId("CGDD_" + UUID.randomUUID().toString());
                procurementApplyOrderVo.setOrderStatus((byte) 1);
                break;
            //采购退货申请
            case 2:
                //判断参数
                if (paogsvs.size() == 0 || procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                //设置初始属性
                procurementApplyOrderVo.setId("CGTHSQD_" + UUID.randomUUID().toString());
                procurementApplyOrderVo.setOrderStatus((byte) 3);
                break;
            //采购换货申请
            case 3:
                //判断参数
                if (paogsvs.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null ||
                        procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                //设置初始属性
                procurementApplyOrderVo.setId("CGHHSQD_" + UUID.randomUUID().toString());
                procurementApplyOrderVo.setOrderStatus((byte) 5);
            default:
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        procurementApplyOrderVo.setClearStatus((byte) 0);
        procurementApplyOrderVo.setClearMoney(new BigDecimal(0));
        //新增采购换货申请
        if (myProcurementMapper.addApplyOrder(procurementApplyOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //新增采购申请订单/商品规格关系
        paogsvs.stream().forEach(paogsv -> {
            //判断参数
            if (paogsv.getType() == null || paogsv.getGoodsSkuId() == null || paogsv.getQuantity() == null || paogsv.getMoney() == null || paogsv.getDiscountMoney() == null) {
                throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
            }
            //设置初始属性
            paogsv.setStoreId(procurementApplyOrderVo.getStoreId());
            paogsv.setApplyOrderId(procurementApplyOrderVo.getId());
            //新增
            if (myProcurementMapper.addApplyOrderGoodsSku(paogsv) != 1) {
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
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
            ProcurementApplyOrder pao = myProcurementMapper.findApplyOrderById(procurementApplyOrderVo);
            if (pao == null) {
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
            //判断单据状态
            Byte orderStatus = pao.getOrderStatus();
            if (orderStatus != 1 && orderStatus != 3 && orderStatus != 5) {     //1：未收，3：未发，5：未收未发
                throw new CommonException(CommonResponse.CODE19, "【 " + pao.getId() + "】" + CommonResponse.MESSAGE19);
            }
            //判断结算状态
            Byte clearStatus = pao.getClearStatus();
            if (clearStatus != 0) {     //0：未完成
                throw new CommonException(CommonResponse.CODE19, "【 " + pao.getId() + "】" + CommonResponse.MESSAGE19);
            }
            //删除采购申请订单
            if (myProcurementMapper.deleteApplyOrder(procurementApplyOrderVo) != 1) {
                throw new CommonException(CommonResponse.CODE8, "【 " + pao.getId() + "】" + CommonResponse.MESSAGE8);
            }
            //删除采购申请订单/商品规格关系
            ProcurementApplyOrderGoodsSkuVo paogsv = new ProcurementApplyOrderGoodsSkuVo(procurementApplyOrderVo.getStoreId(), pao.getId());
            myProcurementMapper.deleteApplyOrderGoodsSku(paogsv);
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
        if (orderStatus != 1 && orderStatus != 3 && orderStatus != 5) {     //1：未收，3：未发，5：未收未发
            return new CommonResponse(CommonResponse.CODE19, null, "【 " + pao.getId() + "】" + CommonResponse.MESSAGE19);
        }
        //判断结算状态
        Byte clearStatus = pao.getClearStatus();
        if (clearStatus != 0) {     //0：未完成
            return new CommonResponse(CommonResponse.CODE19, null, "【 " + pao.getId() + "】" + CommonResponse.MESSAGE19);
        }
        List<ProcurementApplyOrderGoodsSkuVo> paogsvs = procurementApplyOrderVo.getDetails();
        //判断是采购订单/采购退货申请/采购换货申请
        Byte type = pao.getType();
        switch (type) {
            //采购订单
            case 1:
                //判断参数
                if (paogsvs.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                break;
            //采购退货申请
            case 2:
                //判断参数
                if (paogsvs.size() == 0 || procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                break;
            //采购换货申请
            case 3:
                //判断参数
                if (paogsvs.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null ||
                        procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null) {
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
            default:
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改采购换货申请
        if (myProcurementMapper.updateApplyOrder(procurementApplyOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        //删除采购申请订单/商品规格关系
        myProcurementMapper.deleteApplyOrderGoodsSku(new ProcurementApplyOrderGoodsSkuVo(procurementApplyOrderVo.getStoreId(), procurementApplyOrderVo.getId()));
        //新增采购申请订单/商品规格关系
        paogsvs.stream().forEach(paogsv -> {
            //判断参数
            if (paogsv.getType() == null || paogsv.getGoodsSkuId() == null || paogsv.getQuantity() == null || paogsv.getMoney() == null || paogsv.getDiscountMoney() == null) {
                throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
            }
            //设置初始属性
            paogsv.setStoreId(procurementApplyOrderVo.getStoreId());
            paogsv.setApplyOrderId(procurementApplyOrderVo.getId());
            //新增
            if (myProcurementMapper.addApplyOrderGoodsSku(paogsv) != 1) {
                throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
            }
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    //收货/发货

    /**
     * 修改采购申请订单单据状态
     * @param procurementApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse updateApplyOrderOrderStatus(ProcurementApplyOrderVo procurementApplyOrderVo){
        Integer storeId = procurementApplyOrderVo.getStoreId();
        Byte option = procurementApplyOrderVo.getType();
        //获取采购申请订单
        ProcurementApplyOrder procurementApplyOrder = myProcurementMapper.findApplyOrderById(procurementApplyOrderVo);
        if (procurementApplyOrder == null) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        Byte type = procurementApplyOrder.getType();
        Byte orderStatus = procurementApplyOrder.getOrderStatus();
        Byte status = -1;
        //判断收货/发货
        switch (option) {
            //收货
            case 1:
                //判断单据类型和单据状态
                if (type == 1 && orderStatus == 1) {        //是采购订单且单据状态为未收
                    status = 2;     //修改单据状态为【已收】
                } else if (type == 3 && (orderStatus == 5 || orderStatus == 6)) {     //是采购换货申请且单据状态为未收未发或未收已发
                    if (orderStatus == 5) {     //未收未发
                        status = 7;     //修改单据状态为【已收未发】
                    } else {        //未收已发
                        status = 8;     //修改单据状态为【已收已发】
                    }
                }
                break;
            //发货
            case 2:
                //判断单据类型和单据状态
                if (type == 2 && orderStatus == 3) {        //是采购退货申请且单据状态为未发
                    status = 4;     //修改单据状态为【已发】
                } else if (type == 3 && (orderStatus == 5 || orderStatus == 7)) {     //是采购换货申请且单据状态为未收未发或已收未发
                    if (orderStatus == 5) {     //未收未发
                        status = 6;     //修改单据状态为【未收已发】
                    } else {        //已收未发
                        status = 8;     //修改单据状态为【已收已发】
                    }
                }
                break;
            default:
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //新增采购结果单
        ProcurementResultOrderVo procurementResultOrderVo = new ProcurementResultOrderVo(storeId,
                new Date(),
                procurementApplyOrder.getId(),
                procurementApplyOrder.getClearStatus(),
                procurementApplyOrder.getSupplierId(),
                procurementApplyOrder.getInWarehouseId(),
                procurementApplyOrder.getOutWarehouseId(),
                (procurementApplyOrder.getInTotalQuantity() == null ? 0 : procurementApplyOrder.getInTotalQuantity()) - (procurementApplyOrder.getOutTotalQuantity() == null ? 0 : procurementApplyOrder.getOutTotalQuantity()),
                procurementApplyOrder.getTotalMoney(),
                procurementApplyOrder.getTotalDiscountMoney(),
                procurementApplyOrder.getClearMoney(),
                procurementApplyOrder.getUserId(),
                procurementApplyOrder.getRemark());
        if (status == 2) {      //新增采购入库单
            procurementResultOrderVo.setId("CGRKD_" + UUID.randomUUID().toString());
            procurementResultOrderVo.setType((byte) 1);
        } else if (status == 4) {       //新增采购退货单
            procurementResultOrderVo.setId("CGTHD_" + UUID.randomUUID().toString());
            procurementResultOrderVo.setType((byte) 2);
        } else if (status == 8) {       //新增采换货单
            procurementResultOrderVo.setId("CGHHD_" + UUID.randomUUID().toString());
            procurementResultOrderVo.setType((byte) 3);
        } else {
            return new CommonResponse(CommonResponse.CODE19, null, CommonResponse.MESSAGE19);
        }
        if (myProcurementMapper.addResultOrder(procurementResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //修改单据状态
        if (myProcurementMapper.updateApplyOrderOrderStatus(new ProcurementApplyOrderVo(storeId, procurementApplyOrder.getId(), status)) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    //收款/付款

    /**
     * 修改采购申请订单结算状态
     * @param procurementApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse updateApplyOrderCheckStatus(ProcurementApplyOrderVo procurementApplyOrderVo) {
        Integer storeId = procurementApplyOrderVo.getStoreId();
        Byte option = procurementApplyOrderVo.getType();
        //获取采购申请订单
        ProcurementApplyOrder procurementApplyOrder = myProcurementMapper.findApplyOrderById(procurementApplyOrderVo);
        if (procurementApplyOrder == null) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        Byte type = procurementApplyOrder.getType();
        Byte clearStatus = procurementApplyOrder.getClearStatus();
        Byte status = -1;
        //判断收款/付款
        switch (option) {
            //收款
            case 1:

                break;
            //付款
            case 2:

                break;
            default:
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
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
        titles.add(new Title("结算状态", "clearStatus"));
        titles.add(new Title("供应商", "supplierName"));
        titles.add(new Title("入库仓库", "inWarehouseName"));
        titles.add(new Title("入库总商品数量", "inTotalQuantity"));
        titles.add(new Title("出库仓库", "outWarehouseName"));
        titles.add(new Title("出库总商品数量", "outTotalQuantity"));
        titles.add(new Title("总订单金额", "totalMoney"));
        titles.add(new Title("总优惠金额", "totalDiscountMoney"));
        titles.add(new Title("已结金额", "clearMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, procurementApplyOrderVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据单据编号查询单据详情
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
}
