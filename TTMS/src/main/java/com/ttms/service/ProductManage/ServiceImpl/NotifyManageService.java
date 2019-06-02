package com.ttms.service.ProductManage.ServiceImpl;

import com.ttms.Entity.MesMessage;
import com.ttms.Entity.SysUser;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.MesMessageMapper;
import com.ttms.service.ProductManage.INotifyManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class NotifyManageService implements INotifyManageService {
    @Autowired
    private MesMessageMapper mesMessageMapper;

    /**
    * 功能描述: <br>
    * 〈〉查询所有消息
    * @Param: []
    * @Return: java.util.List<com.ttms.Entity.MesMessage>
    * @Author: 吴彬
    * @Date: 10:02 10:02
     */
    @Override
    public List<MesMessage> queryAllnew() {
        Example example=new Example(MesMessage.class);
        example.setOrderByClause("sendTime DESC");
        List<MesMessage> mesMessages = this.mesMessageMapper.selectByExample(example);
        return mesMessages;
    }

    /**
    * 功能描述: <br>
    * 〈〉查询所有有关自己的通知
    * @Param: [user]
    * @Return: java.util.List<com.ttms.Entity.MesMessage>
    * @Author: 吴彬
    * @Date: 10:06 10:06
     */
    @Override
    public List<MesMessage> querybyUser(SysUser user) {
        Example example=new Example(MesMessage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("senderid", user.getId());
        criteria.andEqualTo("valid", 1);
        List<MesMessage> mesMessages = this.mesMessageMapper.selectByExample(example);
        return mesMessages;
    }

    /**
    * 功能描述: <br>
    * 〈〉更新状态
    * @Param: [mid]
    * @Return: java.lang.Void
    * @Author: 吴彬
    * @Date: 10:15 10:15
     */
    @Override
    @Transactional
    public Void updateState(Integer mid) {
        MesMessage mesMessage = this.mesMessageMapper.selectByPrimaryKey(mid);
        mesMessage.setValid((byte)(mesMessage.getValid() ^ 1));
        int i = this.mesMessageMapper.updateByPrimaryKey(mesMessage);
        if(i!=1){
            throw new TTMSException(ExceptionEnum.MSG_UPDATE_FALI);
        }
        return null;
    }
}
