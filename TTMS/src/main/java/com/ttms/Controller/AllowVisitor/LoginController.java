package com.ttms.Controller.AllowVisitor;

import com.ttms.Config.MyThreadLocal;
import com.ttms.Entity.SysMenus;
import com.ttms.Entity.SysUser;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.service.SystemManage.SysMenusService;
import com.ttms.utils.CodecUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private SysMenusService sysMenusService;
    @Autowired
    private MyThreadLocal myThreadLocal;
    /**
     * 功能描述: <br>
     * 〈〉登陆逻辑
     * @Param: []
     * @Return: org.springframework.http.ResponseEntity<java.util.List<com.ttms.Entity.SysMenus>>
     * @Author: 万少波
     * @Date: 2019/5/26 17:05
     */
    @PostMapping
    public ResponseEntity<List<SysMenus>> login(@RequestParam String username , @RequestParam String password){
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //根据用户名查询到用户
        SysUser currUser = sysMenusService.getUserByUserName(username);
        //如果没有该用户则一定登录失败
        if(currUser == null)
            throw new TTMSException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        myThreadLocal.setTempUser(currUser);
        password = CodecUtils.md5Hex(password, currUser.getSalt());
        //封装用户名和密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        try {
            subject.login(usernamePasswordToken);    //只要没有任何异常则表示登录成功
            //查询用户能访问到的菜单并返回
            return ResponseEntity.ok(null);
        }catch (Exception e) {
            //用户名不存在
            throw new TTMSException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
    }
}
