package com.a2m.gen.models.common;

import com.a2m.gen.entities.common.ApproveEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.utils.ModelUtil;

public class ApproveModel extends ParamBaseModel{
	
	private String approvalStatus;
	private Long refId;
	private String refTable;
	private String documentUrl;
	private Boolean viewedAprHistory;
	private String empAprUid;
	private String feedBack;
	private Boolean noti;
	public ApproveModel() {
		this.approvalStatus = "";
		this.refId = Long.valueOf(0);
		this.refTable = "";
		this.documentUrl = "";
		this.viewedAprHistory = false;
		this.empAprUid = "";
	}
	
	public ApproveModel(ApproveEntity approve) {
		this.key = approve.getApprovalHistoryId();
		this.approvalStatus = approve.getApprovalStatus();
		this.refId = approve.getRefId();
		this.refTable = approve.getRefTable();
		this.documentUrl = approve.getDocumentUrl();
		this.viewedAprHistory = approve.getViewedAprHistory();
		this.empAprUid = approve.getEmpAprUid();
		this.feedBack = approve.getFeedback();
		ModelUtil.setCommonFields(approve, this);
	}
	
	
	
	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

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
	public String getDocumentUrl() {
		return documentUrl;
	}
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public Boolean getNoti() {
		return noti;
	}

	public void setNoti(Boolean noti) {
		this.noti = noti;
	}

	
}
