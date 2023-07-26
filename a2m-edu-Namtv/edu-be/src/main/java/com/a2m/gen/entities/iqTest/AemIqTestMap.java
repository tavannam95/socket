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
@Table(name = "EAM_IQ_TEST_MAP")
public class AemIqTestMap extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TABLE_ID")
	private Long tableId;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "IQTEST_ID")
	private AemIqTest aemIqTest;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "QUES_ID")
	private IqTestQuesEntity iqTestQuesEntity;

	public AemIqTestMap() {
		super();
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public AemIqTest getAemIqTest() {
		return aemIqTest;
	}

	public void setAemIqTest(AemIqTest aemIqTest) {
		this.aemIqTest = aemIqTest;
	}

	public IqTestQuesEntity getiqTestQuesEntity() {
		return iqTestQuesEntity;
	}

	public void setiqTestQuesEntity(IqTestQuesEntity iqTestQuesEntity) {
		this.iqTestQuesEntity = iqTestQuesEntity;
	}

}
