package com.a2m.gen.models.course;

import java.util.ArrayList;
import java.util.List;

import com.a2m.gen.entities.edu.QuizItemAnswerEntity;
import com.a2m.gen.entities.edu.QuizItemEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.utils.CastUtil;

public class QuizItemModel extends ParamBaseModel {
	private Long quizId;
	private String quizItemContent;
	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;
	private String answerCorrectString;
	private String quizItemType;
	private String result;
	private Boolean status;
	private List<AnswerCorrectLabelDefault> answerCorrect;
	private List<QuizItemAnswerModel> listAnswer;
	private List<AnswerCorrectLabel> listAnswerCorrect;
	
	public QuizItemModel() {
		super();
		this.listAnswer = new ArrayList<QuizItemAnswerModel>();
		this.answerCorrect = new ArrayList<AnswerCorrectLabelDefault>();		
	}
	
	public QuizItemModel(QuizItemEntity entity) {
		super();
		this.key = entity.getQuizItemId();
		this.quizId = entity.getQuiz().getQuizId();
		this.quizItemContent = entity.getQuizItemContent();
		this.answerA = entity.getAnswerA();
		this.answerB = entity.getAnswerB();
		this.answerC = entity.getAnswerC();
		this.answerD = entity.getAnswerD();
		this.answerCorrectString = entity.getAnswerCorrect();
		this.quizItemType = entity.getQuizItemType();
		this.status = entity.getStats();
		this.crudType = "R";
		String[] arrayCorrect = entity.getAnswerCorrect().split("-");
		
		this.answerCorrect = new ArrayList<AnswerCorrectLabelDefault>();
		for (String value : arrayCorrect) {
			AnswerCorrectLabelDefault answerCorrect = new AnswerCorrectLabelDefault(value);
			this.answerCorrect.add(answerCorrect);
		}
		
		this.listAnswer = new ArrayList<QuizItemAnswerModel>();
		this.listAnswerCorrect = new ArrayList<AnswerCorrectLabel>();
		List<QuizItemAnswerEntity> listAnswerEntity = entity.getListQuizItemAnswertEntity();
		for(int i = 0; i< listAnswerEntity.size() ;i++) {
			QuizItemAnswerModel answerModel = new QuizItemAnswerModel(listAnswerEntity.get(i));
			this.listAnswer.add(answerModel);
			
			if(answerModel.getIsAnswerCorrect()) {
				AnswerCorrectLabel  correct = new AnswerCorrectLabel(CastUtil.castToLong(i), ""+(i+1));
				this.listAnswerCorrect.add(correct);
			}
		}
		if(listAnswerEntity.size()==0) {
			QuizItemAnswerModel model = new QuizItemAnswerModel();
			this.listAnswer.add(model);
		}
		
		
		
	}
	
	public Long getQuizId() {
		return quizId;
	}
	public void setQuizId(Long quizId) {
		this.quizId = quizId;
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
	public String getAnswerCorrectString() {
		return answerCorrectString;
	}
	public void setAnswerCorrectString(String answerCorrectString) {
		this.answerCorrectString = answerCorrectString;
	}
	public String getQuizItemType() {
		return quizItemType;
	}
	public void setQuizItemType(String quizItemType) {
		this.quizItemType = quizItemType;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<AnswerCorrectLabelDefault> getAnswerCorrect() {
		return answerCorrect;
	}

	public void setAnswerCorrect(List<AnswerCorrectLabelDefault> answerCorrect) {
		this.answerCorrect = answerCorrect;
	}
	
	public void addAnswerCorrect(String answerCorrectString) {
		String[] arrayCorrect = answerCorrectString.split("-");
		
		this.answerCorrect = new ArrayList<AnswerCorrectLabelDefault>();
		
		for (String value : arrayCorrect) {
			AnswerCorrectLabelDefault answerCorrect = new AnswerCorrectLabelDefault(value);
			this.answerCorrect.add(answerCorrect);
		}
	}

	public void setAnswerCorrectString(List<AnswerCorrectLabelDefault> answerCorrect) {
		String result = "";
		if(answerCorrect.size()>0) {
			result = answerCorrect.get(0).getValue();
		}
		for(int i = 1; i< answerCorrect.size();i++) {
			result += "-" + answerCorrect.get(i).getValue();
		}
		this.answerCorrectString = result;
	}

	public List<QuizItemAnswerModel> getListAnswer() {
		return listAnswer;
	}

	public void setListAnswer(List<QuizItemAnswerModel> listAnswer) {
		this.listAnswer = listAnswer;
	}

	public List<AnswerCorrectLabel> getListAnswerCorrect() {
		return listAnswerCorrect;
	}

	public void setListAnswerCorrect(List<AnswerCorrectLabel> listAnswerCorrect) {
		this.listAnswerCorrect = listAnswerCorrect;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
