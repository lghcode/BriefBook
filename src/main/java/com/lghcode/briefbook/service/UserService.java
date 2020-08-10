package com.lghcode.briefbook.service;

import com.lghcode.briefbook.model.User;

/**
 * @Author lgh
 * @Date 2020/8/10 10:20
 */
public interface UserService {

    /**
     * 检验用户手机号是否存在
     *
     * @Author laiyou
     * @param  mobile 手机号
     * @return boolean
     * @Date 2020/8/10 12:00
     */
    boolean checkUserMobileExist(String mobile);

    /**
     * 根据手机号和密码查询单个用户
     *
     * @Author laiyou
     * @param  mobile 手机号
     * @param  md5Str 加密之后的密码
     * @return User
     * @Date 2020/8/10 12:01
     */
    User getUserByMobileAndMd5Str(String mobile, String md5Str);

    /**
     * 根据用户的id更新昵称
     *
     * @Author laiyou
     * @param id      用户id
     * @param nickname  昵称
     * @Date 2020/8/10 15:37
     */
     void updateNicknameById(Long id,String nickname);
}
