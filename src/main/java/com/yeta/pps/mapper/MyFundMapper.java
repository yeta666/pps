package com.yeta.pps.mapper;

import com.yeta.pps.vo.FundCheckOrderVo;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.FundOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YETA
 * @date 2018/12/11/15:59
 */
public interface MyFundMapper {

    //收/付款单、预收/付款单

    int addFundOrder(FundOrderVo fundOrderVo);

    int redDashed(FundOrderVo fundOrderVo);

    int findCountFundOrder(FundOrderVo fundOrderVo);

    List<FundOrderVo> findAllPagedFundOrder(@Param(value = "fundOrderVo") FundOrderVo fundOrderVo,
                                            @Param(value = "pageVo") PageVo pageVo);

    FundOrderVo findFundOrderById(FundOrderVo fundOrderVo);

    //资金对账

    int addFundCheckOrder(FundCheckOrderVo fundCheckOrderVo);

    int redDashedFundCheckOrder(FundCheckOrderVo fundCheckOrderVo);

    FundCheckOrderVo findSumFundCheckOrder(FundCheckOrderVo fundCheckOrderVo);

    int findCountFundCheckOrder(FundCheckOrderVo fundCheckOrderVo);

    List<FundCheckOrderVo> findAllPagedFundCheckOrder(@Param(value = "vo") FundCheckOrderVo fundCheckOrderVo,
                                                      @Param(value = "pageVo") PageVo pageVo);

    FundCheckOrderVo findFundCheckOrderByOrderId(FundCheckOrderVo fundCheckOrderVo);

    FundCheckOrderVo findLastBalanceMoney(FundCheckOrderVo fundCheckOrderVo);
}
