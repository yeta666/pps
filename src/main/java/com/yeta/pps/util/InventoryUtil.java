package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyGoodsMapper;
import com.yeta.pps.mapper.MyStorageMapper;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
     * 库存期初设置
     * @param vo
     * @return
     */
    @Transactional
    public void addOrUpdateWarehouseGoodsSkuMethod(WarehouseGoodsSkuVo vo) {
        //判断参数
        if (vo.getStoreId() == null || vo.getWarehouseId() == null || vo.getGoodsSkuId() == null ||
                vo.getOpeningQuantity() == null || vo.getOpeningMoney() == null || vo.getOpeningTotalMoney() == null ||
                vo.getOpeningQuantity() * vo.getOpeningMoney() != vo.getOpeningTotalMoney()) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        //判断库存期初是否存在
        WarehouseGoodsSkuVo warehouseGoodsSkuVo = myGoodsMapper.findWarehouseGoodsSku(vo);
        if (warehouseGoodsSkuVo == null) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        //修改库存期初，只有期初都为0的时候才能修改
        if (myGoodsMapper.updateOpening(vo) != 1) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        //创建库存对账记录对象
        StorageCheckOrderVo storageCheckOrderVo = new StorageCheckOrderVo();
        storageCheckOrderVo.setStoreId(vo.getStoreId());
        storageCheckOrderVo.setOrderId("期初调整");
        storageCheckOrderVo.setCreateTime(new Date());
        storageCheckOrderVo.setOrderStatus((byte) 1);
        storageCheckOrderVo.setGoodsSkuId(vo.getGoodsSkuId());
        storageCheckOrderVo.setWarehouseId(vo.getWarehouseId());
        storageCheckOrderVo.setInQuantity(vo.getOpeningQuantity());
        storageCheckOrderVo.setInMoney(vo.getOpeningMoney());
        storageCheckOrderVo.setInTotalMoney(vo.getOpeningTotalMoney());
        storageCheckOrderVo.setUserId(vo.getUserId());

        //查询是否有该商品规格的库存对账记录
        StorageCheckOrderVo sVo = myStorageMapper.findLastCheckMoneyByGoodsSkuId(new StorageCheckOrderVo(vo.getStoreId(), vo.getGoodsSkuId()));

        if (sVo == null) {
            storageCheckOrderVo.setCheckQuantity(vo.getOpeningQuantity());
            storageCheckOrderVo.setCheckMoney(vo.getOpeningMoney());
            storageCheckOrderVo.setCheckTotalMoney(vo.getOpeningTotalMoney());

            storageCheckOrderVo.setCheckQuantity1(vo.getOpeningQuantity());
            storageCheckOrderVo.setCheckMoney1(vo.getOpeningMoney());
            storageCheckOrderVo.setCheckTotalMoney1(vo.getOpeningTotalMoney());
        } else {
            storageCheckOrderVo.setCheckQuantity(vo.getOpeningQuantity());
            storageCheckOrderVo.setCheckMoney(vo.getOpeningMoney());
            storageCheckOrderVo.setCheckTotalMoney(vo.getOpeningTotalMoney());

            int changeQuantity1 = vo.getOpeningQuantity() + sVo.getCheckQuantity1();
            double changeTotalMoney1 = vo.getOpeningTotalMoney() + sVo.getCheckTotalMoney1();
            double changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);
            storageCheckOrderVo.setCheckQuantity1(changeQuantity1);
            storageCheckOrderVo.setCheckMoney1(changeMoney1);
            storageCheckOrderVo.setCheckTotalMoney1(changeTotalMoney1);
        }

        if (myStorageMapper.addStorageCheckOrder(storageCheckOrderVo) != 1) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
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
        //查询该商品规格的最新库存对账记录
        StorageCheckOrderVo sVo = myStorageMapper.findLastCheckMoneyByGoodsSkuIdAndWarehouseId(new StorageCheckOrderVo(vo.getStoreId(), vo.getGoodsSkuId(), vo.getWarehouseId()));
        StorageCheckOrderVo sVo1 = myStorageMapper.findLastCheckMoneyByGoodsSkuId(new StorageCheckOrderVo(vo.getStoreId(), vo.getGoodsSkuId()));
        if (sVo == null || sVo1 == null) {
            throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        //验证结存是否正确
        if (orderGoodsSkuVo != null) {
            if (orderGoodsSkuVo.getCheckQuantity() != sVo.getCheckQuantity() || orderGoodsSkuVo.getCheckMoney().doubleValue() != sVo.getCheckMoney().doubleValue() || orderGoodsSkuVo.getCheckTotalMoney().doubleValue() != sVo.getCheckTotalMoney().doubleValue()) {
                throw new CommonException(CommonResponse.INVENTORY_ERROR);
            }
        }

        int changeQuantity;
        double changeTotalMoney;
        double changeMoney;

        int changeQuantity1;
        double changeTotalMoney1;
        double changeMoney1;
        switch (flag) {
            case 0:     //成本不变
                changeQuantity = sVo.getCheckQuantity() - vo.getOutQuantity();
                changeTotalMoney = sVo.getCheckTotalMoney() - getNumberMethod(vo.getOutQuantity() * sVo.getCheckMoney());
                changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);

                changeQuantity1 = sVo1.getCheckQuantity1() - vo.getOutQuantity();
                changeTotalMoney1 = sVo1.getCheckTotalMoney1() - getNumberMethod(vo.getOutQuantity() * sVo1.getCheckMoney1());
                changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);

                vo.setOutMoney(sVo.getCheckMoney());
                vo.setOutTotalMoney(getNumberMethod(vo.getOutQuantity() * sVo.getCheckMoney()));
                break;

            case 1:     //成本可能变
                changeQuantity = vo.getInQuantity() + sVo.getCheckQuantity();
                changeTotalMoney = getNumberMethod(vo.getInTotalMoney()) + sVo.getCheckTotalMoney();
                changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);

                changeQuantity1 = vo.getInQuantity() + sVo1.getCheckQuantity1();
                changeTotalMoney1 = getNumberMethod(vo.getInTotalMoney()) + sVo1.getCheckTotalMoney1();
                changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);
                break;

            case 2:     //成本不变
                changeQuantity = sVo.getCheckQuantity() + vo.getInQuantity();
                changeTotalMoney = sVo.getCheckTotalMoney() + getNumberMethod(vo.getInQuantity() * sVo.getCheckMoney());
                changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);

                changeQuantity1 = sVo1.getCheckQuantity1() + vo.getInQuantity();
                changeTotalMoney1 = sVo1.getCheckTotalMoney1() + getNumberMethod(vo.getInQuantity() * sVo1.getCheckMoney1());
                changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);

                vo.setInMoney(sVo.getCheckMoney());
                vo.setInTotalMoney(getNumberMethod(vo.getInQuantity() * sVo.getCheckMoney()));
                break;

            case 3:
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
                break;

            default:
                throw new CommonException(CommonResponse.INVENTORY_ERROR);
        }

        vo.setCheckQuantity(changeQuantity);
        vo.setCheckMoney(changeMoney);
        vo.setCheckTotalMoney(changeTotalMoney);

        vo.setCheckQuantity1(changeQuantity1);
        vo.setCheckMoney1(changeMoney1);
        vo.setCheckTotalMoney1(changeTotalMoney1);

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

        int changeQuantity;
        double changeMoney;
        double changeTotalMoney;

        int changeQuantity1;
        double changeMoney1;
        double changeTotalMoney1;
        switch (flag) {
            case 0:
                //红冲
                if (myStorageMapper.redDashedOutStorageCheckOrder(vo) != 1) {
                    throw new CommonException(CommonResponse.INVENTORY_ERROR);
                }

                //获取红冲蓝单
                vo = myStorageMapper.findOutStorageCheckOrder(vo);

                changeQuantity = lastVo.getCheckQuantity() + vo.getOutQuantity();
                changeTotalMoney = lastVo.getCheckTotalMoney() + vo.getOutTotalMoney();
                changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);

                changeQuantity1 = lastVo1.getCheckQuantity1() + vo.getOutQuantity();
                changeTotalMoney1 = lastVo1.getCheckTotalMoney1() + vo.getOutTotalMoney();
                changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);

                vo.setOutQuantity(-vo.getOutQuantity());
                vo.setOutMoney(-vo.getOutMoney());
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
                changeTotalMoney = lastVo.getCheckTotalMoney() - vo.getInTotalMoney();
                changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);

                changeQuantity1 = lastVo1.getCheckQuantity1() - vo.getInQuantity();
                changeTotalMoney1 = lastVo1.getCheckTotalMoney1() - vo.getInTotalMoney();
                changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);

                vo.setInQuantity(-vo.getInQuantity());
                vo.setInMoney(-vo.getInMoney());
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
                changeTotalMoney = lastVo.getCheckTotalMoney() - vo.getOutTotalMoney();
                changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);

                changeQuantity1 = lastVo1.getCheckQuantity1();
                changeTotalMoney1 = lastVo1.getCheckTotalMoney1() - vo.getOutTotalMoney();
                changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);
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
                changeTotalMoney = lastVo.getCheckTotalMoney() + vo.getInTotalMoney();
                changeMoney = getNumberMethod(changeTotalMoney / changeQuantity);

                changeQuantity1 = lastVo1.getCheckQuantity1();
                changeTotalMoney1 = lastVo1.getCheckTotalMoney1() + vo.getInTotalMoney();
                changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);
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
