package com.ttms.service.ProductManage.ServiceImpl;

import com.ttms.Entity.MesMessage;
import com.ttms.Entity.SysUser;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.MesMessageMapper;
import com.ttms.service.ProductManage.IPubNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PubNotifyService implements IPubNotifyService {

    @Autowired
    private MesMessageMapper mesMessageMapper;
    /**
    * 功能描述: <br>
    * 〈〉发布通知
    * @Param: [messageClassName, messageTitle, messageContent]
    * @Return: java.lang.Void
    * @Author: 吴彬
    * @Date: 10:49 10:49
     */
    @Override
    @Transactional
    public Void publicMsg(String messageClassName, String messageTitle, String messageContent,SysUser user) {
        MesMessage mesMessage=new MesMessage();
        mesMessage.setValid((byte)1);
        mesMessage.setMessageclassname(messageClassName);
        mesMessage.setMessagecontent(messageContent);
        mesMessage.setMessagetitle(messageTitle);
        mesMessage.setSenderid(user.getId());
        mesMessage.setUpdatetime(null);
        mesMessage.setSendtime(new Date());
        int i = this.mesMessageMapper.insert(mesMessage);
        if(i!=1){
            throw new TTMSException(ExceptionEnum.MSG_RELEASE_FALI);
        }
        return null;
    }
}
