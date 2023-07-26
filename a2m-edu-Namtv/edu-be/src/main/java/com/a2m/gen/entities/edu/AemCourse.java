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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;

@Entity
@Table(name = "EAM_COURSE")
public class AemCourse extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "COURSE_ID")
	private Long courseId;

	@Column(name = "COURSE_NM")
	private String courseNm;

	@Column(name = "COURSE_CODE")
	private String courseCode;

	@Column(name = "START_DT")
	private Date courseStartDt;

	@Column(name = "END_DT")
	private Date courseEndDt;

	@Column(name = "COURSE_CONTENT")
	private String courseContent;

	@Column(name = "COURSE_GOAL")
	private String courseGoal;

	@Column(name = "STATUS")
	private Boolean status;

    @Column(name = "DISPLAY_STATUS")
    private Boolean displayStatus;

	@Column(name = "ISCOMINGSOON")
	private Boolean isComingSoon;

	@Column(name = "IMG_PATH")
	private String imgPath;

	@OneToOne(mappedBy = "aemCourse",fetch = FetchType.LAZY)
	private AemCourseInfo aemCourseInfo;

	@OneToMany(mappedBy = "aemCourse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemCourseSubjectMap> listOfCourseSubjectMap = new ArrayList<AemCourseSubjectMap>();

	@OneToMany(mappedBy = "courseEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemCourseProgram> listCourseInfo = new ArrayList<AemCourseProgram>();
	
	@OneToMany(mappedBy = "eventCourse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemEvent> listEventCourse = new ArrayList<AemEvent>();

	@OneToMany(mappedBy = "candiDateCourse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemCandidate> listCandidateCourse = new ArrayList<AemCandidate>();

//    @OneToMany(mappedBy = "courseEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<AemCourseInfo> listCourseInfo = new ArrayList<AemCourseInfo>();
	
//	@OneToMany(mappedBy = "aemCourseS", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private	List<AemStudentCourseMap> listStudentCourseMap = new ArrayList<AemStudentCourseMap>();
	
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemClass> listClass = new ArrayList<AemClass>();

	public AemCourse() {
		super();
	}

	public Boolean getIsComingSoon() {
		return isComingSoon;
	}

	public void setIsComingSoon(Boolean isComingSoon) {
		this.isComingSoon = isComingSoon;
	}

//	public List<AemStudentCourseMap> getListStudentCourseMap() {
//		return listStudentCourseMap;
//	}
//
//	public void setListStudentCourseMap(List<AemStudentCourseMap> listStudentCourseMap) {
//		this.listStudentCourseMap = listStudentCourseMap;
//	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public List<AemCourseProgram> getListCourseInfo() {
		return listCourseInfo;
	}

	public void setListCourseInfo(List<AemCourseProgram> listCourseInfo) {
		this.listCourseInfo = listCourseInfo;
	}
//
//    public List<AemCourseInfo> getListCourseInfo() {
//        return listCourseInfo;
//    }
//
//    public void setListCourseInfo(List<AemCourseInfo> listCourseInfo) {
//        this.listCourseInfo = listCourseInfo;
//    }

	public Long getCourseId() {
		return courseId;
	}

    public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseNm() {
		return courseNm;
	}

	public void setCourseNm(String courseNm) {
		this.courseNm = courseNm;
	}

	public String getCourseContent() {
		return courseContent;
	}

	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}

	public String getCourseGoal() {
		return courseGoal;
	}

	public void setCourseGoal(String courseGoal) {
		this.courseGoal = courseGoal;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(Boolean displayStatus) {
        this.displayStatus = displayStatus;
    }

    public Date getCourseStartDt() {
		return courseStartDt;
	}

	public void setCourseStartDt(Date courseStartDt) {
		this.courseStartDt = courseStartDt;
	}

	public Date getCourseEndDt() {
		return courseEndDt;
	}

	public void setCourseEndDt(Date courseEndDt) {
		this.courseEndDt = courseEndDt;
	}

	public List<AemCourseSubjectMap> getListOfCourseSubjectMap() {
		return listOfCourseSubjectMap;
	}

	public void setListOfCourseSubjectMap(List<AemCourseSubjectMap> listOfCourseSubjectMap) {
		this.listOfCourseSubjectMap = listOfCourseSubjectMap;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public AemCourseInfo getAemCourseInfo() {
		return aemCourseInfo;
	}

	public void setAemCourseInfo(AemCourseInfo aemCourseInfo) {
		this.aemCourseInfo = aemCourseInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<AemClass> getListClass() {
		return listClass;
	}

	public void setListClass(List<AemClass> listClass) {
		this.listClass = listClass;
	}

	public List<AemEvent> getListEventCourse() {
		return listEventCourse;
	}

	public void setListEventCourse(List<AemEvent> listEventCourse) {
		this.listEventCourse = listEventCourse;
	}

    
}
