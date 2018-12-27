package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyGoodsMapper;
import com.yeta.pps.mapper.MyStorageMapper;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
     * 库存期初设置
     * @param vo
     * @return
     */
    @Transactional
    public void addOrUpdateWarehouseGoodsSku(WarehouseGoodsSkuVo vo) {
        //判断参数
        if (vo.getStoreId() == null || vo.getWarehouseId() == null || vo.getGoodsSkuId() == null ||
                vo.getOpeningQuantity() == null || vo.getOpeningMoney() == null || vo.getOpeningTotalMoney() == null ||
                vo.getOpeningQuantity() * vo.getOpeningMoney().doubleValue() != vo.getOpeningTotalMoney().doubleValue()) {
            throw new CommonException("设置库存期初失败");
        }

        //判断库存期初是否存在
        WarehouseGoodsSkuVo warehouseGoodsSkuVo = myGoodsMapper.findWarehouseGoodsSku(vo);
        if (warehouseGoodsSkuVo == null) {
            throw new CommonException("设置库存期初失败");
        }

        //修改库存期初
        if (myGoodsMapper.updateOpening(vo) != 1) {
            throw new CommonException("设置库存期初失败");
        }

        //创建库存对账记录对象
        StorageCheckOrderVo storageCheckOrderVo = new StorageCheckOrderVo();
        storageCheckOrderVo.setStoreId(vo.getStoreId());
        storageCheckOrderVo.setOrderId("期初调整");
        storageCheckOrderVo.setCreateTime(new Date());
        storageCheckOrderVo.setOrderStatus((byte) 1);
        storageCheckOrderVo.setGoodsSkuId(vo.getGoodsSkuId());
        storageCheckOrderVo.setInWarehouseId(vo.getWarehouseId());
        storageCheckOrderVo.setInQuantity(vo.getOpeningQuantity());
        storageCheckOrderVo.setInMoney(vo.getOpeningMoney());
        storageCheckOrderVo.setInTotalMoney(warehouseGoodsSkuVo.getOpeningTotalMoney());
        storageCheckOrderVo.setUserId(vo.getUserId());

        //查询是否有该商品规格的库存对账记录
        StorageCheckOrderVo sVo = myStorageMapper.findLastCheckMoney(new StorageCheckOrderVo(vo.getStoreId(), vo.getGoodsSkuId()));
        if (sVo == null) {
            storageCheckOrderVo.setCheckQuantity(vo.getOpeningQuantity());
            storageCheckOrderVo.setCheckMoney(vo.getOpeningMoney());
            storageCheckOrderVo.setCheckTotalMoney(vo.getOpeningTotalMoney());
        } else {
            int changeQuantity = vo.getOpeningQuantity() + sVo.getCheckQuantity();
            double changeMoney = (vo.getOpeningTotalMoney().doubleValue() + sVo.getCheckTotalMoney().doubleValue()) / changeQuantity;
            double changeTotalMoney = changeQuantity * changeMoney;

            storageCheckOrderVo.setCheckQuantity(changeQuantity);
            storageCheckOrderVo.setCheckMoney(new BigDecimal(changeMoney));
            storageCheckOrderVo.setCheckTotalMoney(new BigDecimal(changeTotalMoney));
        }

        if (myStorageMapper.addStorageCheckOrder(storageCheckOrderVo) != 1) {
            throw new CommonException("库存记账失败");
        }
    }

    /**
     * 新增库存对账记录的方法
     * @param flag 1：入库，0：出库，3：销售退换货入库，4：成本调价单
     * @param vo
     * @return
     */
    @Transactional
    public double addStorageCheckOrder(int flag, StorageCheckOrderVo vo, StorageResultOrderVo rVo) {
        //查询该商品规格的库存对账记录
        StorageCheckOrderVo sVo = myStorageMapper.findLastCheckMoney(new StorageCheckOrderVo(vo.getStoreId(), vo.getGoodsSkuId()));
        if (sVo == null) {
            throw new CommonException("库存记账失败");
        }

        int changeQuantity;
        double changeTotalMoney;
        double changeMoney;
        switch (flag) {
            case 1:     //成本可能变
                changeQuantity = vo.getInQuantity() + sVo.getCheckQuantity();
                changeMoney = (vo.getInTotalMoney().doubleValue() + sVo.getCheckTotalMoney().doubleValue()) / changeQuantity;
                break;

            case 0:     //成本不变
                changeQuantity = sVo.getCheckQuantity() - vo.getOutQuantity();
                changeMoney = sVo.getCheckMoney().doubleValue();
                vo.setOutMoney(sVo.getCheckMoney());
                vo.setOutTotalMoney(new BigDecimal(vo.getOutQuantity() * sVo.getCheckMoney().doubleValue()));
                break;

            case 3:     //成本不变
                changeQuantity = sVo.getCheckQuantity() + vo.getInQuantity();
                changeMoney = sVo.getCheckMoney().doubleValue();
                vo.setInMoney(sVo.getCheckMoney());
                vo.setInTotalMoney(new BigDecimal(vo.getInQuantity() * sVo.getCheckMoney().doubleValue()));
                break;

            case 4:
                //获取参数
                int checkQuantity = rVo.getCheckQuantity();
                double checkMoney = rVo.getCheckMoney().doubleValue();
                double checkTotalMoney = rVo.getCheckTotalMoney().doubleValue();
                double afterChangeCheckMoney = rVo.getAfterChangeCheckMoney().doubleValue();
                double changeCheckTotalMoney = rVo.getChangeCheckTotalMoney().doubleValue();
                double changeCheckTotalMoney1 = (double) Math.round((checkQuantity * afterChangeCheckMoney - checkTotalMoney) * 100) / 100;

                //判断参数
                if (checkQuantity != sVo.getCheckQuantity() || checkMoney != sVo.getCheckMoney().doubleValue() || checkTotalMoney != sVo.getCheckTotalMoney().doubleValue() ||
                        changeCheckTotalMoney1 != changeCheckTotalMoney || changeCheckTotalMoney == 0) {
                    throw new CommonException("库存记账失败");
                }

                changeQuantity = checkQuantity;
                changeMoney = afterChangeCheckMoney;

                if (changeCheckTotalMoney > 0) {
                    vo.setInWarehouseId(rVo.getWarehouseId());
                    vo.setInTotalMoney(new BigDecimal(changeCheckTotalMoney));
                } else {
                    vo.setOutWarehouseId(rVo.getWarehouseId());
                    vo.setOutTotalMoney(new BigDecimal(-changeCheckTotalMoney));
                }
                break;

            default:
                throw new CommonException("库存记账失败");
        }

        changeTotalMoney = changeQuantity * changeMoney;
        vo.setCheckQuantity(changeQuantity);
        vo.setCheckMoney(new BigDecimal(changeMoney));
        vo.setCheckTotalMoney(new BigDecimal(changeTotalMoney));

        if (myStorageMapper.addStorageCheckOrder(vo) != 1) {
            throw new CommonException("库存记账失败");
        }

        return changeMoney;
    }

    /**
     * 红冲库存记账记录
     * @param flag 1：原来是入库，0：原来是出库，4：原来是成本调价单入库，4：原来是成本调价单出库
     * @param vo
     */
    @Transactional
    public void redDashedStorageCheckOrder(int flag, StorageCheckOrderVo vo) {
        //判断参数
        if (vo.getStoreId() == null || vo.getOrderId() == null || vo.getGoodsSkuId() == null) {
            throw new CommonException("红冲库存记账记录失败");
        }

        Integer storeId = vo.getStoreId();
        String userId = vo.getUserId();

        //查询最新成本
        StorageCheckOrderVo lastVo = myStorageMapper.findLastCheckMoney(vo);

        int changeQuantity;
        double changeMoney;
        switch (flag) {
            case 1:
                //红冲
                if (myStorageMapper.redDashedInStorageCheckOrder(vo) != 1) {
                    throw new CommonException("红冲库存记账记录失败");
                }

                //获取红冲蓝单
                vo = myStorageMapper.findInStorageCheckOrder(vo);

                changeQuantity = lastVo.getCheckQuantity() - vo.getInQuantity();
                changeMoney = (lastVo.getCheckTotalMoney().doubleValue() - vo.getInTotalMoney().doubleValue()) / changeQuantity;
                vo.setInQuantity(-vo.getInQuantity());
                vo.setInMoney(new BigDecimal(-vo.getInMoney().doubleValue()));
                vo.setInTotalMoney(new BigDecimal(-vo.getInTotalMoney().doubleValue()));
                break;

            case 0:
                //红冲
                if (myStorageMapper.redDashedOutStorageCheckOrder(vo) != 1) {
                    throw new CommonException("红冲库存记账记录失败");
                }

                //获取红冲蓝单
                vo = myStorageMapper.findOutStorageCheckOrder(vo);

                changeQuantity = lastVo.getCheckQuantity() + vo.getOutQuantity();
                changeMoney = (lastVo.getCheckTotalMoney().doubleValue() + vo.getOutTotalMoney().doubleValue()) / changeQuantity;
                vo.setOutQuantity(-vo.getOutQuantity());
                vo.setOutMoney(new BigDecimal(-vo.getOutMoney().doubleValue()));
                vo.setOutTotalMoney(new BigDecimal(-vo.getOutTotalMoney().doubleValue()));
                break;

            case 4:
                //红冲
                if (myStorageMapper.redDashedInStorageCheckOrder(vo) != 1) {
                    throw new CommonException("红冲库存记账记录失败");
                }

                //获取红冲蓝单
                vo = myStorageMapper.findInStorageCheckOrder(vo);

                vo.setOutWarehouseId(vo.getInWarehouseId());
                vo.setInWarehouseId(null);
                vo.setOutTotalMoney(vo.getInTotalMoney());
                vo.setInTotalMoney(null);
                changeQuantity = lastVo.getCheckQuantity();
                changeMoney = (lastVo.getCheckTotalMoney().doubleValue() - vo.getOutTotalMoney().doubleValue()) / changeQuantity;
                break;
            case 5:
                //红冲
                if (myStorageMapper.redDashedOutStorageCheckOrder(vo) != 1) {
                    throw new CommonException("红冲库存记账记录失败");
                }

                //获取红冲蓝单
                vo = myStorageMapper.findOutStorageCheckOrder(vo);

                vo.setInWarehouseId(vo.getOutWarehouseId());
                vo.setOutWarehouseId(null);
                vo.setInTotalMoney(vo.getOutTotalMoney());
                vo.setOutTotalMoney(null);
                changeQuantity = lastVo.getCheckQuantity();
                changeMoney = (lastVo.getCheckTotalMoney().doubleValue() + vo.getInTotalMoney().doubleValue()) / changeQuantity;
                break;

            default:
                throw new CommonException("红冲库存记账记录失败");
        }

        //设置红冲红单
        vo.setStoreId(storeId);
        vo.setCreateTime(new Date());
        vo.setOrderStatus((byte) -2);
        vo.setUserId(userId);
        vo.setCheckQuantity(changeQuantity);
        vo.setCheckMoney(new BigDecimal(changeMoney));
        vo.setCheckTotalMoney(new BigDecimal(changeQuantity * changeMoney));

        //新增红冲红单
        if (myStorageMapper.addStorageCheckOrder(vo) != 1) {
            throw new CommonException("库存记账失败");
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
            throw new CommonException("修改库存失败");
        }
        switch (flag) {
            case 1:
                if (myGoodsMapper.increaseInventory(vo) != 1) {
                    throw new CommonException("修改库存失败");
                }
                break;
            case 0:
                if (myGoodsMapper.decreaseInventory(vo) != 1) {
                    throw new CommonException("修改库存失败");
                }
                break;
            default:
                throw new CommonException("修改库存失败");
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
            throw new CommonException("修改库存待发货数量或待收货数量失败");
        }
        switch (flag) {
            case 1:
                if (myGoodsMapper.increaseNotQuantity(vo) != 1) {
                    throw new CommonException("修改库存待发货数量或待收货数量失败");
                }
                break;
            case 0:
                if (myGoodsMapper.decreaseNotQuantity(vo) != 1) {
                    throw new CommonException("修改库存待发货数量或待收货数量失败");
                }
                break;
            default:
                throw new CommonException("修改库存待发货数量或待收货数量失败");
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
            throw new CommonException("修改库存上限或下限失败");
        }
        if (myGoodsMapper.updateLimitInventoryMethod(vo) != 1) {
            throw new CommonException("修改库存上限或下限失败");
        }
    }
}
