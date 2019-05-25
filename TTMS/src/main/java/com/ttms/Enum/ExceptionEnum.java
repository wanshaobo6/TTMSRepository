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

    ;
    private int status;
    private String message;
}
