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
@Table(name = "EAM_QUIZ_ITEM")
public class QuizItemEntity extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUIZ_ITEM_ID")
	private Long quizItemId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "QUIZ_ID")
	private QuizEntity quiz;
	
	@OneToMany(mappedBy = "quizItemEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<QuizItemResultEntity> listQuizItemResultEntity = new ArrayList<QuizItemResultEntity>();
	
	@OneToMany(mappedBy = "quizItemEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<QuizItemAnswerEntity> listQuizItemAnswertEntity = new ArrayList<QuizItemAnswerEntity>();

	@Column(name = "QUIZ_ITEM_CONTENT")
	private String quizItemContent;

	@Column(name = "ANSWER_A")
	private String answerA;
	
	@Column(name = "ANSWER_B")
	private String answerB;
	
	@Column(name = "ANSWER_C")
	private String answerC;
	
	@Column(name = "ANSWER_D")
	private String answerD;
	
	@Column(name = "ANSWER_CORRECT")
	private String answerCorrect;
	
	@Column(name = "QUIZ_ITEM_TYPE")
	private String quizItemType;
	
	@Column(name = "STATUS")
	private Boolean stats;

	public QuizItemEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getQuizItemId() {
		return quizItemId;
	}

	public void setQuizItemId(Long quizItemId) {
		this.quizItemId = quizItemId;
	}

	public QuizEntity getQuiz() {
		return quiz;
	}

	public void setQuiz(QuizEntity quiz) {
		this.quiz = quiz;
	}

	public String getQuizItemContent() {
		return quizItemContent;
	}

	public void setQuizItemContent(String quizItemContent) {
		this.quizItemContent = quizItemContent;
	}

	public String getAnswerA() {
		return answerA;
	}

	public void setAnswerA(String answerA) {
		this.answerA = answerA;
	}

	public String getAnswerB() {
		return answerB;
	}

	public void setAnswerB(String answerB) {
		this.answerB = answerB;
	}

	public String getAnswerC() {
		return answerC;
	}

	public void setAnswerC(String answerC) {
		this.answerC = answerC;
	}

	public String getAnswerD() {
		return answerD;
	}

	public void setAnswerD(String answerD) {
		this.answerD = answerD;
	}

	public String getAnswerCorrect() {
		return answerCorrect;
	}

	public void setAnswerCorrect(String answerCorrect) {
		this.answerCorrect = answerCorrect;
	}

	public String getQuizItemType() {
		return quizItemType;
	}

	public void setQuizItemType(String quizItemType) {
		this.quizItemType = quizItemType;
	}

	public Boolean getStats() {
		return stats;
	}

	public void setStats(Boolean stats) {
		this.stats = stats;
	}

	public List<QuizItemResultEntity> getListQuizItemResultEntity() {
		return listQuizItemResultEntity;
	}

	public void setListQuizItemResultEntity(List<QuizItemResultEntity> listQuizItemResultEntity) {
		this.listQuizItemResultEntity = listQuizItemResultEntity;
	}

	public List<QuizItemAnswerEntity> getListQuizItemAnswertEntity() {
		return listQuizItemAnswertEntity;
	}

	public void setListQuizItemAnswertEntity(List<QuizItemAnswerEntity> listQuizItemAnswertEntity) {
		this.listQuizItemAnswertEntity = listQuizItemAnswertEntity;
	}
	
	
}
