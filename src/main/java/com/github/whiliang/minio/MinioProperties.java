package com.github.whiliang.minio;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * MinioProperties
 *
 * @author whiliang
 * @date 2021-06-01
 */
@ConfigurationProperties("spring.minio")
public class MinioProperties {
    /**
     * enable
     */
    private boolean enable;

    /**
     * Minio Internet URL
     */
    private String intranetUrl = "http://192.168.1.101:9000";

    /**
     * Minio Internet URL
     */
    private String internetUrl = "http://47.3.111.33:9000";

    /**
     * Access key
     */
    private String accessKey = "loginName";

    /**
     * Secret key
     */
    private String secretKey = "password";

    /**
     * Bucket name for the application
     */
    private String bucket;

    /**
     * Define the connect timeout for the Minio Client.
     */
    private Duration connectTimeout = Duration.ofSeconds(10);

    /**
     * Define the write timeout for the Minio Client.
     */
    private Duration writeTimeout = Duration.ofSeconds(60);

    /**
     * Define the read timeout for the Minio Client.
     */
    private Duration readTimeout = Duration.ofSeconds(10);

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getIntranetUrl() {
        return intranetUrl;
    }

    public void setIntranetUrl(String intranetUrl) {
        this.intranetUrl = intranetUrl;
    }

    public String getInternetUrl() {
        return internetUrl;
    }

    public void setInternetUrl(String internetUrl) {
        this.internetUrl = internetUrl;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Duration getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Duration writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }
}
