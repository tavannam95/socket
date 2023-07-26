package com.a2m.gen.services.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.dao.CourseDao;
import com.a2m.gen.dao.CourseInfoDao;
import com.a2m.gen.entities.edu.AemCourse;
import com.a2m.gen.entities.edu.AemCourseInfo;
import com.a2m.gen.entities.edu.AemCourseSubject;
import com.a2m.gen.entities.edu.AemCourseSubjectMap;
import com.a2m.gen.entities.edu.AemEvent;
import com.a2m.gen.models.ParamSearchModel;
import com.a2m.gen.models.course.CourseInfoModel;
import com.a2m.gen.models.course.CourseSubjectMapModel;
import com.a2m.gen.models.course.SubjectModel;
import com.a2m.gen.models.edu.Edu0102RequestModel;
import com.a2m.gen.services.common.CommonService;
import com.a2m.gen.utils.CastUtil;

@Service
public class CourseInfoService {
	
	 @Autowired 
	 private CommonService commonService;
	
	@Autowired 
	private CourseInfoDao courseInfoDao;
	
	@Autowired 
	private CourseDao courseDao;
	
	public Map<Object, Object> save(CourseInfoModel model) throws Exception {
	    Map<Object, Object> res = new HashMap<Object, Object>();
	    
	    //Get course entity
	    Long courseId = model.getCourseId();
	    Edu0102RequestModel courseModel = new Edu0102RequestModel();
	    courseModel.setKey(courseId);
	    AemCourse aemCourse = courseDao.getCourse(courseModel);
	    
	    //Set course entity to courseInfo entity 
	    AemCourseInfo db = new AemCourseInfo();
	    String userUid = commonService.getUserUid();
	    
	    if(!"".equals(CastUtil.castToString(model.getKey()))) {
			db = courseInfoDao.getCourseInfo(model);
			model.setUpdUid(userUid);
			AemCourseInfo saveCourseInfo = courseInfoDao.saveCourseInfo(model, db);		
	    }else {
	        model.setInsUid(userUid);
	    	db.setAemCourse(aemCourse);
	    	AemCourseInfo saveCourseInfo = courseInfoDao.saveCourseInfo(model, db);		
		}
		return res;
	}
	
	public List<CourseInfoModel> getListCourseInFoByCourseId(ParamSearchModel args ){
		List<CourseInfoModel> listCourseInfoModel = new ArrayList<>();
		List<AemCourseInfo> listAemCourseInfo = courseInfoDao.getCourseInfoByCourseId(args);
		for (AemCourseInfo aemCourseInfo : listAemCourseInfo) {
			CourseInfoModel courseInfoModel = new CourseInfoModel(aemCourseInfo);
			listCourseInfoModel.add(courseInfoModel);
		}
		
		return listCourseInfoModel;
	}

}
