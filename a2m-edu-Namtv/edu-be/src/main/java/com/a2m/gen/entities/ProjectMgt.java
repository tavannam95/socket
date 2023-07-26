package com.a2m.gen.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "GEN_PROJECT_MGT")

public class ProjectMgt {
	@Id
	@Column(name = "PROJECT_ID")
	private String projectId;
	@Column(name = "GEN_CYCLE")
	private String genCycle;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "PROJECT_NAME")
	private String projectName;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	@Column(name = "TOPIC")
	private String topic;
	@Column(name = "DATA_FILE_PATH")
	private String dataFilePath;
	@Column(name = "CREATED_BY")
	private String createdBy;

	@JsonIgnoreProperties({ "project" })
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private List<TargetMgt> targets;

	public ProjectMgt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDataFilePath() {
		return dataFilePath;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}

	public List<TargetMgt> getTargets() {
		return targets;
	}

	public void setTargets(List<TargetMgt> targets) {
		this.targets = targets;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getGenCycle() {
		return genCycle;
	}

	public void setGenCycle(String genCycle) {
		this.genCycle = genCycle;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
