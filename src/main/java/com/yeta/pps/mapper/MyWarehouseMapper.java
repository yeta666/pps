package com.yeta.pps.mapper;

import com.yeta.pps.po.User;
import com.yeta.pps.po.Warehouse;
import com.yeta.pps.vo.WarehouseUserVo;
import com.yeta.pps.vo.WarehouseVo;

import java.util.List;

public interface MyWarehouseMapper {

    int add(WarehouseVo warehouseVo);

    int delete(WarehouseVo warehouseVo);

    int update(WarehouseVo warehouseVo);

    List<Warehouse> findAll(WarehouseVo warehouseVo);

    Warehouse findById(WarehouseVo warehouseVo);

    //

    int addWarehouseUser(WarehouseUserVo departmentUserVo);

    int deleteWarehouseUser(WarehouseUserVo departmentUserVo);

    int deleteUserWarehouse(WarehouseUserVo departmentUserVo);

    List<User> findWarehouseUser(WarehouseUserVo departmentUserVo);
}