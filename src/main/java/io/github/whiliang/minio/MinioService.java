package io.github.whiliang.minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * Minio Operation Service
 *
 * @author whiliang
 * @date 2021-06-01
 */
@Service
public class MinioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioConfiguration.class);

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;

    public static String getBucketPolicyJson(String bucketName) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("    \"Statement\": [\n");
        builder.append("        {\n");
        builder.append("            \"Action\": [\n");
        builder.append("                \"s3:GetBucketLocation\",\n");
        builder.append("                \"s3:ListBucket\"\n");
        builder.append("            ],\n");
        builder.append("            \"Effect\": \"Allow\",\n");
        builder.append("            \"Principal\": \"*\",\n");
        builder.append("            \"Resource\": \"arn:aws:s3:::" + bucketName + "\"\n");
        builder.append("        },\n");
        builder.append("        {\n");
        builder.append("            \"Action\": \"s3:GetObject\",\n");
        builder.append("            \"Effect\": \"Allow\",\n");
        builder.append("            \"Principal\": \"*\",\n");
        builder.append("            \"Resource\": \"arn:aws:s3:::" + bucketName + "/*\"\n");
        builder.append("        }\n");
        builder.append("    ],\n");
        builder.append("    \"Version\": \"2012-10-17\"\n");
        builder.append("}\n");
        return builder.toString();
    }


    /**
     * 创建bucket
     *
     * @param bucketName bucketName
     */
    public void createReadWriteBucket(String bucketName) {
        try {
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                LOGGER.warn("bucket {} already exists", bucketName);
                return;
            } else {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(minioProperties.getBucket()).config(getBucketPolicyJson(bucketName)).build());
            }
        } catch (Exception e) {
            LOGGER.error("create minio bucket failed", e);
        }
    }


    /**
     * 列出bucket下的所有对象
     *
     * @param bucketName bucketName
     * @param recursive  是否递归查找，如果是false,就模拟文件夹结构查找。
     * @return List of items
     */
    public List<Item> list(String bucketName, boolean recursive) {
        Iterable<Result<Item>> myObjects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(recursive).build());
        return getItems(myObjects);
    }

    /**
     * 列出bucket下的所有对象
     *
     * @param bucketName bucketName
     * @param recursive  是否递归查找，如果是false,就模拟文件夹结构查找
     * @param prefix     对象名称的前缀
     * @return List of items
     */
    public List<Item> list(String bucketName, boolean recursive, String prefix) {
        Iterable<Result<Item>> myObjects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(recursive).prefix(prefix).build());
        return getItems(myObjects);
    }

    /**
     * Utility method which map results to items and return a list
     *
     * @param myObjects Iterable of results
     * @return List of items
     */
    private List<Item> getItems(Iterable<Result<Item>> myObjects) {
        return StreamSupport
                .stream(myObjects.spliterator(), true)
                .map(itemResult -> {
                    try {
                        return itemResult.get();
                    } catch (
                            NoSuchAlgorithmException | InsufficientDataException | IOException | InvalidKeyException | XmlParserException | InvalidResponseException | ErrorResponseException | InternalException | ServerException e) {
                        throw new io.github.whiliang.minio.MinioException("Error while parsing list of objects", e);
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取文件元数据信息
     *
     * @param bucketName    bucketName
     * @param minioFileName minioFileName
     * @return Metadata of the  object
     * @throws io.github.whiliang.minio.MinioException if an error occur while fetching object metadatas
     */
    public StatObjectResponse getMetadata(String bucketName, String minioFileName) throws io.github.whiliang.minio.MinioException {
        try {
            return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(minioFileName).build());
        } catch (XmlParserException | NoSuchAlgorithmException | InsufficientDataException | IOException | InvalidKeyException | ErrorResponseException | InternalException | InvalidResponseException | ServerException e) {
            throw new io.github.whiliang.minio.MinioException("Error while fetching files in Minio", e);
        }
    }

    /**
     * 上传文件
     *
     * @param bucketName    bucket名称
     * @param minioFileName minio文件名称
     * @param inputStream   文件流
     * @param mimeTypeEnum  文件的MIME, eg "video/mp4"
     * @throws io.github.whiliang.minio.MinioException if an error occur while uploading object
     */
    public MinioUploadResponse upload(String bucketName, String minioFileName, InputStream inputStream, MimeTypeEnum mimeTypeEnum) throws
            io.github.whiliang.minio.MinioException {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(minioFileName).stream(
                            inputStream, -1, 10485760)
                            .contentType(mimeTypeEnum == null ? MimeTypeEnum.UNKNOWN.mimeType() : mimeTypeEnum.mimeType())
                            .build());
            return MinioUploadResponse.builder().success(Boolean.TRUE).minioFilename(minioFileName)
                    .internetUrl(minioProperties.getInternetUrl(), bucketName, minioFileName)
                    .intranetUrl(minioProperties.getIntranetUrl(), bucketName, minioFileName)
                    .build();
        } catch (ServerException | ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | XmlParserException e) {
            throw new io.github.whiliang.minio.MinioException("Error while uploading files in Minio", e);
        }
    }

    /**
     * 删除文件
     *
     * @param bucketName    bucketName
     * @param minioFileName minioFileName
     * @throws io.github.whiliang.minio.MinioException if an error occur while removing object
     */
    public void remove(String bucketName, String minioFileName) throws io.github.whiliang.minio.MinioException {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(minioFileName).build());
        } catch (XmlParserException | NoSuchAlgorithmException | InsufficientDataException | IOException | InvalidKeyException | ErrorResponseException | InternalException | InvalidResponseException | ServerException e) {
            throw new MinioException("Error while removing files in Minio", e);
        }
    }

    /**
     * 根据扩展名生成uuid文件名
     * @param extend 扩展名
     * @return
     */
    public static String generateUUIDFilename(String extend){
        return UUID.randomUUID().toString()+ "."+extend;
    }
}
