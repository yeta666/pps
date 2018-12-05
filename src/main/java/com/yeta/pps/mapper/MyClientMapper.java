package com.yeta.pps.mapper;

import com.yeta.pps.po.ClientClientLevel;
import com.yeta.pps.po.ClientLevel;
import com.yeta.pps.po.ClientMembershipNumber;
import com.yeta.pps.vo.ClientVo;
import com.yeta.pps.vo.PageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyClientMapper {

    int addClientMembershipNumber(ClientMembershipNumber clientMembershipNumber);

    int deleteClientMembershipNumber(ClientMembershipNumber clientMembershipNumber);

    int updateClientMembershipNumber(ClientMembershipNumber clientMembershipNumber);

    int findCountClientMembershipNumber();

    List<ClientMembershipNumber> findAllPagedClientMembershipNumber(@Param(value = "clientMembershipNumber") ClientMembershipNumber clientMembershipNumber,
                                                                    @Param(value = "pageVo") PageVo pageVo);

    ClientMembershipNumber findClientMembershipNumberById(ClientMembershipNumber clientMembershipNumber);

    ClientMembershipNumber findClientMembershipNumber(ClientMembershipNumber clientMembershipNumber);

    //

    int addClientLevel(ClientLevel clientLevel);

    int deleteClientLevel(ClientLevel clientLevel);

    int updateClientLevel(ClientLevel clientLevel);

    int findCountClientLevel();

    List<ClientLevel> findAllClientLevel();

    List<ClientLevel> findAllPagedClientLevel(PageVo pageVo);

    ClientLevel findClientLevelById(ClientLevel clientLevel);

    //

    int addClientClientLevel(ClientClientLevel clientClientLevel);

    int deleteClientClientLevel(ClientClientLevel clientClientLevel);

    int updateClientClientLevel(ClientClientLevel clientClientLevel);

    ClientClientLevel findClientClientLevel(ClientClientLevel clientClientLevel);

    //

    int add(ClientVo clientVo);

    int delete(ClientVo clientVo);

    int update(ClientVo clientVo);

    int updateIntegral(ClientVo clientVo);

    int updateLastDealTime(ClientVo clientVo);

    int findCount();

    List<ClientVo> findAllPaged(@Param(value = "clientVo") ClientVo clientVo,
                                         @Param(value = "pageVo") PageVo pageVo);

    ClientVo findClient(ClientVo clientVo);
}