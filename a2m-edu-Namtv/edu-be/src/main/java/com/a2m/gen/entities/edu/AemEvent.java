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
import com.a2m.gen.models.edu.EventModel;

@Entity
@Table(name = "EAM_EVENT")
public class AemEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EVENT_ID")
	private Long eventId;
	
	@Column(name = "EVENT_TITLE")
	private String eventTitle;

	@Column(name = "EVENT_CONTENT")
	private String eventContent;
	
	@Column(name = "FILE_PATH")
	private String filePath;
	
	@Column(name = "STATUS")
	private Boolean eventStatus;
		
	@Column(name = "INS_DT")
	private Date insDt;
	
	@Column(name = "UPD_DT")
	private Date upDt;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "COURSE_ID")
	private AemCourse eventCourse;

	@OneToMany(mappedBy = "candiDateEvent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AemCandidate> listCandidateEvent = new ArrayList<AemCandidate>();

	public AemEvent() {
		super();
	}

	
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getEventContent() {
		return eventContent;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Boolean getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(Boolean eventStatus) {
		this.eventStatus = eventStatus;
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

	public AemCourse getEventCourse() {
		return eventCourse;
	}

	public void setEventCourse(AemCourse eventCourse) {
		this.eventCourse = eventCourse;
	}


	public List<AemCandidate> getListCandidateEvent() {
		return listCandidateEvent;
	}


	public void setListCandidateEvent(List<AemCandidate> listCandidateEvent) {
		this.listCandidateEvent = listCandidateEvent;
	}

}
