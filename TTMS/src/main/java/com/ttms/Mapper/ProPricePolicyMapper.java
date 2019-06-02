package com.ttms.Mapper;

import com.ttms.Entity.ProPricepolicy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProPricePolicyMapper extends Mapper<ProPricepolicy> {

    @Select("SELECT pp.*,ppp.`priceAfterDiscount`  FROM pro_product_pricepolicy ppp " +
            "JOIN pro_pricepolicy pp ON ppp.`pricePolicyId` = pp.`id` WHERE ppp.`productId` = #{pid}")
    List<ProPricepolicy> getProPricepoliciesByProductId(@Param(value = "pid") Integer pid);
}
