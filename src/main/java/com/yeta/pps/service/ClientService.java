package com.yeta.pps.service;

import com.alibaba.fastjson.JSON;
import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyClientMapper;
import com.yeta.pps.po.*;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.ClientVo;
import com.yeta.pps.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 客户相关逻辑处理
 * @author YETA
 * @date 2018/12/04/13:51
 */
@Service
public class ClientService {

    @Autowired
    private MyClientMapper myClientMapper;

    /**
     * 新增会员卡号
     * @param clientMembershipNumber
     * @return
     */
    public CommonResponse addClientMembershipNumber(ClientMembershipNumber clientMembershipNumber) {
        //新增
        if (myClientMapper.addClientMembershipNumber(clientMembershipNumber) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除会员卡号
     * @param clientMembershipNumber
     * @return
     */
    public CommonResponse deleteClientMembershipNumber(ClientMembershipNumber clientMembershipNumber) {
        //判断会员卡号是否使用
        ClientClientMembershipNumber clientClientMembershipNumber = new ClientClientMembershipNumber(clientMembershipNumber.getId());
        if (myClientMapper.findClientClientMembershipNumber(clientClientMembershipNumber) != null) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        //删除会员卡号
        if (myClientMapper.deleteClientMembershipNumber(clientMembershipNumber) != 1) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改会员卡号
     * @param clientMembershipNumber
     * @return
     */
    public CommonResponse updateClientMembershipNumber(ClientMembershipNumber clientMembershipNumber) {
        //判断参数
        if (clientMembershipNumber.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        if (myClientMapper.updateClientMembershipNumber(clientMembershipNumber) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查找所有会员卡号
     * @return
     */
    public CommonResponse findAllClientMembershipNumber(ClientMembershipNumber clientMembershipNumber, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myClientMapper.findCountClientMembershipNumber() * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        List<ClientMembershipNumber> clientMembershipNumbers = myClientMapper.findAllPagedClientMembershipNumber(clientMembershipNumber, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("number", "会员卡号"));
        CommonResult commonResult = new CommonResult(titles, clientMembershipNumbers, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id超找会员卡号
     * @param clientMembershipNumber
     * @return
     */
    public CommonResponse findClientMembershipNumberById(ClientMembershipNumber clientMembershipNumber) {
        clientMembershipNumber = myClientMapper.findClientMembershipNumberById(clientMembershipNumber);
        if (clientMembershipNumber == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, clientMembershipNumber, CommonResponse.MESSAGE1);
    }

    //

    /**
     * 新增客户级别
     * @param clientLevel
     * @return
     */
    public CommonResponse addClientLevel(ClientLevel clientLevel) {
        if (myClientMapper.addClientLevel(clientLevel) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除客户级别
     * @param clientLevel
     * @return
     */
    public CommonResponse deleteClientLevel(ClientLevel clientLevel) {
        //判断客户级别是否使用
        ClientClientLevel clientClientLevel = new ClientClientLevel(clientLevel.getId());
        if (myClientMapper.findClientClientLevel(clientClientLevel) != null) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        //删除客户级别
        if (myClientMapper.deleteClientLevel(clientLevel) != 1) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改客户级别
     * @param clientLevel
     * @return
     */
    public CommonResponse updateClientLevel(ClientLevel clientLevel) {
        //判断参数
        if (clientLevel.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改客户级别
        if (myClientMapper.updateClientLevel(clientLevel) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查找所有客户级别
     * @return
     */
    public CommonResponse findAllClientLevel(PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage((int) Math.ceil(myClientMapper.findCountClientLevel() * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<ClientLevel> clientLevels = myClientMapper.findAllPagedClientLevel(pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("name", "客户级别"));
            titles.add(new Title("priceType", "级别价格类型"));
            titles.add(new Title("price", "级别默认价格"));
            CommonResult commonResult = new CommonResult(titles, clientLevels, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<ClientLevel> clientLevels = myClientMapper.findAllClientLevel();
        return new CommonResponse(CommonResponse.CODE1, clientLevels, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id超找客户级别
     * @param clientLevel
     * @return
     */
    public CommonResponse findClientLevelById(ClientLevel clientLevel) {
        clientLevel = myClientMapper.findClientLevelById(clientLevel);
        if (clientLevel == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, clientLevel, CommonResponse.MESSAGE1);
    }

    //

    /**
     * 新增客户
     * @param clientVo
     * @return
     */
    @Transactional
    public CommonResponse add(ClientVo clientVo) {
        //判断参数
        if (clientVo.getName() == null || clientVo.getUsername() == null || clientVo.getPassword() == null || clientVo.getPhone() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //设置初始属性
        clientVo.setId(UUID.randomUUID().toString());
        clientVo.setIntegral(0);
        clientVo.setCreateTime(new Date());
        clientVo.setDisabled((byte)0);
        ClientClientLevel clientClientLevel = new ClientClientLevel(clientVo.getId(), 4);     //4表示普通客户
        //判断是否填写会员卡号
        if (clientVo.getMembershipNumber() != null) {       //可能是普通vip，需要经过后续判断
            //填写会员卡号必须填写邀请人
            if (clientVo.getInviterName() == null) {
                return new CommonResponse(CommonResponse.CODE16, null, CommonResponse.MESSAGE16);
            }
            //判断邀请人是否存在
            ClientVo inviter = new ClientVo(null, clientVo.getInviterName());
            inviter = myClientMapper.findByName(inviter);
            if (inviter == null) {
                return new CommonResponse(CommonResponse.CODE16, null, CommonResponse.MESSAGE16);
            }
            clientVo.setInviterId(inviter.getId());
            //判断会员卡号是否存在
            ClientMembershipNumber clientMembershipNumber = new ClientMembershipNumber(clientVo.getMembershipNumber());
            clientMembershipNumber = myClientMapper.findClientMembershipNumberByMembershipNumber(clientMembershipNumber);
            if (clientMembershipNumber == null) {
                return new CommonResponse(CommonResponse.CODE15, null, CommonResponse.MESSAGE15);
            }
            //新增客户会员卡号关系
            ClientClientMembershipNumber clientClientMembershipNumber = new ClientClientMembershipNumber(clientVo.getId(), clientMembershipNumber.getId());
            if (myClientMapper.addClientClientMembershipNumber(clientClientMembershipNumber) != 1) {
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }
            //修改客户级别
            clientClientLevel.setLevelId(3);        //3表示普通vip
        }
        //新增客户
        if (myClientMapper.add(clientVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //新增客户客户级别关系
        if (myClientMapper.addClientClientLevel(clientClientLevel) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除客户
     * @param clientVo
     * @return
     */
    @Transactional
    public CommonResponse delete(ClientVo clientVo) {
        //删除客户
        if (myClientMapper.delete(clientVo) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }
        //删除客户客户关系
        ClientClientLevel clientClientLevel = new ClientClientLevel(clientVo.getId());
        if (myClientMapper.deleteClientClientLevel(clientClientLevel) != 1) {
            throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
        }
        //删除客户会员卡号关系
        ClientClientMembershipNumber clientClientMembershipNumber = new ClientClientMembershipNumber(clientVo.getId());
        myClientMapper.deleteClientClientMembershipNumber(clientClientMembershipNumber);        //普通客户没有客户会员卡号关系，所以不能判断返回结果
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改客户信息
     * @param clientVo
     * @return
     */
    public CommonResponse updateInfo(ClientVo clientVo) {
        //判断参数
        if (clientVo.getId() == null || clientVo.getPassword() == null || clientVo.getPhone()  == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        if (myClientMapper.updateInfo(clientVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改客户其他信息
     * @param clientVo
     * @return
     */
    public CommonResponse updateOther(ClientVo clientVo) {
        //判断参数
        if (clientVo.getId() == null || clientVo.getDisabled()  == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        if (myClientMapper.updateOther(clientVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查找vip客户
     * @return
     */
    public CommonResponse findAllVIPClient(ClientVo clientVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myClientMapper.findCountVIPClient() * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        //查找vip客户
        List<ClientVo> clientVos = myClientMapper.findAllPagedVIPClient(clientVo, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("id", "客户编号"));
        titles.add(new Title("name", "客户姓名"));
        titles.add(new Title("username", "客户用户名"));
        titles.add(new Title("phone", "客户电话"));
        titles.add(new Title("levelName", "客户级别"));
        titles.add(new Title("membershipNumber", "会员卡号"));
        titles.add(new Title("birthday", "客户生日"));
        titles.add(new Title("inviterId", "邀请人编号"));
        titles.add(new Title("inviterName", "邀请人姓名"));
        titles.add(new Title("integral", "积分"));
        titles.add(new Title("address", "客户地址"));
        titles.add(new Title("postcode", "邮编"));
        titles.add(new Title("lastDealTime", "最近交易时间"));
        titles.add(new Title("createTime", "创建时间"));
        titles.add(new Title("disabled", "是否停用"));
        titles.add(new Title("remark", "备注"));
        CommonResult commonResult = new CommonResult(titles, clientVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 查找普通客户
     * @return
     */
    public CommonResponse findAllCommonClient(ClientVo clientVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myClientMapper.findCountCommonClient() * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        //查找vip客户
        List<ClientVo> clientVos = myClientMapper.findAllPagedCommonClient(clientVo, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("id", "客户编号"));
        titles.add(new Title("name", "客户姓名"));
        titles.add(new Title("username", "客户用户名"));
        titles.add(new Title("phone", "客户电话"));
        titles.add(new Title("levelName", "客户级别"));
        titles.add(new Title("membershipNumber", "会员卡号"));
        titles.add(new Title("birthday", "客户生日"));
        titles.add(new Title("inviterId", "邀请人编号"));
        titles.add(new Title("inviterName", "邀请人姓名"));
        titles.add(new Title("integral", "积分"));
        titles.add(new Title("address", "客户地址"));
        titles.add(new Title("postcode", "邮编"));
        titles.add(new Title("lastDealTime", "最近交易时间"));
        titles.add(new Title("createTime", "创建时间"));
        titles.add(new Title("disabled", "是否停用"));
        titles.add(new Title("remark", "备注"));
        CommonResult commonResult = new CommonResult(titles, clientVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查找vip客户
     * @param clientVo
     * @return
     */
    public CommonResponse findVIPClientById(ClientVo clientVo) {
        clientVo = myClientMapper.findVIPClientById(clientVo);
        if (clientVo == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, clientVo, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查找普通客户
     * @param clientVo
     * @return
     */
    public CommonResponse findCommonClientById(ClientVo clientVo) {
        clientVo = myClientMapper.findCommonClientById(clientVo);
        if (clientVo == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, clientVo, CommonResponse.MESSAGE1);
    }
}
