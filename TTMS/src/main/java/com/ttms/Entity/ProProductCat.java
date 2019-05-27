package com.ttms.Entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "pro_product_cat")
@Data
public class ProProductCat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productname;

    private String note;

    private Integer parentid;

    private Byte isparent;

    private Date createtime;

    private Date updatetime;

    private Integer createuserid;

    private Integer updateuserid;

}