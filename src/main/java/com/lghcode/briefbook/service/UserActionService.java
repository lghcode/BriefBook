package com.lghcode.briefbook.service;

import com.lghcode.briefbook.model.vo.UserActionVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author lgh
 * @Date 2020/8/23 12:27
 */
@Transactional(rollbackFor = Exception.class)
public interface UserActionService {

    /**
     * 新增到用户动态表
     *
     * @Author lghcode
     * @param  userId 用户id
     * @param  action 动作类型
     * @param  objId 对象id
     * @param  type 对象类型
     * @Date 2020/8/23 13:32
     */
    void newAction(Long userId,Integer action,Long objId,Integer type);

    /**
     * 取消到用户动态表
     *
     * @Author lghcode
     * @param  userId 用户id
     * @param  action 动作类型
     * @param  objId 对象id
     * @Date 2020/8/23 13:32
     */
    void cannelAction(Long userId,Integer action,Long objId);

    /**
     * 获取用户动态列表
     *
     * @Author lghcode
     * @param  userId 用户id
     * @return List<UserActionVo>
     * @Date 2020/8/23 15:27
     */
    List<UserActionVo> getUserActions(Long userId);
}
