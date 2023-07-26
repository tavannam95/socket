package com.a2m.gen.models;

import java.util.Map;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.TsstUserInfo;
import com.a2m.gen.entities.edu.EamStudentInfo;
import com.a2m.gen.entities.edu.EamTeacherInfo;

public class TsstUserInfoModel extends ParamBaseModel {
	
	private String userUid;
	private String userId;
	private String userType;
	private Long userInfoId;
	private String fullName;
	private String address;
	private String phone;
	private String email;
	private String imgPath;
	
	
	public TsstUserInfoModel() {
		super();
	}
	public TsstUserInfoModel(TsstUser user) {
		String userType = user.getUserType();

		this.userId = user.getUserId();
		this.userUid = user.getUserUid();
		this.userType = userType;
		
		
		if( userType.equals("STU") ) {
			EamStudentInfo eamStudentInfo = user.getEamStudentInfo();
			this.userInfoId = eamStudentInfo.getStudentInfoId();
			this.fullName = eamStudentInfo.getFullName();
			this.imgPath = eamStudentInfo.getImgPath();
		}else if( userType.equals("TEA") ) {
			EamTeacherInfo eamTeacherInfo = user.getEamTeacherInfo();
			this.userInfoId = eamTeacherInfo.getTeacherInfoId();
			this.fullName = eamTeacherInfo.getFullName();
			this.imgPath = eamTeacherInfo.getImgPath();
		}else {
			TsstUserInfo tsstUserInfo = user.getTsstUserInfo();
			this.userInfoId = tsstUserInfo.getUserInfoId();
			this.fullName = tsstUserInfo.getFullName();
			this.imgPath = tsstUserInfo.getImgPath();
		}
	}
	public TsstUserInfoModel(TsstUserInfo db) {
	    this.fullName = db.getFullName();
	    this.address = db.getAddress();
	    this.phone = db.getCellPhone();
	    this.email = db.getEmail();
	}
	public TsstUserInfoModel(String fullName, String imgPath) {
		this.fullName = fullName;
		this.imgPath = imgPath;
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
		return userInfoId;
	}
	public void setUserInfoId(Long userInfoId) {
		this.userInfoId = userInfoId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }	
	
}
