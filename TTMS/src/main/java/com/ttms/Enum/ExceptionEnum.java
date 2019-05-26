package com.ttms.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnum {
    USER_UNLOGIN(403,"您没有登陆"),
    NOT_AUTHORITY(403,"您没有权限访问"),
    SYSTEM_ERROR(555,"系统错误，请正确访问"),
    UNAME_ERROR(500,"用户名密码错误"),
    NOT_FOUND_LIST(404,"查询用户失败"),
    NOT_FOUND_USER(404,"查询用户不存在"),
    USER_DELETE_FAIL(500,"查询用户失败")，
    NOT_FOUND_ROLERS(500,"查询角色失败"),
    INSERT_ROLERS_FILE(500,"新增角色失败"),
    INSERT_ROLERS_MENUS_FILE(500,"新增角色菜单失败"),


    ;
    private int status;
    private String message;
}
