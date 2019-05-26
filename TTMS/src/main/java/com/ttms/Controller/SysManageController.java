package com.ttms.Controller;

import com.ttms.Entity.SysMenus;
import com.ttms.service.SystemManage.SysMenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SysManageController {
    @Autowired
    SysMenusService sysMenusService;

    @RequestMapping("/hello")
    public ResponseEntity<List<SysMenus>> getSysMenusTree(){
        System.out.println("sysMenusService = " + sysMenusService.getUrlPermissionMapping());
        return ResponseEntity.ok(null);
    }
}
