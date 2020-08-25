package com.lghcode.briefbook.service;

import com.lghcode.briefbook.model.vo.CorpusDetailVo;
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

    /**
     * 查看文集详情
     *
     * @Author lghcode
     * @param authToken 登录用户token
     * @param corpusId 文集id
     * @return CorpusDetailVo
     * @Date 2020/8/24 10:06
     */
    CorpusDetailVo getCorpusDetail(String authToken, Long corpusId);

    /**
     * 关注或取消关注 文集
     *
     * @Author lghcode
     * @param currentUserId 当前登录用户id
     * @param corpusId 文集id
     * @param type 类型id
     * @Date 2020/8/24 11:33
     */
    void followCorpus(Long currentUserId, Long corpusId, Integer type);

    /**
     * 新建文集
     *
     * @Author lghcode
     * @param loginUserId 当前登录用户id
     * @param name 文集名
     * @Date 2020/8/25 9:59
     */
    void create(Long loginUserId, String name);
}
