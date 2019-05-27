package com.ttms.Controller.ProduceManage;

import com.ttms.service.SystemManage.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//----------产品管理->产品
@RestController
@RequestMapping("/producemanage/project")
public class ProductController {
    @Autowired
    private IProjectService projectService;

    /**
     * 功能描述: <br>
     * 〈〉查询所有团信息
     * @Param: []
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: 万少波
     * @Date: 2019/5/27 17:35
     */
    @RequestMapping("/group/all")
    public ResponseEntity<Void> getAllGroups(){
        projectService.getAllGroups();
        return ResponseEntity.ok(null);
    }
}
