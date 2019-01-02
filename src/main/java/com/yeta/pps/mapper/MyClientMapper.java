package com.yeta.pps.mapper;

import com.yeta.pps.po.Client;
import com.yeta.pps.po.ClientIntegralDetail;
import com.yeta.pps.po.ClientLevel;
import com.yeta.pps.po.MembershipNumber;
import com.yeta.pps.vo.ClientIntegralDetailVo;
import com.yeta.pps.vo.ClientVo;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.StoreIntegralVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyClientMapper {

    //客户登陆
    Client findByUsernameAndPassword(ClientVo clientVo);

    //会员卡号

    int addMembershipNumber(MembershipNumber membershipNumber);

    int deleteMembershipNumber(MembershipNumber membershipNumber);

    int updateMembershipNumber(MembershipNumber membershipNumber);

    int findCountMembershipNumber(MembershipNumber membershipNumber);

    List<MembershipNumber> findAllPagedMembershipNumber(@Param(value = "membershipNumber") MembershipNumber membershipNumber,
                                                        @Param(value = "pageVo") PageVo pageVo);

    List<MembershipNumber> findUsedMembershipNumber();

    MembershipNumber findMembershipNumberByNumber(MembershipNumber membershipNumber);

    //客户级别

    int addClientLevel(ClientLevel clientLevel);

    int deleteClientLevel(ClientLevel clientLevel);

    int updateClientLevel(ClientLevel clientLevel);

    int findCountClientLevel();

    List<ClientLevel> findAllPagedClientLevel(PageVo pageVo);

    List<ClientLevel> findAllClientLevel();

    List<ClientLevel> findUsedClientLevel();

    ClientLevel findClientLevelByIdOrName(ClientLevel clientLevel);

    //客户

    int add(ClientVo clientVo);

    int delete(ClientVo clientVo);

    int update(ClientVo clientVo);

    int updateDisabledAndRemark(ClientVo clientVo);

    int updateLastDealTime(ClientVo clientVo);

    int findCount(ClientVo clientVo);

    List<ClientVo> findAllPaged(@Param(value = "clientVo") ClientVo clientVo,
                                @Param(value = "pageVo") PageVo pageVo);

    List<ClientVo> findAll(ClientVo clientVo);

    Client findByIdOrPhone(ClientVo clientVo);

    //店铺/积分关系

    int addStoreIntegral(StoreIntegralVo storeIntegralVo);

    int deleteStoreIntegralByClientId(StoreIntegralVo storeIntegralVo);

    int increaseIntegral(StoreIntegralVo storeIntegralVo);

    int decreaseIntegral(StoreIntegralVo storeIntegralVo);

    int updateAdvanceMoney(StoreIntegralVo storeIntegralVo);

    int findCountStoreIntegral(StoreIntegralVo storeIntegralVo);

    List<StoreIntegralVo> findAllPagedStoreIntegral(@Param(value = "storeIntegralVo") StoreIntegralVo storeIntegralVo,
                                                    @Param(value = "pageVo") PageVo pageVo);

    StoreIntegralVo findStoreIntegralByStoreIdAndClientId(StoreIntegralVo storeIntegralVo);

    //客户/积分明细关系

    int addIntegralDetail(ClientIntegralDetailVo clientIntegralDetailVo);

    int deleteIntegralDetailByClientId(ClientIntegralDetailVo clientIntegralDetailVo);

    int findCountIntegralDetail(ClientIntegralDetailVo clientIntegralDetailVo);

    List<ClientIntegralDetailVo> findAllPagedIntegralDetail(@Param(value = "clientIntegralDetailVo") ClientIntegralDetailVo clientIntegralDetailVo,
                                                            @Param(value = "pageVo") PageVo pageVo);
}