package com.yeta.pps.util;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyClientMapper;
import com.yeta.pps.po.GoodsSku;
import com.yeta.pps.vo.ClientIntegralDetailVo;
import com.yeta.pps.vo.OrderGoodsSkuVo;
import com.yeta.pps.vo.StoreIntegralVo;
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
public class IntegralUtil {

    @Autowired
    private MyClientMapper myClientMapper;

    /**
     * 客户积分相关方法
     * @param storeId
     * @param clientId
     * @param resultOrderId
     * @param goodsSkus
     * @param orderGoodsSkuVos
     */
    @Transactional
    public void updateIntegralMethod(Integer storeId, String clientId, String resultOrderId, List<GoodsSku> goodsSkus, List<OrderGoodsSkuVo> orderGoodsSkuVos) {
        //统计积分
        StoreIntegralVo storeIntegralVo = new StoreIntegralVo(storeId, clientId, 0);
        orderGoodsSkuVos.stream().forEach(orderGoodsSkuVo -> {
            GoodsSku goodsSku = goodsSkus.stream().filter(sku -> sku.getId().equals(orderGoodsSkuVo.getGoodsSkuId())).findFirst().get();
            storeIntegralVo.setIntegral(storeIntegralVo.getIntegral() + goodsSku.getIntegral());
        });
        if (storeIntegralVo.getIntegral() > 0) {
            //增加客户积分
            if (myClientMapper.increaseIntegral(storeIntegralVo) != 1) {        //还没有数据
                if (myClientMapper.addStoreIntegral(storeIntegralVo) != 1) {
                    throw new CommonException(CommonResponse.UPDATE_ERROR);
                }
            }
            //新增客户积分明细
            if (myClientMapper.addIntegralDetail(new ClientIntegralDetailVo(
                    storeId,
                    clientId,
                    new Date(),
                    (byte) 1,
                    storeIntegralVo.getIntegral(),
                    myClientMapper.findStoreIntegralByStoreIdAndClientId(storeIntegralVo).getIntegral(),
                    resultOrderId)) != 1) {
                throw new CommonException(CommonResponse.UPDATE_ERROR);
            }
        }
    }
}
