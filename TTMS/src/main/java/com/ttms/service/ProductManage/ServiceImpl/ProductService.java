package com.ttms.service.ProductManage.ServiceImpl;

import com.ttms.Entity.ProProduct;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProProductMapper;
import com.ttms.Vo.ProductVo;
import com.ttms.service.ProductManage.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProProductMapper productMapper;

    /**
    * 功能描述: <br>
    * 〈〉修改产品的状态
    * @Param: [productId, pstatus]
    * @Return: void
    * @Author: 吴彬
    * @Date: 15:32 15:32
     */
    @Override
    @Transactional
    public void updateproductStatus(Integer productId, Integer pstatus) {
        ProProduct proProduct = new ProProduct();
        proProduct.setId(productId);
        proProduct.setProductstatus(pstatus);
        int i = this.productMapper.updateByPrimaryKeySelective(proProduct);
        if(i!=1){
            throw new TTMSException(ExceptionEnum.PRODUCT_UPDATE_FAIL);
        }
        return;
    }

    /**
    * 功能描述: <br>
    * 〈〉查询该商品有多少个分销商
    * @Param: [pid]
    * @Return: java.util.List<com.ttms.Vo.ProductVo>
    * @Author: 吴彬
    * @Date: 17:01 17:01
     */
    @Override
    public List<ProductVo> getDistributorsByPid(Integer pid) {
        List<ProductVo> productVoList = this.productMapper.getDistributorsByPid(pid);
        return productVoList;
    }
}
