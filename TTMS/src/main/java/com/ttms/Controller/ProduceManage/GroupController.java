package com.ttms.Controller.ProduceManage;

import com.ttms.Vo.GroupManageVo;
import com.ttms.service.ProductManage.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/producemanage/project/group")
public class GroupController {

    @Autowired
    private IGroupService groupService;

    /**
     * 功能描述: 修改团信息
     * 〈〉
     * @Param: [groupId, groupName, belongProjectId, chargeUserId, groupNote]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: lhf
     * @Date: 2019/5/28 8:59
     */
    @PutMapping
    public ResponseEntity<Void> updateGroup(@RequestParam int groupId, @RequestParam String groupName, @RequestParam int belongProjectId,
                                            @RequestParam int chargeUserId, @RequestParam String groupNote){
        this.groupService.updateGroup(groupId,groupName,belongProjectId,chargeUserId,groupNote);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 功能描述: 启动禁用团状态
     * 〈〉
     * @Param: [pid]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: lhf
     * @Date: 2019/5/28 14:37
     */
    @GetMapping("/validorinvalid/{pid}")
    public ResponseEntity<Void> pathvariable(@PathVariable("pid") Integer pid){
        groupService.pathvariable(pid);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupManageVo>> getAllGroupsByConditionAndPage(){
        return null;
    }

}
