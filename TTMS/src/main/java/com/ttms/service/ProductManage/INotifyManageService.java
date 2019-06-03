package com.ttms.service.ProductManage;

import com.ttms.Entity.MesMessage;
import com.ttms.Entity.SysUser;

import java.util.List;

public interface INotifyManageService {

    //查询所有通知
    public List<MesMessage> queryAllnew();

    //查询所有有关自己的通知
    List<MesMessage> querybyUser(SysUser user);

    Void updateState(Integer mid);
}
