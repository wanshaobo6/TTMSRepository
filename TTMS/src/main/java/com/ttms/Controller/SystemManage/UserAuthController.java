package com.ttms.Controller.SystemManage;

import com.ttms.Entity.SysUser;
import com.ttms.Exception.TTMSException;
import com.ttms.service.SystemManage.SysMenusService;
import com.ttms.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//系统管理->用户权限模块
@RestController
@RequestMapping("/sysmanage/userauth")    //类中所有方法访问都需要加上共享
public class UserAuthController {
    @Autowired
    private SysMenusService sysMenusService;

    /*功能描述：分页查询已注册的用户
    *@author罗占
    *@Description
    *Date15:21 2019/5/26
    *Param
    *return
    **/
@GetMapping("/usermanage/page")
    public ResponseEntity<PageResult<SysUser>> queryUserByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                              @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                                              @RequestParam(value = "name", required = false) String name){

    PageResult<SysUser> result = sysMenusService.queryUserByPage(page, rows, name);
    return ResponseEntity.ok().body(result);
}

    /*
    *功能描述：根据id查询用户
    *@author罗占
    *@Description
    *Date15:38 2019/5/26
    *Param
    *return
    **/
    @GetMapping("/usermanage/{id}")
    public ResponseEntity<SysUser> getUserById(@PathVariable("id")Integer id){
        SysUser user = sysMenusService.getUserById(id);
    return ResponseEntity.ok().body(user);
    }

    /*
     *功能描述：根据id删除用户
     *@author罗占
     *@Description
     *Date17:01 2019/5/26
     *Param
     *return
     **/
    @DeleteMapping("/usermanage/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Integer id){
        this.sysMenusService.deleteUserById(id);
        return ResponseEntity.ok().body(null);
    }
 }
