package com.a2m.gen.models.edu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.entities.edu.AemClassTeacherMap;
import com.a2m.gen.models.ParamBaseModel;

/**
 *
 * @author phongnc
 */
public class ClassModel extends ParamBaseModel {
	private String className;

	private String classType;
	
	private String classCode;

	private Boolean classStatus;
	
	private Boolean isFinish;

	private Boolean isStart;
	
	private Long courseId;
	
	private Date startDate;
	
	private Date endDate;

	private List<String> listStudentId;
	
	private List<String> listTeacherId;
	
	private List<ClassStudentMapModel> classStudentMapModels = new ArrayList<ClassStudentMapModel>();
	
	private List<ClassTeacherMapModel> classTeacherMapModels = new ArrayList<ClassTeacherMapModel>();
	
	private Edu0102RequestModel courseModel ;
	
	List<StudentModel> lstStudentModel = new ArrayList<StudentModel>();

	public ClassModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassModel(AemClass entity) {
		this.key = entity.getClassId();
		this.startDate = entity.getStartDate();
		this.classCode = entity.getClassCode();
		this.endDate = entity.getEndDate();
		this.key = entity.getClassId();
		this.className = entity.getClassNm();
		this.classStatus = entity.getStatus();
		this.isFinish = entity.getIsFinish();
		this.isStart = entity.getIsStart();
		this.classType = entity.getClassType();
		this.courseId = entity.getCourse().getCourseId();
		this.insUid = entity.getInsUid();
//		this.courseModel = new Edu0102RequestModel(entity.getCourse()) ;
		this.courseModel = new Edu0102RequestModel(entity.getCourse(),true, true) ;
//		List<AemClassStudentMap> listOfClassStudentMap = entity.getListOfClassStudentMap();
//		for(AemClassStudentMap stuMap : listOfClassStudentMap) {
//            ClassStudentMapModel classStudentMapModel = new ClassStudentMapModel(stuMap);
//            this.classStudentMapModels.add(classStudentMapModel);
//        }
	}	

    public ClassModel(AemClass entity, Boolean isGetAllData) {
        this.key = entity.getClassId();
        this.startDate = entity.getStartDate();
        this.classCode = entity.getClassCode();
        this.endDate = entity.getEndDate();
        this.key = entity.getClassId();
        this.className = entity.getClassNm();
        this.classStatus = entity.getStatus();
        this.isFinish = entity.getIsFinish();
		this.isStart = entity.getIsStart();
        this.classType = entity.getClassType();
        this.courseId = entity.getCourse().getCourseId();
        this.insUid = entity.getInsUid();
//      this.courseModel = new Edu0102RequestModel(entity.getCourse()) ;
        this.courseModel = new Edu0102RequestModel(entity.getCourse(),true, true) ;
        List<AemClassStudentMap> listOfClassStudentMap = entity.getListOfClassStudentMap();
        for(AemClassStudentMap stuMap : listOfClassStudentMap) {
            ClassStudentMapModel classStudentMapModel = new ClassStudentMapModel(stuMap);
            this.classStudentMapModels.add(classStudentMapModel);
        }
        
    }   
	
	public ClassModel(String className, String classType, String classCode, Boolean classStatus, Long courseId,
			Date startDate, Date endDate, List<String> listStudentId, List<String> listTeacherId,
			List<ClassStudentMapModel> classStudentMapModels, List<ClassTeacherMapModel> classTeacherMapModels,
			Edu0102RequestModel courseModel, List<StudentModel> lstStudentModel) {
		super();
		this.className = className;
		this.classType = classType;
		this.classCode = classCode;
		this.classStatus = classStatus;
		this.courseId = courseId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.listStudentId = listStudentId;
		this.listTeacherId = listTeacherId;
		this.classStudentMapModels = classStudentMapModels;
		this.classTeacherMapModels = classTeacherMapModels;
		this.courseModel = courseModel;
		this.lstStudentModel = lstStudentModel;
	}
	
	

	public Boolean getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Boolean isFinish) {
		this.isFinish = isFinish;
	}

	public Edu0102RequestModel getCourseModel() {
		return courseModel;
	}

	public void setCourseModel(Edu0102RequestModel courseModel) {
		this.courseModel = courseModel;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
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

	public List<ClassStudentMapModel> getClassStudentMapModels() {
		return classStudentMapModels;
	}

	public void setClassStudentMapModels(List<ClassStudentMapModel> classStudentMapModels) {
		this.classStudentMapModels = classStudentMapModels;
	}

	public List<ClassTeacherMapModel> getClassTeacherMapModels() {
		return classTeacherMapModels;
	}

	public void setClassTeacherMapModels(List<ClassTeacherMapModel> classTeacherMapModels) {
		this.classTeacherMapModels = classTeacherMapModels;
	}

	public List<String> getListTeacherId() {
		return listTeacherId;
	}

	public void setListTeacherId(List<String> listTeacherId) {
		this.listTeacherId = listTeacherId;
	}

	public List<StudentModel> getLstStudentModel() {
		return lstStudentModel;
	}

	public void setLstStudentModel(List<StudentModel> lstStudentModel) {
		this.lstStudentModel = lstStudentModel;
	}

	public List<String> getListStudentId() {
		return listStudentId;
	}

	public void setListStudentId(List<String> listStudentId) {
		this.listStudentId = listStudentId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public Boolean getClassStatus() {
		return classStatus;
	}

	public void setClassStatus(Boolean classStatus) {
		this.classStatus = classStatus;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Boolean getIsStart() {
		return isStart;
	}

	public void setIsStart(Boolean isStart) {
		this.isStart = isStart;
	}

}
