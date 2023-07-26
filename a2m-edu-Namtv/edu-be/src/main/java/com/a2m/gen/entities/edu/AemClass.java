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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;
import com.a2m.gen.models.edu.ClassModel;

@Entity
@Table(name = "EAM_CLASS")
public class AemClass extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLASS_ID")
	private Long classId;

	@Column(name = "CLASS_NM")
	private String classNm;
	
	@Column(name = "CLASS_CODE")
	private String classCode;

	@Column(name = "CLASS_TYPE")
	private String classType;
	
	@Column(name = "START_DT")
	private Date startDate;
	
	@Column(name = "END_DT")
	private Date endDate;

	@Column(name = "STATUS")
	private Boolean status;
	
	@Column(name = "ISFINISH")
	private Boolean isFinish;

	@Column(name = "ISSTART")
	private Boolean isStart;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COURSE_ID")
	private AemCourse course;
	
	@OneToMany(mappedBy = "aemClassAnnou", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemClassAnnouncement> listClassAnnoucement = new ArrayList<AemClassAnnouncement>();

	@OneToMany(mappedBy = "aemClass", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemClassStudentMap> listOfClassStudentMap = new ArrayList<AemClassStudentMap>();
	
	@OneToMany(mappedBy = "aemClassT", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemClassTeacherMap> listOfClassTeacherMap = new ArrayList<AemClassTeacherMap>();

    @OneToMany(mappedBy = "classSchedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AemSchedule> listScheduleOfClass = new ArrayList<AemSchedule>();

	public AemClass() {
		super();
	}
	
	

	public AemClass(ClassModel classModel) {
		this.startDate = classModel.getStartDate();
		this.endDate = classModel.getEndDate();
		this.classCode = classModel.getClassCode();
		this.classId = classModel.getKey();
		this.classNm = classModel.getClassName();
		this.classType = classModel.getClassType();
		this.status = classModel.getClassStatus();
		this.isFinish = classModel.getIsFinish();
//		this.listOfClassStudentMap = classModel.getListOfClassStudentMap();
//		this.listOfClassTeacherMap = classModel.getListOfClassTeacherMap();
	}

	public Date getStartDate() {
		return startDate;
	}

	


	public Boolean getIsFinish() {
		return isFinish;
	}



	public void setIsFinish(Boolean isFinish) {
		this.isFinish = isFinish;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	

	public String getClassCode() {
		return classCode;
	}



	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassNm() {
		return classNm;
	}

	public void setClassNm(String classNm) {
		this.classNm = classNm;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<AemClassStudentMap> getListOfClassStudentMap() {
		return listOfClassStudentMap;
	}

	public void setListOfClassStudentMap(List<AemClassStudentMap> listOfClassStudentMap) {
		this.listOfClassStudentMap = listOfClassStudentMap;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<AemClassTeacherMap> getListOfClassTeacherMap() {
		return listOfClassTeacherMap;
	}

	public void setListOfClassTeacherMap(List<AemClassTeacherMap> listOfClassTeacherMap) {
		this.listOfClassTeacherMap = listOfClassTeacherMap;
	}



	public AemCourse getCourse() {
		return course;
	}



	public void setCourse(AemCourse course) {
		this.course = course;
	}



    public List<AemSchedule> getListScheduleOfClass() {
        return listScheduleOfClass;
    }



    public void setListScheduleOfClass(List<AemSchedule> listScheduleOfClass) {
        this.listScheduleOfClass = listScheduleOfClass;
    }



	public Boolean getIsStart() {
		return isStart;
	}



	public void setIsStart(Boolean isStart) {
		this.isStart = isStart;
	}
	
	
	

}
