package com.lghcode.briefbook.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;

/**
 * 文集
 * @Author lgh
 * @Date 2020/8/20 17:45
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Corpus {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Long creatorId;

    private Date createTime;
}
