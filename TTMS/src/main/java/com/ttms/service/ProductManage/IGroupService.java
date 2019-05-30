package com.ttms.service.ProductManage;


import com.ttms.Entity.ProProject;
import com.ttms.Entity.SysUser;
import com.ttms.Vo.GroupManageVo;
import com.ttms.Vo.PageResult;

import java.util.List;

public interface IGroupService {
        ///修改团信息
        void updateGroup(int groupId ,String groupName, int belongProjectId,
              int chargeUserId, String groupNote);

        //启动禁用团状态
        void ValidOrInvalidGroup(Integer pid);

        //查询所有项目名
        List<ProProject> getprojectinfo();

        //创建团
    void createGroup(String groupName,Integer belongProjectId, Integer chargeUserId, String groupNote);
    //根据项目id查找其部门下所有职员
    List<SysUser> getAllStaffInDep(Integer projectId);

        PageResult<GroupManageVo> getAllGroupsByConditionAndPage(String groupName, String projectName, int valid, int page, int rows);
}
