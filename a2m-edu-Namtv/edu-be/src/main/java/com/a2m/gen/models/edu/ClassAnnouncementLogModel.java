package com.a2m.gen.models.edu;

import com.a2m.gen.entities.edu.AemClassAnnouncementLog;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author phongnc
 */
public class ClassAnnouncementLogModel extends ParamBaseModel {
	
	private Long classAnnoucementId;
	
	private String userUidLog;

	public ClassAnnouncementLogModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ClassAnnouncementLogModel(AemClassAnnouncementLog db) {
		super();
		this.key = db.getAnnoucementLogId();
		this.classAnnoucementId = db.getClassAnnoucementId();
		this.userUidLog = db.getUserUidLog();
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


	
	
}
