package com.lghcode.briefbook.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 赞赏用户Vo
 * @Author lgh
 * @Date 2020/8/19 22:12
 */
@Data
public class PraiseUserVo {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 赞赏用户名
     */
    private String userName;

    /**
     * 赞赏用户头像
     */
    private String headImg;

    /**
     * 赞赏时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date praiseTime;
}
