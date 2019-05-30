package com.ttms.Entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "pro_product")
@Data
public class ProProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productnumber;

    private Integer groupid;

    private Integer productcatid1;

    private Integer productcatid2;

    private Integer productcatid3;

    private int projectId;

    private  String projectName;

    private String productname;

    private Integer productstatus;

    private Date serverstarttime;

    private Date serverendtime;

    private Integer presellnumber;

    private Integer sellednumber;

    private Integer lowestnumber;

    private Date onselltime;

    private Integer projectprice;

    private Date upselltime;

    private String hottip;

    private String productintroduction;

    private Date createtime;

    private Date updatetime;

    private Integer createuserid;

    private Integer updateuserid;

}