package com.a2m.gen.models.edu;

import java.util.List;

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.Equipment;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author phongnc
 */
public class ClassStudentMapModel extends ParamBaseModel{

//	
//	private AemClass aemClass;
//	
//	private AemStudentEntity eamStudent;
	
	private StudentModel studentModel;
	
	private ClassModel classModel;


	public ClassStudentMapModel() {
        super();
        // TODO Auto-generated constructor stub
    }
	
    public ClassStudentMapModel(AemClassStudentMap db) {
        AemStudentEntity studentEndtity = db.getEamStudent();
        this.studentModel = new StudentModel(studentEndtity, true);
        AemClass aemClass = db.getAemClass();
        this.classModel = new ClassModel(aemClass);
    }
    
//	public ClassStudentMapModel(Long tableId, AemClass aemClass, AemStudentEntity eamStudent) {
//		super();
//		this.aemClass = aemClass;
//		this.eamStudent = eamStudent;
//	}	

//
//	public AemClass getAemClass() {
//		return aemClass;
//	}
//
//	public void setAemClass(AemClass aemClass) {
//		this.aemClass = aemClass;
//	}
//
//	public AemStudentEntity getEamStudent() {
//		return eamStudent;
//	}
//
//	public void setEamStudent(AemStudentEntity eamStudent) {
//		this.eamStudent = eamStudent;
//	}

    public StudentModel getStudentModel() {
        return studentModel;
    }

    public void setStudentModel(StudentModel studentModel) {
        this.studentModel = studentModel;
    }

	public ClassModel getClassModel() {
		return classModel;
	}

	public void setClassModel(ClassModel classModel) {
		this.classModel = classModel;
	}
	
	
}
