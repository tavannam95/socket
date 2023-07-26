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
@Table(name = "EAM_COURSE_SUBJECT_MAP")
public class AemCourseSubjectMap extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TABLE_ID")
	private Long tableId;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "COURSE_ID")
	private AemCourse aemCourse;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "SUBJECT_ID")
	private AemCourseSubject aemCouSubject;

	public AemCourseSubjectMap() {
		super();
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public AemCourse getAemCourse() {
		return aemCourse;
	}

	public void setAemCourse(AemCourse aemCourse) {
		this.aemCourse = aemCourse;
	}

	public AemCourseSubject getAemCouSubject() {
		return aemCouSubject;
	}

	public void setAemCouSubject(AemCourseSubject aemCouSubject) {
		this.aemCouSubject = aemCouSubject;
	}


}
