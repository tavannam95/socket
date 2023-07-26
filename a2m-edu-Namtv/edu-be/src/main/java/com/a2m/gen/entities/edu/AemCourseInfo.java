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
@Table(name = "EAM_COURSE_INFO")
public class AemCourseInfo extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "COURSE_INFO_ID")
	private Long courseInfoId;
	
	@Column(name = "TIME")
	private Integer time;

	@Column(name = "COURSE_DISCRIPTION")
	private String courseDisciption;
	
	@Column(name = "COURSE_GOAL")
	private String courseGoal;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COURSE_ID", referencedColumnName = "COURSE_ID")
	private AemCourse aemCourse;

	public AemCourseInfo() {
		super();
	}

	public AemCourseInfo(Long courseInfoId, String courseDisciption, String courseGoal, AemCourse aemCourse) {
		super();
		this.courseInfoId = courseInfoId;
		this.courseDisciption = courseDisciption;
		this.courseGoal = courseGoal;
		this.aemCourse = aemCourse;
	}

	
	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Long getCourseInfoId() {
		return courseInfoId;
	}

	public void setCourseInfoId(Long courseInfoId) {
		this.courseInfoId = courseInfoId;
	}

	public String getCourseDisciption() {
		return courseDisciption;
	}

	public void setCourseDisciption(String courseDisciption) {
		this.courseDisciption = courseDisciption;
	}

	public String getCourseGoal() {
		return courseGoal;
	}

	public void setCourseGoal(String courseGoal) {
		this.courseGoal = courseGoal;
	}

	public AemCourse getAemCourse() {
		return aemCourse;
	}

	public void setAemCourse(AemCourse aemCourse) {
		this.aemCourse = aemCourse;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		
	
}
