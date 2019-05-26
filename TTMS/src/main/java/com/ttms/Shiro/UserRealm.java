package com.ttms.Shiro;

import com.ttms.Config.MyThreadLocal;
import com.ttms.Entity.SysUser;
import com.ttms.service.SystemManage.SysMenusService;
import com.ttms.utils.CodecUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
//ShiroConfig中已加入了Spring容器
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private MyThreadLocal myThreadLocal;

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
        try{
            System.out.println("执行认证逻辑");
            //如果这里返回null shiro底层会抛出一个UnknowAccountException
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
            String username = token.getUsername();
            SysUser currUser = myThreadLocal.getTempUser();
            //第一个参数为需要返回给login方法的返回值  第二个是数据库数据的密码
            return new SimpleAuthenticationInfo(currUser,currUser.getPassword(),"");
        }finally {
            myThreadLocal.removeTempUser();
        }
    }
}
