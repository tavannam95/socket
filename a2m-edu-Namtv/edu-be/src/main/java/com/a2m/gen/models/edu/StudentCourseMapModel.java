package com.a2m.gen.models.edu;

import java.util.List;

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemStudentCourseMap;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.Equipment;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author phongnc
 */
public class StudentCourseMapModel extends ParamBaseModel{

	
	private Edu0102RequestModel aemCourse;
	
	private StudentModel eamStudent;
	
	public StudentCourseMapModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentCourseMapModel(Edu0102RequestModel aemCourse, StudentModel eamStudent) {
		super();
		this.aemCourse = aemCourse;
		this.eamStudent = eamStudent;
	}

//	public StudentCourseMapModel(AemStudentCourseMap db) {
//	    this.aemCourse = new Edu0102RequestModel(db.getAemCourseS());
//	    this.eamStudent = new StudentModel(db.getAemStudentC());
//	}
//
//    public StudentCourseMapModel(AemStudentCourseMap db, boolean allStatus) {
//        this.aemCourse = new Edu0102RequestModel(db.getAemCourseS(), allStatus);
//        this.eamStudent = new StudentModel(db.getAemStudentC());
//    }

    public Edu0102RequestModel getAemCourse() {
        return aemCourse;
    }

    public void setAemCourse(Edu0102RequestModel aemCourse) {
        this.aemCourse = aemCourse;
    }

    public StudentModel getEamStudent() {
        return eamStudent;
    }

    public void setEamStudent(StudentModel eamStudent) {
        this.eamStudent = eamStudent;
    }
	


	
}
