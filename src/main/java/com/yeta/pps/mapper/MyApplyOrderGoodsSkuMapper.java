package com.yeta.pps.mapper;

import com.yeta.pps.po.ApplyOrderGoodsSku;
import com.yeta.pps.vo.ApplyOrderGoodsSkuVo;

import java.util.List;

/**
 * @author YETA
 * @date 2018/12/14/15:23
 */
public interface MyApplyOrderGoodsSkuMapper {

    //采购申请单、销售申请单/商品规格关系

    int addApplyOrderGoodsSku(ApplyOrderGoodsSkuVo applyOrderGoodsSkuVo);

    int deleteApplyOrderGoodsSku(ApplyOrderGoodsSkuVo applyOrderGoodsSkuVo);

    int updateApplyOrderGoodsSku(ApplyOrderGoodsSkuVo applyOrderGoodsSkuVo);

    List<ApplyOrderGoodsSku> findAllApplyOrderGoodsSku(ApplyOrderGoodsSkuVo applyOrderGoodsSkuVo);
}
