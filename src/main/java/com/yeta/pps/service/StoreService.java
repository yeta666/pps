package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyClientMapper;
import com.yeta.pps.mapper.StoreMapper;
import com.yeta.pps.mapper.SystemMapper;
import com.yeta.pps.mapper.TableMapper;
import com.yeta.pps.po.*;
import com.yeta.pps.util.*;
import com.yeta.pps.vo.ClientVo;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.StoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 店铺相关逻辑处理
 * @author YETA
 * @date 2018/11/28/18:06
 */
@Service
public class StoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private MyClientMapper myClientMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private PrimaryKeyUtil primaryKeyUtil;

    @Autowired
    private StoreClientUtil storeClientUtil;

    /**
     * 新增店铺，插入一套表
     * @param storeVo
     * @param check
     * @return
     */
    @Transactional      //create语句不支持事务回滚，所有需要手动事务回滚
    public CommonResponse add(StoreVo storeVo, String check) {
        //判断权限
        if (!storeClientUtil.checkMethod(check)) {
            return CommonResponse.error(CommonResponse.PERMISSION_ERROR);
        }

        //新增店长级别客户
        ClientVo clientVo = new ClientVo();
        clientVo.setId(primaryKeyUtil.getPrimaryKeyMethod(myClientMapper.findPrimaryKey(), "kh"));
        clientVo.setName(storeVo.getClientName());
        clientVo.setUsername(storeVo.getClientPhone());
        clientVo.setPassword(storeVo.getClientPhone().substring(storeVo.getClientPhone().length() - 4));
        clientVo.setPhone(storeVo.getClientPhone());

        //判断客户级别是否存在
        if (myClientMapper.findClientLevelById(new ClientLevel(1)) == null) {
            throw new CommonException(CommonResponse.ADD_ERROR, "店长客户级别不存在");
        }
        clientVo.setLevelId(1);

        //判断会员卡号是否存在
        String membershipNumber = storeVo.getClientMembershipNumber();
        if (myClientMapper.findMembershipNumberByNumber(new MembershipNumber(membershipNumber)) == null) {
            throw new CommonException(CommonResponse.ADD_ERROR, "会员卡号不存在");
        }

        //判断会员卡号是否使用
        Client client = new Client();
        client.setMembershipNumber(membershipNumber);
        if (myClientMapper.findClient(client) != null) {
            throw new CommonException(CommonResponse.ADD_ERROR, "会员卡号已被使用");
        }
        clientVo.setMembershipNumber(storeVo.getClientMembershipNumber());
        clientVo.setDisabled((byte) 0);
        clientVo.setCreateTime(new Date());

        //新增店长客户
        if (myClientMapper.add(clientVo) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }

        //新增店铺客户关系
        List<Store> stores = storeMapper.findAll();
        stores.stream().forEach(store -> {
            StoreClient storeClient = new StoreClient(store.getId(), clientVo.getId());
            storeClient.setIntegral(0);
            storeClient.setNeedInMoneyOpening(0.0);
            storeClient.setAdvanceInMoneyOpening(0.0);
            storeClient.setPushMoney(0.0);
            if (myClientMapper.addStoreClient(storeClient) != 1) {
                throw new CommonException(CommonResponse.ADD_ERROR);
            }
        });

        //新增店铺
        Store store = new Store();
        store.setName(storeVo.getName());
        store.setAddress(storeVo.getAddress());
        store.setClientId(clientVo.getId());
        if (storeMapper.add(store) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }
        Integer storeId = store.getId();

        //新增店铺客户关系
        List<ClientVo> clientVos = myClientMapper.findAll(new ClientVo());
        clientVos.stream().forEach(vo -> {
            StoreClient storeClient = new StoreClient(storeId, vo.getId());
            storeClient.setIntegral(0);
            storeClient.setNeedInMoneyOpening(0.0);
            storeClient.setAdvanceInMoneyOpening(0.0);
            storeClient.setPushMoney(0.0);
            if (myClientMapper.addStoreClient(storeClient) != 1) {
                throw new CommonException(CommonResponse.ADD_ERROR);
            }
        });

        //新增店铺系统设置
        if (systemMapper.addStoreSystem(new SSystem(storeId)) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }

        try {
            //新增一套表
            tableMapper.add(storeId);
        } catch (Exception e) {

            //删除店铺系统设置
            systemMapper.deleteStoreSystem(new SSystem(storeId));

            //删除店铺客户关系
            StoreClient storeClient = new StoreClient(store.getId(), storeVo.getClientId());
            myClientMapper.deleteStoreClientByStoreId(storeClient);
            myClientMapper.deleteStoreClientByClientId(storeClient);

            //删除店铺
            storeMapper.delete(store);

            //删除店长客户
            myClientMapper.delete(clientVo);

            //删除一套表
            tableMapper.delete(storeId);
            throw new CommonException(CommonResponse.ADD_ERROR, e.getMessage());
        }
        
        return CommonResponse.success();
    }

    /**
     * 删除店铺接口
     * @param store
     * @return
     */
    @Transactional
    public CommonResponse delete(Store store) {
        //删除店铺系统设置
        systemMapper.deleteStoreSystem(new SSystem(store.getId()));

        StoreVo storeVo = storeMapper.findById(store);

        //删除店铺客户关系
        StoreClient storeClient = new StoreClient(store.getId(), storeVo.getClientId());
        myClientMapper.deleteStoreClientByStoreId(storeClient);
        myClientMapper.deleteStoreClientByClientId(storeClient);

        //删除店铺
        storeMapper.delete(store);

        //删除店长客户
        myClientMapper.delete(new ClientVo(storeVo.getClientId()));

        //删除一套表
        tableMapper.delete(store.getId());
        
        return CommonResponse.success();
    }

    /**
     * 修改店铺
     * @param store
     * @param check
     * @return
     */
    public CommonResponse update(Store store, String check) {
        //判断权限
        if (!storeClientUtil.checkMethod(check)) {
            return CommonResponse.error(CommonResponse.PERMISSION_ERROR);
        }

        //修改
        if (storeMapper.update(store) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        return CommonResponse.success();
    }

    /**
     * 增加店铺剩余短信条数
     * @param store
     * @param check
     * @return
     */
    public CommonResponse increaseSMSQuantity(Store store, String check) {
        //判断权限
        if (!storeClientUtil.checkMethod(check)) {
            return CommonResponse.error(CommonResponse.PERMISSION_ERROR);
        }

        if (store.getId() == null) {
            //所有店铺
            storeMapper.increaseSMSQuantity();
        } else {
            if (storeMapper.increaseSMSQuantityId(store) != 1) {
                return CommonResponse.error(CommonResponse.UPDATE_ERROR);
            }
        }

        return CommonResponse.success();
    }

    /**
     * 查询所有店铺
     * @param pageVo
     * @return
     */
    public CommonResponse findAll(PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage((int) Math.ceil(storeMapper.findCount() * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<StoreVo> vos = storeMapper.findPaged(pageVo);

            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("店铺编号", "id"));
            titles.add(new Title("店铺名称", "name"));
            titles.add(new Title("店铺地址", "address"));
            titles.add(new Title("店长会员编号", "clientId"));
            titles.add(new Title("店长姓名", "clientName"));
            titles.add(new Title("店长电话", "clientPhone"));
            titles.add(new Title("店长会员编号", "clientMembershipNumber"));
            titles.add(new Title("店铺剩余短信条数", "smsQuantity"));
            CommonResult commonResult = new CommonResult(titles, vos, pageVo);
            return CommonResponse.success(commonResult);
        }
        //不分页
        List<Store> stores = storeMapper.findAll();
        return CommonResponse.success(stores);
    }

    /**
     * 根据编号查询店铺
     * @param store
     * @return
     */
    public CommonResponse findById(Store store) {
        StoreVo storeVo = storeMapper.findById(store);
        if (storeVo == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }
        return CommonResponse.success(storeVo);
    }
}
