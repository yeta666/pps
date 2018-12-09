package com.yeta.pps.service;

import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MySupplierMapper;
import com.yeta.pps.po.Supplier;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.util.CommonResult;
import com.yeta.pps.util.Title;
import com.yeta.pps.vo.PageVo;
import com.yeta.pps.vo.SupplierVo;
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

    /**
     * 新增供应商
     * @param supplierVo
     * @return
     */
    public CommonResponse add(SupplierVo supplierVo) {
        //TODO
        //新增
        if (mySupplierMapper.add(supplierVo) != 1) {
            return new CommonResponse(CommonResponse.CODE7, null, CommonResponse.MESSAGE7);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 删除供应商
     * @param supplierVos
     * @return
     */
    @Transactional
    public CommonResponse delete(List<SupplierVo> supplierVos) {
        supplierVos.stream().forEach(supplierVo -> {
            //删除供应商
            if (mySupplierMapper.delete(supplierVo) != 1) {
                throw new CommonException(CommonResponse.CODE8, CommonResponse.MESSAGE8);
            }
        });
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 修改供应商
     * @param supplierVo
     * @return
     */
    public CommonResponse update(SupplierVo supplierVo) {
        //修改
        if (mySupplierMapper.update(supplierVo) != 1) {
            return new CommonResponse(CommonResponse.CODE9, null, CommonResponse.MESSAGE9);
        }
        return new CommonResponse(CommonResponse.CODE1, null, CommonResponse.MESSAGE1);
    }

    /**
     * 查询所有供应商
     * @param supplierVo
     * @return
     */
    public CommonResponse findAll(SupplierVo supplierVo, PageVo pageVo) {
        //分页
        if (pageVo.getPage() != null && pageVo.getPageSize() != null) {
            //查询所有页数
            pageVo.setTotalPage((int) Math.ceil(mySupplierMapper.findCount(supplierVo) * 1.0 / pageVo.getPageSize()));
            pageVo.setStart(pageVo.getPageSize() * (pageVo.getPage() - 1));
            List<Supplier> suppliers = mySupplierMapper.findAllPaged(supplierVo, pageVo);
            //封装返回结果
            List<Title> titles = new ArrayList<>();
            titles.add(new Title("供应商编号", "id"));
            titles.add(new Title("供应商名称", "name"));
            titles.add(new Title("联系人", "contacts"));
            titles.add(new Title("联系电话", "contactNumber"));
            titles.add(new Title("联系地址", "contactAddress"));
            titles.add(new Title("传真", "fax"));
            titles.add(new Title("备注", "remark"));
            CommonResult commonResult = new CommonResult(titles, suppliers, pageVo);
            return new CommonResponse(CommonResponse.CODE1, commonResult, CommonResponse.MESSAGE1);
        }
        //不分页
        List<Supplier> suppliers = mySupplierMapper.findAll(supplierVo);
        return new CommonResponse(CommonResponse.CODE1, suppliers, CommonResponse.MESSAGE1);
    }

    /**
     * 根据id查询供应商
     * @param supplierVo
     * @return
     */
    public CommonResponse findById(SupplierVo supplierVo) {
        Supplier supplier = mySupplierMapper.findById(supplierVo);
        if (supplier == null) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, supplier, CommonResponse.MESSAGE1);
    }
}
