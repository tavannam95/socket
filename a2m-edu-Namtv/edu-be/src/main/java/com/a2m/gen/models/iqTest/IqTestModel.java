package com.a2m.gen.models.iqTest;

import java.util.ArrayList;
import java.util.List;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.iqTest.AemIqTest;
import com.a2m.gen.entities.iqTest.AemIqTestMap;
import com.a2m.gen.entities.iqTest.AemNonUser;
import com.a2m.gen.entities.iqTest.IqTestAnswerEntity;
import com.a2m.gen.entities.iqTest.QuesStudentMapEntity;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author doanhq
 */
public class IqTestModel extends ParamBaseModel{
	
	private String iqTestNm;
    private String iqTestTime;
    private List<String> ListQuesId;
    private List<NumOfCategory> listNumOfCategory =  new ArrayList<NumOfCategory>();
    private List<IqTestQuesMapModel> listIqTestMap = new ArrayList<IqTestQuesMapModel>();
    private List<IqTestQuesModel> listQues = new ArrayList<IqTestQuesModel>();
    private List<QuesStudentMapModel> ListUser = new ArrayList<QuesStudentMapModel>();
    private Boolean status;
    private Boolean saveForListQues;
    private Boolean tested;
    private Long totalQuestion;
    private Long totalCorrect;
   
	
	
	public IqTestModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IqTestModel(AemIqTest db) {
		this.key = db.getIqTestId();
        this.iqTestNm = db.getIqTestNm();
        this.iqTestTime = db.getIqTestTime();
        this.status = db.getStatus();
        
        List<QuesStudentMapEntity> quesStudentMapEntities = db.getQuesStudentMapEntities();
        if(quesStudentMapEntities.size()>0) {
            for(QuesStudentMapEntity user : quesStudentMapEntities) {
                QuesStudentMapModel userInfo = new QuesStudentMapModel(user);
                this.ListUser.add(userInfo);
            }
            this.tested = true;
        	Long correct = quesStudentMapEntities.get(0).getTotalCorrectAnswer();
        	Long notChoose = quesStudentMapEntities.get(0).getTotalNotChoose();
        	Long wrong = quesStudentMapEntities.get(0).getTotalWrongAnswer();
        	this.totalQuestion = correct + notChoose + wrong;
        	this.totalCorrect = correct;
        }else {
        	this.tested = false;
        }
        
	}
	
	public IqTestModel(AemIqTest db, Boolean isGetAllData) {
        this.key = db.getIqTestId();
        this.iqTestNm = db.getIqTestNm();
        this.iqTestTime = db.getIqTestTime();
        this.status = db.getStatus();
        
        List<AemIqTestMap> listIqTestMap = db.getListIqTestMap();
        for ( AemIqTestMap iqTestQues : listIqTestMap) {
            IqTestQuesMapModel iqTestQuesModel = new IqTestQuesMapModel(iqTestQues);
            this.listIqTestMap.add(iqTestQuesModel);
        }
        
        
        List<QuesStudentMapEntity> quesStudentMapEntities = db.getQuesStudentMapEntities();
        //String userId = quesStudentMapEntities.get("id");
        if(quesStudentMapEntities.size()>0) {
            for(QuesStudentMapEntity user : quesStudentMapEntities) {
                QuesStudentMapModel userInfo = new QuesStudentMapModel(user);
                this.ListUser.add(userInfo);
            }
        	this.tested = true;
        	Long correct = quesStudentMapEntities.get(0).getTotalCorrectAnswer();
        	Long notChoose = quesStudentMapEntities.get(0).getTotalNotChoose();
        	Long wrong = quesStudentMapEntities.get(0).getTotalWrongAnswer();
        	this.totalQuestion = correct + notChoose + wrong;
        	this.totalCorrect = correct;
        }else {
        	this.tested = false;
        }
        
    }
    public String getIqTestNm() {
        return iqTestNm;
    }

    public void setIqTestNm(String iqTestNm) {
        this.iqTestNm = iqTestNm;
    }

    public String getIqTestTime() {
        return iqTestTime;
    }

    public void setIqTestTime(String iqTestTime) {
        this.iqTestTime = iqTestTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<String> getListQuesId() {
        return ListQuesId;
    }

    public void setListQuesId(List<String> listQuesId) {
        ListQuesId = listQuesId;
    }

    public List<NumOfCategory> getListNumOfCategory() {
        return listNumOfCategory;
    }

    public void setListNumOfCategory(List<NumOfCategory> listNumOfCategory) {
        this.listNumOfCategory = listNumOfCategory;
    }

    public List<IqTestQuesMapModel> getListIqTestMap() {
        return listIqTestMap;
    }

    public void setListIqTestMap(List<IqTestQuesMapModel> listIqTestMap) {
        this.listIqTestMap = listIqTestMap;
    }

    public List<IqTestQuesModel> getListQues() {
        return listQues;
    }

    public void setListQues(List<IqTestQuesModel> listQues) {
        this.listQues = listQues;
    }

    public Boolean getSaveForListQues() {
        return saveForListQues;
    }

    public void setSaveForListQues(Boolean saveForListQues) {
        this.saveForListQues = saveForListQues;
    }

	public Boolean getTested() {
		return tested;
	}

	public void setTested(Boolean tested) {
		this.tested = tested;
	}

	public Long getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(Long totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	public Long getTotalCorrect() {
		return totalCorrect;
	}

	public void setTotalCorrect(Long totalCorrect) {
		this.totalCorrect = totalCorrect;
	}

    public List<QuesStudentMapModel> getListUser() {
        return ListUser;
    }

    public void setListUser(List<QuesStudentMapModel> listUser) {
        ListUser = listUser;
    }

}
