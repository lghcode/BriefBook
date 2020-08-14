package com.lghcode.briefbook.service;

import com.lghcode.briefbook.model.User;
import com.lghcode.briefbook.model.param.EditProfileParam;
import com.lghcode.briefbook.model.vo.LoginUserInfo;

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
      * 根据不同类型字段更新个人资料
      *
      * @Author lghcode
      * @param  editProfileParam 更新资料参数
      * @return boolean
      * @Date 2020/8/11 16:27
      */
     boolean updateProfile(EditProfileParam editProfileParam);

     /**
      * 用户密码重置更新
      *
      * @Author laiyou
      * @param id 用户id
      * @param password 用户密码
      * @Date 2020/8/11 19:27
      */

    void updateNewPwd(Long id, String password);

    /**
     * 根据用户id查询用户手机号
     *
     * @Author lghcode
     * @param  userId 用户id
     * @return String 用户手机号
     * @Date 2020/8/12 16:39
     */
    String getMobileByUserId(Long userId);

    /**
     * 用户第一次登录进行默认注册
     *
     * @Author lghcode
     * @param  mobile 手机号
     * @Date 2020/8/12 17:16
     */
    void defaultRegister(String mobile);

    /**
     * 根据手机号查找用户
     *
     * @Author lghcode
     * @param mobile 用户手机号
     * @return User
     * @Date 2020/8/12 17:49
     */
    User getUserByMobile(String mobile);

    /**
     * 校验某用户要更改的新昵称是否被其他人占用
     *
     * @Author lghcode
     * @param  userId 用户id
     * @param  nickName 用户昵称
     * @return boolean
     * @Date 2020/8/12 18:05
     */
    boolean checkUserNickNameIsRepeat(Long userId, String nickName);

    /**
     * 判断要更换的手机号是否已经被人注册
     *
     * @Author laiyou
     * @param id 用户id
     * @param newMobile 新手机号
     * @return Boolean
     * @Date 2020/8/13 0:05
     */
    Boolean checkIsNewMobile(Long id, String newMobile);

    /**
     *更新手机号
     *
     * @Author laiyou
     * @param id   用户id
     * @param newMobile 新手机号
     * @Date 2020/8/13 0:13
     */
    void saveNewMobile(Long id, String newMobile);

    /**
     * 获取登录用户基本信息
     *
     * @Author laiyou
     * @param  currUser 登录用户实体类
     * @return LoginUserInfo
     * @Date 2020/8/14 17:13
     */
    LoginUserInfo getLoginUser(User currUser);
}
