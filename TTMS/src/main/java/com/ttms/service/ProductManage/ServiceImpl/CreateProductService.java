package com.ttms.service.ProductManage.ServiceImpl;

import com.ttms.Entity.ProGroup;
import com.ttms.Entity.ProProduct;
import com.ttms.Entity.ProProductCat;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProProductMapper;
import com.ttms.service.ProductManage.ICreateProductService;
import com.ttms.service.ProductManage.IGroupService;
import com.ttms.service.ProductManage.IProductCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class CreateProductService implements ICreateProductService {
    @Autowired
    private IGroupService groupService;

    @Autowired
    private IProductCatService productCatService;
    @Autowired
    private ProProductMapper proProductMapper;


    @Override
    public void createProduct(Integer groupId, Integer productCatId1, Integer productCatId2,
                              Integer productCatId3, String productName, Date serverStartTime,
                              Date serverEndTime, Integer preSellNumber, Integer selledNumber,
                              Integer lowestNumber, Date onsellTime, Integer productPrice, Date upsellTime,
                              String hotTip, String productIntroduction) {
        ProProduct proProduct = new ProProduct();
        //查询团号是否存在
        ProGroup proGroup = groupService.getGroupById(groupId);
        //当前用户是否和团负责人一致
        if(proGroup.getChargeuserid().equals(groupId)){
            throw new TTMSException(ExceptionEnum.USER_NOT_GRUOPCHARGEUSER);
        }
        //团下面是否有产品，有，则不创建


        proProduct.setGroupid(groupId);

        //查询三级分类是否是父子关系 和是否存在
        ProProductCat proProductCat3 = productCatService.getProductCatById(productCatId3);
        if(!proProductCat3.getParentid().equals(productCatId2)) {
            throw new TTMSException(ExceptionEnum.NOT_FOUND_PARENTID);
        }
        ProProductCat proProductCat2 = productCatService.getProductCatById(productCatId2);
        if(!proProductCat2.getParentid().equals(productCatId1)) {
            throw new TTMSException(ExceptionEnum.NOT_FOUND_PARENTID);
        }
        proProduct.setProductcatid1(productCatId1);
        proProduct.setProductcatid2(productCatId2);
        proProduct.setProductcatid3(productCatId3);
        proProduct.setProductname(productName);
        proProduct.setServerstarttime(serverStartTime);
        proProduct.setServerendtime(serverEndTime);
        proProduct.setOnselltime(onsellTime);
        proProduct.setUpselltime(upsellTime);
        proProduct.setPresellnumber(preSellNumber);
        proProduct.setSellednumber(selledNumber);
        proProduct.setLowestnumber(lowestNumber);
        proProduct.setProductprice(productPrice);
        proProduct.setHottip(hotTip);
        proProduct.setProductintroduction(productIntroduction);
        int i = this.proProductMapper.insert(proProduct);
        if (i != 1) {
            throw new TTMSException(ExceptionEnum.PRODUCT_ADD_FAIL);
        }
    }
}
