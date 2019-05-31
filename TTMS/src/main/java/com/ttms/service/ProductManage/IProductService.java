package com.ttms.service.ProductManage;

import com.ttms.Vo.ProductVo;

import java.util.List;

public interface IProductService {

    void updateproductStatus(Integer productId, Integer pstatus);

    List<ProductVo> getDistributorsByPid(Integer pid);
}
