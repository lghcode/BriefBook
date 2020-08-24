package com.lghcode.briefbook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lghcode.briefbook.model.UserFans;
import com.lghcode.briefbook.model.vo.UserListVo;

import java.util.List;

/**
 * @Author:LaLion
 * @Date:2020/8/1319:48
 * @Version 1.0
 * @todo
 */
public interface UserFansMapper extends BaseMapper<UserFans> {


    /**
     * 获取某个用户关注的用户列表数据
     *
     * @param userId 某个用户id
     * @return List<UserListVo>
     * @Author lghcode
     * @Date 2020/8/24 8:15
     */
    List<UserListVo> getUserFollow(Long userId);

    /**
     * 获取某个用户的粉丝用户列表数据
     *
     * @param userId 某个用户id
     * @return List<UserListVo>
     * @Author lghcode
     * @Date 2020/8/24 8:15
     */
    List<UserListVo> getUserFans(Long userId);
}
