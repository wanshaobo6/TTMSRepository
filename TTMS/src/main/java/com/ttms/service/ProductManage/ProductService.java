package com.ttms.service.ProductManage;

import com.ttms.Entity.ProGroup;
import com.ttms.Entity.ProProject;
import com.ttms.Entity.SysUser;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProGroupMapper;
import com.ttms.Mapper.ProProjectMapper;
import com.ttms.Mapper.SysDepartmentMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProGroupMapper proGroupMapper;
    @Autowired
    private ProProjectMapper proProjectMapper;
    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;
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
        proGroup.setCreateuserid(curUser.getId());
        proGroup.setUpdateuserid(curUser.getId());
        proGroup.setCreatetime(now);
        proGroup.setUpdatetime(now);

        int i = this.proGroupMapper.updateByPrimaryKeySelective(proGroup);
        if (i != 1) {
            throw new TTMSException(ExceptionEnum.GROUP_UPDATE_FAILURE);
        }
    }

}
