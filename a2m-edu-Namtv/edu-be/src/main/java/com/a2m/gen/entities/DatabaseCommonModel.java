package com.a2m.gen.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * <strong>DatabaseCommonModel</strong><br>
 * <br>
 *
 * @author doanhq
 * @version $Id$
 *
 */
@MappedSuperclass
public class DatabaseCommonModel{

	@Column(name = "INS_DT")
	private Date insDt;
	
	@Column(name = "INS_UID")
	private String insUid;
	
	@Column(name = "UPD_DT")
	private Date updDt;
	
	@Column(name = "UPD_UID")
	private String updUid;
	
	@Column(name = "DELETE_FLAG")
	private Boolean deleteFlag;

	public Date getInsDt() {
		return insDt;
	}

	public void setInsDt(Date insDt) {
		this.insDt = insDt;
	}

	public String getInsUid() {
		return insUid;
	}

	public void setInsUid(String insUid) {
		this.insUid = insUid;
	}

	public Date getUpdDt() {
		return updDt;
	}

	public void setUpdDt(Date updDt) {
		this.updDt = updDt;
	}

	public String getUpdUid() {
		return updUid;
	}

	public void setUpdUid(String updUid) {
		this.updUid = updUid;
	}

	public Boolean getDeleteFlag(){
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	
}
