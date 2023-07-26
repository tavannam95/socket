package com.a2m.gen.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "TCCO_STD")
@Entity
public class TccoStd {
	@Id
	@Column(name = "COMM_CD")
	private String commCd;
	@Column(name = "COMM_NM")
	private String commNm;
	@Column(name = "COMM_NM_EN")
	private String commNmEn;
	@Column(name = "COMM_NM_VI")
	private String commNmVi;
	@Column(name = "UP_COMM_CD")
	private String upCommCd;
	@Column(name = "USE_YN")
	private String useYn;
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
	@Column(name = "LEV")
	private Integer lev;
	
	@JsonIgnore
	@OneToMany(mappedBy = "connectType")
    private List<TargetMgt> targetMgt;
	
	public TccoStd() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public TccoStd(String commCd, String commNm, String commNmEn, String commNmVi, String upCommCd, String useYn,
			String description, String createdBy, Date createdDate, String updatedBy, Date updatedDate, Integer lev,
			List<TargetMgt> targetMgt) {
		super();
		this.commCd = commCd;
		this.commNm = commNm;
		this.commNmEn = commNmEn;
		this.commNmVi = commNmVi;
		this.upCommCd = upCommCd;
		this.useYn = useYn;
		this.description = description;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.lev = lev;
	}


	public List<TargetMgt> getTargetMgt() {
		return targetMgt;
	}

	public void setTargetMgt(List<TargetMgt> targetMgt) {
		this.targetMgt = targetMgt;
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
	
}
