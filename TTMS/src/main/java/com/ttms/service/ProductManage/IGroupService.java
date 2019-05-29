package com.ttms.service.ProductManage;


import com.ttms.Vo.GroupManageVo;
import com.ttms.Vo.PageResult;

import java.util.List;

public interface IGroupService {
        void updateGroup(int groupId ,String groupName, int belongProjectId,
              int chargeUserId, String groupNote);

        void ValidOrInvalidGroup(Integer pid);



        PageResult<GroupManageVo> getAllGroupsByConditionAndPage(String groupName, String projectName, int valid, int page, int rows);
}
