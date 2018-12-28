package com.yeta.pps.mapper;

import com.yeta.pps.vo.OrderGoodsSkuVo;

/**
 * @author YETA
 * @date 2018/12/14/15:23
 */
public interface MyOrderGoodsSkuMapper {

    //采购申请单、销售申请单/商品规格关系

    int addOrderGoodsSku(OrderGoodsSkuVo orderGoodsSkuVo);

    int deleteOrderGoodsSku(OrderGoodsSkuVo orderGoodsSkuVo);

    int updateOrderGoodsSku(OrderGoodsSkuVo orderGoodsSkuVo);

    int updateOperatedQuantity(OrderGoodsSkuVo orderGoodsSkuVo);
}
