package com.ttms.service.ProductManage.ServiceImpl;


import com.ttms.Entity.ProGroup;
import com.ttms.Entity.ProProject;
import com.ttms.Entity.SysDepartment;
import com.ttms.Entity.SysUser;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProGroupMapper;
import com.ttms.Mapper.ProProjectMapper;
import com.ttms.Mapper.SysDepartmentMapper;
import com.ttms.Mapper.SysUserMapper;
import com.ttms.service.ProductManage.IGroupService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService implements IGroupService {
    @Autowired
    private ProGroupMapper proGroupMapper;
    @Autowired
    private ProProjectMapper proProjectMapper;
    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 功能描述: 修改团信息
     * 〈〉
     * @Param: [groupId, groupName, belongProjectId, chargeUserId, groupNote]
     * @Return: void
     * @Author: lhf
     * @Date: 2019/5/28 8:56
     */
    public void updateGroup(int groupId ,String groupName, int belongProjectId,
                            int chargeUserId, String groupNote){
        ProGroup proGroup = new ProGroup();
        proGroup.setId(groupId);
        proGroup.setGroupname(groupName);
        //判断当前项目是不是存在
        //查询belongProjectId是否为空，为空抛异常
        ProProject projectInDb = this.proProjectMapper.selectByPrimaryKey(belongProjectId);
        if(projectInDb == null) {
            throw new TTMSException(ExceptionEnum.PROJECT_NOT_EXIST);
        }
        proGroup.setProjectname(projectInDb.getProjectname());
        proGroup.setProjectid(belongProjectId);
        //判断用户是不是属于产品部
        List<String> curDepartmentStaffIds = sysDepartmentMapper.
                getAllStaffIdsOfDepartment(projectInDb.getDepartmentid());
        if(!curDepartmentStaffIds.contains(String.valueOf(chargeUserId)))
            throw new TTMSException(ExceptionEnum.USER_NOT_BELONG_PRODUCT_DEP);
        proGroup.setChargeuserid(chargeUserId);
        proGroup.setGroupnote(groupNote);
        proGroup.setValid((byte)1);
        //获取当前用户
        SysUser curUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Date now = new Date();
        proGroup.setUpdateuserid(curUser.getId());
        proGroup.setUpdatetime(now);

        int i = this.proGroupMapper.updateByPrimaryKeySelective(proGroup);
        if (i != 1) {
            throw new TTMSException(ExceptionEnum.GROUP_UPDATE_FAILURE);
        }
    }

    /**
     * 功能描述: 启动禁用团状态
     * 〈〉
     * @Param: [pid]
     * @Return: void
     * @Author: lhf
     * @Date: 2019/5/28 14:38
     */
    public void ValidOrInvalidGroup(Integer pid) {
        ProGroup proGroup = proGroupMapper.selectByPrimaryKey(pid);
        proGroup.setValid((byte) (proGroup.getValid() ^ 1));
        int i = this.proGroupMapper.updateByPrimaryKeySelective(proGroup);
        if (i != 1) {
            throw new TTMSException(ExceptionEnum.GROUP_VALID_MODIFY_ERROR);
        }
    }
    /*
    *功能描述：创建团
    *@author罗占
    *@Description
    *Date15:35 2019/5/28
    *Param[groupName, belongProjectI, chargeUserId, groupNote]
    *returnvoid
    **/
    @Override
    public void createGroup(String groupName, Integer belongProjectId, Integer chargeUserId, String groupNote) {
            ProGroup proGroup = new ProGroup();

        proGroup.setGroupname(groupName);
        //判断当前项目是不是存在
        //查询belongProjectId是否为空，为空抛异常
        ProProject projectInDb = this.proProjectMapper.selectByPrimaryKey(belongProjectId);
        if(projectInDb == null) {
            throw new TTMSException(ExceptionEnum.PROJECT_NOT_EXIST);
        }
        proGroup.setProjectid(belongProjectId);
        proGroup.setProjectname(projectInDb.getProjectname());
        //判断用户是不是属于产品部
        List<String> curDepartmentStaffIds = sysDepartmentMapper.
                getAllStaffIdsOfDepartment(projectInDb.getDepartmentid());
        if(!curDepartmentStaffIds.contains(String.valueOf(chargeUserId)))
            throw new TTMSException(ExceptionEnum.USER_NOT_BELONG_PRODUCT_DEP);
        proGroup.setChargeuserid(chargeUserId);
        proGroup.setGroupnote(groupNote);
        proGroup.setValid((byte)1);
        //获取当前用户
        SysUser curUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Date now = new Date();
        proGroup.setCreateuserid(curUser.getId());
        proGroup.setUpdateuserid(curUser.getId());
        proGroup.setCreatetime(now);
        proGroup.setUpdatetime(now);

        int i = this.proGroupMapper.insert(proGroup);
        if (i != 1) {
            throw new TTMSException(ExceptionEnum.GROUP_ADD_FAILURE);
        }
    }


    /*
    *功能描述：根据项目id查找其部门下所有职员
    *@author罗占
    *@Description
    *Date9:01 2019/5/29
    *Param[projectId]
    *returnjava.util.List<com.ttms.Entity.SysUser>
    **/
    public List<SysUser> getAllStaffInDep(Integer projectId){
        //判断id是否存在
        if(StringUtils.isEmpty(String.valueOf(projectId))){
            throw new TTMSException(ExceptionEnum.PROJECTID_NOT_FOUND);
        }

        ProProject project = this.proProjectMapper.selectByPrimaryKey(projectId);
         if(project==null){
                 throw new TTMSException(ExceptionEnum.PROJECT_NOT_EXIST);

         }
        List<String> curDepartmentStaffIds = sysDepartmentMapper.
                getAllStaffIdsOfDepartment( project.getDepartmentid());
         if (CollectionUtils.isEmpty(curDepartmentStaffIds))
         {
             throw new TTMSException(ExceptionEnum.PROJECT_NOT_EXIST);

         }
        List<Long> list=new ArrayList<>();
        for (String staffId : curDepartmentStaffIds) {
            list.add(Long.parseLong(staffId));
        }

        List<SysUser> users = this.sysUserMapper.selectByIdList(list);
        return users;
        }
    }


