package com.a2m.gen.models.course;

import java.util.ArrayList;
import java.util.List;

import com.a2m.gen.entities.edu.AemCourseSubjectStandard;
import com.a2m.gen.entities.edu.AemCourseSubjectStandardHistory;
import com.a2m.gen.entities.edu.AemCourseSubjectStandardNoteHistory;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author doanhq
 */
public class SubjectStandardResultHistoryModel extends ParamBaseModel{
	
	private Long noteId;
	
	private Long chapterId;
	
	private Long standHistoryId;
	
    private String standResult;

    private String standResultNm;
    
	private String standType;
	
	private Boolean status;
    
	public SubjectStandardResultHistoryModel() {
		super();
	}
	
	public SubjectStandardResultHistoryModel(AemCourseSubjectStandardNoteHistory db) {
	    this.key = db.getNoteHistoryId();
	    this.noteId = db.getNoteId();
	    this.chapterId = db.getChapterId();
	    this.standHistoryId = db.getStandHistoryId();
        this.standType = db.getStandType();
        this.standResult = db.getStandResult();
		this.status = db.getStatus();
		this.deleteFlag = db.getDeleteFlag();
		this.insUid = db.getInsUid();
		this.insDate = db.getInsDt();
		this.updDate = db.getUpdDt();
		this.updUid = db.getUpdUid();
		if(this.standResult.equals("13-01")) {
			this.standResultNm = "A";
		}
		if(this.standResult.equals("13-02")) {
			this.standResultNm = "B";
		}
		if(this.standResult.equals("13-03")) {
			this.standResultNm = "C";
		}
	}
	
    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public Long getStandHistoryId() {
        return standHistoryId;
    }

    public void setStandHistoryId(Long standHistoryId) {
        this.standHistoryId = standHistoryId;
    }

    public String getStandResult() {
        return standResult;
    }

    public void setStandResult(String standResult) {
        this.standResult = standResult;
    }

    public String getStandType() {
        return standType;
    }

    public void setStandType(String standType) {
        this.standType = standType;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

	public String getStandResultNm() {
		return standResultNm;
	}

	public void setStandResultNm(String standResultNm) {
		this.standResultNm = standResultNm;
	}

}
