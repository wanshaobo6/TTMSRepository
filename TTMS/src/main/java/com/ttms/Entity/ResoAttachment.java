package com.ttms.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "reso_attachment")
public class ResoAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String filename;

    private Integer productId;

    private String attachmenttitle;

    private Byte invalid;

    private String fileurl;

    private Date uploadtime;

    private Integer uploaduserid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getAttachmenttitle() {
        return attachmenttitle;
    }

    public void setAttachmenttitle(String attachmenttitle) {
        this.attachmenttitle = attachmenttitle == null ? null : attachmenttitle.trim();
    }

    public Byte getInvalid() {
        return invalid;
    }

    public void setInvalid(Byte invalid) {
        this.invalid = invalid;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl == null ? null : fileurl.trim();
    }

    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    public Integer getUploaduserid() {
        return uploaduserid;
    }

    public void setUploaduserid(Integer uploaduserid) {
        this.uploaduserid = uploaduserid;
    }
}