package com.lghcode.briefbook.controller;

import cn.hutool.core.util.ObjectUtil;
import com.lghcode.briefbook.model.DemoUser;
import com.lghcode.briefbook.service.DemoUserService;
import com.lghcode.briefbook.util.AliyunOssUploadUtil;
import com.lghcode.briefbook.util.ResultJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author lgh
 * @Date 2020/8/9 16:57
 */
@RestController
@RequestMapping("/oss")
public class DemoUserController {

    @Autowired
    private DemoUserService demoUserService;

    @Autowired
    private AliyunOssUploadUtil aliyunOssUploadUtil;


    /**
     * 新增demo用户
     *
     * @Author lghcode
     * @param demoUser 用户属性
     * @return ResultJson
     * @Date 2020/8/9 18:02
     */
    @PostMapping("/save")
    public ResultJson save(DemoUser demoUser){
        if (ObjectUtil.isEmpty(demoUser)) {
            return ResultJson.error("参数为空");
        }
        demoUserService.insert(demoUser);
        return ResultJson.success("新增成功");
    }

    /**
     * 文件上传到阿里云OSS
     *
     * @Author lghcode
     * @param  multipartFile 要上传的文件
     * @return ResultJson
     * @Date 2020/8/10 19:22
     */
    @RequestMapping("/uploadImg")
    public ResultJson uploadImg(MultipartFile multipartFile){
        if (null == multipartFile ) {
            return ResultJson.error("文件为空");
        }
        //上传到OSS
        String uploadUrl = aliyunOssUploadUtil.upload(multipartFile);
        if (StringUtils.isEmpty(uploadUrl)) {
            return ResultJson.error("上传失败");
        }
        return ResultJson.success("上传成功",uploadUrl);
    }

}
