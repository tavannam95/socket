package com.a2m.gen.models.course;

import com.a2m.gen.entities.edu.AemCourseSubjectStandard;
import com.a2m.gen.entities.edu.AemCourseSubjectStandardNote;
import com.a2m.gen.entities.edu.AemCourseSubjectStandardNoteHistory;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author doanhq
 */
public class SubjectStandardNoteModel extends ParamBaseModel{
	
	private Long standId;
	
	private Long standHistoryId;
	
	private Long ChapterId;

    private String standType;
    
    private String standResult;

    private String standResultNm;
	
	private Boolean status;
	
	public SubjectStandardNoteModel() {
		super();
	}
	
	public SubjectStandardNoteModel(AemCourseSubjectStandardNote db) {
		this.key = db.getNoteId();
		this.standId = db.getStandNote().getStandId();
		this.ChapterId = db.getChapterStand().getChapterId();
		this.standType = db.getStandType();
		this.standResult = db.getStandResult();
		this.status = db.getStatus();
		this.deleteFlag = db.getDeleteFlag();
		this.insUid = db.getInsUid();
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
	
	public SubjectStandardNoteModel(AemCourseSubjectStandardNote db, Boolean isGetAllData) {
	    this.key = db.getNoteId();
        this.standId = db.getStandNote().getStandId();
        this.ChapterId = db.getChapterStand().getChapterId();
        this.standType = db.getStandType();
        this.standResult = db.getStandResult();
        this.status = db.getStatus();
        this.deleteFlag = db.getDeleteFlag();
        this.insUid = db.getInsUid();
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

    public SubjectStandardNoteModel(AemCourseSubjectStandardNoteHistory noteHis, Long key, Long standId) {
    	this.key = key;
        this.standId = standId;
        this.ChapterId = noteHis.getChapterId();
        this.standType = noteHis.getStandType();
        this.standResult = noteHis.getStandResult();
        this.status = noteHis.getStatus();
        this.deleteFlag = noteHis.getDeleteFlag();
        this.insUid = noteHis.getInsUid();
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

	/**
	 * @param noteHis
	 */
	public SubjectStandardNoteModel(SubjectStandardResultHistoryModel noteHis) {
		this.ChapterId = noteHis.getChapterId();
		this.standHistoryId = noteHis.getStandHistoryId();
		this.standResult = noteHis.getStandResult();
		this.standResultNm = noteHis.getStandResultNm();
		this.standType = noteHis.getStandType();
		this.status = noteHis.getStatus();
	}

	public Long getStandId() {
        return standId;
    }

    public void setStandId(Long standId) {
        this.standId = standId;
    }

    public String getStandResult() {
        return standResult;
    }

    public Long getChapterId() {
        return ChapterId;
    }

    public void setChapterId(Long chapterId) {
        ChapterId = chapterId;
    }

    public String getStandType() {
        return standType;
    }

    public void setStandType(String standType) {
        this.standType = standType;
    }

    public void setStandResult(String standResult) {
        this.standResult = standResult;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

	public Long getStandHistoryId() {
		return standHistoryId;
	}

	public void setStandHistoryId(Long standHistoryId) {
		this.standHistoryId = standHistoryId;
	}

	public String getStandResultNm() {
		return standResultNm;
	}

	public void setStandResultNm(String standResultNm) {
		this.standResultNm = standResultNm;
	}

}
