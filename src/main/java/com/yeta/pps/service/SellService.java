package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.*;
import com.yeta.pps.po.GoodsSku;
import com.yeta.pps.po.SellApplyOrder;
import com.yeta.pps.po.Warehouse;
import com.yeta.pps.util.*;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private IntegralUtil integralUtil;

    @Autowired
    private FundUtil fundUtil;

    //销售申请订单

    /**
     * 新增销售申请订单
     * @param sellApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse addApplyOrder(SellApplyOrderVo sellApplyOrderVo) {
        //获取参数
        Integer storeId = sellApplyOrderVo.getStoreId();
        List<OrderGoodsSkuVo> orderGoodsSkuVos = sellApplyOrderVo.getDetails();
        Byte type = sellApplyOrderVo.getType();
        if (type == 3 || type == 4) {
            SellResultOrderVo srVo = mySellMapper.findResultOrderDetailById(new SellResultOrderVo(storeId, sellApplyOrderVo.getResultOrderId()));
            if (srVo == null || srVo.getOrderStatus() < 0) {
                return CommonResponse.error(CommonResponse.ADD_ERROR, CommonResponse.STATUS_ERROR);
            }
        }

        //优惠券的钱
        double discountCouponMoney = 0;

        //判断是新增类型
        switch (type) {
            case 1:     //零售单
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getOutWarehouseId() == null || sellApplyOrderVo.getOutTotalQuantity() == null || sellApplyOrderVo.getBankAccountId() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }

                //设置初始属性
                sellApplyOrderVo.setId("LSD_" + UUID.randomUUID().toString().replace("-", ""));
                sellApplyOrderVo.setCreateTime(new Date());
                sellApplyOrderVo.setOrderStatus((byte) 6);       //已发
                sellApplyOrderVo.setClearStatus((byte) 1);      //已完成
                sellApplyOrderVo.setOutSentQuantity(sellApplyOrderVo.getOutTotalQuantity());
                sellApplyOrderVo.setOutNotSentQuantity(0);
                sellApplyOrderVo.setClearedMoney(sellApplyOrderVo.getOrderMoney());
                sellApplyOrderVo.setNotClearedMoney(0.0);
                break;

            case 2:     //销售订单
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getOutWarehouseId() == null || sellApplyOrderVo.getOutTotalQuantity() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }

                //设置初始属性
                sellApplyOrderVo.setId("XXDD_" + UUID.randomUUID().toString().replace("-", ""));
                sellApplyOrderVo.setCreateTime(new Date());
                sellApplyOrderVo.setOrderStatus((byte) 4);       //未发
                sellApplyOrderVo.setClearStatus((byte) 0);      //未完成
                sellApplyOrderVo.setOutSentQuantity(0);
                sellApplyOrderVo.setOutNotSentQuantity(sellApplyOrderVo.getOutTotalQuantity());
                sellApplyOrderVo.setClearedMoney(0.0);
                sellApplyOrderVo.setNotClearedMoney(sellApplyOrderVo.getOrderMoney());
                break;

            case 3:     //销售退货申请单
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getInWarehouseId() == null || sellApplyOrderVo.getInTotalQuantity() == null ||
                        sellApplyOrderVo.getResultOrderId() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }

                //设置初始属性
                sellApplyOrderVo.setId("XXTHSQD_" + UUID.randomUUID().toString().replace("-", ""));
                sellApplyOrderVo.setCreateTime(new Date());
                sellApplyOrderVo.setOrderStatus((byte) 1);       //未收
                sellApplyOrderVo.setClearStatus((byte) 0);      //未完成
                sellApplyOrderVo.setInReceivedQuantity(0);
                sellApplyOrderVo.setInNotReceivedQuantity(sellApplyOrderVo.getInTotalQuantity());
                sellApplyOrderVo.setClearedMoney(0.0);
                sellApplyOrderVo.setNotClearedMoney(sellApplyOrderVo.getOrderMoney());
                break;

            case 4:     //销售换货申请单
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getInWarehouseId() == null || sellApplyOrderVo.getInTotalQuantity() == null ||
                        sellApplyOrderVo.getOutWarehouseId() == null || sellApplyOrderVo.getOutTotalQuantity() == null ||
                        sellApplyOrderVo.getResultOrderId() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }

                //设置初始属性
                sellApplyOrderVo.setId("XXHHSQD_" + UUID.randomUUID().toString().replace("-", ""));
                sellApplyOrderVo.setCreateTime(new Date());
                sellApplyOrderVo.setOrderStatus((byte) 7);       //未收未发
                sellApplyOrderVo.setClearStatus((byte) 0);      //未完成
                sellApplyOrderVo.setInReceivedQuantity(0);
                sellApplyOrderVo.setInNotReceivedQuantity(sellApplyOrderVo.getInTotalQuantity());
                sellApplyOrderVo.setOutSentQuantity(0);
                sellApplyOrderVo.setOutNotSentQuantity(sellApplyOrderVo.getOutTotalQuantity());
                sellApplyOrderVo.setClearedMoney(0.0);
                sellApplyOrderVo.setNotClearedMoney(sellApplyOrderVo.getOrderMoney());
                break;

            default:
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //判断优惠券
        if (sellApplyOrderVo.getDiscountCouponId() != null) {
            //TODO
            //判断优惠券是否存在
            //discountCouponMoney =
        }

        //判断钱
        if (sellApplyOrderVo.getDiscountMoney() + discountCouponMoney != sellApplyOrderVo.getTotalDiscountMoney() ||
                sellApplyOrderVo.getOrderMoney() + sellApplyOrderVo.getTotalDiscountMoney() != sellApplyOrderVo.getTotalMoney()) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //新增销售申请单
        if (mySellMapper.addApplyOrder(sellApplyOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }

        //新增销售申请单/商品规格关系
        List<GoodsSku> goodsSkus = myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(storeId));
        SellResultOrderVo sellResultOrderVo = new SellResultOrderVo(0.0);
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            //判断参数
            if (orderGoodsSkuVo.getType() == null || orderGoodsSkuVo.getGoodsSkuId() == null || orderGoodsSkuVo.getQuantity() == null || orderGoodsSkuVo.getMoney() == null || orderGoodsSkuVo.getDiscountMoney() == null) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }

            //设置初始属性
            orderGoodsSkuVo.setStoreId(sellApplyOrderVo.getStoreId());
            orderGoodsSkuVo.setOrderId(sellApplyOrderVo.getId());
            if (type == 1) {        //零售单新增结果单
                orderGoodsSkuVo.setFinishQuantity(orderGoodsSkuVo.getQuantity());
                orderGoodsSkuVo.setNotFinishQuantity(0);
                orderGoodsSkuVo.setOperatedQuantity(0);

                //减库存
                inventoryUtil.updateInventoryMethod((byte) 0, new WarehouseGoodsSkuVo(storeId, sellApplyOrderVo.getOutWarehouseId(), orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getQuantity(), orderGoodsSkuVo.getQuantity(), orderGoodsSkuVo.getQuantity()));

                //库存记账
                StorageCheckOrderVo storageCheckOrderVo = new StorageCheckOrderVo();
                storageCheckOrderVo.setStoreId(storeId);
                storageCheckOrderVo.setOrderId(sellApplyOrderVo.getId());
                storageCheckOrderVo.setTargetId(sellApplyOrderVo.getClientId());
                storageCheckOrderVo.setCreateTime(new Date());
                storageCheckOrderVo.setOrderStatus((byte) 1);
                storageCheckOrderVo.setGoodsSkuId(orderGoodsSkuVo.getGoodsSkuId());
                storageCheckOrderVo.setOutWarehouseId(sellApplyOrderVo.getOutWarehouseId());
                storageCheckOrderVo.setOutQuantity(orderGoodsSkuVo.getQuantity());
                storageCheckOrderVo.setUserId(sellApplyOrderVo.getUserId());
                double costMoney = inventoryUtil.addStorageCheckOrderMethod(0, storageCheckOrderVo, null);

                //统计成本
                sellResultOrderVo.setCostMoney(sellResultOrderVo.getCostMoney() + costMoney * orderGoodsSkuVo.getQuantity());
            } else {
                orderGoodsSkuVo.setFinishQuantity(0);
                orderGoodsSkuVo.setNotFinishQuantity(orderGoodsSkuVo.getQuantity());
                orderGoodsSkuVo.setOperatedQuantity(0);

                //修改库存相关
                Integer warehouseId = null;
                int notSentQuantity = 0;
                int notReceivedQuantity = 0;
                if (orderGoodsSkuVo.getType() == 1) {
                    warehouseId = sellApplyOrderVo.getInWarehouseId();
                    notReceivedQuantity = orderGoodsSkuVo.getQuantity();
                } else if (orderGoodsSkuVo.getType() == 0) {
                    warehouseId = sellApplyOrderVo.getOutWarehouseId();
                    notSentQuantity = orderGoodsSkuVo.getQuantity();

                    //减少可用库存
                    inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, warehouseId, orderGoodsSkuVo.getGoodsSkuId(), 0, notSentQuantity, 0));
                }
                //增加待发货数量、待收货数量
                inventoryUtil.updateNotQuantityMethod(1, new WarehouseGoodsSkuVo(storeId, warehouseId, orderGoodsSkuVo.getGoodsSkuId(), notSentQuantity, notReceivedQuantity));
            }

            //新增
            if (myOrderGoodsSkuMapper.addOrderGoodsSku(orderGoodsSkuVo) != 1) {
                throw new CommonException(CommonResponse.ADD_ERROR);
            }

            //修改销售出库单对应的商品规格已操作数量
            if (type == 3 || (type == 4 && orderGoodsSkuVo.getType() == 1)) {       //如果是销售退/换货申请单
                if (orderGoodsSkuVo.getId() == null) {
                    throw new CommonException(CommonResponse.PARAMETER_ERROR);
                }
                if (myOrderGoodsSkuMapper.updateOperatedQuantity(new OrderGoodsSkuVo(sellApplyOrderVo.getStoreId(), orderGoodsSkuVo.getId(), orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getQuantity())) != 1) {
                    throw new CommonException(CommonResponse.ADD_ERROR);
                }
            }
        });

        //新增销售结果单
        if (type == 1) {
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
            sellResultOrderVo.setUserId(sellApplyOrderVo.getUserId());
            sellResultOrderVo.setRemark(sellApplyOrderVo.getRemark());
            if (mySellMapper.addResultOrder(sellResultOrderVo) != 1) {
                throw new CommonException(CommonResponse.ADD_ERROR);
            }

            //修改客户积分相关信息
            integralUtil.updateIntegralMethod(1, storeId, sellApplyOrderVo.getClientId(), sellResultOrderVo.getId(), goodsSkus, orderGoodsSkuVos);

            //记录资金对账记录
            FundCheckOrderVo vo = new FundCheckOrderVo();
            vo.setStoreId(storeId);
            vo.setOrderId(sellApplyOrderVo.getId());
            vo.setCreateTime(new Date());
            vo.setOrderStatus((byte) 1);
            vo.setBankAccountId(sellApplyOrderVo.getBankAccountId());
            vo.setUserId(sellApplyOrderVo.getUserId());
            vo.setRemark(sellApplyOrderVo.getRemark());

            //查询最新的资金对账记录
            FundCheckOrderVo lastVo = myFundMapper.findLastBalanceMoney(vo);
            if (lastVo == null) {
                throw new CommonException(CommonResponse.ADD_ERROR);
            }

            vo.setInMoney(sellApplyOrderVo.getOrderMoney());
            vo.setOutMoney(0.0);
            vo.setBalanceMoney(lastVo.getBalanceMoney() + sellApplyOrderVo.getOrderMoney());
            if (myFundMapper.addFundCheckOrder(vo) != 1) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
        }

        //修改客户最近交易时间
        if (myClientMapper.updateLastDealTime(new ClientVo(sellApplyOrderVo.getClientId())) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR);
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
            //获取销售申请订单
            SellApplyOrderVo applyOrderVo =  mySellMapper.findApplyOrderDetailById(sellApplyOrderVo);
            if (applyOrderVo == null || applyOrderVo.getDetails().size() == 0) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }

            //判断单据状态
            Byte orderStatus = applyOrderVo.getOrderStatus();
            if (orderStatus != 1 && orderStatus != 4 && orderStatus != 7) {     //1：未收，4：未发，7：未收未发
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.STATUS_ERROR);
            }

            //判断结算状态
            Byte clearStatus = applyOrderVo.getClearStatus();
            if (clearStatus != 0) {     //0：未完成
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.STATUS_ERROR);
            }

            //修改商品规格可操作数量
            if (applyOrderVo.getType() == 3 || applyOrderVo.getType() == 4) {
                List<OrderGoodsSkuVo> applyOrderGoodsSkuVos = applyOrderVo.getDetails().stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("1")).collect(Collectors.toList());        //采购退换申请单或采购换货申请单中入库的的商品规格
                SellResultOrderVo resultOrderVo = mySellMapper.findResultOrderDetailById(new SellResultOrderVo(sellApplyOrderVo.getStoreId(), applyOrderVo.getResultOrderId()));
                List<OrderGoodsSkuVo> resultOrderGoodsSkuVos = resultOrderVo.getDetails();
                resultOrderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
                    Optional<OrderGoodsSkuVo> optional = applyOrderGoodsSkuVos.stream().filter(vo -> vo.getGoodsSkuId().equals(orderGoodsSkuVo.getGoodsSkuId())).findFirst();
                    if (optional.isPresent()) {
                        int changeQuantity = optional.get().getQuantity();
                        orderGoodsSkuVo.setStoreId(sellApplyOrderVo.getStoreId());
                        orderGoodsSkuVo.setOperatedQuantity(-changeQuantity);
                        if (myOrderGoodsSkuMapper.updateOperatedQuantity(orderGoodsSkuVo) != 1) {
                            throw new CommonException(CommonResponse.DELETE_ERROR);
                        }
                    }
                });
            }

            //删除销售申请订单
            if (mySellMapper.deleteApplyOrder(sellApplyOrderVo) != 1) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }

            //删除销售申请订单/商品规格关系
            OrderGoodsSkuVo orderGoodsSkuVo = new OrderGoodsSkuVo(sellApplyOrderVo.getStoreId(), applyOrderVo.getId());
            myOrderGoodsSkuMapper.deleteOrderGoodsSku(orderGoodsSkuVo);
        });

        return CommonResponse.success();
    }

    /**
     * 修改销售申请订单
     * @param sellApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse updateApplyOrder(SellApplyOrderVo sellApplyOrderVo) {
        //获取销售申请订单
        SellApplyOrderVo vo = mySellMapper.findApplyOrderById(sellApplyOrderVo);
        if (vo == null) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //判断单据状态
        Byte orderStatus = vo.getOrderStatus();
        if (orderStatus != 1 && orderStatus != 4 && orderStatus != 7) {     //1：未收，4：未发，7：未收未发
            return CommonResponse.error(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
        }

        //判断结算状态
        Byte clearStatus = vo.getClearStatus();
        if (clearStatus != 0) {     //0：未完成
            return CommonResponse.error(CommonResponse.UPDATE_ERROR, CommonResponse.STATUS_ERROR);
        }

        //判断是销售订单/销售退货申请/销售换货申请
        List<OrderGoodsSkuVo> orderGoodsSkuVos = sellApplyOrderVo.getDetails();
        Byte type = vo.getType();
        switch (type) {
            case 2:     //销售订单
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getOutWarehouseId() == null || sellApplyOrderVo.getOutTotalQuantity() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                break;

            case 3:     //销售退货申请单
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getInWarehouseId() == null || sellApplyOrderVo.getInTotalQuantity() == null ||
                        sellApplyOrderVo.getResultOrderId() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                break;

            case 4:     //销售换货申请单
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getInWarehouseId() == null || sellApplyOrderVo.getInTotalQuantity() == null ||
                        sellApplyOrderVo.getOutWarehouseId() == null || sellApplyOrderVo.getOutTotalQuantity() == null ||
                        sellApplyOrderVo.getResultOrderId() == null) {
                    return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
                }
                break;

            default:
                return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //修改销售申请订单
        if (mySellMapper.updateApplyOrder(sellApplyOrderVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }

        //删除销售申请订单/商品规格关系
        myOrderGoodsSkuMapper.deleteOrderGoodsSku(new OrderGoodsSkuVo(sellApplyOrderVo.getStoreId(), sellApplyOrderVo.getId()));

        //新增销售申请订单/商品规格关系
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            //判断参数
            if (orderGoodsSkuVo.getType() == null || orderGoodsSkuVo.getGoodsSkuId() == null ||
                    orderGoodsSkuVo.getQuantity() == null || orderGoodsSkuVo.getMoney() == null || orderGoodsSkuVo.getDiscountMoney() == null) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR);
            }

            //设置初始属性
            orderGoodsSkuVo.setStoreId(sellApplyOrderVo.getStoreId());
            orderGoodsSkuVo.setOrderId(sellApplyOrderVo.getId());
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
     * 查询所有销售申请订单
     * @param sellApplyOrderVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAllApplyOrder(SellApplyOrderVo sellApplyOrderVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(mySellMapper.findCountApplyOrder(sellApplyOrderVo) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<SellApplyOrderVo> sellApplyOrderVos = mySellMapper.findAllPagedApplyOrder(sellApplyOrderVo, pageVo);
        
        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(sellApplyOrderVo.getStoreId()));
        sellApplyOrderVos.stream().forEach(vo -> {
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
        titles.add(new Title("单据日期", "createTime"));
        titles.add(new Title("产生方式", "prodcingWay"));
        titles.add(new Title("单据状态", "orderStatus"));
        titles.add(new Title("客户", "client.name"));
        titles.add(new Title("电话", "client.phone"));
        titles.add(new Title("会员卡号", "client.membershipNumber"));
        byte type = sellApplyOrderVo.getType();
        if (type == 3 || type == 4) {
            titles.add(new Title("入库仓库", "inWarehouseName"));
            titles.add(new Title("总收货数量", "inTotalQuantity"));
            titles.add(new Title("已收货数量", "inReceivedQuantity"));
            titles.add(new Title("未收货数量", "inNotReceivedQuantity"));
        } else if (type == 1 || type == 2 || type == 4) {
            if (type != 1) {
                titles.add(new Title("来源订单", "resultOrderId"));
            }
            titles.add(new Title("出库仓库", "outWarehouseName"));
            titles.add(new Title("总发货数量", "outTotalQuantity"));
            titles.add(new Title("已发货数量", "outSentQuantity"));
            titles.add(new Title("未发货数量数量", "outNotSentQuantity"));
        }
        //仓库收发货不关心金额
        if (sellApplyOrderVo.getOrderStatus() == null && sellApplyOrderVo.getClearStatus() != null) {
            titles.add(new Title("总商品金额", "totalMoney"));
            titles.add(new Title("直接优惠金额", "discountMoney"));
            titles.add(new Title("优惠券编号", "discountCouponId"));
            //TODO
            //优惠券金额
            titles.add(new Title("总优惠金额", "totalDiscountMoney"));
            titles.add(new Title("本单金额", "orderMoney"));
            titles.add(new Title("结算状态", "clearStatus"));
            titles.add(new Title("已结金额", "clearedMoney"));
            titles.add(new Title("未结金额", "notClearedMoney"));
        }
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, sellApplyOrderVos, pageVo);
        
        return CommonResponse.success(commonResult);
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
        sellApplyOrderVo = mySellMapper.findApplyOrderDetailById(sellApplyOrderVo);
        if (sellApplyOrderVo == null || sellApplyOrderVo.getDetails().size() == 0) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }

        //补上仓库名
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(storeId));
        for (Warehouse warehouse : warehouses) {
            if (sellApplyOrderVo.getInWarehouseId() == warehouse.getId()) {
                sellApplyOrderVo.setInWarehouseName(warehouse.getName());
            }
            if (sellApplyOrderVo.getOutWarehouseId() == warehouse.getId()) {
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
        sellResultOrderVo.setId("HC_" + oldResultOrderId);
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
        SellApplyOrderVo applyOrderVo = mySellMapper.findApplyOrderDetailById(new SellApplyOrderVo(storeId, sellResultOrderVo.getApplyOrderId()));
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

            if (orderGoodsSkuVo.getType() == 0) {        //原来是出库
                //增加商品规格库存
                inventoryUtil.updateInventoryMethod(1, new WarehouseGoodsSkuVo(storeId, sellResultOrderVo.getSellApplyOrderVo().getOutWarehouseId(), orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getQuantity(), orderGoodsSkuVo.getQuantity(), orderGoodsSkuVo.getQuantity()));

                //红冲库存记账记录
                inventoryUtil.redDashedStorageCheckOrderMethod(0, new StorageCheckOrderVo(storeId, oldResultOrderId, orderGoodsSkuVo.getGoodsSkuId(), userId));

                if (applyOrderVo.getType() != 1) {
                    //减少完成数量
                    Optional<OrderGoodsSkuVo> optional = applyOrderOutVos.stream().filter(applyOrderInVo -> applyOrderInVo.getGoodsSkuId().toString().equals(orderGoodsSkuVo.getGoodsSkuId().toString())).findFirst();
                    if (!optional.isPresent()) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    OrderGoodsSkuVo applyOrderInVo = optional.get();
                    applyOrderInVo.setStoreId(storeId);
                    applyOrderInVo.setChangeQuantity(-orderGoodsSkuVo.getQuantity());
                    if (myOrderGoodsSkuMapper.updateOrderGoodsSku(applyOrderInVo) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    applyOrderVo.setOutSentQuantity(applyOrderVo.getOutSentQuantity() - orderGoodsSkuVo.getQuantity());
                    applyOrderVo.setOutNotSentQuantity(applyOrderVo.getOutNotSentQuantity() + orderGoodsSkuVo.getQuantity());
                }
            } else if (orderGoodsSkuVo.getType() == 1) {     //原来是入库
                //减少商品规格库存
                inventoryUtil.updateInventoryMethod(0, new WarehouseGoodsSkuVo(storeId, sellResultOrderVo.getSellApplyOrderVo().getInWarehouseId(), orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getQuantity(), orderGoodsSkuVo.getQuantity(), orderGoodsSkuVo.getQuantity()));

                //红冲库存记账记录
                inventoryUtil.redDashedStorageCheckOrderMethod(1, new StorageCheckOrderVo(storeId, oldResultOrderId, orderGoodsSkuVo.getGoodsSkuId(), userId));

                if (applyOrderVo.getType() != 1) {
                    //减少完成数量
                    Optional<OrderGoodsSkuVo> optional = applyOrderInVos.stream().filter(applyOrderInVo -> applyOrderInVo.getGoodsSkuId().toString().equals(orderGoodsSkuVo.getGoodsSkuId().toString())).findFirst();
                    if (!optional.isPresent()) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    OrderGoodsSkuVo applyOrderInVo = optional.get();
                    applyOrderInVo.setStoreId(storeId);
                    applyOrderInVo.setChangeQuantity(-orderGoodsSkuVo.getQuantity());
                    if (myOrderGoodsSkuMapper.updateOrderGoodsSku(applyOrderInVo) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    applyOrderVo.setInReceivedQuantity(applyOrderVo.getInReceivedQuantity() - orderGoodsSkuVo.getQuantity());
                    applyOrderVo.setInNotReceivedQuantity(applyOrderVo.getInNotReceivedQuantity() + orderGoodsSkuVo.getQuantity());
                }
            } else {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
        }

        //修改申请订单的单据状态和完成数量
        Byte orderStatus = null;
        switch (applyOrderVo.getType()) {
            case 2:     //销售订单
                if (applyOrderVo.getOutSentQuantity() == 0 && applyOrderVo.getOutTotalQuantity() == applyOrderVo.getOutNotSentQuantity()) {        //未发
                    orderStatus = 4;
                } else if (applyOrderVo.getOutSentQuantity() > 0 && applyOrderVo.getOutNotSentQuantity() < applyOrderVo.getOutTotalQuantity()) {        //部分发
                    orderStatus = 5;
                } else if (applyOrderVo.getOutSentQuantity() == applyOrderVo.getOutTotalQuantity() && applyOrderVo.getOutNotSentQuantity() == 0) {        //已发
                    orderStatus = 6;
                }
                if (mySellMapper.updateApplyOrderOrderStatusAndQuantity(new StorageOrderVo(storeId, (byte) 3, applyOrderVo.getId(), orderStatus, applyOrderVo.getOutSentQuantity() - applyOrderVo.getOutTotalQuantity())) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                break;
            case 3:     //销售退货申请单
                if (applyOrderVo.getInReceivedQuantity() == 0 && applyOrderVo.getInNotReceivedQuantity() == applyOrderVo.getInTotalQuantity()) {        //未收
                    orderStatus = 1;
                } else if (applyOrderVo.getInReceivedQuantity() > 0 && applyOrderVo.getInNotReceivedQuantity() < applyOrderVo.getInTotalQuantity()) {        //部分收
                    orderStatus = 2;
                } else if (applyOrderVo.getInReceivedQuantity() == applyOrderVo.getInTotalQuantity() && applyOrderVo.getInNotReceivedQuantity() == 0) {        //已收
                    orderStatus = 3;
                }
                if (mySellMapper.updateApplyOrderOrderStatusAndQuantity(new StorageOrderVo(storeId, (byte) 2, applyOrderVo.getId(), orderStatus, applyOrderVo.getInReceivedQuantity() - applyOrderVo.getInTotalQuantity())) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                break;
            case 4:     //销售换货申请单
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

        //修改商品规格可操作数量
        if (sellResultOrderVo.getType() == 3 || sellResultOrderVo.getType() == 4) {
            List<OrderGoodsSkuVo> applyOrderGoodsSkuVos = applyOrderVo.getDetails().stream().filter(orderGoodsSkuVo -> orderGoodsSkuVo.getType().toString().equals("1")).collect(Collectors.toList());        //采购退换申请单或采购换货申请单中出库的的商品规格
            //查询该销售退货申请单或销售换货申请单对应的销售出库单
            SellResultOrderVo resultOrderVo = mySellMapper.findResultOrderDetailById(new SellResultOrderVo(storeId, applyOrderVo.getResultOrderId()));
            List<OrderGoodsSkuVo> resultOrderGoodsSkuVos = resultOrderVo.getDetails();      //销售出库单的商品规格
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
        } else if (sellResultOrderVo.getType() == 1) {      //零售单红冲资金对账记录、减少客户积分
            //TODO
            fundUtil.redDashedFundCheckOrderMethod(new FundCheckOrderVo(storeId, sellResultOrderVo.getApplyOrderId(), userId));
        }

        return CommonResponse.success();
    }

    /**
     * 查询所有销售结果订单
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
                if (vo.getSellApplyOrderVo().getInWarehouseId() == warehouse.getId()) {
                    vo.getSellApplyOrderVo().setInWarehouseName(warehouse.getName());
                }
                if (vo.getSellApplyOrderVo().getOutWarehouseId() == warehouse.getId()) {
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
        titles.add(new Title("入库仓库", "sellApplyOrderVo.inWarehouseName"));
        titles.add(new Title("出库仓库", "sellApplyOrderVo.outWarehouseName"));
        titles.add(new Title("客户", "sellApplyOrderVo.client.name"));
        titles.add(new Title("电话", "sellApplyOrderVo.client.phone"));
        titles.add(new Title("会员卡号", "sellApplyOrderVo.client.membershipNumber"));
        titles.add(new Title("总商品数量", "totalQuantity"));
        titles.add(new Title("总订单金额", "totalMoney"));
        titles.add(new Title("总优惠金额", "totalDiscountMoney"));
        titles.add(new Title("本单金额", "orderMoney"));
        titles.add(new Title("成本", "costMoney"));
        titles.add(new Title("毛利", "grossMarginMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, sellResultOrderVos, pageVo);
        
        return CommonResponse.success(commonResult);
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
            if (sellResultOrderVo.getSellApplyOrderVo().getInWarehouseId() == warehouse.getId()) {
                sellResultOrderVo.getSellApplyOrderVo().setInWarehouseName(warehouse.getName());
            }
            if (sellResultOrderVo.getSellApplyOrderVo().getOutWarehouseId() == warehouse.getId()) {
                sellResultOrderVo.getSellApplyOrderVo().setOutWarehouseName(warehouse.getName());
            }
        }

        return CommonResponse.success(sellResultOrderVo);
    }
}
