package com.ttms.Shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {
    static final String name = "lisi";
    static final String password = "123";

    /*执行授权逻辑*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println(" 执行授权逻辑 " );
        /*给资源进行授权*/
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set set = new HashSet<String>();
        set.add("admin");
        info.setRoles(set);
        //获得当前用户
        Subject subject = SecurityUtils.getSubject();
        //获取认证后传过来的值
        Object principal = subject.getPrincipal();
        //doSomesThing
        info.addStringPermission("sysmanage:sysconfig");
        return info;
    }

    /*执行认证逻辑*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");

        //如果这里返回null shiro底层会抛出一个UnknowAccountException
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if(!token.getUsername().equals(name))
            return null;
        //第一个参数为需要返回给login方法的返回值  第二个是数据库的密码
        return new SimpleAuthenticationInfo("",password,"");
    }
}
