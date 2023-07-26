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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "EAM_IQ_QUES")
public class IqTestQuesEntity extends DatabaseCommonModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUES_ID")
    private Long quesId;

    @Column(name = "QUES_NM")
    private String quesNm;

    @Column(name = "QUES_CONTENT")
    private String quesContent;

    @Column(name = "QUES_CATEGORY")
    private String quesCategory;

    @Column(name = "QUES_TYPE")
    private String quesType;

    @Column(name = "LEV")
    private String quesLevel;

    @Column(name = "STATUS")
    private Boolean status;

    @OneToMany(mappedBy = "iqTestQues", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<IqTestAnswerEntity> listOfAnswerByQues = new ArrayList<IqTestAnswerEntity>();
    
//    @OneToMany(mappedBy = "iTestQuesEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JsonIgnoreProperties("iTestQuesEntity")
//	private List<AemIqTestMap> listIqTestMap = new ArrayList<AemIqTestMap>();
    public IqTestQuesEntity() {
        super();
    }

    public Long getQuesId() {
        return quesId;
    }

    public void setQuesId(Long quesId) {
        this.quesId = quesId;
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

    public String getQuesLevel() {
        return quesLevel;
    }

    public void setQuesLevel(String quesLevel) {
        this.quesLevel = quesLevel;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getQuesContent() {
        return quesContent;
    }

    public void setQuesContent(String quesContent) {
        this.quesContent = quesContent;
    }

    public String getQuesNm() {
        return quesNm;
    }

    public void setQuesNm(String quesNm) {
        this.quesNm = quesNm;
    }

    public List<IqTestAnswerEntity> getListOfAnswerByQues() {
        return listOfAnswerByQues;
    }

    public void setListOfAnswerByQues(List<IqTestAnswerEntity> listOfAnswerByQues) {
        this.listOfAnswerByQues = listOfAnswerByQues;
    }

}
