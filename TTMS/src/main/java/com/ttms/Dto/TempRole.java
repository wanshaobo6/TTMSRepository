package com.ttms.Dto;

import lombok.Data;

//sysMenusService ->queryUserByPage 用于封装多表数据
@Data
public class TempRole {
    //角色id
    private Integer id;

    //子部门id
    private Integer cdepartmentid;

    //子部门name
    private String  childdepartmentname;

    //父部门id
    private Integer pdepartmentid;

    //父部门名称
    private String  parentdepartmentname;
}
