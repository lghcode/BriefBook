package com.lghcode.briefbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lghcode.briefbook.mapper.UserMapper;
import com.lghcode.briefbook.model.User;
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

    @Override
    public boolean checkUserMobileExist(String mobile) {
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getMobile,mobile));
        return user != null;
    }

    @Override
    public User getUserByMobileAndMd5Str(String mobile, String md5Str) {
        return userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getMobile,mobile).eq(User::getPassword,md5Str));
    }

    @Override
    public void updateNicknameById(Long id, String nickname) {
        User user =new User();
        user.setId(id);
        user.setNickName(nickname);
        userMapper.updateById(user);
    }
}
