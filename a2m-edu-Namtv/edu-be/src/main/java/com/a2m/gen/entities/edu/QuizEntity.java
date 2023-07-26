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

@Entity
@Table(name = "EAM_QUIZ")
public class QuizEntity extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUIZ_ID")
	private Long quizId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "CHAPTER_ID")
	private AemCouSbjChapter chapter;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "SUBJECT_ID")
	private AemCourseSubject subject;
	
	@OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<QuizItemEntity> listQuizItem = new ArrayList<QuizItemEntity>();
	
	@OneToMany(mappedBy = "quizEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<QuizStudentMapEntity> quizStudentMapEntities = new ArrayList<QuizStudentMapEntity>();

	@Column(name = "QUIZ_NM")
	private String quizNm;

	@Column(name = "QUIZ_CONTENT")
	private String quizContent;
	
	@Column(name = "QUIZ_TYPE")
	private String quizType;
	
	@Column(name = "ORD_NO")
	private Long ordNo;
	
	@Column(name = "QUIZ_TIME")
	private Long quizTime;
	
	@Column(name = "STATUS")
	private Boolean status;

	@OneToMany(mappedBy = "quizEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemDoUserMapEntity> listViewDoc = new ArrayList<AemDoUserMapEntity>();

	public QuizEntity() {
		super();
	}

	public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public AemCouSbjChapter getChapter() {
		return chapter;
	}

	public void setChapter(AemCouSbjChapter chapter) {
		this.chapter = chapter;
	}

	public AemCourseSubject getSubject() {
		return subject;
	}

	public void setSubject(AemCourseSubject subject) {
		this.subject = subject;
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

	public List<QuizItemEntity> getListQuizItem() {
		return listQuizItem;
	}

	public void setListQuizItem(List<QuizItemEntity> listQuizItem) {
		this.listQuizItem = listQuizItem;
	}

	public List<QuizStudentMapEntity> getQuizStudentMapEntities() {
		return quizStudentMapEntities;
	}

	public void setQuizStudentMapEntities(List<QuizStudentMapEntity> quizStudentMapEntities) {
		this.quizStudentMapEntities = quizStudentMapEntities;
	}

	public Long getQuizTime() {
		return quizTime;
	}

	public void setQuizTime(Long quizTime) {
		this.quizTime = quizTime;
	}

	public List<AemDoUserMapEntity> getListViewDoc() {
		return listViewDoc;
	}

	public void setListViewDoc(List<AemDoUserMapEntity> listViewDoc) {
		this.listViewDoc = listViewDoc;
	}

}
