package com.ttms.Controller;

import com.ttms.Entity.User;
import com.ttms.Mapper.Usermapper;
import com.ttms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/all")
public class UserController {

   @Autowired
   private UserService userService;
    public ResponseEntity<Void>  addUser(User user){
        this.userService.addUser(user);
       return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
