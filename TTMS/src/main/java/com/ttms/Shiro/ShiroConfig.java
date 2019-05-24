package com.ttms.Shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("ALL")
@Configuration
public class ShiroConfig {
   /*创建ShrioFilterFactoryBean*/
    //@Bean ---------------------------！！！
    public ShiroFilterFactoryBean getShrioFilterFactoryBean(@Qualifier("webSecurityManager")DefaultWebSecurityManager securityManager,
                                                            /*@Qualifier("authorizationFilter")*/ CustomRolesAuthorizationFilter authorizationFilter){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        /*设置安全管理器*/
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        HashMap<String, Filter> filters = new HashMap<>();
        filters.put("roles",authorizationFilter);
        shiroFilterFactoryBean.setFilters(filters);
          /**
         * Shiro内置过滤器
         *   常用过滤器:
         *      anno:无序认证可以访问
         *      authc：必须认证才可以访问
         *      user:必须rememberme才能访问
         *      perms: 该资源必须得到授权
         *      role:  该资源必须得到角色权限
           *          正常情况下URL路径的拦截设置如下:
           *       /admins/user/**=roles[admin]
           *      参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如/admins/user/**=roles["admin,guest"]
           *     但是这个设置方法是需要每个参数满足才算通过，相当于hasAllRoles()方法。
         */
//        Map<String,String> filterMap = new LinkedHashMap<String,String>();
//        filterMap.put("/add","perms[admins]");
//        filterMap.put("/update","authc");
        /*授权拦截后 ， 自动跳到为授权拦截页面*/
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }
    /*创建DefaultWebSecurityManager*/
    @Bean("webSecurityManager")
    public DefaultWebSecurityManager getDefaultSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    /*创建Realm*/
    @Bean("userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }

  /*  @Bean("authorizationFilter")
    public CustomRolesAuthorizationFilter getCustomRolesAuthorizationFilter(){
        return new CustomRolesAuthorizationFilter();
    }*/
}