package com.ttms.service;

import com.ttms.Entity.User;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.Usermapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

   @Autowired
    private  Usermapper usermapper;
    public  void addUser(User user){
        int i = this.usermapper.insert(user);
        if(i!=1){
            throw new TTMSException(ExceptionEnum.NOT_AUTHORITY);
        }

    }


}
