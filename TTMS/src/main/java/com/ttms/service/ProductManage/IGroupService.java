package com.ttms.service.ProductManage;


public interface IGroupService {
        void updateGroup(int groupId ,String groupName, int belongProjectId,
              int chargeUserId, String groupNote);

        void pathvariable(Integer pid);

        //创建团
    void createGroup(String groupName,Integer belongProjectId, Integer chargeUserId, String groupNote);



}
