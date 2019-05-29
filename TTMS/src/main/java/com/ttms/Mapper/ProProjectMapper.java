package com.ttms.Mapper;

import com.ttms.Entity.ProProject;
import com.ttms.Vo.ProjectVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProProjectMapper extends Mapper<ProProject> {
    @Select("SELECT j.*,d.departmentname FROM pro_project j INNER  JOIN sys_department d ON d.`id`=j.departmentId")
    public List<ProjectVo> selectAllProjectAndDepartment();

}
