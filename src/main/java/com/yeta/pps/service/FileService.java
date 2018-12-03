package com.yeta.pps.service;

import com.yeta.pps.util.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件相关逻辑处理
 * @author YETA
 * @date 2018/08/28/21:15
 */
@Service
public class FileService implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(FileService.class);
    public static File path;
    public static File upload;
    public static File download;

    /**
     * 在开发测试模式时，得到的地址为：{项目根目录}/target/classes/upload/
     * 在打包成jar正式发布时，得到的地址为：{发布jar包目录}/upload/
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
        path = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!path.exists()) {
            path = new File("");        //这一句才是精华
        }
        LOG.info("获取项目根目录，{}", path.getAbsolutePath());

        upload = new File(path.getAbsolutePath(), "upload/");
        if (!upload.exists()) {
            LOG.info("获取upload目录失败，创建upload目录，{}", upload.getAbsolutePath());
            upload.mkdirs();
        }
        LOG.info("获取upload目录成功，{}", upload.getAbsolutePath());

        download = new File(path.getAbsolutePath(), "download/");
        if (!download.exists()) {
            LOG.info("获取download目录失败，创建download目录，{}", download.getAbsolutePath());
            download.mkdirs();
        }
        LOG.info("获取download目录成功，{}", download.getAbsolutePath());
    }

    /**
     * 文件上传
     * @param multipartFile
     * @param type
     * @return
     * @throws IOException
     */
    public CommonResponse upload(MultipartFile multipartFile, Integer type) throws IOException {
        String dir;
        //判断文件类型
        if (type == 1) {        //商品
            dir = "goods/";
        } else if (type == 2) {     //商品
            dir = "vehicle/";
        } else if (type == 3) {     //订单
            dir = "order/";
        } else if (type == 4) {     //系统
            dir = "system/";
        } else {
            return new CommonResponse(CommonResponse.CODE12, null, CommonResponse.MESSAGE12);
        }
        //保存文件
        File file = new File(upload, dir);
        if (!file.exists()) {
            file.mkdir();
        }
        File file1 = new File(file, System.currentTimeMillis() + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")));
        multipartFile.transferTo(file1);
        //返回图片路径
        return new CommonResponse(CommonResponse.CODE1, "/upload/" + dir + file1.getName(), CommonResponse.MESSAGE1);
    }

    /**
     * 定时任务
     * 清空download目录
     */
    @Scheduled(cron = "0 0 3 * * ?")      //每天3点执行
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void clean() {
        //记录开始日志
        LOG.info("定时任务【清空download目录】开始...");
        if (download.exists()) {
            File[] files = download.listFiles();
            for (File file : files) {
                LOG.info("{}", file.getName());
                if (file.isFile()) {
                    file.delete();
                }
            }
            LOG.info("定时任务【清空download目录】结束...");
        } else {
            LOG.info("定时任务【清空download目录】失败，失败原因【目录不存在】...");
        }
    }
}
