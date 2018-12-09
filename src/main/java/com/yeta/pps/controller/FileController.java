package com.yeta.pps.controller;

import com.yeta.pps.service.FileService;
import com.yeta.pps.util.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件相关接口
 * @author YETA
 * @date 2018/08/28/16:02
 */
@Api(value = "文件相关接口")
@RestController
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传接口
     * @param file
     * @param type
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "上传文件", notes = "type(1：商品，2：银行账户)")
    @PostMapping(value = "/upload")
    public CommonResponse upload(@RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "type") Integer type) throws IOException {
        return fileService.upload(file, type);
    }
}
