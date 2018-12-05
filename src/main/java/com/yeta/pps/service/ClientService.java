package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyClientMapper;
import com.yeta.pps.po.ClientClientLevel;
import com.yeta.pps.po.ClientIntegralDetail;
import com.yeta.pps.po.ClientLevel;
import com.yeta.pps.po.ClientMembershipNumber;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.ClientIntegralDetailVo;
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
        clientMembershipNumber = myClientMapper.findClientMembershipNumberById(clientMembershipNumber);
        if (clientMembershipNumber == null || clientMembershipNumber.getNumber() == null) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        ClientVo clientVo = new ClientVo(null, null, clientMembershipNumber.getNumber());
        if (myClientMapper.findClient(clientVo) != null) {
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
        //判断会员卡号是否使用
        clientMembershipNumber = myClientMapper.findClientMembershipNumberById(clientMembershipNumber);
        if (clientMembershipNumber == null || clientMembershipNumber.getNumber() == null) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        ClientVo clientVo = new ClientVo(null, null, clientMembershipNumber.getNumber());
        if (myClientMapper.findClient(clientVo) != null) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        //修改会员卡号
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
        if (clientVo.getName() == null || clientVo.getUsername() == null || clientVo.getPassword() == null || clientVo.getPhone() == null ||
                clientVo.getLevelId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //判断客户编号是否填写
        if (clientVo.getId() == null) {
            clientVo.setId(UUID.randomUUID().toString());
        }
        //判断积分是否填写
        if (clientVo.getIntegral() == null) {
            clientVo.setIntegral(0);
        }
        //设置初始属性
        clientVo.setCreateTime(new Date());
        clientVo.setDisabled((byte)0);
        //判断会员卡号是否填写
        if (clientVo.getMembershipNumber() != null) {
            //判断会员卡号是否存在
            ClientMembershipNumber clientMembershipNumber = new ClientMembershipNumber(clientVo.getMembershipNumber());
            clientMembershipNumber = myClientMapper.findClientMembershipNumber(clientMembershipNumber);
            if (clientMembershipNumber == null) {
                return new CommonResponse(CommonResponse.CODE15, null, CommonResponse.MESSAGE15);
            }
            //判断会员卡号是否使用
            if (myClientMapper.findClient(new ClientVo(null, null, clientMembershipNumber.getNumber())) != null) {
                return new CommonResponse(CommonResponse.CODE15, null, CommonResponse.MESSAGE15);
            }
        }
        //判断邀请人是否填写
        if (clientVo.getInviterId() != null || clientVo.getInviterPhone() != null) {
            //判断邀请人是否存在
            ClientVo inviter = new ClientVo(clientVo.getInviterId(), clientVo.getInviterPhone(), null);
            inviter = myClientMapper.findClient(inviter);
            if (inviter == null) {
                return new CommonResponse(CommonResponse.CODE16, null, CommonResponse.MESSAGE16);
            }
            clientVo.setInviterId(inviter.getId());
            clientVo.setInviterName(inviter.getName());
        }
        //新增客户
        if (myClientMapper.add(clientVo) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        //新增客户客户级别关系
        ClientClientLevel clientClientLevel = new ClientClientLevel(clientVo.getId(), clientVo.getLevelId());
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
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改客户
     * @param clientVo
     * @return
     */
    @Transactional
    public CommonResponse update(ClientVo clientVo) {
        //判断参数
        if (clientVo.getId() == null || clientVo.getName() == null || clientVo.getUsername() == null || clientVo.getPassword() == null || clientVo.getPhone()  == null ||
                clientVo.getLevelId() == null || clientVo.getDisabled() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //获取原来的客户级别
        ClientVo oldClientVo = new ClientVo(clientVo.getInviterId(), clientVo.getPhone(), null);
        oldClientVo = myClientMapper.findClient(oldClientVo);
        if (oldClientVo == null) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        if (oldClientVo.getLevelId() != clientVo.getLevelId()) {        //客户级别已修改
            //修改客户客户级别关系
            ClientClientLevel clientClientLevel = new ClientClientLevel(clientVo.getId(), clientVo.getLevelId());
            if (myClientMapper.updateClientClientLevel(clientClientLevel) != 1) {
                throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
            }
        }
        //判断会员卡号是否填写
        if (clientVo.getMembershipNumber() != null) {
            //判断会员卡号是否存在
            ClientMembershipNumber clientMembershipNumber = new ClientMembershipNumber(clientVo.getMembershipNumber());
            clientMembershipNumber = myClientMapper.findClientMembershipNumber(clientMembershipNumber);
            if (clientMembershipNumber == null) {
                throw new CommonException(CommonResponse.CODE15, CommonResponse.MESSAGE15);
            }
            //判断会员卡号是否使用
            if (myClientMapper.findClient(new ClientVo(null, null, clientMembershipNumber.getNumber())) != null) {
                throw new CommonException(CommonResponse.CODE15, CommonResponse.MESSAGE15);
            }
        }
        //修改客户
        if (myClientMapper.update(clientVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }


    /**
     * 修改客户积分
     * @param clientIntegralDetailVo
     * @return
     */
    @Transactional
    public CommonResponse updateIntegral(ClientIntegralDetailVo clientIntegralDetailVo) {
        //判断客户是否存在
        ClientVo clientVo = new ClientVo(clientIntegralDetailVo.getClientId());
        clientVo = myClientMapper.findClient(clientVo);
        if (clientVo == null || clientVo.getLevelId() == 4) {       //4，普通客户没有积分
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        ClientIntegralDetail clientIntegralDetail = new ClientIntegralDetail();
        //判断是增加积分还是减少积分
        if (clientIntegralDetailVo.getType() == 0) {        //减少积分
            clientVo.setIntegral(clientVo.getIntegral() - clientIntegralDetailVo.getChangeIntegral());
            if (clientVo.getIntegral() < 0) {
                return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
            }
            clientIntegralDetail.setType((byte)5);      //5：后台减少
        } else if (clientIntegralDetailVo.getType() == 1) {
            clientVo.setIntegral(clientVo.getIntegral() + clientIntegralDetailVo.getChangeIntegral());
            clientIntegralDetail.setType((byte)1);      //1：后台增加
        } else {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改客户积分
        if (myClientMapper.updateIntegral(clientVo) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        //增加积分明细
        clientIntegralDetail.setId(UUID.randomUUID().toString());
        clientIntegralDetail.setClientId(clientIntegralDetailVo.getClientId());
        clientIntegralDetail.setCreateTime(new Date());
        clientIntegralDetail.setChangeIntegral(clientIntegralDetailVo.getChangeIntegral());
        clientIntegralDetail.setAfterChangeIntegral(clientVo.getIntegral());
        clientIntegralDetail.setHandledBy(clientIntegralDetailVo.getUserId());
        clientIntegralDetail.setRemark(clientIntegralDetailVo.getRemark());
        if (myClientMapper.insertIntegralDetail(clientIntegralDetail) != 1) {
            throw new CommonException(CommonResponse.CODE9, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查找所有客户
     * @return
     */
    public CommonResponse findAll(ClientVo clientVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myClientMapper.findCount() * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        //查找所有客户
        List<ClientVo> clientVos = myClientMapper.findAllPaged(clientVo, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("id", "客户编号"));
        titles.add(new Title("name", "客户姓名"));
        titles.add(new Title("levelId", "客户级别id"));
        titles.add(new Title("levelName", "客户级别"));
        titles.add(new Title("username", "客户用户名"));
        titles.add(new Title("password", "客户密码"));
        titles.add(new Title("phone", "客户电话"));
        titles.add(new Title("birthday", "客户生日"));
        titles.add(new Title("inviterId", "邀请人编号"));
        titles.add(new Title("inviterName", "邀请人姓名"));
        titles.add(new Title("integral", "钻石"));
        titles.add(new Title("address", "客户地址"));
        titles.add(new Title("postcode", "邮编"));
        titles.add(new Title("membershipNumber", "会员卡号"));
        titles.add(new Title("lastDealTime", "最近交易时间"));
        titles.add(new Title("createTime", "创建时间"));
        titles.add(new Title("disabled", "是否停用"));
        titles.add(new Title("remark", "备注"));
        CommonResult commonResult = new CommonResult(titles, clientVos, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查找客户
     * @param clientVo
     * @return
     */
    public CommonResponse findById(ClientVo clientVo) {
        clientVo = myClientMapper.findClient(clientVo);
        if (clientVo == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, clientVo, CommonResponse.MESSAGE1);
    }

    //

    /**
     * 查找所有积分明细
     * @param clientIntegralDetail
     * @param pageVo
     * @return
     */
    public CommonResponse findAllIntegralDetail(ClientIntegralDetail clientIntegralDetail, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage((int) Math.ceil(myClientMapper.findCountIntegralDetail(clientIntegralDetail) * 1.0 / pageVo.getPageSize()));
        pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
        //查找所有客户
        List<ClientIntegralDetail> clientIntegralDetails = myClientMapper.findAllPagedIntegralDetail(clientIntegralDetail, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("id", "积分明细id"));
        titles.add(new Title("clientId", "客户编号"));
        titles.add(new Title("createTime", "发生日期"));
        titles.add(new Title("type", "操作类型"));
        titles.add(new Title("changeIntegral", "改变积分"));
        titles.add(new Title("afterChangeIntegral", "改变后的积分"));
        titles.add(new Title("invoicesDate", "单据日期"));
        titles.add(new Title("invoicesId", "单据编号"));
        titles.add(new Title("invoicesType", "单据类型"));
        titles.add(new Title("handledBy", "经手人"));
        titles.add(new Title("remark", "备注"));
        CommonResult commonResult = new CommonResult(titles, clientIntegralDetails, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

}
