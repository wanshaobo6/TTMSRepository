package com.ttms.Controller.ProduceManage;

import com.ttms.service.ProductManage.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ttms.service.SystemManage.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//----------产品管理->产品
@RestController
@RequestMapping("/producemanage")
public class ProductController {
    @Autowired
    private IProjectService projectService;

    @Autowired
    private ProductService productService;

    /**
     * 功能描述: 修改团信息
     * 〈〉
     * @Param: [groupId, groupName, belongProjectId, chargeUserId, groupNote]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: lhf
     * @Date: 2019/5/28 8:59
     */
    @PutMapping("/project/group")
    public ResponseEntity<Void> updateGroup(@RequestParam int groupId,@RequestParam String groupName, @RequestParam int belongProjectId,
                                            @RequestParam int chargeUserId, @RequestParam String groupNote){
        this.productService.updateGroup(groupId,groupName,belongProjectId,chargeUserId,groupNote);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

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
