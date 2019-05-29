package com.ttms.service.ProductManage.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttms.Entity.ProGroup;
import com.ttms.Entity.ProProject;
import com.ttms.Entity.SysUser;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProGroupMapper;
import com.ttms.Mapper.ProProjectMapper;
import com.ttms.Mapper.SysDepartmentMapper;
import com.ttms.Mapper.SysUserMapper;
import com.ttms.Vo.GroupManageVo;
import com.ttms.Vo.PageResult;
import com.ttms.service.ProductManage.IGroupService;
import com.ttms.service.SystemManage.SysMenusService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
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
    private SysMenusService sysMenusService;
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

    @Override
    public PageResult<GroupManageVo> getAllGroupsByConditionAndPage(String groupName, String projectName, int valid, int page, int rows) {
        PageResult<GroupManageVo> result = new PageResult<>();
        List<GroupManageVo> resultVO = new ArrayList<>();
        //分页
        PageHelper.startPage(page,rows);
        //封装条件
        Example example = new Example(ProGroup.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(groupName)){
            criteria.andLike("groupname","%"+groupName+"%");
        }
        if(!StringUtils.isEmpty(projectName)){
            criteria.andLike("projectname","%"+projectName+"%");
        }
        if(valid != -1){
            criteria.andEqualTo("valid",valid);
        }
        //返回结果
        List<ProGroup> proGroups = proGroupMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(proGroups))
            throw new TTMSException(ExceptionEnum.GROUP_NOT_FOUND);
        //获取所有相关的用户id
        Set<Integer> idsSet = proGroups.stream().map(ProGroup::getChargeuserid).collect(Collectors.toSet());
        ArrayList<Integer> idList = new ArrayList<>();
        List<SysUser> userList = sysMenusService.getUsersByIds(idList);
        //转化为map(id , sysUser)
        Map<Integer, SysUser> idUserMap = userList.stream().collect(Collectors.toMap(SysUser::getId, item -> item));
        //封装数据
        SysUser curUser = null;
        for (ProGroup proGroup:proGroups){
            GroupManageVo groupManageVo = new GroupManageVo();
            groupManageVo.setProGroup(proGroup);
             curUser = idUserMap.get(proGroup.getChargeuserid());
            groupManageVo.setChargerName(curUser.getUsername());
            groupManageVo.setChargerPhoneNumber(curUser.getMobile());
            resultVO.add(groupManageVo);
        }
        PageInfo pageInfo = new PageInfo(proGroups);
        long total = pageInfo.getTotal();
        long totalPage = ((total % rows) == 0)?(total/rows):(total/rows+1);
        result.setTotal(total);
        result.setTotalPage(totalPage);
        result.setItems(resultVO);
        return result;
    }

}
