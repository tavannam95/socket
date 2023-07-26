package com.a2m.gen.models.edu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemClassAnnouncement;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.entities.edu.AemClassTeacherMap;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.utils.ModelUtil;

/**
 *
 * @author phongnc
 */
public class ClassAnnouncementModel extends ParamBaseModel {
	
	private String title;
	
	private String content;
	
	private Date startDate;
	
	private Date endDate;
	
	private Boolean status;
	
	private ClassModel classModel;
	
	private ClassAnnouncementLogModel announcementLog;

	public ClassAnnouncementModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ClassAnnouncementModel(AemClassAnnouncement entity) {
		this.key = entity.getClassAnnoucementId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.startDate = entity.getStartDate();
		this.endDate = entity.getEndDate();
		this.status = entity.getStatus();
		AemClass aemClass = entity.getAemClassAnnou();
		this.classModel =  new ClassModel(aemClass);
		ModelUtil.setCommonFields(entity, this);
	}

	public ClassAnnouncementModel(String title, String content, Date startDate, Date endDate, Boolean status,
			ClassModel classModel) {
		super();
		this.title = title;
		this.content = content;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.classModel = classModel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public ClassModel getClassModel() {
		return classModel;
	}

	public void setClassModel(ClassModel classModel) {
		this.classModel = classModel;
	}

	public ClassAnnouncementLogModel getAnnouncementLog() {
		return announcementLog;
	}

	public void setAnnouncementLog(ClassAnnouncementLogModel announcementLog) {
		this.announcementLog = announcementLog;
	}
}
