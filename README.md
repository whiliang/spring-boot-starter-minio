# Spring Boot Starter Minio

Spring Boot Starter Minio适配minio 8.0.3及以上版本,用于minio文件的上传、下载、删除等操作.  

## 集成

1. 添加maven依赖

Maven
```xml
<dependency>
    <groupId>io.github.whiliang</groupId>
    <artifactId>spring-boot-starter-minio</artifactId>
    <version>8.0.3.RELEASE</version>
</dependency>
```


2. 添加配置信息

```properties
#Minio enable or disable (true or false)
spring.minio.enable=true
# Minio intranet host(eg: http://192.168.1.101:9000)
spring.minio.intranet-url=###Minio intranet url###
# Minio internet host(eg: http://47.73.111.33:9000)
spring.minio.internet-url=###Minio internet url###
# Minio Bucket name for your application
spring.minio.bucket=###Minio Bucket name###
# Minio access key (login)
spring.minio.access-key=###Minio accessKey###
# Minio secret key (password)
spring.minio.secret-key=###Minio secretKey###
```
3. 程序调用

引入Bean MinioService完成文件的上传、删除、创建bucket等工作

```java
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private MinioService minioService;

    @Resource
    private MinioProperties minioProperties;

    @GetMapping("/{bucketName}")
    public List<Item> testMinio() throws MinioException {
        return minioService.list(bucketName,false);
    }
    
    public MinioUploadResponse upload(@RequestParam(value = "file") MultipartFile file) {
        String extend = FilenameUtils.getExtension(file.getOriginalFilename());
        String minioFilename = MinioService.generateUUIDFilename(extend);
        MinioUploadResponse resp = MinioUploadResponse.builder().build();
        try {
            resp = minioService.upload(minioProperties.getBucket(), minioFilename, file.getInputStream(), MimeTypeEnum.PNG);
        } catch (Exception e) {
            log.error("exception occurs when uploading:"+e.getMessage(),e);
        }
        return resp;
    }
}
```
