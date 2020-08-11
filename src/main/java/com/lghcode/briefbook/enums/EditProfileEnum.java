package com.lghcode.briefbook.enums;

/**
 * @Author lgh
 * @Date 2020/8/11 15:07
 */
public enum  EditProfileEnum implements BaseEnum{

    /**
     * 更改类型枚举
     */
    EDIT_HEADIMG(1,"更改头像"),
    EDIT_NICKNAME(2,"更改昵称"),
    EDIT_SEX(3,"更改性别"),
    EDIT_BIRTHDAY(4,"更改生日"),
    EDIT_HOMEPAGE(5,"更改主页"),
    EDIT_PROFILE(6,"更改个人签名")
    ;


    private int code;
    private String desc;


    EditProfileEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public static String getEnumDescByCode(int code){
        for (EditProfileEnum editProfileEnum : EditProfileEnum.values()){
            if (code == editProfileEnum.getCode()) {
                return editProfileEnum.getDesc();
            }
        }
        return null;
    }
}
