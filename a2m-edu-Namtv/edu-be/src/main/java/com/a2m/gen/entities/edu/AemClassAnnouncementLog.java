package com.a2m.gen.entities.edu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;

@Entity
@Table(name = "EAM_CLASS_ANNOUNCEMENT_LOG")
public class AemClassAnnouncementLog extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ANNOUNCEMENT_LOG_ID")
	private Long annoucementLogId;

	@Column(name = "CLASS_ANNOUNCEMENT_ID")
	private Long classAnnoucementId;
	
	@Column(name = "USER_UID_LOG")
	private String userUidLog;


	public AemClassAnnouncementLog() {
		super();
	}


	public Long getAnnoucementLogId() {
		return annoucementLogId;
	}


	public void setAnnoucementLogId(Long annoucementLogId) {
		this.annoucementLogId = annoucementLogId;
	}


	public Long getClassAnnoucementId() {
		return classAnnoucementId;
	}


	public void setClassAnnoucementId(Long classAnnoucementId) {
		this.classAnnoucementId = classAnnoucementId;
	}


	public String getUserUidLog() {
		return userUidLog;
	}


	public void setUserUidLog(String userUidLog) {
		this.userUidLog = userUidLog;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	
	
}
