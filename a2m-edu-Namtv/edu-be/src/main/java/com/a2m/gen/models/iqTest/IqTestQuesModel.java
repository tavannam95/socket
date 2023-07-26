package com.a2m.gen.models.iqTest;

import java.util.ArrayList;
import java.util.List;

import com.a2m.gen.entities.iqTest.IqTestAnswerEntity;
import com.a2m.gen.entities.iqTest.IqTestQuesEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.course.AnswerCorrectLabel;
import com.a2m.gen.utils.CastUtil;

/**
 *
 * @author doanhq
 */
public class IqTestQuesModel extends ParamBaseModel{
    
    private String quesNm;
	private String quesContent;
	private String quesCategory;
	private String quesType;
	private String level;
	private String answerCorrectString;
    private String result;
	
    private List<IqTestAnswerModel> answerList =  new ArrayList<IqTestAnswerModel>();
    private List<IqTestAnswerModel> answerDelList =  new ArrayList<IqTestAnswerModel>();
    private List<AnswerCorrectLabel> listAnswerCorrect = new ArrayList<AnswerCorrectLabel>();
	private Boolean status;
	
	
	public IqTestQuesModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IqTestQuesModel(IqTestQuesEntity db) {
		this.key = db.getQuesId();
		this.quesNm = db.getQuesNm();
		this.quesContent = db.getQuesContent();
		this.quesCategory = db.getQuesCategory();
		this.quesType = db.getQuesType();
		this.level = db.getQuesLevel();
        this.status = db.getStatus();
        this.answerCorrectString = "";
        List<IqTestAnswerEntity> listAnswerEntity = db.getListOfAnswerByQues();
        for ( IqTestAnswerEntity iqAnswes : listAnswerEntity) {
            IqTestAnswerModel iqAnswerModel = new IqTestAnswerModel(iqAnswes);
            if(iqAnswerModel.getDeleteFlag() != true) {
                this.answerList.add(iqAnswerModel);
            }
        }
        
        for(int i = 0; i< listAnswerEntity.size() ;i++) {
            IqTestAnswerModel answerCorrect = new IqTestAnswerModel(listAnswerEntity.get(i));
            if(answerCorrect.getAnswerCorrect() && answerCorrect.getDeleteFlag() != true) {
                AnswerCorrectLabel  correct = new AnswerCorrectLabel(CastUtil.castToLong(i), ""+(i+1));
                this.listAnswerCorrect.add(correct);
            }
        }
	}

    public String getQuesNm() {
        return quesNm;
    }

    public void setQuesNm(String quesNm) {
        this.quesNm = quesNm;
    }

    public String getQuesContent() {
        return quesContent;
    }

    public void setQuesContent(String quesContent) {
        this.quesContent = quesContent;
    }

    public String getQuesCategory() {
        return quesCategory;
    }

    public void setQuesCategory(String quesCategory) {
        this.quesCategory = quesCategory;
    }

    public String getQuesType() {
        return quesType;
    }

    public void setQuesType(String quesType) {
        this.quesType = quesType;
    }

    public String getLev() {
        return level;
    }

    public void setLev(String level) {
        this.level = level;
    }

    public List<IqTestAnswerModel> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<IqTestAnswerModel> answerList) {
        this.answerList = answerList;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    public List<IqTestAnswerModel> getAnswerDelList() {
        return answerDelList;
    }

    public void setAnswerDelList(List<IqTestAnswerModel> answerDelList) {
        this.answerDelList = answerDelList;
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

    public String getAnswerCorrectString() {
        return answerCorrectString;
    }

    public void setAnswerCorrectString(String answerCorrectString) {
        this.answerCorrectString = answerCorrectString;
    }


}
