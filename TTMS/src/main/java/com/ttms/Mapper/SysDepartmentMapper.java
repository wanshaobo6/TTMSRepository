package com.ttms.Mapper;

import com.ttms.Entity.SysDepartment;
import com.ttms.Entity.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysDepartmentMapper extends Mapper<SysDepartment> {
    @Select("select sur.user_id from sys_user_roles sur where sur.role_id in " +
            "(select sr.id from sys_department sd join sys_roles sr  on sd.id = sr.departmentId )")
    public List<String> getAllStaffIdsOfDepartment(int departmentId);
}
