package com.ttms.Config;

import com.ttms.Filter.ChargerOpeProductFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public ChargerOpeProductFilter createChargerOpeProductFilter(){
        return new ChargerOpeProductFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //只让负责人对产品相关一切进行操作
        registry.addInterceptor(this.createChargerOpeProductFilter()).addPathPatterns("/producemanage/product/productlist/privilege/**");
    }
}
