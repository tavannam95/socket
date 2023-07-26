package com.a2m.gen.models.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.edu.AemCouSbjChtrLecture;
import com.a2m.gen.entities.edu.AemDoUserMapEntity;
import com.a2m.gen.entities.edu.QuizEntity;
import com.a2m.gen.entities.edu.QuizItemEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.DocUserMapModel;

/**
 *
 * @author doanhq
 */
public class QuizModel extends ParamBaseModel{
	
	private Long chapterId;
	private Long subjectId;

	private String quizNm;
	private String quizContent;
	private String quizType;
	private Long ordNo;
	private Long quizTime;
	private Boolean status;
	private List<QuizItemModel> listQuizItem;
	
	private List<DocUserMapModel> listViewDoc = new ArrayList<DocUserMapModel>();

	private Boolean cannotCRUD;
	
	
	public QuizModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuizModel(QuizEntity db) {
		this.key = db.getQuizId();
		this.chapterId = db.getChapter().getChapterId();
		this.subjectId = db.getSubject().getSubjectId();
		this.quizNm = db.getQuizNm();
		this.quizContent = db.getQuizContent();
		this.quizType = db.getQuizType();
		this.ordNo = db.getOrdNo();
		this.insUid = db.getInsUid();
		this.updUid = db.getUpdUid();
		this.quizTime = db.getQuizTime();
		this.status = db.getStatus();
		this.cannotCRUD = (db.getQuizStudentMapEntities().size()>0)?true:false;
		List<QuizItemEntity> list = db.getListQuizItem();
		this.listQuizItem = new ArrayList<QuizItemModel>();
		for(int i = 0; i< list.size() ;i++) {
			QuizItemModel quizItemModel = new QuizItemModel(list.get(i));
			this.listQuizItem.add(quizItemModel);
		}
		List<AemDoUserMapEntity> viewDocs = db.getListViewDoc();
		if(viewDocs.size()!=0) {
			for(AemDoUserMapEntity viewDoc : viewDocs) {
				DocUserMapModel viewDocModel = new DocUserMapModel(viewDoc);
				this.listViewDoc.add(viewDocModel);
			}
		}
		this.deleteFlag = db.getDeleteFlag();
	}

	public Long getChapterId() {
		return chapterId;
	}
	
	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public String getQuizNm() {
		return quizNm;
	}
	public void setQuizNm(String quizNm) {
		this.quizNm = quizNm;
	}
	public String getQuizContent() {
		return quizContent;
	}

	public void setQuizContent(String quizContent) {
		this.quizContent = quizContent;
	}

	public String getQuizType() {
		return quizType;
	}
	public void setQuizType(String quizType) {
		this.quizType = quizType;
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

	public List<QuizItemModel> getListQuizItem() {
		return listQuizItem;
	}

	public void setListQuizItem(List<QuizItemModel> listQuizItem) {
		this.listQuizItem = listQuizItem;
	}

	public Boolean getCannotCRUD() {
		return cannotCRUD;
	}

	public void setCannotCRUD(Boolean cannotCRUD) {
		this.cannotCRUD = cannotCRUD;
	}

	public Long getQuizTime() {
		return quizTime;
	}

	public void setQuizTime(Long quizTime) {
		this.quizTime = quizTime;
	}

	public List<DocUserMapModel> getListViewDoc() {
		return listViewDoc;
	}

	public void setListViewDoc(List<DocUserMapModel> listViewDoc) {
		this.listViewDoc = listViewDoc;
	}	
	
}
