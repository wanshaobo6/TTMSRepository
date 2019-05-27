package com.ttms.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "pro_product")
public class ProProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productnumber;

    private Integer groupid;

    private Integer productcatid;

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

    private String produceintroduction;

    private Date createtime;

    private Date updatetime;

    private Integer createuserid;

    private Integer updateuserid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductnumber() {
        return productnumber;
    }

    public void setProductnumber(String productnumber) {
        this.productnumber = productnumber == null ? null : productnumber.trim();
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getProductcatid() {
        return productcatid;
    }

    public void setProductcatid(Integer productcatid) {
        this.productcatid = productcatid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public Integer getProductstatus() {
        return productstatus;
    }

    public void setProductstatus(Integer productstatus) {
        this.productstatus = productstatus;
    }

    public Date getServerstarttime() {
        return serverstarttime;
    }

    public void setServerstarttime(Date serverstarttime) {
        this.serverstarttime = serverstarttime;
    }

    public Date getServerendtime() {
        return serverendtime;
    }

    public void setServerendtime(Date serverendtime) {
        this.serverendtime = serverendtime;
    }

    public Integer getPresellnumber() {
        return presellnumber;
    }

    public void setPresellnumber(Integer presellnumber) {
        this.presellnumber = presellnumber;
    }

    public Integer getSellednumber() {
        return sellednumber;
    }

    public void setSellednumber(Integer sellednumber) {
        this.sellednumber = sellednumber;
    }

    public Integer getLowestnumber() {
        return lowestnumber;
    }

    public void setLowestnumber(Integer lowestnumber) {
        this.lowestnumber = lowestnumber;
    }

    public Date getOnselltime() {
        return onselltime;
    }

    public void setOnselltime(Date onselltime) {
        this.onselltime = onselltime;
    }

    public Integer getProjectprice() {
        return projectprice;
    }

    public void setProjectprice(Integer projectprice) {
        this.projectprice = projectprice;
    }

    public Date getUpselltime() {
        return upselltime;
    }

    public void setUpselltime(Date upselltime) {
        this.upselltime = upselltime;
    }

    public String getHottip() {
        return hottip;
    }

    public void setHottip(String hottip) {
        this.hottip = hottip == null ? null : hottip.trim();
    }

    public String getProduceintroduction() {
        return produceintroduction;
    }

    public void setProduceintroduction(String produceintroduction) {
        this.produceintroduction = produceintroduction == null ? null : produceintroduction.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(Integer createuserid) {
        this.createuserid = createuserid;
    }

    public Integer getUpdateuserid() {
        return updateuserid;
    }

    public void setUpdateuserid(Integer updateuserid) {
        this.updateuserid = updateuserid;
    }
}