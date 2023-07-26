package com.a2m.gen.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "GEN_TABLE_INFO")
public class TableInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TABLE_ID")
	private Long tableId;
	@Column(name = "TABLE_NM")
	private String tableName;
	@Column(name = "LIST_COLUMN")
	private String listColumn;
//	@Column(name = "TARGET_ID")
//	private String targetId;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "TARGET_ID", referencedColumnName = "TARGET_ID")
	private TargetMgt target;

	public TableInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TargetMgt getTarget() {
		return target;
	}

	public void setTarget(TargetMgt target) {
		this.target = target;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getListColumn() {
		return listColumn;
	}

	public void setListColumn(String listColumn) {
		this.listColumn = listColumn;
	}

//	public String getTargetId() {
//		return targetId;
//	}
//
//	public void setTargetId(String targetId) {
//		this.targetId = targetId;
//	}

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

}
