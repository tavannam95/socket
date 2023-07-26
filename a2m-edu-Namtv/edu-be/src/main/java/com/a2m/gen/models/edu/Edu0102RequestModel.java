package com.a2m.gen.models.edu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.course.CourseInfoModel;
import com.a2m.gen.models.course.CourseSubjectMapModel;
import com.a2m.gen.models.course.SubjectModel;

/**
 *
 * @author doanhq
 */
public class Edu0102RequestModel extends ParamBaseModel {
	
	private String approvalStatus;
	
	private String courseNm;
	
	private String courseCode;

	private Date courseStartDate;

	private Date courseEndDate;

	private String courseContent;

	private String courseGoal;

	private Boolean status;

    private Boolean displayStatus;
    
	private Boolean isComingSoon;
	
	private String imgPath;
	
	private List<String> listUserUid;
	
	private CourseInfoModel courseInfoModel;
	
	private List<SubjectModel> subjectModels = new ArrayList<SubjectModel>();
	
//	private List<AemStudentCourseMap> studentCourseMaps = new ArrayList<>();
	
//	private List<StudentCourseMapModel> studentCourseMaps = new ArrayList<StudentCourseMapModel>();

    private List<CourseSubjectMapModel> courseSubjectMaps = new ArrayList<CourseSubjectMapModel>();
    
	private List<ClassModel> ListClassModel = new ArrayList<ClassModel>();
	
	public Edu0102RequestModel() {
		super();
	}

	public Edu0102RequestModel(AemCourse db) {
		this.key = db.getCourseId();
		this.courseNm = db.getCourseNm();
		this.courseCode = db.getCourseCode();
		this.courseStartDate = db.getCourseStartDt();
		this.courseEndDate = db.getCourseEndDt();
		this.courseContent = db.getCourseContent();
		this.courseGoal = db.getCourseGoal();
		this.status = db.getStatus();
		this.displayStatus = db.getDisplayStatus();
		this.isComingSoon = db.getIsComingSoon();
		this.imgPath = db.getImgPath();
		this.insUid = db.getInsUid();
		this.insDate = db.getInsDt();
		this.updUid = db.getUpdUid();
		this.updDate = db.getUpdDt();
		this.deleteFlag = db.getDeleteFlag();
		if(db.getAemCourseInfo()!=null) {
			this.courseInfoModel = new CourseInfoModel(db.getAemCourseInfo());
		}
		List<AemClass> listClass = db.getListClass();
		if(listClass != null) {
		    for(AemClass classEntity : listClass) {
		        ClassModel classModel = new ClassModel(classEntity);
		        this.ListClassModel.add(classModel);
		    }
		}
	}

	public Edu0102RequestModel(AemCourse db, Boolean isGetAllData) {
		this.key = db.getCourseId();
		this.courseCode = db.getCourseCode();
		this.courseNm = db.getCourseNm();
		this.courseStartDate = db.getCourseStartDt();
		this.courseEndDate = db.getCourseEndDt();
		this.courseContent = db.getCourseContent();
		this.courseGoal = db.getCourseGoal();
		this.status = db.getStatus();
        this.displayStatus = db.getDisplayStatus();
		this.isComingSoon = db.getIsComingSoon();
		this.imgPath = db.getImgPath();
        this.insUid = db.getInsUid();
        this.insDate = db.getInsDt();
        this.updUid = db.getUpdUid();
        this.updDate = db.getUpdDt();
        this.deleteFlag = db.getDeleteFlag();
//        if(db.getAemCourseInfo()!=null) {
//            this.courseInfoModel = new CourseInfoModel(db.getAemCourseInfo());
//        }
//        List<AemCourseSubjectMap> courseSubjectMapList = db.getListOfCourseSubjectMap();
//        for(AemCourseSubjectMap courseSubjectMap : courseSubjectMapList) {
//            AemCourseSubject subject = courseSubjectMap.getAemCouSubject();
//            if(subject != null) {
//                SubjectModel model = new SubjectModel(subject);
//                this.subjectModels.add(model);
//            }
//        }
	}

    public Edu0102RequestModel(AemCourse db, Boolean isGetAllData, boolean isClassOfSchudle) {
        this.key = db.getCourseId();
        this.courseCode = db.getCourseCode();
        this.courseNm = db.getCourseNm();
        this.courseStartDate = db.getCourseStartDt();
        this.courseEndDate = db.getCourseEndDt();
        this.courseContent = db.getCourseContent();
        this.courseGoal = db.getCourseGoal();
        this.status = db.getStatus();
        this.displayStatus = db.getDisplayStatus();
        this.isComingSoon = db.getIsComingSoon();
        this.imgPath = db.getImgPath();
        this.insUid = db.getInsUid();
        this.insDate = db.getInsDt();
        this.updUid = db.getUpdUid();
        this.updDate = db.getUpdDt();
        this.deleteFlag = db.getDeleteFlag();
//        if(db.getAemCourseInfo()!=null) {
//            this.courseInfoModel = new CourseInfoModel(db.getAemCourseInfo());
//        }
        List<AemCourseSubjectMap> courseSubjectMapList = db.getListOfCourseSubjectMap();
        for(AemCourseSubjectMap courseSubjectMap : courseSubjectMapList) {
            AemCourseSubject subject = courseSubjectMap.getAemCouSubject();
            if(subject != null) {
                SubjectModel model = new SubjectModel(subject);
                this.subjectModels.add(model);
            }
        }
    }
    
	
	
	public CourseInfoModel getCourseInfoModel() {
		return courseInfoModel;
	}

	public void setCourseInfoModel(CourseInfoModel courseInfoModel) {
		this.courseInfoModel = courseInfoModel;
	}

	public Boolean getIsComingSoon() {
		return isComingSoon;
	}

	public void setIsComingSoon(Boolean isComingSoon) {
		this.isComingSoon = isComingSoon;
	}
	
//
//	public List<StudentCourseMapModel> getStudentCourseMaps() {
//        return studentCourseMaps;
//    }
//
//    public void setStudentCourseMaps(List<StudentCourseMapModel> studentCourseMaps) {
//        this.studentCourseMaps = studentCourseMaps;
//    }
//
//    public List<AemStudentCourseMap> getStudentCourseMaps() {
//        return studentCourseMaps;
//    }
//
//    public void setStudentCourseMaps(List<AemStudentCourseMap> studentCourseMaps) {
//        this.studentCourseMaps = studentCourseMaps;
//    }

    public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseNm() {
		return courseNm;
	}

	public void setCourseNm(String courseNm) {
		this.courseNm = courseNm;
	}

	public String getCourseContent() {
		return courseContent;
	}

	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}

	public String getCourseGoal() {
		return courseGoal;
	}

	public void setCourseGoal(String courseGoal) {
		this.courseGoal = courseGoal;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(Boolean displayStatus) {
        this.displayStatus = displayStatus;
    }

    public Date getCourseStartDate() {
		return courseStartDate;
	}

	public void setCourseStartDate(Date courseStartDate) {
		this.courseStartDate = courseStartDate;
	}

	public Date getCourseEndDate() {
		return courseEndDate;
	}

	public void setCourseEndDate(Date courseEndDate) {
		this.courseEndDate = courseEndDate;
	}

	public List<SubjectModel> getSubjectModels() {
		return subjectModels;
	}

	public void setSubjectModels(List<SubjectModel> subjectModels) {
		this.subjectModels = subjectModels;
	}

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

    public List<ClassModel> getListClassModel() {
        return ListClassModel;
    }

    public void setListClassModel(List<ClassModel> listClassModel) {
        ListClassModel = listClassModel;
    }

    public List<String> getListUserUid() {
        return listUserUid;
    }

    public void setListUserUid(List<String> listUserUid) {
        this.listUserUid = listUserUid;
    }

}
