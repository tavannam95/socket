package com.a2m.gen.entities.common;


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
@Table(name = "EAM_APPROVAL_HISTORY")
public class ApproveEntity extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "APPROVAL_HISTORY_ID")
	private Long approvalHistoryId;

	@Column(name = "APPROVAL_STATUS")
    private String approvalStatus;

	@Column(name = "REF_ID")
	private Long refId;

	@Column(name = "REF_TABLE")
	private String refTable;
	
	@Column(name = "DOCUMENT_URL")
	private String documentUrl;
	
	@Column(name = "FEEDBACK")
	private String feedback;

	@Column(name = "VIEWED_APR_HISTORY")
	private Boolean viewedAprHistory;
	
	@Column(name = "EMP_APR_UID")
	private String empAprUid;


	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}
	
	

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getRefTable() {
		return refTable;
	}

	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}

	public Boolean getViewedAprHistory() {
		return viewedAprHistory;
	}

	public void setViewedAprHistory(Boolean viewedAprHistory) {
		this.viewedAprHistory = viewedAprHistory;
	}

	public String getEmpAprUid() {
		return empAprUid;
	}

	public void setEmpAprUid(String empAprUid) {
		this.empAprUid = empAprUid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getApprovalHistoryId() {
		return approvalHistoryId;
	}

	public void setApprovalHistoryId(Long approvalHistoryId) {
		this.approvalHistoryId = approvalHistoryId;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
}
