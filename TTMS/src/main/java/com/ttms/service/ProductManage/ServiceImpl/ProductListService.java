package com.ttms.service.ProductManage.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttms.Entity.ProProduct;
import com.ttms.Entity.ProProductCat;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProProductMapper;
import com.ttms.Vo.PageResult;
import com.ttms.Vo.ProductVo;
import com.ttms.service.ProductManage.IProductCatService;
import com.ttms.service.ProductManage.IProductListService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductListService implements IProductListService {
    @Autowired
    private ProProductMapper productMapper;

    @Autowired
    private IProductCatService productCatService;


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

    /**
     * 功能描述: <br>
     * 〈〉为产品添加分销商
     * @Param: [pid, distributorId, distributorNumber, startTime, endTime]
     * @Return: void
     * @Author: 吴彬
     * @Date: 17:20 17:20
     */
    @Override
    public void addProductDistribute(Integer pid, Integer distributorId, Integer distributorNumber, Date startTime, Date endTime) {

    }

    /**
     * 功能描述: <br>
     * 〈〉查询产品的负责人id
     * @Param: []
     * @Return: java.lang.Integer
     * @Author: 吴彬
     * @Date: 17:24 17:24
     */
    @Override
    public Integer selectProductCreateUser(Integer productId) {
        ProProduct proProduct = this.productMapper.selectByPrimaryKey(productId);
        return proProduct.getCreateuserid();
    }
    @Override
    public PageResult<ProProduct> queryProjectByPage(int status, int productCatId1,
           int productCatId2, int productCatId3, String projectName, String productNumber,
            String productName, Date serverStartTime, Date serverEndTime, int page, int size) {
        PageResult<ProProduct> result = new PageResult<>();
        //分页
        PageHelper.startPage(page,size);
        //封装条件
        Example example = new Example(ProProduct.class);
        Example.Criteria criteria = example.createCriteria();
        if(status != -1){
            criteria.andEqualTo("productstatus",status);
        }
        if(productCatId1 != -1){
            criteria.andEqualTo("productcatid1",productCatId1);
        }
        if(productCatId2 != -1){
            criteria.andEqualTo("productcatid2",productCatId2);
        }
        if(productCatId3 != -1){
            criteria.andEqualTo("productcatid3",productCatId3);
        }
        if(!StringUtil.isEmpty(projectName)){
            criteria.andLike("projectname","%"+projectName+"%");
        }
        if(!StringUtil.isEmpty(productNumber)){
            criteria.andLike("productnumber","%"+productNumber+"%");
        }
        if(!StringUtil.isEmpty(productName)){
            criteria.andLike("productname","%"+productName+"%");
        }
        if(serverStartTime != null){
            criteria.andGreaterThan("serverstarttime",serverStartTime);
        }
        if(serverEndTime != null){
            criteria.andLessThan("serverendtime",serverEndTime);
        }
        //查询
        List<ProProduct> proProducts = productMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(proProducts)){
            throw new TTMSException(ExceptionEnum.PROJECT_NOT_EXIST);
        }
        PageInfo<ProProduct> pageinfo = new PageInfo<>(proProducts);
        //封装数据
        result.setTotalPage(pageinfo.getPages());
        result.setTotal(pageinfo.getTotal());
        for (ProProduct proProduct:proProducts){
            List<Integer> ids = Arrays.asList(proProduct.getProductcatid1(),
                    proProduct.getProductcatid2(), proProduct.getProductcatid3());
            proProduct.setProductcatnames(StringUtils.join(productCatService.
                    getProductCatByIds(ids).stream().map(ProProductCat::getProductcatname)
                    .collect(Collectors.toList()),"-"));
        }
        result.setItems(proProducts);
        return result;
    }


    public ProProduct getProductById(int pid){
        ProProduct proProduct = productMapper.selectByPrimaryKey(pid);
        if (proProduct == null) {
            throw new TTMSException(ExceptionEnum.PRODUCT_NOT_FOUND);
        }
        return proProduct;
    }
}
