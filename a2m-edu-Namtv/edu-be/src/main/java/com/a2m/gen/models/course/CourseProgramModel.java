package com.a2m.gen.models.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.entities.edu.AemCouSbjChapter;
import com.a2m.gen.entities.edu.AemCourseInfo;
import com.a2m.gen.entities.edu.AemCourseProgram;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;

/**
 *
 * @author doanhq
 */
public class CourseProgramModel extends ParamBaseModel{
	
	
	private String title;
	
	private String shortDiscription;
	
	private String lecture;
	
	private Long courseId;
	
	private Edu0102RequestModel courseModel;
	
	public CourseProgramModel() {
		super();
	}

	public CourseProgramModel(AemCourseProgram aemCourseProgram) {
		super();
		this.title = aemCourseProgram.getTitle();
		this.shortDiscription = aemCourseProgram.getShortDiscription();
		this.lecture = aemCourseProgram.getLecture();
		this.courseModel = new Edu0102RequestModel(aemCourseProgram.getCourseEntity());
		this.key = aemCourseProgram.getCourseProgramId();
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

	public String getLecture() {
		return lecture;
	}

	public void setLecture(String lecture) {
		this.lecture = lecture;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Edu0102RequestModel getCourseModel() {
		return courseModel;
	}

	public void setCourseModel(Edu0102RequestModel courseModel) {
		this.courseModel = courseModel;
	}

	
	
	
	
}
