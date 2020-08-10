package com.lghcode.briefbook.controller;

import com.lghcode.briefbook.model.User;
import com.lghcode.briefbook.service.UserService;
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

}
