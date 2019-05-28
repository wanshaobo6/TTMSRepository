package com.ttms.service.ProductManage;


public interface IGroupService {
        void updateGroup(int groupId ,String groupName, int belongProjectId,
              int chargeUserId, String groupNote);

        void pathvariable(Integer pid);



}
