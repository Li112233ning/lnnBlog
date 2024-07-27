package com.lnn.config;

import com.lnn.utils.AliOSSProperties;
import com.lnn.utils.AliOSSUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，创建AliOssUtil对象
 */
@Configuration
@Slf4j
public class OssConfiguration {

    @Bean
    @ConditionalOnMissingBean//保证容器只有一个aliOssUtil
    public AliOSSUtils aliOssUtil(AliOSSProperties aliOssProperties){
        return new AliOSSUtils(aliOssProperties.getEndpoint(),aliOssProperties.getAccessKeyId(),aliOssProperties.getAccessKeySecret(),aliOssProperties.getBucketName());
    }

}
