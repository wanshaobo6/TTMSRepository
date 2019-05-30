package com.ttms.Controller.ProduceManage;

import com.ttms.Entity.ProGroup;
import com.ttms.service.ProductManage.ICreateProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//----------产品管理->产品->创建产品------------
@RestController
@RequestMapping("/producemanage/product/createproduct")
public class CreateProjectController {
    @Autowired
    private ICreateProjectService createProjectService;
    /*
    *功能描述：查询所有团号
    *@author罗占
    *@Description
    *Date17:04 2019/5/30
    *Param
    *return
    **/
    @GetMapping("/queryAllGroups")
    public ResponseEntity<List<ProGroup>> queryAllGroups(){
        List<ProGroup> groups = this.createProjectService.queryAllGroups();
        return ResponseEntity.ok().body(groups);
    }
}
