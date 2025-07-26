package com.hclx.hclx_ai1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hclx.hclx_ai1.mapper")
public class HclxAi1Application {

    public static void main(String[] args) {
        SpringApplication.run(HclxAi1Application.class, args);
    }

}
