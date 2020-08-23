package com.lghcode.briefbook.controller;

import cn.hutool.core.util.RandomUtil;
import com.lghcode.briefbook.constant.Constant;
import com.lghcode.briefbook.constant.TencentSmsConstant;
import com.lghcode.briefbook.enums.EditProfileEnum;
import com.lghcode.briefbook.enums.SendSmsEnum;
import com.lghcode.briefbook.enums.UserEnum;
import com.lghcode.briefbook.exception.BizErrorCodeEnum;
import com.lghcode.briefbook.exception.BizException;
import com.lghcode.briefbook.model.SmsCode;
import com.lghcode.briefbook.model.User;
import com.lghcode.briefbook.model.param.EditProfileParam;
import com.lghcode.briefbook.model.vo.LoginUserInfo;
import com.lghcode.briefbook.model.vo.UserIndexVo;
import com.lghcode.briefbook.service.SmsCodeService;
import com.lghcode.briefbook.service.UserService;
import com.lghcode.briefbook.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 用户模块控制层
 * @Author lgh
 * @Date 2020/8/10 10:22
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TencentSmsUtil tencentSmsUtil;

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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
    public ResultJson loginSms(@NotEmpty(message = "手机号不能为空") @RequestParam("mobile") String mobile,
                               @NotEmpty(message = "验证码不能为空") @RequestParam("code") String code){
        if (!CommonUtil.isMobile(mobile)) {
            throw new BizException(BizErrorCodeEnum.MOBILE_ILLEGAL);
        }
        //得到SmsCode
        SmsCode smsCode = smsCodeService.getByMobileAndType(mobile,SendSmsEnum.LOGIN_SMS.getCode());
        if (smsCode == null){
            throw new BizException(BizErrorCodeEnum.SMSCODE_NOT_EXIST);
        }
        //检验验证码是否过期
        if (checkSmsCodeIsExpired(smsCode)){
            throw new BizException(BizErrorCodeEnum.SMSCODE_EXPIRED);
        }
        //校验验证码是否输入正确
        if (checkSmsCodeIsCorrect(smsCode,code)) {
            throw new BizException(BizErrorCodeEnum.SMSCODE_INCORRECT);
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
            throw new BizException(BizErrorCodeEnum.USER_NOT_EXIST);
        }
        LoginUserInfo loginUserInfo = userService.getLoginUser(currentUser);
        return ResultJson.success("登录成功",loginUserInfo);
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
    public ResultJson loginPw(@NotEmpty(message = "手机号不能为空") @RequestParam("mobile") String mobile,
                              @NotEmpty(message = "密码不能为空") @RequestParam("password") String password) {
        //判断手机号是否存在
        boolean mobileExis = userService.checkUserMobileExist(mobile);
        if (!mobileExis) {
            throw new BizException(BizErrorCodeEnum.USER_NOT_EXIST);
        }
        //判断密码是否正确
        User currUser = userService.getUserByMobileAndMd5Str(mobile,MD5Utils.getMD5Str(password));
        if (currUser == null) {
            throw new BizException(BizErrorCodeEnum.PASWORD_ERROR);
        }
        //登录成功
        LoginUserInfo loginUserInfo = userService.getLoginUser(currUser);
        return ResultJson.success("登录成功",loginUserInfo);
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
    public ResultJson sendUpPwdSms(@NotEmpty(message = "手机号不能为空") @RequestParam("mobile") String mobile,
                                   @NotNull(message = "验证码不能为空") @RequestParam("smsType") Integer smsType) {
        if (!CommonUtil.isMobile(mobile)) {
           throw new BizException(BizErrorCodeEnum.MOBILE_ILLEGAL);
        }
        if (smsType > SendSmsEnum.UPMOBILE_SMS.getCode() ||
                smsType < SendSmsEnum.LOGIN_SMS.getCode()) {
            throw new BizException(BizErrorCodeEnum.SMSCODE_ILLEGAL);
        }
        //如果短信验证码类型为重置密码，则判断手机号是否存在
        if (smsType == SendSmsEnum.RESET_SMS.getCode()) {
            boolean mobileExis = userService.checkUserMobileExist(mobile);
            if (!mobileExis) {
                throw new BizException(BizErrorCodeEnum.USER_NOT_EXIST);
            }
        }
        //判断当前手机号是否在1分钟之内已经发送过登录验证码
        boolean isRepect = smsCodeService.checkRepeatSendSms(mobile, SendSmsEnum.RESET_SMS.getCode(), CommonUtil.getOneMintueBefore(),new Date());
        if (isRepect) {
            throw new BizException(BizErrorCodeEnum.SMSCODE_ONE_MINUTE_REPEAT);
        }
        //生成6位验证码
        String code = String.valueOf(RandomUtil.randomInt(1,999999));
        //发送登录时的验证码
        if (smsType == SendSmsEnum.LOGIN_SMS.getCode()) {
            boolean flag = tencentSmsUtil.sendLoginSms(code,mobile);
            if (!flag) {
                throw new BizException(BizErrorCodeEnum.SMSCODE_SEND_FAIL);
            }
        }else if(smsType == SendSmsEnum.RESET_SMS.getCode()){
            //发送重置密码时的验证码
            boolean flag = tencentSmsUtil.sendUpPwdSms(code,mobile);
            if (!flag) {
                throw new BizException(BizErrorCodeEnum.SMSCODE_SEND_FAIL);
            }
        }else if(smsType == SendSmsEnum.UPMOBILE_SMS.getCode()){
            //发送更换手机号时的验证码
            boolean flag = tencentSmsUtil.sendUpMobileSms(code,mobile);
            if (!flag) {
                throw new BizException(BizErrorCodeEnum.SMSCODE_SEND_FAIL);
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
    public ResultJson updatePwd(@NotNull(message = "用户id不能为空") @RequestParam("id") Long id,
                                @NotEmpty(message = "验证码不能为空") @RequestParam("code") String code,
                                @NotEmpty(message = "新密码不能为空") @RequestParam("newPassword") String newPassword) {
        //根据用户id查询用户手机号
        String userMobile = userService.getMobileByUserId(id);
        //得到SmsCode
        SmsCode resultSmsCode = smsCodeService.getByMobileAndType(userMobile,SendSmsEnum.RESET_SMS.getCode());
        if (resultSmsCode == null){
           throw new BizException(BizErrorCodeEnum.SMSCODE_NOT_EXIST);
        }
        //检验验证码是否过期
        if (checkSmsCodeIsExpired(resultSmsCode)){
            throw new BizException(BizErrorCodeEnum.SMSCODE_EXPIRED);
        }
        //校验验证码是否输入正确
        if (checkSmsCodeIsCorrect(resultSmsCode,code)) {
            throw new BizException(BizErrorCodeEnum.SMSCODE_INCORRECT);
        }
        //验证码正确，修改密码
        userService.updateNewPwd(id,MD5Utils.getMD5Str(newPassword));
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
    public ResultJson editProfile(@Validated EditProfileParam editProfileParam, HttpServletRequest request){
        Long userId = jwtTokenUtil.getUserIdFromHeader(request);
        if (!editProfileParam.getUserId().equals(userId)) {
            throw new BizException("用户id不是当前登录用户");
        }
        //校验性别参数格式
        if (editProfileParam.getEditType() == EditProfileEnum.EDIT_SEX.getCode()
            && !checkSexValueFormat(editProfileParam.getEditValue())) {
                throw new BizException(BizErrorCodeEnum.USER_SEX_ILLEGAL);
        }
        //校验生日参数格式
        if (editProfileParam.getEditType() == EditProfileEnum.EDIT_BIRTHDAY.getCode()
                && !CommonUtil.isRqFormat(editProfileParam.getEditValue())) {
            throw new BizException(BizErrorCodeEnum.USER_BIRTHDAY_ILLEGAL);
        }
        //如果是更改昵称，则判断该用户新输入的昵称是否已经被其他人使用
        if (editProfileParam.getEditType() == EditProfileEnum.EDIT_NICKNAME.getCode()
                && userService.checkUserNickNameIsRepeat(editProfileParam.getUserId(),editProfileParam.getEditValue())) {
            throw new BizException(BizErrorCodeEnum.USER_NICKNAME_REPEAT);
        }
        boolean isSuc = userService.updateProfile(editProfileParam);
        if (!isSuc) {
            throw new BizException(EditProfileEnum.getEnumDescByCode(editProfileParam.getEditType())+"失败");
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
    public ResultJson updateMobile(@NotNull(message = "用户id不能为空") @RequestParam("id") Long id,
                                   @NotEmpty(message = "短信验证码不能为空") @RequestParam("code") String code,
                                   @NotEmpty(message = "新手机号不能为空") @RequestParam("newMobile") String newMobile){
        if (!CommonUtil.isMobile(newMobile)) {
           throw new BizException(BizErrorCodeEnum.MOBILE_ILLEGAL);
        }
        //根据用户id查询用户手机号
        String userMobile = userService.getMobileByUserId(id);
        //得到SmsCode
         SmsCode resultSmsCode = smsCodeService.getByMobileAndType(userMobile,SendSmsEnum.UPMOBILE_SMS.getCode());
        if (resultSmsCode == null){
            throw new BizException(BizErrorCodeEnum.SMSCODE_NOT_EXIST);
        }
        //检验验证码是否过期
        if (checkSmsCodeIsExpired(resultSmsCode)){
            throw new BizException(BizErrorCodeEnum.SMSCODE_EXPIRED);
        }
        //校验验证码是否输入正确
        if (checkSmsCodeIsCorrect(resultSmsCode,code)) {
            throw new BizException(BizErrorCodeEnum.SMSCODE_INCORRECT);
        }
        //判断要更换的手机号是否已经被人注册
        Boolean existMobile = userService.checkIsNewMobile(id,newMobile);
        if (existMobile){
            throw new BizException(BizErrorCodeEnum.USER_MOBILE_REPEAT);
        }
        //更新手机号
        try {
            userService.saveNewMobile(id,newMobile);
        } catch (Exception e) {
            throw new BizException("手机号更新失败");
        }
        return ResultJson.success("手机号已经更换成功");
    }

    /**
     * 检验验证码是否过期
     *
     * @Author lghcode
     * @param  smsCode 短信验证码记录
     * @Date 2020/8/13 11:33
     */
    private boolean checkSmsCodeIsExpired(SmsCode smsCode){
        //检验验证码是否过期
        long sendCodeTime = smsCode.getCreateTime().getTime();
        long nowTime = System.currentTimeMillis();
        return (nowTime - sendCodeTime) > (TencentSmsConstant.PERIOD_OF_VALIDITY);
    }


    /**
     * 检验验证码是否输入正确
     *
     * @Author lghcode
     * @param  smsCode 短信验证码记录
     * @param  inputCode 用户输入的验证码
     * @Date 2020/8/13 11:33
     */
    private boolean checkSmsCodeIsCorrect(SmsCode smsCode,String inputCode){
        String realCode = smsCode.getCode();
        return !MD5Utils.getMD5Str(inputCode).equals(realCode);
    }

    /**
     * 关注/取消关注
     *
     * @Author lghcode
     * @param  followUserId 对方用户id
     * @return type  0-关注，1-取消关注
     * @Date 2020/8/21 17:41
     */
    @PostMapping("/followUser")
    public ResultJson followUser(@NotNull(message = "用户id不能为空") @RequestParam("followUserId") Long followUserId,
                                 @NotNull(message = "类型不能为空") @RequestParam("type") Integer type,HttpServletRequest request){
        Long userId = jwtTokenUtil.getUserIdFromHeader(request);
        userService.followUser(followUserId,type,userId);
        return ResultJson.success("操作成功");
    }

    /**
     * 查看用户主页
     *
     * @Author lghcode
     * @param  userId 用户id
     * @return ResultJson
     * @Date 2020/8/23 11:15
     */
    @PostMapping("/index")
    public ResultJson index(@NotNull(message = "用户id不能为空") @RequestParam("userId") Long userId,HttpServletRequest request){
        String authToken = request.getHeader(Constant.TOKEN_NAME);
        UserIndexVo userIndexVo = userService.getUserIndex(authToken,userId);
        return ResultJson.success("查询成功",userIndexVo);
    }
}
