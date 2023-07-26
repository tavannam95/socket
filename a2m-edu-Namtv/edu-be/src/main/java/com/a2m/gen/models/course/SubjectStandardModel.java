package com.a2m.gen.models.course;

import java.util.ArrayList;
import java.util.List;

import com.a2m.gen.entities.edu.AemCourseSubjectStandard;
import com.a2m.gen.entities.edu.AemCourseSubjectStandardHistory;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author doanhq
 */
public class SubjectStandardModel extends ParamBaseModel{
	
	private Long standId;
	
	private Long subjectId;
	
	private Long standardHistoryId;
	
	private Boolean isApproval;
	
	private String documentStatus;

    private String standCode;
    
	private String standContent;
    
    private String standResult;
    
	private String standType;
	
	private Boolean status;

    private List<SubjectStandardNoteModel> listStandNote = new ArrayList<SubjectStandardNoteModel>();
    
    private Boolean isHistory;
    
	public SubjectStandardModel() {
		super();
	}
	
	public SubjectStandardModel(AemCourseSubjectStandard db) {
		this.standId = db.getStandId();
		this.key = db.getStandId();
		this.subjectId = db.getSubjectStand().getSubjectId();
        this.standCode = db.getStandCode();
        this.standContent = db.getStandContent();
        this.standType = db.getStandType();
		this.status = db.getStatus();
		this.deleteFlag = db.getDeleteFlag();
		this.insUid = db.getInsUid();
	}
	
	public SubjectStandardModel(AemCourseSubjectStandard db, Boolean isGetAllData) {
		this.standId = db.getStandId();
	    this.key = db.getStandId();
        this.subjectId = db.getSubjectStand().getSubjectId();
        this.standCode = db.getStandCode();
        this.standContent = db.getStandContent();
        this.standType = db.getStandType();
        this.status = db.getStatus();
        this.deleteFlag = db.getDeleteFlag();
        this.insUid = db.getInsUid();
	}   

	public SubjectStandardModel(SubjectStandardHistoryModel standHis, Long key) {
		this.standId = key;
	    this.key = key;
        this.subjectId = standHis.getSubjectId();
        this.standCode = standHis.getStandCode();
        this.standContent = standHis.getStandContent();
        this.standType = standHis.getStandType();
        this.status = standHis.getStatus();
        this.deleteFlag = standHis.getDeleteFlag();
        this.insUid = standHis.getInsUid();
	}

	public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getStandCode() {
        return standCode;
    }

    public void setStandCode(String standCode) {
        this.standCode = standCode;
    }

    public String getStandContent() {
        return standContent;
    }

    public void setStandContent(String standContent) {
        this.standContent = standContent;
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

    public String getStandResult() {
        return standResult;
    }

    public void setStandResult(String standResult) {
        this.standResult = standResult;
    }

    public List<SubjectStandardNoteModel> getListStandNote() {
        return listStandNote;
    }

    public void setListStandNote(List<SubjectStandardNoteModel> listStandNote) {
        this.listStandNote = listStandNote;
    }

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public Boolean getIsApproval() {
		return isApproval;
	}

	public void setIsApproval(Boolean isApproval) {
		this.isApproval = isApproval;
	}

	public Long getStandardHistoryId() {
		return standardHistoryId;
	}

	public void setStandardHistoryId(Long standardHistoryId) {
		this.standardHistoryId = standardHistoryId;
	}

	public Long getStandId() {
		return standId;
	}

	public void setStandId(Long standId) {
		this.standId = standId;
	}


	public Boolean getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(Boolean isHistory) {
		this.isHistory = isHistory;
	}
    
}
