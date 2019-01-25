package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyGoodsMapper;
import com.yeta.pps.mapper.MyStorageMapper;
import com.yeta.pps.po.GoodsSku;
import com.yeta.pps.vo.GoodsSkuVo;
import com.yeta.pps.vo.OrderGoodsSkuVo;
import com.yeta.pps.vo.StorageCheckOrderVo;
import com.yeta.pps.vo.WarehouseGoodsSkuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author YETA
 * @date 2018/12/22/14:55
 */
@Component
public class InventoryUtil {

    @Autowired
    private MyGoodsMapper myGoodsMapper;

    @Autowired
    private MyStorageMapper myStorageMapper;

    /**
     * 返回四舍五入2位小数的方法
     * @param number
     * @return
     */
    public double getNumberMethod(double number) {
        return (double) Math.round(number * 100) / 100;
    }

    /**
     * 判断商品规格是否能出库的方法
     * @param storeId
     * @param goodsSkuId
     * @param warehouseId
     * @return
     */
    public boolean judgeCanOutMethod(Integer storeId, Integer goodsSkuId, Integer warehouseId) {
        //查询最新库存对账记录
        StorageCheckOrderVo sVo = myStorageMapper.findLastCheckMoneyByGoodsSkuIdAndWarehouseId(new StorageCheckOrderVo(storeId, goodsSkuId, warehouseId));
        if (sVo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 新增库存对账记录的方法
     * @param flag 0：出库，1：入库，2：销售退换货入库，3：成本调价单
     * @param vo
     * @return
     */
    @Transactional
    public double addStorageCheckOrderMethod(int flag, StorageCheckOrderVo vo, OrderGoodsSkuVo orderGoodsSkuVo) {
        //查询最新库存对账记录
        StorageCheckOrderVo sVo = myStorageMapper.findLastCheckMoneyByGoodsSkuIdAndWarehouseId(new StorageCheckOrderVo(vo.getStoreId(), vo.getGoodsSkuId(), vo.getWarehouseId()));
        StorageCheckOrderVo sVo1 = myStorageMapper.findLastCheckMoneyByGoodsSkuId(new StorageCheckOrderVo(vo.getStoreId(), vo.getGoodsSkuId()));
        GoodsSku goodsSku = myGoodsMapper.findGoodsSkuById(new GoodsSkuVo(vo.getStoreId(), vo.getGoodsSkuId()));
        if (goodsSku == null) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }
        StorageCheckOrderVo sVo2 = myStorageMapper.findLastCheckMoneyByGoodsId(new StorageCheckOrderVo(vo.getStoreId(), goodsSku.getGoodsId()));
        if (flag != 1 && (sVo == null || sVo1 == null || sVo2 == null)) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        //验证结存是否正确
        if (orderGoodsSkuVo != null) {
            if (orderGoodsSkuVo.getCheckQuantity().intValue() != sVo.getCheckQuantity().intValue() || orderGoodsSkuVo.getCheckMoney().doubleValue() != sVo.getCheckMoney().doubleValue() || orderGoodsSkuVo.getCheckTotalMoney().doubleValue() != sVo.getCheckTotalMoney().doubleValue()) {
                throw new CommonException(CommonResponse.INVENTORY_ERROR);
            }
        }

        int changeQuantity;
        double changeTotalMoney;
        double changeMoney;

        int changeQuantity1;
        double changeTotalMoney1;
        double changeMoney1;

        int changeQuantity2;
        double changeTotalMoney2;
        double changeMoney2;
        switch (flag) {
            case 0:     //成本不变
                changeQuantity = sVo.getCheckQuantity() - vo.getOutQuantity();
                changeTotalMoney = getNumberMethod(sVo.getCheckTotalMoney() - vo.getOutQuantity() * sVo.getCheckMoney());
                if (changeQuantity != 0) {
                    changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);
                } else {
                    changeMoney = sVo.getCheckMoney();
                }

                changeQuantity1 = sVo1.getCheckQuantity1() - vo.getOutQuantity();
                changeTotalMoney1 = getNumberMethod(sVo1.getCheckTotalMoney1() - vo.getOutQuantity() * sVo.getCheckMoney());
                if (changeQuantity1 != 0) {
                    changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);
                } else {
                    changeMoney1 = sVo1.getCheckMoney1();
                }

                changeQuantity2 = sVo2.getCheckQuantity2() - vo.getOutQuantity();
                changeTotalMoney2 = getNumberMethod(sVo2.getCheckTotalMoney2() - vo.getOutQuantity() * sVo.getCheckMoney());
                if (changeQuantity2 != 0) {
                    changeMoney2 = getNumberMethod(changeTotalMoney2 / changeQuantity2);
                } else {
                    changeMoney2 = sVo2.getCheckMoney2();
                }

                vo.setOutMoney(sVo.getCheckMoney());
                vo.setOutTotalMoney(getNumberMethod(vo.getOutQuantity() * sVo.getCheckMoney()));
                break;

            case 1:     //成本可能变
                if (sVo == null) {
                    changeQuantity = vo.getInQuantity();
                    changeMoney = vo.getInMoney();
                    changeTotalMoney = vo.getInTotalMoney();
                } else {
                    changeQuantity = vo.getInQuantity() + sVo.getCheckQuantity();
                    changeTotalMoney = getNumberMethod(vo.getInTotalMoney() + sVo.getCheckTotalMoney());
                    if (changeQuantity != 0) {
                        changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);
                    } else {
                        changeMoney = sVo.getCheckMoney();
                    }
                }

                if (sVo1 == null) {
                    changeQuantity1 = vo.getInQuantity();
                    changeMoney1 = vo.getInMoney();
                    changeTotalMoney1 = vo.getInTotalMoney();
                } else {
                    changeQuantity1 = vo.getInQuantity() + sVo1.getCheckQuantity1();
                    changeTotalMoney1 = getNumberMethod(vo.getInTotalMoney() + sVo1.getCheckTotalMoney1());
                    if (changeQuantity1 != 0) {
                        changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);
                    } else {
                        changeMoney1 = sVo1.getCheckMoney1();
                    }
                }

                if (sVo2 == null) {
                    changeQuantity2 = vo.getInQuantity();
                    changeMoney2 = vo.getInMoney();
                    changeTotalMoney2 = vo.getInTotalMoney();
                } else {
                    changeQuantity2 = vo.getInQuantity() + sVo2.getCheckQuantity2();
                    changeTotalMoney2 = getNumberMethod(vo.getInTotalMoney() + sVo2.getCheckTotalMoney2());
                    if (changeQuantity2 != 0) {
                        changeMoney2 = getNumberMethod(changeTotalMoney2 / changeQuantity2);
                    } else {
                        changeMoney2 = sVo2.getCheckMoney2();
                    }
                }
                break;

            case 2:     //成本不变
                changeQuantity = sVo.getCheckQuantity() + vo.getInQuantity();
                changeTotalMoney = getNumberMethod(sVo.getCheckTotalMoney() + vo.getInQuantity() * sVo.getCheckMoney());
                if (changeQuantity != 0) {
                    changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);
                } else {
                    changeMoney = sVo.getCheckMoney();
                }

                changeQuantity1 = sVo1.getCheckQuantity1() + vo.getInQuantity();
                changeTotalMoney1 = getNumberMethod(sVo1.getCheckTotalMoney1() + vo.getInQuantity() * sVo1.getCheckMoney1());
                if (changeQuantity1 != 0) {
                    changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);
                } else {
                    changeMoney1 = sVo1.getCheckMoney1();
                }

                changeQuantity2 = sVo2.getCheckQuantity2() + vo.getInQuantity();
                changeTotalMoney2 = getNumberMethod(sVo2.getCheckTotalMoney2() + vo.getInQuantity() * sVo2.getCheckMoney2());
                if (changeQuantity2 != 0) {
                    changeMoney2 = getNumberMethod(changeTotalMoney2 / changeQuantity2);
                } else {
                    changeMoney2 = sVo2.getCheckMoney2();
                }

                vo.setInMoney(sVo.getCheckMoney());
                vo.setInTotalMoney(getNumberMethod(vo.getInQuantity() * sVo.getCheckMoney()));
                break;

            case 3:
                if (sVo.getCheckQuantity() == 0 && sVo.getCheckTotalMoney() == 0) {
                    throw new CommonException(CommonResponse.INVENTORY_ERROR, "结存数量、结存成本金额都为0的时候不能调价");
                }

                //获取参数
                int checkQuantity = orderGoodsSkuVo.getCheckQuantity();     //库存数量
                double afterChangeCheckMoney = orderGoodsSkuVo.getAfterChangeCheckMoney();       //调价后成本价
                double checkTotalMoney = orderGoodsSkuVo.getCheckTotalMoney();
                double changeCheckTotalMoney = orderGoodsSkuVo.getChangeCheckTotalMoney();
                double check = getNumberMethod(getNumberMethod(checkQuantity * afterChangeCheckMoney) - checkTotalMoney);

                //判断参数
                if (check != changeCheckTotalMoney || changeCheckTotalMoney == 0) {
                    throw new CommonException(CommonResponse.INVENTORY_ERROR);
                }

                changeQuantity = checkQuantity;
                changeMoney = afterChangeCheckMoney;
                changeTotalMoney = getNumberMethod(changeQuantity * changeMoney);

                changeQuantity1 = sVo1.getCheckQuantity1();
                changeMoney1 = getNumberMethod((sVo1.getCheckTotalMoney1() - checkTotalMoney + changeTotalMoney) / changeQuantity1);
                changeTotalMoney1 = getNumberMethod(changeQuantity1 * changeMoney1);

                changeQuantity2 = sVo2.getCheckQuantity2();
                changeMoney2 = getNumberMethod((sVo2.getCheckTotalMoney2() - checkTotalMoney + changeTotalMoney) / changeQuantity2);
                changeTotalMoney2 = getNumberMethod(changeQuantity2 * changeMoney2);
                break;

            default:
                throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        vo.setGoodsId(goodsSku.getGoodsId());

        vo.setCheckQuantity(changeQuantity);
        vo.setCheckMoney(new BigDecimal(changeMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        vo.setCheckTotalMoney(new BigDecimal(changeTotalMoney).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

        vo.setCheckQuantity1(changeQuantity1);
        vo.setCheckMoney1(new BigDecimal(changeMoney1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        vo.setCheckTotalMoney1(new BigDecimal(changeTotalMoney1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

        vo.setCheckQuantity2(changeQuantity2);
        vo.setCheckMoney2(new BigDecimal(changeMoney2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        vo.setCheckTotalMoney2(new BigDecimal(changeTotalMoney2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

        if (myStorageMapper.addStorageCheckOrder(vo) != 1) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        return changeMoney;
    }

    /**
     * 红冲库存记账记录
     * @param flag 0：原来是出库，1：原来是入库，2：原来是成本调价单入库，3：原来是成本调价单出库
     * @param vo
     */
    @Transactional
    public void redDashedStorageCheckOrderMethod(int flag, StorageCheckOrderVo vo) {
        //判断参数
        if (vo.getStoreId() == null || vo.getOrderId() == null || vo.getGoodsSkuId() == null) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        //获取参数
        Integer storeId = vo.getStoreId();
        String userId = vo.getUserId();

        //查询最新成本
        StorageCheckOrderVo lastVo = myStorageMapper.findLastCheckMoneyByGoodsSkuIdAndWarehouseId(vo);
        StorageCheckOrderVo lastVo1 = myStorageMapper.findLastCheckMoneyByGoodsSkuId(vo);
        GoodsSku goodsSku = myGoodsMapper.findGoodsSkuById(new GoodsSkuVo(vo.getStoreId(), vo.getGoodsSkuId()));
        if (goodsSku == null) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }
        StorageCheckOrderVo lastVo2 = myStorageMapper.findLastCheckMoneyByGoodsId(new StorageCheckOrderVo(vo.getStoreId(), goodsSku.getGoodsId()));
        if (lastVo == null || lastVo1 == null || lastVo2 == null) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        int changeQuantity;
        double changeMoney;
        double changeTotalMoney;

        int changeQuantity1;
        double changeMoney1;
        double changeTotalMoney1;

        int changeQuantity2;
        double changeMoney2;
        double changeTotalMoney2;
        switch (flag) {
            case 0:
                //红冲
                if (myStorageMapper.redDashedOutStorageCheckOrder(vo) != 1) {
                    throw new CommonException(CommonResponse.INVENTORY_ERROR);
                }

                //获取红冲蓝单
                vo = myStorageMapper.findOutStorageCheckOrder(vo);

                changeQuantity = lastVo.getCheckQuantity() + vo.getOutQuantity();
                changeTotalMoney = getNumberMethod(lastVo.getCheckTotalMoney() + vo.getOutTotalMoney());
                if (changeQuantity != 0) {
                    changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);
                } else {
                    changeMoney = lastVo.getCheckMoney();
                }

                changeQuantity1 = lastVo1.getCheckQuantity1() + vo.getOutQuantity();
                changeTotalMoney1 = getNumberMethod(lastVo1.getCheckTotalMoney1() + vo.getOutTotalMoney());
                if (changeQuantity1 != 0) {
                    changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);
                } else {
                    changeMoney1 = lastVo1.getCheckMoney1();
                }

                changeQuantity2 = lastVo2.getCheckQuantity2() + vo.getOutQuantity();
                changeTotalMoney2 = getNumberMethod(lastVo2.getCheckTotalMoney2() + vo.getOutTotalMoney());
                if (changeQuantity2 != 0) {
                    changeMoney2 = getNumberMethod(changeTotalMoney2 / changeQuantity2);
                } else {
                    changeMoney2 = lastVo2.getCheckMoney2();
                }

                vo.setOutQuantity(-vo.getOutQuantity());
                vo.setOutTotalMoney(-vo.getOutTotalMoney());
                break;

            case 1:
                //红冲
                if (myStorageMapper.redDashedInStorageCheckOrder(vo) != 1) {
                    throw new CommonException(CommonResponse.INVENTORY_ERROR);
                }

                //获取红冲蓝单
                vo = myStorageMapper.findInStorageCheckOrder(vo);

                changeQuantity = lastVo.getCheckQuantity() - vo.getInQuantity();
                changeTotalMoney = getNumberMethod(lastVo.getCheckTotalMoney() - vo.getInTotalMoney());
                if (changeQuantity != 0) {
                    changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);
                } else {
                    changeMoney = lastVo.getCheckMoney();
                }

                changeQuantity1 = lastVo1.getCheckQuantity1() - vo.getInQuantity();
                changeTotalMoney1 = getNumberMethod(lastVo1.getCheckTotalMoney1() - vo.getInTotalMoney());
                if (changeQuantity1 != 0) {
                    changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);
                } else {
                    changeMoney1 = lastVo1.getCheckMoney1();
                }

                changeQuantity2 = lastVo2.getCheckQuantity2() - vo.getInQuantity();
                changeTotalMoney2 = getNumberMethod(lastVo2.getCheckTotalMoney2() - vo.getInTotalMoney());
                if (changeQuantity2 != 0) {
                    changeMoney2 = getNumberMethod(changeTotalMoney2 / changeQuantity2);
                } else {
                    changeMoney2 = lastVo2.getCheckMoney2();
                }

                vo.setInQuantity(-vo.getInQuantity());
                vo.setInTotalMoney(-vo.getInTotalMoney());
                break;

            case 2:
                //红冲
                if (myStorageMapper.redDashedInStorageCheckOrder(vo) != 1) {
                    throw new CommonException(CommonResponse.INVENTORY_ERROR);
                }

                //获取红冲蓝单
                vo = myStorageMapper.findInStorageCheckOrder(vo);

                vo.setOutTotalMoney(vo.getInTotalMoney());
                vo.setInTotalMoney(0.0);

                changeQuantity = lastVo.getCheckQuantity();
                changeTotalMoney = getNumberMethod(lastVo.getCheckTotalMoney() - vo.getOutTotalMoney());
                changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);

                changeQuantity1 = lastVo1.getCheckQuantity1();
                changeTotalMoney1 = getNumberMethod(lastVo1.getCheckTotalMoney1() - vo.getOutTotalMoney());
                changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);

                changeQuantity2 = lastVo2.getCheckQuantity2();
                changeTotalMoney2 = getNumberMethod(lastVo2.getCheckTotalMoney2() - vo.getOutTotalMoney());
                changeMoney2 = getNumberMethod(changeTotalMoney2 / changeQuantity2);
                break;

            case 3:
                //红冲
                if (myStorageMapper.redDashedOutStorageCheckOrder(vo) != 1) {
                    throw new CommonException(CommonResponse.INVENTORY_ERROR);
                }

                //获取红冲蓝单
                vo = myStorageMapper.findOutStorageCheckOrder(vo);

                vo.setInTotalMoney(vo.getOutTotalMoney());
                vo.setOutTotalMoney(0.0);

                changeQuantity = lastVo.getCheckQuantity();
                changeTotalMoney = getNumberMethod(lastVo.getCheckTotalMoney() + vo.getInTotalMoney());
                changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);

                changeQuantity1 = lastVo1.getCheckQuantity1();
                changeTotalMoney1 = getNumberMethod(lastVo1.getCheckTotalMoney1() + vo.getInTotalMoney());
                changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);

                changeQuantity2 = lastVo2.getCheckQuantity2();
                changeTotalMoney2 = getNumberMethod(lastVo2.getCheckTotalMoney2() + vo.getInTotalMoney());
                changeMoney2 = getNumberMethod(changeTotalMoney2 / changeQuantity2);
                break;

            default:
                throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        //设置红冲红单
        vo.setStoreId(storeId);
        vo.setCreateTime(new Date());
        vo.setOrderStatus((byte) -2);
        vo.setUserId(userId);
        vo.setCheckQuantity(changeQuantity);
        vo.setCheckMoney(changeMoney);
        vo.setCheckTotalMoney(changeTotalMoney);

        vo.setCheckQuantity1(changeQuantity1);
        vo.setCheckMoney1(changeMoney1);
        vo.setCheckTotalMoney1(changeTotalMoney1);

        vo.setCheckQuantity2(changeQuantity2);
        vo.setCheckMoney2(changeMoney2);
        vo.setCheckTotalMoney2(changeTotalMoney2);

        //新增红冲红单
        if (myStorageMapper.addStorageCheckOrder(vo) != 1) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }
    }

    /**
     * 修改库存的方法
     * @param flag 1：增，0：减
     * @param vo
     */
    @Transactional
    public void updateInventoryMethod(int flag, WarehouseGoodsSkuVo vo) {
        //判断参数
        if (vo.getStoreId() == null || vo.getWarehouseId() == null || vo.getGoodsSkuId() == null ||
                vo.getRealInventory() == null || vo.getCanUseInventory() == null || vo.getBookInventory() == null) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        switch (flag) {
            case 1:
                if (myGoodsMapper.increaseInventory(vo) != 1) {
                    throw new CommonException(CommonResponse.INVENTORY_ERROR);
                }
                break;

            case 0:
                if (myGoodsMapper.decreaseInventory(vo) != 1) {
                    throw new CommonException(CommonResponse.INVENTORY_ERROR);
                }
                break;

            default:
                throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }
    }

    /**
     * 修改库存待发货数量或待收货数量的方法
     * @param flag 1：增，0：减
     * @param vo
     */
    @Transactional
    public void updateNotQuantityMethod(int flag, WarehouseGoodsSkuVo vo) {
        //判断参数
        if (vo.getStoreId() == null || vo.getWarehouseId() == null || vo.getGoodsSkuId() == null ||
                vo.getNotSentQuantity() == null || vo.getNotReceivedQuantity() == null) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        switch (flag) {
            case 1:
                if (myGoodsMapper.increaseNotQuantity(vo) != 1) {
                    throw new CommonException(CommonResponse.INVENTORY_ERROR);
                }
                break;

            case 0:
                if (myGoodsMapper.decreaseNotQuantity(vo) != 1) {
                    throw new CommonException(CommonResponse.INVENTORY_ERROR);
                }
                break;

            default:
                throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }
    }

    /**
     * 修改库存上限或下限的方法
     * @param vo
     */
    @Transactional
    public void updateLimitInventoryMethod( WarehouseGoodsSkuVo vo) {
        //判断参数
        if (vo.getStoreId() == null || vo.getWarehouseId() == null || vo.getGoodsSkuId() == null) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        if (myGoodsMapper.updateLimitInventoryMethod(vo) != 1) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }
    }
}
