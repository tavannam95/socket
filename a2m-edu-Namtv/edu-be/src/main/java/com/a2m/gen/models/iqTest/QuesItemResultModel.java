package com.a2m.gen.models.iqTest;

import java.util.List;

import com.a2m.gen.entities.edu.QuizItemAnswerEntity;
import com.a2m.gen.entities.edu.QuizItemAnswerResultEntity;
import com.a2m.gen.entities.edu.QuizItemEntity;
import com.a2m.gen.entities.edu.QuizItemResultEntity;
import com.a2m.gen.entities.iqTest.IqTestAnswerEntity;
import com.a2m.gen.entities.iqTest.IqTestQuesEntity;
import com.a2m.gen.entities.iqTest.QuesItemAnswerResultEntity;
import com.a2m.gen.entities.iqTest.QuesItemResultEntity;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author doanhq
 */
public class QuesItemResultModel extends IqTestQuesModel{
	
	private Long quesStudentMapId;
	private Long quesId;
	private String chooseAnswer;
	private Boolean status;
	private String result;
	private String quesType;
	
	
	public QuesItemResultModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QuesItemResultModel(QuesItemResultEntity entity) {
		super();
		// TODO Auto-generated constructor stub
		this.key = entity.getQuesItemStudentId();
		this.quesStudentMapId = entity.getQuesStudentMapEntity().getQuesStudentId();
		this.quesId = entity.getIqTestQuesEntity().getQuesId();
		this.chooseAnswer = entity.getChooseAnswer();
		this.setAnswerCorrectString(entity.getChooseAnswer());
		this.status = entity.getStatus();
		this.result = entity.getResult();
		
		IqTestQuesEntity quesEntity = entity.getIqTestQuesEntity();
		this.quesType = quesEntity.getQuesType();
		super.setQuesContent(quesEntity.getQuesContent());
		
		if(this.quesType.equals("09-02")) {
			List<QuesItemAnswerResultEntity> listQuesItemAnswerResult = entity.getListQuesItemAnswerResult();
			
			for(int i = 0 ; i< listQuesItemAnswerResult.size( );i++) {
			    QuesItemAnswerResultEntity answerResult = listQuesItemAnswerResult.get(i);
				IqTestAnswerEntity answer =  answerResult.getIqTestAnswerEntity();
				
				IqTestAnswerModel answerModel = new IqTestAnswerModel();
				answerModel.setAnswerContent(answer.getAnswerContent());
				answerModel.setAnswerCorrect(answer.getAnswerCorrect());
				answerModel.setIsChoose(answerResult.getIsChoose());
				super.getAnswerList().add(answerModel);
				
			}
			
		}
		
		if(this.quesType.equals("09-01")) {
		    List<QuesItemAnswerResultEntity> listQuesItemAnswerResult = entity.getListQuesItemAnswerResult();
            
            for(int i = 0 ; i< listQuesItemAnswerResult.size( );i++) {
                QuesItemAnswerResultEntity answerResult = listQuesItemAnswerResult.get(i);
                IqTestAnswerEntity answer =  answerResult.getIqTestAnswerEntity();
                
                IqTestAnswerModel answerModel = new IqTestAnswerModel();
                answerModel.setAnswerContent(answer.getAnswerContent());
                answerModel.setAnswerCorrect(answer.getAnswerCorrect());
                answerModel.setIsChoose(answerResult.getIsChoose());
                super.getAnswerList().add(answerModel);
                
            }
		}
		
	}

    public Long getQuesStudentMapId() {
        return quesStudentMapId;
    }

    public void setQuesStudentMapId(Long quesStudentMapId) {
        this.quesStudentMapId = quesStudentMapId;
    }

    public Long getQuesId() {
        return quesId;
    }

    public void setQuesId(Long quesId) {
        this.quesId = quesId;
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

    public String getQuesType() {
        return quesType;
    }

    public void setQuesType(String quesType) {
        this.quesType = quesType;
    }

}
