package com.ttms.service.SystemManage;

public interface IGroupService {
    void getAllGroups();

    //创建团
    void createGroup(String groupName,int belongProjectId,
                     int chargeUserId , String groupNote);
}
