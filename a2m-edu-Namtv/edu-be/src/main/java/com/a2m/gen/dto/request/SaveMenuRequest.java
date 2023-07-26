package com.a2m.gen.dto.request;

import java.util.List;

import javax.validation.Valid;

import com.a2m.gen.dto.TsstMenuDto;

public class SaveMenuRequest {
	@Valid
	private List<TsstMenuDto> tsstMenu;
	
	private String sessUserId;
	
	private String idsDel;


	public List<TsstMenuDto> getTsstMenu() {
		return tsstMenu;
	}

	public void setTsstMenu(List<TsstMenuDto> tsstMenu) {
		this.tsstMenu = tsstMenu;
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
