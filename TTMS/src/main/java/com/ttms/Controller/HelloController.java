package com.ttms.Controller;

import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello(){
        throw new TTMSException(ExceptionEnum.NOT_AUTHORITY);
    }
}
