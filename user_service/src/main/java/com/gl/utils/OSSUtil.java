package com.gl.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * OSS文件上传工具类
 */
public class OSSUtil {

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
    private static String accessKeyId = "LTAI5tL4aaqd4ph2WW5ieyfW";//LTAI5t8YEk7tFHJiF49aq7GX
    private static String accessKeySecret = "n3qPT8A16jdBut0wYS3vPAgyv3XvdW";
    private static String bucketName = "gl747571569";
    //目录名
    public static String dir = "images/";

    /**
     * 文件上传
     * @param inputStream
     * @param fileName
     */
    public static void upload(InputStream inputStream, String fileName){
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        oss.putObject(bucketName,dir + fileName,inputStream);
        oss.shutdown();
    }

    /**
     * 获得文件URL
     * @param fileName
     * @return
     */
    public static String getURL(String fileName){
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //设置过期时间
        LocalDateTime time = LocalDateTime.now().plusDays(100);
        Date expiration = Date.from(time.atZone( ZoneId.systemDefault()).toInstant());
        URL url = oss.generatePresignedUrl(bucketName, dir + fileName, expiration);
        System.out.println(url);
        oss.shutdown();
        return url.toString();
    }
}
