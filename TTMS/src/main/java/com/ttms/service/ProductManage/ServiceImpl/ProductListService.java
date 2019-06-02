package com.ttms.service.ProductManage.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttms.Entity.*;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.*;
import com.ttms.Vo.PageResult;
import com.ttms.Vo.ProductVo;
import com.ttms.service.ProductManage.IPricePolicyService;
import com.ttms.service.ProductManage.IProductCatService;
import com.ttms.service.ProductManage.IProductListService;
import com.ttms.service.ResourceManage.IAttachmentService;
import com.ttms.service.ResourceManage.IGuideInfoManageService;
import com.ttms.service.SupplyManage.IDistributorManageService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductListService implements IProductListService {
    @Autowired
    private ProProductMapper productMapper;

    @Autowired
    private ProProductDistributorMapper proProductDistributorMapper;

    @Autowired
    private ProProductGuideMapper productGuideMapper;

    @Autowired
    private ProProductPricepolicyMapper productPricepolicyMapper;

    @Autowired
    private IProductCatService productCatService;

    @Autowired
    private IDistributorManageService distributorManageService;

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private IGuideInfoManageService guideInfoManageService;

    @Autowired
    private IPricePolicyService pricePolicyService;

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
    @Transactional
    public void addProductDistribute(Integer pid, Integer distributorId, Integer distributorNumber, Date startTime, Date endTime) {
        ProProductDistributor proProductDistributor=new ProProductDistributor();
        proProductDistributor.setProductid(pid);
        proProductDistributor.setDistributorid(distributorId);
        proProductDistributor.setDistributenum(distributorNumber);
        proProductDistributor.setStarttime(startTime);
        proProductDistributor.setEndtime(endTime);
        proProductDistributor.setCreatetime(new Date());
        proProductDistributor.setUpdatetime(null);
        int i = this.proProductDistributorMapper.insert(proProductDistributor);
        if(i!=1){
            throw new TTMSException(ExceptionEnum.INSERT_DISTRIBUTOR_FAIL);
        }
        //为产品添加分销商之后产品的数量更改  修改产品的数量 售出的数量 和剩余的数量
        ProProduct product=new ProProduct();
        //查询产品的售出数量
        ProProduct productById = getProductById(pid);
        product.setId(pid);
        product.setSellednumber(productById.getSellednumber()+distributorNumber);
        product.setLowestnumber(productById.getLowestnumber()-distributorNumber);
        int update = this.productMapper.updateByPrimaryKeySelective(product);
        if(update!=1){
            throw new TTMSException(ExceptionEnum.PRODUCT_UPDATE_NUM_FAIL);
        }
        return;
    }



    @Override
    @Transactional
    public Void deleteProductDistribute(int pid, int productDistributorId) {
        //查询出该产品
        ProProduct product = getProductById(pid);
        //根据id查询产品的分销商
        ProProductDistributor proProductDistributor = getProProductDistributorByid(productDistributorId);
        //判断该产品和该分销商是否匹配
        if (product.getId() != proProductDistributor.getProductid()) {
            throw new TTMSException(ExceptionEnum.PRODUCTDISTRIBUTOR_NOT_MATCH);
        }
        //修改数量
        product.setSellednumber(product.getSellednumber()-proProductDistributor.getDistributenum());
        product.setLowestnumber(product.getLowestnumber()+proProductDistributor.getDistributenum());
        //更新产品数量
        productMapper.updateByPrimaryKey(product);
        //删除记录
        proProductDistributorMapper.deleteByPrimaryKey(productDistributorId);
        return null;
    }

    @Override
    public List<SupDistributor> getAllDistributorInfo() {
        return distributorManageService.getAllDistributor();
    }

    /**
    * 功能描述: <br>
    * 〈〉查询商品的剩余数量
    * @Param: [pid]
    * @Return: java.lang.Integer
    * @Author: 吴彬
    * @Date: 13:55 13:55
     */
    @Override
    public Integer selectProductLowestNumber(Integer pid) {
        ProProduct proProduct = this.productMapper.selectByPrimaryKey(pid);
        return proProduct.getLowestnumber();
    }

    @Override
    public List<ResGuide> getGuidesByProductId(Integer pid) {
        return guideInfoManageService.getGuidesByProductId(pid);
    }

    @Override
    public Void deleteProductGuide(Integer productId, Integer guideId) {
        ProProductGuide proProductGuide = new ProProductGuide();
        proProductGuide.setProductid(productId);
        proProductGuide.setGuideid(productId);
        int delete = productGuideMapper.delete(proProductGuide);
        if(delete != 1){
            throw new TTMSException(ExceptionEnum.PRODUCT_GUIDE_DELETE_FAIL);
        }
        return null;
    }

    @Override
    public PageResult<ResGuide> queryGuidesNotInProduct(Integer productId ,
                 String guideName, String mobile, String language, String nationality, int page, int rows) {
        PageResult<ResGuide> pageResult = new PageResult<>();
        //查询出该产品下添加的所有导游Id
        ProProductGuide proProductGuide = new ProProductGuide();
        proProductGuide.setProductid(productId);
        List<ProProductGuide> proProductGuides = productGuideMapper.select(proProductGuide);
        //获取产品下所有的导游id
        List<Integer> productGuideIds = null;
        if(!CollectionUtils.isEmpty(proProductGuides)){
            productGuideIds = proProductGuides.stream().map(guide -> {
                return guide.getGuideid();
            }).collect(Collectors.toList());
        }
        //分页
        PageHelper.startPage(page,rows);
        //查询条件并查询
        List<ResGuide> resGuides = guideInfoManageService.
                queryGuidesByCriteria(productGuideIds,guideName,mobile,language,nationality);
        //封装结果
        PageInfo<ResGuide> pageInfo = new PageInfo<>(resGuides);
        pageResult.setItems(resGuides);
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @Override
    @Transactional
    public Void addProductGuide﻿(Integer productId, List<Integer> guideIds) {
        Example example = new Example(ProProductGuide.class);
        example.createCriteria().andIn("guideid",guideIds);
        List<ProProductGuide> proProductGuides = productGuideMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(proProductGuides)) {
            throw new TTMSException(ExceptionEnum.PRODUCT_GUIDE_ALREADY_EXIST);
        }
        //插入记录
        List<ProProductGuide> productGuides = new ArrayList<>();
        Date now = new Date();
        for (Integer gudieId:guideIds){
            ProProductGuide productGuide = new ProProductGuide();
            productGuide.setProductid(productId);
            productGuide.setGuideid(gudieId);
            productGuide.setCreatetime(now);
            productGuides.add(productGuide);
        }
        int count = productGuideMapper.insertList(productGuides);
        //特殊情况
        if(count != guideIds.size()){
            throw new TTMSException(ExceptionEnum.PRODUCT_GUIDE_INSERT_FAIL);
        }
        return null;
    }

    @Override
    public List<ProPricepolicy> getPricePolicyByProductId(Integer pid) {
        return pricePolicyService.getProPricepoliciesByProductId(pid);
    }

    @Override
    public Void deleteProductPricePolicy(int productId, int pricePolicyId) {
        ProProductPricepolicy productPricepolicy = new ProProductPricepolicy();
        productPricepolicy.setProductid(productId);
        productPricepolicy.setPricepolicyid(pricePolicyId);
        int count = productPricepolicyMapper.delete(productPricepolicy);
        if(count != 1){
            throw new TTMSException(ExceptionEnum.PRODUCT_PRICE_POLICY_DELETE_FAIL);
        }
        return null;
    }

    @Override
    public PageResult<ProPricepolicy> getPolicyNotinProductByPage(Integer productId,
                                          String pricePolicyName, Date startTime, Date endTime , int page  , int rows) {
        PageResult<ProPricepolicy> pageResult = new PageResult<>();
        //查询出该产品下添加的所有价格政策Id
        ProProductPricepolicy productPricepolicy = new ProProductPricepolicy();
        productPricepolicy.setProductid(productId);
        List<ProProductPricepolicy> productPricepolicies = productPricepolicyMapper.select(productPricepolicy);
        //获取产品下所有的导游id
        List<Integer> pricePolicyIds = null;
        if(!CollectionUtils.isEmpty(productPricepolicies)){
            pricePolicyIds = productPricepolicies.stream().map(pricepolicy -> {
                return pricepolicy.getPricepolicyid();
            }).collect(Collectors.toList());
        }
        //分页
        PageHelper.startPage(page,rows);
        //查询条件并查询
        List<ProPricepolicy> pricePolicies = pricePolicyService.
                queryPricePolicyByCriteria(pricePolicyIds , pricePolicyName,startTime ,endTime);
        //封装结果
        PageInfo<ResGuide> pageInfo = new PageInfo(pricePolicies);
        pageResult.setItems(pricePolicies);
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @Override
    public List<ResoAttachment> getAttachmentsByPid(int pid) {
        return attachmentService.getAttachmentsByPid(pid);
    }

    @Override
    public Void addAttachement(int pid, String fileName, String fileUrl, String attachmentname) {
        return attachmentService.addAttachment(pid,fileName,fileUrl,attachmentname, ((SysUser) SecurityUtils.getSubject().getPrincipal()).getId());
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


    /**
    * 功能描述: <br>
    * 〈〉根据id查询产品
    * @Param: [pid]
    * @Return: com.ttms.Entity.ProProduct
    * @Author: 吴彬
    * @Date: 14:06 14:06
     */
    public ProProduct getProductById(int pid){
        ProProduct proProduct = productMapper.selectByPrimaryKey(pid);
        if (proProduct == null) {
            throw new TTMSException(ExceptionEnum.PRODUCT_NOT_FOUND);
        }
        return proProduct;
    }

    @Override
    public boolean checkIsCharger(int productId) {
        //判断当前用户是否是主管人
        SysUser sysUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
        System.out.println("当前用户是"+sysUser.toString());
        //查询当前product
        ProProduct proProduct = getProductById(productId);
        if(sysUser.getId() != proProduct.getCreateuserid())
            return false;
        return true;
    }

    @Override
    public Integer selectProductCreateUser(Integer productId) {
        return null;
    }

    public ProProductDistributor getProProductDistributorByid(int id){
        ProProductDistributor proProductDistributor = proProductDistributorMapper.selectByPrimaryKey(id);
        if (proProductDistributor==null) {
            throw new TTMSException(ExceptionEnum.PRODUCT_DISTRIBUTOR_NOT_FOUND);
        }
        return proProductDistributor;
    }
}
