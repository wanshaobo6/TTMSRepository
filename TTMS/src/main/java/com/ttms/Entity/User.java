package com.ttms.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="sys_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Integer id;

    private String username;
    private String password;
    private String salt;
    private String eamil;
    private Integer mobile;
    private String valid;
    private Date createdTime;
    private Date modifiedTime;
    private String createdUser;
    private String modifiedUser;


}
