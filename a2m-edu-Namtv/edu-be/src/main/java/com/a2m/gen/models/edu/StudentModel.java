package com.a2m.gen.models.edu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.entities.edu.AemStudentCourseMap;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.EamStudentInfo;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.TsstUserModel;

/**
 *
 * @author phongnc
 */
public class StudentModel extends ParamBaseModel{
    
	private String address;
	
	private String cellPhone;
	
	private Date dob;
	
	private String email;
	
	private String emailVerifyKey;
	
	private String fullName;

	private String userId;
	
	private Boolean gender;
	
	private String imgPath;
	
	private  String organization;
	
	private String position;
	
	private Boolean twoFAenable;
	
	private  String twoFAKey;
	
	private String idea;
	
	private String comment;
	
	private Boolean typicalFlag;

	private Boolean isExportAll;

	private List<Integer> listCheckedId;
	
	private List<TsstUserModel> listTsstUserModel;
	
	private List<ClassStudentMapModel> ListClassStudentMapModel;
	
//	private List<AemStudentCourseMap> studentCourseMaps = new ArrayList<>();

	public StudentModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StudentModel(AemStudentEntity aemStudent) {
		this.key = aemStudent.getStudentInfoId();
		this.address = aemStudent.getAddress();
		this.cellPhone = aemStudent.getCellPhone();
		this.dob = aemStudent.getDob();
		this.email = aemStudent.getEmail();
		this.emailVerifyKey = aemStudent.getEmailVerifyKey();
		this.fullName = aemStudent.getFullName();
		this.gender = aemStudent.getGender();
		this.imgPath = aemStudent.getImgPath();
		this.organization = aemStudent.getOrganization();
		this.position = aemStudent.getPosition();
		this.twoFAenable = aemStudent.getTwoFAEnable();
		this.twoFAKey = aemStudent.getTwoFAKey();
		this.idea = aemStudent.getIdea();
//		this.comment = comment;
		this.typicalFlag = aemStudent.getTypicalFlag();
		List<AemClassStudentMap> listAemClassStudentMap = aemStudent.getListOfClassStudentMap();
		for(AemClassStudentMap classStudentmap: listAemClassStudentMap) {
			ClassStudentMapModel classStudentMapModel = new ClassStudentMapModel(classStudentmap);
			if(classStudentMapModel.getKey() != null) {
				this.ListClassStudentMapModel.add(classStudentMapModel);
			}
		}
	}

	public StudentModel(EamStudentInfo aemStudent) {
		this.key = aemStudent.getStudentInfoId();
		this.address = aemStudent.getAddress();
		this.cellPhone = aemStudent.getCellPhone();
		this.dob = aemStudent.getDob();
		this.email = aemStudent.getEmail();
		this.emailVerifyKey = aemStudent.getEmailVerifyKey();
		this.fullName = aemStudent.getFullName();
		this.gender = aemStudent.getGender();
		this.imgPath = aemStudent.getImgPath();
		this.organization = aemStudent.getOrganization();
		this.position = aemStudent.getPosition();
		this.twoFAenable = aemStudent.getTwoFAEnable();
		this.twoFAKey = aemStudent.getTwoFAKey();
		this.idea = aemStudent.getIdea();
//		this.comment = comment;
		this.typicalFlag = aemStudent.getTypicalFlag();
		
//		List<AemClassStudentMap> listAemClassStudentMap = aemStudent.getListOfClassStudentMap();
//		for(AemClassStudentMap classStudentmap: listAemClassStudentMap) {
//			ClassStudentMapModel classStudentMapModel = new ClassStudentMapModel(classStudentmap);
//			if(classStudentMapModel.getKey() != null) {
//				this.ListClassStudentMapModel.add(classStudentMapModel);
//			}
//		}
	}

	
	public StudentModel(AemStudentEntity aemStudent,Boolean isPublic ) {
		this.key = aemStudent.getStudentInfoId();
		this.address = aemStudent.getAddress();
		this.cellPhone = aemStudent.getCellPhone();
		this.dob = aemStudent.getDob();
		this.email = aemStudent.getEmail();
		this.emailVerifyKey = aemStudent.getEmailVerifyKey();
		this.fullName = aemStudent.getFullName();
		this.gender = aemStudent.getGender();
		this.imgPath = aemStudent.getImgPath();
		this.organization = aemStudent.getOrganization();
		this.position = aemStudent.getPosition();
		this.twoFAenable = aemStudent.getTwoFAEnable();
		this.twoFAKey = aemStudent.getTwoFAKey();
		this.idea = aemStudent.getIdea();
//		this.comment = comment;
		this.typicalFlag = aemStudent.getTypicalFlag();
	}
	
//	public List<AemStudentCourseMap> getStudentCourseMaps() {
//		return studentCourseMaps;
//	}
//
//	public void setStudentCourseMaps(List<AemStudentCourseMap> studentCourseMaps) {
//		this.studentCourseMaps = studentCourseMaps;
//	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsExportAll() {
		return isExportAll;
	}

	public void setIsExportAll(Boolean isExportAll) {
		this.isExportAll = isExportAll;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellphone) {
		this.cellPhone = cellphone;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailVerifyKey() {
		return emailVerifyKey;
	}

	public void setEmailVerifyKey(String emailVerifyKey) {
		this.emailVerifyKey = emailVerifyKey;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Boolean getTwoFAenable() {
		return twoFAenable;
	}

	public void setTwoFAenable(Boolean twoFAenable) {
		this.twoFAenable = twoFAenable;
	}

	public String getTwoFAKey() {
		return twoFAKey;
	}

	public void setTwoFAKey(String twoFAKey) {
		this.twoFAKey = twoFAKey;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getTypicalFlag() {
		return typicalFlag;
	}

	public void setTypicalFlag(Boolean typicalFlag) {
		this.typicalFlag = typicalFlag;
	}

	public List<Integer> getListCheckedId() {
		return listCheckedId;
	}

	public void setListCheckedId(List<Integer> listCheckedId) {
		this.listCheckedId = listCheckedId;
	}

	public List<ClassStudentMapModel> getListClassStudentMapModel() {
		return ListClassStudentMapModel;
	}

	public void setListClassStudentMapModel(List<ClassStudentMapModel> listClassStudentMapModel) {
		ListClassStudentMapModel = listClassStudentMapModel;
	}

	public List<TsstUserModel> getListTsstUserModel() {
		return listTsstUserModel;
	}

	public void setListTsstUserModel(List<TsstUserModel> listTsstUserModel) {
		this.listTsstUserModel = listTsstUserModel;
	}

}
