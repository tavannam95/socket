package com.a2m.gen.models.edu;

import com.a2m.gen.entities.edu.AemDoUserMapEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.course.LectureModel;
import com.a2m.gen.models.course.QuizModel;

/**
 *
 * @author phongnc
 */
public class DocUserMapModel extends ParamBaseModel{	
	
	private Long quizId;
	
	private Long docId;

	private String UserUid;
	
	private Boolean isRead;
	
	private Boolean isComplete;
	
	private QuizModel quizModel;

	private LectureModel lectureModel;
	
	public DocUserMapModel(AemDoUserMapEntity entity) {
		super();
		this.key = entity.getTableId();
//		this.quizId = entity.getQuizEntity().getQuizId();
//		this.docId = entity.getAemCouSbjChtrLecture().getLectureId();
		this.UserUid = entity.getUserUid().getUserUid();
		this.isRead = entity.getIsRead();
		this.isComplete = entity.getIsComplete();
	}
	
	public DocUserMapModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public String getUserUid() {
		return UserUid;
	}

	public void setUserUid(String userUid) {
		UserUid = userUid;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Boolean getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}

	public QuizModel getQuizModel() {
		return quizModel;
	}

	public void setQuizModel(QuizModel quizModel) {
		this.quizModel = quizModel;
	}

	public LectureModel getLectureModel() {
		return lectureModel;
	}

	public void setLectureModel(LectureModel lectureModel) {
		this.lectureModel = lectureModel;
	}		

}
