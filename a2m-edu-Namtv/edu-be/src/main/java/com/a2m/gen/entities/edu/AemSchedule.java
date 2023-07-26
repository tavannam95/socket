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
import com.a2m.gen.models.edu.ClassModel;

@Entity
@Table(name = "EAM_SCHEDULE")
public class AemSchedule extends DatabaseCommonModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SCHEDULE_ID")
	private Long scheduleId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROOM_ID")
    private AemRoom roomSchedule;
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CLASS_ID")
	private AemClass classSchedule;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBJECT_ID")       
	private AemCourseSubject subjectSchedule;

	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "STATUS")
	private Boolean status;
	
	@Column(name = "ISFINISH")
	private Boolean isFinish;
	
	public AemSchedule() {
		super();
	}

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public AemRoom getRoomSchedule() {
        return roomSchedule;
    }

    public void setRoomSchedule(AemRoom roomSchedule) {
        this.roomSchedule = roomSchedule;
    }

    public AemClass getClassSchedule() {
        return classSchedule;
    }

    public void setClassSchedule(AemClass classSchedule) {
        this.classSchedule = classSchedule;
    }

    public AemCourseSubject getSubjectSchedule() {
        return subjectSchedule;
    }

    public void setSubjectSchedule(AemCourseSubject subjectSchedule) {
        this.subjectSchedule = subjectSchedule;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Boolean isFinish) {
        this.isFinish = isFinish;
    }

}
