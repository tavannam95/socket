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
@Table(name = "EAM_STUDENT_COURSE_MAP")
public class AemStudentCourseMap extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TABLE_ID")
	private Long tableId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "COURSE_ID")
	private AemCourse aemCourseS;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "STUDENT_INFO_ID")
	private AemStudentEntity aemStudentC;
	
	
	public AemStudentCourseMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}


	public AemCourse getAemCourseS() {
		return aemCourseS;
	}

	public void setAemCourseS(AemCourse aemCourseS) {
		this.aemCourseS = aemCourseS;
	}


	public AemStudentEntity getAemStudentC() {
		return aemStudentC;
	}

	public void setAemStudentC(AemStudentEntity aemStudentC) {
		this.aemStudentC = aemStudentC;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
