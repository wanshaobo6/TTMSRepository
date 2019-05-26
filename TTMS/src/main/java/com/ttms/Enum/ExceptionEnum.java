package com.ttms.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnum {
    NOT_AUTHORITY(403,"您没有得到授权"),
    UNAME_ERROR(500,"用户名密码错误"),
    NOT_FOUND_ROLERS(500,"查询角色失败"),
    INSERT_ROLERS_FILE(500,"新增角色失败"),
    INSERT_ROLERS_MENUS_FILE(500,"新增角色菜单失败"),

    ;
    private int status;
    private String message;
}
