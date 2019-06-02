package com.ttms.service.ProductManage;

import com.ttms.Entity.SysUser;

public interface IPubNotifyService  {
    Void publicMsg(String messageClassName, String messageTitle, String messageContent, SysUser user);
}
