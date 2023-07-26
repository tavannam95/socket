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

@Entity
@Table(name = "EAM_COURSE_SUBJECT_CHAPTER")
public class AemCouSbjChapter extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CHAPTER_ID")
	private Long chapterId;

	@Column(name = "DOCUMENT_STATUS")
	private String documentStatus;
	
	@Column(name = "CHAPTER_NM")
	private String chapterNm;
	
	@Column(name = "TOTAL_LESSON")
	private Long totalLesson;
	
	@Column(name = "ORGANIZATION_FORMAL")
	private String organizationFormal;
	
	@Column(name = "TAGS")
	private String tags;

	@Column(name = "START_DT")
	private Date startDt;

	@Column(name = "END_DT")
	private Date endDt;

	@Column(name = "CHAPTER_CONTENT")
	private String chapterContent;
	
	@Column(name = "CHAPTER_TYPE")
	private String chapterType;

	@Column(name = "ORD_NO")
	private Integer odrNo;

    @Column(name = "STATUS")
    private Boolean status;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "SUBJECT_ID")
	private AemCourseSubject aemCouSubject;

	@OneToMany(mappedBy = "aemCouSubChapter", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<AemCouSbjChtrLecture> couSbjChtrLectures = new ArrayList<AemCouSbjChtrLecture>();
	
	@OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<QuizEntity> listQuiz = new ArrayList<QuizEntity>();

    @OneToMany(mappedBy = "chapterStand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AemCourseSubjectStandardNote> listStandNote = new ArrayList<AemCourseSubjectStandardNote>();

	public AemCouSbjChapter() {
		super();
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

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public String getChapterNm() {
		return chapterNm;
	}

	public void setChapterNm(String chapterNm) {
		this.chapterNm = chapterNm;
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

	public String getChapterContent() {
		return chapterContent;
	}

	public void setChapterContent(String chapterContent) {
		this.chapterContent = chapterContent;
	}


	public Boolean getStatus() {
		return status;
	}

    public void setStatus(Boolean status) {
		this.status = status;
	}

	public AemCourseSubject getAemCouSubject() {
		return aemCouSubject;
	}

	public void setAemCouSubject(AemCourseSubject aemCouSubject) {
		this.aemCouSubject = aemCouSubject;
	}

	public List<AemCouSbjChtrLecture> getCouSbjChtrLectures() {
		return couSbjChtrLectures;
	}

	public void setCouSbjChtrLectures(List<AemCouSbjChtrLecture> couSbjChtrLectures) {
		this.couSbjChtrLectures = couSbjChtrLectures;
	}

	public List<QuizEntity> getListQuiz() {
		return listQuiz;
	}

	public void setListQuiz(List<QuizEntity> listQuiz) {
		this.listQuiz = listQuiz;
	}

    public List<AemCourseSubjectStandardNote> getListStandNote() {
        return listStandNote;
    }

    public void setListStandNote(List<AemCourseSubjectStandardNote> listStandNote) {
        this.listStandNote = listStandNote;
    }

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
