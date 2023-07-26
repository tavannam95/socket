package com.a2m.gen.entities.edu;

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
@Table(name = "EAM_CLASS_TEACHER_MAP")
public class AemClassTeacherMap extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TABLE_ID")
	private Long tableId;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "CLASS_ID")
	private AemClass aemClassT;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "TEACHER_INFO_ID")
	private AemTeacherEntity eamTeacher;

	public AemClassTeacherMap() {
		super();
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public AemClass getAemClassT() {
		return aemClassT;
	}

	public void setAemClassT(AemClass aemClassT) {
		this.aemClassT = aemClassT;
	}

	public AemTeacherEntity getEamTeacher() {
		return eamTeacher;
	}

	public void setEamTeacher(AemTeacherEntity eamTeacher) {
		this.eamTeacher = eamTeacher;
	}

}
