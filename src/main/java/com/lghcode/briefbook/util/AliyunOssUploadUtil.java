package com.lghcode.briefbook.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.lghcode.briefbook.config.AliyunConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author lgh
 * @Date 2020/8/10 17:01
 */
@Service
@Slf4j
public class AliyunOssUploadUtil {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private AliyunConfigProperties aliyunConfigProperties;
    /**
     * 上传File文件到阿里云OSS
     *
     * @Author lghcode
     * @param  file 要上传的文件
     * @return String
     * @Date 2020/8/10 17:21
     */
    public String upload(File file){
        log.info("=========>OSS文件上传开始："+file.getName());
        String endpoint = aliyunConfigProperties.getEndpoint();
        String accessKeyId = CodeUtil.aesDecrypt(aliyunConfigProperties.getAccessKeyId(),"666");
        String accessKeySecret = CodeUtil.aesDecrypt(aliyunConfigProperties.getAccessKeySecret(),"666");
        String bucketName = aliyunConfigProperties.getBucketName();
        String fileHost = aliyunConfigProperties.getFilePath();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());

        OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);
        try {
            //容器不存在，就创建
            if(! ossClient.doesBucketExist(bucketName)){
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            //创建文件路径
            String fileUrl = fileHost+"/"+(dateStr + "/" + UUID.randomUUID().toString().replace("-","")+"-"+file.getName());
            //上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            //设置权限 这里是公开读
            ossClient.setBucketAcl(bucketName,CannedAccessControlList.PublicRead);
            if(null != result){
                log.info("==========>OSS文件上传成功,OSS地址："+fileUrl);
                return aliyunConfigProperties.getDomain()+"/"+fileUrl;
            }
        }catch (OSSException oe){
            log.error(oe.getMessage());
        }catch (ClientException ce){
            log.error(ce.getMessage());
        }finally {
            //关闭
            ossClient.shutdown();
        }
        return null;
    }

    /**
     * 上传MultipartFile文件到阿里云OSS
     *
     * @Author lghcode
     * @param  multipartFile 要上传的文件
     * @return String
     * @Date 2020/8/10 19:12
     */
    public String upload(MultipartFile multipartFile) {
        if (null == multipartFile ) {
            return "";
        }
        String fileName = multipartFile.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)){
            return "";
        }
        File newFile;
        try {
            newFile = new File(fileName);
            FileOutputStream os = new FileOutputStream(newFile);
            os.write(multipartFile.getBytes());
            os.close();
            multipartFile.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        //上传到OSS
        String uploadUrl = upload(newFile);
        newFile.delete();
        return uploadUrl;
    }

    /**
     * 根据文件名删除文件
     *
     * @Author lghcode
     * @param  fileKey 文件名
     * @return String
     * @Date 2020/8/10 17:22
     */
    public String deleteBlog(String fileKey){
        log.info("=========>OSS文件删除开始");
        String endpoint = aliyunConfigProperties.getEndpoint();
        String accessKeyId = CodeUtil.aesDecrypt(aliyunConfigProperties.getAccessKeyId(),"666");
        String accessKeySecret = CodeUtil.aesDecrypt(aliyunConfigProperties.getAccessKeySecret(),"666");
        String bucketName = aliyunConfigProperties.getBucketName();
        try {
            OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);

            if(!ossClient.doesBucketExist(bucketName)){
                log.info("==============>您的Bucket不存在");
                return "您的Bucket不存在";
            }else {
                log.info("==============>开始删除Object");
                ossClient.deleteObject(bucketName,fileKey);
                log.info("==============>Object删除成功："+fileKey);
                return "==============>Object删除成功："+fileKey;
            }
        }catch (Exception ex){
            log.info("删除Object失败",ex);
            return "删除Object失败";
        }
    }

    /**
     * 查询文件名列表
     *
     * @Author lghcode
     * @param  bucketName 块名称
     * @return List<String>
     * @Date 2020/8/10 17:23
     */
    public List<String> getObjectList(String bucketName){
        List<String> listRe = new ArrayList<>();
        String endpoint = aliyunConfigProperties.getEndpoint();
        String accessKeyId = CodeUtil.aesDecrypt(aliyunConfigProperties.getAccessKeyId(),"666");
        String accessKeySecret = CodeUtil.aesDecrypt(aliyunConfigProperties.getAccessKeySecret(),"666");
        String fileHost = aliyunConfigProperties.getFilePath();
        try {
            log.info("===========>查询文件名列表");
            OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
            //列出fileHost目录下今天所有文件
            listObjectsRequest.setPrefix(fileHost+"/"+format.format(new Date())+"/");
            ObjectListing list = ossClient.listObjects(listObjectsRequest);
            for(OSSObjectSummary objectSummary : list.getObjectSummaries()){
                System.out.println(objectSummary.getKey());
                listRe.add(objectSummary.getKey());
            }
            return listRe;
        }catch (Exception ex){
            log.info("==========>查询列表失败",ex);
            return new ArrayList<>();
        }
    }

}

