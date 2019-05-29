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

    //判断该用户是否属于产品部门
    @Select("SELECT depart.departmentname FROM sys_department depart WHERE  depart.id IN" +
            "( SELECT r.departmentId FROM sys_user u, sys_roles r,sys_user_roles s WHERE u.id=s.user_id AND s.role_id=r.id AND u.id=#{Uid})")
    public SysDepartment IsBelongProduceManager(@Param("Uid")Integer Uid);
}
