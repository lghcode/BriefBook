package com.lghcode.briefbook.controller;

import com.lghcode.briefbook.constant.Constant;
import com.lghcode.briefbook.enums.ArticleEnum;
import com.lghcode.briefbook.exception.BizException;
import com.lghcode.briefbook.model.Article;
import com.lghcode.briefbook.model.param.PublishArticleParam;
import com.lghcode.briefbook.model.param.RecommendArticleParam;
import com.lghcode.briefbook.model.param.UpdateArticleParam;
import com.lghcode.briefbook.model.vo.ArticleDetailVo;
import com.lghcode.briefbook.model.vo.RecycleBinListVo;
import com.lghcode.briefbook.service.ArticleService;
import com.lghcode.briefbook.service.UserArticleService;
import com.lghcode.briefbook.util.JwtTokenUtil;
import com.lghcode.briefbook.util.PageResponse;
import com.lghcode.briefbook.util.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 文章模块控制层
 * @Author lgh
 * @Date 2020/8/18 15:28
 */
@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserArticleService userArticleService;

    /**
     * 发布文章
     *
     * @Author lghcode
     * @param  articleParam 发布文章用的参数
     * @return ResultJson
     * @Date 2020/8/18 15:51
     */
    @PostMapping("/publishArticle")
    public ResultJson publishArticle(@Validated PublishArticleParam articleParam, HttpServletRequest request){
        Long loginUserId = jwtTokenUtil.getUserIdFromHeader(request);
        if (!loginUserId.equals(articleParam.getUserId())){
            throw new BizException("当前用户id未登录");
        }
        articleService.publishArticle(articleParam);
        return ResultJson.success("发布成功");
    }

    /**
     * 根据文章id获取文章内容
     *
     * @Author lghcode
     * @param  articleId 文章id不能为空
     * @return ResultJson
     * @Date 2020/8/25 18:52
     */
    @GetMapping("/getOneArticle")
    public ResultJson getOneArticle(@NotNull(message = "文章id不能为空") @RequestParam("articleId") Long articleId){
        Article article = articleService.getOneById(articleId);
        return ResultJson.success("获取成功",article);
    }

    /**
     * 推荐文章列表数据，采用随机文章id
     *
     * @Author lghcode
     * @param  articleParam 请求文章参数
     * @return ResultJson
     * @Date 2020/8/19 14:47
     */
    @GetMapping("/recommendArticle")
    public ResultJson recommendArticle(@Validated RecommendArticleParam articleParam){
        PageResponse<Article> articleIPage = articleService.queryArticle(articleParam);
        return ResultJson.success("查询成功",articleIPage);
    }

    /**
     * 根据文章id获取文章相关信息
     *
     * @Author lghcode
     * @param  articleId 文章id不能为空
     * @return ResultJson
     * @Date 2020/8/19 21:37
     */
    @GetMapping("/getArticleById")
    public ResultJson getArticleById(@NotNull(message = "文章id不能为空") @RequestParam("articleId") Long articleId,
                                     HttpServletRequest request){
        String authToken = request.getHeader(Constant.TOKEN_NAME);
        ArticleDetailVo articleDetailVo = articleService.getArticleById(authToken,articleId);
        return ResultJson.success("获取成功",articleDetailVo);
    }

    /**
     * 点赞/取消点赞  文章
     *
     * @Author lghcode
     * @param articleId 文章id
     * @param type 0-点赞，1-取消点赞
     * @return ResultJson
     * @Date 2020/8/22 11:00
     */
    @PostMapping("/like")
    public ResultJson like(@NotNull(message = "文章id不能为空") @RequestParam("articleId") Long articleId,
                           @NotNull(message = "点赞类型不能为空") @RequestParam("type") Integer type,
                           HttpServletRequest request){
        Long userId = jwtTokenUtil.getUserIdFromHeader(request);
        userArticleService.userLikeArticle(userId,articleId,type);
        return ResultJson.success("操作成功");
    }

    /**
     * 收藏/取消收藏  文章
     *
     * @Author lghcode
     * @param articleId 文章id
     * @param type 0-收藏，1-取消收藏
     * @return ResultJson
     * @Date 2020/8/22 11:00
     */
    @PostMapping("/collect")
    public ResultJson collect(@NotNull(message = "文章id不能为空") @RequestParam("articleId") Long articleId,
                           @NotNull(message = "收藏类型不能为空") @RequestParam("type") Integer type,
                           HttpServletRequest request){
        Long userId = jwtTokenUtil.getUserIdFromHeader(request);
        userArticleService.userCollectArticle(userId,articleId,type);
        return ResultJson.success("操作成功");
    }

    /**
     * 赞赏/取消赞赏  文章
     *
     * @Author lghcode
     * @param articleId 文章id
     * @return ResultJson
     * @Date 2020/8/22 11:00
     */
    @PostMapping("/praise")
    public ResultJson praise(@NotNull(message = "文章id不能为空") @RequestParam("articleId") Long articleId,
                              @NotEmpty(message = "简钻不能为空") @RequestParam("diamond") String diamond,
                              HttpServletRequest request){
        Long userId = jwtTokenUtil.getUserIdFromHeader(request);
        userArticleService.userPraiseArticle(userId,diamond,articleId);
        return ResultJson.success("操作成功");
    }

    /**
     * 更新文章
     *
     * @Author lghcode
     * @param updateArticleParam 更新参数
     * @return ResultJson
     * @Date 2020/8/25 15:23
     */
    @PostMapping("/updateArticle")
    public ResultJson updateArticle(@Validated UpdateArticleParam updateArticleParam){
        articleService.updateArticle(updateArticleParam);
        return ResultJson.success("更新成功");
    }

    /**
     * 将公开文章设为私密文章
     *
     * @Author lghcode
     * @param articleId 文章id
     * @return ResultJson
     * @Date 2020/8/25 15:23
     */
    @PostMapping("/setPrivate")
    public ResultJson setPrivate(@NotNull(message = "文章id不能为空") @RequestParam("articleId") Long articleId){
        articleService.setArticleAccess(articleId,ArticleEnum.PRIVATE_ARTICLE.getCode());
        return ResultJson.success("设置成功");
    }

    /**
     * 将私密文章发布公开
     *
     * @Author lghcode
     * @param articleId 文章id
     * @return ResultJson
     * @Date 2020/8/25 15:23
     */
    @PostMapping("/setPublic")
    public ResultJson setPublic(@NotNull(message = "文章id不能为空") @RequestParam("articleId") Long articleId){
        articleService.setArticleAccess(articleId,ArticleEnum.PUBLIC_ARTICLE.getCode());
        return ResultJson.success("设置成功");
    }

    /**
     * 删除文章
     *
     * @Author lghcode
     * @param articleId 文章id
     * @return ResultJson
     * @Date 2020/8/25 18:26
     */
    @PostMapping("/delete")
    public ResultJson delete(@NotNull(message = "文章id不能为空") @RequestParam("articleId") Long articleId){
        articleService.deleteArticle(articleId);
        return ResultJson.success("删除成功");
    }

    /**
     * 获取用户的回收站文章列表
     *
     * @Author lghcode
     * @param userId 当前登录用户id
     * @return ResultJson
     * @Date 2020/8/25 19:08
     */
    @GetMapping("/recycleBinList")
    public ResultJson recycleBinList(@NotNull(message = "用户id不能为空") @RequestParam("userId") Long userId,HttpServletRequest request){
        Long curUserId = jwtTokenUtil.getUserIdFromHeader(request);
        if (!curUserId.equals(userId)){
            throw new BizException("非法访问");
        }
        List<RecycleBinListVo> recycleBinListVos = articleService.getRecycleBinList(curUserId);
        return ResultJson.success("查询成功",recycleBinListVos);
    }

    /**
     * 彻底删除文章
     *
     * @Author lghcode
     * @param articleId 文章id
     * @return ResultJson
     * @Date 2020/8/25 18:26
     */
    @PostMapping("/deleteForever")
    public ResultJson deleteForever(@NotNull(message = "文章id不能为空") @RequestParam("articleId") Long articleId){
        articleService.deleteForeverArticle(articleId);
        return ResultJson.success("删除成功");
    }

    /**
     * 恢复文章
     *
     * @Author lghcode
     * @param articleId 文章id
     * @return ResultJson
     * @Date 2020/8/25 18:26
     */
    @PostMapping("/restore")
    public ResultJson restore(@NotNull(message = "文章id不能为空") @RequestParam("articleId") Long articleId){
        articleService.restoreArticle(articleId);
        return ResultJson.success("恢复成功");
    }
}
