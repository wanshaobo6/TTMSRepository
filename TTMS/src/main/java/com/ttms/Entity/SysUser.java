package com.ttms.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name = "sys_user")
@Data
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    private String image;

    private String salt;

    private String email;

    private String mobile;

    private Byte valid;

    private Date createdtime;

    private Date modifiedtime;

    private int createduserid;

    private int modifieduserid;

    @Transient
    private SysRoles sysRole;
}