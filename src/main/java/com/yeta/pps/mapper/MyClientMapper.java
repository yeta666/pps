package com.yeta.pps.mapper;

import com.yeta.pps.po.*;
import com.yeta.pps.vo.*;
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

    ClientVo findById(ClientVo clientVo);

    //店铺/客户关系

    int addStoreClient(StoreClient storeClient);

    int deleteStoreClientByClientId(StoreClient storeClient);

    int updateStoreClientIntegral(StoreClient storeClient);

    int updateStoreClientAdvanceMoney(StoreClient storeClient);

    int updateStoreClientPushMoney(StoreClient storeClient);

    int findCountStoreClient(StoreClientVo storeClientVo);

    List<StoreClientVo> findPagedStoreClient(@Param(value = "vo") StoreClientVo storeClientVo,
                                             @Param(value = "pageVo") PageVo pageVo);

    StoreClient findStoreClientByStoreIdAndClientId(StoreClient storeClient);

    //店铺/客户明细关系

    int addStoreClientDetail(StoreClientDetail storeClientDetail);

    int deleteStoreClientDetailByClientId(StoreClientDetail storeClientDetail);

    int updateStoreClientDetailStatusAndRemark(StoreClientDetail storeClientDetail);

    int findCountStoreClientDetail(StoreClientDetailVo storeClientDetailVo);

    List<StoreClientDetailVo> findPagedStoreClientDetail(@Param(value = "vo") StoreClientDetailVo storeClientDetailVo,
                                                         @Param(value = "pageVo") PageVo pageVo);

    StoreClientDetail findStoreClientDetailById(StoreClientDetail storeClientDetail);

    //下级

    List<SubordinateVo> findSubordinateByInviterId(SubordinateVo subordinateVo);

    List<SubordinateVo> findSubordinateByClientId(SubordinateVo subordinateVo);
}