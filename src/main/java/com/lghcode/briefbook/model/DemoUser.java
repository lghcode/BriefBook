package com.lghcode.briefbook.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * @Author lgh
 * @Date 2020/8/9 16:50
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemoUser {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String number;

    private Integer status;
}
