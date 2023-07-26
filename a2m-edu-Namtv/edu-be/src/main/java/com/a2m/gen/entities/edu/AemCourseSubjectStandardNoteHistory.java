package com.a2m.gen.entities.edu;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;
import com.a2m.gen.models.course.SubjectStandardModel;

@Entity
@Table(name = "EAM_COURSE_SUBJECT_STANDARD_RESULT_HISTORY")
public class AemCourseSubjectStandardNoteHistory extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NOTE_HISTORY_ID")
	private Long noteHistoryId;

	@Column(name = "NOTE_ID")
    private Long noteId;

    @Column(name = "CHAPTER_ID")
    private Long chapterId;
    
    @Column(name = "STANDARD_HISTORY_ID")
    private Long standHistoryId;
    
    @Column(name = "STAND_TYPE")
    private String StandType;
    
	@Column(name = "STAND_RESULT")
	private String standResult;
	
	@Column(name = "STATUS")
	private Boolean status;


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Long getNoteHistoryId() {
		return noteHistoryId;
	}


	public void setNoteHistoryId(Long noteHistoryId) {
		this.noteHistoryId = noteHistoryId;
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


	public String getStandType() {
		return StandType;
	}


	public void setStandType(String standType) {
		StandType = standType;
	}


	public String getStandResult() {
		return standResult;
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

}
