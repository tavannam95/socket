package com.a2m.gen.models.course;

import java.util.List;

import com.a2m.gen.entities.edu.QuizItemAnswerEntity;
import com.a2m.gen.entities.edu.QuizItemAnswerResultEntity;
import com.a2m.gen.entities.edu.QuizItemEntity;
import com.a2m.gen.entities.edu.QuizItemResultEntity;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author doanhq
 */
public class QuizItemResultModel extends QuizItemModel{
	
	private Long quizStudentMapId;
	private Long quizItemId;
	private String chooseAnswer;
	private Boolean status;
	private String result;
	
	private Boolean chooseA;
	private Boolean chooseB;
	private Boolean chooseC;
	private Boolean chooseD;
	
	private Boolean correctA;
	private Boolean correctB;
	private Boolean correctC;
	private Boolean correctD;
	
	private String quizItemType;
	
	
	public QuizItemResultModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QuizItemResultModel(QuizItemResultEntity entity) {
		super();
		// TODO Auto-generated constructor stub
		this.key = entity.getQuizItemStudentId();
		this.quizStudentMapId = entity.getQuizStudentMapEntity().getQuizStudentId();
		this.quizItemId = entity.getQuizItemEntity().getQuizItemId();
		this.chooseAnswer = entity.getChooseAnswer();
		
		this.status = entity.getStatus();
		this.result = entity.getResult();
		
		QuizItemEntity quizItemEntity = entity.getQuizItemEntity();
		this.quizItemType = quizItemEntity.getQuizItemType();
		super.setQuizItemContent(quizItemEntity.getQuizItemContent());
		super.setAnswerA(quizItemEntity.getAnswerA());
		super.setAnswerB(quizItemEntity.getAnswerB());
		super.setAnswerC(quizItemEntity.getAnswerC());
		super.setAnswerD(quizItemEntity.getAnswerD());
		
		if(this.quizItemType.equals("08-03")) {
			String answer[] = entity.getChooseAnswer().split("-");
			this.chooseA = false;
			this.chooseB = false;
			this.chooseC = false;
			this.chooseD = false;
			
			for(int i = 0 ; i< answer.length;i++) {
				if(answer[i].equals("A")) this.chooseA = true;
				if(answer[i].equals("B")) this.chooseB = true;
				if(answer[i].equals("C")) this.chooseC = true;
				if(answer[i].equals("D")) this.chooseD = true;			
					
			}
			
			String correctAnswer[] = quizItemEntity.getAnswerCorrect().split("-");
			this.correctA = false;
			this.correctB = false;
			this.correctC = false;
			this.correctD = false;
			for(int i = 0 ; i< correctAnswer.length;i++) {
				if(correctAnswer[i].equals("A")) this.correctA = true;
				if(correctAnswer[i].equals("B")) this.correctB = true;
				if(correctAnswer[i].equals("C")) this.correctC = true;
				if(correctAnswer[i].equals("D")) this.correctD = true;
			}
		}else {
			this.chooseA = false;
			this.chooseB = false;
			this.chooseC = false;
			this.chooseD = false;
		}
		
		if(this.quizItemType.equals("08-01")) {
			List<QuizItemAnswerResultEntity> listQuizItemAnswerResult = entity.getListQuizItemAnswerResult();
			
			for(int i = 0 ; i< listQuizItemAnswerResult.size( );i++) {
				QuizItemAnswerResultEntity answerResult = listQuizItemAnswerResult.get(i);
				QuizItemAnswerEntity answer =  answerResult.getQuizItemAnswerEntity();
				
				QuizItemAnswerModel answerModel = new QuizItemAnswerModel();
				answerModel.setAnswerContent(answer.getAnswerContent());
				answerModel.setIsAnswerCorrect(answer.getIsAnswerCorrect());
				answerModel.setIsChoose(answerResult.getIsChoose());
				super.getListAnswer().add(answerModel);
				
					
			}
			
		}
		
		if(this.quizItemType.equals("08-02")) {
			super.setAnswerCorrectString(entity.getChooseAnswer());
		}
		
		
	}

	public Long getQuizStudentMapId() {
		return quizStudentMapId;
	}

	public void setQuizStudentMapId(Long quizStudentMapId) {
		this.quizStudentMapId = quizStudentMapId;
	}

	public Long getQuizItemId() {
		return quizItemId;
	}

	public void setQuizItemId(Long quizItemId) {
		this.quizItemId = quizItemId;
	}

	public String getChooseAnswer() {
		return chooseAnswer;
	}
	
	public String getChooseAnswerToString(){
		String result = "";
		if(chooseA) {
			result = "A";
			
			if(chooseB) result += "-B";
			if(chooseC) result += "-C";
			if(chooseD) result += "-D";
			
		}else if(chooseB) {
			result = "B";
			
			if(chooseC) result += "-C";
			if(chooseD) result += "-D";
			
		}else if(chooseC) {
			result = "C";
			
			if(chooseD) result += "-D";
			
		}else if(chooseD) {
			result = "D";			
		}
		
		return result;
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

	public Boolean getChooseA() {
		return chooseA;
	}

	public void setChooseA(Boolean chooseA) {
		this.chooseA = chooseA;
	}

	public Boolean getChooseB() {
		return chooseB;
	}

	public void setChooseB(Boolean chooseB) {
		this.chooseB = chooseB;
	}

	public Boolean getChooseC() {
		return chooseC;
	}

	public void setChooseC(Boolean chooseC) {
		this.chooseC = chooseC;
	}

	public Boolean getChooseD() {
		return chooseD;
	}

	public void setChooseD(Boolean chooseD) {
		this.chooseD = chooseD;
	}

	public Boolean getCorrectA() {
		return correctA;
	}

	public void setCorrectA(Boolean correctA) {
		this.correctA = correctA;
	}

	public Boolean getCorrectB() {
		return correctB;
	}

	public void setCorrectB(Boolean correctB) {
		this.correctB = correctB;
	}

	public Boolean getCorrectC() {
		return correctC;
	}

	public void setCorrectC(Boolean correctC) {
		this.correctC = correctC;
	}

	public Boolean getCorrectD() {
		return correctD;
	}

	public void setCorrectD(Boolean correctD) {
		this.correctD = correctD;
	}

	public String getQuizItemType() {
		return quizItemType;
	}

	public void setQuizItemType(String quizItemType) {
		this.quizItemType = quizItemType;
	}


}
