package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyClientMapper;
import com.yeta.pps.mapper.SystemMapper;
import com.yeta.pps.po.GoodsSku;
import com.yeta.pps.po.StoreClient;
import com.yeta.pps.po.StoreClientDetail;
import com.yeta.pps.po.SSystem;
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
            throw new CommonException(CommonResponse.UPDATE_ERROR);
        }

        if (clientVo.getInviterId() != null) {      //店长级别客户没有邀请人
            String inviterId = clientVo.getInviterId();
            StoreClient storeClient = new StoreClient(storeId, inviterId);

            //查询提成比例
            SSystem sSystem = systemMapper.findPushMoneyRate();
            if (sSystem == null || sSystem.getPushMoneyRate() == null || sSystem.getPushMoneyRate() < 0) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
            switch (flag) {
                case 0:
                    //减少
                    storeClient.setPushMoney(orderMoney * sSystem.getPushMoneyRate());
                    if (myClientMapper.updateStoreClientPushMoney(storeClient) != 1) {
                        throw new CommonException(CommonResponse.UPDATE_ERROR);
                    }
                    break;

                case 1:
                    //设置提成
                    storeClient.setPushMoney(orderMoney * sSystem.getPushMoneyRate());

                    //增加
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
