package com.ttms.Controller.ProduceManage;

import com.ttms.service.ProductManage.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//----------产品管理->产品
@RestController
@RequestMapping("/producemanage")
public class ProductController {

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

}
