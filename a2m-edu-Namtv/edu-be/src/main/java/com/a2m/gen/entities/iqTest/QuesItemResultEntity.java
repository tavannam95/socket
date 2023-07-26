package com.a2m.gen.entities.iqTest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;
import com.a2m.gen.entities.edu.QuizStudentMapEntity;
import com.a2m.gen.models.course.QuizItemResultModel;

@Entity
@Table(name = "EAM_QUES_ITEM_RESULT")
public class QuesItemResultEntity extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUES_ITEM_RESULT_ID")
	private Long quesItemStudentId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "QUES_STUDENT_ID")
	private QuesStudentMapEntity quesStudentMapEntity;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "QUES_ID")
	private IqTestQuesEntity iqTestQuesEntity;
	
	@OneToMany(mappedBy = "quesItemResultEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<QuesItemAnswerResultEntity> listQuesItemAnswerResult = new ArrayList<QuesItemAnswerResultEntity>();

	@Column(name = "CHOOSE_ANSWER")
	private String chooseAnswer;
	
	@Column(name = "STATUS")
	private Boolean status;
	
	@Column(name = "RESULT")
	private String result;
	
	public QuesItemResultEntity() {
		super();
	}

    public Long getQuesItemStudentId() {
        return quesItemStudentId;
    }

    public void setQuesItemStudentId(Long quesItemStudentId) {
        this.quesItemStudentId = quesItemStudentId;
    }

    public QuesStudentMapEntity getQuesStudentMapEntity() {
        return quesStudentMapEntity;
    }

    public void setQuesStudentMapEntity(QuesStudentMapEntity quesStudentMapEntity) {
        this.quesStudentMapEntity = quesStudentMapEntity;
    }

    public IqTestQuesEntity getIqTestQuesEntity() {
        return iqTestQuesEntity;
    }

    public void setIqTestQuesEntity(IqTestQuesEntity iqTestQuesEntity) {
        this.iqTestQuesEntity = iqTestQuesEntity;
    }

    public List<QuesItemAnswerResultEntity> getListQuesItemAnswerResult() {
        return listQuesItemAnswerResult;
    }

    public void setListQuesItemAnswerResult(List<QuesItemAnswerResultEntity> listQuesItemAnswerResult) {
        this.listQuesItemAnswerResult = listQuesItemAnswerResult;
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

}
