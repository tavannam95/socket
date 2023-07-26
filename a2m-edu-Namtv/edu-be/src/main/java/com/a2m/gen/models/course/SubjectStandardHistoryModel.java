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
public class SubjectStandardHistoryModel extends ParamBaseModel{
	
	private String documentStatus;
	
	private Boolean isApproval;
	
	private Long standId;
	
	private Long standHistoryId;
	
	private Long subjectId;

    private String standCode;
    
	private String standContent;
    
    private String standResult;
    
	private String standType;
	
	private Boolean status;
	
	private Boolean isHistory;

    private List<SubjectStandardResultHistoryModel> listStandNote = new ArrayList<SubjectStandardResultHistoryModel>();
    
	public SubjectStandardHistoryModel() {
		super();
	}
	
	public SubjectStandardHistoryModel(AemCourseSubjectStandard db) {
		this.documentStatus = db.getDocumentStatus();
		this.isApproval = db.getIsApproval();
		this.standId = db.getStandId();		
		this.subjectId = db.getSubjectStand().getSubjectId();
        this.standCode = db.getStandCode();
        this.standContent = db.getStandContent();
        this.standType = db.getStandType();
		this.status = db.getStatus();
		this.deleteFlag = db.getDeleteFlag();
		this.insUid = db.getInsUid();
		this.insDate = db.getInsDt();
		this.updDate = db.getUpdDt();
		this.updUid = db.getUpdUid();
	}

    public SubjectStandardHistoryModel(AemCourseSubjectStandardHistory db) {
    	this.key = db.getStandardHistoryId();
		this.documentStatus = db.getDocumentStatus();
		this.isApproval = db.getIsApproval();
		this.standId = db.getStandId();	
		this.standHistoryId = db.getStandardHistoryId();
		this.subjectId = db.getSubjectId();
        this.standCode = db.getStandCode();
        this.standContent = db.getStandContent();
        this.standType = db.getStandType();
		this.status = db.getStatus();
		this.deleteFlag = db.getDeleteFlag();
		this.insUid = db.getInsUid();
		this.insDate = db.getInsDt();
		this.updDate = db.getUpdDt();
		this.updUid = db.getUpdUid();
		this.isHistory = true;
	}

	public SubjectStandardHistoryModel(SubjectStandardModel subjectStandardModel) {
		this.key = subjectStandardModel.getStandardHistoryId();
		this.documentStatus = subjectStandardModel.getDocumentStatus();
		this.isApproval = subjectStandardModel.getIsApproval();
		this.standId = subjectStandardModel.getStandId();		
		this.subjectId = subjectStandardModel.getSubjectId();
        this.standCode = subjectStandardModel.getStandCode();
        this.standContent = subjectStandardModel.getStandContent();
        this.standType = subjectStandardModel.getStandType();
		this.status = subjectStandardModel.getStatus();
		this.deleteFlag = subjectStandardModel.getDeleteFlag();
		this.insUid = subjectStandardModel.getInsUid();
		this.insDate = subjectStandardModel.getInsDate();
		this.updDate = subjectStandardModel.getUpdDate();
		this.updUid = subjectStandardModel.getUpdUid();
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

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public Long getStandId() {
		return standId;
	}

	public List<SubjectStandardResultHistoryModel> getListStandNote() {
        return listStandNote;
    }

    public void setListStandNote(List<SubjectStandardResultHistoryModel> listStandNote) {
        this.listStandNote = listStandNote;
    }

    public void setStandId(Long standId) {
		this.standId = standId;
	}

	public Boolean getIsApproval() {
		return isApproval;
	}

	public void setIsApproval(Boolean isApproval) {
		this.isApproval = isApproval;
	}

	public Boolean getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(Boolean isHistory) {
		this.isHistory = isHistory;
	}

	public Long getStandHistoryId() {
		return standHistoryId;
	}

	public void setStandHistoryId(Long standHistoryId) {
		this.standHistoryId = standHistoryId;
	}
    
}
