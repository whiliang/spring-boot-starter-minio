package io.github.whiliang.minio;

/**
 * MinioUploadResponse
 *
 * @author whiliang
 * @date 2021-06-01
 */
public class MinioUploadResponse {

    /**
     * upload success
     */
    private boolean success;

    /**
     * internetUrl for full http url of minio file
     */
    private String internetUrl;

    /**
     * intranetUrl for full http url of minio file
     */
    private String intranetUrl;

    /**
     * minioFilename
     */
    private String minioFilename;

    public MinioUploadResponse(Builder builder) {
        this.success = builder.success;
        this.internetUrl = builder.internetUrl;
        this.intranetUrl = builder.intranetUrl;
        this.minioFilename = builder.minioFilename;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        /**
         * upload success
         */
        private boolean success;

        /**
         * internetUrl for full http url of minio file
         */
        private String internetUrl;

        /**
         * intranetUrl for full http url of minio file
         */
        private String intranetUrl;

        /**
         * minioFilename
         */
        private String minioFilename;


        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder internetUrl(String internetUrl, String bucketName, String minioFileName) {
            this.internetUrl = internetUrl + "/" + bucketName + "/" + minioFileName;
            return this;
        }

        public Builder intranetUrl(String intranetUrl, String bucketName, String minioFileName) {
            this.intranetUrl = intranetUrl + "/" + bucketName + "/" + minioFileName;
            return this;
        }

        public Builder minioFilename(String minioFilename){
            this.minioFilename=minioFilename;
            return this;
        }

        public MinioUploadResponse build() {
            return new MinioUploadResponse(this);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInternetUrl() {
        return internetUrl;
    }

    public void setInternetUrl(String internetUrl) {
        this.internetUrl = internetUrl;
    }

    public String getIntranetUrl() {
        return intranetUrl;
    }

    public void setIntranetUrl(String intranetUrl) {
        this.intranetUrl = intranetUrl;
    }
}
