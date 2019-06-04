package com.ttms.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name="sys_roles")
@Data
public class SysRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String note;

    private Date createdtime;

    private Date modifiedtime;

    private int createduserId;

    private int modifieduserId;

    private Integer departmentId;

    @Transient
    private SysMenus sysMenus;
}