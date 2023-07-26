package com.a2m.gen.models.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemCourseSubjectHistory;
import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.common.ApproveModel;
import com.a2m.gen.models.edu.TeacherModel;

/**
 *
 * @author doanhq
 */
public class SubjectHistoryModel extends ParamBaseModel{
	
	private Long subjectId;

	private Long courseId;
	
	private String documentStatus;

	private String subjectNm;

    private String subjectCode;
    
	private String subjectContent;
	
	private String subjectGoal;
	
	private Date startDt;
	
	private Date endDt;
	
	private Long ordNo;
	
	private Boolean status;
	
	private Boolean editSubjectForm;
	
	private Boolean editListChapter;
	
	private Boolean editStand;
	
	private TeacherModel  teacherModel;
	
	private TsstUser tsstUser;
	
	public String courseUid;
	   
    private List<String> listSubjectId;
    
    private List<AemCourseSubjectMap> listOfCourseSubMap = new ArrayList<AemCourseSubjectMap>();
	
	private List<SbjChapterModel> chapterModels = new ArrayList<SbjChapterModel>();
	
//	private List<SubjectStandardNoteModel> listNote = new ArrayList<SubjectStandardNoteModel>();

    private List<SubjectStandardModel> listStand = new ArrayList<SubjectStandardModel>();
    
    private Long teacherInfoId;
    
    private String userUid;
    
    private Long approvalHistoryId;
    
    private ApproveModel approveHistory;
    
    private List<CourseSelectModel> listCourse = new ArrayList<CourseSelectModel>();
	
	public SubjectHistoryModel() {
		super();
	}

	public SubjectHistoryModel(AemCourseSubjectHistory db) {
		this.key = db.getSubjectHistoryId();
		this.subjectId = db.getSubjectId();
		this.documentStatus = db.getDocumentStatus();
		this.subjectNm = db.getSubjectNm();
		this.subjectCode = db.getSubjectCode();
		this.subjectContent = db.getSubjectContent();
		this.subjectGoal = db.getSubjectGoal();
		this.startDt = db.getStartDt();
		this.endDt = db.getEndDt();
		this.ordNo = db.getOrdNo();
		this.status = db.getStatus();
		this.deleteFlag = db.getDeleteFlag();
		
		this.editSubjectForm = db.getEditSubjectForm();
		this.editListChapter = db.getEditListChapter();
		this.editStand = db.getEditStand();
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
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

	public Long getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(Long ordNo) {
		this.ordNo = ordNo;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public TeacherModel getTeacherModel() {
		return teacherModel;
	}

	public void setTeacherModel(TeacherModel teacherModel) {
		this.teacherModel = teacherModel;
	}

	public TsstUser getTsstUser() {
		return tsstUser;
	}

	public void setTsstUser(TsstUser tsstUser) {
		this.tsstUser = tsstUser;
	}

	public String getCourseUid() {
		return courseUid;
	}

	public void setCourseUid(String courseUid) {
		this.courseUid = courseUid;
	}

	public List<String> getListSubjectId() {
		return listSubjectId;
	}

	public void setListSubjectId(List<String> listSubjectId) {
		this.listSubjectId = listSubjectId;
	}

	public List<AemCourseSubjectMap> getListOfCourseSubMap() {
		return listOfCourseSubMap;
	}

	public void setListOfCourseSubMap(List<AemCourseSubjectMap> listOfCourseSubMap) {
		this.listOfCourseSubMap = listOfCourseSubMap;
	}

	public List<SbjChapterModel> getChapterModels() {
		return chapterModels;
	}

	public void setChapterModels(List<SbjChapterModel> chapterModels) {
		this.chapterModels = chapterModels;
	}

	public List<SubjectStandardModel> getListStand() {
		return listStand;
	}

	public void setListStand(List<SubjectStandardModel> listStand) {
		this.listStand = listStand;
	}

	public ApproveModel getApproveHistory() {
		return approveHistory;
	}

	public void setApproveHistory(ApproveModel approveHistory) {
		this.approveHistory = approveHistory;
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

	public Long getApprovalHistoryId() {
		return approvalHistoryId;
	}

	public void setApprovalHistoryId(Long approvalHistoryId) {
		this.approvalHistoryId = approvalHistoryId;
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

	public List<CourseSelectModel> getListCourse() {
		return listCourse;
	}

	public void setListCourse(List<CourseSelectModel> listCourse) {
		this.listCourse = listCourse;
	}
}
