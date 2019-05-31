package com.ttms.service.ProductManage;

import com.ttms.Entity.ProProduct;
import com.ttms.Vo.PageResult;

import java.util.Date;

public interface IProductListService {
    PageResult<ProProduct> queryProjectByPage(int status, int productCatId1, int productCatId2, int productCatId3, String projectName, String productNumber, String productName, Date serverStartTime, Date serverEndTime, int page, int size);
}
