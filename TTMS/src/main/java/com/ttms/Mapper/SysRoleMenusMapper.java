package com.ttms.Mapper;

import com.ttms.Dto.TempRole;
import com.ttms.Entity.SysMenus;
import com.ttms.Entity.SysRoleMenus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRoleMenusMapper extends Mapper<SysRoleMenus> {

    @Select("SELECT sm.* FROM sys_role_menus srm JOIN sys_menus sm ON srm.`menu_id`= sm.`id` WHERE srm.`role_id`=#{id}")
    List<SysMenus> getMenusListByRoleId(@Param("id") int id);

    //需传入(1,5,6)这样的参数
    @Select("SELECT sr.`id` , sr.`name` , sd1.`id` AS cdepartmentid , sd1.`departmentname` AS childdepartmentname  " +
            ", sd2.`id` AS pdepartmentid , sd2.`departmentname` AS parentdepartmentname FROM sys_roles sr " +
            "JOIN sys_department sd1 ON sr.`departmentId` = sd1.`id` JOIN sys_department sd2 " +
            "ON sd2.`id` = sd1.`parentid` WHERE sr.`id` IN ${ids}")
    List<TempRole> getRoleAndDePartInfoByRoleId(@Param("ids") String ids);
}
