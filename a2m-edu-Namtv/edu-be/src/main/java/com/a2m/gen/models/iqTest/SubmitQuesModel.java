package com.a2m.gen.models.iqTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.a2m.gen.entities.iqTest.AemIqTest;
import com.a2m.gen.entities.iqTest.AemNonUser;
import com.a2m.gen.entities.iqTest.QuesItemResultEntity;
import com.a2m.gen.entities.iqTest.QuesStudentMapEntity;

public class SubmitQuesModel extends IqTestModel{
	private Long iqTestId;
	private Long studentInfoId;
	private Long totalCorrectAnswer;
	private Long totalWrongAnswer;
	private Long totalNotChoose;
	private Long nonUserId;
    private String iqTestTime;
	private Boolean status;
	private List<QuesItemResultModel> listQuesResult;
	private List<IqTestQuesModel> listQues;
	private Boolean tested;
	
	public SubmitQuesModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SubmitQuesModel(QuesStudentMapEntity quesStudentMapEntity) {
		super();
		this.tested = true;
		this.iqTestId = quesStudentMapEntity.getIqTestEntity().getIqTestId();
		//this.studentInfoId = quesStudentMapEntity.getStudentEntity().getStudentInfoId();
		this.totalCorrectAnswer = quesStudentMapEntity.getTotalCorrectAnswer();
		this.totalWrongAnswer = quesStudentMapEntity.getTotalWrongAnswer();
		this.totalNotChoose = quesStudentMapEntity.getTotalNotChoose();
		
		this.listQuesResult = new ArrayList<QuesItemResultModel>();
		this.listQues = new ArrayList<IqTestQuesModel>();
		
		List<QuesItemResultEntity> listQuesItemResult = quesStudentMapEntity.getListQuesItemResultEntity();
		Collections.sort(listQuesItemResult, new Comparator<QuesItemResultEntity>() {
			  @Override
			  public int compare(QuesItemResultEntity u1, QuesItemResultEntity u2) {
			    return u1.getIqTestQuesEntity().getQuesId().compareTo(u2.getIqTestQuesEntity().getQuesId());
			  }
			});
		
		for(int i = 0; i < listQuesItemResult.size(); i++) {
			QuesItemResultEntity quesResult = listQuesItemResult.get(i);
			QuesItemResultModel quesItemResult = new QuesItemResultModel(quesResult);
			
			this.listQuesResult.add(quesItemResult);
		}
		
		AemIqTest quizEntity = quesStudentMapEntity.getIqTestEntity();
		super.setIqTestNm(quizEntity.getIqTestNm());
		
	}
	
	public Long getIqTestId() {
        return iqTestId;
    }

    public void setIqTestId(Long iqTestId) {
        this.iqTestId = iqTestId;
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

    public List<QuesItemResultModel> getListQuesResult() {
        return listQuesResult;
    }

    public void setListQuesItemResult(List<QuesItemResultModel> listQuesResult) {
        this.listQuesResult = listQuesResult;
    }

    public List<IqTestQuesModel> getListQues() {
        return listQues;
    }

    public void setListQues(List<IqTestQuesModel> listQues) {
        this.listQues = listQues;
    }

	public Boolean getTested() {
		return tested;
	}

	public void setTested(Boolean tested) {
		this.tested = tested;
	}

	public void setListQuesResult(List<QuesItemResultModel> listQuesResult) {
		this.listQuesResult = listQuesResult;
	}

    public Long getNonUserId() {
        return nonUserId;
    }

    public void setNonUserId(Long nonUserId) {
        this.nonUserId = nonUserId;
    }

    public String getIqTestTime() {
        return iqTestTime;
    }

    public void setIqTestTime(String iqTestTime) {
        this.iqTestTime = iqTestTime;
    }
	
}
 
