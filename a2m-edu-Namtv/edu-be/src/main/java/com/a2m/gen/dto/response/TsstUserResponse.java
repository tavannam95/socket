/*
 * Copyright (C) Hitachi Distribution Software Co., Ltd. 2018.
 */
package com.a2m.gen.dto.response;

import java.io.Serializable;
import java.util.Date;

public class TsstUserResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userUid;
    private String userId;
    private String pwd;
	private String userInfoId;
    private String userType;
    private String useYn;
//    private Boolean isFirstLogin;
    private String resetPwdToken;
    private Date tokenExpired;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;

    public TsstUserResponse() {

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

//    public void setIsFirstLogin(Boolean isFirstLogin) {
//        this.isFirstLogin = isFirstLogin;
//    }
//
//    public Boolean getIsFirstLogin() {
//        return this.isFirstLogin;
//    }

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