package com.ttms.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnum {
    USER_UNLOGIN(403,"您没有登陆"),
    NOT_AUTHORITY(403,"您没有权限访问"),
    SYSTEM_ERROR(555,"系统错误，请正确访问"),
    USER_NOT_FOUND(404,"查询用户不存在"),
    USER_DELETE_FAIL(500,"查询用户失败"),
    UNAME_OR_PASSWORD_ERROR(500,"用户名或者密码错误"),

    ;
    private int status;
    private String message;
}
