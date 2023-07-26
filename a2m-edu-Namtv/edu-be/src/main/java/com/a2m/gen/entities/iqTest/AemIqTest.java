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
import com.a2m.gen.entities.edu.QuizStudentMapEntity;

@Entity
@Table(name = "EAM_IQ_TEST_LIST")
public class AemIqTest extends DatabaseCommonModel {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IQTEST_ID")
    private Long iqTestId;

    @Column(name = "IQTEST_TIME")
    private String iqTestTime;

    @Column(name = "IQTEST_NM")
    private String iqTestNm;

    @Column(name = "STATUS")
    private Boolean status;

	@OneToMany(mappedBy = "aemIqTest", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<AemIqTestMap> listIqTestMap = new ArrayList<AemIqTestMap>();
    
    @OneToMany(mappedBy = "iqTestEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QuesStudentMapEntity> quesStudentMapEntities = new ArrayList<QuesStudentMapEntity>();

    public AemIqTest() {
        super();
    }

    public Long getIqTestId() {
        return iqTestId;
    }

    public void setIqTestId(Long iqTestId) {
        this.iqTestId = iqTestId;
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

    public List<AemIqTestMap> getListIqTestMap() {
        return listIqTestMap;
    }

    public void setListIqTestMap(List<AemIqTestMap> listIqTestMap) {
        this.listIqTestMap = listIqTestMap;
    }

    public List<QuesStudentMapEntity> getQuesStudentMapEntities() {
        return quesStudentMapEntities;
    }

    public void setQuesStudentMapEntities(List<QuesStudentMapEntity> quesStudentMapEntities) {
        this.quesStudentMapEntities = quesStudentMapEntities;
    }
    
}
