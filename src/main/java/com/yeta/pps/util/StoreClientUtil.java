package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyClientMapper;
import com.yeta.pps.mapper.SystemMapper;
import com.yeta.pps.po.GoodsSku;
import com.yeta.pps.po.StoreClient;
import com.yeta.pps.po.StoreClientDetail;
import com.yeta.pps.po.System;
import com.yeta.pps.vo.ClientVo;
import com.yeta.pps.vo.OrderGoodsSkuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
            storeClient.setIntegral(storeClient.getIntegral() + goodsSku.getIntegral());
        });
        if (storeClient.getIntegral() > 0) {
            if (flag == 0) {        //减少积分
                storeClient.setIntegral(-storeClient.getIntegral());
                if (myClientMapper.updateStoreClientIntegral(storeClient) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
            } else if (flag == 1) {     //增加积分
                if (myClientMapper.findStoreClientByStoreIdAndClientId(storeClient) != null) {
                    if (myClientMapper.updateStoreClientIntegral(storeClient) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                } else {
                    storeClient.setAdvanceMoney(0.0);
                    storeClient.setPushMoney(0.0);
                    if (myClientMapper.addStoreClient(storeClient) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
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
        ClientVo clientVo = myClientMapper.findById(new ClientVo(clientId));
        if (clientVo == null || clientVo.getInviterId() == null) {
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        String inviterId = clientVo.getInviterId();

        StoreClient storeClient = new StoreClient(storeId, inviterId);
        switch (flag) {
            case 0:
                //减少
                storeClient.setPushMoney(-orderMoney);
                if (myClientMapper.updateStoreClientIntegral(storeClient) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
                break;

            case 1:
                //查询提成比例
                System system = systemMapper.findSystem();
                if (system == null || system.getPushMoneyRate() == null || system.getPushMoneyRate() < 0) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }

                //设置提成
                storeClient.setPushMoney(orderMoney * system.getPushMoneyRate());

                //增加或新增
                if (myClientMapper.findStoreClientByStoreIdAndClientId(storeClient) != null) {
                    if (myClientMapper.updateStoreClientIntegral(storeClient) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                } else {
                    storeClient.setIntegral(0);
                    storeClient.setAdvanceMoney(0.0);
                    if (myClientMapper.addStoreClient(storeClient) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
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