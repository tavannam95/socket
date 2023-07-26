package com.a2m.gen.dto;

import java.util.Date;

public class TsstUserDto {
	private String userUid;
	private String userId;
	private boolean status;
	private Date createdDate;
	private TsstUserInfoDto tsstUserInfo;
    private TeacherInfoDto eamTeacherInfo;
	public TsstUserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUserUid() {
		return userUid;
	}
	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public TsstUserInfoDto getTsstUserInfo() {
		return tsstUserInfo;
	}
	public void setTsstUserInfo(TsstUserInfoDto tsstUserInfo) {
		this.tsstUserInfo = tsstUserInfo;
	}
	public TeacherInfoDto getEamTeacherInfo() {
		return eamTeacherInfo;
	}
	public void setEamTeacherInfo(TeacherInfoDto eamTeacherInfo) {
		this.eamTeacherInfo = eamTeacherInfo;
	}
    
}