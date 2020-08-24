package com.lghcode.briefbook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lghcode.briefbook.model.UserCorpus;
import com.lghcode.briefbook.model.vo.UserBaseVo;

import java.util.List;

/**
 * @Author lgh
 * @Date 2020/8/23 14:34
 */
public interface UserCorpusMapper extends BaseMapper<UserCorpus> {
    List<UserBaseVo> selectUserList(Long corpusId);
}
