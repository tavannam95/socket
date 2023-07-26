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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;

@Entity
@Table(name = "EAM_QUIZ_ITEM_ANSWER_RESULT")
public class QuizItemAnswerResultEntity extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ANSWER_RESULT_ID")
	private Long quizItemAnswerResultId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "QUIZ_ITEM_RESULT_ID")
	private QuizItemResultEntity quizItemResultEntity;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUIZ_ITEM_ANSWER_ID")
	private QuizItemAnswerEntity quizItemAnswerEntity;
	
	@Column(name = "IS_CHOOSE")
	private Boolean isChoose;
	
	public QuizItemAnswerResultEntity() {
		super();
		// TODO Auto-generated constructor stub
	}



	public QuizItemResultEntity getQuizItemResultEntity() {
		return quizItemResultEntity;
	}

	public void setQuizItemResultEntity(QuizItemResultEntity quizItemResultEntity) {
		this.quizItemResultEntity = quizItemResultEntity;
	}

	public QuizItemAnswerEntity getQuizItemAnswerEntity() {
		return quizItemAnswerEntity;
	}

	public void setQuizItemAnswerEntity(QuizItemAnswerEntity quizItemAnswerEntity) {
		this.quizItemAnswerEntity = quizItemAnswerEntity;
	}

	public Boolean getIsChoose() {
		return isChoose;
	}

	public void setIsChoose(Boolean isChoose) {
		this.isChoose = isChoose;
	}



	public Long getQuizItemAnswerResultId() {
		return quizItemAnswerResultId;
	}



	public void setQuizItemAnswerResultId(Long quizItemAnswerResultId) {
		this.quizItemAnswerResultId = quizItemAnswerResultId;
	}
}
