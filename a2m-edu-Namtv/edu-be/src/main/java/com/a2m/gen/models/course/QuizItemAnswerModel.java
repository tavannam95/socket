package com.a2m.gen.models.course;

import com.a2m.gen.entities.edu.QuizItemAnswerEntity;
import com.a2m.gen.models.ParamBaseModel;

public class QuizItemAnswerModel extends ParamBaseModel {
	
	private Long quizItemAnswerId;
	private QuizItemModel quizItemModel;
	private String answerContent;
	private Boolean isAnswerCorrect;
	private Boolean isChoose;
	
	public QuizItemAnswerModel() {
		super();
		this.isAnswerCorrect = false;
		this.answerContent = "";
		this.isChoose = false;
	}
	
	public QuizItemAnswerModel(QuizItemAnswerEntity entity) {
		super();
		this.key = entity.getQuizItemAnswerId();
		this.quizItemAnswerId = entity.getQuizItemAnswerId();
		this.answerContent = entity.getAnswerContent();
		this.isAnswerCorrect = entity.getIsAnswerCorrect();
		this.isChoose = false;
	}
	
	public QuizItemAnswerModel(QuizItemAnswerEntity entity, Boolean isChoose) {
		super();
		this.key = entity.getQuizItemAnswerId();
		this.quizItemAnswerId = entity.getQuizItemAnswerId();
		this.answerContent = entity.getAnswerContent();
		this.isAnswerCorrect = entity.getIsAnswerCorrect();
		this.isChoose = isChoose;
	}

	public Long getQuizItemAnswerId() {
		return quizItemAnswerId;
	}

	public void setQuizItemAnswerId(Long quizItemAnswerId) {
		this.quizItemAnswerId = quizItemAnswerId;
	}

	public QuizItemModel getQuizItemModel() {
		return quizItemModel;
	}

	public void setQuizItemModel(QuizItemModel quizItemModel) {
		this.quizItemModel = quizItemModel;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public Boolean getIsAnswerCorrect() {
		return isAnswerCorrect;
	}

	public void setIsAnswerCorrect(Boolean isAnswerCorrect) {
		this.isAnswerCorrect = isAnswerCorrect;
	}

	public Boolean getIsChoose() {
		return isChoose;
	}

	public void setIsChoose(Boolean isChoose) {
		this.isChoose = isChoose;
	}
}
