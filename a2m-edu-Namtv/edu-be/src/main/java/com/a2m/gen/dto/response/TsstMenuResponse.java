package com.a2m.gen.dto.response;

import java.io.Serializable;
import java.util.Date;

public class TsstMenuResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String menuId; 
    private String menuNm; 
    private String menuNmEn; 
    private String menuNmVi; 
    private Integer lev; 
    private String upMenuId; 
    private String useYn; 
    private String url; 
    private Integer ordNo; 
    private String menuType;
    private String description; 
    private String createdBy; 
    private Date createdDate; 
    private String updatedBy; 
    private Date updatedDate; 

    public TsstMenuResponse() {
       
	}

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuId() {
        return this.menuId;
    }
    
    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public String getMenuNm() {
        return this.menuNm;
    }

    public void setMenuNmEn(String menuNmEn) {
        this.menuNmEn = menuNmEn;
    }

    public String getMenuNmEn() {
        return this.menuNmEn;
    }

    public void setMenuNmVi(String menuNmVi) {
        this.menuNmVi = menuNmVi;
    }

    public String getMenuNmVi() {
        return this.menuNmVi;
    }

    public void setLev(Integer lev) {
        this.lev = lev;
    }

    public Integer getLev() {
        return this.lev;
    }

    public void setUpMenuId(String upMenuId) {
        this.upMenuId = upMenuId;
    }

    public String getUpMenuId() {
        return this.upMenuId;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getUseYn() {
        return this.useYn;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setOrdNo(Integer ordNo) {
        this.ordNo = ordNo;
    }

    public Integer getOrdNo() {
        return this.ordNo;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuType() {
        return this.menuType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }


}
