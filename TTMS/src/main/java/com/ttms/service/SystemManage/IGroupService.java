package com.ttms.service.SystemManage;


public interface IGroupService {
        void updateGroup(int groupId ,String groupName, int belongProjectId,
              int chargeUserId, String groupNote);

        void pathvariable(Integer pid);



}
