package com.ttms.service.ProductManage.ServiceImpl;

import com.ttms.Entity.ProGroup;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProGroupMapper;
import com.ttms.service.ProductManage.ICreateProjectService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CreateProjectService implements ICreateProjectService {
    @Autowired
    private ProGroupMapper proGroupMapper;
    /*
    *功能描述：查询出所有团号
    *@author罗占
    *@Description
    *Date14:29 2019/5/30
    *Param[]
    *returnjava.util.List<com.ttms.Entity.ProGroup>
    **/
    @Override
    public List<ProGroup> queryAllGroups() {
        List<ProGroup> groups = this.proGroupMapper.selectAll();
        if (CollectionUtils.isEmpty(groups)){
            throw new TTMSException(ExceptionEnum.GROUP_NOT_FOUND);
        }
        return groups;
    }
}
