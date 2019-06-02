package com.ttms.Controller.ProduceManage;

import com.ttms.Entity.SysUser;
import com.ttms.service.ProductManage.IProductCatService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//----------产品管理->产品->产品分类------------
@RestController
@RequestMapping("/producemanage/product/productcat")
public class ProductCatController {

    @Autowired
    private IProductCatService productCatService;


    /**
    * 功能描述: <br>
    * 〈〉添加分类
    * @Param: []
    * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
    * @Author: 吴彬
    * @Date: 20:42 20:42
     */
    @PostMapping("/add")
    public ResponseEntity<Void> addProProductCat(@RequestParam(defaultValue = "0") Integer parentId,@RequestParam String name){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
         this.productCatService.addProductCat(parentId, name, user);
         return ResponseEntity.ok().build();
    }
    /**
    * 功能描述: <br>
    * 〈〉删除分类
    * @Param: [productId]
    * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
    * @Author: 吴彬
    * @Date: 21:33 21:33
     */
    @DeleteMapping("{productId}")
    public ResponseEntity<Void> deleteProProductCat(Integer productId){
        this.productCatService.deleteProProductCat(productId);
        return  ResponseEntity.ok().build();
    }
    
    /**
    * 功能描述: <br>
    * 〈〉修改分类
    * @Param: [productId]
    * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
    * @Author: 吴彬
    * @Date: 21:55 21:55
     */
    @PutMapping("{productId}")
    public ResponseEntity<Void> updateProProductCat(@RequestParam Integer productId,@RequestParam String name){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        this.productCatService.updateProProductCat(productId,name,user);
        return  ResponseEntity.ok().build();
    }




}
