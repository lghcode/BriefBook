package com.lghcode.briefbook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lghcode.briefbook.constant.Constant;
import com.lghcode.briefbook.enums.UserActionEnum;
import com.lghcode.briefbook.enums.UserEnum;
import com.lghcode.briefbook.exception.BizException;
import com.lghcode.briefbook.mapper.ArticleMapper;
import com.lghcode.briefbook.mapper.CorpusMapper;
import com.lghcode.briefbook.mapper.UserCorpusMapper;
import com.lghcode.briefbook.mapper.UserMapper;
import com.lghcode.briefbook.model.Corpus;
import com.lghcode.briefbook.model.User;
import com.lghcode.briefbook.model.UserCorpus;
import com.lghcode.briefbook.model.vo.ArticleVo;
import com.lghcode.briefbook.model.vo.CorpusDetailVo;
import com.lghcode.briefbook.model.vo.UserBaseVo;
import com.lghcode.briefbook.service.CorpusService;
import com.lghcode.briefbook.service.UserActionService;
import com.lghcode.briefbook.service.UserArticleService;
import com.lghcode.briefbook.util.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author lgh
 * @Date 2020/8/20 18:07
 */
@Service
public class CorpusServiceImpl implements CorpusService {

    @Autowired
    private CorpusMapper corpusMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserCorpusMapper userCorpusMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserArticleService userArticleService;

    @Autowired
    private UserActionService userActionService;

    @Autowired
    private ArticleMapper articleMapper;
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

    /**
     * 查看文集详情
     *
     * @param authToken 登录用户token
     * @param corpusId  文集id
     * @return CorpusDetailVo
     * @Author lghcode
     * @Date 2020/8/24 10:06
     */
    @Override
    public CorpusDetailVo getCorpusDetail(String authToken, Long corpusId) {
        CorpusDetailVo corpusDetailVo = new CorpusDetailVo();
        //如果authToken不等于空并且没有过期
        if (!StringUtils.isBlank(authToken) && redisTemplate.hasKey(Constant.REDIS_LOGIN_KEY+authToken)){
            Long loginUserId = jwtTokenUtil.getUserIdFromToken(authToken);
            //判断当前登录用户有没有对这些用户关注过
            Integer m = userCorpusMapper.selectCount(new QueryWrapper<UserCorpus>().lambda()
                    .eq(UserCorpus::getUserId,loginUserId)
                    .eq(UserCorpus::getCorpusId,corpusId));
            if (m > 0){
                corpusDetailVo.setFollow(true);
            }
        }
        Corpus corpus = corpusMapper.selectById(corpusId);
        corpusDetailVo.setCorpusId(corpus.getId());
        corpusDetailVo.setCorpusName(corpus.getName());
        corpusDetailVo.setCreatorId(corpus.getCreatorId());
        User user = userMapper.selectById(corpus.getCreatorId());
        corpusDetailVo.setCreatorName(user.getNickName());
        //获取当前用户发布的公开文章的总字数
        Integer wordCount = userArticleService.getUserWordCount(user.getId());
        corpusDetailVo.setCreatorWordCount(wordCount);
        //获取当前用户收获文章赞的数量
        Integer approvalCount = userArticleService.getUserApprovalCount(user.getId());
        corpusDetailVo.setCreatorLikeCount(approvalCount);
        //获取文集所属文章
        List<ArticleVo> articleVoList = articleMapper.selectListByCorpusId(corpusId);
        corpusDetailVo.setArticleVoList(articleVoList);
        if (articleVoList == null){
            corpusDetailVo.setArticleCount(0);
        }
        corpusDetailVo.setArticleCount(articleVoList.size());
        //获取文集被关注的用户列表
        List<UserBaseVo> userBaseVos = userCorpusMapper.selectUserList(corpusId);
        corpusDetailVo.setFollowUserList(userBaseVos);
        if (userBaseVos == null) {
            corpusDetailVo.setFollowCount(0);
        }
        corpusDetailVo.setFollowCount(userBaseVos.size());
        return corpusDetailVo;
    }

    /**
     * 关注或取消关注 文集
     *
     * @param currentUserId 当前登录用户id
     * @param corpusId      文集id
     * @param type          类型id
     * @Author lghcode
     * @Date 2020/8/24 11:33
     */
    @Override
    public void followCorpus(Long currentUserId, Long corpusId, Integer type) {
        //关注
        if (type == UserEnum.FOLLOW.getCode()){
            Integer count = userCorpusMapper.selectCount(new QueryWrapper<UserCorpus>().lambda()
                    .eq(UserCorpus::getUserId,currentUserId)
                    .eq(UserCorpus::getCorpusId,corpusId));
            if (count > 0){
                throw new BizException("您已经关注过该文集");
            }
            UserCorpus userCorpus = UserCorpus.builder().userId(currentUserId).corpusId(corpusId).createTime(new Date()).build();
            userCorpusMapper.insert(userCorpus);
            //同步用户动态表
            userActionService.newAction(currentUserId, UserActionEnum.SUBSCRIPTION.getCode(),corpusId,UserActionEnum.CORPUS.getCode());
        }else if(type == UserEnum.CANNEL_FOLLOW.getCode()){
            //取消关注
            userCorpusMapper.delete(new QueryWrapper<UserCorpus>().lambda()
                    .eq(UserCorpus::getUserId,currentUserId)
                    .eq(UserCorpus::getCorpusId,corpusId));
            //同步用户动态表
            userActionService.cannelAction(currentUserId,UserActionEnum.SUBSCRIPTION.getCode(),corpusId);
        }

    }

    /**
     * 新建文集
     *
     * @param loginUserId 当前登录用户id
     * @param name        文集名
     * @Author lghcode
     * @Date 2020/8/25 9:59
     */
    @Override
    public void create(Long loginUserId, String name) {
        Integer count = corpusMapper.selectCount(new QueryWrapper<Corpus>().lambda().eq(Corpus::getName,name)
                                .eq(Corpus::getCreatorId,loginUserId));
        if (count > 0) {
            throw new BizException("该文集已经存在");
        }
        Corpus corpus = Corpus.builder().name(name).creatorId(loginUserId).createTime(new Date()).build();
        corpusMapper.insert(corpus);
    }
}
