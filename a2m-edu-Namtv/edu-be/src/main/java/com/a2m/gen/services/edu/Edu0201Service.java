package com.a2m.gen.services.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.ClassDao;
import com.a2m.gen.dao.ClassStudentMapDao;
import com.a2m.gen.dao.ClassTeacherMapDao;
import com.a2m.gen.dao.StudentCourseDao;
import com.a2m.gen.dao.StudentDao;
import com.a2m.gen.dao.SubjectDao;
import com.a2m.gen.dao.TeacherDao;
import com.a2m.gen.entities.edu.AemClass;
import com.a2m.gen.entities.edu.AemClassStudentMap;
import com.a2m.gen.entities.edu.AemClassTeacherMap;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemStudentCourseMap;
import com.a2m.gen.entities.edu.AemStudentEntity;
import com.a2m.gen.entities.edu.AemTeacherEntity;
import com.a2m.gen.models.ParamBaseModel;
import com.a2m.gen.models.edu.ClassModel;
import com.a2m.gen.models.edu.ClassStudentMapModel;
import com.a2m.gen.models.edu.ClassTeacherMapModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.models.edu.RoomModel;
import com.a2m.gen.models.edu.StudentCourseMapModel;
import com.a2m.gen.models.edu.StudentModel;
import com.a2m.gen.models.edu.TeacherModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;


@Service
public class Edu0201Service {
	 @Autowired 
	 private CommonService commonService;
	 
	 @Autowired 
	 private ClassDao classDao;
	 
	 @Autowired
	 private StudentDao studentDao;
	 
	 @Autowired
	 private TeacherDao teacherDao;
	 
	 @Autowired
	 private ClassStudentMapDao clssStuDao;
	 
	 @Autowired
	 private ClassTeacherMapDao clssTeaDao;
     
     @Autowired 
     private SubjectDao subjectDao;
	 
	 @Autowired StudentCourseDao studentCourseDao;
	 
	public List<ClassModel> get(ClassModel search) throws Exception {
		List<ClassModel> classModels = new ArrayList<ClassModel>();
		List<AemClass> lst = classDao.get(search);
		for(AemClass db :lst) {
			ClassModel model = new ClassModel(db, true); 
			classModels.add(model);
		}
		return classModels;
	}
	
	public List<ClassModel> getListClassInprogress(ClassModel search) throws Exception {
		List<ClassModel> classModels = new ArrayList<ClassModel>();
		List<AemClass> lst = classDao.getListClassInProgress(search);
		for(AemClass db :lst) {
			ClassModel model = new ClassModel(db); 
			classModels.add(model);
		}
		return classModels;
	}
	
	public Long getCountInProgress(ParamBaseModel search) {
		return classDao.countInprogress(search);
	}
	
	public ClassModel getLastClass() throws Exception {
		ClassModel search = new ClassModel();
		search.setRows(1);
		search.setPage(0);
		List<ClassModel> classModels = new ArrayList<ClassModel>();
		List<AemClass> lst = classDao.get(search);
		for(AemClass db :lst) {
			ClassModel model = new ClassModel(db); 
			
			classModels.add(model);
		}
		if(classModels.size()>0) {
			return classModels.get(0);
		}else {
			return null;
		}
	}
	
	public Map<Object, Object> saveClass(ClassModel model) throws Exception {
		Map<Object, Object> res = new HashMap<Object, Object>();
		res.put(CommonContants.STATUS_KEY, CommonContants.RESULT_OK);
		String userUid = commonService.getUserUid();
		AemClass db = new AemClass();
		AemClass saveClass = new AemClass();
		List<String> newListStudentId = model.getListStudentId();
		List<String> newListTeacherId  = model.getListTeacherId();
		if(!"".equals(CastUtil.castToString(model.getKey()))) {
				db = classDao.getClassEntity(model);
				model.setUpdUid(userUid);
				 saveClass = classDao.saveClass(model, db);
		}else {
				model.setInsUid(userUid);
				
				model.setClassCode("0");
				saveClass = classDao.saveClass(model, db);
				
//		   Start Set class code
				ClassModel saveModel = new ClassModel(db);
				saveModel.setClassCode("CL"+db.getClassId());
//			End	Set class code
				saveClass = classDao.saveClass(saveModel,db);
				
				 
		}
		
		//		// Get Old List Class Student and Remove it
		List<AemClassStudentMap> oldListClassStudentMap = saveClass.getListOfClassStudentMap();
		for (AemClassStudentMap aemClassStudentMap : oldListClassStudentMap) {
				clssStuDao.deleteClassStudent(aemClassStudentMap.getTableId());
		}
		
		// Get Old List Class Teacher and Remove it
		List<AemClassTeacherMap> oldListClassTeacherMap = saveClass.getListOfClassTeacherMap();
		for (AemClassTeacherMap aemClassTeacherMap : oldListClassTeacherMap) {
			clssTeaDao.deleteClassTeacher(aemClassTeacherMap.getTableId());
		}
	
		//Start Save ClassStudent
		ClassStudentMapModel ClssStuModel = new ClassStudentMapModel();
		ClssStuModel.setInsUid(userUid);
		ClassModel classModelSet = new ClassModel(saveClass);
		ClssStuModel.setClassModel(classModelSet);
		for (String element : newListStudentId) {
			AemClassStudentMap dbMap = new AemClassStudentMap();
			StudentModel studentModel = new StudentModel();
			studentModel.setKey(Long.parseLong(element));
			AemStudentEntity student = studentDao.getStudent(studentModel);
			StudentModel studentModelSet = new StudentModel(student);
			ClssStuModel.setStudentModel(studentModelSet);
			clssStuDao.saveClassStudent(ClssStuModel,dbMap);	
		}
		//End Save ClassStudent
		
		//Start Save ClassTeacher
		ClassTeacherMapModel clssTeaModel = new ClassTeacherMapModel();
		clssTeaModel.setInsUid(userUid);
		clssTeaModel.setAemClass(saveClass);
		for (String element  : newListTeacherId) {
			AemClassTeacherMap dbTeacherMap = new AemClassTeacherMap();
			TeacherModel teacherModel = new TeacherModel();
			teacherModel.setKey(Long.parseLong(element));
			AemTeacherEntity teacher = teacherDao.getTeacher(teacherModel);
			clssTeaModel.setEamTeacherEntity(teacher);
			clssTeaDao.saveClassTeacher(clssTeaModel, dbTeacherMap);
		}
		
		//End Save ClassTeacher

        //Start Save ClassStudent
//		AemStudentCourseMap studentCourseMap = new AemStudentCourseMap();
//		for (String element : newListStudentId) {
////		    StudentCourseMapModel studentCourseMapModel = new StudentCourseMapModel(studentCourseMap);
////		    studentCourseMap = 
//            clssStuDao.saveStudentCourseMap(element, model, studentCourseMap);    
//        }
        //End Save ClassStudent
		
		ClassModel classModel = new ClassModel(saveClass);
		res.put(CommonContants.DETAIL_KEY, classModel);
		
		return res;
	}
	
	public Long getCount(ParamBaseModel search) {
		return classDao.count(search);
	}

	public Boolean deleteList(List<ClassModel> listClass) throws Exception {
		for(ClassModel classModel : listClass) {
			Long id = classModel.getKey();
			delete(id);
		}
		return true;
	}

	public Boolean delete(Long id) {
		ClassModel classModel = new ClassModel();
		classModel.setKey(id);
		 AemClass classEntity= classDao.getClassEntity(classModel);
		 List<AemClassStudentMap> listClssStuMap = classEntity.getListOfClassStudentMap();
		 for (AemClassStudentMap element : listClssStuMap) {
			clssStuDao.deleteClassStudent(element.getTableId());
		}
		 
		 List<AemClassTeacherMap> listClssTeaMap = classEntity.getListOfClassTeacherMap();
		 for (AemClassTeacherMap element : listClssTeaMap) {
			clssTeaDao.deleteClassTeacher(element.getTableId());
		}
		boolean deleteClass = classDao.deleteClass(id);
	return deleteClass;
	}
	
	public List<StudentModel> getListStudent(ParamBaseModel search) throws Exception {
		List<StudentModel> studentModels = new ArrayList<StudentModel>();
		List<AemStudentEntity> lst = studentDao.getList(search);
		for(AemStudentEntity db :lst) {
			StudentModel model = new StudentModel(db); 
			studentModels.add(model);
		}
		return studentModels;
	}
	
	public Long getCountStudent(ParamBaseModel search) {
		return studentDao.count(search);
	}
	
	public Long getCountTeacher(ParamBaseModel search) {
		return teacherDao.count(search);
	}
	
	public List<StudentModel> getListStudentByClassId(Long Id) {
		List<StudentModel> list  = new ArrayList<StudentModel>();
		
		ClassModel classModel = new ClassModel();
		classModel.setKey(Id);
		AemClass aemClass = classDao.getClassEntity(classModel);
		List<AemClassStudentMap> listMap = aemClass.getListOfClassStudentMap();
		for (AemClassStudentMap element : listMap) {
			AemStudentEntity entity = element.getEamStudent();
			StudentModel model = new StudentModel(entity);
			list.add(model);
		}
		return list;
	}
	
	public List<TeacherModel> getListTeacher(ParamBaseModel search) throws Exception {
		List<TeacherModel> teacherModels = new ArrayList<TeacherModel>();
		List<AemTeacherEntity> lst = teacherDao.get(search);
		for(AemTeacherEntity db :lst) {
			TeacherModel model = new TeacherModel(db); 
			String userUid = subjectDao.getUserIdByTeachId(model.getKey());
			model.setUserUid(userUid);
			teacherModels.add(model);
		}
		return teacherModels;
	}
	
	public List<TeacherModel> getListTeacherByClassId(Long Id) {
		List<TeacherModel> list  = new ArrayList<TeacherModel>();
		
		ClassModel classModel = new ClassModel();
		classModel.setKey(Id); 
		AemClass aemClass = classDao.getClassEntity(classModel);
		List<AemClassTeacherMap> listMap = aemClass.getListOfClassTeacherMap();
		for (AemClassTeacherMap element : listMap) {
			AemTeacherEntity entity = element.getEamTeacher();
			TeacherModel model = new TeacherModel(entity);
			list.add(model);
		}
		return list;
	}
	
	public ClassModel getClassById (Long Id) {
		ClassModel classModel = new ClassModel();
		classModel.setKey(Id);
		AemClass aemClass =  classDao.getClassEntity(classModel);
		ClassModel result = new ClassModel(aemClass);
		return result;
	}

	public List<ClassModel> getListClassModelByStudentId(Long Id){
		StudentModel studentModel  = new StudentModel();
		studentModel.setKey(Id);
		List<AemClassStudentMap> aemClassStudentMaps =  clssStuDao.getClassStudentsByStudentModel(studentModel);
		List<ClassModel> classModels = new ArrayList<ClassModel>();
		for (AemClassStudentMap aemClassStudentMap : aemClassStudentMaps) {
		AemClass aemClass	= aemClassStudentMap.getAemClass();
		ClassModel classModel = new ClassModel(aemClass);
		classModels.add(classModel);
		}
		return classModels;
	}
	
	public List<Edu0102RequestModel> getCourseByStudentId(Long id){
		List<Edu0102RequestModel> courseModels = new ArrayList<>();
		StudentModel model = new StudentModel();
		model.setKey(id);
		List<AemStudentCourseMap> list = studentCourseDao.getStudentCoursesByStudentModel(model);
		for (AemStudentCourseMap element : list) {
			AemCourse aemCourseS = element.getAemCourseS();
			Edu0102RequestModel courseModel = new Edu0102RequestModel(aemCourseS);
			courseModels.add(courseModel);
		}
		return courseModels;
	}
	
	public List<ClassModel> getListClassByUserUid(String userUid) throws Exception{
		List<AemClass> list = classDao.getListClassByUserUid(userUid);
		List<ClassModel> result = new ArrayList<>();
		for (AemClass aemClass : list) {
			ClassModel model = new ClassModel(aemClass);
			result.add(model);
		}
		return result;
	}
	
}
