package com.lghcode.briefbook.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lghcode.briefbook.enums.EditProfileEnum;
import com.lghcode.briefbook.mapper.UserMapper;
import com.lghcode.briefbook.model.User;
import com.lghcode.briefbook.model.param.EditProfileParam;
import com.lghcode.briefbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author lgh
 * @Date 2020/8/10 10:20
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 检验用户手机号是否存在
     *
     * @Author laiyou
     * @param  mobile 手机号
     * @return boolean
     * @Date 2020/8/10 12:00
     */
    @Override
    public boolean checkUserMobileExist(String mobile) {
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getMobile,mobile));
        return user != null;
    }

    /**
     * 根据手机号和密码查询单个用户
     *
     * @Author laiyou
     * @param  mobile 手机号
     * @param  md5Str 加密之后的密码
     * @return User
     * @Date 2020/8/10 12:01
     */
    @Override
    public User getUserByMobileAndMd5Str(String mobile, String md5Str) {
        return userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getMobile,mobile).eq(User::getPassword,md5Str));
    }

    /**
     * 根据不同类型字段更新个人资料
     *
     * @param editProfileParam 更新资料参数
     * @return boolean
     * @Author lghcode
     * @Date 2020/8/11 16:27
     */
    @Override
    public boolean updateProfile(EditProfileParam editProfileParam) {
        User resUser = userMapper.selectById(editProfileParam.getUserId());
        if (editProfileParam.getEditType() == EditProfileEnum.EDIT_HEADIMG.getCode()){
            //更改头像
            resUser.setHeadImg(editProfileParam.getEditValue());
        }else if(editProfileParam.getEditType() == EditProfileEnum.EDIT_NICKNAME.getCode()){
            //更改昵称
            resUser.setNickName(editProfileParam.getEditValue());
        }else if(editProfileParam.getEditType() == EditProfileEnum.EDIT_SEX.getCode()){
            //更性别
            resUser.setSex(Integer.valueOf(editProfileParam.getEditValue()));
        }else if(editProfileParam.getEditType() == EditProfileEnum.EDIT_BIRTHDAY.getCode()){
            //更改生日   前端时间字符串格式必须是 2020-07-12
            resUser.setBirthday(DateUtil.parse(editProfileParam.getEditValue()));
        }else if(editProfileParam.getEditType() == EditProfileEnum.EDIT_HOMEPAGE.getCode()){
            //更改主页
            resUser.setHomePage(editProfileParam.getEditValue());
        }else if(editProfileParam.getEditType() == EditProfileEnum.EDIT_PROFILE.getCode()){
            //更改个性签名
            resUser.setProfile(editProfileParam.getEditValue());
        }
        resUser.setUpdateTime(new Date());
        int r = userMapper.updateById(resUser);
        return r > 0;
    }

    /**
     * 用户密码重置更新
     *
     * @param id       用户id
     * @param password 用户密码
     * @Author laiyou
     * @Date 2020/8/11 19:27
     */
    @Override
    public void updateNewPwd(Long id, String password) {
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        user.setUpdateTime(new Date());
        userMapper.updateById(user);
    }

    /**
     * 根据用户id查询用户手机号
     *
     * @param userId 用户id
     * @return String 用户手机号
     * @Author lghcode
     * @Date 2020/8/12 16:39
     */
    @Override
    public String getMobileByUserId(Long userId) {
        User user = userMapper.selectOne(new QueryWrapper<User>().select("mobile").lambda()
        .eq(User::getId,userId));
        if (user == null) {
            return null;
        }
        return user.getMobile();
    }

    /**
     * 用户第一次登录进行默认注册
     *
     * @param mobile 手机号
     * @Author lghcode
     * @Date 2020/8/12 17:16
     */
    @Override
    public void defaultRegister(String mobile) {
       User user = User.builder().
               mobile(mobile).
               nickName(RandomUtil.randomString(12)).
               headImg("http://lgh-chat-im.oss-cn-hangzhou.aliyuncs.com/briefBook/2020-08-12/d1b9233040ff48ff814fbb1438430935-timg.jpg").
               createTime(new Date()).
               updateTime(new Date()).
               build();
       userMapper.insert(user);
    }

    /**
     * 根据手机号查找用户
     *
     * @param mobile 用户手机号
     * @return User
     * @Author lghcode
     * @Date 2020/8/12 17:49
     */
    @Override
    public User getUserByMobile(String mobile) {
        return userMapper.selectOne(new QueryWrapper<User>().lambda()
                .eq(User::getMobile,mobile));
    }

    /**
     * 校验某用户要更改的新昵称是否被其他人占用
     *
     * @param userId   用户id
     * @param nickName 用户昵称
     * @return boolean
     * @Author lghcode
     * @Date 2020/8/12 18:05
     */
    @Override
    public boolean checkUserNickNameIsRepeat(Long userId, String nickName) {
        Integer count = userMapper.selectCount(new QueryWrapper<User>().lambda()
                .eq(User::getNickName, nickName)
                .ne(User::getId,userId));
        return count > 0;
    }

    /**
     * 判断要更换的手机号是否已经被人注册
     *
     * @param id 用户id
     * @param newMobile 新手机号
     * @return Boolean
     * @Author laiyou
     * @Date 2020/8/13 0:05
     */
    @Override
    public Boolean checkIsNewMobile(Long id, String newMobile) {
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getId,id).eq(User::getMobile, newMobile));
        return user != null;
    }

    /**
     * 更新手机号
     *
     * @param id 用户id
     * @param newMobile 新手机号
     * @Author laiyou
     * @Date 2020/8/13 0:13
     */
    @Override
    public void saveNewMobile(Long id, String newMobile) {
        User user = new User();
        user.setId(id);
        user.setMobile(newMobile);
        userMapper.updateById(user);
    }
}
