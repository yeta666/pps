package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyOrderGoodsSkuMapper;
import com.yeta.pps.mapper.MyProcurementMapper;
import com.yeta.pps.mapper.MyWarehouseMapper;
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

    @Autowired
    private FinancialAffairsUtil financialAffairsUtil;

    @Autowired
    private PrimaryKeyUtil primaryKeyUtil;

    //采购申请单

    /**
     * 采购订单
     * @param procurementApplyOrderVo
     */
    @Transactional
    public void cgdd(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //获取参数
        List<OrderGoodsSkuVo> orderGoodsSkuVos = procurementApplyOrderVo.getDetails();

        //判断参数
        if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //设置初始属性
        procurementApplyOrderVo.setId(primaryKeyUtil.getOrderPrimaryKeyMethod(myProcurementMapper.findApplyOrderPrimaryKey(procurementApplyOrderVo), "CGDD"));
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

    /**
     * 采购退货申请单
     * @param procurementApplyOrderVo
     */
    @Transactional
    public void cgthsqd(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //获取参数
        List<OrderGoodsSkuVo> orderGoodsSkuVos = procurementApplyOrderVo.getDetails();

        //判断参数
        if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //已红冲的采购入库单不能退换货
        if (procurementApplyOrderVo.getResultOrderId() != null) {
            ProcurementResultOrderVo prVo = myProcurementMapper.findResultOrderDetailById(new ProcurementResultOrderVo(procurementApplyOrderVo.getStoreId(), procurementApplyOrderVo.getResultOrderId()));
            if (prVo == null || prVo.getOrderStatus() < 0) {
                throw new CommonException(CommonResponse.ADD_ERROR, CommonResponse.STATUS_ERROR);
            }
        }

        //设置初始属性
        procurementApplyOrderVo.setId(primaryKeyUtil.getOrderPrimaryKeyMethod(myProcurementMapper.findApplyOrderPrimaryKey(procurementApplyOrderVo), "CGTHSQD"));
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

    /**
     * 采购换货申请单
     * @param procurementApplyOrderVo
     */
    @Transactional
    public void cghhsqd(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //获取参数
        List<OrderGoodsSkuVo> orderGoodsSkuVos = procurementApplyOrderVo.getDetails();

        //判断参数
        if (orderGoodsSkuVos.size() == 0 || procurementApplyOrderVo.getInWarehouseId() == null || procurementApplyOrderVo.getInTotalQuantity() == null ||
                procurementApplyOrderVo.getOutWarehouseId() == null || procurementApplyOrderVo.getOutTotalQuantity() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //已红冲的采购入库单不能退换货
        if (procurementApplyOrderVo.getResultOrderId() != null) {
            ProcurementResultOrderVo prVo = myProcurementMapper.findResultOrderDetailById(new ProcurementResultOrderVo(procurementApplyOrderVo.getStoreId(), procurementApplyOrderVo.getResultOrderId()));
            if (prVo == null || prVo.getOrderStatus() < 0) {
                throw new CommonException(CommonResponse.ADD_ERROR, CommonResponse.STATUS_ERROR);
            }
        }

        //设置初始属性
        procurementApplyOrderVo.setId(primaryKeyUtil.getOrderPrimaryKeyMethod(myProcurementMapper.findApplyOrderPrimaryKey(procurementApplyOrderVo), "CGHHSQD"));
        procurementApplyOrderVo.setCreateTime(new Date());
        procurementApplyOrderVo.setOrderStatus((byte) 7);       //未收未发
        procurementApplyOrderVo.setClearStatus(procurementApplyOrderVo.getOrderMoney() == 0 ? (byte) 1 : (byte) 0);
        procurementApplyOrderVo.setInReceivedQuantity(0);
        procurementApplyOrderVo.setInNotReceivedQuantity(procurementApplyOrderVo.getInTotalQuantity());
        procurementApplyOrderVo.setOutSentQuantity(0);
        procurementApplyOrderVo.setOutNotSentQuantity(procurementApplyOrderVo.getOutTotalQuantity());
        procurementApplyOrderVo.setClearedMoney(0.0);
        procurementApplyOrderVo.setNotClearedMoney(procurementApplyOrderVo.getOrderMoney());

        //商品规格相关操作
        inventoryMethod(procurementApplyOrderVo, null);
    }

    /**
     * 库存相关操作共用方法
     * @param paoVo
     * @param oldVo
     */
    @Transactional
    public void inventoryMethod(ProcurementApplyOrderVo paoVo, ProcurementApplyOrderVo oldVo) {
        List<OrderGoodsSkuVo> orderGoodsSkuVos = paoVo.getDetails();
        Integer storeId = paoVo.getStoreId();
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {

            Integer quantity = orderGoodsSkuVo.getQuantity();
            Integer goodsSkuId = orderGoodsSkuVo.getGoodsSkuId();

            //设置初始属性
            orderGoodsSkuVo.setStoreId(storeId);
            orderGoodsSkuVo.setOrderId(paoVo.getId());
            orderGoodsSkuVo.setFinishQuantity(0);
            orderGoodsSkuVo.setNotFinishQuantity(quantity);
            orderGoodsSkuVo.setOperatedQuantity(0);

            //修改库存相关
            Integer warehouseId = null;
            int notSentQuantity = 0;
            int notReceivedQuantity = 0;
            if (orderGoodsSkuVo.getType() == 1) {
                warehouseId = paoVo.getInWarehouseId() == null ? oldVo.getInWarehouseId() : paoVo.getInWarehouseId();
                notReceivedQuantity = quantity;
            } else if (orderGoodsSkuVo.getType() == 0) {
                warehouseId = paoVo.getOutWarehouseId() == null ? oldVo.getOutWarehouseId() : paoVo.getOutWarehouseId();
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
            if (paoVo.getResultOrderId() != null) {
                byte type = paoVo.getType() == null ? oldVo.getType() : paoVo.getType();
                if (type == 2 || (type == 3 && orderGoodsSkuVo.getType() == 0)) {
                    if (orderGoodsSkuVo.getId() == null) {
                        throw new CommonException(CommonResponse.PARAMETER_ERROR);
                    }
                    if (myOrderGoodsSkuMapper.updateOperatedQuantity(new OrderGoodsSkuVo(storeId, orderGoodsSkuVo.getId(), goodsSkuId, quantity)) != 1) {
                        throw new CommonException(CommonResponse.ADD_ERROR);
                    }
                }
            }
        });
    }

    /**
     * 新增采购申请单
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
                check.getTotalMoney().doubleValue() != procurementApplyOrderVo.getTotalMoney().doubleValue() ||
                check.getTotalDiscountMoney().doubleValue() != procurementApplyOrderVo.getTotalDiscountMoney().doubleValue() ||
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
     * 删除采购申请单
     * @param procurementApplyOrderVos
     * @return
     */
    @Transactional
    public CommonResponse deleteApplyOrder(List<ProcurementApplyOrderVo> procurementApplyOrderVos) {
        procurementApplyOrderVos.stream().forEach(procurementApplyOrderVo -> {
            //重置库存
            backMethod(procurementApplyOrderVo);

            //删除采购申请单
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

        //获取采购申请单
        List<ProcurementApplyOrderVo> oldVos =  myProcurementMapper.findAllApplyOrderDetail(procurementApplyOrderVo);
        if (oldVos.size() == 0 || oldVos.get(0).getDetails().size() == 0) {
            throw new CommonException(CommonResponse.DELETE_ERROR);
        }
        ProcurementApplyOrderVo oldVo = oldVos.get(0);


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
        if (oldVo.getResultOrderId() != null) {
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

        //删除采购申请单/商品规格关系
        myOrderGoodsSkuMapper.deleteOrderGoodsSku(new OrderGoodsSkuVo(storeId, oldVo.getId()));

        return oldVo;
    }

    /**
     * 修改采购申请单
     * @param procurementApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse updateApplyOrder(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //判断参数
        if (procurementApplyOrderVo.getStoreId() == null || procurementApplyOrderVo.getId() == null ||
                procurementApplyOrderVo.getDetails() == null || procurementApplyOrderVo.getDetails().size() == 0 ||
                procurementApplyOrderVo.getTotalMoney() == null || procurementApplyOrderVo.getTotalDiscountMoney() == null || procurementApplyOrderVo.getOrderMoney() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //重置库存
        ProcurementApplyOrderVo oldVo = backMethod(procurementApplyOrderVo);

        //修改采购申请单
        if (myProcurementMapper.updateApplyOrder(procurementApplyOrderVo) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //商品规格相关操作
        inventoryMethod(procurementApplyOrderVo, oldVo);

        return CommonResponse.success();
    }

    /**
     * 修改采购申请单备注
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
     * 根据type查询采购申请单
     * @param paoVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllApplyOrder(ProcurementApplyOrderVo paoVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myProcurementMapper.findCountApplyOrder(paoVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ProcurementApplyOrderVo> procurementApplyOrderVos = myProcurementMapper.findAllPagedApplyOrder(paoVo, pageVo);

        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(paoVo.getStoreId()));
        procurementApplyOrderVos.stream().forEach(vo -> {
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
        titles.add(new Title("单据状态", "orderStatus"));
        if (paoVo.getType() == 1 || paoVo.getType() == 3) {
            titles.add(new Title("入库仓库", "inWarehouseName"));
            titles.add(new Title("总收货数量", "inTotalQuantity"));
            titles.add(new Title("已收货数量", "inReceivedQuantity"));
            titles.add(new Title("未收货数量", "inNotReceivedQuantity"));
        }
        if (paoVo.getType() == 2 || paoVo.getType() == 3) {
            titles.add(new Title("来源订单", "resultOrderId"));
            titles.add(new Title("出库仓库", "outWarehouseName"));
            titles.add(new Title("总发货数量", "outTotalQuantity"));
            titles.add(new Title("已发货数量", "outSentQuantity"));
            titles.add(new Title("未发货数量", "outNotSentQuantity"));
        }
        titles.add(new Title("供应商编号", "supplierId"));
        titles.add(new Title("供应商名称", "supplierName"));
        titles.add(new Title("总商品金额", "totalMoney"));
        titles.add(new Title("总优惠金额", "totalDiscountMoney"));
        titles.add(new Title("本单金额", "orderMoney"));
        titles.add(new Title("结算状态", "clearStatus"));
        titles.add(new Title("已结金额", "clearedMoney"));
        titles.add(new Title("未结金额", "notClearedMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, procurementApplyOrderVos, pageVo);

        return CommonResponse.success(commonResult);
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 根据type导出采购申请单
     * @param paoVo
     * @param response
     * @return
     */
    public void exportApplyOrder(ProcurementApplyOrderVo paoVo, HttpServletResponse response) {
        try {
            //获取参数
            Byte type = paoVo.getType();

            //根据条件查询单据
            List<ProcurementApplyOrderVo> vos = myProcurementMapper.findAllApplyOrder(paoVo);

            //补上仓库名
            List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(paoVo.getStoreId()));
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
                    "\n单据编号：" + (paoVo.getId() == null ? "无" : paoVo.getId()) +
                    " 开始时间：" + (paoVo.getStartTime() == null ? "无" : sdf.format(paoVo.getStartTime())) +
                    " 结束时间：" + (paoVo.getEndTime() == null ? "无" : sdf.format(paoVo.getEndTime())) +
                    " 供应商名称：" + (paoVo.getSupplierName() == null ? "无" : paoVo.getSupplierName());

            //标题行
            List<String> titleRowCell1 = Arrays.asList(new String[]{
                    "单据编号", "单据类型", "单据日期", "单据状态", "入库仓库", "总收货数量", "已收货数量", "未收货数量", "供应商编号", "供应商名称", "总商品金额", "总优惠金额", "本单金额", "结算状态", "已结金额", "未结金额", "经手人", "备注"
            });

            List<String> titleRowCell2 = Arrays.asList(new String[]{
                    "单据编号", "单据类型", "单据日期", "单据状态", "来源订单", "出库仓库", "总发货数量", "已发货数量", "未发货数量", "供应商编号", "供应商名称", "总商品金额", "总优惠金额", "本单金额", "结算状态", "已结金额", "未结金额", "经手人", "备注"
            });

            List<String> titleRowCell3 = Arrays.asList(new String[]{
                    "单据编号", "单据类型", "单据日期", "单据状态", "来源订单", "入库仓库", "总收货数量", "已收货数量", "未收货数量", "出库仓库", "总发货数量", "已发货数量", "未发货数量", "供应商编号", "供应商名称", "总商品金额", "总优惠金额", "本单金额", "结算状态", "已结金额", "未结金额", "经手人", "备注"
            });

            //最后一个必填列列数
            int lastRequiredCol = -1;

            String typeName = type == 1 ? "采购订单" : type == 2 ? "采购退货申请单" : type == 3 ? "采购换货申请单" : null;

            //数据行
            List<List<String>> dataRowCells = new ArrayList<>();
            vos.stream().forEach(vo -> {
                List<String> dataRowCell = new ArrayList<>();
                dataRowCell.add(vo.getId());
                dataRowCell.add(typeName);
                dataRowCell.add(sdf.format(vo.getCreateTime()));
                dataRowCell.add(vo.getOrderStatus() == 1 ? "未收" : vo.getOrderStatus() == 2 ? "部分收" : vo.getOrderStatus() == 3 ? "已收" : vo.getOrderStatus() == 4 ? "未发" : vo.getOrderStatus() == 5 ? "部分发" : vo.getOrderStatus() == 6 ? "已发" : vo.getOrderStatus() == 7 ? "未收未发" : vo.getOrderStatus() == 8 ? "未收部分发" : vo.getOrderStatus() == 9 ? "未收已发" : vo.getOrderStatus() == 10 ? "部分收未发" : vo.getOrderStatus() == 11 ? "部分收部分发" : vo.getOrderStatus() == 12 ? "部分收已发" : vo.getOrderStatus() == 13 ? "已收未发" : vo.getOrderStatus() == 14 ? "已收部分发" : vo.getOrderStatus() == 15 ? "已收已发" : null);
                if (type == 1 || type == 3) {
                    dataRowCell.add(vo.getInWarehouseName());
                    dataRowCell.add(vo.getInTotalQuantity().toString());
                    dataRowCell.add(vo.getInReceivedQuantity().toString());
                    dataRowCell.add(vo.getInNotReceivedQuantity().toString());
                }
                if (type == 2 || type == 3) {
                    dataRowCell.add(vo.getResultOrderId());
                    dataRowCell.add(vo.getOutWarehouseName());
                    dataRowCell.add(vo.getOutTotalQuantity().toString());
                    dataRowCell.add(vo.getOutSentQuantity().toString());
                    dataRowCell.add(vo.getOutNotSentQuantity().toString());
                }
                dataRowCell.add(vo.getSupplierId());
                dataRowCell.add(vo.getSupplierName());
                dataRowCell.add(vo.getTotalMoney().toString());
                dataRowCell.add(vo.getTotalDiscountMoney().toString());
                dataRowCell.add(vo.getClearedMoney().toString());
                dataRowCell.add(vo.getClearStatus() == 0 ? "未完成" : vo.getClearStatus() == 1 ? "已完成" : null);
                dataRowCell.add(vo.getClearedMoney().toString());
                dataRowCell.add(vo.getNotClearedMoney().toString());
                dataRowCell.add(vo.getUserName());
                dataRowCell.add(vo.getRemark());
                dataRowCells.add(dataRowCell);
            });

            //输出excel
            String fileName = "【" + typeName + "导出】_" + System.currentTimeMillis() + ".xls";
            CommonUtil.outputExcelMethod(remark, type == 1 ? titleRowCell1 : type == 2 ? titleRowCell2 : titleRowCell3, lastRequiredCol, dataRowCells, fileName, response);
        } catch (Exception e) {
            throw new CommonException(CommonResponse.EXPORT_ERROR, e.getMessage());
        }
    }

    /**
     * 根据单据编号查询申请单详情
     * @param procurementApplyOrderVo
     * @return
     */
    public CommonResponse findApplyOrderDetailById(ProcurementApplyOrderVo procurementApplyOrderVo) {
        //获取参数
        Integer storeId = procurementApplyOrderVo.getStoreId();

        //根据单据编号查询单据详情
        List<ProcurementApplyOrderVo> paoVos = myProcurementMapper.findAllApplyOrderDetail(procurementApplyOrderVo);
        if (paoVos.size() == 0 || paoVos.get(0).getDetails().size() == 0) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }
        procurementApplyOrderVo = paoVos.get(0);


        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(storeId));
        for (Warehouse warehouse : warehouses) {
            if (procurementApplyOrderVo.getInWarehouseId() != null && procurementApplyOrderVo.getInWarehouseId().intValue() == warehouse.getId()) {
                procurementApplyOrderVo.setInWarehouseName(warehouse.getName());
            }
            if (procurementApplyOrderVo.getOutWarehouseId() != null && procurementApplyOrderVo.getOutWarehouseId().intValue() == warehouse.getId()) {
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
        procurementResultOrderVo.setId("HC-" + oldResultOrderId);
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
        ProcurementApplyOrderVo applyOrderVo = myProcurementMapper.findAllApplyOrderDetail(new ProcurementApplyOrderVo(storeId, procurementResultOrderVo.getApplyOrderId())).get(0);
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

        //修改申请单的单据状态和完成数量
        Byte orderStatus = null;
        switch (applyOrderVo.getType()) {
            case 1:     //采购订单
                if (applyOrderVo.getInReceivedQuantity() == 0 && applyOrderVo.getInNotReceivedQuantity().intValue() == applyOrderVo.getInTotalQuantity()) {        //未收
                    orderStatus = 1;
                } else if (applyOrderVo.getInReceivedQuantity() > 0 && applyOrderVo.getInNotReceivedQuantity() < applyOrderVo.getInTotalQuantity()) {        //部分收
                    orderStatus = 2;
                } else if (applyOrderVo.getInReceivedQuantity().intValue() == applyOrderVo.getInTotalQuantity() && applyOrderVo.getInNotReceivedQuantity() == 0) {        //已收
                    orderStatus = 3;
                }
                if (myProcurementMapper.updateApplyOrderOrderStatusAndQuantity(new StorageOrderVo(storeId, (byte) 1, applyOrderVo.getId(), orderStatus, applyOrderVo.getInReceivedQuantity() - applyOrderVo.getInTotalQuantity())) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                break;
            case 2:     //采购退货申请单
                if (applyOrderVo.getOutSentQuantity() == 0 && applyOrderVo.getOutTotalQuantity().intValue() == applyOrderVo.getOutNotSentQuantity()) {        //未发
                    orderStatus = 4;
                } else if (applyOrderVo.getOutSentQuantity() > 0 && applyOrderVo.getOutNotSentQuantity() < applyOrderVo.getOutTotalQuantity()) {        //部分发
                    orderStatus = 5;
                } else if (applyOrderVo.getOutSentQuantity().intValue() == applyOrderVo.getOutTotalQuantity() && applyOrderVo.getOutNotSentQuantity() == 0) {        //已发
                    orderStatus = 6;
                }
                if (myProcurementMapper.updateApplyOrderOrderStatusAndQuantity(new StorageOrderVo(storeId, (byte) 4, applyOrderVo.getId(), orderStatus, applyOrderVo.getOutSentQuantity() - applyOrderVo.getOutTotalQuantity())) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                break;
            case 3:     //采购换货申请单
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

        //红冲会计凭证
        financialAffairsUtil.redDashedAccountingDocumentMethod(new AccountingDocumentVo(storeId, oldResultOrderId, userId));

        return CommonResponse.success();
    }

    /**
     * 查询采购结果订单
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
                if (vo.getProcurementApplyOrderVo() != null && vo.getProcurementApplyOrderVo().getInWarehouseId() != null &&
                        vo.getProcurementApplyOrderVo().getInWarehouseId().intValue() == warehouse.getId()) {
                    vo.getProcurementApplyOrderVo().setInWarehouseName(warehouse.getName());
                }
                if (vo.getProcurementApplyOrderVo() != null && vo.getProcurementApplyOrderVo().getOutWarehouseId() != null &&
                        vo.getProcurementApplyOrderVo().getOutWarehouseId().intValue() == warehouse.getId()) {
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
        titles.add(new Title("供应商编号", "procurementApplyOrderVo.supplierId"));
        titles.add(new Title("供应商名称", "procurementApplyOrderVo.supplierName"));
        titles.add(new Title("入库仓库", "procurementApplyOrderVo.inWarehouseName"));
        titles.add(new Title("出库仓库", "procurementApplyOrderVo.outWarehouseName"));
        titles.add(new Title("总商品数量", "totalQuantity"));
        titles.add(new Title("总商品金额", "totalMoney"));
        titles.add(new Title("总优惠金额", "totalDiscountMoney"));
        titles.add(new Title("本单金额", "orderMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, procurementResultOrderVos, pageVo);
        
        return CommonResponse.success(commonResult);
    }

    /**
     * 导出采购结果订单
     * @param proVo
     * @param response
     * @return
     */
    public void exportResultOrder(ProcurementResultOrderVo proVo, HttpServletResponse response) {
        try {
            //根据条件查询单据
            List<ProcurementResultOrderVo> proVos = myProcurementMapper.findAllResultOrder(proVo);

            //补上仓库名
            List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(proVo.getStoreId()));
            proVos.stream().forEach(vo -> {
                warehouses.stream().forEach(warehouse -> {
                    if (vo.getProcurementApplyOrderVo() != null && vo.getProcurementApplyOrderVo().getInWarehouseId() != null &&
                            vo.getProcurementApplyOrderVo().getInWarehouseId().intValue() == warehouse.getId()) {
                        vo.getProcurementApplyOrderVo().setInWarehouseName(warehouse.getName());
                    }
                    if (vo.getProcurementApplyOrderVo() != null && vo.getProcurementApplyOrderVo().getOutWarehouseId() != null &&
                            vo.getProcurementApplyOrderVo().getOutWarehouseId().intValue() == warehouse.getId()) {
                        vo.getProcurementApplyOrderVo().setOutWarehouseName(warehouse.getName());
                    }
                });
            });

            //备注
            String remark = "【筛选条件】" +
                    "\n单据编号：" + (proVo.getId() == null ? "无" : proVo.getId()) +
                    " 单据类型：" + (proVo.getType() == null ? "无" : proVos.get(0).getType() == 1 ? "采购入库单" : proVos.get(0).getType() == 2 ? "采购退货单" : "采购换货单") +
                    " 开始时间：" + (proVo.getProcurementApplyOrderVo().getStartTime() == null ? "无" : sdf.format(proVo.getProcurementApplyOrderVo().getStartTime())) +
                    " 结束时间：" + (proVo.getProcurementApplyOrderVo().getEndTime() == null ? "无" : sdf.format(proVo.getProcurementApplyOrderVo().getEndTime())) +
                    " 供应商名称：" + (proVo.getProcurementApplyOrderVo().getSupplierName() == null ? "无" : proVo.getProcurementApplyOrderVo().getSupplierName());

            //标题行
            List<String> titleRowCell = Arrays.asList(new String[]{
                    "单据编号", "单据类型", "单据日期", "来源订单", "结算状态", "供应商编号", "供应商名称", "入库仓库", "出库仓库", "总商品数量", "总商品金额", "总优惠金额", "本单金额", "经手人", "备注"
            });

            //最后一个必填列列数
            int lastRequiredCol = -1;

            //数据行
            List<List<String>> dataRowCells = new ArrayList<>();
            proVos.stream().forEach(vo -> {
                List<String> dataRowCell = new ArrayList<>();
                dataRowCell.add(vo.getId());
                dataRowCell.add(vo.getType() == 1 ? "采购入库单" : vo.getType() == 2 ? "采购退货单" : "彩果换货单");
                dataRowCell.add(sdf.format(vo.getCreateTime()));
                dataRowCell.add(vo.getApplyOrderId());
                ProcurementApplyOrderVo paoVo = vo.getProcurementApplyOrderVo();
                dataRowCell.add(paoVo.getClearStatus() == 0 ? "未完成" : paoVo.getClearStatus() == 1 ? "已完成" : null);
                dataRowCell.add(paoVo.getSupplierId());
                dataRowCell.add(paoVo.getSupplierName());
                dataRowCell.add(paoVo.getInWarehouseName());
                dataRowCell.add(paoVo.getOutWarehouseName());
                dataRowCell.add(vo.getTotalQuantity().toString());
                dataRowCell.add(vo.getTotalMoney().toString());
                dataRowCell.add(vo.getTotalDiscountMoney().toString());
                dataRowCell.add(vo.getOrderMoney().toString());
                dataRowCell.add(vo.getUserName());
                dataRowCell.add(vo.getRemark());
                if (vo.getOrderStatus() < 1) {
                    dataRowCell.add(vo.getOrderStatus() == -1 ? "红冲蓝单" : vo.getOrderStatus() == -2 ? "红冲红单" : null);
                }
                dataRowCells.add(dataRowCell);
            });

            //输出excel
            String fileName = "【采购历史导出】_" + System.currentTimeMillis() + ".xls";
            CommonUtil.outputExcelMethod(remark, titleRowCell, lastRequiredCol, dataRowCells, fileName, response);
        } catch (Exception e) {
            throw new CommonException(CommonResponse.EXPORT_ERROR, e.getMessage());
        }
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
            if (procurementResultOrderVo.getProcurementApplyOrderVo() != null && procurementResultOrderVo.getProcurementApplyOrderVo().getInWarehouseId() != null &&
                    procurementResultOrderVo.getProcurementApplyOrderVo().getInWarehouseId().intValue() == warehouse.getId()) {
                procurementResultOrderVo.getProcurementApplyOrderVo().setInWarehouseName(warehouse.getName());
            }
            if (procurementResultOrderVo.getProcurementApplyOrderVo() != null && procurementResultOrderVo.getProcurementApplyOrderVo().getOutWarehouseId() != null &&
                    procurementResultOrderVo.getProcurementApplyOrderVo().getOutWarehouseId().intValue() == warehouse.getId()) {
                procurementResultOrderVo.getProcurementApplyOrderVo().setOutWarehouseName(warehouse.getName());
            }
        }

        return CommonResponse.success(procurementResultOrderVo);
    }
}
