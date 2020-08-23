package com.lghcode.briefbook.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @Author lgh
 * @Date 2020/8/23 14:34
 */
@Transactional(rollbackFor = Exception.class)
public interface UserCorpusService {

    /**
     * 获取用户关注的文集数量
     *
     * @Author lghcode
     * @param  userId 用户id
     * @return Integer
     * @Date 2020/8/23 14:38
     */
    Integer getUserCorpusCount(Long userId);
}
