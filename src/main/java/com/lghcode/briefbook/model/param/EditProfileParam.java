package com.lghcode.briefbook.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 用户要更改的字段类型
     */
    @NotNull(message = "要更改的类型不能为空")
    private Integer editType;

    /**
     * 更改后的值
     */
    @NotEmpty(message = "更改后的值不能为空")
    private String editValue;

}
