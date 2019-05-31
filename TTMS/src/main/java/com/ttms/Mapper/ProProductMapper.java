package com.ttms.Mapper;

import com.ttms.Entity.ProProduct;
import com.ttms.Vo.ProductVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProProductMapper extends Mapper<ProProduct> {
    //查询所有分销此产品的分销商
    @Select("SELECT p.`productName`,p.`serverStartTime`,p.`serverEndTime` ,sd.* FROM pro_product p,pro_product_distributor d,sup_distributor sd WHERE d.`productId`=p.`id` AND sd.`id`=d.`id` AND d.`productId`=#{projectId}")
    List<ProductVo> getDistributorsByPid(@Param("projectId") Integer parjectId);
}
