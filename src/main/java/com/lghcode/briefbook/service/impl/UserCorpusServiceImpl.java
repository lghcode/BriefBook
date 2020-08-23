package com.lghcode.briefbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lghcode.briefbook.mapper.UserCorpusMapper;
import com.lghcode.briefbook.model.UserCorpus;
import com.lghcode.briefbook.service.UserCorpusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author lgh
 * @Date 2020/8/23 14:35
 */
@Service
public class UserCorpusServiceImpl implements UserCorpusService {

    @Autowired
    private UserCorpusMapper userCorpusMapper;

    /**
     * 获取用户关注的文集数量
     *
     * @param userId 用户id
     * @return Integer
     * @Author lghcode
     * @Date 2020/8/23 14:38
     */
    @Override
    public Integer getUserCorpusCount(Long userId) {
        return userCorpusMapper.selectCount(new QueryWrapper<UserCorpus>().lambda()
                    .eq(UserCorpus::getUserId,userId));
    }
}
