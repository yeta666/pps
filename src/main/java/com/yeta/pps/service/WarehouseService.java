package com.yeta.pps.service;

import com.yeta.pps.mapper.MyWarehouseMapper;
import com.yeta.pps.po.Warehouse;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.WarehouseUserVo;
import com.yeta.pps.vo.WarehouseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 仓库相关逻辑处理
 * @author YETA
 * @date 2018/11/30/14:42
 */
@Service
public class WarehouseService {

    @Autowired
    private MyWarehouseMapper myWarehouseMapper;

    /**
     * 新增仓库
     * @param warehouseVo
     * @return
     */
    public CommonResponse add(WarehouseVo warehouseVo) {
        //判断参数
        if (warehouseVo.getName() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //新增
        if (myWarehouseMapper.add(warehouseVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除仓库
     * @param warehouseVo
     * @return
     */
    @Transactional
    public CommonResponse delete(WarehouseVo warehouseVo) {
        //判断参数
        if (warehouseVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //删除仓库
        if (myWarehouseMapper.delete(warehouseVo) != 1) {
            return new CommonResponse(CommonResponse.CODE8, null, CommonResponse.MESSAGE8);
        }
        //删除仓库用户关系
        WarehouseUserVo warehouseUserVo = new WarehouseUserVo(warehouseVo.getStoreId(), warehouseVo.getId());
        myWarehouseMapper.deleteWarehouseUser(warehouseUserVo);
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改仓库
     * @param warehouseVo
     * @return
     */
    public CommonResponse update(WarehouseVo warehouseVo) {
        //判断参数
        if (warehouseVo.getId() == null || warehouseVo.getName() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //修改
        if (myWarehouseMapper.update(warehouseVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查询所有仓库
     * @param warehouseVo
     * @return
     */
    public CommonResponse findAll(WarehouseVo warehouseVo, PageVo pageVo) {
        //查询所有页数
        pageVo.setTotalPage(myWarehouseMapper.findCount(warehouseVo) / pageVo.getPageSize());
        List<Warehouse> warehouses = myWarehouseMapper.findAll(warehouseVo, pageVo);
        //封装返回结果
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("仓库名", "name"));
        titles.add(new Title("联系人", "contacts"));
        titles.add(new Title("联系电话", "contactNumber"));
        titles.add(new Title("地址", "address"));
        titles.add(new Title("邮编", "postcode"));
        titles.add(new Title("备注", "remark"));
        CommonResult commonResult = new CommonResult(titles, warehouses, pageVo);
        return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
    }

    /**
     * 根据仓库id查询仓库
     * @param warehouseVo
     * @return
     */
    public CommonResponse findById(WarehouseVo warehouseVo) {
        //判断参数
        if (warehouseVo.getId() == null) {
            return new CommonResponse(CommonResponse.CODE3, null, CommonResponse.MESSAGE3);
        }
        //根据仓库id查询仓库
        Warehouse department = myWarehouseMapper.findById(warehouseVo);
        if (department == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, department, CommonResponse.MESSAGE1);
    }
}
