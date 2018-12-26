package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyGoodsMapper;
import com.yeta.pps.mapper.MyUserMapper;
import com.yeta.pps.mapper.MyWarehouseMapper;
import com.yeta.pps.po.GoodsSku;
import com.yeta.pps.po.Warehouse;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.*;
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

    @Autowired
    private MyGoodsMapper myGoodsMapper;

    @Autowired
    private MyUserMapper myUserMapper;

    /**
     * 新增仓库
     * @param warehouseVo
     * @return
     */
    public CommonResponse add(WarehouseVo warehouseVo) {
        //新增
        if (myWarehouseMapper.add(warehouseVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }

        //查询所有商品规格
        List<GoodsSku> goodsSkus = myGoodsMapper.findAllGoodsSku(new GoodsSkuVo(warehouseVo.getStoreId()));
        goodsSkus.stream().forEach(goodsSku -> {
            //新增商品规格仓库关系
            if (myGoodsMapper.initializeOpening(new WarehouseGoodsSkuVo(warehouseVo.getStoreId(), warehouseVo.getId(), goodsSku.getId())) != 1) {
                throw new CommonException(CommonResponse.CODE7, CommonResponse.MESSAGE7);
            }
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除仓库
     * @param warehouseVos
     * @return
     */
    @Transactional
    public CommonResponse delete(List<WarehouseVo> warehouseVos) {
        warehouseVos.stream().forEach(warehouseVo -> {
            //判断仓库是否使用
            if (myUserMapper.findAll(new UserVo(warehouseVo.getStoreId(), warehouseVo.getId())).size() > 0) {
                throw new CommonException(CommonResponse.CODE18, CommonResponse.MESSAGE18);
            }
            //删除仓库
            if (myWarehouseMapper.delete(warehouseVo) != 1) {
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改仓库
     * @param warehouseVo
     * @return
     */
    public CommonResponse update(WarehouseVo warehouseVo) {
        //判断参数
        if (warehouseVo.getId() == null) {
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
        //分页
        if (pageVo.getPageSize() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage((int) Math.ceil(myWarehouseMapper.findCount(warehouseVo) * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<Warehouse> warehouses = myWarehouseMapper.findAllPaged(warehouseVo, pageVo);
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
        //不分页
        List<Warehouse> warehouses = myWarehouseMapper.findAll(warehouseVo);
        return new CommonResponse(CommonResponse.CODE1, warehouses, CommonResponse.MESSAGE1);
    }

    /**
     * 根据仓库id查询仓库
     * @param warehouseVo
     * @return
     */
    public CommonResponse findById(WarehouseVo warehouseVo) {
        //根据仓库id查询仓库
        Warehouse warehouse = myWarehouseMapper.findById(warehouseVo);
        if (warehouse == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, warehouse, CommonResponse.MESSAGE1);
    }
}
