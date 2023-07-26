package com.a2m.gen.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemDoUserMapEntity;
import com.a2m.gen.entities.edu.EamStudentInfo;
import com.a2m.gen.entities.edu.EamTeacherInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TSST_USER")
public class TsstUser {

	@Id
	@Column(name = "USER_UID")
	public String userUid;

	@Column(name = "USER_ID", nullable = false, unique = true)
	private String userId;

//	@Column(name = "USER_INFO_ID")
//	private long userInfoId;

	@Column(name = "PWD", nullable = false)
	private String password;

	@Column(name = "CREATED_DATE")
	public Date createdDate;

	@Column(name = "STATUS")
	private boolean status;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Transient
	private transient Boolean isChangePassword;

	@Transient
	private String newPassword;
	
	@Transient
	private transient String listClassChecked;
	
	@Transient
	private transient String listCourseChecked;

//	@Column(name = "IS_FIRST_LOGIN", nullable=true )
//	private boolean isFirstLogin;
	
	@Column(name = "USER_TYPE")
	private String userType;	

	@JsonIgnoreProperties(value = "tsstUser")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_INFO_ID", referencedColumnName = "USER_INFO_ID", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private TsstUserInfo tsstUserInfo;

	@JsonIgnoreProperties(value = "tsstUser")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_INFO_ID", referencedColumnName = "STUDENT_INFO_ID", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private EamStudentInfo eamStudentInfo;

	@JsonIgnoreProperties(value = "tsstUser")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_INFO_ID", referencedColumnName = "TEACHER_INFO_ID", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private EamTeacherInfo eamTeacherInfo;

	@JsonIgnoreProperties(value = "users")
	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private List<TsstRole> roles;
	
	@JsonIgnore
	@OneToMany(mappedBy = "tsstUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemCourseSubject> listSubject = new ArrayList<AemCourseSubject>();

	@OneToMany(mappedBy = "quizEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemDoUserMapEntity> listViewDoc = new ArrayList<AemDoUserMapEntity>();

	public EamStudentInfo getEamStudentInfo() {
		return eamStudentInfo;
	}

	public void setEamStudentInfo(EamStudentInfo eamStudentInfo) {
		this.eamStudentInfo = eamStudentInfo;
	}

	public TsstUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public String getListCourseChecked() {
		return listCourseChecked;
	}

	public void setListCourseChecked(String listCourseChecked) {
		this.listCourseChecked = listCourseChecked;
	}

	public List<TsstRole> getRoles() {
		return roles;
	}

	public void setRoles(List<TsstRole> roles) {
		this.roles = roles;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public TsstUserInfo getTsstUserInfo() {
		return tsstUserInfo;
	}

	public void setTsstUserInfo(TsstUserInfo tsstUserInfo) {
		this.tsstUserInfo = tsstUserInfo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Boolean getIsChangePassword() {
		return isChangePassword;
	}

	public void setIsChangePassword(Boolean isChangePassword) {
		this.isChangePassword = isChangePassword;
	}

    public String getListClassChecked() {
        return listClassChecked;
    }

    public void setListClassChecked(String listClassChecked) {
        this.listClassChecked = listClassChecked;
    }

    public EamTeacherInfo getEamTeacherInfo() {
        return eamTeacherInfo;
    }

    public void setEamTeacherInfo(EamTeacherInfo eamTeacherInfo) {
        this.eamTeacherInfo = eamTeacherInfo;
    }

	public List<AemCourseSubject> getListSubject() {
		return listSubject;
	}

	public void setListSubject(List<AemCourseSubject> listSubject) {
		this.listSubject = listSubject;
	}

	public List<AemDoUserMapEntity> getListViewDoc() {
		return listViewDoc;
	}

	public void setListViewDoc(List<AemDoUserMapEntity> listViewDoc) {
		this.listViewDoc = listViewDoc;
	}
	
//	public boolean isFirstLogin() {
//		return isFirstLogin;
//	}
//
//	public void setFirstLogin(boolean isFirstLogin) {
//		this.isFirstLogin = isFirstLogin;
//	}

}
