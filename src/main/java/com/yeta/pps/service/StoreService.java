package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.*;
import com.yeta.pps.po.*;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.System;
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
    private MyUserMapper myUserMapper;

    @Autowired
    private MyRoleMapper myRoleMapper;

    @Autowired
    private FunctionMapper functionMapper;

    @Autowired
    private MyClientMapper myClientMapper;

    /**
     * 新增店铺，插入一套表
     * @param storeVo
     * @return
     */
    @Transactional      //create语句不支持事务回滚，所有需要手动事务回滚
    public CommonResponse add(StoreVo storeVo) {
        //新增店长级别客户
        ClientVo clientVo = new ClientVo();
        clientVo.setId(UUID.randomUUID().toString().replace("-", ""));
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
        if (myClientMapper.add(clientVo) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }

        //新增店铺
        Store store = new Store();
        store.setName(storeVo.getName());
        store.setAddress(storeVo.getAddress());
        store.setClientId(clientVo.getId());
        if (storeMapper.add(store) != 1) {
            throw new CommonException(CommonResponse.ADD_ERROR);
        }
        Integer storeId = store.getId();
        System.out.println(storeId);
        try {
            //创建该分店的一套表
            //tableMapper.addBankAccount(storeId);
            //tableMapper.addFundOrder(storeId);
            /*tableMapper.addGoods(storeId);
            tableMapper.addGoodsGoodsLabel(storeId);
            tableMapper.addGoodsLabel(storeId);
            tableMapper.addGoodsPropertyKey(storeId);
            tableMapper.addGoodsPropertyValue(storeId);
            tableMapper.addGoodsSkuMethod(storeId);
            tableMapper.addGoodsType(storeId);
            tableMapper.addIncomeExpenses(storeId);
            tableMapper.addOrderGoodsSku(storeId);
            tableMapper.addProcurementApplyOrder(storeId);
            tableMapper.addProcurementResultOrder(storeId);
            tableMapper.addRole(storeId);
            tableMapper.addRoleFunction(storeId);
            tableMapper.addSellApplyOrder(storeId);
            tableMapper.addSellResultOrder(storeId);
            tableMapper.addStorageOrder(storeId);
            tableMapper.addSupplier(storeId);
            tableMapper.addUser(storeId);
            tableMapper.addUserRole(storeId);
            tableMapper.addWarehouse(storeId);*/
            //TODO
        } catch (Exception e) {
            //手动尽量回滚事务，如果这部分出现异常会导致回滚不成功
            //storeMapper.delete(storeVo);
            //tableMapper.deleteBankAccount(storeId);
            /*tableMapper.deleteFundOrder(storeId);
            tableMapper.deleteGoods(storeId);
            tableMapper.deleteGoodsGoodsLabel(storeId);
            tableMapper.deleteGoodsLabel(storeId);
            tableMapper.deleteGoodsPropertyKey(storeId);
            tableMapper.deleteGoodsPropertyValue(storeId);
            tableMapper.deleteGoodsSku(storeId);
            tableMapper.deleteGoodsType(storeId);
            tableMapper.deleteIncomeExpenses(storeId);
            tableMapper.deleteOrderGoodsSku(storeId);
            tableMapper.deleteProcurementApplyOrder(storeId);
            tableMapper.deleteProcurementResultOrder(storeId);
            tableMapper.deleteRole(storeId);
            tableMapper.deleteRoleFunction(storeId);
            tableMapper.deleteSellApplyOrder(storeId);
            tableMapper.deleteSellResultOrder(storeId);
            tableMapper.deleteStorageOrder(storeId);
            tableMapper.deleteSupplier(storeId);
            tableMapper.deleteUser(storeId);
            tableMapper.deleteUserRole(storeId);
            tableMapper.deleteWarehouse(storeId);*/
            //TODO
            throw new CommonException(CommonResponse.ADD_ERROR, e.getMessage());
        }
        
        return CommonResponse.success();
    }

    /**
     * 修改店铺
     * @param store
     * @return
     */
    public CommonResponse update(Store store) {
        //修改
        if (storeMapper.update(store) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
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
