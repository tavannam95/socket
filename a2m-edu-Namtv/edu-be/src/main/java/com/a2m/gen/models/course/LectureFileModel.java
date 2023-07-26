package com.a2m.gen.models.course;

import com.a2m.gen.entities.edu.AemCscLectureFile;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.common.TccoFileModel;

public class LectureFileModel extends ParamBaseModel{
	
	private Long lectureId;
	
	private String fileType;
	
	private String fileId;
	
	private TccoFileModel tccoFileModel;
	
	private String crud;

	public Long getLectureId() {
		return lectureId;
	}

	public void setLectureId(Long lectureId) {
		this.lectureId = lectureId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public TccoFileModel getTccoFileModel() {
		return tccoFileModel;
	}

	public void setTccoFileModel(TccoFileModel tccoFileModel) {
		this.tccoFileModel = tccoFileModel;
	}



	public String getCrud() {
		return crud;
	}

	public void setCrud(String crud) {
		this.crud = crud;
	}

	public LectureFileModel(AemCscLectureFile db) {
		this.key = db.getLectureFileId();
		this.lectureId = db.getLectureId();
		this.fileType = db.getFileType();
		this.fileId = db.getFileId();
		this.tccoFileModel = new TccoFileModel(db.getTccoFile());
		this.crud = "R";
	}

	public LectureFileModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}	
