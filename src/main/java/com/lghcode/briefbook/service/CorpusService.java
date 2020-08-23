package com.lghcode.briefbook.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @Author lgh
 * @Date 2020/8/20 18:07
 */
@Transactional(rollbackFor = Exception.class)
public interface CorpusService {
    /**
     * 获取用户新建的文集数量
     *
     * @Author lghcode
     * @param userId 用户id
     * @return Integer
     * @Date 2020/8/23 14:53
     */
    Integer getUserCorpusCount(Long userId);
}
