package com.yeta.pps.mapper;

import com.yeta.pps.po.Warehouse;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.WarehouseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyWarehouseMapper {

    int add(WarehouseVo warehouseVo);

    int delete(WarehouseVo warehouseVo);

    int update(WarehouseVo warehouseVo);

    int findCount(WarehouseVo warehouseVo);

    List<Warehouse> findAll(WarehouseVo warehouseVo);

    List<Warehouse> findAllPaged(@Param(value = "warehouseVo") WarehouseVo warehouseVo, @Param(value = "pageVo") PageVo pageVo);

    Warehouse findById(WarehouseVo warehouseVo);
}