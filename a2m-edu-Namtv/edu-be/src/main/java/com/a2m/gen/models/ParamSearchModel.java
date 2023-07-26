package com.a2m.gen.models;

import java.util.List;

public class ParamSearchModel extends ParamBaseModel{
	
	public String progressType;
	
	private String  startTime;
	
	private String  endTime;
	
	private Long courseId ;
	
	private String tagName ;
	
	private List<Long> listCheckedId ;

	private Boolean displayStatus ;


	public ParamSearchModel(String progressType, String startTime, String endTime, Long courseId, String tagName,
			List listCheckedId, Boolean displayStatus) {
		super();
		this.progressType = progressType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.courseId = courseId;
		this.tagName = tagName;
		this.listCheckedId = listCheckedId;
		this.displayStatus = displayStatus;
	}


	public ParamSearchModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public List getListCheckedId() {
		return listCheckedId;
	}


	public void setListCheckedId(List listCheckedId) {
		this.listCheckedId = listCheckedId;
	}


	public Boolean getDisplayStatus() {
		return displayStatus;
	}


	public void setDisplayStatus(Boolean displayStatus) {
		this.displayStatus = displayStatus;
	}


	public String getTagName() {
		return tagName;
	}


	public void setTagName(String tagName) {
		this.tagName = tagName;
	}


	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getProgressType() {
		return progressType;
	}

	public void setProgressType(String progressType) {
		this.progressType = progressType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	
}
