package com.lghcode.briefbook.model.param;

import lombok.Data;

/**
 * 编辑个人资料请求参数类
 * @Author lgh
 * @Date 2020/8/11 15:30
 */
@Data
public class EditProfileParam {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户要更改的字段类型
     */
    private Integer editType;

    /**
     * 更改后的值
     */
    private String editValue;

}
