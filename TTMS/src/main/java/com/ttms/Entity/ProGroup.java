package com.ttms.Entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "pro_group")
@Data
public class ProGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String groupname;

    private Integer projectid;

    private String projectName;

    private Integer chargeuserid;

    private String chargeUserMobile;

    private String groupnote;

    private Byte valid;

    private Date createtime;

    private Date updatetime;

    private Integer createuserid;

    private Integer updateuserid;

}