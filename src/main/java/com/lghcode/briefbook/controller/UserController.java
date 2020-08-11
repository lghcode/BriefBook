package com.lghcode.briefbook.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.lghcode.briefbook.enums.EditProfileEnum;
import com.lghcode.briefbook.enums.UserEnum;
import com.lghcode.briefbook.model.SmsCode;
import com.lghcode.briefbook.model.User;
import com.lghcode.briefbook.model.param.EditProfileParam;
import com.lghcode.briefbook.service.SmsCodeService;
import com.lghcode.briefbook.service.UserService;
import com.lghcode.briefbook.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 用户模块控制层
 * @Author lgh
 * @Date 2020/8/10 10:22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TencentSmsUtil tencentSmsUtil;

    @Autowired
    private SmsCodeService smsCodeService;

    /**
     * 用户登录 -手机号密码
     *
     * @Author laiyou
     * @param mobile 手机号
     * @param password 密码
     * @return ResultJson
     * @Date 2020/8/10 12:12
     */
    @PostMapping("/loginPw")
    public ResultJson loginPw(String mobile,String password) {
        //对参数进行空值校验
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            return ResultJson.error("参数不能为空");
        }
        //判断手机号是否存在
        boolean mobileExis = userService.checkUserMobileExist(mobile);
        if (!mobileExis) {
            return ResultJson.error("手机号未注册");
        }
        //判断密码是否正确
        User currUser = null;
        try {
            currUser = userService.getUserByMobileAndMd5Str(mobile,MD5Utils.getMD5Str(password));
        } catch (Exception e) {
           return ResultJson.error("md5加密失败",e.getMessage());
        }
        if (currUser == null) {
            return ResultJson.error("密码输入错误");
        }
        //登录成功
        return ResultJson.success("登录成功",currUser);
    }

    /**
     * 更改昵称--用户id和昵称
     * @Author laiyou
     * @param id  用户id
     * @param nickname  昵称
     * @return ResultJson
     * @Date 2020/8/10 16:42
     */

    @PostMapping("/upNickname")
    public ResultJson upNickname(Long id,String nickname){
        //对参数进行空值校验

        if(StringUtils.isBlank(nickname)){
            return ResultJson.error("昵称不能为空");
        }
        //判断用户id是否存在
        if(id == null){
            return ResultJson.error("用户id不能为空");
        }

        try {
            userService.updateNicknameById(id, nickname);
        } catch (Exception e) {
            return ResultJson.error("昵称更新失败");
        }
        //登录成功
        return ResultJson.success("昵称更新成功");


    }


    /**
     * 发送重置密码的验证码
     * @Author laiyou
     * @param mobile
     * @return ResultJson
     * @Date 2020/8/11 14:14
     */
    @PostMapping("/sendUpPwdSms")
    public ResultJson sendUpPwdSms(String mobile){
        if (StringUtils.isBlank(mobile)){
            return  ResultJson.error("手机号不能为空");
        }
        //判断手机号是否存在
        boolean mobileExis = userService.checkUserMobileExist(mobile);
        if (!mobileExis) {
            return ResultJson.error("手机号未注册");
        }
        //判断当前手机号是否在1分钟之内已经发送过登录验证码
        boolean isRepect = smsCodeService.checkRepeatSendSms(mobile,1, DateUtil.getOneMintueBefore(),new Date());
        if (isRepect) {
            return ResultJson.error("请不要在一分钟之内重复发送验证码");
        }
        //生成6位验证码
        String code = String.valueOf(RandomUtil.randomInt(1,999999));
        boolean flag = tencentSmsUtil.sendUpPwdSms(code,mobile);
        if (!flag) {
            return ResultJson.error("验证码发送失败");
        }
        //将信息同步到验证码表
        SmsCode smsCode = SmsCode.builder().mobile(mobile).code(code).type(1).createTime(new Date()).build();
        smsCodeService.save(smsCode);
        return ResultJson.success("验证码发送成功");

    }

    /**
     *
     *重置用户密码
     * @Author laiyou
     * @param  id 用户id
     * @param  code  短信验证码
     * @return newPassword  新密码
     * @Date 2020/8/11 15:11
     */
    @PostMapping("/updatePwd")
    public ResultJson updatePwd(Long id,String code,String newPassword) {
        //空值校验
        if (id == null || StringUtils.isBlank(code) || StringUtils.isBlank(newPassword)) {
            return ResultJson.error("参数不能为空");
        }
        //得到SmsCode
        SmsCode resultSmsCode = smsCodeService.getByUserIdAndType(id,1);
        if (resultSmsCode == null){
            return ResultJson.error("更新失败");
        }
        //检验验证码是否过期
        long sendCodeTime = resultSmsCode.getCreateTime().getTime();
        long nowTime = System.currentTimeMillis();
        if ((nowTime - sendCodeTime)>(15 * 60 * 1000)){
            return ResultJson.error("验证码已过期，请重新发送");
        }
        //校验验证码是否输入正确
        String realCode = resultSmsCode.getCode();
        if (!code.equals(realCode)) {
            return ResultJson.error("验证码输入有误");
        }
        //验证码正确，修改密码
        try {
            userService.updateNewPwd(id,MD5Utils.getMD5Str(newPassword));
        } catch (Exception e) {
            return ResultJson.error("md5加密失败",e.getMessage());
        }
        return ResultJson.success("密码重置成功");
    }


    /**
     * 编辑个人资料
     * @Author laiyou
     * @param editProfileParam  编辑个人资料请求参数
     * @return ResultJson
     * @Date 2020/8/10 16:42
     */
    @PostMapping("/editProfile")
    public ResultJson editProfile(EditProfileParam editProfileParam){
        //对参数进行空值校验
        if (ObjectUtil.isEmpty(editProfileParam)) {
            return ResultJson.error("参数不能为空");
        }
        if (editProfileParam.getUserId() == null ||
                editProfileParam.getEditType() == null ||
                StringUtils.isBlank(editProfileParam.getEditValue())){
            return ResultJson.error("参数不能为空");
        }
        //校验性别参数格式
        if (editProfileParam.getEditType() == EditProfileEnum.EDIT_SEX.getCode()
            && !checkSexValueFormat(editProfileParam.getEditValue())) {
                return ResultJson.error("性别参数不合法");
        }
        //校验生日参数格式
        if (editProfileParam.getEditType() == EditProfileEnum.EDIT_BIRTHDAY.getCode()
                && !DateUtil.isRqFormat(editProfileParam.getEditValue())) {
            return ResultJson.error("生日参数不合法");
        }
        boolean isSuc = userService.updateProfile(editProfileParam);
        if (!isSuc) {
            return ResultJson.error(EditProfileEnum.getEnumDescByCode(editProfileParam.getEditType())+"失败");
        }
        return ResultJson.success(EditProfileEnum.getEnumDescByCode(editProfileParam.getEditType())+"成功");
    }

    /**
     * 校验性别参数是否为 "0" "1" "2"
     *
     * @Author lghcode
     * @param  sexValue 性别参数
     * @return boolean
     * @Date 2020/8/11 18:18
     */
    private boolean checkSexValueFormat(String sexValue){
        return String.valueOf(UserEnum.SEX_SECRET.getCode()).equals(sexValue)
                || String.valueOf(UserEnum.SEX_MALE.getCode()).equals(sexValue)
                || String.valueOf(UserEnum.SEX_FEMALE.getCode()).equals(sexValue);
    }

}
