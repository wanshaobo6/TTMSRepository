package com.ttms.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Table(name = "sys_user")
@Data
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    @JsonIgnore
    private String password;

    @NotNull(message = "头像不能为空")
    private String image;

    private String salt;

    private String email;

    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    private String mobile;

    @NotNull(message = "角色不能为空")
    private Integer roleid;

    private Byte valid;

    private Date createdtime;

    private Date modifiedtime;

    private Integer createduserid;

    @Transient
    private SysRoles sysRole;
}