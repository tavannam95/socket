package com.a2m.gen.models.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.a2m.gen.entities.edu.QuizEntity;
import com.a2m.gen.entities.edu.QuizItemResultEntity;
import com.a2m.gen.entities.edu.QuizStudentMapEntity;

public class SubmitQuizModel extends QuizModel{
	private Long quizId;
	private Long studentInfoId;
	private Long totalCorrectAnswer;
	private Long totalWrongAnswer;
	private Long totalNotChoose;
	
	private Boolean status;
	private List<QuizItemResultModel> listQuizItemResult;
	private List<QuizItemModel> listQuizItem;
	
	public SubmitQuizModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SubmitQuizModel(QuizStudentMapEntity quizStudentMapEntity) {
		super();
		this.quizId = quizStudentMapEntity.getQuizEntity().getQuizId();
		this.studentInfoId = quizStudentMapEntity.getStudentEntity().getStudentInfoId();
		this.totalCorrectAnswer = quizStudentMapEntity.getTotalCorrectAnswer();
		this.totalWrongAnswer = quizStudentMapEntity.getTotalWrongAnswer();
		this.totalNotChoose = quizStudentMapEntity.getTotalNotChoose();
		
		this.listQuizItemResult = new ArrayList<QuizItemResultModel>();
		this.listQuizItem = new ArrayList<QuizItemModel>();
		
		List<QuizItemResultEntity> listQuizItemResult = quizStudentMapEntity.getListQuizItemResultEntity();
		Collections.sort(listQuizItemResult, new Comparator<QuizItemResultEntity>() {
			  @Override
			  public int compare(QuizItemResultEntity u1, QuizItemResultEntity u2) {
			    return u1.getQuizItemEntity().getQuizItemId().compareTo(u2.getQuizItemEntity().getQuizItemId());
			  }
			});
		
		for(int i = 0; i < listQuizItemResult.size(); i++) {
			QuizItemResultEntity quizResult = listQuizItemResult.get(i);
			QuizItemResultModel quizItemResult = new QuizItemResultModel(quizResult);
			
			this.listQuizItemResult.add(quizItemResult);
		}
		
		QuizEntity quizEntity = quizStudentMapEntity.getQuizEntity();
		super.setQuizContent(quizEntity.getQuizContent());
		super.setQuizNm(quizEntity.getQuizNm());
		
	}
	public Long getStudentInfoId() {
		return studentInfoId;
	}
	public void setStudentInfoId(Long studentInfoId) {
		this.studentInfoId = studentInfoId;
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
	public List<QuizItemResultModel> getListQuizItemResult() {
		return listQuizItemResult;
	}
	public void setListQuizItemResult(List<QuizItemResultModel> listQuizItemResult) {
		this.listQuizItemResult = listQuizItemResult;
	}
	public Long getQuizId() {
		return quizId;
	}
	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}
	public List<QuizItemModel> getListQuizItem() {
		return listQuizItem;
	}
	public void setListQuizItem(List<QuizItemModel> listQuizItem) {
		this.listQuizItem = listQuizItem;
	}
	
	
}
 
