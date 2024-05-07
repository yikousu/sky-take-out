package com.sky.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 文件上传
     * 就三步
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes, String objectName) {
        // 1创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);
        ossClient.shutdown();

        log.info("文件上传到:{}", stringBuilder.toString());
        return stringBuilder.toString();
    }

//第一次写的OSS  参考
//    public String uploadOss(InputStream inputStream, String filePath) {
//        //因为要尽量避免同一个文件夹中文件太多
//        String path = DateUtil.format(new Date(), "yyyy/MM/dd/");//三层路径可随时间变化
//        String s = UUID.randomUUID().toString();
//        String suffix = filePath.substring(filePath.lastIndexOf("."));
//        String objectName = path + s + suffix;
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(
//                ossProperties.getEndpoint(),
//                ossProperties.getAccessKeyId(),
//                ossProperties.getAccessKeySecret());
//        // 创建PutObjectRequest对象。
//        PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucketName(), objectName, inputStream);
//        // 创建PutObject请求。
//        PutObjectResult result = ossClient.putObject(putObjectRequest);
//        ossClient.shutdown();
//        //图片访问地址
//        String picUrl = ossProperties.getUrl() + objectName;
//        return picUrl;
//    }
}
