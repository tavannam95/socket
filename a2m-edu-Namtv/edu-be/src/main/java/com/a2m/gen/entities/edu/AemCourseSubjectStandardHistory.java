package com.a2m.gen.entities.edu;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;
import com.a2m.gen.models.course.SubjectStandardModel;

@Entity
@Table(name = "EAM_COURSE_SUBJECT_STANDARD_HISTORY")
public class AemCourseSubjectStandardHistory extends DatabaseCommonModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STANDARD_HISTORY_ID")
    private Long standardHistoryId;
    
    @Column(name = "IS_APPROVAL")
    private Boolean isApproval;
    
	@Column(name = "DOCUMENT_STATUS")
	private String documentStatus;
	
	@Column(name = "STAND_ID")
    private Long standId;

	@Column(name = "SUBJECT_ID")
    private Long subjectId;

    @Column(name = "STAND_CODE")
    private String standCode;

    @Column(name = "STAND_CONTENT")
    private String standContent;

    @Column(name = "STAND_TYPE")
    private String standType;

    @Column(name = "STATUS")
    private Boolean status;

	public Long getStandardHistoryId() {
		return standardHistoryId;
	}

	public void setStandardHistoryId(Long standardHistoryId) {
		this.standardHistoryId = standardHistoryId;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public Long getStandId() {
		return standId;
	}

	public void setStandId(Long standId) {
		this.standId = standId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getStandCode() {
		return standCode;
	}

	public void setStandCode(String standCode) {
		this.standCode = standCode;
	}

	public String getStandContent() {
		return standContent;
	}

	public void setStandContent(String standContent) {
		this.standContent = standContent;
	}

	public String getStandType() {
		return standType;
	}

	public void setStandType(String standType) {
		this.standType = standType;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getIsApproval() {
		return isApproval;
	}

	public void setIsApproval(Boolean isApproval) {
		this.isApproval = isApproval;
	}
}
