package com.a2m.gen.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TSST_MENU")
public class TsstMenu {
	
	@Id
	@Column(name = "MENU_ID")
	private String menuId;
	
	@Column(name = "MENU_NM")
	private String menuNm;
	
	@Column(name = "MENU_NM_EN")
	private String menuNmEn;
	
	@Column(name = "MENU_NM_VI")
	private String menuNmVi;
	
	@Column(name = "LEV")
	private Integer lev;
	
	@Column(name = "UP_MENU_ID")
	private String upMenuId;
	
	@Column(name = "USE_YN")
	private String useYn;
	
	@Column(name = "URL")
	private String url;
	
	@Column(name = "ORD_NO")
	private Integer ordNo;
	
	@Column(name = "MENU_TYPE")
	private String menuType;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	public TsstMenu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TsstMenu(String menuId, String menuNm, String menuNmEn, String menuNmVi, Integer lev, String upMenuId,
			String useYn, String url, Integer ordNo, String menuType, String description, String createdBy,
			Date createdDate, String updatedBy, Date updatedDate) {
		super();
		this.menuId = menuId;
		this.menuNm = menuNm;
		this.menuNmEn = menuNmEn;
		this.menuNmVi = menuNmVi;
		this.lev = lev;
		this.upMenuId = upMenuId;
		this.useYn = useYn;
		this.url = url;
		this.ordNo = ordNo;
		this.menuType = menuType;
		this.description = description;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuNm() {
		return menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getMenuNmEn() {
		return menuNmEn;
	}

	public void setMenuNmEn(String menuNmEn) {
		this.menuNmEn = menuNmEn;
	}

	public String getMenuNmVi() {
		return menuNmVi;
	}

	public void setMenuNmVi(String menuNmVi) {
		this.menuNmVi = menuNmVi;
	}

	public Integer getLev() {
		return lev;
	}

	public void setLev(Integer lev) {
		this.lev = lev;
	}

	public String getUpMenuId() {
		return upMenuId;
	}

	public void setUpMenuId(String upMenuId) {
		this.upMenuId = upMenuId;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(Integer ordNo) {
		this.ordNo = ordNo;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
	
}
