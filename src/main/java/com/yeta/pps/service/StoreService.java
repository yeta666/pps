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

import java.util.ArrayList;
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

    /**
     * 新增店铺，插入一套表
     * @param store
     * @return
     */
    @Transactional      //create语句不支持事务回滚，所有需要手动事务回滚
    public CommonResponse add(Store store) {
        //新增店铺
        if (storeMapper.add(store) != 1) {
            throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
        }
        Integer storeId = store.getId();
        try {
            //创建该分店的一套表
            tableMapper.addBankAccount(storeId);
            tableMapper.addFundOrder(storeId);
            /*tableMapper.addGoods(storeId);
            tableMapper.addGoodsGoodsLabel(storeId);
            tableMapper.addGoodsLabel(storeId);
            tableMapper.addGoodsPropertyKey(storeId);
            tableMapper.addGoodsPropertyValue(storeId);
            tableMapper.addGoodsSku(storeId);
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
            storeMapper.delete(store);
            tableMapper.deleteBankAccount(storeId);
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
            throw new CommonException(CommonResponse.CODE2, e.getMessage());
        }
        return new CommonResponse(CommonResponse.CODE1, store, CommonResponse.MESSAGE1);
    }

    /**
     * 初始化表数据
     * @return
     */
    @Transactional
    public CommonResponse initialize(Integer storeId) {
        //TODO
        //用户
        //角色
        //用户角色关系
        //角色功能关系
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改店铺
     * @param store
     * @return
     */
    public CommonResponse update(Store store) {
        //判断参数
        if (store.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改
        if (storeMapper.update(store) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
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
            List<Store> stores = storeMapper.findAllPaged(pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("店铺编号", "id"));
            titles.add(new Title("店铺名称", "name"));
            titles.add(new Title("地址", "address"));
            titles.add(new Title("店长", "boss"));
            titles.add(new Title("电话", "phone"));
            CommonResult commonResult = new CommonResult(titles, stores, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<Store> stores = storeMapper.findAll();
        return new CommonResponse(CommonResponse.CODE1, stores, CommonResponse.MESSAGE1);
    }
}
