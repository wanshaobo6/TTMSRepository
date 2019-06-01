package com.ttms.service.ProductManage;

import com.ttms.Entity.ProProduct;
import com.ttms.Vo.PageResult;
import com.ttms.Vo.ProductVo;

import java.util.Date;
import java.util.List;

public interface IProductListService {
    PageResult<ProProduct> queryProjectByPage(int status, int productCatId1, int productCatId2, int productCatId3, String projectName, String productNumber, String productName, Date serverStartTime, Date serverEndTime, int page, int size);
    void updateproductStatus(Integer productId, Integer pstatus);

    List<ProductVo> getDistributorsByPid(Integer pid);

    void addProductDistribute(Integer pid, Integer distributorId, Integer distributorNumber, Date startTime, Date endTime);

    Integer selectProductCreateUser(Integer productId);


    //查询商品剩余的数量
    Integer selectProductLowestNumber(Integer pid);

    //为产品添加分销商之后产品的数量更改  修改产品的数量
   // Void updataProductNumber(Integer productId);




}
