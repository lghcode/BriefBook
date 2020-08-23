package com.lghcode.briefbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lghcode.briefbook.mapper.CorpusMapper;
import com.lghcode.briefbook.model.Corpus;
import com.lghcode.briefbook.service.CorpusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author lgh
 * @Date 2020/8/20 18:07
 */
@Service
public class CorpusServiceImpl implements CorpusService {

    @Autowired
    private CorpusMapper corpusMapper;
    /**
     * 获取用户新建的文集数量
     *
     * @param userId 用户id
     * @return Integer
     * @Author lghcode
     * @Date 2020/8/23 14:53
     */
    @Override
    public Integer getUserCorpusCount(Long userId) {
        return corpusMapper.selectCount(new QueryWrapper<Corpus>().lambda()
                    .eq(Corpus::getCreatorId,userId));
    }
}
