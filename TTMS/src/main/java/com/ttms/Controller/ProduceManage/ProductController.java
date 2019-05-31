package com.ttms.Controller.ProduceManage;

import com.ttms.Entity.SysUser;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Vo.ProductVo;
import com.ttms.service.ProductManage.IProductService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//----------产品管理->产品->产品列表------------
@RestController
@RequestMapping("/producemanage/project/productlist")
public class ProductController {

    @Autowired
    private IProductService productService;

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
        productService.updateproductStatus(productId,pstatus);
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
        return ResponseEntity.ok(this.productService.getDistributorsByPid(pid));
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
    public ResponseEntity<Void> addProductDistribute(Integer pid ,Integer distributorId ,
                                                     Integer distributorNumber, Date startTime, Date endTime){
        //判断是否有权利操作
        Boolean opt = isPermissionOpt(pid);
        if(!opt){
            throw new TTMSException(ExceptionEnum.PWERMISSION_OPTERATION);
        }
        //TODO 没有完成需要查询产品的数量是否够分销  需要加一次判断
        this.productService.addProductDistribute(pid,distributorId,distributorNumber,startTime,endTime);

        return   ResponseEntity.ok().build();
    }

    private  Boolean isPermissionOpt(Integer productId){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        //查询产品的创建者是不是和当前用户一致
        Integer userId = productService.selectProductCreateUser(productId);
        // 判断当前用户是否为该产品的负责人
        if(!user.getId().equals(userId)){
            return false;
        }
        return true;
    }
}
