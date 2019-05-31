package com.ttms.Controller.ProduceManage;

import com.ttms.Entity.ProProductCat;
import com.ttms.service.ProductManage.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//----------产品管理->产品->产品分类------------
@RestController
@RequestMapping("/producemanage/product/createproduct")
public class ProductCatController {

    @Autowired
    private IGroupService groupService;

    /**
    * 功能描述: <br>
    * 〈〉根据id查询所有
    * @Param: [catId]
    * @Return: java.util.List<com.ttms.Entity.ProProductCat>
    * @Author: 吴彬
    * @Date: 11:10 11:10
     */
    @GetMapping("queryCatById")
    public ResponseEntity<List<ProProductCat>> queryCatById(@PathVariable("catId") Integer catId){
        return ResponseEntity.ok(this.groupService.queryCatById(catId));
    }


}
