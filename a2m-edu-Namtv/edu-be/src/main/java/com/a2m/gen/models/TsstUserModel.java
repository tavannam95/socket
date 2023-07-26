package com.a2m.gen.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemCouSbjChapter;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author doanhq
 */
public class TsstUserModel extends ParamBaseModel{
	
	private String userUid;

	private String userId;

	private String userType;

	private Long UserInfoId;

	public TsstUserModel() {
		super();
	}
	
	public TsstUserModel(TsstUser db) {
		this.userUid = db.getUserUid();
		this.userId = db.getUserId();
		this.userType = db.getUserType();
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getUserInfoId() {
		return UserInfoId;
	}

	public void setUserInfoId(Long userInfoId) {
		UserInfoId = userInfoId;
	}

}
