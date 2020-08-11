package com.lghcode.briefbook.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.lghcode.briefbook.model.DemoUser;
import com.lghcode.briefbook.model.SmsCode;
import com.lghcode.briefbook.service.DemoUserService;
import com.lghcode.briefbook.service.SmsCodeService;
import com.lghcode.briefbook.util.AliyunOssUploadUtil;
import com.lghcode.briefbook.util.DateUtil;
import com.lghcode.briefbook.util.ResultJson;
import com.lghcode.briefbook.util.TencentSmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

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

    @Autowired
    private TencentSmsUtil tencentSmsUtil;

    @Autowired
    private SmsCodeService smsCodeService;


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

    @RequestMapping("/sendLoginSms")
    public ResultJson sendSms(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return ResultJson.error("手机号不能为空");
        }
        //判断当前手机号是否在1分钟之内已经发送过登录验证码
        boolean isRepect = smsCodeService.checkRepeatSendSms(mobile,0, DateUtil.getOneMintueBefore(),new Date());
        if (isRepect) {
            return ResultJson.error("请不要在一分钟之内重复发送验证码");
        }
        //生成6位验证码
        String code = String.valueOf(RandomUtil.randomInt(1,999999));
        boolean flag = tencentSmsUtil.sendLoginSms(code,mobile);
        if (!flag) {
            return ResultJson.error("验证码发送失败");
        }
        //将信息同步到验证码表
        SmsCode smsCode = SmsCode.builder().mobile(mobile).code(code).type(0).createTime(new Date()).build();
        smsCodeService.save(smsCode);
        return ResultJson.success("验证码发送成功");
    }

}
