package com.a2m.gen.entities.edu;

import java.util.Date;

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
import com.a2m.gen.models.edu.CandidateModel;

@Entity
@Table(name = "EAM_CANDIDATE")
public class AemCandidate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CANDIDATE_ID")
	private Long candidateId;
	
	@Column(name = "CANDIDATE_NAME")
	private String candidateName;
	
	@Column(name = "CANDIDATE_EMAIL")
	private String candidateEmail;
	
	@Column(name = "CANDIDATE_PHONE")
	private String candidatePhone;
	
	@Column(name = "CANDIDATE_MESSAGE")
	private String candidateMessage;
	
	@Column(name = "FILE_PATH")
	private String candidateFilePath;
	
	@Column(name = "PROGRESS_TYPE")
	private String candidateprogressType;
	
	@Column(name = "COURSE_NM")
    private String courseNm;
	
	@Column(name = "STATUS")
	private Boolean candidateStatus;
	
	@Column(name = "INS_DT")
	private Date insDt;
	
	@Column(name = "UPD_DT")
	private Date upDt;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "COURSE_ID")
	private AemCourse candiDateCourse;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "EVENT_ID")
	private AemEvent candiDateEvent;


	public Long getCandidateId() {
		return candidateId;
	}

	public void setAllParamFormModel(CandidateModel model) {
		this.candidateName = model.getCandidateName();
		this.candidateEmail = model.getCandidateEmail();
		this.candidatePhone = model.getCandidatePhone();
		this.candidateMessage = model.getCandidateMessage();
		this.candidateFilePath = model.getCandidateFilePath();
		this.candidateprogressType = model.getCandidateprogressType();
		this.candidateStatus = model.getCandidateStatus();
//		this.courseNm = model.getCourseNm();
	}


	public AemCandidate(Long candidateId, String candidateName, String candidateEmail, String candidatePhone,
			String candidateMessage, String candidateFilePath, String candidateprogressType, Boolean candidateStatus,
			Date candidateInsDt, Date candidateUpdDt) {
		super();
		this.candidateId = candidateId;
		this.candidateName = candidateName;
		this.candidateEmail = candidateEmail;
		this.candidatePhone = candidatePhone;
		this.candidateMessage = candidateMessage;
		this.candidateFilePath = candidateFilePath;
		this.candidateprogressType = candidateprogressType;
		this.candidateStatus = candidateStatus;

	}


	public AemCandidate() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Date getInsDt() {
		return insDt;
	}

	public void setInsDt(Date insDt) {
		this.insDt = insDt;
	}

	public Date getUpDt() {
		return upDt;
	}

	public void setUpDt(Date upDt) {
		this.upDt = upDt;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getCandidatePhone() {
		return candidatePhone;
	}

	public void setCandidatePhone(String candidatePhone) {
		this.candidatePhone = candidatePhone;
	}

	public String getCandidateMessage() {
		return candidateMessage;
	}

	public void setCandidateMessage(String candidateMessage) {
		this.candidateMessage = candidateMessage;
	}

	public String getCandidateFilePath() {
		return candidateFilePath;
	}

	public void setCandidateFilePath(String candidateFilePath) {
		this.candidateFilePath = candidateFilePath;
	}

	public String getCandidateprogressType() {
		return candidateprogressType;
	}

	public void setCandidateprogressType(String candidateprogressType) {
		this.candidateprogressType = candidateprogressType;
	}

	public Boolean getCandidateStatus() {
		return candidateStatus;
	}


	public void setCandidateStatus(Boolean candidateStatus) {
		this.candidateStatus = candidateStatus;
	}

  public String getCourseNm() {
    return courseNm;
  }

  public void setCourseNm(String courseNm) {
    this.courseNm = courseNm;
  }

public AemCourse getCandiDateCourse() {
	return candiDateCourse;
}

public void setCandiDateCourse(AemCourse candiDateCourse) {
	this.candiDateCourse = candiDateCourse;
}

public AemEvent getCandiDateEvent() {
	return candiDateEvent;
}

public void setCandiDateEvent(AemEvent candiDateEvent) {
	this.candiDateEvent = candiDateEvent;
}
  
}
