package com.a2m.gen.models.edu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.entities.edu.AemClassTeacherMap;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemRoom;
import com.a2m.gen.entities.edu.AemSchedule;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.course.SubjectModel;

/**
 *
 * @author phongnc
 */
public class ScheduleModel extends ParamBaseModel {
	private Long roomId;

	private Long classId;
	
	private Long subjectId;

	private Date startDate;
	
	private Date endDate;

    private Date usageDate;
    
	private Boolean status;
	
    private Boolean isFinish;
    
    private String roomNm;

    private String classNm;

    private String subjectNm;
	
	private String  startTime;
	
	private String  endTime;
	
    private SubjectModel subjectModel; 

    private ClassModel classModel; 

    private RoomModel roomModel; 
    

	public ScheduleModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScheduleModel(AemSchedule db) {
	    this.key = db.getScheduleId();
	    this.roomId = db.getRoomSchedule().getRoomId();
	    this.classId = db.getClassSchedule().getClassId();
	    this.subjectId = db.getSubjectSchedule().getSubjectId();
		this.startDate = db.getStartDate();
        this.usageDate = db.getStartDate();
		this.endDate = db.getEndDate();
		this.status = db.getStatus();
		this.isFinish = db.getIsFinish();
		this.insUid = db.getInsUid();
		this.roomNm = db.getRoomSchedule().getRoomNm();
		this.classNm = db.getClassSchedule().getClassNm();
        this.subjectNm = db.getSubjectSchedule().getSubjectNm();
        AemClass aemClass = db.getClassSchedule();
        this.classModel = new ClassModel(aemClass, true);
        AemRoom aemRoom = db.getRoomSchedule();
        this.roomModel = new RoomModel(aemRoom);
        AemCourseSubject AemCourseSubject = db.getSubjectSchedule();
        this.subjectModel = new SubjectModel(AemCourseSubject);
        
	}

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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

    public Date getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(Date usageDate) {
        this.usageDate = usageDate;
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

    public String getRoomNm() {
        return roomNm;
    }

    public void setRoomNm(String roomNm) {
        this.roomNm = roomNm;
    }

    public String getClassNm() {
        return classNm;
    }

    public void setClassNm(String classNm) {
        this.classNm = classNm;
    }

    public String getSubjectNm() {
        return subjectNm;
    }

    public void setSubjectNm(String subjectNm) {
        this.subjectNm = subjectNm;
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

	public SubjectModel getSubjectModel() {
        return subjectModel;
    }

    public void setSubjectModel(SubjectModel subjectModel) {
        this.subjectModel = subjectModel;
    }

    public ClassModel getClassModel() {
        return classModel;
    }

    public void setClassModel(ClassModel classModel) {
        this.classModel = classModel;
    }

    public RoomModel getRoomModel() {
        return roomModel;
    }

    public void setRoomModel(RoomModel roomModel) {
        this.roomModel = roomModel;
    }	

}
