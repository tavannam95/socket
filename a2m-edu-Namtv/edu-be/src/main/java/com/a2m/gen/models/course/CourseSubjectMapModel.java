package com.a2m.gen.models.course;

import java.util.List;

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.Equipment;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;

/**
 *
 * @author phongnc
 */
public class CourseSubjectMapModel extends ParamBaseModel{

	
	private AemCourseSubject aemCourseSubject;
	
	private AemCourse aemCourse;
	
	private SubjectModel subjectModel;
    
    private Edu0102RequestModel edu0102RequestModel;
    
	public CourseSubjectMapModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CourseSubjectMapModel(Long tableId, AemCourseSubject aemCourseSubject, AemCourse aemCourse) {
		super();
		this.aemCourseSubject = aemCourseSubject;
		this.aemCourse = aemCourse;
	}
    
    public AemCourseSubject getAemCourseSubject() {
        return aemCourseSubject;
    }

    public void setAemCourseSubject(AemCourseSubject aemCourseSubject) {
        this.aemCourseSubject = aemCourseSubject;
    }

    public AemCourse getAemCourse() {
        return aemCourse;
    }

    public void setAemCourse(AemCourse aemCourse) {
        this.aemCourse = aemCourse;
    }

    public SubjectModel getSubjectModel() {
        return subjectModel;
    }

    public void setSubjectModel(SubjectModel subjectModel) {
        this.subjectModel = subjectModel;
    }

    public Edu0102RequestModel getEdu0102RequestModel() {
        return edu0102RequestModel;
    }

    public void setEdu0102RequestModel(Edu0102RequestModel edu0102RequestModel) {
        this.edu0102RequestModel = edu0102RequestModel;
    }	

}
