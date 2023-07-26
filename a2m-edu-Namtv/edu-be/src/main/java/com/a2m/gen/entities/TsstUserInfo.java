package com.a2m.gen.entities;

import java.util.Date;

import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TSST_USER_INFO")
public class TsstUserInfo {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_INFO_ID", nullable = false)
    private Long userInfoId;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "DOB")
    private Date dob;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CELL_PHONE", length = 50)
    private String cellPhone;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "GENDER")
    private Boolean gender;

    @Column(name = "IMG_PATH")
    private String imgPath;

    @Column(name = "EMAIL_VERIFY_KEY")
    private String emailVerifyKey;

    @Column(name = "2FA_ENABLE")
    private Boolean twoFAEnable;

    @Column(name = "2FA_KEY")
    private String twoFAKey;
    
    @Column(name = "ORGANIZATION")
    private String organization;

    @Column(name = "POSITION")
    private String position;
    
    @JsonIgnoreProperties(value = "tsstUserInfo")
    @OneToOne(cascade =  CascadeType.ALL,mappedBy = "tsstUserInfo")
    @Transient
    private TsstUser tsstUser;
    
   
    public TsstUserInfo() {

	}

	public TsstUser getTsstUser() {
		return tsstUser;
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

	public void setTsstUser(TsstUser tsstUser) {
		this.tsstUser = tsstUser;
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

	public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getEmailVerifyKey() {
        return emailVerifyKey;
    }

    public void setEmailVerifyKey(String emailVerifyKey) {
        this.emailVerifyKey = emailVerifyKey;
    }

}
