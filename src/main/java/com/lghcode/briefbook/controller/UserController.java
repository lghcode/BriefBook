package com.lghcode.briefbook.controller;

import cn.hutool.core.util.ObjectUtil;
import com.lghcode.briefbook.enums.EditProfileEnum;
import com.lghcode.briefbook.enums.UserEnum;
import com.lghcode.briefbook.model.User;
import com.lghcode.briefbook.model.param.EditProfileParam;
import com.lghcode.briefbook.service.UserService;
import com.lghcode.briefbook.util.DateUtil;
import com.lghcode.briefbook.util.MD5Utils;
import com.lghcode.briefbook.util.ResultJson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
