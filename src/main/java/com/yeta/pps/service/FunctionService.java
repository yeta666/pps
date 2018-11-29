package com.yeta.pps.service;

import com.yeta.pps.mapper.FunctionMapper;
import com.yeta.pps.po.Function;
import com.yeta.pps.util.CommonResponse;
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

    public CommonResponse findAll() {
        //最终结果
        List<Function> result = new ArrayList<>();
        //查找所有功能
        List<Function> functions =  functionMapper.selectAll();
        //过滤一级功能
        List<Function> levelOneFunctions = functions.stream()
                .filter(function -> function.getLevel().toString().equals("1"))
                .collect(Collectors.toList());
        //遍历每个一级功能，找到每一个一级功能对应的二级功能
        for (Function levelOneFunction : levelOneFunctions) {
            List<Function> levelTwoFunctions = functions.stream()
                    .filter(function -> function.getParnetId().toString().equals(levelOneFunction.getId().toString()))
                    .collect(Collectors.toList());
            levelOneFunction.setSubFunctions(levelTwoFunctions);
            //遍历每个二级功能，找到每一个二级功能对应的三级功能
            for (Function levelTwoFunction : levelTwoFunctions) {
                List<Function> levelThreeFunctions = functions.stream()
                        .filter(function -> function.getParnetId().toString().equals(levelTwoFunction.getId().toString()))
                        .collect(Collectors.toList());
                levelTwoFunction.setSubFunctions(levelThreeFunctions);
            }
            result.add(levelOneFunction);
        }
        if (result.size() == 0) {
            return new CommonResponse(CommonResponse.CODE10, null, CommonResponse.MESSAGE10);
        }
        return new CommonResponse(CommonResponse.CODE1, result, CommonResponse.MESSAGE1);
    }
}

