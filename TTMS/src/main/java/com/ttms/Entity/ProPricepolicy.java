package com.ttms.Entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "pro_pricepolicy")
@Data
public class ProPricepolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String policyname;

    private Integer policydiscount;

    private Integer maxnum;

    private Integer minnum;

    private Date starttime;

    private Date endtime;

    private String policynote;

    private Date createtime;

    private Date updatetime;
}
