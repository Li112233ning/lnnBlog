package com.lnn.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliOSSProperties {

    //@Value("${aliyun.oss.endpoint}")
    private String endpoint ;
    //@Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId ;
    //@Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret ;
    //@Value("${aliyun.oss.bucketName}")
    private String bucketName ;
}
