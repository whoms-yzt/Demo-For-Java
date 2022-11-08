package com.whom.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MinioClientConfig {

    @Value("${minio.local.endpoint}")
    private String endpoint;
    @Value("${minio.local.accessKey}")
    private String accessKey;
    @Value("${minio.local.secretKey}")
    private String secretKey;

    /**
     * 注入minio客户端
     *
     * @author zhitong
     * @date 2022/9/23 15:56
     */
    @Bean
    public MinioClient getMinioClient(){

        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey,secretKey)
                .build();
    }
}
