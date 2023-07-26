package com.a2m.gen.entities.edu;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;

@Entity
@Table(name = "EAM_COURSE_SUBJECT_HISTORY")
public class AemCourseSubjectHistory extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SUBJECT_HISTORY_ID")
	private Long subjectHistoryId;
	
	@Column(name = "DOCUMENT_STATUS")
	private String documentStatus;


	@Column(name = "SUBJECT_ID")
	private Long subjectId;

	@Column(name = "TEACHER_INFO_ID")
	private Long teacherInfoId;

	@Column(name = "USER_UID")
	private String userUid;

	@Column(name = "SUBJECT_NM")
	private String subjectNm;

	@Column(name = "SUBJECT_CODE")
	private String subjectCode;

	@Column(name = "SUBJECT_CONTENT")
	private String subjectContent;

	@Column(name = "SUBJECT_GOAL")
	private String subjectGoal;

	@Column(name = "APPROVAL_HISTORY_ID")
	private Long approvalHistoryId;

	@Column(name = "ORD_NO")
	private Long ordNo;

	@Column(name = "START_DT")
	private Date startDt;

	@Column(name = "END_DT")
	private Date endDt;

	@Column(name = "STATUS")
	private Boolean status;
	
	@Column(name = "EDIT_SUBJECT_FORM")
	private Boolean editSubjectForm;
	
	@Column(name = "EDIT_LIST_CHAPTER")
	private Boolean editListChapter;
	
	@Column(name = "EDIT_STAND")
	private Boolean editStand;

	public AemCourseSubjectHistory() {
		super();
	}

	public Long getSubjectHistoryId() {
		return subjectHistoryId;
	}

	public void setSubjectHistoryId(Long subjectHistoryId) {
		this.subjectHistoryId = subjectHistoryId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getTeacherInfoId() {
		return teacherInfoId;
	}

	public void setTeacherInfoId(Long teacherInfoId) {
		this.teacherInfoId = teacherInfoId;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getSubjectNm() {
		return subjectNm;
	}

	public void setSubjectNm(String subjectNm) {
		this.subjectNm = subjectNm;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectContent() {
		return subjectContent;
	}

	public void setSubjectContent(String subjectContent) {
		this.subjectContent = subjectContent;
	}

	public String getSubjectGoal() {
		return subjectGoal;
	}

	public void setSubjectGoal(String subjectGoal) {
		this.subjectGoal = subjectGoal;
	}

	public Long getApprovalHistoryId() {
		return approvalHistoryId;
	}

	public void setApprovalHistoryId(Long approvalHistoryId) {
		this.approvalHistoryId = approvalHistoryId;
	}

	public Long getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(Long ordNo) {
		this.ordNo = ordNo;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public Boolean getEditSubjectForm() {
		return editSubjectForm;
	}

	public void setEditSubjectForm(Boolean editSubjectForm) {
		this.editSubjectForm = editSubjectForm;
	}

	public Boolean getEditListChapter() {
		return editListChapter;
	}

	public void setEditListChapter(Boolean editListChapter) {
		this.editListChapter = editListChapter;
	}

	public Boolean getEditStand() {
		return editStand;
	}

	public void setEditStand(Boolean editStand) {
		this.editStand = editStand;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
