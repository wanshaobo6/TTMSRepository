package com.ttms.service.DistributorEntry.ServiceImpl;

import com.ttms.Entity.DisTourist;
import com.ttms.Mapper.DisToruistMapper;
import com.ttms.service.DistributorEntry.IDistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ContributorServiceImpl implements IDistributorService {
    @Autowired
    private DisToruistMapper disToruistMapper;

    @Override
    public Void login(String distributorname, String password) {
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
