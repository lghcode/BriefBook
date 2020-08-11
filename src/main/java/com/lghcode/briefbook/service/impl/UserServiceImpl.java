package com.lghcode.briefbook.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lghcode.briefbook.enums.EditProfileEnum;
import com.lghcode.briefbook.mapper.UserMapper;
import com.lghcode.briefbook.model.User;
import com.lghcode.briefbook.model.param.EditProfileParam;
import com.lghcode.briefbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 根据用户的id更新昵称
     *
     * @Author laiyou
     * @param id      用户id
     * @param nickname  昵称
     * @Date 2020/8/10 15:37
     */
    @Override
    public void updateNicknameById(Long id, String nickname) {
        User user =new User();
        user.setId(id);
        user.setNickName(nickname);
        userMapper.updateById(user);
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
        int r = userMapper.updateById(resUser);
        return r > 0;
    }


}
