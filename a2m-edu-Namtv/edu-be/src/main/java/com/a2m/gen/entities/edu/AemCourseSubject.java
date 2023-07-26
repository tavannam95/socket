package com.a2m.gen.entities.edu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.a2m.gen.entities.DatabaseCommonModel;
import com.a2m.gen.entities.TsstUser;
import com.a2m.gen.models.course.SubjectModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "EAM_COURSE_SUBJECT")
public class AemCourseSubject extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SUBJECT_ID")
	private Long subjectId;
	
	@Column(name = "DOCUMENT_STATUS")
	private String documentStatus;

	@Column(name = "SUBJECT_NM")
	private String subjectNm;

    @Column(name = "SUBJECT_CODE")
    private String subjectCode;

	@Column(name = "START_DT")
	private Date startDt;

	@Column(name = "END_DT")
	private Date endDt;

	@Column(name = "SUBJECT_CONTENT")
	private String subjectContent;

	@Column(name = "SUBJECT_GOAL")
	private String subjectGoal;
	
	@Column(name = "APPROVAL_HISTORY_ID")
	private Long approvalHistoryId;

	@Column(name = "ORD_NO")
	private Long ordNo;

	@Column(name = "STATUS")
	private Boolean status;

//	@ManyToOne(fetch = FetchType.EAGER, optional = false)
//	@JoinColumn(name = "COURSE_ID")
//	private AemCourse eamCourse;

	@OneToMany(mappedBy = "aemCouSubject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("aemCouSubject")
	private List<AemCourseSubjectMap> listOfCouSubjectMap = new ArrayList<AemCourseSubjectMap>();
	
	@OneToMany(mappedBy = "aemCouSubject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemCouSbjChapter> couSbjChapters = new ArrayList<AemCouSbjChapter>();
	
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<QuizEntity> listQuiz = new ArrayList<QuizEntity>();

    @OneToMany(mappedBy = "subjectStand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AemCourseSubjectStandard> listStand = new ArrayList<AemCourseSubjectStandard>();

    @OneToMany(mappedBy = "subjectSchedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AemSchedule> listScheduleOfSubject = new ArrayList<AemSchedule>();

//
//    @OneToMany(mappedBy = "subjectNote", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<AemCourseSubjectStandardNote> listNote = new ArrayList<AemCourseSubjectStandardNote>();
//    

    @ManyToOne
    @JoinColumn(name = "TEACHER_INFO_ID", nullable = false)
    private AemTeacherEntity teacherEntity;
    
    
    @ManyToOne
    @JoinColumn(name = "USER_UID", nullable = false)
    private TsstUser tsstUser;
    
	public AemCourseSubject() {
		super();
	}

    public AemCourseSubject(SubjectModel subjectModel) {
        this.subjectId = subjectModel.getKey();
        this.subjectNm = subjectModel.getSubjectNm();
        this.subjectContent = subjectModel.getSubjectCode();
        this.startDt = subjectModel.getStartDt();
        this.endDt = subjectModel.getEndDt();
        this.subjectContent = subjectModel.getSubjectContent();
        this.subjectGoal = subjectModel.getSubjectGoal();
        this.ordNo = subjectModel.getOrdNo();
        this.status = subjectModel.getStatus();
        this.listOfCouSubjectMap = subjectModel.getListOfCourseSubMap();
    }

    
    
	public TsstUser getTsstUser() {
		return tsstUser;
	}

	public void setTsstUser(TsstUser tsstUser) {
		this.tsstUser = tsstUser;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public AemTeacherEntity getTeacherEntity() {
		return teacherEntity;
	}

	public void setTeacherEntity(AemTeacherEntity teacherEntity) {
		this.teacherEntity = teacherEntity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(Long ordNo) {
		this.ordNo = ordNo;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<AemCouSbjChapter> getCouSbjChapters() {
		return couSbjChapters;
	}

	public void setCouSbjChapters(List<AemCouSbjChapter> couSbjChapters) {
		this.couSbjChapters = couSbjChapters;
	}

    public List<AemCourseSubjectMap> getListOfCouSubjectMap() {
        return listOfCouSubjectMap;
    }

    public void setListOfCouSubjectMap(List<AemCourseSubjectMap> listOfCouSubjectMap) {
        this.listOfCouSubjectMap = listOfCouSubjectMap;
    }

	public List<QuizEntity> getListQuiz() {
		return listQuiz;
	}

	public void setListQuiz(List<QuizEntity> listQuiz) {
		this.listQuiz = listQuiz;
	}

    public List<AemCourseSubjectStandard> getListStand() {
        return listStand;
    }

    public void setListStand(List<AemCourseSubjectStandard> listStand) {
        this.listStand = listStand;
    }

	public Long getApprovalHistoryId() {
		return approvalHistoryId;
	}

	public void setApprovalHistoryId(Long approvalHistoryId) {
		this.approvalHistoryId = approvalHistoryId;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
    
}
