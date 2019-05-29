package com.ttms.service.SystemManage.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttms.Entity.ProProject;
import com.ttms.Entity.SysDepartment;
import com.ttms.Entity.SysUser;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProProjectMapper;
import com.ttms.Mapper.SysDepartmentMapper;
import com.ttms.Vo.PageResult;
import com.ttms.Vo.ProjectVo;
import com.ttms.service.SystemManage.IProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProjectService implements IProjectService {
    @Autowired
    private ProProjectMapper proProjectMapper;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;
    /**
     * 功能描述: <br>
     * 〈〉多条件组合查询
     * @Param: [page, rows, project, departName]
     * @Return: com.ttms.Vo.PageResult<com.ttms.Entity.ProProject>
     * @Author: 吴彬
     * @Date: 15:51 15:51
     */
    @Override
    public PageResult<ProjectVo> queryProjectByPage(Integer page, Integer rows, ProProject project, String departName) {
        PageHelper.startPage(page, rows);
        Example example=new Example(ProProject.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(project.getProjectnumber())){
            criteria.andLike("projectNumber","%"+project.getProjectnumber()+"%");
        }else if(StringUtils.isNotBlank(project.getProjectname())){
            criteria.andLike("projectName","%"+project.getProjectname()+"%");
        }
        SysDepartment sysDepartment = getSysDepartment(departName,null);
        Integer departmentId = sysDepartment.getId();
        if(StringUtils.isNotBlank(String.valueOf(departmentId))){
            criteria .andEqualTo("departmentId", departmentId);
        }else if(project.getStarttime()!=null){
            criteria.andEqualTo("startTime", project.getStarttime());
        }else if(project.getEndtime()!=null){
            criteria.andEqualTo("endTime", project.getEndtime());
        }

        List<ProProject> list = this.proProjectMapper.selectByExample(example);
        List<ProjectVo> projectVoList=new ArrayList<>();
        for (ProProject proProject : list) {
            SysDepartment sysDepartment1 = getSysDepartment(null, project.getDepartmentid());
            ProjectVo projectVo=new ProjectVo();
            projectVo.setDpartname(sysDepartment1.getDepartmentname());
            projectVoList.add(projectVo);
        }
        PageInfo<ProjectVo> info=new PageInfo<>(projectVoList);
        PageResult<ProjectVo> result=new PageResult<>();
        result.setTotalPage(info.getPages());
        result.setTotal(info.getTotal());
        result.setItems(info.getList());
        return result;
    }

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
            throw new TTMSException(ExceptionEnum.NOT_FOUND_DEPARTMENT);
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
    @Transactional
    public Void addProject(ProProject project, SysUser user, String departName) {
        project.setCreatetime(new Date());
        project.setCreateuserid(user.getId());
        project.setUpdatetime(null);
        SysDepartment department = getSysDepartment(departName,null);
        project.setDepartmentid(department.getId());
        project.setValid((byte)1);
        int i = this.proProjectMapper.insert(project);
        if(i!=1){
            throw new TTMSException(ExceptionEnum.NOT_AUTHORITY);
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
            throw new TTMSException(ExceptionEnum.EDIT_PROJECT_FILE);
        }
        return null;
    }

    /**
     * 功能描述: <br>
     * 〈〉根据id查询项目和部门
     * @Param: [id]
     * @Return: com.ttms.Vo.ProjectVo
     * @Author: 吴彬
     * @Date: 19:32 19:32
     */
    @Override
    public ProjectVo queryProProjectByid(Integer id) {
        ProjectVo proProject = this.proProjectMapper.selectProjectAndDepartment(id);
        if(proProject==null){
            throw new TTMSException(ExceptionEnum.QUERY_PROJECT_FILE);
        }
        return proProject;
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
    public void getAllGroups() {

    }
}
