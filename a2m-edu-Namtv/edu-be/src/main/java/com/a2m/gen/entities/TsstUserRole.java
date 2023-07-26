package com.a2m.gen.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "TSST_USER_ROLE")
@IdClass(UserRoleId.class)
public class TsstUserRole {
	@Id
	@Column(name = "ROLE_ID")
	private String roleId;
	@Id
	@Column(name = "USER_UID")
	private String userUid;
	
	public TsstUserRole() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TsstUserRole(String roleId, String userUid) {
		super();
		this.roleId = roleId;
		this.userUid = userUid;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUserUid() {
		return userUid;
	}
	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}
	
	
}
