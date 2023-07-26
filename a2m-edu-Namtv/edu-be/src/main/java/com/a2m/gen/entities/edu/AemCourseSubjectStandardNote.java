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
@Table(name = "EAM_COURSE_SUBJECT_STANDARD_RESULT")
public class AemCourseSubjectStandardNote extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NOTE_ID")
	private Long noteId;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "STAND_ID")
    private AemCourseSubjectStandard standNote;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "CHAPTER_ID")
    private AemCouSbjChapter chapterStand;

    @Column(name = "STAND_TYPE")
    private String StandType;
    
	@Column(name = "STAND_RESULT")
	private String standResult;
	
	@Column(name = "STATUS")
	private Boolean status;

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public AemCourseSubjectStandard getStandNote() {
        return standNote;
    }

    public void setStandNote(AemCourseSubjectStandard standNote) {
        this.standNote = standNote;
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

    public AemCouSbjChapter getChapterStand() {
        return chapterStand;
    }

    public void setChapterStand(AemCouSbjChapter chapterStand) {
        this.chapterStand = chapterStand;
    }

}
