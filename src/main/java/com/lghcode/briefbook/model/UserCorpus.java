package com.lghcode.briefbook.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;

/**
 * 用户-文集 类
 * @Author lgh
 * @Date 2020/8/23 14:32
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCorpus {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long corpusId;

    private Date createTime;
}
