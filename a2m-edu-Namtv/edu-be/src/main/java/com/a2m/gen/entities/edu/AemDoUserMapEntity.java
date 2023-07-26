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
import com.a2m.gen.entities.TsstUser;

@Entity
@Table(name = "EAM_DOCUMENT_USER_MAP")
public class AemDoUserMapEntity extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TABLE_ID")
	private Long tableId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "QUIZ_ID")
    private QuizEntity quizEntity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DOC_ID")
    private AemCouSbjChtrLecture aemCouSbjChtrLecture;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_UID")
	private TsstUser userUid;
	
	@Column(name = "IS_READED")
	private Boolean isRead;
	
	@Column(name = "IS_COMPLETE")
	private Boolean isComplete;
	
	public AemDoUserMapEntity() {
		super();
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public QuizEntity getQuizEntity() {
		return quizEntity;
	}

	public void setQuizEntity(QuizEntity quizEntity) {
		this.quizEntity = quizEntity;
	}

	public AemCouSbjChtrLecture getAemCouSbjChtrLecture() {
		return aemCouSbjChtrLecture;
	}

	public void setAemCouSbjChtrLecture(AemCouSbjChtrLecture aemCouSbjChtrLecture) {
		this.aemCouSbjChtrLecture = aemCouSbjChtrLecture;
	}

	public TsstUser getUserUid() {
		return userUid;
	}

	public void setUserUid(TsstUser userUid) {
		this.userUid = userUid;
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

}
