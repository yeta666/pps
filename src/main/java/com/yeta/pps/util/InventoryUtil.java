package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyGoodsMapper;
import com.yeta.pps.mapper.MyStorageMapper;
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
        storageCheckOrderVo.setGoodsSkuId(vo.getGoodsSkuId());
        storageCheckOrderVo.setInWarehouseId(vo.getWarehouseId());
        storageCheckOrderVo.setInQuantity(vo.getOpeningQuantity());
        storageCheckOrderVo.setInMoney(vo.getOpeningMoney());
        storageCheckOrderVo.setInTotalMoney(new BigDecimal(vo.getOpeningQuantity() * vo.getOpeningMoney().doubleValue()));
        storageCheckOrderVo.setUserId(vo.getUserId());

        //查询是否有该商品规格的库存对账记录
        StorageCheckOrderVo sVo = myStorageMapper.findLastCheckMoney(new StorageCheckOrderVo(vo.getStoreId(), vo.getGoodsSkuId()));
        if (sVo == null) {
            storageCheckOrderVo.setCheckQuantity(vo.getOpeningQuantity());
            storageCheckOrderVo.setCheckMoney(vo.getOpeningMoney());
            storageCheckOrderVo.setCheckTotalMoney(new BigDecimal(vo.getOpeningQuantity() * vo.getOpeningMoney().doubleValue()));
        } else {
            int changeQuantity = vo.getOpeningQuantity() + sVo.getCheckQuantity();
            double changeTotalMoney = vo.getOpeningQuantity() * vo.getOpeningMoney().doubleValue() + sVo.getCheckQuantity() * sVo.getCheckMoney().doubleValue();
            double changeMoney = changeTotalMoney / changeQuantity;

            storageCheckOrderVo.setCheckQuantity(changeQuantity);
            storageCheckOrderVo.setCheckMoney(new BigDecimal(changeMoney));
            storageCheckOrderVo.setCheckTotalMoney(new BigDecimal(changeTotalMoney));
        }

        if (myStorageMapper.addStorageCheckOrder(storageCheckOrderVo) != 1) {
            throw new CommonException("新增库存对账记录失败");
        }
    }

    /**
     * 新增库存对账记录的方法
     * @param vo
     */
    @Transactional
    public void addStorageCkeckOrder(StorageCheckOrderVo vo) {

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
