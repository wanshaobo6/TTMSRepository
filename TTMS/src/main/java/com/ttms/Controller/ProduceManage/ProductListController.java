package com.ttms.Controller.ProduceManage;

import com.ttms.Entity.ProProduct;
import com.ttms.Entity.SysUser;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Vo.PageResult;
import com.ttms.Vo.ProductVo;
import com.ttms.service.ProductManage.IProductListService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//----------产品管理->产品->产品列表------------
@RestController
@RequestMapping("/producemanage/product/productlist")
public class ProductListController {
    @Autowired
    private IProductListService productListService;

    /**
     * 功能描述: <br>
     * 〈〉更新产品的状态【上架，下架，代售】
     * @Param: [productId, pstatus]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: 吴彬
     * @Date: 17:18 17:18
     */
    @PutMapping("/privilege/updateproductStatus")
    public ResponseEntity<Void> updateproductStatus(Integer productId , Integer pstatus){

        Boolean opt = isPermissionOpt(productId);
        if(!opt){
            throw new TTMSException(ExceptionEnum.PWERMISSION_OPTERATION);
        }
        productListService.updateproductStatus(productId,pstatus);
        return  ResponseEntity.ok().build();
    }

    /**
     * 功能描述: <br>
     * 〈〉查询该产品所有的分销商
     * @Param: [pid]
     * @Return: org.springframework.http.ResponseEntity<java.util.List<com.ttms.Vo.ProductVo>>
     * @Author: 吴彬
     * @Date: 17:17 17:17
     */
    @GetMapping("/distributor/{pid}")
    public ResponseEntity<List<ProductVo>> getDistributorsByPid(@PathVariable("pid") Integer pid){
        return ResponseEntity.ok(this.productListService.getDistributorsByPid(pid));
    }

    /**
     * 功能描述: <br>
     * 〈〉为产品添加分销商
     * @Param: [pid, distributorId, distributorNumber, startTime, endTime]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: 吴彬
     * @Date: 17:29 17:29
     */
    @PostMapping("/privilege/distributor/{pid}")
    public ResponseEntity<Void> addProductDistribute(@PathVariable("pid") Integer pid ,Integer distributorId ,
                                                     @RequestParam Integer distributorNumber, @RequestParam Date startTime,@RequestParam Date endTime){
        //判断是否有权利操作
        Boolean opt = isPermissionOpt(pid);
        if(!opt){
            throw new TTMSException(ExceptionEnum.PWERMISSION_OPTERATION);
        }
        //  需要查询产品的数量是否够分销  需要加一次判断
        //查询商品剩余数量
        Integer b = this.productListService.selectProductLowestNumber(pid);
       // int min = Math.min(distributorNumber, b);
        if(b<distributorNumber ? true:false){
            //分销数量大于剩余数量
            throw new TTMSException(ExceptionEnum.NUMBER_NOT_ENOUGH);
        }
        this.productListService.addProductDistribute(pid,distributorId,distributorNumber,startTime,endTime);

        return   ResponseEntity.ok().build();
    }


    private  Boolean isPermissionOpt(Integer productId){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        //查询产品的创建者是不是和当前用户一致
        Integer userId = productListService.selectProductCreateUser(productId);
        // 判断当前用户是否为该产品的负责人
        if(!user.getId().equals(userId)){
            return false;
        }
        return true;
    }

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
