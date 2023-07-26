package com.a2m.gen.models.edu;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.AemTeacherEntity;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author phongnc
 */
public class TeacherModel extends ParamBaseModel{
    private String fullName;

    private Date dob;

    private String email;

    private String cellPhone;

    private String address;

    private Boolean gender;

    private String imgPath;

    private String emailVerifyKey;

    private Boolean twoFAEnable;

    private String twoFAKey;
    
    private String organization;

    private String position;
    
    private String UserUid;

	public TeacherModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public TeacherModel(AemTeacherEntity entity ) {
		super();
		this.key = entity.getTeacherInfoId();
		this.fullName = entity.getFullName();
		this.dob = entity.getDob();
		this.email = entity.getEmail();
		this.cellPhone = entity.getCellPhone();
		this.address = entity.getAddress();
		this.gender = entity.getGender();
		this.imgPath = entity.getImgPath();
		this.emailVerifyKey = entity.getEmailVerifyKey();
		this.twoFAEnable = entity.getTwoFAEnable();
		this.twoFAKey = entity.getTwoFAKey();
		this.organization = entity.getOrganization();
		this.position = entity.getPosition();
		this.deleteFlag = entity.getDeleteFlag();
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

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getEmailVerifyKey() {
		return emailVerifyKey;
	}

	public void setEmailVerifyKey(String emailVerifyKey) {
		this.emailVerifyKey = emailVerifyKey;
	}

	public Boolean getTwoFAEnable() {
		return twoFAEnable;
	}

	public void setTwoFAEnable(Boolean twoFAEnable) {
		this.twoFAEnable = twoFAEnable;
	}

	public String getTwoFAKey() {
		return twoFAKey;
	}

	public void setTwoFAKey(String twoFAKey) {
		this.twoFAKey = twoFAKey;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

    public String getUserUid() {
        return UserUid;
    }

    public void setUserUid(String userUid) {
        UserUid = userUid;
    }

	
}
