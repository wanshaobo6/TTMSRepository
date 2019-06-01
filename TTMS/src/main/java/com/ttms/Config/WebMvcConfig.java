package com.ttms.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    /**
     * 功能描述: <br>
     * 〈〉
     * @Param: [registry]
     * @Return: void
     * @Author: 万少波
     * @Date: 2019/6/1 15:31
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }
}
