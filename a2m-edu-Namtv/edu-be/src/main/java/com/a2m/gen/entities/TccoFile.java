package com.a2m.gen.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TCCO_FILE")
public class TccoFile {
	
	@Id
    @Column(name = "ATCH_FLE_SEQ")
    private String atchFleSeq;

    @Column(name = "FLE_KEY")
    private String fleKey;

    @Column(name = "FLE_TP")
    private String fleTp;

    @Column(name = "FLE_PATH")
    private String flePath;

    @Column(name = "FLE_NM")
    private String fleNm;

    @Column(name = "NEW_FLE_NM")
    private String newFleNm;

    @Column(name = "FLE_SZ")
    private String fleSz;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

	public TccoFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TccoFile(String atchFleSeq, String fleKey, String fleTp, String flePath, String fleNm, String newFleNm,
			String fleSz, String createdBy, Date createdDate, String updatedBy, Date updatedDate) {
		super();
		this.atchFleSeq = atchFleSeq;
		this.fleKey = fleKey;
		this.fleTp = fleTp;
		this.flePath = flePath;
		this.fleNm = fleNm;
		this.newFleNm = newFleNm;
		this.fleSz = fleSz;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
	}

	public String getAtchFleSeq() {
		return atchFleSeq;
	}

	public void setAtchFleSeq(String atchFleSeq) {
		this.atchFleSeq = atchFleSeq;
	}

	public String getFleKey() {
		return fleKey;
	}

	public void setFleKey(String fleKey) {
		this.fleKey = fleKey;
	}

	public String getFleTp() {
		return fleTp;
	}

	public void setFleTp(String fleTp) {
		this.fleTp = fleTp;
	}

	public String getFlePath() {
		return flePath;
	}

	public void setFlePath(String flePath) {
		this.flePath = flePath;
	}

	public String getFleNm() {
		return fleNm;
	}

	public void setFleNm(String fleNm) {
		this.fleNm = fleNm;
	}

	public String getNewFleNm() {
		return newFleNm;
	}

	public void setNewFleNm(String newFleNm) {
		this.newFleNm = newFleNm;
	}

	public String getFleSz() {
		return fleSz;
	}

	public void setFleSz(String fleSz) {
		this.fleSz = fleSz;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

    
}
