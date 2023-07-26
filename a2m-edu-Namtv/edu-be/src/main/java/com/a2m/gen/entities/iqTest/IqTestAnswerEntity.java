package com.a2m.gen.entities.iqTest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;

@Entity
@Table(name = "EAM_IQ_ANSWER")
public class IqTestAnswerEntity extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWER_ID")
    private Long answerId;

    @Column(name = "ANSWER_CONTENT")
    private String answerContent;

    @Column(name = "IS_ANSWER_CORRECT")
    private boolean answerCorrect;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "QUES_ID")
    private IqTestQuesEntity iqTestQues;

    public IqTestAnswerEntity() {
        super();
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public boolean getAnswerCorrect() {
        return answerCorrect;
    }

    public void setAnswerCorrect(boolean answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    public IqTestQuesEntity getIqTestQues() {
        return iqTestQues;
    }

    public void setIqTestQues(IqTestQuesEntity iqTestQues) {
        this.iqTestQues = iqTestQues;
    }

}
