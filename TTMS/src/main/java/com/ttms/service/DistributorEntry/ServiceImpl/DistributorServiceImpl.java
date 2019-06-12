package com.ttms.service.DistributorEntry.ServiceImpl;

import com.ttms.Entity.DisTourist;
import com.ttms.Entity.ProPricepolicy;
import com.ttms.Entity.ProProduct;
import com.ttms.Entity.SupDistributor;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.DisToruistMapper;
import com.ttms.Mapper.SupDistributorMapper;
import com.ttms.service.DistributorEntry.IDistributorService;
import com.ttms.service.ProductManage.IPricePolicyService;
import com.ttms.service.ProductManage.IProductListService;
import com.ttms.service.ProductManage.ServiceImpl.PricePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DistributorServiceImpl implements IDistributorService {
    @Autowired
    private SupDistributorMapper distributorMapper;
    @Autowired
    private DisToruistMapper disToruistMapper;
    @Autowired
    private IPricePolicyService pricePolicyService;
    @Autowired
    private IProductListService productService;


    @Override
    public Void login(String distributorname, String password, HttpServletRequest request) {
        Example example = new Example(SupDistributor.class);
        example.createCriteria().andEqualTo("loginname",distributorname).andEqualTo("loginpass",password);
        List<SupDistributor> supDistributors = distributorMapper.selectByExample(example);
        //登录失败
        if(supDistributors == null || supDistributors.size() !=1)
            throw new TTMSException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        //登录成功
        //将用户信息存session
        System.out.println("supDistributors.get(0) = " + supDistributors.get(0));
        request.getSession().setAttribute("curdistributor",supDistributors.get(0));
        return null;
    }


    /**
     * 功能描述: <br>
     * 〈〉查询该分销商下该产品的报名游客的人
     * @Param: [productId]
     * @Return: org.springframework.http.ResponseEntity<java.util.List<com.ttms.Entity.DisTourist>>
     * @Author: 吴彬
     * @Date: 9:35 9:35
     */
    @Override
    public List<DisTourist> getMySignUpTourist(Integer id, Integer productId) {
        Example example=new Example(DisTourist.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("distributorid", id).andEqualTo("productid", productId);
        List<DisTourist> disTourists = this.disToruistMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(disTourists)){
            throw new TTMSException(ExceptionEnum.TOURIST_RECORD_NOTFOUND);
        }
        return disTourists;
    }

    @Override
    public List<ProPricepolicy> getPricePolicyByProductId(Integer productId) {
  //SELECT * FROM pro_pricepolicy pp JOIN pro_product_pricepolicy ppp ON ppp.`pricePolicyId` = pp.`id` WHERE ppp.`productId` = 1
        return pricePolicyService.getPricePolicyByProductId(productId);
    }

    @Override
    public Void signup(Integer pricePolicyId , String name, Byte sex, String idcard, String phone, String desc, Integer productId, Integer did) {
        DisTourist disTourist = new DisTourist();
        disTourist.setTName(name);
        disTourist.setSignuptime(new Date());
        disTourist.setDistributorid(did);
        disTourist.setTIdcard(idcard);
        disTourist.setTNote(desc);
        disTourist.setTPhone(phone);
        disTourist.setTSex(sex);
        disTourist.setProductid(productId);
        //查看当前的产品是否有该价格政策
        Map<Integer, ProPricepolicy> ProPricepolicieyMap = getPricePolicyByProductId(productId).stream().collect(Collectors.toMap(ProPricepolicy::getId, item -> item));
        Set<Integer> ids = ProPricepolicieyMap.keySet();
        if(pricePolicyId != null && ids.contains(pricePolicyId)){
            disTourist.setPricepolicyid(pricePolicyId);
            disTourist.setAcutalpay(ProPricepolicieyMap.get(pricePolicyId).getPriceafterdiscount());
        }else{
            ProProduct product = productService.getProductById(productId);
            disTourist.setAcutalpay(product.getProductprice());
        }
        //查看当前价格政策是否属于当前产品
        disToruistMapper.insert(disTourist);
        return null;
    }

}
