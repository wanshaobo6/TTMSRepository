package com.ttms.service.SystemManage.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ttms.Entity.ProProject;
import com.ttms.Entity.SysDepartment;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProProjectMapper;
import com.ttms.Mapper.SysDepartmentMapper;
import com.ttms.service.SystemManage.IProjectService;
import com.ttms.utils.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

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
    * @Return: com.ttms.utils.PageResult<com.ttms.Entity.ProProject>
    * @Author: 吴彬
    * @Date: 15:51 15:51
     */
    @Override
    public PageResult<ProProject> queryProjectByPage(Integer page,Integer rows,ProProject project,String departName ) {
        PageHelper.startPage(page, rows);
        Example example=new Example(ProProject.class);
        if(StringUtils.isNotBlank(project.getProjectnumber())){
            example.createCriteria().andLike("projectNumber","%"+project.getProjectnumber()+"%");
        }else if(StringUtils.isNotBlank(project.getProjectname())){
            example.createCriteria().andLike("projectName","%"+project.getProjectname()+"%");
        }
        Example departExample=new Example(SysDepartment.class);
        departExample.createCriteria().andEqualTo("departmentname", departName);
        List<SysDepartment> departList = this.sysDepartmentMapper.selectByExample(departExample);
        if(CollectionUtils.isEmpty(departList)){
            throw new TTMSException(ExceptionEnum.DEPARTMENT_NOT_FOUND);
        }
        SysDepartment sysDepartment = departList.get(0);
        Integer departmentId = sysDepartment.getId();
        if(StringUtils.isNotBlank(String.valueOf(departmentId))){
            example.createCriteria().andEqualTo("departmentId", departmentId);
        }else if(project.getStarttime()!=null){
            example.createCriteria().andEqualTo("startTime", project.getStarttime());
        }else if(project.getEndtime()!=null){
            example.createCriteria().andEqualTo("endTime", project.getEndtime());
        }

        List<ProProject> list = this.proProjectMapper.selectByExample(example);
        PageInfo<ProProject> info=new PageInfo<>(list);
        PageResult<ProProject> result=new PageResult<>();
        result.setTotalPage(info.getPages());
        result.setTotal(info.getTotal());
        result.setItems(info.getList());
        return result;
    }


    @Override
    public Void addProject(ProProject project) {
        return null;
    }


    @Override
    public Void editProject(ProProject project) { return null;
    }


    @Override
    public Void prohibitProject(Integer pid) {
        operProject(pid);
        return  null;
    }


    @Override
    public Void enableProject(Integer pid) {
     operProject(pid);
     return  null;
}

    public void operProject(Integer pid) {
        ProProject project = this.proProjectMapper.selectByPrimaryKey(pid);
        project.setValid((byte) (project.getValid() ^ 1));
        int i = this.proProjectMapper.updateByPrimaryKey(project);
        if (i != 1) {
            throw new TTMSException(ExceptionEnum.OPERATION_FAILURE);
        }

    }
}