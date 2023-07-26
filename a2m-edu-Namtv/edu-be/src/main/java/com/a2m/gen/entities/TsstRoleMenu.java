package com.a2m.gen.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "TSST_ROLE_MENU")
@IdClass(TsstRoleMenuId.class)
public class TsstRoleMenu {
	
	@Id
	@Column(name = "ROLE_ID")
	private String roleId;
	
	@Id
	@Column(name = "MENU_ID")
	private String menuId;
	
	@Column(name = "READ_YN")
	private String readYn;
	
	@Column(name = "WRT_YN")
	private String wrtYn;
	
	@Column(name = "MOD_YN")
	private String modYn;
	
	@Column(name = "DEL_YN")
	private String delYn;
	
	@Column(name = "PNT_YN")
	private String pntYn;
	
	@Column(name = "EXC_DN_YN")
	private String excDnYn;
	
	@Column(name = "MNG_YN")
	private String mngYn;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	public TsstRoleMenu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TsstRoleMenu(String roleId, String menuId, String readYn, String wrtYn, String modYn, String delYn,
			String pntYn, String excDnYn, String mngYn, String createdBy, Date createdDate) {
		super();
		this.roleId = roleId;
		this.menuId = menuId;
		this.readYn = readYn;
		this.wrtYn = wrtYn;
		this.modYn = modYn;
		this.delYn = delYn;
		this.pntYn = pntYn;
		this.excDnYn = excDnYn;
		this.mngYn = mngYn;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getReadYn() {
		return readYn;
	}

	public void setReadYn(String readYn) {
		this.readYn = readYn;
	}

	public String getWrtYn() {
		return wrtYn;
	}

	public void setWrtYn(String wrtYn) {
		this.wrtYn = wrtYn;
	}

	public String getModYn() {
		return modYn;
	}

	public void setModYn(String modYn) {
		this.modYn = modYn;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getPntYn() {
		return pntYn;
	}

	public void setPntYn(String pntYn) {
		this.pntYn = pntYn;
	}

	public String getExcDnYn() {
		return excDnYn;
	}

	public void setExcDnYn(String excDnYn) {
		this.excDnYn = excDnYn;
	}

	public String getMngYn() {
		return mngYn;
	}

	public void setMngYn(String mngYn) {
		this.mngYn = mngYn;
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
	
	
	
}
