package com.lghcode.briefbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lghcode.briefbook.enums.UserActionEnum;
import com.lghcode.briefbook.exception.BizException;
import com.lghcode.briefbook.mapper.*;
import com.lghcode.briefbook.model.*;
import com.lghcode.briefbook.model.vo.UserActionVo;
import com.lghcode.briefbook.service.UserActionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lgh
 * @Date 2020/8/23 12:27
 */
@Service
public class UserActionServiceImpl implements UserActionService {

    @Autowired
    private UserActionMapper userActionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CorpusMapper corpusMapper;
    /**
     * 新增到用户动态表
     *
     * @param userId 用户id
     * @param action 动作类型
     * @param objId  对象id
     * @Author lghcode
     * @Date 2020/8/23 13:32
     */
    @Override
    public void newAction(Long userId, Integer action, Long objId,Integer type) {
        UserAction userAction = UserAction.builder().userId(userId).action(action)
                .objId(objId).type(type).build();
        userActionMapper.insert(userAction);
    }

    /**
     * 取消到用户动态表
     *
     * @param userId 用户id
     * @param action 动作类型
     * @param objId  对象id
     * @Author lghcode
     * @Date 2020/8/23 13:32
     */
    @Override
    public void cannelAction(Long userId, Integer action, Long objId) {
        userActionMapper.delete(new QueryWrapper<UserAction>().lambda()
                .eq(UserAction::getUserId,userId).eq(UserAction::getAction, action)
                .eq(UserAction::getObjId,objId));
    }

    /**
     * 获取用户动态列表
     *
     * @param userId 用户id
     * @return List<UserActionVo>
     * @Author lghcode
     * @Date 2020/8/23 15:27
     */
    @Override
    public List<UserActionVo> getUserActions(Long userId) {
        List<UserAction> userActions = userActionMapper.selectList(new QueryWrapper<UserAction>().lambda()
                .eq(UserAction::getUserId,userId).orderByDesc(UserAction::getCreateTime));
        if (userActions == null){
            throw new BizException("用户动态列表数据为空");
        }
        List<UserActionVo> userActionVoList = new ArrayList<>();
        for (UserAction userAction : userActions) {
            UserActionVo userActionVo = new UserActionVo();
            BeanUtils.copyProperties(userAction,userActionVo);
            User us = userMapper.selectOne(new QueryWrapper<User>().select("nick_name").lambda().eq(User::getId,userActionVo.getUserId()));
            userActionVo.setUserNickName(us.getNickName());
            if (userAction.getAction() == UserActionEnum.FOLLOW.getCode()){
                User user = userMapper.selectOne(new QueryWrapper<User>().select("nick_name").lambda().eq(User::getId,userAction.getObjId()));
                userActionVo.setObjName(user.getNickName());
            }else if(userAction.getAction() == UserActionEnum.PUBLISH.getCode()
                || userAction.getAction() == UserActionEnum.COLLECT.getCode()
                || userAction.getAction() == UserActionEnum.PRAISE.getCode()){
                Article article = articleMapper.selectOne(new QueryWrapper<Article>().select("title").lambda().eq(Article::getId,userAction.getObjId()));
                userActionVo.setObjName(article.getTitle());
            }else if(userAction.getAction() == UserActionEnum.LIKE.getCode()){
                if (userAction.getType() == UserActionEnum.ARTICLE.getCode()){
                    Article article2 = articleMapper.selectOne(new QueryWrapper<Article>().select("title").lambda().eq(Article::getId,userAction.getObjId()));
                    userActionVo.setObjName(article2.getTitle());
                }else if(userAction.getType() == UserActionEnum.COMMEN.getCode()){
                    Comment comment = commentMapper.selectOne(new QueryWrapper<Comment>().select("article_id","from_user_id","cont").lambda().eq(Comment::getId,userAction.getObjId()));
                    userActionVo.setObjName(comment.getCont());
                    User user = userMapper.selectOne(new QueryWrapper<User>().select("nick_name").lambda().eq(User::getId,comment.getFromUserId()));
                    userActionVo.setFromUserId(comment.getFromUserId());
                    userActionVo.setNickName(user.getNickName());
                    Article article = articleMapper.selectOne(new QueryWrapper<Article>().select("title").lambda().eq(Article::getId,comment.getArticleId()));
                    userActionVo.setArticleId(comment.getArticleId());
                    userActionVo.setArticleTitle(article.getTitle());
                }
            }else if(userAction.getAction() == UserActionEnum.COMMENT.getCode()){
                Comment comment = commentMapper.selectOne(new QueryWrapper<Comment>().select("article_id","to_user_id","cont").lambda().eq(Comment::getId,userAction.getObjId()));
                userActionVo.setObjName(comment.getCont());
                Article article = articleMapper.selectOne(new QueryWrapper<Article>().select("title").lambda().eq(Article::getId,comment.getArticleId()));
                userActionVo.setArticleId(comment.getArticleId());
                userActionVo.setArticleTitle(article.getTitle());
                User user = userMapper.selectOne(new QueryWrapper<User>().select("nick_name").lambda().eq(User::getId,comment.getToUserId()));
                if (user != null){
                    userActionVo.setToUserId(comment.getToUserId());
                    userActionVo.setToNickName(user.getNickName());
                }
            }else if(userAction.getAction() == UserActionEnum.REGISTER.getCode()){
                userActionVo.setObjName("注册了新用户");
            }else if(userAction.getAction() == UserActionEnum.SUBSCRIPTION.getCode()){
                Corpus corpus = corpusMapper.selectOne(new QueryWrapper<Corpus>().select("name").lambda().eq(Corpus::getId,userAction.getObjId()));
                userActionVo.setObjName(corpus.getName());
            }
            userActionVoList.add(userActionVo);
        }
        return userActionVoList;
    }
}
