package com.ttms.service.ProductManage;

import com.ttms.Vo.ProductVo;

import java.util.Date;
import java.util.List;

public interface IProductService {

    void updateproductStatus(Integer productId, Integer pstatus);

    List<ProductVo> getDistributorsByPid(Integer pid);

    void addProductDistribute(Integer pid, Integer distributorId, Integer distributorNumber, Date startTime, Date endTime);

    Integer selectProductCreateUser(Integer productId);
}
