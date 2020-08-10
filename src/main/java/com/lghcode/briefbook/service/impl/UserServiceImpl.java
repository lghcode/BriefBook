package com.lghcode.briefbook.service.impl;

import com.lghcode.briefbook.mapper.UserMapper;
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


}
