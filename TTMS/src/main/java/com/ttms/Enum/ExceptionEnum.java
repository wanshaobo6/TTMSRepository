package com.ttms.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnum {
    NOT_AUTHORITY(403,"您没有得到授权"),

    ;
    private int status;
    private String message;
}
