package com.a2m.gen.entities.edu;

import java.util.ArrayList;
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
@Table(name = "EAM_COURSE_SUBJECT_CHAPTER_LECTURES")
public class AemCouSbjChtrLecture extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LECTURES_ID")
	private Long lectureId;

	@Column(name = "LECTURES_NM")
	private String lectureNm;
	
	@Column(name = "TOTAL_LESSON")
	private Long totalLesson;
	
	@Column(name = "TYPE_DOCUMENT")
	private String typeDocument;
	
	@Column(name = "TAGS")
	private String tags;
	
	@Column(name = "LECTURES_CONTENT")
	private String lectureContent;

	@Column(name = "LECTURES_GOAL")
	private String lectureGoal;

	@Column(name = "LECTURES_TYPE")
	private String lectureType;

	@Column(name = "LINK_VIDEO")
	private String linkVideo;

	@Column(name = "ORD_NO")
	private Integer odrNo;

	@Column(name = "STATUS")
	private Boolean status;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "CHAPTER_ID")
	private AemCouSbjChapter aemCouSubChapter;

	@OneToMany(mappedBy = "aemCouSbjChtrLecture", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemDoUserMapEntity> listViewDoc = new ArrayList<AemDoUserMapEntity>();

	public AemCouSbjChtrLecture() {
		super();
	}

	public Long getLectureId() {
		return lectureId;
	}

	public void setLectureId(Long lectureId) {
		this.lectureId = lectureId;
	}

	public String getLectureNm() {
		return lectureNm;
	}

	public void setLectureNm(String lectureNm) {
		this.lectureNm = lectureNm;
	}

	public String getLectureContent() {
		return lectureContent;
	}

	public void setLectureContent(String lectureContent) {
		this.lectureContent = lectureContent;
	}

	public String getLectureGoal() {
		return lectureGoal;
	}

	public void setLectureGoal(String lectureGoal) {
		this.lectureGoal = lectureGoal;
	}

	public String getLectureType() {
		return lectureType;
	}

	public void setLectureType(String lectureType) {
		this.lectureType = lectureType;
	}

	public Integer getOdrNo() {
		return odrNo;
	}

	public void setOdrNo(Integer odrNo) {
		this.odrNo = odrNo;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public AemCouSbjChapter getAemCouSubChapter() {
		return aemCouSubChapter;
	}

	public void setAemCouSubChapter(AemCouSbjChapter aemCouSubChapter) {
		this.aemCouSubChapter = aemCouSubChapter;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Long getTotalLesson() {
		return totalLesson;
	}

	public void setTotalLesson(Long totalLesson) {
		this.totalLesson = totalLesson;
	}

	public String getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(String typeDocument) {
		this.typeDocument = typeDocument;
	}

	public String getLinkVideo() {
		return linkVideo;
	}

	public void setLinkVideo(String linkVideo) {
		this.linkVideo = linkVideo;
	}

	public List<AemDoUserMapEntity> getListViewDoc() {
		return listViewDoc;
	}

	public void setListViewDoc(List<AemDoUserMapEntity> listViewDoc) {
		this.listViewDoc = listViewDoc;
	}

}
