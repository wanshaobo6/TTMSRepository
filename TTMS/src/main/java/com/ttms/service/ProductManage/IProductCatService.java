package com.ttms.service.ProductManage;

import com.ttms.Entity.ProProductCat;

import java.util.List;

public interface IProductCatService {
    ProProductCat getProductCatById(Integer parentid);

    List<ProProductCat> getProductCatByIds(List<Integer> ids);
}
