package com.ttms.Config;

import com.ttms.service.SysMenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//SpringBoot容器启动后执行
///@Component
public class ExecuteOnStartUp implements CommandLineRunner {
    public void run(String... args) throws Exception {
        System.out.println("执行commandLineRunner方法");
    }
}
