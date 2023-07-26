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
import com.a2m.gen.models.course.QuizItemResultModel;

@Entity
@Table(name = "EAM_QUIZ_ITEM_RESULT")
public class QuizItemResultEntity extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUIZ_ITEM_RESULT_ID")
	private Long quizItemStudentId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "QUIZ_STUDENT_ID")
	private QuizStudentMapEntity quizStudentMapEntity;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "QUIZ_ITEM_ID")
	private QuizItemEntity quizItemEntity;
	
	@OneToMany(mappedBy = "quizItemResultEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<QuizItemAnswerResultEntity> listQuizItemAnswerResult = new ArrayList<QuizItemAnswerResultEntity>();

	@Column(name = "CHOOSE_ANSWER")
	private String chooseAnswer;
	
	@Column(name = "STATUS")
	private Boolean status;
	
	@Column(name = "RESULT")
	private String result;
	
	public QuizItemResultEntity() {
		super();
	}

	public Long getQuizItemStudentId() {
		return quizItemStudentId;
	}

	public void setQuizItemStudentId(Long quizItemStudentId) {
		this.quizItemStudentId = quizItemStudentId;
	}

	public QuizStudentMapEntity getQuizStudentMapEntity() {
		return quizStudentMapEntity;
	}

	public void setQuizStudentMapEntity(QuizStudentMapEntity quizStudentMapEntity) {
		this.quizStudentMapEntity = quizStudentMapEntity;
	}

	public QuizItemEntity getQuizItemEntity() {
		return quizItemEntity;
	}

	public void setQuizItemEntity(QuizItemEntity quizItemEntity) {
		this.quizItemEntity = quizItemEntity;
	}

	public String getChooseAnswer() {
		return chooseAnswer;
	}

	public void setChooseAnswer(String chooseAnswer) {
		this.chooseAnswer = chooseAnswer;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<QuizItemAnswerResultEntity> getListQuizItemAnswerResult() {
		return listQuizItemAnswerResult;
	}

	public void setListQuizItemAnswerResult(List<QuizItemAnswerResultEntity> listQuizItemAnswerResult) {
		this.listQuizItemAnswerResult = listQuizItemAnswerResult;
	}


}
