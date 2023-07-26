package com.a2m.gen.models.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.a2m.gen.entities.edu.AemCouSbjChapter;
import com.a2m.gen.entities.edu.AemCouSbjChtrLecture;
import com.a2m.gen.entities.edu.AemCourseSubjectStandard;
import com.a2m.gen.entities.edu.AemCourseSubjectStandardNote;
import com.a2m.gen.entities.edu.QuizEntity;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author doanhq
 */
public class SbjChapterModel extends ParamBaseModel {

    private Long subjectId;
    
    private String documentStatus;
    
    private String chapterNm;
    
	private Long totalLesson;
	
	private String organizationFormal;
	
	private String tags;

    private String chapterContent;
    
	private String chapterType;

	private Integer odrNo;

    private Boolean status;

    private Date startDt;

    private Date endDt;
    
	private List<ChapterFileModel> chapterFileModels = new ArrayList<ChapterFileModel>();


//    public String courseUid;

    private List<LectureModel> lectureModels = new ArrayList<LectureModel>();

    private List<QuizModel> listQuiz = new ArrayList<QuizModel>();

    private List<SubjectStandardModel> listStand = new ArrayList<SubjectStandardModel>();

    private List<SubjectStandardNoteModel> listStandNote = new ArrayList<SubjectStandardNoteModel>();
    
    private List<SubjectStandardResultHistoryModel> listStandNoteHis = new ArrayList<SubjectStandardResultHistoryModel>();

    public SbjChapterModel() {
        super();
    }

    public SbjChapterModel(AemCouSbjChapter db) {
        this.key = db.getChapterId();
        this.documentStatus = db.getDocumentStatus();
        this.chapterNm = db.getChapterNm();
        this.chapterContent = db.getChapterContent();
        this.startDt = db.getStartDt();
        this.endDt = db.getEndDt();
        this.status = db.getStatus();
        this.subjectId = db.getAemCouSubject().getSubjectId();
        this.deleteFlag = db.getDeleteFlag();
        this.totalLesson = db.getTotalLesson();
        this.organizationFormal = db.getOrganizationFormal();
        this.tags = db.getTags();
        this.chapterType = db.getChapterType();
        this.odrNo = db.getOdrNo();
        this.insUid = db.getInsUid();
        this.insDate = db.getInsDt();
        List<AemCourseSubjectStandardNote> listNoteEntity = db.getListStandNote();
        List<AemCourseSubjectStandard> listStandEntity = db.getAemCouSubject().getListStand();
        

        if (listNoteEntity.size() != 0) {
            for (AemCourseSubjectStandardNote note : listNoteEntity) {
                AemCourseSubjectStandard standard = note.getStandNote();
                if(standard.getDeleteFlag()) {
                    note.setDeleteFlag(true);
                }else {
                    note.setDeleteFlag(false);
                }
                SubjectStandardNoteModel noteModel = new SubjectStandardNoteModel(note);
                if(!noteModel.getDeleteFlag()) {
                    this.listStandNote.add(noteModel);
                }
            }
            for (AemCourseSubjectStandard stand : listStandEntity) {
                List<AemCourseSubjectStandardNote> standNote = stand.getListStandNote();
                if (standNote.size() == 0) {
                    SubjectStandardNoteModel noteModels = new SubjectStandardNoteModel();
                    noteModels.setChapterId(db.getChapterId());
                    noteModels.setStandId(stand.getStandId());
                    noteModels.setStandType(stand.getStandType());
                    noteModels.setStatus(true);
                    noteModels.setStandResult("13-01");
                    if(!stand.getDeleteFlag()) {
                        this.listStandNote.add(noteModels);
                    }
                }
            }
        } else {
            for (AemCourseSubjectStandard stand : listStandEntity) {
                SubjectStandardModel standModel = new SubjectStandardModel(stand);
                this.listStand.add(standModel);
                SubjectStandardNoteModel noteModel = new SubjectStandardNoteModel();
                noteModel.setChapterId(db.getChapterId());
                noteModel.setStandId(stand.getStandId());
                noteModel.setStandType(stand.getStandType());
                noteModel.setStatus(true);
                noteModel.setStandResult("13-01");
                if(!stand.getDeleteFlag()) {
                    this.listStandNote.add(noteModel);
                }
            }
        }

        List<AemCouSbjChtrLecture> lectures = db.getCouSbjChtrLectures();
        for (AemCouSbjChtrLecture aemCouSbjChtrLecture : lectures) {
            LectureModel lectureModel = new LectureModel(aemCouSbjChtrLecture);
            lectureModels.add(lectureModel);
        }

        List<QuizEntity> listQuizs = db.getListQuiz();
        for (QuizEntity quizEntity : listQuizs) {
            QuizModel quizModel = new QuizModel(quizEntity);
            this.listQuiz.add(quizModel);
        }
        
    }

    public SbjChapterModel(AemCouSbjChapter db, Boolean isGetAllData) {
        this.key = db.getChapterId();
        this.documentStatus = db.getDocumentStatus();
        this.chapterNm = db.getChapterNm();
        this.chapterContent = db.getChapterContent();
        this.startDt = db.getStartDt();
        this.endDt = db.getEndDt();
        this.status = db.getStatus();
        this.subjectId = db.getAemCouSubject().getSubjectId();
        this.deleteFlag = db.getDeleteFlag();
        this.insUid = db.getInsUid();
        List<AemCouSbjChtrLecture> lectures = db.getCouSbjChtrLectures();
        for (AemCouSbjChtrLecture aemCouSbjChtrLecture : lectures) {
            LectureModel lectureModel = new LectureModel(aemCouSbjChtrLecture);
            lectureModels.add(lectureModel);
        }

        List<QuizEntity> listQuizs = db.getListQuiz();
        for (QuizEntity quizEntity : listQuizs) {
            QuizModel quizModel = new QuizModel(quizEntity);
            this.listQuiz.add(quizModel);
        }
    }
    
    
    
    

    public List<ChapterFileModel> getChapterFileModels() {
		return chapterFileModels;
	}

	public void setChapterFileModels(List<ChapterFileModel> chapterFileModels) {
		this.chapterFileModels = chapterFileModels;
	}

	public Long getTotalLesson() {
		return totalLesson;
	}

	public void setTotalLesson(Long totalLesson) {
		this.totalLesson = totalLesson;
	}

	public String getOrganizationFormal() {
		return organizationFormal;
	}

	public void setOrganizationFormal(String organizationFormal) {
		this.organizationFormal = organizationFormal;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getChapterType() {
		return chapterType;
	}

	public void setChapterType(String chapterType) {
		this.chapterType = chapterType;
	}

	public Integer getOdrNo() {
		return odrNo;
	}

	public void setOdrNo(Integer odrNo) {
		this.odrNo = odrNo;
	}

	public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getChapterNm() {
        return chapterNm;
    }

    public void setChapterNm(String chapterNm) {
        this.chapterNm = chapterNm;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }


    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
//
//    public String getCourseUid() {
//        return courseUid;
//    }
//
//    public void setCourseUid(String courseUid) {
//        this.courseUid = courseUid;
//    }

    public List<LectureModel> getLectureModels() {
        return lectureModels;
    }

    public void setLectureModels(List<LectureModel> lectureModels) {
        this.lectureModels = lectureModels;
    }

    public List<QuizModel> getListQuiz() {
        return listQuiz;
    }

    public void setListQuiz(List<QuizModel> listQuiz) {
        this.listQuiz = listQuiz;
    }

    public List<SubjectStandardModel> getListStand() {
        return listStand;
    }

    public void setListStand(List<SubjectStandardModel> listStand) {
        this.listStand = listStand;
    }

    public List<SubjectStandardNoteModel> getListStandNote() {
        return listStandNote;
    }

    public void setListStandNote(List<SubjectStandardNoteModel> listStandNote) {
        this.listStandNote = listStandNote;
    }

    public List<SubjectStandardResultHistoryModel> getListStandNoteHis() {
        return listStandNoteHis;
    }

    public void setListStandNoteHis(List<SubjectStandardResultHistoryModel> listStandNoteHis) {
        this.listStandNoteHis = listStandNoteHis;
    }

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

}
