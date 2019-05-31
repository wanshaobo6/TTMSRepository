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

    @Override
    public List<ProGroup> queryAllGroups() {
        List<ProGroup> groups = this.proGroupMapper.selectAll();
        for(ProGroup group:groups){
            group.setProjectid(null);
            group.setProjectname(null);
            group.setValid(null);
            group.setChargeuserid(null);
            group.setGroupnote(null);
            group.setUpdatetime(null);
            group.setCreatetime(null);
            group.setCreateuserid(null);
            group.setUpdateuserid(null);
        }
        return groups;
    }
}
