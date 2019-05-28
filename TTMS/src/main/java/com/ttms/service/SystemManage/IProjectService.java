package com.ttms.service.SystemManage;

import com.ttms.Entity.ProProject;
import com.ttms.Vo.PageResult;

public interface IProjectService {

    //分页查询所有项目多条件组合查询 项目编号，名称，归属部门，起始日期--结束日期.状态
    PageResult<ProProject> queryProjectByPage(Integer page,Integer rows,ProProject project,String departName);


    //新增项目 表单提交的数据有 项目编号 . 名称 起始日期--结束日期. 部门(部门需要根据输入的)，备注 没有的数据自己设置
    Void addProject(ProProject project);

    //类似新增
    Void editProject(ProProject project);

    //对某个项目禁用修改valid的值
    Void prohibitProject(Integer pid);

    //对某个项目启用修改valid的值
    Void enableProject(Integer pid);


    void getAllGroups();


}
