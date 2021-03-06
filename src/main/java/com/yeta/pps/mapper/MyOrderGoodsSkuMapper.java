package com.yeta.pps.mapper;

import com.yeta.pps.vo.OrderGoodsSkuVo;

import java.util.List;

/**
 * @author YETA
 * @date 2018/12/14/15:23
 */
public interface MyOrderGoodsSkuMapper {

    //单据/商品规格关系

    int addOrderGoodsSku(OrderGoodsSkuVo orderGoodsSkuVo);

    int deleteOrderGoodsSku(OrderGoodsSkuVo orderGoodsSkuVo);

    int updateOrderGoodsSku(OrderGoodsSkuVo orderGoodsSkuVo);

    int updateOperatedQuantity(OrderGoodsSkuVo orderGoodsSkuVo);

    List<OrderGoodsSkuVo> findAllOrderGoodsSku(OrderGoodsSkuVo orderGoodsSkuVo);
}
