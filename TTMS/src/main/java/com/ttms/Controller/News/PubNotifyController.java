package com.ttms.Controller.News;

import com.ttms.Entity.SysUser;
import com.ttms.service.ProductManage.IPubNotifyService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//----------------------消息中心-通知管理-发布通知------------------------
@RestController
@RequestMapping("/news/notifymanage/pubnotify")
public class PubNotifyController {

    @Autowired
    private IPubNotifyService iPubNotifyService;
    /**
    * 功能描述: <br>
    * 〈〉发布通知
    * @Param: [messageClassName, messageTitle, messageContent]
    * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
    * @Author: 吴彬
    * @Date: 10:55 10:55
     */
    @PostMapping("/pubmsg")
    public ResponseEntity<Void> publicMsg(@RequestParam String messageClassName,@RequestParam  String messageTitle,@RequestParam  String messageContent
        ){
        SysUser user  = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return ResponseEntity.ok(this.iPubNotifyService.publicMsg(messageClassName,
                messageTitle, messageTitle, user));
    }

}
