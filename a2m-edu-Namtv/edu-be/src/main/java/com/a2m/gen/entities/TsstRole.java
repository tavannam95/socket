package com.a2m.gen.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TSST_ROLE")
public class TsstRole {
	
	@Id
	@Column(name = "ROLE_ID")
	private String roleId;
	
	@Column(name = "ROLE_NM")
	private String roleNm;
	
	@Column(name = "USE_YN")
	private String useYn;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@ManyToMany
	@JoinTable(
			  name = "TSST_USER_ROLE", 
			  joinColumns = @JoinColumn(name = "ROLE_ID"), 
			  inverseJoinColumns = @JoinColumn(name = "USER_UID"))
	private List<TsstUser> users;

	public TsstRole() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TsstRole(String roleId, String roleNm, String useYn, String description, Date createdDate, String createdBy,
			Date updatedDate, String updatedBy, List<TsstUser> users) {
		super();
		this.roleId = roleId;
		this.roleNm = roleNm;
		this.useYn = useYn;
		this.description = description;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.updatedDate = updatedDate;
		this.updatedBy = updatedBy;
		this.users = users;
	}

	public List<TsstUser> getUsers() {
		return users;
	}

	public void setUsers(List<TsstUser> users) {
		this.users = users;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleNm() {
		return roleNm;
	}

	public void setRoleNm(String roleNm) {
		this.roleNm = roleNm;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	
}
