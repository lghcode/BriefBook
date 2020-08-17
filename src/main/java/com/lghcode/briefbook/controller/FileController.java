package com.lghcode.briefbook.controller;

import com.lghcode.briefbook.exception.BizException;
import com.lghcode.briefbook.util.AliyunOssUploadUtil;
import com.lghcode.briefbook.util.ResultJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * 文件模块控制类
 * @Author lgh
 * @Date 2020/8/11 14:38
 */
@RestController
@RequestMapping("/file")
@Validated
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
    public ResultJson upload(@NotNull(message = "文件不能为空") @RequestParam("file") MultipartFile file){
        //上传到OSS
        String uploadUrl = aliyunOssUploadUtil.upload(file);
        if (StringUtils.isEmpty(uploadUrl)) {
            throw new BizException("上传失败");
        }
        return ResultJson.success("上传成功",uploadUrl);
    }
}
