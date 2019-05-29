package com.ttms.Mapper;

import com.ttms.Entity.ProProject;
import com.ttms.Vo.ProjectVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProProjectMapper extends Mapper<ProProject> {
    @Select("SELECT j.*,d.departmentname FROM pro_project j INNER  JOIN sys_department d ON d.`id`=j.departmentId")
    public List<ProjectVo> selectProjectAndDepartment();

    @Select("select j.*,d.departmentname from pro_project j inner join sys_department d on d.`id`=j.departmentId and j.departmentId=#{dId}")
    public ProjectVo selectProjectAndDepartment(@Param("dId") Integer dId);
}
