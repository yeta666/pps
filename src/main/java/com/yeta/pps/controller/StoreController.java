package com.yeta.pps.controller;

import com.yeta.pps.po.Store;
import com.yeta.pps.service.StoreService;
import com.yeta.pps.util.CommonResponse;
import com.yeta.pps.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 店铺相关接口
 * @author YETA
 * @date 2018/11/28/18:59
 */
@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    /**
     * 新增店铺接口
     * @param store
     * @return
     */
    @PostMapping(value = "/stores")
    public CommonResponse add(@RequestBody @Valid Store store) {
        return storeService.add(store);
    }

    /**
     * 新增店铺初始化接口
     * @param userVo
     * @return
     */
    @PostMapping(value = "/stores/initialization")
    public CommonResponse initialize(@RequestBody @Valid UserVo userVo) {
        return storeService.initialize(userVo);
    }
}
