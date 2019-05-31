package com.ttms.Controller.ProduceManage;

import com.ttms.service.ProductManage.ICreateProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


//----------产品管理->产品->创建产品------------
@RestController
@RequestMapping("/producemanage/product/createproduct")
public class CreateProductController {
    @Autowired
    private ICreateProductService createProductService;

    /**
     * 功能描述: 创建产品
     * 〈〉
     * @Param: []
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: lhf
     * @Date: 2019/5/31 10:40
     */
    @PostMapping("/createProduct")
    public ResponseEntity<Void> createProduct(@RequestParam(name = "groupId") Integer groupId,
                                              @RequestParam Integer productCatId1,
                                              @RequestParam Integer productCatId2,
                                              @RequestParam(value = "productCatId3") Integer productCatId3,
                                              @RequestParam(value = "productName") String productName,
                                              @RequestParam(value = "serverStartTime") Date serverStartTime,
                                              @RequestParam(value = "serverEndTime") Date serverEndTime,
                                              @RequestParam(value = "preSellNumber") Integer preSellNumber,
                                              @RequestParam(value = "selledNumber") Integer selledNumber,
                                              @RequestParam(value = "lowestNumber") Integer lowestNumber,
                                              @RequestParam(value = "onsellTime") Date onsellTime,
                                              @RequestParam(value = "projectPrice") Integer productPrice,
                                              @RequestParam(value = "upsellTime") Date upsellTime,
                                              @RequestParam(value = "hotTip") String hotTip,
                                              @RequestParam(value = "productIntroduction") String productIntroduction){
        createProductService.createProduct(groupId,productCatId1,productCatId2,
                                            productCatId3,productName,serverStartTime,
                                            serverEndTime,preSellNumber,selledNumber,lowestNumber,
                                            onsellTime,productPrice,upsellTime, hotTip,productIntroduction);
        return ResponseEntity.ok().body(null);
    }

}
