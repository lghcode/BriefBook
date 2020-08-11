package com.lghcode.briefbook.controller;

import com.lghcode.briefbook.util.AliyunOssUploadUtil;
import com.lghcode.briefbook.util.ResultJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件模块控制类
 * @Author lgh
 * @Date 2020/8/11 14:38
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private AliyunOssUploadUtil aliyunOssUploadUtil;

    /**
     * 文件上传到阿里云OSS
     *
     * @Author lghcode
     * @param  file 要上传的文件
     * @return ResultJson
     * @Date 2020/8/10 19:22
     */
    @PostMapping("/upload")
    public ResultJson upload(MultipartFile file){
        if (null == file ) {
            return ResultJson.error("文件为空");
        }
        //上传到OSS
        String uploadUrl = aliyunOssUploadUtil.upload(file);
        if (StringUtils.isEmpty(uploadUrl)) {
            return ResultJson.error("上传失败");
        }
        return ResultJson.success("上传成功",uploadUrl);
    }
}
