package com.yeta.pps.controller;

import com.yeta.pps.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能相关接口
 * @author YETA
 * @date 2018/11/29/19:31
 */
@RestController
public class FunctionController {

    @Autowired
    private FunctionService functionService;
}
