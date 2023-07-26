package com.a2m.gen.models.iqTest;

import java.util.ArrayList;
import java.util.List;

import com.a2m.gen.entities.edu.QuizStudentMapEntity;
import com.a2m.gen.entities.iqTest.AemNonUser;
import com.a2m.gen.entities.iqTest.QuesItemResultEntity;
import com.a2m.gen.entities.iqTest.QuesStudentMapEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.TsstUserModel;
import com.a2m.gen.models.edu.StudentModel;

/**
 *
 * @author doanhq
 */
public class QuesStudentMapModel extends ParamBaseModel{
	
	private TsstUserModel tsstUserModel;
	private NonUserModel nonUser;
	private Long totalCorrectAnswer;
	private Long totalWrongAnswer;
	private Long totalNotChoose;
	private Boolean status;
	private List<QuesItemResultModel> listQuesResult;
	
	
	public QuesStudentMapModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QuesStudentMapModel(QuesStudentMapEntity entity) {
		// TODO Auto-generated constructor stub
		this.key = entity.getQuesStudentId();
		if(entity.getTsstUser() != null && entity.getNonUser() == null) {
	        this.tsstUserModel = new TsstUserModel(entity.getTsstUser());
            this.nonUser = null;
		}else if(entity.getNonUser() != null && entity.getTsstUser() == null){
		    this.tsstUserModel = null;
            this.nonUser = new NonUserModel(entity.getNonUser());
		}else {
            this.tsstUserModel = null;
            this.nonUser = null;
		}
		this.totalCorrectAnswer = entity.getTotalCorrectAnswer();
		this.totalWrongAnswer = entity.getTotalWrongAnswer();
		this.totalNotChoose = entity.getTotalNotChoose();
		this.listQuesResult = new ArrayList<QuesItemResultModel>();
        List<QuesItemResultEntity> listQuesItemResult = entity.getListQuesItemResultEntity();
        for(int i = 0; i < listQuesItemResult.size(); i++) {
            QuesItemResultEntity quesResult = listQuesItemResult.get(i);
            QuesItemResultModel quesItemResult = new QuesItemResultModel(quesResult);
            listQuesResult.add(quesItemResult);
        }
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

    public TsstUserModel getTsstUserModel() {
        return tsstUserModel;
    }

    public void setTsstUserModel(TsstUserModel tsstUserModel) {
        this.tsstUserModel = tsstUserModel;
    }

    public NonUserModel getNonUser() {
        return nonUser;
    }

    public void setNonUser(NonUserModel nonUser) {
        this.nonUser = nonUser;
    }

    public List<QuesItemResultModel> getListQuesResult() {
        return listQuesResult;
    }

    public void setListQuesResult(List<QuesItemResultModel> listQuesResult) {
        this.listQuesResult = listQuesResult;
    }

}
