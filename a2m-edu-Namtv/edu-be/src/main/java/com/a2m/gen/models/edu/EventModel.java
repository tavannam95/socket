package com.a2m.gen.models.edu;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.a2m.gen.entities.TccoFile;
import com.a2m.gen.entities.edu.AemCandidate;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemEvent;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author phongnc
 */
public class EventModel extends ParamBaseModel{	
	
	private String eventTitle;
	
	private String eventContent;

	private Edu0102RequestModel courseModel;
	
	private String filePath;
	
	private Boolean status;
	
	public EventModel(AemEvent aemEvent) {
		super();
		this.key = aemEvent.getEventId();
		this.eventTitle = aemEvent.getEventTitle();
		this.eventContent = aemEvent.getEventContent();
		this.filePath = aemEvent.getFilePath();
		this.status = aemEvent.getEventStatus();
		AemCourse courseEntity = aemEvent.getEventCourse();
		this.courseModel = new Edu0102RequestModel(courseEntity);
	}
	
	public EventModel() {
		super();
		// TODO Auto-generated constructor stub
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Edu0102RequestModel getCourseModel() {
		return courseModel;
	}

	public void setCourseModel(Edu0102RequestModel courseModel) {
		this.courseModel = courseModel;
	}
	
}
