package com.a2m.gen.models.edu;

import java.util.List;

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.AemTeacherEntity;
import com.a2m.gen.entities.edu.Equipment;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author phongnc
 */
public class ClassTeacherMapModel extends ParamBaseModel{

	
	private AemClass aemClass;
	
	private AemTeacherEntity eamTeacherEntity;

	public ClassTeacherMapModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassTeacherMapModel(Long tableId, AemClass aemClass, AemTeacherEntity aemTeacher) {
		super();
		this.aemClass = aemClass;
		this.eamTeacherEntity = aemTeacher;
	}


	public AemClass getAemClass() {
		return aemClass;
	}

	public void setAemClass(AemClass aemClass) {
		this.aemClass = aemClass;
	}

	public AemTeacherEntity getEamTeacherEntity() {
		return eamTeacherEntity;
	}

	public void setEamTeacherEntity(AemTeacherEntity eamTeacherEntity) {
		this.eamTeacherEntity = eamTeacherEntity;
	}


	
	
}
