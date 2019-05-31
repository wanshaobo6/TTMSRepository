package com.ttms.service.ProductManage;


import java.util.Date;

public interface ICreateProductService {
    //创建产品
    void createProduct(Integer groupId, Integer productCatId1, Integer productCatId2, Integer productCatId3, String productName,
                       Date serverStartTime, Date serverEndTime, Integer preSellNumber , Integer selledNumber, Integer lowestNumber,
                       Date onsellTime , Integer productPrice, Date upsellTime, String hotTip, String productIntroduction);
}
