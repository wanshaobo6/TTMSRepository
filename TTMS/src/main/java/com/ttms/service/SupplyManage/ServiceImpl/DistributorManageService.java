package com.ttms.service.SupplyManage.ServiceImpl;

import com.ttms.Entity.SupDistributor;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.SupDistributorMapper;
import com.ttms.service.SupplyManage.IDistributorManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class DistributorManageService implements IDistributorManageService {
    @Autowired
    private SupDistributorMapper supDistributorMapper;

    public List<SupDistributor> getAllDistributor(){
        List<SupDistributor> supDistributors = supDistributorMapper.selectAll();
        if (CollectionUtils.isEmpty(supDistributors)) {
            throw new TTMSException(ExceptionEnum.SUPDISTRIBUTOR_NOT_FOUND);
        }
        return supDistributors;
    }
}
