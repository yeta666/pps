package com.yeta.pps.service;

import com.yeta.pps.mapper.FunctionMapper;
import com.yeta.pps.po.Function;
import com.yeta.pps.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能相关逻辑处理
 * @author YETA
 * @date 2018/11/29/18:42
 */
@Service
public class FunctionService {

    @Autowired
    private FunctionMapper functionMapper;

    /**
     * 获取所有功能
     * @return
     */
    public CommonResponse findAll() {
        //查找所有功能
        List<Function> functions =  functionMapper.selectAll();
        List<Integer> ids = new ArrayList<>();
        functions.stream().forEach(function -> ids.add(function.getId()));
        return CommonResponse.success(ids);
    }
}

