package com.ttms.service.DistributorEntry.ServiceImpl;

import com.ttms.Entity.SupDistributor;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.SupDistributorMapper;
import com.ttms.service.DistributorEntry.IDistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ContributorServiceImpl implements IDistributorService {
    @Autowired
    private SupDistributorMapper distributorMapper;

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
        return null;
    }
}
