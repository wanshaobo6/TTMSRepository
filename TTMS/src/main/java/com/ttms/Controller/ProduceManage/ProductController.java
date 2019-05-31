package com.ttms.Controller.ProduceManage;

import com.ttms.Entity.SysUser;
import com.ttms.Vo.ProductVo;
import com.ttms.service.ProductManage.IProductService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//----------产品管理->产品->产品列表------------
@RestController
@RequestMapping("/producemanage/project/productlist")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PutMapping("/privilege/updateproductStatus")
    public ResponseEntity<Void> updateproductStatus(Integer productId , Integer pstatus){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
       //TODO 判断当前用户是否为该产品的负责人
        productService.updateproductStatus(productId,pstatus);
        return  ResponseEntity.ok().build();
    }

    @GetMapping("/distributor/{pid}")
    public ResponseEntity<List<ProductVo>> getDistributorsByPid(@PathVariable("pid") Integer pid){
        return ResponseEntity.ok(this.productService.getDistributorsByPid(pid));
    }
}
