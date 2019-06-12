package com.ttms.Controller.Distributor;

import com.ttms.Entity.ProProduct;
import com.ttms.Vo.PageResult;
import com.ttms.service.ProductManage.IProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/distributorEntry")
public class DistributorController {

    @Autowired
    private IProductListService productListService;
    /**
    * 功能描述: <br>
    * 〈〉查询所有可用的产品
    * @Param: []
    * @Return: org.springframework.http.ResponseEntity<java.util.List<com.ttms.Entity.ProProduct>>
    * @Author: 吴彬
    * @Date: 8:38 8:38
     */
    @GetMapping("/getAvailableProducts")
    public ResponseEntity<PageResult<ProProduct>> getAvailableProducts(
            @RequestParam(required = false,defaultValue = "-1") int status,
            @RequestParam(required = false,defaultValue = "-1") int productCatId1,
            @RequestParam(required = false,defaultValue = "-1") int productCatId2,
            @RequestParam(required = false,defaultValue = "-1") int productCatId3,
            @RequestParam(required = false) String projectName,
            @RequestParam(required = false) String productNumber,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Date serverStartTime,
            @RequestParam(required = false) Date serverEndTime ,
            @RequestParam(required = false ,defaultValue = "1") int page ,
            @RequestParam(required = false , defaultValue = "5") int size
    ){
        return ResponseEntity.ok(this.productListService.queryProjectByPage(status,productCatId1,
                productCatId2,productCatId3,projectName,productNumber,productName,serverStartTime,
                serverEndTime,page,size));
    }

   /* @GetMapping("/showMySignUpTourist")
    public ResponseEntity<List<>>*/
}
