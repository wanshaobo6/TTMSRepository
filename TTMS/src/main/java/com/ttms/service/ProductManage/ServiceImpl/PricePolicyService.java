package com.ttms.service.ProductManage.ServiceImpl;

import com.ttms.Entity.ProPricepolicy;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProPricePolicyMapper;
import com.ttms.service.ProductManage.IPricePolicyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class PricePolicyService implements IPricePolicyService {
    @Autowired
    private ProPricePolicyMapper pricePolicyMapper;

    @Override
    public List<ProPricepolicy> getProPricepoliciesByProductId(Integer pid) {
        List<ProPricepolicy> pricepolicies = pricePolicyMapper.getProPricepoliciesByProductId(pid);
        if (CollectionUtils.isEmpty(pricepolicies)) {
            throw new TTMSException(ExceptionEnum.PRODUCT_PRICE_POLICY_NOT_FOUND);
        }
        return pricepolicies;
    }

    @Override
    public List<ProPricepolicy> queryPricePolicyByCriteria(List<Integer> pricePolicyIds,
                                    String pricePolicyName, Date startTime, Date endTime) {
        Example example = new Example(ProPricepolicy.class);
        Example.Criteria criteria = example.createCriteria();
        //封装条件
        if(!CollectionUtils.isEmpty(pricePolicyIds)){
            criteria.andNotIn("id",pricePolicyIds);
        }
        if(!StringUtils.isEmpty(pricePolicyName)){
            criteria.andLike("policyname","%"+pricePolicyName+"%");
        }
        if(startTime != null){
            criteria.andGreaterThan("starttime",startTime);
        }
        if(endTime != null){
            criteria.andLessThan("endtime",endTime);
        }
        //查询并返回
        List<ProPricepolicy> pricepolicies = pricePolicyMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(pricepolicies)) {
            throw new TTMSException(ExceptionEnum.PRODUCT_PRICE_POLICY_NOT_FOUND);
        }
        return pricepolicies;
    }
}
