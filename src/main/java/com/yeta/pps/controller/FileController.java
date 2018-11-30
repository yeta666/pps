package com.yeta.pps.controller;

import com.yeta.pps.service.FileService;
import com.yeta.pps.util.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件相关接口
 * @author YETA
 * @date 2018/08/28/16:02
 */
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
    @PostMapping(value = "/upload")
    public CommonResponse upload(@RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "type") Integer type) throws IOException {
        return fileService.upload(file, type);
    }

    /**
     * 文件清理接口
     */
    @GetMapping(value = "/clean")
    public void clean() {
        fileService.clean();
    }
}
