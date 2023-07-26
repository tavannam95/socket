package com.a2m.gen.models.course;

import java.util.List;

import com.a2m.gen.entities.edu.QuizStudentMapEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.StudentModel;

/**
 *
 * @author doanhq
 */
public class QuizStudentMapModel extends ParamBaseModel{
	
	private StudentModel studentModel;

	private Long totalCorrectAnswer;
	private Long totalWrongAnswer;
	private Long totalNotChoose;
	private Boolean status;

	
	
	public QuizStudentMapModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QuizStudentMapModel(QuizStudentMapEntity entity) {
		super();
		// TODO Auto-generated constructor stub
		this.key = entity.getQuizStudentId();
		this.studentModel = new StudentModel(entity.getStudentEntity());
		this.totalCorrectAnswer = entity.getTotalCorrectAnswer();
		this.totalWrongAnswer = entity.getTotalWrongAnswer();
		this.totalNotChoose = entity.getTotalNotChoose();
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

	public StudentModel getStudentModel() {
		return studentModel;
	}

	public void setStudentModel(StudentModel studentModel) {
		this.studentModel = studentModel;
	}

}
