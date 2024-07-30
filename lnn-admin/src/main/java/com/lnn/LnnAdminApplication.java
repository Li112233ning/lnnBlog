package com.lnn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lnn.mapper")
public class LnnAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(LnnAdminApplication.class, args);
    }

}
