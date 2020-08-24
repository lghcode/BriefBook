package com.lghcode.briefbook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lghcode.briefbook.model.User;
import com.lghcode.briefbook.model.vo.CorpusListVo;

import java.util.List;

/**
 * @Author lgh
 * @Date 2020/8/10 10:18
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查看用户的文集列表
     *
     * @Author lghcode
     * @param  userId 用户id
     * @return List<CorpusListVo>
     * @Date 2020/8/24 9:39
     */
    List<CorpusListVo> getUserCorpusList(Long userId);
}
