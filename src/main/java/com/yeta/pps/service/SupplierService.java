package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyFundMapper;
import com.yeta.pps.mapper.MyProcurementMapper;
import com.yeta.pps.mapper.MyStorageMapper;
import com.yeta.pps.mapper.MySupplierMapper;
import com.yeta.pps.util.*;
import com.yeta.pps.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 供应商相关逻辑处理
 * @author YETA
 * @date 2018/12/04/10:33
 */
@Service
public class SupplierService {

    @Autowired
    private MySupplierMapper mySupplierMapper;

    @Autowired
    private SystemUtil systemUtil;

    @Autowired
    private FundUtil fundUtil;

    @Autowired
    private MyFundMapper myFundMapper;

    @Autowired
    private PrimaryKeyUtil primaryKeyUtil;

    /**
     * 新增供应商
     * @param supplierVo
     * @return
     */
    public CommonResponse add(SupplierVo supplierVo) {
        //设置初始属性
        supplierVo.setId(primaryKeyUtil.getPrimaryKeyMethod(mySupplierMapper.findPrimaryKey(supplierVo), "gys"));

        //新增
        if (mySupplierMapper.add(supplierVo) != 1) {
            return CommonResponse.error(CommonResponse.ADD_ERROR);
        }

        //判断系统是否开账
        if (systemUtil.judgeStartBillMethod(supplierVo.getStoreId())) {
            fundUtil.addOpeningFundTargetCheckOrderMethod(0, supplierVo.getStoreId(), supplierVo.getId(), 0.0, 0.0, supplierVo.getUserId());
        }
        return CommonResponse.success();
    }

    /**
     * 删除供应商
     * @param supplierVos
     * @return
     */
    @Transactional
    public CommonResponse delete(List<SupplierVo> supplierVos) {
        supplierVos.stream().forEach(supplierVo -> {
            //判断供应商是否使用
            FundTargetCheckOrderVo ftcoVo = new FundTargetCheckOrderVo();
            ftcoVo.setStoreId(supplierVo.getStoreId());
            ftcoVo.setTargetId(supplierVo.getId());
            if (myFundMapper.findFundTargetCheckOrder(ftcoVo).size() > 0) {
                throw new CommonException(CommonResponse.DELETE_ERROR, CommonResponse.USED_ERROR);
            }

            //删除供应商
            if (mySupplierMapper.delete(supplierVo) != 1) {
                throw new CommonException(CommonResponse.DELETE_ERROR);
            }
        });
        return CommonResponse.success();
    }

    /**
     * 修改供应商
     * @param supplierVo
     * @return
     */
    public CommonResponse update(SupplierVo supplierVo) {
        //修改
        if (mySupplierMapper.update(supplierVo) != 1) {
            return CommonResponse.error(CommonResponse.UPDATE_ERROR);
        }
        return CommonResponse.success();
    }

    /**
     * 查询所有供应商
     * @param supplierVo
     * @param pageVo
     * @return
     */
    public CommonResponse findAll(SupplierVo supplierVo, PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage((int) Math.ceil(mySupplierMapper.findCount(supplierVo) * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<SupplierVo> supplierVos = mySupplierMapper.findAllPaged(supplierVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("供应商编号", "id"));
            titles.add(new Title("供应商名称", "name"));
            titles.add(new Title("联系人", "contacts"));
            titles.add(new Title("联系电话", "contactNumber"));
            titles.add(new Title("联系地址", "contactAddress"));
            titles.add(new Title("传真", "fax"));
            titles.add(new Title("备注", "remark"));
            CommonResult commonResult = new CommonResult(titles, supplierVos, pageVo);
            return CommonResponse.success(commonResult);
        }
        //不分页
        List<SupplierVo> supplierVos = mySupplierMapper.findAll(supplierVo);
        return CommonResponse.success(supplierVos);
    }

    /**
     * 根据id查询供应商
     * @param supplierVo
     * @return
     */
    public CommonResponse findById(SupplierVo supplierVo) {
        supplierVo = mySupplierMapper.findById(supplierVo);
        if (supplierVo == null) {
            return CommonResponse.error(CommonResponse.FIND_ERROR);
        }
        return CommonResponse.success(supplierVo);
    }
}
