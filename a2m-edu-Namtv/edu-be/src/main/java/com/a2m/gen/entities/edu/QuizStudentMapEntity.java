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
@Table(name = "EAM_QUIZ_STUDENT_MAP")
public class QuizStudentMapEntity extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUIZ_STUDENT_ID")
	private Long quizStudentId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "QUIZ_ID")
	private QuizEntity quizEntity;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "STUDENT_INFO_ID")
	private AemStudentEntity studentEntity;
	
	@OneToMany(mappedBy = "quizStudentMapEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<QuizItemResultEntity> listQuizItemResultEntity = new ArrayList<QuizItemResultEntity>();

	@Column(name = "TOTAL_CORRECT_ANSWER")
	private Long totalCorrectAnswer;
	
	@Column(name = "TOTAL_WRONG_ANSWER")
	private Long totalWrongAnswer;
	
	@Column(name = "TOTAL_NOT_CHOOSE")
	private Long totalNotChoose;
	
	@Column(name = "STATUS")
	private Boolean status;
	
	public QuizStudentMapEntity() {
		super();
	}

	public Long getQuizStudentId() {
		return quizStudentId;
	}

	public void setQuizStudentId(Long quizStudentId) {
		this.quizStudentId = quizStudentId;
	}

	public QuizEntity getQuizEntity() {
		return quizEntity;
	}

	public void setQuizEntity(QuizEntity quizEntity) {
		this.quizEntity = quizEntity;
	}



	public List<QuizItemResultEntity> getListQuizItemResultEntity() {
		return listQuizItemResultEntity;
	}

	public void setListQuizItemResultEntity(List<QuizItemResultEntity> listQuizItemResultEntity) {
		this.listQuizItemResultEntity = listQuizItemResultEntity;
	}

	public Long getTotalCorrectAnswer() {
		return totalCorrectAnswer;
	}

	public void setTotalCorrectAnswer(Long totalCorrectAnswer) {
		this.totalCorrectAnswer = totalCorrectAnswer;
	}

	public Long getTotalWrongAnswer() {
		return totalWrongAnswer;
	}

	public void setTotalWrongAnswer(Long totalWrongAnswer) {
		this.totalWrongAnswer = totalWrongAnswer;
	}

	public Long getTotalNotChoose() {
		return totalNotChoose;
	}

	public void setTotalNotChoose(Long totalNotChoose) {
		this.totalNotChoose = totalNotChoose;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public AemStudentEntity getStudentEntity() {
		return studentEntity;
	}

	public void setStudentEntity(AemStudentEntity studentEntity) {
		this.studentEntity = studentEntity;
	}


}
