package com.yeta.pps.mapper;

import com.yeta.pps.po.Supplier;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.SupplierVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MySupplierMapper {

    int add(SupplierVo supplierVo);

    int delete(SupplierVo supplierVo);

    int update(SupplierVo supplierVo);

    int findCount(SupplierVo supplierVo);

    List<Supplier> findAll(SupplierVo supplierVo);

    List<Supplier> findAllPaged(@Param(value = "supplierVo") SupplierVo supplierVo, @Param(value = "pageVo") PageVo pageVo);

    Supplier findById(SupplierVo supplierVo);
}