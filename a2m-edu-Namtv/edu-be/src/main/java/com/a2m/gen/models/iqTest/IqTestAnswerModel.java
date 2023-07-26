package com.a2m.gen.models.iqTest;

import com.a2m.gen.entities.iqTest.IqTestAnswerEntity;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author doanhq
 */
public class IqTestAnswerModel extends ParamBaseModel{
	
	private Long quesId;

	private String answerContent;
	private Boolean answerCorrect;
    private Boolean isChoose;
	
	public IqTestAnswerModel() {
		super();

        this.answerCorrect = false;
        this.answerContent = "";
        this.isChoose = false;
	}

	public IqTestAnswerModel(IqTestAnswerEntity db) {
		this.key = db.getAnswerId();
		this.answerContent = db.getAnswerContent();
		this.answerCorrect = db.getAnswerCorrect();
		this.deleteFlag = db.getDeleteFlag();
        this.isChoose = false;
	}

    public IqTestAnswerModel(IqTestAnswerEntity db, Boolean isChoose) {
        this.key = db.getAnswerId();
        this.answerContent = db.getAnswerContent();
        this.answerCorrect = db.getAnswerCorrect();
        this.deleteFlag = db.getDeleteFlag();
        this.isChoose = isChoose;
    }

    public Long getQuesId() {
        return quesId;
    }

    public void setQuesId(Long quesId) {
        this.quesId = quesId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Boolean getAnswerCorrect() {
        return answerCorrect;
    }

    public void setAnswerCorrect(Boolean answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    public Boolean getIsChoose() {
        return isChoose;
    }

    public void setIsChoose(Boolean isChoose) {
        this.isChoose = isChoose;
    }

}
