package com.lghcode.briefbook.controller;

import com.lghcode.briefbook.constant.Constant;
import com.lghcode.briefbook.exception.BizException;
import com.lghcode.briefbook.model.Article;
import com.lghcode.briefbook.model.param.PublishArticleParam;
import com.lghcode.briefbook.model.param.RecommendArticleParam;
import com.lghcode.briefbook.model.vo.ArticleDetailVo;
import com.lghcode.briefbook.service.ArticleService;
import com.lghcode.briefbook.util.JwtTokenUtil;
import com.lghcode.briefbook.util.PageResponse;
import com.lghcode.briefbook.util.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

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
}
