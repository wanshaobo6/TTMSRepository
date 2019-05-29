package com.ttms.Controller.SystemManage;

import com.ttms.service.SystemManage.SysMenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//系统管理->用户权限模块->组织机构管理
@RestController
@RequestMapping("/sysmanage/userauth/organmanage")    //类中所有方法访问都需要加上共享
public class OrganizeManageController {

    @Autowired
    private SysMenusService sysMenusService;

    /**
     * 功能描述: 新增部门
     * 〈〉
     * @Param: [sysdepartment]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: lhf
     * @Date: 2019/5/27 15:01
     */
    @PostMapping("/add/dartment")
    public ResponseEntity<Void> addDepartment(@RequestParam String departmentName, @RequestParam String departmentCode,
                                              @RequestParam String departmentNote, @RequestParam int parentId){
        this.sysMenusService.addDepartment(departmentName,departmentCode,departmentNote,parentId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
