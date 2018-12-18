package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.*;
import com.yeta.pps.po.GoodsSku;
import com.yeta.pps.po.SellApplyOrder;
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
 * 销售相关逻辑处理
 * @author YETA
 * @date 2018/12/10/15:04
 */
@Service
public class SellService {

    private static final Logger LOG = LoggerFactory.getLogger(SellService.class);

    @Autowired
    private MySellMapper mySellMapper;

    @Autowired
    private MyOrderGoodsSkuMapper myOrderGoodsSkuMapper;

    @Autowired
    private MyFundMapper myFundMapper;

    @Autowired
    private MyWarehouseMapper myWarehouseMapper;

    @Autowired
    private MyGoodsMapper myGoodsMapper;

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
        //优惠券的钱
        double discountCouponMoney = 0;
        //判断是零售单/销售订单/销售退货申请单/销售换货申请单
        switch (type) {
            //零售单
            case 1:
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getOutWarehouseId() == null || sellApplyOrderVo.getOutTotalQuantity() == null || sellApplyOrderVo.getBankAccountId() == null) {
                    LOG.info("新增销售申请单，参数不匹配，类型：【{}】，商品规格数量：【{}】，出库仓库【{}】，出库数量：【{}】，银行账户编号：【{}】", type, orderGoodsSkuVos.size(), sellApplyOrderVo.getOutWarehouseId(), sellApplyOrderVo.getOutTotalQuantity(), sellApplyOrderVo.getBankAccountId());
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                //设置初始属性
                sellApplyOrderVo.setId("LSD_" + UUID.randomUUID().toString().replace("-", ""));
                sellApplyOrderVo.setOrderStatus((byte) 6);       //已发
                sellApplyOrderVo.setClearStatus((byte) 1);      //已完成
                sellApplyOrderVo.setOutSentQuantity(sellApplyOrderVo.getOutTotalQuantity());
                sellApplyOrderVo.setOutNotSentQuantity(0);
                sellApplyOrderVo.setClearedMoney(sellApplyOrderVo.getOrderMoney());
                sellApplyOrderVo.setNotClearedMoney(new BigDecimal(0));
                break;
            //销售订单
            case 2:
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getOutWarehouseId() == null || sellApplyOrderVo.getOutTotalQuantity() == null) {
                    LOG.info("新增销售申请单，参数不匹配，类型：【{}】，商品规格数量：【{}】，出库仓库【{}】，出库数量：【{}】", type, orderGoodsSkuVos.size(), sellApplyOrderVo.getOutWarehouseId(), sellApplyOrderVo.getOutTotalQuantity());
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                //设置初始属性
                sellApplyOrderVo.setId("XXDD_" + UUID.randomUUID().toString().replace("-", ""));
                sellApplyOrderVo.setOrderStatus((byte) 4);       //已发
                sellApplyOrderVo.setClearStatus((byte) 0);      //未完成
                sellApplyOrderVo.setOutSentQuantity(0);
                sellApplyOrderVo.setOutNotSentQuantity(sellApplyOrderVo.getOutTotalQuantity());
                sellApplyOrderVo.setClearedMoney(new BigDecimal(0));
                sellApplyOrderVo.setNotClearedMoney(sellApplyOrderVo.getOrderMoney());
                break;
            //销售退货申请单
            case 3:
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getInWarehouseId() == null || sellApplyOrderVo.getInTotalQuantity() == null ||
                        sellApplyOrderVo.getResultOrderId() == null) {
                    LOG.info("新增销售申请单，参数不匹配，类型：【{}】，商品规格数量：【{}】，入库仓库【{}】，入库数量：【{}】，来源订单【{}】", type, orderGoodsSkuVos.size(), sellApplyOrderVo.getInWarehouseId(), sellApplyOrderVo.getInTotalQuantity(), sellApplyOrderVo.getResultOrderId());
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                //设置初始属性
                sellApplyOrderVo.setId("XXTHSQD_" + UUID.randomUUID().toString().replace("-", ""));
                sellApplyOrderVo.setOrderStatus((byte) 1);       //未收
                sellApplyOrderVo.setClearStatus((byte) 0);      //未完成
                sellApplyOrderVo.setInReceivedQuantity(0);
                sellApplyOrderVo.setInNotReceivedQuantity(sellApplyOrderVo.getInTotalQuantity());
                sellApplyOrderVo.setClearedMoney(new BigDecimal(0));
                sellApplyOrderVo.setNotClearedMoney(sellApplyOrderVo.getOrderMoney());
                break;
            //销售换货申请单
            case 4:
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getInWarehouseId() == null || sellApplyOrderVo.getInTotalQuantity() == null ||
                        sellApplyOrderVo.getOutWarehouseId() == null || sellApplyOrderVo.getOutTotalQuantity() == null ||
                        sellApplyOrderVo.getResultOrderId() == null) {
                    LOG.info("新增销售申请单，参数不匹配，类型：【{}】，商品规格数量：【{}】，入库仓库【{}】，入库数量：【{}】，出库仓库【{}】，出库数量【{}】，来源订单【{}】", type, orderGoodsSkuVos.size(), sellApplyOrderVo.getInWarehouseId(), sellApplyOrderVo.getInTotalQuantity(), sellApplyOrderVo.getOutWarehouseId(), sellApplyOrderVo.getOutTotalQuantity(), sellApplyOrderVo.getResultOrderId());
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                //设置初始属性
                sellApplyOrderVo.setId("XXHHSQD_" + UUID.randomUUID().toString().replace("-", ""));
                sellApplyOrderVo.setOrderStatus((byte) 7);       //未收未发
                sellApplyOrderVo.setClearStatus((byte) 0);      //未完成
                sellApplyOrderVo.setInReceivedQuantity(0);
                sellApplyOrderVo.setInNotReceivedQuantity(sellApplyOrderVo.getInTotalQuantity());
                sellApplyOrderVo.setOutSentQuantity(0);
                sellApplyOrderVo.setOutNotSentQuantity(sellApplyOrderVo.getOutTotalQuantity());
                sellApplyOrderVo.setClearedMoney(new BigDecimal(0));
                sellApplyOrderVo.setNotClearedMoney(sellApplyOrderVo.getOrderMoney());
                break;
            default:
                LOG.info("新增销售申请单，参数不匹配，类型：【{}】", type);
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        sellApplyOrderVo.setCreateTime(new Date());
        //判断优惠券
        if (sellApplyOrderVo.getDiscountCouponId() != null) {
            //TODO
            //判断优惠券是否存在
            //discountCouponMoney =
        }
        //判断钱
        if (sellApplyOrderVo.getDiscountMoney().doubleValue() + discountCouponMoney != sellApplyOrderVo.getTotalDiscountMoney().doubleValue() ||
                sellApplyOrderVo.getOrderMoney().doubleValue() + sellApplyOrderVo.getTotalDiscountMoney().doubleValue() != sellApplyOrderVo.getTotalMoney().doubleValue()) {
            LOG.info("新增销售申请单，钱不正确，类型【{}】", type);
            throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
        }
        //新增销售申请单
        if (mySellMapper.addApplyOrder(sellApplyOrderVo) != 1) {
            LOG.info("新增销售申请单，插入销售申请单数据失败，类型【{}】", type);
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //新增销售申请单/商品规格关系
        GoodsSkuVo goodsSkuVo = new GoodsSkuVo(storeId);
        List<GoodsSku> goodsSkus = myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(storeId));
        SellResultOrderVo sellResultOrderVo = new SellResultOrderVo(new BigDecimal(0));
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            //判断参数
            if (orderGoodsSkuVo.getType() == null || orderGoodsSkuVo.getGoodsSkuId() == null || orderGoodsSkuVo.getQuantity() == null || orderGoodsSkuVo.getMoney() == null || orderGoodsSkuVo.getDiscountMoney() == null) {
                LOG.info("新增销售申请单，商品规格参数不匹配，类型：【{}】，商品规格类型：【{}】，商品规格编号：【{}】，数量：【{}】，金额：【{}】，优惠金额：【{}】", type, orderGoodsSkuVo.getType(), orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getQuantity(), orderGoodsSkuVo.getMoney(), orderGoodsSkuVo.getDiscountMoney());
                throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
            }
            //设置初始属性
            orderGoodsSkuVo.setStoreId(sellApplyOrderVo.getStoreId());
            orderGoodsSkuVo.setOrderId(sellApplyOrderVo.getId());
            if (type == 1) {
                orderGoodsSkuVo.setFinishQuantity(orderGoodsSkuVo.getQuantity());
                orderGoodsSkuVo.setNotFinishQuantity(0);
                //减库存
                goodsSkuVo.setId(orderGoodsSkuVo.getGoodsSkuId());
                goodsSkuVo.setInventory(orderGoodsSkuVo.getQuantity());
                if (myGoodsMapper.decreaseGoodsSkuInventory(goodsSkuVo) != 1) {
                    LOG.info("新增销售申请单，减库存失败，类型【{}】", type);
                    throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
                }
                //统计成本
                sellResultOrderVo.setCostMoney(new BigDecimal(sellResultOrderVo.getCostMoney().doubleValue() + goodsSkus.stream().filter(goodsSku -> goodsSku.getId().equals(orderGoodsSkuVo.getGoodsSkuId())).findFirst().get().getPurchasePrice().doubleValue() * orderGoodsSkuVo.getQuantity()));
            } else {
                orderGoodsSkuVo.setFinishQuantity(0);
                orderGoodsSkuVo.setNotFinishQuantity(orderGoodsSkuVo.getQuantity());
            }
            //新增
            if (myOrderGoodsSkuMapper.addOrderGoodsSku(orderGoodsSkuVo) != 1) {
                LOG.info("新增销售申请单，插入销售申请单/商品规格关系数据失败，类型【{}】", type);
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }
            //修改采购入库单对应的商品规格已操作数量
            if (type == 3 || (type == 4 && orderGoodsSkuVo.getType() == 1)) {
                if (orderGoodsSkuVo.getId() == null) {
                    throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
                }
                if (myOrderGoodsSkuMapper.updateOperatedQuantity(new OrderGoodsSkuVo(sellApplyOrderVo.getStoreId(), orderGoodsSkuVo.getId(), orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getQuantity())) != 1) {
                    throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
                }
            }
        });
        //新增销售结果单和收款单
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
            sellResultOrderVo.setGrossMarginMoney(new BigDecimal(sellResultOrderVo.getOrderMoney().doubleValue() - sellResultOrderVo.getCostMoney().doubleValue()));
            sellResultOrderVo.setUserId(sellApplyOrderVo.getUserId());
            sellResultOrderVo.setRemark(sellApplyOrderVo.getRemark());
            if (mySellMapper.addResultOrder(sellResultOrderVo) != 1) {
                LOG.info("新增零售单，插入零售结果单失败");
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }

            if (myFundMapper.addFundOrder(new FundOrderVo(
                    storeId,
                    "SKD_" + UUID.randomUUID().toString().replace("-", ""),
                    (byte) 2,
                    new Date(),
                    sellApplyOrderVo.getId(),
                    (byte) 1,
                    sellApplyOrderVo.getBankAccountId(),
                    sellApplyOrderVo.getOrderMoney(),
                    sellApplyOrderVo.getUserId(),
                    sellApplyOrderVo.getRemark()
            )) != 1) {
                LOG.info("新增零售单，插入收款单失败");
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
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
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
            //判断单据状态
            Byte orderStatus = applyOrderVo.getOrderStatus();
            if (orderStatus != 1 && orderStatus != 4 && orderStatus != 7) {     //1：未收，4：未发，7：未收未发
                LOG.info("删除销售申请单，单据状态已不可删除【{}】", orderStatus);
                throw new CommonException(CommonResponse.CODE19, "【 " + applyOrderVo.getId() + "】" + CommonResponse.MESSAGE19);
            }
            //判断结算状态
            Byte clearStatus = applyOrderVo.getClearStatus();
            if (clearStatus != 0) {     //0：未完成
                LOG.info("删除销售申请单，结算状态已不可删除【{}】", clearStatus);
                throw new CommonException(CommonResponse.CODE19, "【 " + applyOrderVo.getId() + "】" + CommonResponse.MESSAGE19);
            }
            //修改商品规格可操作数量
            if (applyOrderVo.getType() == 3 || applyOrderVo.getType() == 4) {
                List<OrderGoodsSkuVo> applyOrderGoodsSkuVos = applyOrderVo.getDetails();
                SellResultOrderVo resultOrderVo = mySellMapper.findResultOrderDetailById(new SellResultOrderVo(sellApplyOrderVo.getStoreId(), applyOrderVo.getResultOrderId()));
                List<OrderGoodsSkuVo> resultOrderGoodsSkuVos = resultOrderVo.getDetails();
                resultOrderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
                    Optional<OrderGoodsSkuVo> optional = applyOrderGoodsSkuVos.stream().filter(vo -> vo.getGoodsSkuId().equals(orderGoodsSkuVo.getGoodsSkuId())).findFirst();
                    if (optional.isPresent()) {
                        int changeQuantity = optional.get().getQuantity();
                        orderGoodsSkuVo.setStoreId(sellApplyOrderVo.getStoreId());
                        orderGoodsSkuVo.setOperatedQuantity(-changeQuantity);
                        if (myOrderGoodsSkuMapper.updateOperatedQuantity(orderGoodsSkuVo) != 1) {
                            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
                        }
                    }
                });
            }
            //删除销售申请订单
            if (mySellMapper.deleteApplyOrder(sellApplyOrderVo) != 1) {
                LOG.info("删除销售申请单，删除数据失败，单据编号：【{}】", sellApplyOrderVo.getId());
                throw new CommonException(CommonResponse.CODE8, "【 " + applyOrderVo.getId() + "】" + CommonResponse.MESSAGE8);
            }
            //删除销售申请订单/商品规格关系
            OrderGoodsSkuVo orderGoodsSkuVo = new OrderGoodsSkuVo(sellApplyOrderVo.getStoreId(), applyOrderVo.getId());
            myOrderGoodsSkuMapper.deleteOrderGoodsSku(orderGoodsSkuVo);
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改销售申请订单
     * @param sellApplyOrderVo
     * @return
     */
    @Transactional
    public CommonResponse updateApplyOrder(SellApplyOrderVo sellApplyOrderVo) {
        //获取销售申请订单
        SellApplyOrder sellApplyOrder = mySellMapper.findApplyOrderById(sellApplyOrderVo);
        if (sellApplyOrder == null) {
            LOG.info("修改销售申请单，单据编号不存在【{}】", sellApplyOrderVo.getId());
            throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
        }
        //判断单据状态
        Byte orderStatus = sellApplyOrder.getOrderStatus();
        if (orderStatus != 1 && orderStatus != 4 && orderStatus != 7) {     //1：未收，4：未发，7：未收未发
            LOG.info("修改销售申请单，单据状态已不可修改【{}】", orderStatus);
            throw new CommonException(CommonResponse.CODE19, "【 " + sellApplyOrder.getId() + "】" + CommonResponse.MESSAGE19);
        }
        //判断结算状态
        Byte clearStatus = sellApplyOrder.getClearStatus();
        if (clearStatus != 0) {     //0：未完成
            LOG.info("修改销售申请单，结算状态已不可修改【{}】", clearStatus);
            throw new CommonException(CommonResponse.CODE19, "【 " + sellApplyOrder.getId() + "】" + CommonResponse.MESSAGE19);
        }
        List<OrderGoodsSkuVo> orderGoodsSkuVos = sellApplyOrderVo.getDetails();
        //判断是销售订单/销售退货申请/销售换货申请
        Byte type = sellApplyOrder.getType();
        switch (type) {
            //销售订单
            case 2:
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getOutWarehouseId() == null || sellApplyOrderVo.getOutTotalQuantity() == null) {
                    LOG.info("修改销售申请单，参数不匹配，类型：【{}】，商品规格数量：【{}】，出库仓库【{}】，出库数量：【{}】", type, orderGoodsSkuVos.size(), sellApplyOrderVo.getOutWarehouseId(), sellApplyOrderVo.getOutTotalQuantity());
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                break;
            //销售退货申请单
            case 3:
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getInWarehouseId() == null || sellApplyOrderVo.getInTotalQuantity() == null ||
                        sellApplyOrderVo.getResultOrderId() == null) {
                    LOG.info("修改销售申请单，参数不匹配，类型：【{}】，商品规格数量：【{}】，入库仓库【{}】，入库数量：【{}】，来源订单【{}】", type, orderGoodsSkuVos.size(), sellApplyOrderVo.getInWarehouseId(), sellApplyOrderVo.getInTotalQuantity(), sellApplyOrderVo.getResultOrderId());
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                break;
            //销售换货申请单
            case 4:
                //判断参数
                if (orderGoodsSkuVos.size() == 0 || sellApplyOrderVo.getInWarehouseId() == null || sellApplyOrderVo.getInTotalQuantity() == null ||
                        sellApplyOrderVo.getOutWarehouseId() == null || sellApplyOrderVo.getOutTotalQuantity() == null ||
                        sellApplyOrderVo.getResultOrderId() == null) {
                    LOG.info("修改销售申请单，参数不匹配，类型：【{}】，商品规格数量：【{}】，入库仓库【{}】，入库数量：【{}】，出库仓库【{}】，出库数量【{}】，来源订单【{}】", type, orderGoodsSkuVos.size(), sellApplyOrderVo.getInWarehouseId(), sellApplyOrderVo.getInTotalQuantity(), sellApplyOrderVo.getOutWarehouseId(), sellApplyOrderVo.getOutTotalQuantity(), sellApplyOrderVo.getResultOrderId());
                    return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
                }
                break;
            default:
                LOG.info("新增销售申请单，参数不匹配，类型：【{}】", type);
                return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改销售申请订单
        if (mySellMapper.updateApplyOrder(sellApplyOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        //删除销售申请订单/商品规格关系
        myOrderGoodsSkuMapper.deleteOrderGoodsSku(new OrderGoodsSkuVo(sellApplyOrderVo.getStoreId(), sellApplyOrderVo.getId()));
        //新增销售申请订单/商品规格关系
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            //判断参数
            if (orderGoodsSkuVo.getType() == null || orderGoodsSkuVo.getGoodsSkuId() == null || orderGoodsSkuVo.getQuantity() == null || orderGoodsSkuVo.getMoney() == null || orderGoodsSkuVo.getDiscountMoney() == null) {
                LOG.info("修改销售申请单，商品规格参数不匹配，类型：【{}】，商品规格类型：【{}】，商品规格编号：【{}】，数量：【{}】，金额：【{}】，优惠金额：【{}】", type, orderGoodsSkuVo.getType(), orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getQuantity(), orderGoodsSkuVo.getMoney(), orderGoodsSkuVo.getDiscountMoney());
                throw new CommonException(CommonResponse.CODE3, CommonResponse.MESSAGE3);
            }
            //设置初始属性
            orderGoodsSkuVo.setStoreId(sellApplyOrderVo.getStoreId());
            orderGoodsSkuVo.setOrderId(sellApplyOrderVo.getId());
            orderGoodsSkuVo.setFinishQuantity(0);
            orderGoodsSkuVo.setNotFinishQuantity(orderGoodsSkuVo.getQuantity());
            //新增
            if (myOrderGoodsSkuMapper.addOrderGoodsSku(orderGoodsSkuVo) != 1) {
                LOG.info("修改销售申请单，插入销售申请单/商品规格关系数据失败，类型【{}】", type);
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改销售申请订单备注
     * @param sellApplyOrderVo
     * @return
     */
    public CommonResponse updateApplyOrderRemark(SellApplyOrderVo sellApplyOrderVo) {
        //修改备注
        if (mySellMapper.updateApplyOrderRemark(sellApplyOrderVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
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
        titles.add(new Title("单据类型", "type"));
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
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据单据编号查询销售申请订单详情
     * @param sellApplyOrderVo
     * @return
     */
    public CommonResponse findApplyOrderDetailById(SellApplyOrderVo sellApplyOrderVo) {
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(sellApplyOrderVo.getStoreId()));
        //根据单据编号查询单据详情
        sellApplyOrderVo = mySellMapper.findApplyOrderDetailById(sellApplyOrderVo);
        if (sellApplyOrderVo == null || sellApplyOrderVo.getDetails().size() == 0) {
            LOG.info("根据单据编号查询销售申请单详情，数据错误，单据编号：【{}】", sellApplyOrderVo.getId());
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        //补上仓库名
        for (Warehouse warehouse : warehouses) {
            if (sellApplyOrderVo.getInWarehouseId() == warehouse.getId()) {
                sellApplyOrderVo.setInWarehouseName(warehouse.getName());
            }
            if (sellApplyOrderVo.getOutWarehouseId() == warehouse.getId()) {
                sellApplyOrderVo.setOutWarehouseName(warehouse.getName());
            }
        }
        return new CommonResponse(CommonResponse.CODE1, sellApplyOrderVo, CommonResponse.MESSAGE1);
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
        //修改单据状态
        if (mySellMapper.redDashed(sellResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        //获取该订单
        sellResultOrderVo = mySellMapper.findResultOrderDetailById(sellResultOrderVo);
        //新增红冲单
        sellResultOrderVo.setStoreId(storeId);
        sellResultOrderVo.setId("HC_" + sellResultOrderVo.getId());
        sellResultOrderVo.setCreateTime(new Date());
        sellResultOrderVo.setOrderStatus((byte) -2);
        sellResultOrderVo.setTotalQuantity(-sellResultOrderVo.getTotalQuantity());
        sellResultOrderVo.setTotalMoney(new BigDecimal(-sellResultOrderVo.getTotalMoney().doubleValue()));
        sellResultOrderVo.setTotalDiscountMoney(new BigDecimal(-sellResultOrderVo.getTotalDiscountMoney().doubleValue()));
        sellResultOrderVo.setOrderMoney(new BigDecimal(-sellResultOrderVo.getOrderMoney().doubleValue()));
        sellResultOrderVo.setCostMoney(new BigDecimal(-sellResultOrderVo.getCostMoney().doubleValue()));
        sellResultOrderVo.setGrossMarginMoney(new BigDecimal(-sellResultOrderVo.getGrossMarginMoney().doubleValue()));
        if (mySellMapper.addResultOrder(sellResultOrderVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        //修改库存
        List<OrderGoodsSkuVo> orderGoodsSkuVos = sellResultOrderVo.getDetails();
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            if (orderGoodsSkuVo.getType() == 0) {        //原来是出库
                //增加商品规格库存
                if (myGoodsMapper.increaseGoodsSkuInventory(new GoodsSkuVo(storeId, orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getQuantity())) != 1) {
                    throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                }
            } else if (orderGoodsSkuVo.getType() == 1) {     //原来是入库
                //减少商品规格库存
                if (myGoodsMapper.decreaseGoodsSkuInventory(new GoodsSkuVo(storeId, orderGoodsSkuVo.getGoodsSkuId(), orderGoodsSkuVo.getQuantity())) != 1) {
                    throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
                }
            } else {
                LOG.info("商品规格类型不正确【{}】", orderGoodsSkuVo.getType());
                throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
            }
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
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
        titles.add(new Title("来源订单单据状态", "sellApplyOrderVo.orderStatus"));
        titles.add(new Title("来源订单结算状态", "sellApplyOrderVo.clearStatus"));
        titles.add(new Title("客户", "sellApplyOrderVo.client.name"));
        titles.add(new Title("电话", "sellApplyOrderVo.client.phone"));
        titles.add(new Title("会员卡号", "sellApplyOrderVo.client.membershipNumber"));
        titles.add(new Title("入库仓库", "sellApplyOrderVo.inWarehouseName"));
        titles.add(new Title("出库仓库", "sellApplyOrderVo.outWarehouseName"));
        titles.add(new Title("总商品数量", "totalQuantity"));
        titles.add(new Title("总订单金额", "totalMoney"));
        titles.add(new Title("总优惠金额", "totalDiscountMoney"));
        titles.add(new Title("本单金额", "orderMoney"));
        titles.add(new Title("成本", "costMoney"));
        titles.add(new Title("毛利", "grossMarginMoney"));
        titles.add(new Title("经手人", "userName"));
        titles.add(new Title("单据备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, sellResultOrderVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据单据编号查询销售结果订单详情
     * @param sellResultOrderVo
     * @return
     */
    public CommonResponse findResultOrderDetailById(SellResultOrderVo sellResultOrderVo) {
        List<Warehouse> warehouses = myWarehouseMapper.findAll(new WarehouseVo(sellResultOrderVo.getStoreId()));
        //根据单据编号查询单据详情
        sellResultOrderVo = mySellMapper.findResultOrderDetailById(sellResultOrderVo);
        if (sellResultOrderVo == null || sellResultOrderVo.getDetails().size() == 0) {
            LOG.info("根据单据编号查询销售结果订单详情，数据错误，单据编号：【{}】", sellResultOrderVo.getId());
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        //补上仓库名
        for (Warehouse warehouse : warehouses) {
            if (sellResultOrderVo.getSellApplyOrderVo().getInWarehouseId() == warehouse.getId()) {
                sellResultOrderVo.getSellApplyOrderVo().setInWarehouseName(warehouse.getName());
            }
            if (sellResultOrderVo.getSellApplyOrderVo().getOutWarehouseId() == warehouse.getId()) {
                sellResultOrderVo.getSellApplyOrderVo().setOutWarehouseName(warehouse.getName());
            }
        }
        return new CommonResponse(CommonResponse.CODE1, sellResultOrderVo, CommonResponse.MESSAGE1);
    }
}
