package com.a2m.gen.entities.edu;

import javax.persistence.CascadeType;
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
@Table(name = "EAM_QUIZ_ITEM_ANSWER")
public class QuizItemAnswerEntity extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUIZ_ITEM_ANSWER_ID")
	private Long quizItemAnswerId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "QUIZ_ITEM_ID")
	private QuizItemEntity quizItemEntity;

	@Column(name = "ANSWER_CONTENT")
	private String answerContent;
	
	@Column(name = "IS_ANSWER_CORRECT")
	private Boolean answerCorrect;

	public QuizItemAnswerEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getQuizItemAnswerId() {
		return quizItemAnswerId;
	}

	public void setQuizItemAnswerId(Long quizItemAnswerId) {
		this.quizItemAnswerId = quizItemAnswerId;
	}

	public QuizItemEntity getQuizItemEntity() {
		return quizItemEntity;
	}

	public void setQuizItemEntity(QuizItemEntity quizItemEntity) {
		this.quizItemEntity = quizItemEntity;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public Boolean getIsAnswerCorrect() {
		return answerCorrect;
	}

	public void setIsAnswerCorrect(Boolean answerCorrect) {
		this.answerCorrect = answerCorrect;
	}

	
	
}
