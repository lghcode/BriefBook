package com.lghcode.briefbook.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.lghcode.briefbook.constant.TencentSmsConstant;
import com.lghcode.briefbook.enums.EditProfileEnum;
import com.lghcode.briefbook.enums.SendSmsEnum;
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
     * 用户登录(手机号验证码登录)
     *
     * @Author lghcode
     * @param  mobile 手机号
     * @param  code 验证码
     * @return ResultJson
     * @Date 2020/8/12 18:49
     */
    @PostMapping("/loginSms")
    public ResultJson loginSms(String mobile,String code){
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(code)) {
            return ResultJson.error("参数不能为空");
        }
        if (!CommonUtil.isMobile(mobile)) {
            return  ResultJson.error("手机号不合法");
        }
        //得到SmsCode
        SmsCode smsCode = smsCodeService.getByMobileAndType(mobile,SendSmsEnum.LOGIN_SMS.getCode());
        if (smsCode == null){
            return ResultJson.error("没有找到该手机号的验证码记录");
        }
        //检验验证码是否过期
        long sendCodeTime = smsCode.getCreateTime().getTime();
        long nowTime = System.currentTimeMillis();
        if ((nowTime - sendCodeTime)>(TencentSmsConstant.PERIOD_OF_VALIDITY)){
            return ResultJson.error("验证码已过期，请重新发送");
        }
        //校验验证码是否输入正确
        String realCode = smsCode.getCode();
        if (!MD5Utils.getMD5Str(code).equals(realCode)) {
            return ResultJson.error("验证码输入有误");
        }
        //判断该手机号是否注册过
        boolean isExist = userService.checkUserMobileExist(mobile);
        //如果该手机号没注册过，则是新用户，需要进行默认注册
        if (!isExist){
            userService.defaultRegister(mobile);
        }
        //返回当前用户信息给前端
        User currentUser = userService.getUserByMobile(mobile);
        if (currentUser == null) {
            return ResultJson.error("该账号已被注销");
        }
        currentUser.setPassword(null);
        return ResultJson.success("登录成功",currentUser);
    }

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
        User currUser;
        try {
            currUser = userService.getUserByMobileAndMd5Str(mobile,MD5Utils.getMD5Str(password));
        } catch (Exception e) {
           return ResultJson.error("md5加密失败",e.getMessage());
        }
        if (currUser == null) {
            return ResultJson.error("密码输入错误");
        }
        //登录成功
        currUser.setPassword(null);
        return ResultJson.success("登录成功",currUser);
    }

    /**
     * 发送短信验证码
     * @Author lghcode
     * @param mobile 手机号
     * @param smsType 验证码类型，0-登录，1-重置密码，2-更换手机号
     * @return ResultJson
     * @Date 2020/8/11 14:14
     */
    @PostMapping("/sendSms")
    public ResultJson sendUpPwdSms(String mobile,Integer smsType) {
        if (StringUtils.isBlank(mobile)){
            return  ResultJson.error("手机号不能为空");
        }
        if (!CommonUtil.isMobile(mobile)) {
            return  ResultJson.error("手机号不合法");
        }
        if (smsType == null ||
                smsType > SendSmsEnum.UPMOBILE_SMS.getCode() ||
                smsType < SendSmsEnum.LOGIN_SMS.getCode()) {
            return ResultJson.error("验证码类型不合法");
        }
        //如果短信验证码类型为重置密码，则判断手机号是否存在
        if (smsType == SendSmsEnum.RESET_SMS.getCode()) {
            boolean mobileExis = userService.checkUserMobileExist(mobile);
            if (!mobileExis) {
                return ResultJson.error("手机号未注册");
            }
        }
        //判断当前手机号是否在1分钟之内已经发送过登录验证码
        boolean isRepect = smsCodeService.checkRepeatSendSms(mobile, SendSmsEnum.RESET_SMS.getCode(), CommonUtil.getOneMintueBefore(),new Date());
        if (isRepect) {
            return ResultJson.error("请不要在一分钟之内重复发送验证码");
        }
        //生成6位验证码
        String code = String.valueOf(RandomUtil.randomInt(1,999999));
        //发送登录时的验证码
        if (smsType == SendSmsEnum.LOGIN_SMS.getCode()) {
            boolean flag = tencentSmsUtil.sendLoginSms(code,mobile);
            if (!flag) {
                return ResultJson.error("验证码发送失败");
            }
        }else if(smsType == SendSmsEnum.RESET_SMS.getCode()){
            //发送重置密码时的验证码
            boolean flag = tencentSmsUtil.sendUpPwdSms(code,mobile);
            if (!flag) {
                return ResultJson.error("验证码发送失败");
            }
        }else if(smsType == SendSmsEnum.UPMOBILE_SMS.getCode()){
            //发送更换手机号时的验证码
            boolean flag = tencentSmsUtil.sendUpMobileSms(code,mobile);
            if (!flag) {
                return ResultJson.error("验证码发送失败");
            }
        }
        //将信息同步到验证码表
        SmsCode smsCode = SmsCode.builder().mobile(mobile).code(MD5Utils.getMD5Str(code)).type(smsType).createTime(new Date()).build();
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
        //根据用户id查询用户手机号
        String userMobile = userService.getMobileByUserId(id);
        //得到SmsCode
        SmsCode resultSmsCode = smsCodeService.getByMobileAndType(userMobile,SendSmsEnum.RESET_SMS.getCode());
        if (resultSmsCode == null){
            return ResultJson.error("更新失败");
        }
        //检验验证码是否过期
        long sendCodeTime = resultSmsCode.getCreateTime().getTime();
        long nowTime = System.currentTimeMillis();
        if ((nowTime - sendCodeTime)>(TencentSmsConstant.PERIOD_OF_VALIDITY)){
            return ResultJson.error("验证码已过期，请重新发送");
        }
        //校验验证码是否输入正确
        String realCode = resultSmsCode.getCode();
        if (!MD5Utils.getMD5Str(code).equals(realCode)) {
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
                && !CommonUtil.isRqFormat(editProfileParam.getEditValue())) {
            return ResultJson.error("生日参数不合法");
        }
        //如果是更改昵称，则判断该用户新输入的昵称是否已经被其他人使用
        if (editProfileParam.getEditType() == EditProfileEnum.EDIT_NICKNAME.getCode()
                && userService.checkUserNickNameIsRepeat(editProfileParam.getUserId(),editProfileParam.getEditValue())) {
            return ResultJson.error("该昵称已被占用");
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

    /**
     * 更换注册手机号
     *
     * @Author laiyou
     * @param id 用户id
     * @param code 用户输入的短信验证码
     * @param newMobile 用户的新手机号
     * @return ResultJson
     * @Date 2020/8/12 23:40
     */
    @PostMapping("/updateMobile")
    public ResultJson updateMobile(Long id,String code,String newMobile){
        //参数校验
        if (id == null || StringUtils.isBlank(code) || StringUtils.isBlank(newMobile)){
            return ResultJson.error("参数不能为空");
        }
        if (!CommonUtil.isMobile(newMobile)) {
            return  ResultJson.error("手机号不合法");
        }
        //根据用户id查询用户手机号
        String userMobile = userService.getMobileByUserId(id);
        //得到SmsCode
         SmsCode resultSmsCode = smsCodeService.getByMobileAndType(userMobile,SendSmsEnum.UPMOBILE_SMS.getCode());
        if (resultSmsCode == null){
            return ResultJson.error("更新失败,请获取验证码");
        }
        //检验验证码是否过期
        long sendCodeTime = resultSmsCode.getCreateTime().getTime();
        long nowTime = System.currentTimeMillis();
        if ((nowTime - sendCodeTime)>(TencentSmsConstant.PERIOD_OF_VALIDITY)){
            return ResultJson.error("验证码已过期，请重新发送");
        }
        //校验验证码是否输入正确
        String realCode = resultSmsCode.getCode();
        if (!MD5Utils.getMD5Str(code).equals(realCode)) {
            return ResultJson.error("验证码输入有误");
        }
        //判断要更换的手机号是否已经被人注册
        Boolean existMobile = userService.checkIsNewMobile(id,newMobile);
        if (existMobile){
            return ResultJson.error("该手机已经被注册了，请重新输入该号码");
        }
        //更新手机号
        try {
            userService.saveNewMobile(id,newMobile);
        } catch (Exception e) {
            return ResultJson.error("手机号更新失败");
        }
        return ResultJson.success("手机号已经更换成功");

    }
}
