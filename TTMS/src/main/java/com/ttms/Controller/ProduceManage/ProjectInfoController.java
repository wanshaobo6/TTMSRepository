package com.ttms.Controller.ProduceManage;

import com.ttms.Entity.ProProject;
import com.ttms.Entity.SysDepartment;
import com.ttms.Entity.SysUser;
import com.ttms.Vo.PageResult;
import com.ttms.Vo.ProjectVo;
import com.ttms.service.ProductManage.IGroupService;
import com.ttms.service.ProductManage.IProjectService;
import com.ttms.service.ProductManage.ServiceImpl.ProjectInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//----------产品管理->产品-项目信息管理
@RestController
@RequestMapping("/producemanage/project/projectinfomanage")
public class ProjectInfoController {


    @Autowired
    private IProjectService projectService;


    /**
     * 功能描述: <br>
     * 〈〉分页条件查询项目
     * @Param: [projectNumber, projectName, departmentid, startTime, endTime, valid, page, rows]
     * @Return: org.springframework.http.ResponseEntity<com.ttms.Vo.PageResult<com.ttms.Entity.ProProject>>
     * @Author: 万少波
     * @Date: 2019/5/29 15:16
     */
    @RequestMapping("/page")
    public ResponseEntity<PageResult<ProProject>> getAllProjectByPage(@RequestParam(required = false) String projectNumber,
                                                                      @RequestParam(required = false) String projectName,
                                                                      @RequestParam(required = false , defaultValue = "-1")int departmentid,
                                                                      @RequestParam(required = false) Date startTime,
                                                                      @RequestParam(required = false) Date endTime,
                                                                      @RequestParam(required = false,defaultValue = "-1")int valid,
                                                                      @RequestParam(required = false,defaultValue = "1")int page ,
                                                                      @RequestParam(required = false,defaultValue = "5")int rows){
        return ResponseEntity.ok(projectService.getAllProjectByPage(projectNumber,projectName,departmentid,startTime,endTime,valid,page,rows));
    }


    @PutMapping("/{pid}")
    public ResponseEntity<Void> addProject(@RequestParam(required = true,value ="projectnumber" )String projectnumber,
                                           @PathVariable(value = "pid",required = true) Integer pid,
                                           @RequestParam(required = true,value ="projectname" )String projectname,
                                           @RequestParam(required = false,value ="starttime" ) Date starttime,
                                           @RequestParam(required = false,value ="endtime" )Date endtime,
                                           @RequestParam(value = "note",required = false) String note ,@RequestParam(required = false,value ="departmentname" ) String departmentname){
        SysUser user=(SysUser) SecurityUtils.getSubject().getPrincipal();
        //判断是否角色是否为产品经理。。。。。。
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 功能描述: <br>
     * 〈〉查询产品部下面的子部门
     * @Param: []
     * @Return: org.springframework.http.ResponseEntity<java.util.List<com.ttms.Entity.SysDepartment>>
     * @Author: 万少波
     * @Date: 2019/5/30 8:11
     */
    @GetMapping("/subDepOfProduction")
    public ResponseEntity<List<SysDepartment>> getSubdepartmentProductDepartment(){
        return ResponseEntity.ok(projectService.getSubdepartmentProductDepartment());
    }
}
