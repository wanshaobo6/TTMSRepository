package com.ttms.service.DistributorEntry.ServiceImpl;

import com.ttms.Entity.DisTourist;
import com.ttms.Entity.SupDistributor;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.DisToruistMapper;
import com.ttms.Mapper.SupDistributorMapper;
import com.ttms.service.DistributorEntry.IDistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ContributorServiceImpl implements IDistributorService {
    @Autowired
    private SupDistributorMapper distributorMapper;
    @Autowired
    private DisToruistMapper disToruistMapper;

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


    /**
     * 功能描述: <br>
     * 〈〉查询该分销商下的所有游客
     * @Param: [id]
     * @Return: java.util.List<com.ttms.Entity.DisTourist>
     * @Author: 吴彬
     * @Date: 9:20 9:20
     */
    @Override
    public List<DisTourist> getMySignUpTourist(Integer id) {
        Example example=new Example(DisTourist.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("distributorid", id);
        List<DisTourist> disTourists = this.disToruistMapper.selectByExample(example);
        return disTourists;
    }
}
