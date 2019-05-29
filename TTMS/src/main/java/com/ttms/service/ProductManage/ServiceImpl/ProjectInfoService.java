package com.ttms.service.ProductManage.ServiceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttms.Entity.ProGroup;
import com.ttms.Entity.ProProject;
import com.ttms.Entity.SysDepartment;
import com.ttms.Entity.SysUser;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProGroupMapper;
import com.ttms.Mapper.ProProjectMapper;
import com.ttms.Mapper.SysDepartmentMapper;
import com.ttms.Vo.GroupManageVo;
import com.ttms.Vo.ProjectVo;
import com.ttms.service.ProductManage.IProjectService;
import com.ttms.Vo.PageResult;
import com.ttms.service.SystemManage.SysMenusService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectInfoService implements IProjectService {

    @Autowired
    private ProProjectMapper proProjectMapper;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysMenusService sysMenusService;



    //根据部门名称查询部门
    private SysDepartment getSysDepartment(String departName,Integer departId) {
        Example departExample = new Example(SysDepartment.class);
        Example.Criteria criteria = departExample.createCriteria();
        if(StringUtils.isNotBlank(departName)) {
            criteria.andEqualTo("departmentname", departName);
        }else if(StringUtils.isNotBlank(String.valueOf(departId))){
            criteria.andEqualTo("id", departId);
        }
        List<SysDepartment> departList = this.sysDepartmentMapper.selectByExample(departExample);
        if (CollectionUtils.isEmpty(departList)) {
            throw new TTMSException(ExceptionEnum.GROUP_NOT_FOUND);
        }
        return departList.get(0);
    }
    //新增项目 表单提交的数据有 项目编号 . 名称 起始日期--结束日期. 部门(部门需要根据输入的)，备注 没有的数据自己设置
    /**
     * 功能描述: <br>
     * 〈〉新增项目
     * @Param: [project]
     * @Return: java.lang.Void
     * @Author: 吴彬
     * @Date: 15:57 15:57
     */
    @Override
    public Void addProject(ProProject project, SysUser user, String departName) {
        project.setCreatetime(new Date());
        project.setCreateuserid(user.getId());
        project.setUpdatetime(null);
        SysDepartment department = getSysDepartment(departName,null);
        project.setDepartmentid(department.getId());
        project.setValid((byte)1);
        int i = this.proProjectMapper.insert(project);
        if(i!=1){
            throw new TTMSException(ExceptionEnum.PROJECT_INSERT_FAIL);
        }
        return null;
    }

    /**
     * 功能描述: <br>
     * 〈〉修改项目
     * @Param: [project, departName]
     * @Return: java.lang.Void
     * @Author: 吴彬
     * @Date: 19:33 19:33
     */
    @Override
    @Transactional
    public Void editProject(ProProject project,String departName) {
        project.setCreatetime(new Date());
        project.setUpdatetime(null);
        SysDepartment department = getSysDepartment(departName,null);
        project.setDepartmentid(department.getId());
        project.setValid((byte)1);
        int i = this.proProjectMapper.updateByPrimaryKeySelective(project);
        if(i!=1){
            throw new TTMSException(ExceptionEnum.PROJECT_UPDATE_FAIL);
        }
        return null;
    }

    @Override
    public Void prohibitProject(Integer pid) {
        return null;
    }

    @Override
    public Void enableProject(Integer pid) {
        return null;
    }

    @Override
    public PageResult<ProProject> getAllProjectByPage(String projectNumber, String projectName, int departmentid,
                                                      Date startTime, Date endTime, int valid, int page, int rows) {
        //分页
        PageHelper.startPage(page,rows);
        //查询条件
        Example example = new Example(ProProject.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(projectNumber)){
            criteria.andLike("projectnumber","%"+projectNumber+"%");
        }
        if(!StringUtils.isEmpty(projectName)){
            criteria.andLike("projectname","%"+projectName+"%");
        }
        if(departmentid != -1){
            criteria.andEqualTo("departmentid",departmentid);
        }
        if(startTime != null ){
            criteria.andGreaterThan("starttime",startTime);
        }
        if(endTime != null ){
            criteria.andLessThan("endtime",endTime);
        }
        if(valid != -1){
            criteria.andEqualTo("valid",valid);
        }
        //查询
        List<ProProject> proProjects = proProjectMapper.selectByExample(example);
        //创建返回结果
        PageResult<ProProject> result = new PageResult<>();
        PageInfo<ProProject> pageInfo = new PageInfo<>(proProjects);
        //封装部门名称
        //获取所有相关的部门id
        Set<Integer> departmentidsSet = proProjects.stream().map(ProProject::getDepartmentid).collect(Collectors.toSet());
        ArrayList<Integer> departIdList = new ArrayList<>(departmentidsSet);
        List<SysDepartment> sysDepartments = sysMenusService.getDepartmentsByIds(departIdList);
        //转化为map(id , String)
       Map<Integer, String> idNameMap = sysDepartments.stream().collect(Collectors.toMap(SysDepartment::getId, SysDepartment::getDepartmentname));
       for (ProProject proProject:proProjects){
           proProject.setDepartmentName(idNameMap.get(proProject.getDepartmentid()));
       }
        result.setItems(proProjects);
        result.setTotal(pageInfo.getTotal());
        result.setTotalPage(pageInfo.getPages());
        return result;
    }


}