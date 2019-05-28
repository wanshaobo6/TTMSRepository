package com.ttms.Controller.ProduceManage;

import com.ttms.service.SystemManage.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

public class GroupController {
   @Autowired
   private IGroupService groupService;

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
        groupService.getAllGroups();
        return ResponseEntity.ok(null);
    }
}
