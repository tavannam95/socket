package com.a2m.gen.models.common;

import com.a2m.gen.entities.TccoFile;
import com.a2m.gen.models.ParamBaseModel;

public class TccoFileModel extends ParamBaseModel {
	private String atchFleSeq;
	private String fleKey;
	private String fleTp;
	private String flePath;
	private String fleNm;
	private String newFleNm;
	private String fleSz;
	
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
	public TccoFileModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TccoFileModel(TccoFile db) {
		this.atchFleSeq = db.getAtchFleSeq();
		this.fleKey = db.getFleKey();
		this.fleTp = db.getFleTp();
		this.flePath = db.getFlePath();
		this.fleNm = db.getFleNm();
		this.newFleNm = db.getNewFleNm();
		this.fleSz = db.getFleSz();
	}	
}
