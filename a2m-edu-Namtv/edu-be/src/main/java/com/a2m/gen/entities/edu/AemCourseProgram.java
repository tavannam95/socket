package com.a2m.gen.entities.edu;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;

@Entity
@Table(name = "EAM_COURSE_PROGRAM")
public class AemCourseProgram extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COURSE_PROGRAM_ID")
	private Long courseProgramId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "COURSE_ID")
	private AemCourse courseEntity;
	
    @Column(name = "TITLE")
    private String title;
    
    @Column(name = "SHORT_DISCRIPTION")
    private String shortDiscription;
    
    @Column(name = "LECTURE")
    private String lecture;


	public AemCourseProgram() {
		super();
	}
	
	public AemCourseProgram(Long courseProgramId, AemCourse courseEntity, String title, String shortDiscription,
			String lecture) {
		super();
		this.courseProgramId = courseProgramId;
		this.courseEntity = courseEntity;
		this.title = title;
		this.shortDiscription = shortDiscription;
		this.lecture = lecture;
	}

	public String getLecture() {
		return lecture;
	}

	public void setLecture(String lecture) {
		this.lecture = lecture;
	}

	public Long getCourseProgramId() {
		return courseProgramId;
	}


	public void setCourseProgramId(Long courseProgramId) {
		this.courseProgramId = courseProgramId;
	}


	public AemCourse getCourseEntity() {
		return courseEntity;
	}


	public void setCourseEntity(AemCourse courseEntity) {
		this.courseEntity = courseEntity;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getShortDiscription() {
		return shortDiscription;
	}


	public void setShortDiscription(String shortDiscription) {
		this.shortDiscription = shortDiscription;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	 

	

}
