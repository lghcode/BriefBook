package com.lghcode.briefbook.controller;

import com.lghcode.briefbook.model.param.CommentPublishParam;
import com.lghcode.briefbook.service.CommentService;
import com.lghcode.briefbook.util.JwtTokenUtil;
import com.lghcode.briefbook.util.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lgh
 * @Date 2020/8/22 15:06
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 发表或回复评论
     *
     * @Author lghcode
     * @param  publishParam 发表评论参数
     * @return ResultJson
     * @Date 2020/8/22 15:34
     */
    @PostMapping("/publish")
    public ResultJson publish(@Validated CommentPublishParam publishParam, HttpServletRequest request){
        Long loginId = jwtTokenUtil.getUserIdFromHeader(request);
        commentService.publishComment(publishParam,loginId);
        return ResultJson.success("发表或回复评论成功");
    }
}
