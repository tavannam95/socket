package com.a2m.gen.models.edu;

import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.TccoFile;
import com.a2m.gen.entities.edu.AemCandidate;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemEvent;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author phongnc
 */
public class CandidateModel extends ParamBaseModel {

	private String candidateName;
	
	private String candidateEmail;
	
	private String candidatePhone;
	
	private String candidateMessage;
	
	private String candidateFilePath;
	
	private String candidateprogressType;
	
	private Boolean candidateStatus;
	  
	private Boolean isExportAll;
	
	private TccoFile infoFile;
	
	private Long courseId;
	
	private String courseNm;
	  
	private List<Integer> listCheckedId;
  
	public String progressType;
	
	private String  startTime;
	
	private String  endTime;
	
	private Long eventId;
	
	private Edu0102RequestModel courseModel;
	
	private EventModel eventModel;
	
	
  public CandidateModel(String candidateName, String candidateEmail, String candidatePhone, String candidateMessage,
      String candidateFilePath, String candidateprogressType, Boolean candidateStatus, Date candidateInsDt,
      Date candidateUpdDt) {
    super();
    this.candidateName = candidateName;
    this.candidateEmail = candidateEmail;
    this.candidatePhone = candidatePhone;
    this.candidateMessage = candidateMessage;
    this.candidateFilePath = candidateFilePath;
    this.candidateprogressType = candidateprogressType;
    this.candidateStatus = candidateStatus;

  }

  public CandidateModel(AemCandidate aemCandidate) {
    this.key = aemCandidate.getCandidateId();
    this.candidateName = aemCandidate.getCandidateName();
    this.candidateEmail = aemCandidate.getCandidateEmail();
    this.candidatePhone = aemCandidate.getCandidatePhone();
    this.candidateMessage = aemCandidate.getCandidateMessage();
    this.candidateFilePath = aemCandidate.getCandidateFilePath();
    this.candidateprogressType = aemCandidate.getCandidateprogressType();
    this.candidateStatus = aemCandidate.getCandidateStatus();
    this.courseNm = aemCandidate.getCourseNm();
    this.insDate = aemCandidate.getInsDt();
    this.updDate = aemCandidate.getUpDt();
	AemCourse courseEntity = aemCandidate.getCandiDateCourse();
	if(courseEntity != null) {
		this.courseModel = new Edu0102RequestModel(courseEntity);
	}else {
		this.courseModel = new Edu0102RequestModel();
	}
	AemEvent eventEntity = aemCandidate.getCandiDateEvent();
	if(eventEntity != null) {
		this.eventModel = new EventModel(eventEntity);
	}else {
		this.eventModel = new EventModel();
	}
  }

  public CandidateModel() {
    super();
    // TODO Auto-generated constructor stub
  }
  
  
  

  public Boolean getIsExportAll() {
	return isExportAll;
}

public void setIsExportAll(Boolean isExportAll) {
	this.isExportAll = isExportAll;
}

public String getProgressType() {
	return progressType;
}

public void setProgressType(String progressType) {
	this.progressType = progressType;
}

public String getStartTime() {
	return startTime;
}

public void setStartTime(String startTime) {
	this.startTime = startTime;
}

public String getEndTime() {
	return endTime;
}

public void setEndTime(String endTime) {
	this.endTime = endTime;
}

public TccoFile getInfoFile() {
    return infoFile;
  }
  
  public List<Integer> getListCheckedId() {
	return listCheckedId;
}

public void setListCheckedId(List<Integer> listCheckedId) {
	this.listCheckedId = listCheckedId;
}

public void setInfoFile(TccoFile infoFile) {
    this.infoFile = infoFile;
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

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public String getCourseNm() {
    return courseNm;
  }

  public void setCourseNm(String courseNm) {
    this.courseNm = courseNm;
  }

public Long getEventId() {
	return eventId;
}

public void setEventId(Long eventId) {
	this.eventId = eventId;
}

public Edu0102RequestModel getCourseModel() {
	return courseModel;
}

public void setCourseModel(Edu0102RequestModel courseModel) {
	this.courseModel = courseModel;
}

public EventModel getEventModel() {
	return eventModel;
}

public void setEventModel(EventModel eventModel) {
	this.eventModel = eventModel;
}

}
