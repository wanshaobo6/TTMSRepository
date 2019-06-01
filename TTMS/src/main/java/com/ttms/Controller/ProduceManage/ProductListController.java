package com.ttms.Controller.ProduceManage;

import com.ttms.Entity.ProProduct;
import com.ttms.Entity.ResoAttachment;
import com.ttms.Entity.SupDistributor;
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

import javax.xml.ws.Response;
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
    public ResponseEntity<Void> addProductDistribute(Integer pid ,Integer distributorId ,
                                                     Integer distributorNumber, Date startTime, Date endTime){
        //判断是否有权利操作
        Boolean opt = isPermissionOpt(pid);
        if(!opt){
            throw new TTMSException(ExceptionEnum.PWERMISSION_OPTERATION);
        }
        //TODO 没有完成需要查询产品的数量是否够分销  需要加一次判断
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

    /**
     * 功能描述: <br>
     * 〈〉删除为产品分销的分销商
     * @Param: []
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: 万少波
     * @Date: 2019/6/1 13:55
     */
    @DeleteMapping("/privilege/distributor/{pid}")
    public ResponseEntity<Void> deleteProductDistribute(@PathVariable int pid ,@RequestParam int productDistributorId){
        return ResponseEntity.ok(productListService.deleteProductDistribute(pid,productDistributorId));
    }

    /**
     * 功能描述: <br>
     * 〈〉获取所有的分销商
     * @Param: []
     * @Return: org.springframework.http.ResponseEntity<java.util.List<com.ttms.Entity.SupDistributor>>
     * @Author: 万少波
     * @Date: 2019/6/1 14:39
     */
    @GetMapping("/distributors")
    public ResponseEntity<List<SupDistributor>> getAllDistributorInfo(){
        return ResponseEntity.ok(productListService.getAllDistributorInfo());
    }

    /**
     * 功能描述: <br>
     * 〈〉查询当前产品下所有附件
     * @Param: [pid]
     * @Return: org.springframework.http.ResponseEntity<java.util.List<com.ttms.Entity.ResoAttachment>>
     * @Author: 万少波
     * @Date: 2019/6/1 14:49
     */
    @GetMapping("/allAttachment/{pid}")
    public ResponseEntity<List<ResoAttachment>> getAttachmentsByPid(@PathVariable int pid){
        return ResponseEntity.ok(productListService.getAttachmentsByPid(pid));
    }

    /**
     * 功能描述: <br>
     * 〈〉新增附件
     * @Param: [pid, fileName, attachmentname, attachmentUrl]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Void>
     * @Author: 万少波
     * @Date: 2019/6/1 14:58
     */
    @PostMapping("privilege/attachment/{pid}")
    public ResponseEntity<Void> addAttachement(@PathVariable int pid ,
                                               @RequestParam String fileName ,
                                               @RequestParam String fileUrl,
                                               @RequestParam String attachmentname){
        return ResponseEntity.ok(productListService.addAttachement(pid ,fileName ,fileUrl ,attachmentname));
    }
 }
