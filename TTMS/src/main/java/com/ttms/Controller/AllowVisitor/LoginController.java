package com.ttms.Controller.AllowVisitor;

import com.ttms.Config.MyThreadLocal;
import com.ttms.Entity.SysMenus;
import com.ttms.Entity.SysUser;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Vo.ModulesVo;
import com.ttms.service.AllowVisitor.LoginService;
import com.ttms.service.SystemManage.SysMenusService;
import com.ttms.utils.CodecUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private MyThreadLocal myThreadLocal;
    @Autowired
    private LoginService loginService;
    /**
     * 功能描述: <br>
     * 〈〉登陆逻辑
     * @Param: []
     * @Return: org.springframework.http.ResponseEntity<java.util.List<com.ttms.Entity.SysMenus>>
     * @Author: 万少波
     * @Date: 2019/5/26 17:05
     */
    @PostMapping("/login")
    public ResponseEntity<List<ModulesVo>> login(@RequestParam String username , @RequestParam String password){
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //根据用户名查询到用户
        SysUser currUser = loginService.getUserByUserName(username);
        //如果没有该用户则一定登录失败
        if(currUser == null)
            throw new TTMSException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        //如果用户被禁用
        if(currUser.getValid() == 0){
            throw new TTMSException(ExceptionEnum.USER_HAVE_BEEN_LIMIT);
        }
        myThreadLocal.setTempUser(currUser);
        password = CodecUtils.md5Hex(password, currUser.getSalt());
        //封装用户名和密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        try {
            subject.login(usernamePasswordToken);    //只要没有任何异常则表示登录成功
            log.debug("用户" + currUser.getUsername() + "退出登录");
            //查询用户能访问到的菜单并返回
            return ResponseEntity.ok(loginService.getUserMenusVo());
        }catch (LockedAccountException e) {
            //该账户已被锁定
            throw new TTMSException(ExceptionEnum.USER_ACCOUNT_LOCK);
        }catch (Exception e) {
            //用户名不存在
            throw new TTMSException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉退出登录
     * @Param: []
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: 万少波
     * @Date: 2019/6/2 13:51
     */
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(){
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser)subject.getPrincipal();
        if (subject.isAuthenticated()) {
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
              log.debug("用户" + user.getUsername() + "退出登录");
        }
        return ResponseEntity.ok(null);
    }
}
