package com.a2m.gen.models.edu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.course.CourseInfoModel;
import com.a2m.gen.models.course.CourseProgramModel;
import com.a2m.gen.models.course.SubjectModel;

/**
 *
 * @author doanhq
 */
public class Edu010202RequestModel extends ParamBaseModel {

	private String courseId;
	
	private List<CourseProgramModel> courseProgram;
	
	private CourseInfoModel courseInfo ;

	public Edu010202RequestModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Edu010202RequestModel(String courseId, List<CourseProgramModel> courseProgram,
			CourseInfoModel courseInfo) {
		super();
		this.courseId = courseId;
		this.courseProgram = courseProgram;
		this.courseInfo = courseInfo;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public List<CourseProgramModel> getCourseProgram() {
		return courseProgram;
	}

	public void setCourseProgram(List<CourseProgramModel> courseProgram) {
		this.courseProgram = courseProgram;
	}

	public CourseInfoModel getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(CourseInfoModel courseInfo) {
		this.courseInfo = courseInfo;
	}
	
	




	

	
	

}
