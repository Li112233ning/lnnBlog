package com.lnn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lnn.mapper")
public class LnnBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LnnBlogApplication.class, args);
    }

}
