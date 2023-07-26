package com.a2m.gen.models.course;

import com.a2m.gen.entities.edu.AemCsChapterFile;
import com.a2m.gen.entities.edu.AemCscLectureFile;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.common.TccoFileModel;

public class ChapterFileModel extends ParamBaseModel{
	
	private Long chapterId;
	
	private String fileType;
	
	private String fileId;
	
	private TccoFileModel tccoFileModel;
	
	private String crud;



	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
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

	public ChapterFileModel(AemCsChapterFile db) {
		this.key = db.getChapterFileId();
		this.fileType = db.getFileType();
		this.fileId = db.getFileId();
		this.tccoFileModel = new TccoFileModel(db.getTccoFile());
		this.crud = "R";
	}

	public ChapterFileModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}	
