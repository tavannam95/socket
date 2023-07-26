package com.a2m.gen.entities.edu;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.a2m.gen.entities.TsstUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "EAM_STUDENT_INFO")
public class EamStudentInfo {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_INFO_ID", nullable = false)
    private Long studentInfoId;

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

    @Column(name = "DELETE_FLAG")
    private Boolean deleteFlag;
    
    @Column(name = "TYPICAL_FLAG")
    private Boolean typicalFlag;

	@Column(name = "IDEA")
	private String idea;
	
	@Column(name = "STUDENT_CODE")
	private String studentCode;
    
    

    @JsonIgnoreProperties(value = "eamStudentInfo")
    @OneToOne(cascade =  CascadeType.ALL,mappedBy = "eamStudentInfo")
    @Transient
    private TsstUser tsstUser;
    
//    @Transient
//    private List<AemStudentCourseMap> studentCourseMaps ;

    public EamStudentInfo() {

	}

//	public List<AemStudentCourseMap> getStudentCourseMaps() {
//		return studentCourseMaps;
//	}
//
//	public void setStudentCourseMaps(List<AemStudentCourseMap> studentCourseMaps) {
//		this.studentCourseMaps = studentCourseMaps;
//	}

	public String getStudentCode() {
		return studentCode;
	}


	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
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
	
	public Boolean getTypicalFlag() {
		return typicalFlag;
	}

	public void setTypicalFlag(Boolean typicalFlag) {
		this.typicalFlag = typicalFlag;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public void setTsstUser(TsstUser tsstUser) {
		this.tsstUser = tsstUser;
	}

    public Long getStudentInfoId() {
		return studentInfoId;
	}

	public void setStudentInfoId(Long studentInfoId) {
		this.studentInfoId = studentInfoId;
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

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
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
