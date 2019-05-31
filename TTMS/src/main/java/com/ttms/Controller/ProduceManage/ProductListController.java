package com.ttms.Controller.ProduceManage;

import com.ttms.Entity.ProProduct;
import com.ttms.Vo.PageResult;
import com.ttms.service.ProductManage.IProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

//----------产品管理->产品->产品列表------------
@RestController
@RequestMapping("/producemanage/product/productlist")
public class ProductListController {
    @Autowired
    private IProductListService productListService;


    /**
     * 功能描述: <br>
     * 〈〉分页查询项目
     * @Param: [status, productCatId1, productCatId2, productCatId3, projectName, productNumber, productName, serverStartTime, serverEndTime, page, size]
     * @Return: org.springframework.http.ResponseEntity<com.ttms.Vo.PageResult>
     * @Author: 万少波
     * @Date: 2019/5/31 14:15
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<ProProduct>>  queryProjectByPage(
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

        return ResponseEntity.ok(productListService.queryProjectByPage(status,productCatId1,
                productCatId2,productCatId3,projectName,productNumber,productName,serverStartTime,
                serverEndTime,page,size));
    }
}
