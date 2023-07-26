package com.a2m.gen.dto.request;

import java.util.List;

import javax.validation.Valid;

import com.a2m.gen.dto.TccoStdDto;

public class SaveTccoStdRequest {
	@Valid
	private List<TccoStdDto> tssoStdDtos;
	
	private String sessUserId;
	
	private String idsDel;

	public SaveTccoStdRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<TccoStdDto> getTssoStdDtos() {
		return tssoStdDtos;
	}

	public void setTssoStdDtos(List<TccoStdDto> tssoStdDtos) {
		this.tssoStdDtos = tssoStdDtos;
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
