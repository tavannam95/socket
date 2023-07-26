package com.a2m.gen.entities;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleId implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String roleId;
	private String userUid;
	public UserRoleId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserRoleId(String roleId, String userUid) {
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
	@Override
	public int hashCode() {
		return Objects.hash(roleId, userUid);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRoleId other = (UserRoleId) obj;
		return Objects.equals(roleId, other.roleId) && Objects.equals(userUid, other.userUid);
	}
	
	
}
