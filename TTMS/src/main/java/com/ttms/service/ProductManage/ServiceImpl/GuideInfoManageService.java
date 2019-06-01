package com.ttms.service.ProductManage.ServiceImpl;

import com.ttms.Entity.ResGuide;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ResGuideMapper;
import com.ttms.service.ResourceManage.IGuideInfoManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class GuideInfoManageService implements IGuideInfoManageService {
    @Autowired
    private ResGuideMapper resGuideMapper;


    //根据productId查询于其关联的guide
    @Override
    public List<ResGuide> getGuidesByProductId(Integer pid) {
        List<ResGuide> resGuides = resGuideMapper.getGuidesByProductId(pid);
        if (CollectionUtils.isEmpty(resGuides)) {
            throw new TTMSException(ExceptionEnum.RESGUIDE_NOT_FOUND);
        }
        return resGuides;
    }
}
