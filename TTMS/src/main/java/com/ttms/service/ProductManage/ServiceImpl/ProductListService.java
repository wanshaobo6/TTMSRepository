package com.ttms.service.ProductManage.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttms.Entity.ProProduct;
import com.ttms.Entity.ProProductCat;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProProductMapper;
import com.ttms.Vo.PageResult;
import com.ttms.service.ProductManage.IProductCatService;
import com.ttms.service.ProductManage.IProductListService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private ProProductMapper proProductMapper;

    @Autowired
    private IProductCatService productCatService;

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
        List<ProProduct> proProducts = proProductMapper.selectByExample(example);
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
}
