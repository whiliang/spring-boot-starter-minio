package io.github.whiliang.minio;

/**
 * Minio Exception
 *
 * @author whiliang
 * @date 2021-06-01
 */
public class MinioException extends RuntimeException {
    public MinioException(String message, Throwable cause) {
        super(message, cause);
    }
}