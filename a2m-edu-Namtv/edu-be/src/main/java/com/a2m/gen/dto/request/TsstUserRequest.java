package com.a2m.gen.dto.request;

import java.util.Date;

import javax.validation.constraints.Size;

import com.a2m.gen.constants.ValidationCode;


public class TsstUserRequest {

	private String userUid;
	@Size(max = 20, message = ValidationCode.Size)
	private String userId;
	@Size(max = 255, message = ValidationCode.Size)
	private String pwd;
	
	

	private String userInfoId;
	@Size(max = 20, message = ValidationCode.Size)
	private String userType;
	@Size(max = 1, message = ValidationCode.Size)
	private String useYn;
//	private Boolean isFirstLogin;
	@Size(max = 50, message = ValidationCode.Size)
	private String resetPwdToken;
	private Date tokenExpired;
	@Size(max = 20, message = ValidationCode.Size)
	private String createdBy;
	private Date createdDate;
	@Size(max = 20, message = ValidationCode.Size)
	private String updatedBy;
	private Date updatedDate;

	private String newPwd;
	private String oldPwd;

	private String newPwdUpdate;
	private String oldPwdUpdate;

	private boolean changePwd;

//	    private TcdsEmpMstRequest tcdsEmpMstRequest; //Use update empMst;

	public TsstUserRequest() {

	    }

	public boolean isChangePwd() {
		return changePwd;
	}

	public void setChangePwd(boolean changePwd) {
		this.changePwd = changePwd;
	}

	public String getNewPwdUpdate() {
		return newPwdUpdate;
	}

	public void setNewPwdUpdate(String newPwdUpdate) {
		this.newPwdUpdate = newPwdUpdate;
	}

	public String getOldPwdUpdate() {
		return oldPwdUpdate;
	}

	public void setOldPwdUpdate(String oldPwdUpdate) {
		this.oldPwdUpdate = oldPwdUpdate;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getUserUid() {
		return this.userUid;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd() {
		return this.pwd;
	}

	public String getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(String userInfoId) {
		this.userInfoId = userInfoId;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getUseYn() {
		return this.useYn;
	}


	// --- DATABASE MAPPING : IS_FIRST_LOGIN (BIT)
//	public void setIsFirstLogin(Boolean isFirstLogin) {
//		this.isFirstLogin = isFirstLogin;
//	}
//
//	public Boolean getIsFirstLogin() {
//		return this.isFirstLogin;
//	}


	public void setResetPwdToken(String resetPwdToken) {
		this.resetPwdToken = resetPwdToken;
	}

	public String getResetPwdToken() {
		return this.resetPwdToken;
	}

	public void setTokenExpired(Date tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	public Date getTokenExpired() {
		return this.tokenExpired;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

}