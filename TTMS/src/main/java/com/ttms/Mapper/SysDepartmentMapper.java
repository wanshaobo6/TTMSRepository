package com.ttms.Mapper;

import com.ttms.Entity.SysDepartment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysDepartmentMapper extends Mapper<SysDepartment> {
    @Select("select sur.user_id from sys_user_roles sur where sur.role_id in " +
            "(select sr.id from sys_department sd join sys_roles sr  on sd.id = sr.departmentId where " +
            "sd.id = #{departmentId})")
    public List<String> getAllStaffIdsOfDepartment(@Param("departmentId") int departmentId);
}
