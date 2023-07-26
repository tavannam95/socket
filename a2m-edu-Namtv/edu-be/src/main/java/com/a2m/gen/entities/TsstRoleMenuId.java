package com.a2m.gen.entities;

import java.io.Serializable;
import java.util.Objects;

public class TsstRoleMenuId implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String roleId;
	private String menuId;
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
	@Override
	public int hashCode() {
		return Objects.hash(menuId, roleId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TsstRoleMenuId other = (TsstRoleMenuId) obj;
		return Objects.equals(menuId, other.menuId) && Objects.equals(roleId, other.roleId);
	}
	
	
}
