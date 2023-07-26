package com.a2m.gen.models.course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.entities.edu.AemCouSbjChapter;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.entities.edu.AemCourseSubjectStandard;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.common.ApproveModel;
import com.a2m.gen.models.edu.TeacherModel;

/**
 *
 * @author doanhq
 */
public class SubjectModel extends ParamBaseModel{
	
	private Long courseId;
	
	private String documentStatus;

	private String subjectNm;

    private String subjectCode;
    
	private String subjectContent;
	
	private String subjectGoal;
	
	private Date startDt;
	
	private Date endDt;
	
	private Long ordNo;
	
	private Boolean status;
	
	private TeacherModel  teacherModel;
	
	private TsstUser tsstUser;
	
	public String courseUid;
	   
    private List<String> listSubjectId;
    
    private List<AemCourseSubjectMap> listOfCourseSubMap = new ArrayList<AemCourseSubjectMap>();
	
	private List<SbjChapterModel> chapterModels = new ArrayList<SbjChapterModel>();
	
//	private List<SubjectStandardNoteModel> listNote = new ArrayList<SubjectStandardNoteModel>();

    private List<SubjectStandardModel> listStand = new ArrayList<SubjectStandardModel>();
    
    private ApproveModel approveHistory;
    
    private Long approvalHistoryId;
    
    private List<CourseSelectModel> listCourse = new ArrayList<CourseSelectModel>();
    
	public SubjectModel() {
		super();
	}
	
	public SubjectModel(AemCourseSubject db) {
		this.key = db.getSubjectId();
		this.documentStatus = db.getDocumentStatus();
		this.subjectNm = db.getSubjectNm();
		this.subjectCode = db.getSubjectCode();
		this.subjectContent = db.getSubjectContent();
		this.subjectGoal = db.getSubjectGoal();
		this.startDt = db.getStartDt();
		this.endDt = db.getEndDt();
		this.ordNo = db.getOrdNo();
		this.status = db.getStatus();
		this.deleteFlag = db.getDeleteFlag();
		this.insUid = db.getInsUid();
		this.teacherModel = new TeacherModel(db.getTeacherEntity());
		this.tsstUser = db.getTsstUser();
		List<AemCourseSubjectStandard> listStandEntity = db.getListStand();
		Collections.sort(listStandEntity, new Comparator<AemCourseSubjectStandard>() {
            @Override
            public int compare(AemCourseSubjectStandard u1, AemCourseSubjectStandard u2) {
              return u1.getStandType().compareTo(u2.getStandType());
            }
          });
		for(AemCourseSubjectStandard stand : listStandEntity) {
		    SubjectStandardModel standModel = new SubjectStandardModel(stand);
		    if(!standModel.getDeleteFlag()) {
	            this.listStand.add(standModel);
		    }
		}
//		List<AemCourseSubjectStandardNote> listNoteEntity = db.getListNote();
//        for(AemCourseSubjectStandardNote note : listNoteEntity) {
//            SubjectStandardNoteModel noteModel = new SubjectStandardNoteModel(note);
//            if(!noteModel.getDeleteFlag()) {
//                this.listNote.add(noteModel);
//            }
//        }
        List<AemCouSbjChapter> ListChapterEntity = db.getCouSbjChapters();
        for(AemCouSbjChapter chapter : ListChapterEntity) {
            SbjChapterModel chapterModel = new SbjChapterModel(chapter);
            if(!chapterModel.getDeleteFlag()) {
//                chapterModel.setListStand(listStand);
                this.chapterModels.add(chapterModel);
            }
        }
	}
	
	public SubjectModel(AemCourseSubject db, Boolean isGetAllData) {
		this.key = db.getSubjectId();
		this.documentStatus = db.getDocumentStatus();
		this.subjectNm = db.getSubjectNm();
        this.subjectCode = db.getSubjectCode();
		this.subjectContent = db.getSubjectContent();
		this.subjectGoal = db.getSubjectGoal();
		this.startDt = db.getStartDt();
		this.endDt = db.getEndDt();
		this.ordNo = db.getOrdNo();
		this.status = db.getStatus();
		this.deleteFlag = db.getDeleteFlag();
        this.insUid = db.getInsUid();
		
		List<AemCouSbjChapter> chapters = db.getCouSbjChapters();
		for (AemCouSbjChapter aemCouSbjChapter : chapters) {
			SbjChapterModel chapterModel = new SbjChapterModel(aemCouSbjChapter, true);
			chapterModels.add(chapterModel);
		}
	}

	public SubjectModel(SubjectHistoryModel modelHistory) {
		this.key = modelHistory.getSubjectId();
		this.documentStatus = modelHistory.getDocumentStatus();
		this.subjectNm = modelHistory.getSubjectNm();
        this.subjectCode = modelHistory.getSubjectCode();
		this.subjectContent = modelHistory.getSubjectContent();
		this.subjectGoal = modelHistory.getSubjectGoal();
		this.startDt = modelHistory.getStartDt();
		this.endDt = modelHistory.getEndDt();
		this.ordNo = modelHistory.getOrdNo();
		this.status = modelHistory.getStatus();
		this.deleteFlag = modelHistory.getDeleteFlag();
        this.insUid = modelHistory.getInsUid();
        this.tsstUser = modelHistory.getTsstUser();
        this.teacherModel = modelHistory.getTeacherModel();
        this.courseId = modelHistory.getCourseId();
	}

	public TsstUser getTsstUser() {
		return tsstUser;
	}

	public void setTsstUser(TsstUser tsstUser) {
		this.tsstUser = tsstUser;
	}

	public TeacherModel getTeacherModel() {
		return teacherModel;
	}

	public void setTeacherModel(TeacherModel teacherModel) {
		this.teacherModel = teacherModel;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getSubjectContent() {
		return subjectContent;
	}

	public void setSubjectContent(String subjectContent) {
		this.subjectContent = subjectContent;
	}

	public String getSubjectGoal() {
		return subjectGoal;
	}

	public void setSubjectGoal(String subjectGoal) {
		this.subjectGoal = subjectGoal;
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

	public Long getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(Long ordNo) {
		this.ordNo = ordNo;
	}

	public String getSubjectNm() {
		return subjectNm;
	}

	public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSubjectNm(String subjectNm) {
		this.subjectNm = subjectNm;
	}

	public String getCourseUid() {
        return courseUid;
    }

    public void setCourseUid(String courseUid) {
        this.courseUid = courseUid;
    }

    public List<SbjChapterModel> getChapterModels() {
		return chapterModels;
	}

	public void setChapterModels(List<SbjChapterModel> chapterModels) {
		this.chapterModels = chapterModels;
	}

    public List<String> getListSubjectId() {
        return listSubjectId;
    }

    public void setListSubjectId(List<String> listSubjectId) {
        this.listSubjectId = listSubjectId;
    }

    public List<AemCourseSubjectMap> getListOfCourseSubMap() {
        return listOfCourseSubMap;
    }

    public void setListOfCourseSubMap(List<AemCourseSubjectMap> listOfCourseSubMap) {
        this.listOfCourseSubMap = listOfCourseSubMap;
    }
//
//    public List<SubjectStandardNoteModel> getListNote() {
//        return listNote;
//    }
//
//    public void setListNote(List<SubjectStandardNoteModel> listNote) {
//        this.listNote = listNote;
//    }

    public List<SubjectStandardModel> getListStand() {
        return listStand;
    }

    public void setListStand(List<SubjectStandardModel> listStand) {
        this.listStand = listStand;
    }

	public ApproveModel getApproveHistory() {
		return approveHistory;
	}

	public void setApproveHistory(ApproveModel approveHistory) {
		this.approveHistory = approveHistory;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public Long getApprovalHistoryId() {
		return approvalHistoryId;
	}

	public void setApprovalHistoryId(Long approvalHistoryId) {
		this.approvalHistoryId = approvalHistoryId;
	}

	public List<CourseSelectModel> getListCourse() {
		return listCourse;
	}

	public void setListCourse(List<CourseSelectModel> listCourse) {
		this.listCourse = listCourse;
	}

}
