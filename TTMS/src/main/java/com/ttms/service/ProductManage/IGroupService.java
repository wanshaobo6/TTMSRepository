package com.ttms.service.ProductManage;


import com.ttms.Entity.SysUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IGroupService {
        void updateGroup(int groupId ,String groupName, int belongProjectId,
              int chargeUserId, String groupNote);

        void ValidOrInvalidGroup(Integer pid);




}
