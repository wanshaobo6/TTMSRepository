package com.ttms.service.ProductManage;


import com.ttms.Entity.SysUser;

import java.util.List;

public interface IGroupService {
        ///修改团信息
        void updateGroup(int groupId ,String groupName, int belongProjectId,
              int chargeUserId, String groupNote);

        //启动禁用团状态
        void ValidOrInvalidGroup(Integer pid);


        //创建团
    void createGroup(String groupName,Integer belongProjectId, Integer chargeUserId, String groupNote);
    //根据项目id查找其部门下所有职员
    List<SysUser> getAllStaffInDep(Integer projectId);


}
