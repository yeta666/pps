package com.yeta.pps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//扫描Mybatis mapper包路径
@MapperScan(basePackages = "com.yeta.pps.mapper")
public class PpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PpsApplication.class, args);
    }
}
