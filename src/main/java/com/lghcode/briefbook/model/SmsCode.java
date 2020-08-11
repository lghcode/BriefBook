package com.lghcode.briefbook.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;

/**
 * 短信验证码类
 * @Author lgh
 * @Date 2020/8/11 12:01
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsCode {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 短信验证码
     */
    private String code;

    /**
     * 0--登录，1--重置密码
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

}
