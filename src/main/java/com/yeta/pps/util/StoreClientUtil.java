package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyClientMapper;
import com.yeta.pps.mapper.MyGoodsMapper;
import com.yeta.pps.mapper.MySellMapper;
import com.yeta.pps.mapper.SystemMapper;
import com.yeta.pps.po.*;
import com.yeta.pps.vo.ClientVo;
import com.yeta.pps.vo.GoodsVo;
import com.yeta.pps.vo.OrderGoodsSkuVo;
import com.yeta.pps.vo.SellResultOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author YETA
 * @date 2018/12/22/15:00
 */
@Component
public class StoreClientUtil {

    @Autowired
    private MyClientMapper myClientMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private MySellMapper mySellMapper;

    @Autowired
    private MyGoodsMapper myGoodsMapper;

    /**
     * 判断权限的方法
     * @param check
     * @return
     */
    public boolean checkMethod(String check) {
        Client checkClient = new Client();
        checkClient.setId(check);
        checkClient = myClientMapper.findSpecialClient(checkClient);
        if (checkClient == null || checkClient.getLevelId() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 修改客户积分的方法
     * @param flag 0：减少，1：增加
     * @param storeId
     * @param clientId
     * @param resultOrderId
     * @param goodsSkus
     * @param orderGoodsSkuVos
     * @param userId
     */
    @Transactional
    public void updateIntegralMethod(int flag, Integer storeId, String clientId, String resultOrderId, List<GoodsSku> goodsSkus, List<OrderGoodsSkuVo> orderGoodsSkuVos, String userId) {
        //统计积分
        StoreClient storeClient = new StoreClient(storeId, clientId);
        storeClient.setIntegral(0);
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            GoodsSku goodsSku = goodsSkus.stream().filter(sku -> sku.getId().equals(orderGoodsSkuVo.getGoodsSkuId())).findFirst().get();
            storeClient.setIntegral(storeClient.getIntegral() + goodsSku.getIntegral() * orderGoodsSkuVo.getQuantity());
        });
        if (storeClient.getIntegral() > 0) {
            if (flag == 0) {        //减少积分
                storeClient.setIntegral(-storeClient.getIntegral());
                if (myClientMapper.updateStoreClientIntegral(storeClient) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
            } else if (flag == 1) {     //增加积分
                if (myClientMapper.updateStoreClientIntegral(storeClient) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
            }

            //新增店铺/客户明细关系
            if (myClientMapper.addStoreClientDetail(new StoreClientDetail(
                    storeId,
                    clientId,
                    new Date(),
                    new Date(),
                    flag == 1 ? (byte) 1 : (byte) 2,
                    storeClient.getIntegral(),
                    resultOrderId,
                    (byte) 1,
                    userId)) != 1) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
        }
    }

    /**
     * 修改提成的方法
     * @param flag 0：减少，1：增加
     * @param storeId
     * @param clientId
     * @param resultOrderId
     * @param userId
     * @param orderMoney
     */
    @Transactional
    public void updatePushMoneyMethod(int flag, Integer storeId, String clientId, String resultOrderId, String userId, double orderMoney) {
        //查询该客户的邀请人编号
        ClientVo clientVo = myClientMapper.findClientInviter(new ClientVo(clientId));
        if (clientVo == null) {
            return;
        }

        if (clientVo.getInviterId() != null) {      //店长级别客户没有邀请人
            String inviterId = clientVo.getInviterId();
            StoreClient storeClient = new StoreClient(storeId, inviterId, 0.0);

            //查询提成比例
            SSystem sSystem = systemMapper.findSystem();
            if (sSystem == null || sSystem.getPushMoneyRate() == null || sSystem.getPushMoneyRate() < 0) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }

            SellResultOrderVo sellResultOrderVo = mySellMapper.findResultOrderDetailById(new SellResultOrderVo(storeId, resultOrderId));
            if (sellResultOrderVo == null || sellResultOrderVo.getDetails() == null || sellResultOrderVo.getDetails().size() == 0) {
                throw new CommonException(CommonResponse.UPDATE_ERROR, "查询是否提成失败");
            }
            List<GoodsVo> goodsVos = myGoodsMapper.findAll(new GoodsVo(storeId));
            sellResultOrderVo.getDetails().stream().forEach(orderGoodsSkuVo -> {
                Optional<GoodsVo> optional = goodsVos.stream().filter(goodsVo -> goodsVo.getId().equals(orderGoodsSkuVo.getGoodsId())).findFirst();
                if (!optional.isPresent()) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR, "查询是否提成失败");
                }
                if (optional.get().getPushMoneyStatus() == 1) {
                    storeClient.setPushMoney(storeClient.getPushMoney() + orderGoodsSkuVo.getMoney() - orderGoodsSkuVo.getDiscountMoney());
                }
            });

            if (storeClient.getPushMoney() > 0) {
                switch (flag) {
                    case 0:
                        //减少
                        storeClient.setPushMoney(-storeClient.getPushMoney() * sSystem.getPushMoneyRate());
                        if (myClientMapper.updateStoreClientPushMoney(storeClient) != 1) {
                            throw new CommonException(CommonResponse.UPDATE_ERROR);
                        }
                        break;

                    case 1:
                        //增加
                        storeClient.setPushMoney(storeClient.getPushMoney() * sSystem.getPushMoneyRate());
                        if (myClientMapper.updateStoreClientPushMoney(storeClient) != 1) {
                            throw new CommonException(CommonResponse.UPDATE_ERROR);
                        }

                        break;
                    default:
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                }

                //新增店铺/客户明细关系
                if (myClientMapper.addStoreClientDetail(new StoreClientDetail(
                        storeId,
                        inviterId,
                        new Date(),
                        new Date(),
                        flag == 1 ? (byte) 3 : (byte) 4,
                        storeClient.getPushMoney(),
                        resultOrderId,
                        (byte) 1,
                        userId)) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
            }
        }
    }
}
