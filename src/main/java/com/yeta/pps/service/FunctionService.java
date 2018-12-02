package com.yeta.pps.service;

import com.yeta.pps.mapper.FunctionMapper;
import com.yeta.pps.po.Function;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.FunctionMapVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        /*FunctionMapVo functionMapVo = new FunctionMapVo();
        //过滤一级功能
        List<Function> level1 = functions.stream().filter(function -> function.getLevel().toString().equals("1")).collect(Collectors.toList());
        //过滤二级功能
        List<Function> level2 = functions.stream().filter(function -> function.getLevel().toString().equals("2")).collect(Collectors.toList());
        //过滤三级功能
        List<Function> level3 = functions.stream().filter(function -> function.getLevel().toString().equals("3")).collect(Collectors.toList());
        //封装返回结果
        functionMapVo.setLevel1(level1);
        functionMapVo.setLevel2(level2);
        functionMapVo.setLevel3(level3);
        return new CommonResponse(CommonResponse.CODE1, functionMapVo, CommonResponse.MESSAGE1);*/
        List<Integer> ids = new ArrayList<>();
        functions.stream().forEach(function -> ids.add(function.getId()));
        return new CommonResponse(CommonResponse.CODE1, ids, CommonResponse.MESSAGE1);
    }
}

