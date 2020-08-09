package com.lghcode.briefbook.service.impl;

import com.lghcode.briefbook.mapper.DemoUserMapper;
import com.lghcode.briefbook.model.DemoUser;
import com.lghcode.briefbook.service.DemoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author lgh
 * @Date 2020/8/9 16:56
 */
@Service
public class DemoUserServiceImpl implements DemoUserService {

    @Autowired
    private DemoUserMapper demoUserMapper;


    @Override
    public void insert(DemoUser demoUser) {
        demoUserMapper.insert(demoUser);
    }
}
