package com.ttms.service.ProductManage.ServiceImpl;

import com.ttms.Entity.ProProductCat;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProProductCatMapper;
import com.ttms.service.ProductManage.IProductCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ProductCatService implements IProductCatService {
    @Autowired
    private ProProductCatMapper proProductCatMapper;
    @Override
    public ProProductCat getProductCatById(Integer parentid) {
        if(proProductCatMapper.selectByPrimaryKey(parentid) == null){
            throw new TTMSException(ExceptionEnum.PRODUCTCATID_NOT_FOUND);
        }
        return null;
    }

    public List<ProProductCat> getProductCatByIds(List<Integer> ids){
        List<ProProductCat> proProductCats = proProductCatMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(proProductCats)) {
            throw new TTMSException(ExceptionEnum.PRODUCTCATID_NOT_FOUND);
        }
        return proProductCats;
    }
}
