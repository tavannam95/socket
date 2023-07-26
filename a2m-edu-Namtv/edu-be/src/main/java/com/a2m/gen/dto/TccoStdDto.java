package com.a2m.gen.dto;

import java.util.Date;


public class TccoStdDto {
	private String commCd;
	private String commNm;
	private String commNmEn;
	private String commNmVi;
	private String upCommCd;
	private String useYn;
	private String description;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private Integer lev;
	private String state;
	
	public TccoStdDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCommCd() {
		return commCd;
	}
	public void setCommCd(String commCd) {
		this.commCd = commCd;
	}
	public String getCommNm() {
		return commNm;
	}
	public void setCommNm(String commNm) {
		this.commNm = commNm;
	}
	public String getCommNmEn() {
		return commNmEn;
	}
	public void setCommNmEn(String commNmEn) {
		this.commNmEn = commNmEn;
	}
	public String getCommNmVi() {
		return commNmVi;
	}
	public void setCommNmVi(String commNmVi) {
		this.commNmVi = commNmVi;
	}
	public String getUpCommCd() {
		return upCommCd;
	}
	public void setUpCommCd(String upCommCd) {
		this.upCommCd = upCommCd;
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
	public Integer getLev() {
		return lev;
	}
	public void setLev(Integer lev) {
		this.lev = lev;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
