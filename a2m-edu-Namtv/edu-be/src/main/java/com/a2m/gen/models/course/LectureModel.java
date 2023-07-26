package com.a2m.gen.models.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.edu.AemCouSbjChtrLecture;
import com.a2m.gen.entities.edu.AemDoUserMapEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.DocUserMapModel;

/**
 *
 * @author doanhq
 */
public class LectureModel extends ParamBaseModel{
	
	private Long chapterId;

	private String lectureNm;
	
	private Long totalLesson;
	
	private String typeDocument;
	
	private String tags;
	
	private String lectureContent;
	
	private String lectureGoal;
	
	private String lectureType;

	private String linkVideo;
	
	private Date startDt;
	
	private Date endDt;
	
	private Integer ordNo;
	
	private Boolean status;
	
	private List<LectureFileModel> lectureFileModel;
	
	private List<DocUserMapModel> listViewDoc = new ArrayList<DocUserMapModel>();

	public Long getChapterId() {
		return chapterId;
	}
	
	

	public LectureModel(Long chapterId, String lectureNm, String tags, String lectureContent, String lectureGoal,
			String lectureType, Date startDt, Date endDt, Integer ordNo, Boolean status,
			List<LectureFileModel> lectureFileModel) {
		super();
		this.chapterId = chapterId;
		this.lectureNm = lectureNm;
		this.tags = tags;
		this.lectureContent = lectureContent;
		this.lectureGoal = lectureGoal;
		this.lectureType = lectureType;
		this.startDt = startDt;
		this.endDt = endDt;
		this.ordNo = ordNo;
		this.status = status;
		this.lectureFileModel = lectureFileModel;
	}



	public LectureModel(Long chapterId, String lectureNm, String tags, String lectureContent, String lectureGoal,
			String lectureType, Date startDt, Date endDt, Integer ordNo, Boolean status) {
		super();
		this.chapterId = chapterId;
		this.lectureNm = lectureNm;
		this.tags = tags;
		this.lectureContent = lectureContent;
		this.lectureGoal = lectureGoal;
		this.lectureType = lectureType;
		this.startDt = startDt;
		this.endDt = endDt;
		this.ordNo = ordNo;
		this.status = status;
	}


	public LectureModel(AemCouSbjChtrLecture db) {
		this.key = db.getLectureId();
		this.chapterId = db.getAemCouSubChapter().getChapterId();
		this.lectureNm = db.getLectureNm();
		this.tags = db.getTags();
		this.lectureContent = db.getLectureContent();
		this.lectureGoal = db.getLectureGoal();
		this.lectureType = db.getLectureType();
		this.linkVideo = db.getLinkVideo();
		this.typeDocument = db.getTypeDocument();
		this.insUid = db.getInsUid();
//		this.startDt = db.getStartDt();
//		this.endDt = db.getEndDt();
		this.ordNo = db.getOdrNo();
		this.status = db.getStatus();
		this.lectureFileModel = new ArrayList<LectureFileModel>();
		this.deleteFlag = db.getDeleteFlag();
		List<AemDoUserMapEntity> viewDocs = db.getListViewDoc();
		if(viewDocs.size()!=0) {
			for(AemDoUserMapEntity viewDoc : viewDocs) {
				DocUserMapModel viewDocModel = new DocUserMapModel(viewDoc);
				this.listViewDoc.add(viewDocModel);
			}
		}
	}
	
	public LectureModel(AemCouSbjChtrLecture db, List<LectureFileModel> lectureFileModel) {
		this.key = db.getLectureId();
		this.chapterId = db.getAemCouSubChapter().getChapterId();
		this.lectureNm = db.getLectureNm();
		this.totalLesson = db.getTotalLesson();
		this.typeDocument = db.getTypeDocument();
		this.tags = db.getTags();
		this.lectureContent = db.getLectureContent();
		this.lectureGoal = db.getLectureGoal();
		this.lectureType = db.getLectureType();
		this.linkVideo = db.getLinkVideo();
		this.insUid = db.getInsUid();
//		this.startDt = db.getStartDt();
//		this.endDt = db.getEndDt();
		this.ordNo = db.getOdrNo();
		this.status = db.getStatus();
		this.lectureFileModel = lectureFileModel;
		List<AemDoUserMapEntity> viewDocs = db.getListViewDoc();
		if(viewDocs.size()!=0) {
			for(AemDoUserMapEntity viewDoc : viewDocs) {
				DocUserMapModel viewDocModel = new DocUserMapModel(viewDoc);
				this.listViewDoc.add(viewDocModel);
			}
		}
	}



	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getLectureContent() {
		return lectureContent;
	}

	public void setLectureContent(String lectureContent) {
		this.lectureContent = lectureContent;
	}

	public String getLectureGoal() {
		return lectureGoal;
	}

	public void setLectureGoal(String lectureGoal) {
		this.lectureGoal = lectureGoal;
	}

	public String getLectureType() {
		return lectureType;
	}

	public void setLectureType(String lectureType) {
		this.lectureType = lectureType;
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

	public Integer getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(Integer ordNo) {
		this.ordNo = ordNo;
	}

	public LectureModel() {
		super();
	}
	
	public String getLectureNm() {
		return lectureNm;
	}

	public void setLectureNm(String lectureNm) {
		this.lectureNm = lectureNm;
	}

	public List<LectureFileModel> getLectureFileModel() {
		return lectureFileModel;
	}

	public void setLectureFileModel(List<LectureFileModel> lectureFileModel) {
		this.lectureFileModel = lectureFileModel;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}



	public Long getTotalLesson() {
		return totalLesson;
	}



	public void setTotalLesson(Long totalLesson) {
		this.totalLesson = totalLesson;
	}



	public String getTypeDocument() {
		return typeDocument;
	}



	public void setTypeDocument(String typeDocument) {
		this.typeDocument = typeDocument;
	}



	public String getLinkVideo() {
		return linkVideo;
	}



	public void setLinkVideo(String linkVideo) {
		this.linkVideo = linkVideo;
	}



	public List<DocUserMapModel> getListViewDoc() {
		return listViewDoc;
	}



	public void setListViewDoc(List<DocUserMapModel> listViewDoc) {
		this.listViewDoc = listViewDoc;
	}

}
