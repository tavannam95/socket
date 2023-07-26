package com.a2m.gen.models.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.entities.edu.AemCouSbjChapter;
import com.a2m.gen.entities.edu.AemCourseInfo;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;

/**
 *
 * @author doanhq
 */
public class CourseInfoModel extends ParamBaseModel{
	
	
	private String courseDisciption;
	
	private String courseGoal;
	
	private Long courseId;
	
	private Integer time;
	
	private Edu0102RequestModel courseModel;
	
	public CourseInfoModel() {
		super();
	}

	public CourseInfoModel(AemCourseInfo aemCourseInfo ) {
		this.key = aemCourseInfo.getCourseInfoId();
		this.time = aemCourseInfo.getTime();
		this.courseDisciption = aemCourseInfo.getCourseDisciption();
		this.courseGoal = aemCourseInfo.getCourseGoal();
		this.insUid = aemCourseInfo.getInsUid();
//		this.courseModel = new Edu0102RequestModel(aemCourseInfo.getAemCourse());
	}



	public Long getCourseId() {
		return courseId;
	}



	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
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

	public Edu0102RequestModel getCourseModel() {
		return courseModel;
	}

	public void setCourseModel(Edu0102RequestModel courseModel) {
		this.courseModel = courseModel;
	}		
	
	
}
