package com.ttms.Controller.SystemManage;

import com.ttms.Entity.SysDepartment;
import com.ttms.Entity.SysMenus;
import com.ttms.Entity.SysRoles;
import com.ttms.Entity.SysUser;
import com.ttms.service.SystemManage.SysMenusService;
import com.ttms.utils.PageResult;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

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
    /**
     * @Description:    查询所有角色
     * @param
     * @Author:         吴彬
     * @UpdateRemark:   修改内容
     * @Version:        1.0
     */
    @GetMapping("/usermanage/getAllRoles")
    public ResponseEntity<List<SysRoles>> getAllRoles(){
        List<SysRoles> rolesList = this.sysMenusService.getAllRoles();
        return ResponseEntity.ok(rolesList);
    }

    /**
     * @Description:    分页查询所有角色
     * @param
     * @Author:         吴彬
     * @UpdateRemark:   修改内容
     * @Version:        1.0
     */
    @GetMapping("/rolemanage/page")
    public ResponseEntity<PageResult<SysRoles>> getRolesByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                                               @RequestParam(value = "name", required = false) String name){
        PageResult<SysRoles> list = this.sysMenusService.getRolesByPage(page, rows, name);
        return ResponseEntity.ok(list);

    }

    /**
     * 功能描述: 添加角色为角色分配权限
     * 〈〉
     * @Param: [name, note, menuIds]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: 吴彬
     * @Date: 16:48 16:48
     */
    @PostMapping("/rolemanage")
    public ResponseEntity<Void> AddRole(@RequestParam("name") String name , @RequestParam(required = false,name = "note") String note , @RequestParam(name = "menuIds") List<Integer> menuIds, HttpSession session){
        session.getAttribute("user");
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        this.sysMenusService.AddRole(name,note,menuIds,user.getId());
        return ResponseEntity.ok().body(null);
    }

    /**
     * 功能描述: <br>
     * 〈〉查询并返回所有的菜单树
     * @Param: []
     * @Return: org.springframework.http.ResponseEntity<java.util.List<com.ttms.Entity.SysMenus>>
     * @Author: 万少波
     * @Date: 2019/5/27 15:03
     */
    @GetMapping("/rolemanage/tree")
    public ResponseEntity<List<SysMenus>> getMenusTree(){
       return ResponseEntity.ok(sysMenusService.getSysMenusTree());
    }

    /**
     * 功能描述: 新增用户
     * 〈〉
     * @Param: [username, image, password, mail, phonenumber]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: lhf
     * @Date: 2019/5/27 15:03
     */
    @PostMapping("/usermanage")
    public ResponseEntity<Void> addSysUser(@RequestParam  String username,@RequestParam String image,
                                              @RequestParam String password,@RequestParam String mail,
                                              @RequestParam String  phonenumber){
        this.sysMenusService.addSysUser(username,image, password,mail,phonenumber);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 功能描述: 修改用户
     * 〈〉
     * @Param: [id, username, image, password, mail, phonenumber]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: lhf
     * @Date: 2019/5/27 15:03
     */
    @PutMapping("/usermanage/{id}")
    public ResponseEntity<Void> updateUserById(@PathVariable("id") Integer id,
                                                  String username,String image,String password,String mail,
                                                  String phonenumber){
        SysUser user = new SysUser();
        user.setId(id);
        user.setUsername(username);
        user.setImage(image);
        user.setPassword(password);
        user.setEmail(mail);
        user.setMobile(phonenumber);
        user.setModifiedtime(new Date());
        SysUser curUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        user.setCreateduserid(curUser.getId());
        sysMenusService.updateUserById(user);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    /**
     * 功能描述: 启用和禁用用户
     * 〈〉
     * @Param: [id]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: lhf
     * @Date: 2019/5/27 15:02
     */
    @GetMapping("/usermanage/valid/{id}")
    public ResponseEntity<Void> validOrInvalid(@PathVariable("id") Integer id){
        sysMenusService.validOrInvalid(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 功能描述: 新增部门
     * 〈〉
     * @Param: [sysdepartment]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: lhf
     * @Date: 2019/5/27 15:01
     */
    @PostMapping("/organparam/add/dartment")
    public ResponseEntity<Void> addDepartment(@RequestParam String departmentName, @RequestParam String departmentCode,
                                              @RequestParam String departmentNote,@RequestParam int parentId){
        this.sysMenusService.addDepartment(departmentName,departmentCode,departmentNote,parentId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
