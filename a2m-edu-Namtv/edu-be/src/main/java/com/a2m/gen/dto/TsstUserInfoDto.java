package com.a2m.gen.dto;

import java.util.Date;

public class TsstUserInfoDto {
	
	private Long userInfoId;

	private String fullName;

	private Date dob;

	private String email;
	
	
	private String cellPhone;
	
	
	private String address;
	

	private String gender;
	

	private String imgPath;
	
	
//	private String emailVerifyKey;
	

	private String twoFAEnable;
	

//	private String twoFAKey;


	public TsstUserInfoDto() {

	}
	
	public String getTwoFAEnable() {
		return twoFAEnable;
	}



	public void setTwoFAEnable(String twoFAEnable) {
		this.twoFAEnable = twoFAEnable;
	}



//	public String getTwoFAKey() {
//		return twoFAKey;
//	}
//
//
//
//	public void setTwoFAKey(String twoFAKey) {
//		this.twoFAKey = twoFAKey;
//	}

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


	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCellPhone() {
		return cellPhone;
	}


	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getImgPath() {
		return imgPath;
	}


	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}


//	public String getEmailVerifyKey() {
//		return emailVerifyKey;
//	}
//
//
//	public void setEmailVerifyKey(String emailVerifyKey) {
//		this.emailVerifyKey = emailVerifyKey;
//	}
}
