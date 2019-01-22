package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.*;
import com.yeta.pps.po.GoodsSku;
import com.yeta.pps.po.SSystem;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.FundUtil;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 系统设置相关逻辑处理
 * @author YETA
 * @date 2019/01/14/19:09
 */
@Service
public class SystemService {

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private MyGoodsMapper myGoodsMapper;

    @Autowired
    private MyStorageMapper myStorageMapper;

    @Autowired
    private MyBankAccountMapper myBankAccountMapper;

    @Autowired
    private MyClientMapper myClientMapper;

    @Autowired
    private MySupplierMapper mySupplierMapper;

    @Autowired
    private FundUtil fundUtil;

    /**
     * 返回四舍五入2位小数的方法
     * @param number
     * @return
     */
    public double getNumberMethod(double number) {
        return (double) Math.round(number * 100) / 100;
    }

    /**
     * 查询分店设置
     * @return
     */
    public CommonResponse findSystem() {
        SSystem SSystem = systemMapper.findSystem();
        if (SSystem == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }

        return CommonResponse.success(SSystem);
    }

    /**
     * 修改分店设置
     * @param sSystem
     * @return
     */
    public CommonResponse updateSystem(SSystem sSystem) {
        //判断参数
        if (sSystem.getPushMoneyRate() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        if (systemMapper.updateSystem(sSystem) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        
        return CommonResponse.success();
    }

    /**
     * 查询系统开账
     * @param sSystem
     * @return
     */
    public CommonResponse findStartBill(SSystem sSystem) {
        sSystem = systemMapper.findStartBill(sSystem);
        if (sSystem == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }

        return CommonResponse.success(sSystem);
    }

    /**
     * 修改系统开账
     * @param sSystem
     * @return
     */
    @Transactional
    public synchronized CommonResponse updateStartBill(SSystem sSystem) {
        //判断参数
        if (sSystem.getStoreId() == null || sSystem.getUserId() == null) {
            throw new CommonException(CommonResponse.PARAMETER_ERROR);
        }

        //获取参数
        Integer storeId = sSystem.getStoreId();
        String userId = sSystem.getUserId();

        //修改系统开账
        if (systemMapper.updateStartBill(sSystem) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        //查询所有商品规格
        List<GoodsSku> goodsSkus = myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(storeId));

        //查询所有库存期初
        List<WarehouseGoodsSkuVo> warehouseGoodsSkuVos = myGoodsMapper.findAllWarehouseGoodsSku(new WarehouseGoodsSkuVo(storeId));
        warehouseGoodsSkuVos.stream().forEach(vo -> {
            //获取商品货号
            Optional<GoodsSku> optional = goodsSkus.stream().filter(goodsSku -> goodsSku.getId().toString().equals(vo.getGoodsSkuId().toString())).findFirst();
            if (!optional.isPresent()) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
            String goodsId = optional.get().getGoodsId();

            //创建库存对账记录对象
            StorageCheckOrderVo storageCheckOrderVo = new StorageCheckOrderVo();
            storageCheckOrderVo.setStoreId(storeId);
            storageCheckOrderVo.setOrderId("期初调整");
            storageCheckOrderVo.setCreateTime(new Date());
            storageCheckOrderVo.setOrderStatus((byte) 1);
            storageCheckOrderVo.setGoodsId(goodsId);
            storageCheckOrderVo.setGoodsSkuId(vo.getGoodsSkuId());
            storageCheckOrderVo.setWarehouseId(vo.getWarehouseId());
            storageCheckOrderVo.setInQuantity(vo.getOpeningQuantity());
            storageCheckOrderVo.setInMoney(vo.getOpeningMoney());
            storageCheckOrderVo.setInTotalMoney(vo.getOpeningTotalMoney());
            storageCheckOrderVo.setUserId(userId);
            storageCheckOrderVo.setCheckQuantity(vo.getOpeningQuantity());
            storageCheckOrderVo.setCheckMoney(vo.getOpeningMoney());
            storageCheckOrderVo.setCheckTotalMoney(vo.getOpeningTotalMoney());

            //根据商品规格编号查询最新库存对账记录
            StorageCheckOrderVo sVo1 = myStorageMapper.findLastCheckMoneyByGoodsSkuId(new StorageCheckOrderVo(storeId, vo.getGoodsSkuId()));
            if (sVo1 == null) {
                storageCheckOrderVo.setCheckQuantity1(vo.getOpeningQuantity());
                storageCheckOrderVo.setCheckMoney1(vo.getOpeningMoney());
                storageCheckOrderVo.setCheckTotalMoney1(vo.getOpeningTotalMoney());
            } else {
                int changeQuantity1 = vo.getOpeningQuantity() + sVo1.getCheckQuantity1();
                double changeTotalMoney1 = vo.getOpeningTotalMoney() + sVo1.getCheckTotalMoney1();
                double changeMoney1 = getNumberMethod(changeTotalMoney1 / changeQuantity1);
                storageCheckOrderVo.setCheckQuantity1(changeQuantity1);
                storageCheckOrderVo.setCheckMoney1(changeMoney1);
                storageCheckOrderVo.setCheckTotalMoney1(changeTotalMoney1);
            }

            //根据商品货号查询最新库存对账记录
            StorageCheckOrderVo sVo2 = myStorageMapper.findLastCheckMoneyByGoodsId(new StorageCheckOrderVo(storeId, goodsId));
            if (sVo2 == null) {
                storageCheckOrderVo.setCheckQuantity2(vo.getOpeningQuantity());
                storageCheckOrderVo.setCheckMoney2(vo.getOpeningMoney());
                storageCheckOrderVo.setCheckTotalMoney2(vo.getOpeningTotalMoney());
            } else {
                int changeQuantity2 = vo.getOpeningQuantity() + sVo2.getCheckQuantity2();
                double changeTotalMoney2 = vo.getOpeningTotalMoney() + sVo2.getCheckTotalMoney2();
                double changeMoney2 = getNumberMethod(changeTotalMoney2 / changeQuantity2);
                storageCheckOrderVo.setCheckQuantity2(changeQuantity2);
                storageCheckOrderVo.setCheckMoney2(changeMoney2);
                storageCheckOrderVo.setCheckTotalMoney2(changeTotalMoney2);
            }

            //期初库存记账
            if (myStorageMapper.addStorageCheckOrder(storageCheckOrderVo) != 1) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
        });

        //现金银行期初记账
        myBankAccountMapper.findAll(new BankAccountVo(storeId))
                .stream().forEach(vo -> fundUtil.addOpeningFundCheckOrderMethod(storeId, vo.getId(), vo.getOpeningMoney(), userId));

        //应收期初记账
        myClientMapper.findAllStoreClient(new StoreClientVo(storeId))
                .stream().forEach(vo -> fundUtil.addOpeningFundTargetCheckOrderMethod(1, storeId, vo.getClientId(), vo.getNeedInMoneyOpening(), vo.getAdvanceInMoneyOpening(), userId));

        //查询所有应付期初
        mySupplierMapper.findAll(new SupplierVo(storeId))
                .stream().forEach(vo ->  fundUtil.addOpeningFundTargetCheckOrderMethod(0, storeId, vo.getId(), vo.getNeedOutMoneyOpening(), vo.getAdvanceOutMoneyOpening(), userId));

        return CommonResponse.success();
    }

    /**
     * 查询零售默认设置
     * @param sSystem
     * @return
     */
    public CommonResponse findRetail(SSystem sSystem) {
        sSystem = systemMapper.findRetail(sSystem);
        if (sSystem == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }

        return CommonResponse.success(sSystem);
    }

    /**
     * 修改零售默认设置
     * @param sSystem
     * @return
     */
    @Transactional
    public synchronized CommonResponse updateRetail(SSystem sSystem) {
        //判断参数
        if (sSystem.getRetailWarehouseId() == null || sSystem.getRetailBankAccountId() == null) {
            return CommonResponse.error(CommonResponse.PARAMETER_ERROR);
        }

        //修改零售默认设置
        if (systemMapper.updateRetail(sSystem) != 1) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        return CommonResponse.success();
    }
}
