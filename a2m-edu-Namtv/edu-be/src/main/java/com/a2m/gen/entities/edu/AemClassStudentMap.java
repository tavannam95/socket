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
@Table(name = "EAM_CLASS_STUDENT_MAP")
public class AemClassStudentMap extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TABLE_ID")
	private Long tableId;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "CLASS_ID",nullable = true)
	private AemClass aemClass;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "STUDENT_INFO_ID",nullable = true)
	private AemStudentEntity eamStudent;

	public AemClassStudentMap() {
		super();
	}

	public Long getTableId() {
		return tableId;
	}

	public AemClass getAemClass() {
		return aemClass;
	}

	public void setAemClass(AemClass aemClass) {
		this.aemClass = aemClass;
	}

	public AemStudentEntity getEamStudent() {
		return eamStudent;
	}

	public void setEamStudent(AemStudentEntity eamStudent) {
		this.eamStudent = eamStudent;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	
}
