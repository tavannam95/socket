package com.a2m.gen.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "GEN_TARGET_MGT")
public class TargetMgt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TARGET_ID")
	private String targetId;
	@Column(name = "IP")
	private String ip;
	@Column(name = "PORT")
	private int port;
	@Column(name = "USERNAME")
	private String username;
	@Column(name = "PWD")
	private String password;
	@Column(name = "STATUS")
	private Boolean status;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	@Column(name = "DATABASE_NAME")
	private String databaseName;
	@Column(name = "USER_UID")
	private String userUid;
	@Column(name = "FILE_SAVE_PATH")
	private String fileSavePath;
	@Column(name = "TARGET_NAME")
	private String targetName;
//	@Column(name = "PROJECT_ID")
//	private String projectId;
	@Column(name = "FILE_TYPE")
	private String fileType;

	@ManyToOne()
	@JoinColumn(name = "CONNECT_TYPE", referencedColumnName = "COMM_CD")
	private TccoStd connectType;

	@OneToMany(mappedBy = "target")
	private List<TableInfo> tableInfos;

	@JsonIgnoreProperties({ "targets" })
	@ManyToOne
	@JoinColumn(name = "PROJECT_ID", referencedColumnName = "PROJECT_ID")
	private ProjectMgt project;

	public TargetMgt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<TableInfo> getTableInfos() {
		return tableInfos;
	}

	public void setTableInfos(List<TableInfo> tableInfos) {
		this.tableInfos = tableInfos;
	}

	public ProjectMgt getProject() {
		return project;
	}

	public void setProject(ProjectMgt project) {
		this.project = project;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public TccoStd getConnectType() {
		return connectType;
	}

	public void setConnectType(TccoStd connectType) {
		this.connectType = connectType;
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

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public String getFileSavePath() {
		return fileSavePath;
	}

	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = fileSavePath;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

//	public String getProjectId() {
//		return projectId;
//	}
//
//	public void setProjectId(String projectId) {
//		this.projectId = projectId;
//	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
