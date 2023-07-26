package com.a2m.gen.entities.iqTest;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;

@Entity
@Table(name = "EAM_QUES_ITEM_ANSWER_RESULT")
public class QuesItemAnswerResultEntity extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ANSWER_RESULT_ID")
	private Long quesItemAnswerResultId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "QUES_ITEM_RESULT_ID")
	private QuesItemResultEntity quesItemResultEntity;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ANSWER_ID")
	private IqTestAnswerEntity iqTestAnswerEntity;
	
	@Column(name = "IS_CHOOSE")
	private Boolean isChoose;
	
	public QuesItemAnswerResultEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

    public Long getQuesItemAnswerResultId() {
        return quesItemAnswerResultId;
    }

    public void setQuesItemAnswerResultId(Long quesItemAnswerResultId) {
        this.quesItemAnswerResultId = quesItemAnswerResultId;
    }

    public QuesItemResultEntity getQuesItemResultEntity() {
        return quesItemResultEntity;
    }

    public void setQuesItemResultEntity(QuesItemResultEntity quesItemResultEntity) {
        this.quesItemResultEntity = quesItemResultEntity;
    }

    public IqTestAnswerEntity getIqTestAnswerEntity() {
        return iqTestAnswerEntity;
    }

    public void setIqTestAnswerEntity(IqTestAnswerEntity iqTestAnswerEntity) {
        this.iqTestAnswerEntity = iqTestAnswerEntity;
    }

    public Boolean getIsChoose() {
        return isChoose;
    }

    public void setIsChoose(Boolean isChoose) {
        this.isChoose = isChoose;
    }


}
