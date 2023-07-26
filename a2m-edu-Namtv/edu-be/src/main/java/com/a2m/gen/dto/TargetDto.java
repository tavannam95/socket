package com.a2m.gen.dto;

import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.TableInfo;
import com.a2m.gen.entities.TccoStd;

public class TargetDto {
	private String targetId;
	private String ip;
	private int port;
	private String username;
	private String password;
	private Boolean status;
	private TccoStd connectType;
	private Date createdDate;
	private Date updatedDate;
	private String databaseName;
	private String userUid;
	private String fileSavePath;
	private String targetName;
	private String projectId;
	private String fileType;
	private List<TableInfo> tableInfos;
	
	
	public TargetDto() {
		super();
		// TODO Auto-generated constructor stub
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
	
	public String getProjectId() {
		return projectId;
	}
	
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getFileType() {
		return fileType;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public List<TableInfo> getTableInfos() {
		return tableInfos;
	}

	public void setTableInfos(List<TableInfo> tableInfos) {
		this.tableInfos = tableInfos;
	}
	
}
