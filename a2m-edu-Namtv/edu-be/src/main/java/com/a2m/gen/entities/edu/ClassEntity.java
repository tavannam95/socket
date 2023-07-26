package com.a2m.gen.entities.edu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;
import com.a2m.gen.models.edu.ClassModel;

@Entity
@Table(name = "EAM_CLASS")
public class ClassEntity extends DatabaseCommonModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLASS_ID")
	private Long classId;
	
	@Column(name = "CLASS_NM")
	private String className;
	
	@Column(name = "CLASS_TYPE")
	private String classType;
	
	@Column(name = "STATUS")
	private Boolean classStatus;
	
	@Column(name = "ISFINISH")
	private Boolean isFinish;

	public ClassEntity(ClassModel model) {
		this.classId = model.getKey();
		this.className = model.getClassName();
		this.classType = model.getClassType();
		this.classStatus = model.getClassStatus();
	}

	public ClassEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Boolean getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Boolean isFinish) {
		this.isFinish = isFinish;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public Boolean getClassStatus() {
		return classStatus;
	}

	public void setClassStatus(Boolean classStatus) {
		this.classStatus = classStatus;
	}

	
	
	
	
}
