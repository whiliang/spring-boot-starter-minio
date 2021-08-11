package com.github.whiliang.minio;


/**
 * 文件类型枚举
 *
 * @author whiliang
 * @date 2021-06-10
 */
public enum MimeTypeEnum {

    PLAIN("txt", "text/plain", "纯文本格式"),
    GP3("3gp","video/3gpp","3gp格式"),
    APK("apk", "application/vnd.android.package-archive","apk格式"),
    ASF("asf", "video/x-ms-asf","asf格式"),
    AVI("avi", "video/x-msvideo","avi格式"),
    BIN("bin", "application/octet-stream","bin格式"),
    BMP("bmp", "image/bmp","bmp格式"),
    C("c", "text/plain","c格式"),
    CLASS("class", "application/octet-stream","class格式"),
    CONF("conf", "text/plain","conf格式"),
    CPP("cpp", "text/plain","cpp格式"),
    DOC("doc", "application/msword","doc格式"),
    DOCX("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document","docx格式"),
    XLS("xls", "application/vnd.ms-excel","xls格式"),
    XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","xlsx格式"),
    EXE("exe", "application/octet-stream","exe格式"),
    GIF("gif", "image/gif","gif格式"),
    GTAR("gtar", "application/x-gtar","GTAR格式"),
    GZ("gz", "application/x-gzip","GZ格式"),
    H("h", "text/plain","H格式"),
    HTM("htm", "text/html","HTM格式"),
    HTML("html", "text/html","HTML格式"),
    JAR("jar", "application/java-archive","JAR格式"),
    JAVA("java", "text/plain","JAVA格式"),
    JPEG("jpeg", "image/jpeg","JPEG格式"),
    JPG("jpg", "image/jpeg","JPG格式"),
    JS("js", "application/x-javascript","JS格式"),
    LOG("log", "text/plain","LOG格式"),
    M3U("m3u", "audio/x-mpegurl","M3U格式"),
    M4A("m4a", "audio/mp4a-latm","M4A格式"),
    M4B("m4b", "audio/mp4a-latm","M4B格式"),
    M4P("m4p", "audio/mp4a-latm","M4P格式"),
    M4U("m4u", "video/vnd.mpegurl","M4U格式"),
    M4V("m4v", "video/x-m4v","M4V格式"),
    MOV("mov", "video/quicktime","MOV格式"),
    MP2("mp2", "audio/x-mpeg","MP2格式"),
    MP3("mp3", "audio/x-mpeg","MP3格式"),
    MP4("mp4", "video/mp4","MP4格式"),
    MPC("mpc", "application/vnd.mpohun.certificate","MPC格式"),
    MPE("mpe", "video/mpeg","MPE格式"),
    MPEG("mpeg", "video/mpeg","MPEG格式"),
    MPG("mpg", "video/mpeg","MPG格式"),
    MPG4("mpg4", "video/mp4","MPG4格式"),
    MPGA("mpga", "audio/mpeg","MPGA格式"),
    MSG("msg", "application/vnd.ms-outlook","MSG格式"),
    OGG("ogg", "audio/ogg","OGG格式"),
    PDF("pdf", "application/pdf","PDF格式"),
    PNG("png", "image/png","PNG格式"),
    PPS("pps", "application/vnd.ms-powerpoint","PPS格式"),
    PPT("ppt", "application/vnd.ms-powerpoint","PPT格式"),
    PPTX("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation","PPTX格式"),
    PROP("prop", "text/plain","PROP格式"),
    RC("rc", "text/plain","RC格式"),
    RMVB("rmvb", "audio/x-pn-realaudio","RMVB格式"),
    RTF("rtf", "application/rtf","RTF格式"),
    SH("sh", "text/plain","SH格式"),
    TAR("tar", "application/x-tar","TAR格式"),
    TGZ("tgz", "application/x-compressed","TGZ格式"),
    TXT("txt", "text/plain","TXT格式"),
    WAV("wav", "audio/x-wav","WAV格式"),
    WMA("wma", "audio/x-ms-wma","WMA格式"),
    WMV("wmv", "audio/x-ms-wmv","WMV格式"),
    WPS("wps", "application/vnd.ms-works","WPS格式"),
    XML("xml", "text/plain","XML格式"),
    Z("z", "application/x-compress","Z格式"),
    ZIP("zip", "application/x-zip-compressed","ZIP格式"),
    UNKNOWN("*", "application/octet-stream", "二进制流,不确定文件类型");

    /**
     * 文件扩展名
     */
    private String extension;
    /**
     * 文件mimeType
     */
    private String mimeType;
    /**
     * 文件类型说明
     */
    private String remark;

    MimeTypeEnum(String extension, String mimeType, String remark) {
        this.extension = extension;
        this.mimeType = mimeType;
        this.remark = remark;
    }

    public String extension() {
        return this.extension;
    }

    public String mimeType() {
        return this.mimeType;
    }

    public String remark() {
        return this.remark;
    }


    /**
     * 根据文件扩展名获取对应的文件类型枚举
     *
     * @param extension
     * @return
     */
    public static MimeTypeEnum getMimeTypeEnum(String extension) {
        MimeTypeEnum[] mediaTypeEnums = MimeTypeEnum.values();
        for (int i = 0; i < mediaTypeEnums.length; i++) {
            if (mediaTypeEnums[i].extension().equals(extension)) {
                return mediaTypeEnums[i];
            }
        }
        return MimeTypeEnum.UNKNOWN;
    }
}
