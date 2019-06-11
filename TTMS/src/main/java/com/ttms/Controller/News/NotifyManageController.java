package com.ttms.Controller.News;

import com.ttms.Entity.MesMessage;
import com.ttms.Entity.SysUser;
import com.ttms.Vo.PageResult;
import com.ttms.service.ProductManage.INotifyManageService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//----------------------消息中心-通知管理-通知管理------------------------
@RestController
@RequestMapping("/news/notifymanage/notifymanage")
public class NotifyManageController {

    @Autowired
    private INotifyManageService iNotifyManageService;

    /**
    * 功能描述: <br>
    * 〈〉查询所有消息
    * @Param: []
    * @Return: org.springframework.http.ResponseEntity<java.util.List<com.ttms.Entity.MesMessage>>
    * @Author: 吴彬
    * @Date: 10:03 10:03
     */
    @GetMapping("/queryAllnew")
    public ResponseEntity<List<MesMessage>> queryAllnew(){
        return ResponseEntity.ok(this.iNotifyManageService.queryAllnew());
    }

   /**
   * 功能描述: <br>
   * 〈〉消息类型，发布人，消息标题
   * @Param: [messageclassname, messagetitle, sendName]
   * @Return: org.springframework.http.ResponseEntity<java.util.List<com.ttms.Entity.MesMessage>>
   * @Author: 吴彬
   * @Date: 8:29 8:29
    */
    @GetMapping("/queryAllnew/page")
    public ResponseEntity<PageResult<MesMessage>> queryAllnewPage(@RequestParam(value = "page" ,defaultValue = "1")
                                                                        Integer page, @RequestParam(value = "rows" ,defaultValue = "5")
                                                                Integer rows, @RequestParam(required = false) String messageclassname,
                                                                  @RequestParam(required = false) String messagetitle,
                                                                  @RequestParam(required = false) String sendName
                                                            ){
        return ResponseEntity.ok(this.iNotifyManageService.queryAllnewPage
                (page,rows,messageclassname,messagetitle,sendName));
    }

    /**
    * 功能描述: <br>
    * 〈〉查询所有有关自己的通知
    * @Param: []
    * @Return: org.springframework.http.ResponseEntity<java.util.List<com.ttms.Entity.MesMessage>>
    * @Author: 吴彬
    * @Date: 10:04 10:04
     */
    @GetMapping("/ofme")
    public ResponseEntity<List<MesMessage>> querybyUser(){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return ResponseEntity.ok(this.iNotifyManageService.querybyUser(user));
    }

    @GetMapping("{mid}")
    public ResponseEntity<Void> updateState(@PathVariable("mid") Integer mid){
        this.iNotifyManageService.updateState(mid);
        return ResponseEntity.ok().build();
    }

}
