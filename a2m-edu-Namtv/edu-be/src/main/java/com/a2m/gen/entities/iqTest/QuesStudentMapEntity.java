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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;
import com.a2m.gen.entities.TsstUser;

@Entity
@Table(name = "EAM_QUES_STUDENT_MAP")
public class QuesStudentMapEntity extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUES_STUDENT_ID")
	private Long quesStudentId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IQTEST_ID")
	private AemIqTest iqTestEntity;
//	
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "STUDENT_INFO_ID")
//	private AemStudentEntity studentEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_UID")
    private TsstUser tsstUser;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "NON_USER_ID")
    private AemNonUser nonUser;
    
	@OneToMany(mappedBy = "quesStudentMapEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<QuesItemResultEntity> listQuesItemResultEntity = new ArrayList<QuesItemResultEntity>();

	@Column(name = "TOTAL_CORRECT_ANSWER")
	private Long totalCorrectAnswer;
	
	@Column(name = "TOTAL_WRONG_ANSWER")
	private Long totalWrongAnswer;
	
	@Column(name = "TOTAL_NOT_CHOOSE")
	private Long totalNotChoose;
	
	@Column(name = "STATUS")
	private Boolean status;
	
	public QuesStudentMapEntity() {
		super();
	}

    public Long getQuesStudentId() {
        return quesStudentId;
    }

    public void setQuesStudentId(Long quesStudentId) {
        this.quesStudentId = quesStudentId;
    }

    public AemIqTest getIqTestEntity() {
        return iqTestEntity;
    }

    public void setIqTestEntity(AemIqTest iqTestEntity) {
        this.iqTestEntity = iqTestEntity;
    }
//
//    public AemStudentEntity getStudentEntity() {
//        return studentEntity;
//    }
//
//    public void setStudentEntity(AemStudentEntity studentEntity) {
//        this.studentEntity = studentEntity;
//    }

    public List<QuesItemResultEntity> getListQuesItemResultEntity() {
        return listQuesItemResultEntity;
    }

    public TsstUser getTsstUser() {
        return tsstUser;
    }

    public void setTsstUser(TsstUser tsstUser) {
        this.tsstUser = tsstUser;
    }

    public void setListQuesItemResultEntity(List<QuesItemResultEntity> listQuesItemResultEntity) {
        this.listQuesItemResultEntity = listQuesItemResultEntity;
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

    public AemNonUser getNonUser() {
        return nonUser;
    }

    public void setNonUser(AemNonUser nonUser) {
        this.nonUser = nonUser;
    }

}
