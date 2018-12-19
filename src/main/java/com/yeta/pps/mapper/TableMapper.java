package com.yeta.pps.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author YETA
 * @date 2018/11/28/14:38
 */
public interface TableMapper {

    //创建表

    void addBankAccount(@Param(value = "storeId") Integer storeId);

    void addFundOrder(@Param(value = "storeId") Integer storeId);

    void addGoods(@Param(value = "storeId") Integer storeId);

    void addGoodsGoodsLabel(@Param(value = "storeId") Integer storeId);

    void addGoodsLabel(@Param(value = "storeId") Integer storeId);

    void addGoodsPropertyKey(@Param(value = "storeId") Integer storeId);

    void addGoodsPropertyValue(@Param(value = "storeId") Integer storeId);

    void addGoodsSku(@Param(value = "storeId") Integer storeId);

    void addGoodsType(@Param(value = "storeId") Integer storeId);

    void addIncomeExpenses(@Param(value = "storeId") Integer storeId);

    void addOrderGoodsSku(@Param(value = "storeId") Integer storeId);

    void addProcurementApplyOrder(@Param(value = "storeId") Integer storeId);

    void addProcurementResultOrder(@Param(value = "storeId") Integer storeId);

    void addRole(@Param(value = "storeId") Integer storeId);

    void addRoleFunction(@Param(value = "storeId") Integer storeId);

    void addSellApplyOrder(@Param(value = "storeId") Integer storeId);

    void addSellResultOrder(@Param(value = "storeId") Integer storeId);

    void addStorageOrder(@Param(value = "storeId") Integer storeId);

    void addSupplier(@Param(value = "storeId") Integer storeId);

    void addUser(@Param(value = "storeId") Integer storeId);

    void addUserRole(@Param(value = "storeId") Integer storeId);

    void addWarehouse(@Param(value = "storeId") Integer storeId);

    //删除表

    void deleteBankAccount(@Param(value = "storeId") Integer storeId);

    void deleteFundOrder(@Param(value = "storeId") Integer storeId);

    void deleteGoods(@Param(value = "storeId") Integer storeId);

    void deleteGoodsGoodsLabel(@Param(value = "storeId") Integer storeId);

    void deleteGoodsLabel(@Param(value = "storeId") Integer storeId);

    void deleteGoodsPropertyKey(@Param(value = "storeId") Integer storeId);

    void deleteGoodsPropertyValue(@Param(value = "storeId") Integer storeId);

    void deleteGoodsSku(@Param(value = "storeId") Integer storeId);

    void deleteGoodsType(@Param(value = "storeId") Integer storeId);

    void deleteIncomeExpenses(@Param(value = "storeId") Integer storeId);

    void deleteOrderGoodsSku(@Param(value = "storeId") Integer storeId);

    void deleteProcurementApplyOrder(@Param(value = "storeId") Integer storeId);

    void deleteProcurementResultOrder(@Param(value = "storeId") Integer storeId);

    void deleteRole(@Param(value = "storeId") Integer storeId);

    void deleteRoleFunction(@Param(value = "storeId") Integer storeId);

    void deleteSellApplyOrder(@Param(value = "storeId") Integer storeId);

    void deleteSellResultOrder(@Param(value = "storeId") Integer storeId);

    void deleteStorageOrder(@Param(value = "storeId") Integer storeId);

    void deleteSupplier(@Param(value = "storeId") Integer storeId);

    void deleteUser(@Param(value = "storeId") Integer storeId);

    void deleteUserRole(@Param(value = "storeId") Integer storeId);

    void deleteWarehouse(@Param(value = "storeId") Integer storeId);
}
