package com.a2m.gen.dto.request;

import java.util.List;

import javax.validation.Valid;

import com.a2m.gen.dto.TsstRoleDto;

public class SaveRoleRequest {
	
	@Valid
	private List<TsstRoleDto> tsstRole;
	
	private String sessUserId;
	
	private String idsDel;

	public SaveRoleRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SaveRoleRequest(@Valid List<TsstRoleDto> tsstRole, String sessUserId, String idsDel) {
		super();
		this.tsstRole = tsstRole;
		this.sessUserId = sessUserId;
		this.idsDel = idsDel;
	}

	public List<TsstRoleDto> getTsstRole() {
		return tsstRole;
	}

	public void setTsstRole(List<TsstRoleDto> tsstRole) {
		this.tsstRole = tsstRole;
	}

	public String getSessUserId() {
		return sessUserId;
	}

	public void setSessUserId(String sessUserId) {
		this.sessUserId = sessUserId;
	}

	public String getIdsDel() {
		return idsDel;
	}

	public void setIdsDel(String idsDel) {
		this.idsDel = idsDel;
	}
	
	
}
