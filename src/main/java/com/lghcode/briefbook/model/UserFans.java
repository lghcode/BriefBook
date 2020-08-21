package com.lghcode.briefbook.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;

/**
 * 用户粉丝实体类
 * @Author:LaLion
 * @Date:2020/8/1318:33
 * @Version 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFans {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private  Long id;
    /**
     * 用户粉丝id
     */
    private Long userId;
    /**
     * 粉丝id
     */
    private Long fansId;
    /**
     * 创建实际
     */
    private Date createTime;
}
