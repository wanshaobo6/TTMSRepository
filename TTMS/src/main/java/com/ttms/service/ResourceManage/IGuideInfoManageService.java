package com.ttms.service.ResourceManage;

import com.ttms.Entity.ResGuide;

import java.util.List;

public interface IGuideInfoManageService {
    List<ResGuide> getGuidesByProductId(Integer pid);
}
