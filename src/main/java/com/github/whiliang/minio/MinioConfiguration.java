package com.github.whiliang.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * MinioConfiguration
 *
 * @author whiliang
 * @date 2021-06-01
 */
@Configuration
@ConditionalOnClass(MinioClient.class)
@EnableConfigurationProperties(MinioProperties.class)
@ComponentScan("com.github.whiliang.minio")
@ConditionalOnProperty(
        prefix = "spring.minio",
        name = "enable",
        havingValue = "true"
)
public class MinioConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioConfiguration.class);

    @Autowired
    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() throws MinioException {

        MinioClient minioClient;
        try {
            minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getIntranetUrl())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                    .build();
            minioClient.setTimeout(
                    minioProperties.getConnectTimeout().toMillis(),
                    minioProperties.getWriteTimeout().toMillis(),
                    minioProperties.getReadTimeout().toMillis()
            );
        } catch (Exception e) {
            LOGGER.error("Error while connecting to Minio", e);
            throw new MinioException("Cannot connect to minio", e);
        }
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Checking if bucket {} exists", minioProperties.getBucket());
            }
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucket()).build());
            if (!found) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("bucket {} not exists", minioProperties.getBucket());
                }
                MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build();
                //创建bucket
                minioClient.makeBucket(makeBucketArgs);
                //设置bucket read/write policy
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(minioProperties.getBucket()).config(MinioService.getBucketPolicyJson(minioProperties.getBucket())).build());
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("bucket {} exists", minioProperties.getBucket());
                }
            }
        } catch (Exception e) {
            throw new MinioException("Cannot create bucket", e);
        }
        return minioClient;
    }
}
