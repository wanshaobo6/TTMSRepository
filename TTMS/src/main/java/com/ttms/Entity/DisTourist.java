package com.ttms.Entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "dis_tourist_record")
@Data
public class DisTourist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tName;

    private Byte tSex;

    private String tIdcard;

    private String tPhone;

    private String tNote;

    private Integer distributorid;

    private Integer productid;

    private Date signuptime;

    private Integer pricepolicyid;

    private Integer acutalpay;

}