package com.ttms.Controller.Project;

import com.ttms.Entity.ProProject;
import com.ttms.Entity.SysUser;
import com.ttms.Vo.PageResult;
import com.ttms.Vo.ProjectVo;
import com.ttms.service.SystemManage.IGroupService;
import com.ttms.service.SystemManage.IProjectService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/producemanage/project")
public class ProjectController {
    @Autowired
    private IProjectService projectService;

    @Autowired
    private IGroupService groupService;
    /**
    * 功能描述: <br>
    * 〈〉查询所有项目
    * @Param: [page, rows, projectnumber, projectname, starttime, endtime, valid, departmentname]
    * @Return: org.springframework.http.ResponseEntity<com.ttms.utils.PageResult<com.ttms.Vo.ProjectVo>>
    * @Author: 吴彬
    * @Date: 20:03 20:03
     */
    @GetMapping("/projectinfomanage/all")
    public ResponseEntity<PageResult<ProjectVo>> queryProjectByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                    @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                                                    @RequestParam(required = false,value ="projectnumber" )String projectnumber,
                                                                    @RequestParam(required = false,value ="projectname" )String projectname,
                                                                    @RequestParam(required = false,value ="starttime" ) Date starttime,
                                                                    @RequestParam(required = false,value ="endtime" )Date endtime,
                                                                    @RequestParam(required = false,value ="valid",defaultValue = "true")Boolean valid,
                                                                    @RequestParam(required = false,value ="departmentname" )String departmentname){

        ProProject project=new ProProject();
        if(valid){
            project.setValid((byte)1);
        }else {
            project.setValid((byte)0);
        }
        project.setStarttime(starttime);
        project.setEndtime(endtime);
        project.setProjectname(projectname);
        project.setProjectnumber(projectnumber);
        PageResult<ProjectVo> result = this.projectService.queryProjectByPage(page, rows, project, departmentname);
        if(CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping("/projectinfomanage/{pid}")
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

}
